package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.BadgeCreateReq;
import com.ssafy.a107.api.request.BadgeUpdateReq;
import com.ssafy.a107.api.request.UserBadgeReq;
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
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getAllBadges());
    }

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저의 뱃지 리스트 조회", notes = "유저 시퀀스로 뱃지 리스트 제공")
    public ResponseEntity<?> getBadgeListByUserSeq(@PathVariable Long userSeq) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getBadgeByUserSeq(userSeq));
    }

    @PostMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저에게 뱃지를 생성함")
    public ResponseEntity<?> addBadgeToUser(@PathVariable Long userSeq, @RequestBody UserBadgeReq userBadgeReq) throws NotFoundException{
        badgeService.createUserBadge(userBadgeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping()
    @ApiOperation("새로운 뱃지 생성 - 관리자만 사용가능 해야함")
    public ResponseEntity<?> createBadge(@RequestPart MultipartFile multipartFile, @RequestParam String name) throws IOException{
        Long createdSeq = badgeService.createBadge(new BadgeCreateReq(name, multipartFile));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeq);
    }

    @PutMapping("/{badgeSeq}")
    @ApiOperation("뱃지 업데이트 - 관리자만 사용가능 해야함")
    public ResponseEntity<?> updateBadge(@PathVariable Long badgeSeq, @RequestPart MultipartFile multipartFile, @RequestParam String name) throws NotFoundException, IOException{
        Long updatedSeq = badgeService.updateBadge(new BadgeUpdateReq(badgeSeq, name, multipartFile));
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedSeq);
    }

    @DeleteMapping("/{badgeSeq}")
    @ApiOperation("뱃지 삭제 - 관리자만 사용가능 해야함")
    public ResponseEntity<?> deleteBadge(@PathVariable Long badgeSeq) {
        badgeService.deleteBadge(badgeSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}