package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.JwtInvalidException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.JwtTokenProvider;
import com.ssafy.a107.db.entity.Authority;
import com.ssafy.a107.db.entity.LogoutAccessToken;
import com.ssafy.a107.db.entity.RefreshToken;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.LogoutAccessTokenRedisRepository;
import com.ssafy.a107.db.repository.RefreshTokenRedisRepository;
import com.ssafy.a107.db.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.refresh-token-validity-milliseconds}")
    private Long REFRESH_TOKEN_VALIDITY_MILLISECONDS;

    @Transactional
    @Override
    public Long createUser(JoinReq joinReq) {
        User user = User.builder()
                .email(joinReq.getEmail())
                .password(passwordEncoder.encode(joinReq.getPassword()))
                .city(joinReq.getCity())
                .gender(joinReq.getGender())
                .phoneNumber(joinReq.getPhoneNumber())
                .name(joinReq.getName())
                .profileImagePath(joinReq.getProfileImagePath())
                .mbti(joinReq.getMbti())
                .point(0L)
                .isAdmin(joinReq.getIsAdmin())
                .isValid(true)
                .build();

        // 어드민이면
        if(user.getIsAdmin()) {
            user.addAuthority(Authority.ofAdmin(user));
        }
        else {
            user.addAuthority(Authority.ofUser(user));
        }

        User savedUser = userRepository.save(user);

        log.debug("회원가입 이메일: {}", user.getEmail());
        log.debug("유저 Role: {}", user.getRoles());
        log.debug("유저 seq: {}", user.getSeq());

        return savedUser.getSeq();
    }

    @Transactional
    @Override
    public TokenRes login(LoginReq loginReq) throws NotFoundException, ConflictException {
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new NotFoundException("Wrong email!"));

        checkPassword(loginReq.getPassword(), user.getPassword());

        String userEmail = user.getEmail();
        String accessToken = jwtTokenProvider.generateAccessToken(userEmail);
        RefreshToken refreshToken = saveRefreshToken(userEmail);

        log.debug("로그인 이메일: {}", userEmail);
        log.debug("발급된 AccessToken: {}", accessToken);
        log.debug("발급된 RefreshToken: {}", refreshToken.getRefreshToken());

        return TokenRes.of(accessToken, refreshToken.getRefreshToken());
    }

    @Transactional
    @Override
    public TokenRes reissue(String bearerToken) throws JwtInvalidException {
        String curRefreshToken = resolveToken(bearerToken);

        if(!StringUtils.hasText(curRefreshToken)) {
            throw new JwtInvalidException("Wrong grant type");
        }

        String userEmail = getEmailFromToken(curRefreshToken);

        String refreshTokenFromRedis = refreshTokenRedisRepository.findById(userEmail)
                .orElseThrow(() -> new JwtInvalidException("Invalid refresh Token!"))
                .getRefreshToken();

        if(!curRefreshToken.equals(refreshTokenFromRedis)) {
            throw new JwtInvalidException("Refresh Token not match");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(userEmail);
        RefreshToken refreshToken = saveRefreshToken(userEmail);

        log.debug("재발급 이메일: {}", userEmail);
        log.debug("발급된 AccessToken: {}", accessToken);
        log.debug("발급된 RefreshToken: {}", refreshToken.getRefreshToken());

        return TokenRes.of(accessToken, refreshToken.getRefreshToken());
    }

    @Transactional
    @Override
    public String logout(String bearerToken) throws JwtInvalidException, BadRequestException {
        String accessToken = resolveToken(bearerToken);

        if(!validateToken(accessToken)) {
            throw new BadRequestException("Invalid access token!");
        }

        String userEmail = getEmailFromToken(accessToken);

        if(refreshTokenRedisRepository.existsById(userEmail)) {
            refreshTokenRedisRepository.deleteById(userEmail);
        }

        long expiration = jwtTokenProvider.getExpiration(accessToken);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(accessToken,
                userEmail, expiration));

        log.debug("로그아웃 이메일: {}", userEmail);
        log.debug("AccessToken expiration: {}ms", expiration);

        return userEmail;
    }

    @Override
    public String resolveToken(String bearerToken) {
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    public void checkPassword(String rawPassword, String findUserPassword) throws ConflictException {
        if(!passwordEncoder.matches(rawPassword, findUserPassword)) {
            throw new ConflictException("Wrong password!");
        }
    }

    @Override
    public RefreshToken saveRefreshToken(String userEmail) {
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(userEmail,
                jwtTokenProvider.generateRefreshToken(userEmail), REFRESH_TOKEN_VALIDITY_MILLISECONDS));
    }

    @Override
    public boolean validateToken(String accessToken) throws JwtInvalidException {
        String emailFromToken = getEmailFromToken(accessToken);

        return emailFromToken != null && !jwtTokenProvider.isTokenExpired(accessToken);
    }

    @Override
    public String getEmailFromToken(String token) throws JwtInvalidException {
        Claims claims = jwtTokenProvider.parseClaimsFromToken(token);

        if(claims == null) {
            throw new JwtInvalidException("Token not exist in claim");
        }

        return claims.get("email", String.class);
    }
}
