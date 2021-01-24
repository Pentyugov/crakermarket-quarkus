package com.netkreker.service;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.cache.NoCache;

//@NoCache
public class TokenUtil {
    public static String getToken(JsonWebToken jsonWebToken) {
        return "Bearer " + jsonWebToken.getRawToken();
    }
}
