package com.example.jwtandwebsocket.utils.token;

import javax.servlet.http.HttpServletRequest;

public abstract class TokenExtractor {
    public abstract String extract(HttpServletRequest request);
}
