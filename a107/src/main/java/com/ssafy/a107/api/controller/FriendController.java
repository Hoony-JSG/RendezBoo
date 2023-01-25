package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.FriendServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "친구 API", tags = {"Friend"})
@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendServiceImpl friendService;

    /**
     * 친구 추가 api
     * @param userSeq: 추가하는 유저(나)의 userSeq
     * @param otherUserSeq: 친구로 추가할 유저(상대방)의 userSeq
     * @return http status 반환
     */
    @PostMapping("/add-friend/{userSeq}/{otherUserSeq}")
    @ApiOperation(value = "친구 추가", notes="나(userSeq)와 상대방(otherUserSeq)가 친구를 맺는다.")
    public ResponseEntity<?> addFriend(
            @PathVariable Long userSeq, @PathVariable Long otherUserSeq
    ) {
        try {
            friendService.addFriend(userSeq, otherUserSeq);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
