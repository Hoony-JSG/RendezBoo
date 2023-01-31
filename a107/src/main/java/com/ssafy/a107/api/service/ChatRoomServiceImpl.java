package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ChatRoomCreateReq;
import com.ssafy.a107.api.response.ChatRoomRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.db.entity.ChatRoom;
import com.ssafy.a107.db.entity.User;
import com.ssafy.a107.db.repository.ChatRoomRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    /**
     * 남자일 경우 여자일 경우 나눠서 조회
     * @param userSeq
     * @return 해당 유저의 채팅방 리스트
     * @throws NotFoundException
     */
    @Override
    public List<ChatRoomRes> getAllRoomByUserSeq(Long userSeq) throws NotFoundException {

        if(userRepository.existsById(userSeq)) {
            User user = userRepository.getById(userSeq);
            Boolean gender = user.getGender();

            List<ChatRoom> chatRoomList;

            if (gender) {
                chatRoomList = chatRoomRepository.findByUserMaleSeq(userSeq);
            } else {
                chatRoomList = chatRoomRepository.findByUserFemaleSeq(userSeq);
            }

            return chatRoomList.stream()
                    .map(ChatRoomRes::new)
                    .collect(Collectors.toList());
            } else throw new NotFoundException("Wrong User Seq!");
    }

    @Override
    public ChatRoomRes getBySeq(Long seq) throws NotFoundException {
        return new ChatRoomRes(chatRoomRepository.findBySeq(seq)
                .orElseThrow(() -> new NotFoundException("Wrong Room Seq!")));

    }
    
//    수정해야됨 (방 있으면 기존 방 반환하도록)
    @Override
    public Long createChatRoom(ChatRoomCreateReq chatRoomCreateReq) throws HttpClientErrorException.BadRequest {
        ChatRoom chatRoom = ChatRoom.builder()
                .userMale(chatRoomCreateReq.getUserMale())
                .userFemale(chatRoomCreateReq.getUserFemale())
                .build();

        ChatRoom save = chatRoomRepository.save(chatRoom);

        return save.getSeq();
    }
}
