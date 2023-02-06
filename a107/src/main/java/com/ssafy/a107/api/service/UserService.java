package com.ssafy.a107.api.service;

import com.ssafy.a107.api.response.UserRes;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;

import java.util.List;

public interface UserService {

    UserRes getUserBySeq(Long userSeq) throws NotFoundException;
    UserRes getUserByEmail(String email) throws NotFoundException;
    void checkEmailDuplicate(String email) throws ConflictException;
    void checkPhoneNumberDuplicate(String phoneNumber) throws ConflictException;
    List<UserRes> getFriends(Long userSeq) throws NotFoundException;
    List<UserRes> getBlockeds(Long userSeq) throws NotFoundException;
    void deleteUser(Long userSeq) throws NotFoundException;
}
