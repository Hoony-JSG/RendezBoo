package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ExpressionDataReq;
import com.ssafy.a107.api.response.EmotionDataRes;

public interface EmotionDataService {
    Long addExpressionData(ExpressionDataReq req);
    EmotionDataRes getAvgExpressionDataByUserSeq(Long userSeq);

}
