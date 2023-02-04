package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.JwtInvalidException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.JwtTokenProvider;
import com.ssafy.a107.db.entity.Authority;
import com.ssafy.a107.db.entity.RefreshToken;
import com.ssafy.a107.db.entity.User;
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

        log.debug("유저 Role: {}", user.getRoles());
        log.debug("유저 이메일: {}", user.getEmail());

        User savedUser = userRepository.save(user);
        log.debug("유저 seq: {}", user.getSeq());
        return savedUser.getSeq();
    }

    @Override
    public String resolveToken(String bearerToken) {
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    public TokenRes login(LoginReq loginReq) throws NotFoundException, ConflictException {
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new NotFoundException("회원이 없습니다."));

        checkPassword(loginReq.getPassword(), user.getPassword());

        String userEmail = user.getEmail();
        String accessToken = jwtTokenProvider.generateAccessToken(userEmail);
        RefreshToken refreshToken = saveRefreshToken(userEmail);

        return TokenRes.of(accessToken, refreshToken.getRefreshToken());
    }

    @Override
    public TokenRes reissue(String bearerToken) throws JwtInvalidException {
        String curRefreshToken = resolveToken(bearerToken);

        if(!StringUtils.hasText(curRefreshToken)) {
            throw new JwtInvalidException("잘못된 grant type");
        }

        Claims claims = jwtTokenProvider.parseClaimsFromRefreshToken(curRefreshToken);

        if(claims == null) {
            throw new JwtInvalidException("토큰에 claim이 존재하지 않음");
        }

        log.debug("claims: {}", claims);

        String userEmail = claims.get("email", String.class);
        log.debug("userEmail: {}", userEmail);

        String accessToken = jwtTokenProvider.generateAccessToken(userEmail);
        RefreshToken refreshToken = saveRefreshToken(userEmail);

        return TokenRes.of(accessToken, refreshToken.getRefreshToken());
    }

    @Override
    public void checkPassword(String rawPassword, String findUserPassword) throws ConflictException {
        if(!passwordEncoder.matches(rawPassword, findUserPassword)) {
            throw new ConflictException("비밀번호가 맞지 않습니다.");
        }
    }

    @Override
    public RefreshToken saveRefreshToken(String userEmail) {
        return refreshTokenRedisRepository.save(RefreshToken.createRefreshToken(userEmail,
                jwtTokenProvider.generateRefreshToken(userEmail), REFRESH_TOKEN_VALIDITY_MILLISECONDS));
    }
}
