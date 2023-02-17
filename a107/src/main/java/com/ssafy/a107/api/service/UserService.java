package com.ssafy.a107.api.service;

import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserRes getUserBySeq(Long userSeq) throws NotFoundException;

    UserRes getUserByEmail(String email) throws NotFoundException;

    void checkEmailDuplicate(String email) throws ConflictException;

    void checkPhoneNumberDuplicate(String phoneNumber) throws ConflictException;

    List<UserRes> getFriends(Long userSeq) throws NotFoundException;

    List<UserRes> getBlockeds(Long userSeq) throws NotFoundException;

    void deleteUser(String email) throws NotFoundException, ConflictException;

    void checkLeavedUser(String email) throws NotFoundException, ConflictException;

    void addPoint(Long userSeq, Long point) throws NotFoundException;

    void updateProfileImage(MultipartFile file, Long userSeq) throws IOException, NotFoundException;
}
