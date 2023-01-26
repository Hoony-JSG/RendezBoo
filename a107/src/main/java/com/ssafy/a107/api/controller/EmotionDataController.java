package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.EmotionDataReq;
import com.ssafy.a107.api.response.EmotionDataRes;
import com.ssafy.a107.api.service.EmotionDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "감정 API", tags= {"EmotionData"})
@RestController
@RequestMapping("/api/emotion")
@RequiredArgsConstructor
public class EmotionDataController {
    private final EmotionDataService emotionDataService;
    @PostMapping("/")
    @ApiOperation(value="유저가 감정 저장", notes = "RequestBody로 매 1:1미팅에서의 유저가 유발한 감정을 반영한다")
    public ResponseEntity<?> save(@RequestBody EmotionDataReq req){
        Long result = emotionDataService.addExpressionData(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
    @GetMapping("/{userSeq}")
    @ApiOperation(value="유저 감정 조회", notes="유저가 유발한 평균적인 감정을 제공한다.")
    public ResponseEntity<?> getUserEmotion(@PathVariable Long userSeq){
        EmotionDataRes result = emotionDataService.getAvgExpressionDataByUserSeq(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
