package com.ssafy.a107.api.service;

import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

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
            throw new ConflictException("User already exist");
        }
    }

    @Override
    public void checkPhoneNumberDuplicate(String phoneNumber) throws ConflictException {
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ConflictException("User already exist");
        }
    }

    @Override
    public List<UserRes> getFriends(Long userSeq) throws NotFoundException{
        if(userRepository.existsById(userSeq)) {
            return userRepository.findFriendsByUserSeq(userSeq).stream()
                    .map(UserRes::new)
                    .collect(Collectors.toList());
        }else throw new NotFoundException("Wrong User Seq!");
    }

    @Override
    public List<UserRes> getBlockeds(Long userSeq) throws NotFoundException{
        if(userRepository.existsById(userSeq)) {
            return userRepository.findBlockedByUserSeq(userSeq).stream()
                    .map(UserRes::new)
                    .collect(Collectors.toList());
        }else throw new NotFoundException("Wrong User Seq!");
    }

    @Transactional
    @Override
    public void deleteUser(String email) throws NotFoundException, ConflictException {
        checkLeavedUser(email);

        User toBeDeleted = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("Invalid user sequence!"));

        toBeDeleted.deleteUser();
        userRepository.save(toBeDeleted);
    }

    @Override
    public void checkLeavedUser(String email) throws NotFoundException, ConflictException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Wrong user seq!"));

        if(!user.getIsValid()) {
            throw new ConflictException("User does not exist. (Leaved)");
        }
    }
}
