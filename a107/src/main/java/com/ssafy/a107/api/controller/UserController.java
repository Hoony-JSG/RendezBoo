package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.UserService;
import com.ssafy.a107.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    // 유저 정보 조회
    @GetMapping("/{userSeq}")
    @ApiOperation(value = "유저 정보 조회", notes = "Seq로 유저 정보 제공")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userSeq) {
        try {
            User user = userService.getUserBySeq(userSeq);

            if(user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
