package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.EmotionDataReq;
import com.ssafy.a107.api.response.EmotionDataRes;
import com.ssafy.a107.common.exception.NotFoundException;

public interface EmotionDataService {
    Long addExpressionData(EmotionDataReq req) throws NotFoundException;
    EmotionDataRes getAvgExpressionDataByUserSeq(Long userSeq);

}
