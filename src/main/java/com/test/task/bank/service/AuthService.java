package com.test.task.bank.service;

import com.test.task.bank.dto.response.TokenResponse;
import com.test.task.bank.utils.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthService {
    @Value("${jwt.secret.key}")
    String secretKey;
    @Value("${jwt.token.access.expiration}")
    long accessExpiration;

    public Map<String, Object> extractAllClaims(String token) {
        Map<String, Object> jwt = null;
        try {

            jwt = Jwts
                    .parserBuilder()
                    .setSigningKey(JwtHelper.getSignInKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("Token {} was expired.", token);
        } catch (MalformedJwtException e) {
            log.warn("Token {} is MalformedJwt token.", token);
        } catch (IllegalArgumentException e) {
            log.warn("Token {} was IllegalArgument", token);
        } catch (Exception e) {
            log.warn("Uncaught exception in token {} parsing process", token);
        }
        if (jwt == null)
            return Map.of();
        return jwt;
    }

    public TokenResponse buildToken(
            Map<String, Object> claims
    ) {
        String key = secretKey;
        long exp = accessExpiration;
        Date expiredAt = new Date(System.currentTimeMillis() + exp);
        String token = Jwts
                .builder()
                .addClaims(claims)
                .setExpiration(expiredAt)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(JwtHelper.getSignInKey(key), SignatureAlgorithm.HS512)
                .compact();
        return new TokenResponse(token, ZonedDateTime.ofInstant(expiredAt.toInstant(),
                ZoneId.systemDefault()));
    }
}
