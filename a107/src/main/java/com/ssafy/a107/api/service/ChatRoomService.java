package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ChatRoomCreateReq;
import com.ssafy.a107.api.response.ChatRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomRes> getAllRoomByUserSeq(Long userSeq) throws NotFoundException;

//    List<ChatRoom> getRoomByFemaleSeq(Long userFemaleSeq);
//    List<ChatRoom> getRoomByMaleSeq(Long userMaleSeq);
    ChatRoomRes getBySeq(Long Seq) throws NotFoundException;
    Long createChatRoom(ChatRoomCreateReq chatRoomCreateReq) throws HttpClientErrorException.BadRequest;
}
