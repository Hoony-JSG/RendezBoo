package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.*;
import com.ssafy.a107.api.response.BadgeCheckRes;
import com.ssafy.a107.api.response.BadgeRes;
import com.ssafy.a107.api.service.BadgeService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(value = "뱃지 API", tags = {"Badge"})
@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping()
    @ApiOperation(value = "전체 뱃지 리스트 조회")
    public ResponseEntity<List<BadgeRes>> getAllBadgeList() {
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getAllBadges());
    }

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저의 뱃지 리스트 조회", notes = "유저 시퀀스로 뱃지 리스트 제공")
    public ResponseEntity<List<BadgeRes>> getBadgeListByUserSeq(@PathVariable Long userSeq) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(badgeService.getBadgeByUserSeq(userSeq));
    }

    @PostMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저에게 뱃지를 생성함")
    public ResponseEntity<String> addBadgeToUser(@PathVariable Long userSeq, @RequestBody UserBadgeReq userBadgeReq) throws NotFoundException{
        badgeService.createUserBadge(userBadgeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body("Badge " + userBadgeReq.getBadgeSeq() + "has been added to User " + userSeq);
    }

    @PostMapping()
    @ApiOperation("새로운 뱃지 생성 - 관리자만 사용가능 해야함")
    public ResponseEntity<Long> createBadge(@RequestPart MultipartFile multipartFile, @RequestParam String name) throws IOException{
        Long createdSeq = badgeService.createBadge(new BadgeCreateReq(name, multipartFile));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeq);
    }

    @PutMapping("/{badgeSeq}")
    @ApiOperation("뱃지 업데이트 - 관리자만 사용가능 해야함")
    public ResponseEntity<Long> updateBadge(@PathVariable Long badgeSeq, @RequestPart MultipartFile multipartFile, @RequestParam String name) throws NotFoundException, IOException{
        Long updatedSeq = badgeService.updateBadge(new BadgeUpdateReq(badgeSeq, name, multipartFile));
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedSeq);
    }

    @DeleteMapping("/{badgeSeq}")
    @ApiOperation("뱃지 삭제 - 관리자만 사용가능 해야함")
    public ResponseEntity<Long> deleteBadge(@PathVariable Long badgeSeq) throws NotFoundException {
        badgeService.deleteBadge(badgeSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(badgeSeq);
    }

    @PostMapping("/onetoone")
    @ApiOperation("유저가 뱃지 획득 조건을 만족하는지 확인 - 일대일 미팅 종료시")
    public ResponseEntity<BadgeCheckRes> checkBadgeOneToOne(@RequestBody EmotionDataReq emotionDataReq) throws NotFoundException {
        BadgeCheckRes badgeCheckRes = badgeService.checkBadgeOneToOne(emotionDataReq);
        return ResponseEntity.status(HttpStatus.OK).body(badgeCheckRes);
    }

    @PostMapping("/manytomany/{userSeq}")
    @ApiOperation("유저가 뱃지 획득 조건을 만족하는지 확인 - 다대다 미팅 종료시")
    public ResponseEntity<BadgeCheckRes> checkBadgeManyToMany(@PathVariable Long userSeq) throws NotFoundException {
        BadgeCheckRes badgeCheckRes = badgeService.checkBadgeManyToMany(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(badgeCheckRes);
    }

    @PostMapping("/item/{userSeq}")
    @ApiOperation("유저가 뱃지 획득 조건을 만족하는지 확인 - 아이템 구매시")
    public ResponseEntity<BadgeCheckRes> checkBadgeItem(@PathVariable Long userSeq) throws NotFoundException {
        BadgeCheckRes badgeCheckRes = badgeService.checkBadgeItem(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(badgeCheckRes);
    }

//    @PostMapping("/check/{userSeq}")
//    @ApiOperation("유저가 뱃지 획득 조건을 만족하는지 확인")
//    public ResponseEntity<?> checkBadge(@PathVariable Long userSeq, @RequestBody BadgeCheckReq badgeCheckReq) throws NotFoundException {
//        BadgeCheckRes badgeCheckRes = badgeService.checkBadge(userSeq, badgeCheckReq);
//        return ResponseEntity.status(HttpStatus.OK).body(badgeCheckRes);
//    }
}