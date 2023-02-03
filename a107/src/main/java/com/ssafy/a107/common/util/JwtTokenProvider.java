package com.ssafy.a107.common.util;

import com.ssafy.a107.common.auth.CustomUserDetails;
import com.ssafy.a107.db.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * access-token만 만들고
 * -----refresh-token 만들어야함-----
 */
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
                .setSigningKey(secretKey)
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
}