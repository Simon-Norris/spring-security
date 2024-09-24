package com.learn.spring_security.base.service;

import com.learn.spring_security.app.exceptions.TokenRefreshException;
import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.base.entity.RefreshToken;
import com.learn.spring_security.base.entity.RefreshTokenAttributes;
import com.learn.spring_security.base.repo.RefreshTokenRepository;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.repo.UserRepo;
import com.learn.spring_security.utils.EncryptionUtils;
import com.learn.spring_security.utils.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${jwt.refresh-expiration}")
  private long refreshExpirationInMillis;  // Store refresh token expiration in milliseconds


  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public Optional<RefreshToken> findByUser(User user) {
    return refreshTokenRepository.findByUser(user);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public RefreshToken createRefreshToken(String username, RefreshTokenAttributes attrs, String accessToken) throws UsernameNotFoundException, TokenRefreshException {
    Optional<User> byUsername = userRepo.findByUsername(username);
    User user = byUsername.orElseThrow(() -> new UsernameNotFoundException("User not found"));

    //delete old refresh token bind to user if any and delete
    Optional<RefreshToken> existingRefreshToken = this.findByUser(user);
    existingRefreshToken.ifPresent(token -> {
      this.refreshTokenRepository.deleteByUser(user);
    });


    //issue new refresh token
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(user);
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpirationInMillis));
    refreshToken.setToken(System.currentTimeMillis()+"-"+UUID.randomUUID());
    refreshToken.setAttributes(UtilService.convertToJsonString(attrs));

    try {
      refreshToken.setAccessToken(EncryptionUtils.encrypt(accessToken));
    } catch (Exception e) {
      throw new TokenRefreshException("Cannot issue refresh token at the moment. Please try again later");
    }

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  /**
   * Verified Expiry of Refresh token, deletes if it was expired else returns the same token
   *
   * @param token a refresh token
   * @return same refresh token (token) if it wasn't expired
   * @throws TokenRefreshException
   */
  @Transactional
  public RefreshToken verifyExpiration(RefreshToken token, String accessToken) throws TokenRefreshException {

    if (!validateAccessTokenWithRefreshToken(token, accessToken)) {
        throw new TokenRefreshException("Token not matched. Please login again to continue");
    }
    if ((token.getExpiryDate().compareTo(Instant.now()) < 0)){
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException("Session expired. Please login again to continue");
    }
    return token;
  }

  /**
   *
   * @param oldToken existing refresh token
   * @param username subject
   * @param attrs different refresh token attributes
   * @param accessToken jwt accessToken
   * @return new refresh token
   * @throws TokenRefreshException
   * @throws UsernameNotFoundException
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public RefreshToken rotateRefreshToken(String oldToken, String username, RefreshTokenAttributes attrs, String accessToken) throws TokenRefreshException, UsernameNotFoundException {
    Optional<RefreshToken> oldRefreshTokenOpt = refreshTokenRepository.findByToken(oldToken);
    RefreshToken oldRefreshToken = oldRefreshTokenOpt.orElseThrow(() -> new TokenRefreshException("Invalid refresh token"));

    // Invalidate old refresh token by deleting it
    refreshTokenRepository.delete(oldRefreshToken);

    // Now create a new refresh token
    return createRefreshToken(username, attrs, accessToken);
  }

  /**
   * Validates Access Token with refresh token
   *
   * @param refreshToken saved refresh token instance
   * @param accessToken encoded jwt access token
   * @return true if saved refresh token instance and jwt access token matches otherwise false
   */
  public boolean validateAccessTokenWithRefreshToken(RefreshToken refreshToken, String accessToken) throws TokenRefreshException {
    try {
      String decryptedToken = EncryptionUtils.decrypt(refreshToken.getAccessToken());
      return accessToken.equals(decryptedToken);
    } catch (Exception e) {
      throw new TokenRefreshException("Token validation failed. Please login again to continue");
    }
  }

  @Transactional
  public boolean deleteByUser(User user) {
    Optional<RefreshToken> existingRefreshToken = this.findByUser(user);
    if (existingRefreshToken.isEmpty()) return false;
    try {
      existingRefreshToken.ifPresent(token -> {
        this.refreshTokenRepository.deleteByUser(user);
      });
      return true;
    } catch (Exception e ) {
      return false;
    }
  }


}
