package com.ssafy.a107.common.util;

import com.ssafy.a107.common.auth.CustomUserDetails;
import com.ssafy.a107.common.exception.JwtInvalidException;
import com.ssafy.a107.db.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final long ACCESS_TOKEN_VALID_MILISECOND;
    private final long REFRESH_TOKEN_VALID_MILISECOND;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-validity-milliseconds}") long ACCESS_TOKEN_VALID_MILISECOND,
                            @Value("${jwt.refresh-token-validity-milliseconds}") long REFRESH_TOKEN_VALID_MILISECOND) {
        this.secretKey = secretKey;
        this.ACCESS_TOKEN_VALID_MILISECOND = ACCESS_TOKEN_VALID_MILISECOND;
        this.REFRESH_TOKEN_VALID_MILISECOND = REFRESH_TOKEN_VALID_MILISECOND;
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

    public String generateAccessToken(User user) {
        return doGenerateToken(user, ACCESS_TOKEN_VALID_MILISECOND);
    }

    public String generateRefreshToken(User user) {
        return doGenerateToken(user, REFRESH_TOKEN_VALID_MILISECOND);
    }

    private String doGenerateToken(User user, long expTime) {
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("seq", user.getSeq());
        claims.put("name", user.getName());
        claims.put("gender", user.getGender());

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
            throw new JwtInvalidException("Wrong secretKey", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("Expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("Malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("Illegal argument", illegalArgumentException);
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