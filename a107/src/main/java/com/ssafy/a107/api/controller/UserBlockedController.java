package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.UserBlockedService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "차단 API", tags = {"BlockedUser"})
@RestController
@RequestMapping("/api/BlockedUsers")
@RequiredArgsConstructor
public class UserBlockedController {

    private final UserBlockedService blockedUserService;

    @PostMapping("/block/{userSeq}/{targetUserSeq}")
    @ApiOperation(value = "유저 차단", notes="나(userSeq)의 차단 목록에 상대방(targetUserSeq)을 추가")
    public ResponseEntity<?> createBlockedUser(
            @PathVariable Long userSeq, @PathVariable Long targetUserSeq
    ) throws NotFoundException {
        Long blocked = blockedUserService.addBlockedUser(userSeq, targetUserSeq);
        return ResponseEntity.status(HttpStatus.CREATED).body(blocked);
    }
}
