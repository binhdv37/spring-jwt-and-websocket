package com.example.jwtandwebsocket.utils.token;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenHeaderExtractor extends TokenExtractor {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";

    @Override
    public String extract(HttpServletRequest request) {
        return this.extractTokenFromHeader(request);
    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || authHeader.length() == 0) {
            throw new AuthenticationServiceException("Authorization header can not be blank!");
        }

        if (authHeader.length() <= BEARER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size");
        }

        return authHeader.substring(BEARER_PREFIX.length());
    }
}
