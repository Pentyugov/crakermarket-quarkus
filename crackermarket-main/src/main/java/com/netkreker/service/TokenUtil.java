package com.netkreker.service;

import org.eclipse.microprofile.jwt.JsonWebToken;

public class TokenUtil {
    public static String getToken(JsonWebToken jsonWebToken) {
        return "Bearer " + jsonWebToken.getRawToken();
    }
}
