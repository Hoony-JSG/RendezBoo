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
@RequestMapping("/api/badges")
public class BadgeController {
    BadgeService badgeService;

    @Autowired
    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping()
    @ApiOperation(value = "전체 뱃지 리스트 조회")
    public ResponseEntity<?> getAllBadgeList() {
        List<Badge> badges = badgeService.getAllBadges();
        if (badges== null || badges.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getAllBadges());
    }

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저의 뱃지 리스트 조회", notes="유저 시퀀스로 뱃지 리스트 제공")
    public ResponseEntity<?> getBadgeListByUserSeq(@PathVariable Long userSeq){
        List<Badge> badgeList = badgeService.getBadgeByUserSeq(userSeq);
        if(badgeList!=null && !badgeList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(badgeList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}