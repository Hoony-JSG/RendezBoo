package com.ssafy.a107.api.controller;

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
     * @param userSeq: 추가하는 유저(나)의 userSeq
     * @param otherUserSeq: 친구로 추가할 유저(상대방)의 userSeq
     * @return http status 반환
     */
    @PostMapping("/{userSeq}/{otherUserSeq}")
    @ApiOperation(value = "친구 추가", notes="나(userSeq)와 상대방(otherUserSeq)가 친구를 맺는다.")
    public ResponseEntity<?> addFriend(
            @PathVariable Long userSeq, @PathVariable Long otherUserSeq) throws NotFoundException {
        Long friendSeq = friendService.addFriend(userSeq, otherUserSeq);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendSeq);
    }

    /**
     * 친구 삭제 api
     * @param userSeq: 유저(나)의 userSeq
     * @param otherUserSeq: 친구에서 삭제할 유저(상대방)의 userSeq
     * @return http status 반환
     */
    @DeleteMapping("/{userSeq}/{otherUserSeq}")
    @ApiOperation(value = "친구 삭제", notes="나(userSeq)와 상대방(otherUserSeq)의 친구 관계 삭제.")
    public ResponseEntity<?> deleteFriend(@PathVariable Long userSeq, @PathVariable Long otherUserSeq) throws NotFoundException{
        friendService.deleteFriend(userSeq, otherUserSeq);
        return ResponseEntity.noContent().build();
    }
}
