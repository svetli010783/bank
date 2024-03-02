package com.test.task.bank.security.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class JwtHelper {
    public Optional<String> extractJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(SecurityConst.PREFIX_AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(SecurityConst.PREFIX_BEARER)) {
            return Optional.of(authHeader.substring(SecurityConst.PREFIX_BEARER.length()));
        }
        return Optional.empty();
    }

    class SecurityConst {
        public final static String PREFIX_BEARER = "Bearer ";
        public final static String PREFIX_AUTHORIZATION = "Authorization";
    }

}
