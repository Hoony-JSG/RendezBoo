package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.JoinReq;
import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public UserRes getUserBySeq(Long userSeq) throws NotFoundException {
        Optional<User> result = userRepository.findById(userSeq);

        if(result.isPresent()) return buildUserToUserRes(result.get());
        throw new NotFoundException("Wrong User Seq!");
    }

    @Override
    public UserRes getUserByEmail(String email) throws NotFoundException {
        Optional<User> result = userRepository.findByEmail(email);

        if(result.isPresent()) return buildUserToUserRes(result.get());
        throw new NotFoundException("Wrong User Seq!");
    }

    private UserRes buildUserToUserRes(User user) {
        UserRes res = UserRes.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .city(user.getCity())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .name(user.getName())
                .profileImagePath(user.getProfileImagePath())
                .MBTI(user.getMBTI())
                .point(user.getPoint())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .badge(user.getBadge())
                .userInterestRelations(user.getUserInterestRelations())
                .build();

        return res;
    }
}
