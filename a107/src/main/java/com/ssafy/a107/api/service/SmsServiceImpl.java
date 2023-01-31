package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.SmsReq;
import com.ssafy.a107.api.response.SmsRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.Sms;
import com.ssafy.a107.db.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SmsServiceImpl implements SmsService {

    private final SmsRepository smsRepository;

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
}
