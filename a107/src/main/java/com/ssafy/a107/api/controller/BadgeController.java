package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
import com.ssafy.a107.api.response.BadgeRes;
import com.ssafy.a107.api.service.BadgeService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<BadgeRes> badges = badgeService.getAllBadges();
        if (badges == null || badges.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getAllBadges());
    }

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저의 뱃지 리스트 조회", notes = "유저 시퀀스로 뱃지 리스트 제공")
    public ResponseEntity<?> getBadgeListByUserSeq(@PathVariable Long userSeq) {
        List<BadgeRes> badgeList = badgeService.getBadgeByUserSeq(userSeq);
        if (badgeList != null && !badgeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(badgeList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @PostMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저에게 뱃지를 생성함")
    public ResponseEntity<?> addBadgeToUser(@PathVariable Long userSeq, @RequestBody UserBadgeReq userBadgeReq) {
        try {
            badgeService.createUserBadge(userBadgeReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping()
    @ApiOperation("새로운 뱃지 생성 - 관리자만 사용가능 해야함")
    public ResponseEntity<?> createBadge(@RequestPart MultipartFile multipartFile, @RequestParam String name) {
        BadgeCreateReq badgeCreateReq = new BadgeCreateReq(name, multipartFile);
        try {
            badgeService.createBadge(badgeCreateReq);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ msg: 'Image Upload Fail'}");
        }

    }

    @PutMapping("/{badgeSeq}")
    @ApiOperation("뱃지 업데이트 - 관리자만 사용가능 해야함")
    public ResponseEntity<?> updateBadge(@PathVariable Long badgeSeq, @RequestPart MultipartFile multipartFile, @RequestParam String name) {
        BadgeUpdateReq badgeUpdateReq = new BadgeUpdateReq(badgeSeq, name, multipartFile);
        try {
            badgeService.updateBadge(badgeUpdateReq);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{ msg: 'Image Upload Fail'}");
        }
    }

    @DeleteMapping("/{badgeSeq}")
    @ApiOperation("뱃지 삭제 - 관리자만 사용가능 해야함")
    public ResponseEntity<?> deleteBadge(@PathVariable Long badgeSeq) {
        badgeService.deleteBadge(badgeSeq);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}