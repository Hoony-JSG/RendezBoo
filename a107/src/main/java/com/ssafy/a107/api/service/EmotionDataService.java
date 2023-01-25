package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.EmotionDataReq;
import com.ssafy.a107.api.response.EmotionDataRes;

public interface EmotionDataService {
    Long addExpressionData(EmotionDataReq req);
    EmotionDataRes getAvgExpressionDataByUserSeq(Long userSeq);

}
