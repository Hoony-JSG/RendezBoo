package com.ssafy.a107.common.util;

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
import java.security.Key;
import java.util.Date;

/**
 * access-token만 만들고
 * -----refresh-token 만들어야함-----
 */
@Slf4j
@Component
public class JwtTokenProvider {



    private final UserDetailsService userDetailsService;
    private final Key secretKey;
    private final long ACCESS_TOKEN_VALID_MILISECOND;
    private final long REFRESH_TOKEN_VALID_MILISECOND;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-validity-milliseconds}") long ACCESS_TOKEN_VALID_MILISECOND,
                            @Value("${jwt.refresh-token-validity-milliseconds}") long REFRESH_TOKEN_VALID_MILISECOND,
                            UserDetailsService userDetailsService) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.ACCESS_TOKEN_VALID_MILISECOND = ACCESS_TOKEN_VALID_MILISECOND;
        this.REFRESH_TOKEN_VALID_MILISECOND = REFRESH_TOKEN_VALID_MILISECOND;

        this.userDetailsService = userDetailsService;
    }

    public String createToken(User user) {
        return Jwts.builder()
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .claim("email", user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_MILISECOND))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.debug("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.debug("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.debug("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("access-token");
    }
}
