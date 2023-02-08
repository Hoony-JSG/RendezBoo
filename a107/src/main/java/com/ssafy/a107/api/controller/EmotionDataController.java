package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.EmotionDataReq;
import com.ssafy.a107.api.response.EmotionDataRes;
import com.ssafy.a107.api.service.EmotionDataService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "감정 API", tags= {"EmotionData"})
@RestController
@RequestMapping("/api/emotion")
@RequiredArgsConstructor
public class EmotionDataController {
    private final EmotionDataService emotionDataService;
    @PostMapping("/")
    @ApiOperation(value="유저가 유발한 감정 저장", notes = "RequestBody로 매 1:1미팅에서의 유저가 유발한 감정을 반영한다")
    public ResponseEntity<Long> save(@RequestBody EmotionDataReq req){
        try{
            Long result = emotionDataService.addExpressionData(req);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch(NotFoundException e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
    @GetMapping("/{userSeq}")
    @ApiOperation(value="유저 감정 조회", notes="유저가 유발한 평균적인 감정을 제공한다.")
    public ResponseEntity<EmotionDataRes> getUserEmotion(@PathVariable Long userSeq){
        try{
                EmotionDataRes result = emotionDataService.getAvgExpressionDataByUserSeq(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch(NotFoundException e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
