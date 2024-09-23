package com.learn.spring_security.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final SecurityUserDetailsService userDetailsService;

    public AuthFilter(JwtUtils jwtUtils, SecurityUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (jwt != null) {
                String username = jwtUtils.extractUsername(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null && jwtUtils.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            logger.error("::: Access Token Expired ::: ");
            try {
                String refreshToken = request.getHeader("Refresh-Token");
                if (refreshToken != null && !jwtUtils.validateRefreshToken(refreshToken)) {
                    String newAccessToken = jwtUtils.generateRefreshToken(e.getClaims().getSubject());
                    response.setHeader("New-Access-Token", newAccessToken);
                    filterChain.doFilter(request, response);
                } else {
                    logger.error("::: Refresh Token Expired/not presesnt ::: ");
                    request.setAttribute("exception", e);
                }
            } catch (ExpiredJwtException ex) {
                logger.error("::: Refresh Token Expired ::: ");
                request.setAttribute("exception", ex);
            }
        } catch (Exception ex) {
            logger.error("::: Could not set user authentication in security context due to {}:::", ex);
            request.setAttribute("exception", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
