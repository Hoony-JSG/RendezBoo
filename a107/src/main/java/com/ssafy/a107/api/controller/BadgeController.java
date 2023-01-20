package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.service.BadgeService;
import com.ssafy.a107.db.entity.Badge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "뱃지 API", tags = {"Badge"})
@RestController
@RequestMapping("/api/badge")
public class BadgeController {
    @Autowired
    BadgeService badgeService;

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "뱃지 리스트 조회", notes="유저 시퀀스로 뱃지 리스트 제공")
    public ResponseEntity<?> getBadgeListByUserSeq(@PathVariable Long userSeq){
        try{
            List<Badge> badgeList = badgeService.getBadgeByUserSeq(userSeq);
            if(badgeList!=null && !badgeList.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(badgeList);
            }else{
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(e);
        }
    }
}