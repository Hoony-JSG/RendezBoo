package com.ssafy.a107.common.util;

import com.ssafy.a107.common.auth.CustomUserDetails;
import com.ssafy.a107.common.exception.JwtInvalidException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {



    private final UserDetailsService userDetailsService;
    private final String secretKey;
    private final long ACCESS_TOKEN_VALID_MILISECOND;
    private final long REFRESH_TOKEN_VALID_MILISECOND;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-validity-milliseconds}") long ACCESS_TOKEN_VALID_MILISECOND,
                            @Value("${jwt.refresh-token-validity-milliseconds}") long REFRESH_TOKEN_VALID_MILISECOND,
                            UserDetailsService userDetailsService) {
        this.secretKey = secretKey;
        this.ACCESS_TOKEN_VALID_MILISECOND = ACCESS_TOKEN_VALID_MILISECOND;
        this.REFRESH_TOKEN_VALID_MILISECOND = REFRESH_TOKEN_VALID_MILISECOND;

        this.userDetailsService = userDetailsService;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessToken(String userEmail) {
        return doGenerateToken(userEmail, ACCESS_TOKEN_VALID_MILISECOND);
    }

    public String generateRefreshToken(String userEmail) {
        return doGenerateToken(userEmail, REFRESH_TOKEN_VALID_MILISECOND);
    }

    private String doGenerateToken(String userEmail, long expTime) {
        Claims claims = Jwts.claims();
        claims.put("email", userEmail);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, CustomUserDetails userDetails) {
        String userEmail = getUserEmail(token);
        return userEmail.equals(userDetails.getUser().getEmail()) && !isTokenExpired(token);
    }

    public Claims parseClaimsFromToken(String token) throws JwtInvalidException {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException signatureException) {
            throw new JwtInvalidException("잘못된 secretKey", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("만료된 토큰", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("잘못된 토큰", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("잘못된 인수 사용", illegalArgumentException);
        }

        return claims;
    }

    public long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(accessToken)
                .getBody().getExpiration();

        long now = new Date().getTime();
        return expiration.getTime() - now;
    }
}