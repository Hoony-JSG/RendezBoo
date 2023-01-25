package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
                .MBTI(joinReq.getMBTI())
                .point(0L)
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getSeq();
    }

    @Override
    public User getUserBySeq(Long userSeq) {
        return userRepository.findBySeq(userSeq);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
