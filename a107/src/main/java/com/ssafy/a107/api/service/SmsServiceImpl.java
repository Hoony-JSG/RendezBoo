package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.common.exception.BadRequestException;
import com.ssafy.a107.common.exception.ConflictException;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService {

    private final SmsRepository smsRepository;
    private final DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSYTD4MDN2J1VU4", "P3QHU2CCZAKFXA4SK2L6IQQSHXRFTS8F", "https://api.coolsms.co.kr");;

    @Override
    public void createSms(SmsReq smsReq, String code) {
        smsRepository.createSmsRedis(smsReq.getPhoneNumber(), code);
    }

    @Override
    public String getRecentCodeByPhoneNumber(String phoneNumber) {
        return smsRepository.getSmsRedis(phoneNumber);
    }

    @Override
    public void removeSms(String phoneNumber) throws NotFoundException {
        if(smsRepository.hasKey(phoneNumber)) {
            smsRepository.removeSmsRedis(phoneNumber);
        }
        else {
            throw new NotFoundException("해당 번호의 인증코드가 존재하지 않습니다.");
        }
    }

    @Override
    public void matchCodes(String sentCode, String typedCode) throws ConflictException {
        if(!sentCode.equals(typedCode)) {
            throw new ConflictException("인증번호가 일치하지 않습니다.");
        }
    }

    @Override
    public String makeRandomCode() {
        Random rand = new Random();
        StringBuilder code = new StringBuilder();

        for(int i = 0; i < 6; i++) {
            code.append(rand.nextInt(10));
        }

        return code.toString();
    }

    @Override
    public void sendSmsToUser(String phoneNumber, String code) throws Exception {
        Message message = new Message();
        message.setFrom("01099065910");
        message.setTo(phoneNumber);
        message.setText("[Rendezboo] 인증번호는 " + code + "입니다.");

        SingleMessageSentResponse res = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        log.debug("response: {}", res);

        if(!res.getStatusCode().equals("2000")) {
            throw new Exception("SMS 전송 실패");
        }
    }

    @Override
    public void checkSmsReq(SmsReq smsReq) throws BadRequestException {
        if(smsReq.getPhoneNumber() == null || smsReq.getCode() != null) {
            throw new BadRequestException("잘못된 요청입니다.");
        }
        // TODO: 정규표현식으로 변경
        else if(smsReq.getPhoneNumber().contains("-") || !smsReq.getPhoneNumber().startsWith("010")) {
            throw new BadRequestException("잘못된 번호 양식입니다.");
        }
    }

    @Override
    public void checkCode(String code, String from) throws Exception {
        if(code == null) {
            if(from.equals("user")) {
                throw new BadRequestException("잘못된 인증코드입니다.");
            }
            else if(from.equals("redis")) {
                throw new Exception("시간 초과 혹은 서버 에러입니다.");
            }
        }
    }
}
