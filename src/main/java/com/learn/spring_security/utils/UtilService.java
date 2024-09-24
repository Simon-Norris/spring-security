package com.learn.spring_security.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.base.entity.RefreshTokenAttributes;
import com.learn.spring_security.config.security.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.SecureRandom;

public class UtilService {

    private static Logger logger = LoggerFactory.getLogger(UtilService.class);

    public static SecurityUser loggedInUser() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof SecurityUser)
            return (SecurityUser) authentication.getPrincipal();
        else throw new UsernameNotFoundException("Logged in User not identified");
    }

    /**
     * Converts any object to String
     *
     * @param object type of object to parse into string
     * @return string representation of object if successful else null
     */
    public static String convertToJsonString(Object object) {
        try {
            Gson gson = new Gson();
            return gson.toJson(object);
        } catch (Exception e) {
            logger.error("Can't Convert class {} to JSON string", object.getClass().getName());
            return null;
        }
    }

    /**
     * Converts any JSON String to class of Type T
     *
     * @param jsonString JSON String
     * @param tClass Class type
     * @return converted object to of class type if successful else null
     * @param <T> type of class
     */
    public static <T> T convertFromJsonString(String jsonString, Class<T> tClass) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, tClass);
        } catch (JsonSyntaxException e) {
            logger.error("Can't Convert Json String {} to class {}", jsonString, tClass);
            return null;
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");

        if (userAgent == null) {
            return "Unknown Device";
        } else if (userAgent.toLowerCase().contains("mobile")) {
            return "Mobile";
        } else if (userAgent.toLowerCase().contains("tablet")) {
            return "Tablet";
        } else {
            return "Desktop";
        }
    }

    public static RefreshTokenAttributes refreshTokenAttributes(HttpServletRequest request) {
        return RefreshTokenAttributes
                .builder()
                .device(UtilService.getDeviceType(request))
                .ip(UtilService.getClientIp(request))
                .build();
    }

    public static String generateSecureToken() {
        SecureRandom secureRandom = new SecureRandom(); // SecureRandom is a strong random number generator
        byte[] token = new byte[64]; // You can choose the length (64 bytes gives good security)
        secureRandom.nextBytes(token);
        return Base64.encodeBase64URLSafeString(token); // Encode as a base64 URL safe string
    }

    public static byte[] generateRandom(int length) {
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return randomBytes;
    }

}
