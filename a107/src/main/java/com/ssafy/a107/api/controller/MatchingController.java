package com.ssafy.a107.api.controller;

import io.openvidu.java.client.OpenVidu;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Api(value = "매칭 컨트롤러")
@RestController
@RequestMapping( "/api/matching")
public class MatchingController {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    /**
     * OpenVidu 서버를 사용하기 위하여 설정을 읽어서 컨트롤러 생성
     */
    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    /**
     * ----- 제작 예정 -----
     * 일대일 매칭 대기열에 올라감
     * @return
     */
    @PostMapping("/one")
    public ResponseEntity<?> joinOneToOneMatchingSession() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * ----- 제작 예정 -----
     * 일대일 매칭방에 입장함
     * @return
     */
    @PostMapping("/one/{sessionId}/connections")
    public ResponseEntity<?> createConnectionOneToOneMatching(@PathVariable("sessionId") String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * ----- 제작 예정 -----
     * 삼대삼 매칭 대기열에 올라감
     * @return
     */
    @PostMapping("/three")
    public ResponseEntity<?> joinNToNMatching() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * ----- 제작 예정 -----
     * 삼대삼 매칭방에 입장함
     * @return
     */
    @PostMapping("/three/{sessionId}/connections")
    public ResponseEntity<?> createConnectionNToNMatching(@PathVariable("sessionId") String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
