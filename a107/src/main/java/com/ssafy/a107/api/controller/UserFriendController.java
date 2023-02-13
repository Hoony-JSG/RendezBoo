package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.FriendReq;
import com.ssafy.a107.api.service.UserFriendService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "친구 API", tags = {"Friend"})
@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class UserFriendController {

    private final UserFriendService friendService;

    /**
     * 친구 추가 api
     */
    @PostMapping("")
    @ApiOperation(value = "친구 추가", notes="여자와 남자 유저 번호를 받으면 친구를 맺어줍니다")
    public ResponseEntity<Long> addFriend(@RequestBody FriendReq req) throws NotFoundException {
        Long friendSeq = friendService.addFriend(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendSeq);
    }

    /**
     * 친구 삭제 api
     */
    @DeleteMapping("")
    @ApiOperation(value = "친구 삭제", notes="여자와 남자 유저 번호를 받으면 친구 관계를 끊어줍니다")
    public ResponseEntity deleteFriend(@RequestBody FriendReq req) throws NotFoundException{
        friendService.deleteFriend(req);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
