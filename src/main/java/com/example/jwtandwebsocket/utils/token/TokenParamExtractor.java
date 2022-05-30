package com.example.jwtandwebsocket.utils.token;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenParamExtractor extends TokenExtractor {

    private final String TOKEN_PARAM_NAME = "token";

    @Override
    public String extract(HttpServletRequest request) {
        return this.extractTokenFromParam(request);
    }

    public String extractTokenFromParam(HttpServletRequest request) {
        String token = null;
        if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
            String[] tokenParamValue = request.getParameterMap().get(TOKEN_PARAM_NAME);
            if (tokenParamValue != null && tokenParamValue.length == 1) {
                token = tokenParamValue[0];
            }
        }
        if (token == null || token.trim().equals("")) {
            throw new AuthenticationServiceException("Authorization query parameter cannot be blank!");
        }
        return token;
    }
}
