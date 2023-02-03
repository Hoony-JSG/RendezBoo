package com.ssafy.a107.api.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.request.LoginReq;
import com.ssafy.a107.api.response.TokenRes;
import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.exception.TokenException;
import com.ssafy.a107.common.util.JwtTokenProvider;
import com.ssafy.a107.db.entity.Authority;
import com.ssafy.a107.db.entity.RefreshToken;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.RefreshTokenRedisRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${NAVER_CLIENT_SECRET}")
    private String NAVER_CLIENT_SECRET;

    @Value("${KAKAO_CLIENT_SECRET}")
    private String KAKAO_CLIENT_SECRET;

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

        log.debug("유저가 어드민? {}", user.getRoles());
        log.debug("유저 이메일: {}", user.getEmail());

        User savedUser = userRepository.save(user);
        log.debug("유저 seq: {}", user.getSeq());
        return savedUser.getSeq();
    }

    @Override
    public UserRes getUserBySeq(Long userSeq) throws NotFoundException {
        return new UserRes(userRepository.findById(userSeq)
                .orElseThrow(() -> new NotFoundException("Wrong User Seq!")));
    }

    @Override
    public UserRes getUserByEmail(String email) throws NotFoundException {
        return new UserRes(userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Wrong User Seq!")));
    }

    @Override
    public void checkEmailDuplicate(String email) throws ConflictException {
        if(userRepository.existsByEmail(email)) {
            throw new ConflictException("이미 등록된 유저입니다.");
        }
    }

    @Override
    public List<UserRes> getFriends(Long userSeq) throws NotFoundException{
        if(userRepository.existsById(userSeq)) {
            return userRepository.findFriendByUserSeq(userSeq).stream()
                    .map(UserRes::new)
                    .collect(Collectors.toList());
        }else throw new NotFoundException("Wrong User Seq!");
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

    @Override
    public List<UserRes> getBlockeds(Long userSeq) throws NotFoundException{
        if(userRepository.existsById(userSeq)) {
            return userRepository.findBlockedByUserSeq(userSeq).stream()
                    .map(UserRes::new)
                    .collect(Collectors.toList());
        }else throw new NotFoundException("Wrong User Seq!");
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> generateAuthCodeRequest(String code, String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "F3K8r9yyEG_RFk8RpLgi");
        params.add("client_secret", NAVER_CLIENT_SECRET);
        params.add("code", code);
        params.add("state", state);

        return new HttpEntity<>(params, headers);
    }

    @Override
    public String extractAccessToken(String tokenRes) throws TokenException {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(tokenRes);
        String accessToken = element.getAsJsonObject().get("access_token").getAsString();
        
        if(accessToken != null && !accessToken.equals("")) return accessToken;
        else throw new TokenException("토큰 에러 발생");
    }

    @Override
    public ResponseEntity<String> requestAccessToken(HttpEntity req) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                req,
                String.class
        );
    }

    @Override
    public ResponseEntity<String> requestProfile(HttpEntity req) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                req,
                String.class
        );
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(headers);
    }

    @Override
    public String getNaverEmail(String accessToken) {
        ResponseEntity<String> profile = requestProfile(generateProfileRequest(accessToken));

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(profile.getBody());
        JsonElement userInfo = element.getAsJsonObject().get("response");
        String email = userInfo.getAsJsonObject().get("email").toString().replaceAll("\"", "");

        log.debug("userInfo: {}", userInfo);
        log.debug("userEmail: {}", email);

        return email;
    }

    @Override
    public String getKakaoAccessToken(String code) throws TokenException {
        String accessToken = "";
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(tokenUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // POST

            // 쿼리 스트링
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=712c281c808f36c63bc8cea4a11ba42f");
            sb.append("&client_secret=" + KAKAO_CLIENT_SECRET);
            sb.append("&redirect_uri=http://localhost:8080/api/oauth/kakao");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            log.debug("Kakao OAuth responseCode: {}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder res = new StringBuilder();
            String line = "";

            while((line = br.readLine()) != null) {
                res.append(line);
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(res.toString());

            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();

            if(accessToken == null || accessToken.equals("")) {
                throw new TokenException("토큰 에러 발생");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    @Override
    public String getKakaoProfile(String token) throws NotFoundException {
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        StringBuilder res = new StringBuilder();
        String line = "";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token);

            if(conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while((line = br.readLine()) != null) {
                    res.append(line);
                }

                br.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if(res.length() == 0) throw new NotFoundException("카카오 이메일 불러오기 실패");

        return res.toString();
    }

    @Override
    public String getKakaoEmail(String accessToken) throws NotFoundException {
        String profile = getKakaoProfile(accessToken);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(profile);
        JsonElement kakaoAccount = element.getAsJsonObject().get("kakao_account");
        String email = kakaoAccount.getAsJsonObject().get("email").toString().replaceAll("\"", "");

        log.debug("userEmail: {}", email);

        return email;
    }

    @Override
    public void deleteUser(Long userSeq) throws NotFoundException{
        User toBeDeleted = userRepository.findById(userSeq)
                .orElseThrow(()->new NotFoundException("Invalid user sequence!"));
        toBeDeleted.deleteUser();
    }
}
