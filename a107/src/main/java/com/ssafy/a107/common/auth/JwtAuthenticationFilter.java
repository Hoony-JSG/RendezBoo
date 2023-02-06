package com.ssafy.a107.common.auth;

import com.ssafy.a107.common.util.JwtTokenProvider;
import com.ssafy.a107.db.repository.LogoutAccessTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getToken(request);

        if(accessToken != null) {
            checkLogout(accessToken);
            String userEmail = jwtTokenProvider.getUserEmail(accessToken);

            if(userEmail != null) {
                CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);
                isEmailFromTokenAndUserDetailsEqual(userEmail, userDetails.getUser().getEmail());
                validateAccessToken(accessToken, userDetails);
                processSecurity(request, userDetails);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) return headerAuth.substring(7);
        return null;
    }

    private void checkLogout(String accessToken) {
        if(logoutAccessTokenRedisRepository.existsById(accessToken)) {
            throw new IllegalArgumentException("User already logged out");
        }
    }

    private void isEmailFromTokenAndUserDetailsEqual(String emailFromToken, String emailFromUserDetails) {
        if(!emailFromUserDetails.equals(emailFromToken)) {
            throw new IllegalArgumentException("Emails not match");
        }
    }

    private void validateAccessToken(String accessToken, CustomUserDetails userDetails) {
        if(!jwtTokenProvider.validateToken(accessToken, userDetails)) {
            throw new IllegalArgumentException("Invalid access token");
        }
    }

    private void processSecurity(HttpServletRequest request, CustomUserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
