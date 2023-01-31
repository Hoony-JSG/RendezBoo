package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.api.response.SmsRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Sms;
import com.ssafy.a107.db.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SmsServiceImpl implements SmsService {

    private final SmsRepository smsRepository;
    private final DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSYTD4MDN2J1VU4", "P3QHU2CCZAKFXA4SK2L6IQQSHXRFTS8F", "https://api.coolsms.co.kr");;

    @Transactional
    @Override
    public Long createSms(SmsReq smsReq, String code) {
        Sms sms = Sms.builder()
                .email(smsReq.getEmail())
                .phoneNumber(smsReq.getPhoneNumber())
                .code(code)
                .build();

        Sms savedSms = smsRepository.save(sms);
        return savedSms.getSeq();
    }

    @Override
    public SmsRes getRecentCodeByEmail(String email) throws NotFoundException {
        return new SmsRes(smsRepository.findTopByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new NotFoundException("No Code Found!")));
    }

    @Override
    public Boolean matchCodes(String sentCode, String typedCode) {
        if(sentCode == null || typedCode == null) return false;
        return sentCode.equals(typedCode);
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
    public String sendSmsToUser(String phoneNumber, String code) {
        Message message = new Message();
        message.setFrom("01099065910");
        message.setTo(phoneNumber);
        message.setText("[RendezBoo] 인증번호는 " + code + "입니다.");

        SingleMessageSentResponse res = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        log.debug("response: {}", res);

        return res.getStatusCode();
    }
}
