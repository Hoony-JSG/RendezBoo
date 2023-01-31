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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

//    남자 여자 나눠서 조회
    @Override
    public List<ChatRoomRes> getAllRoomByUserSeq(Long userSeq) throws NotFoundException {

        if(userRepository.existsById(userSeq)) {
            User user = userRepository.findById(userSeq).get();
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
    
//    기존에 방 있으면 기존 방 번호 반환 없으면 새로운 방 생성 후 해당 방 번호 반환
    @Override
    public Long createChatRoom(ChatRoomCreateReq chatRoomCreateReq) throws NotFoundException {

        Long userMaleSeq = chatRoomCreateReq.getUserMaleSeq();
        Long userFemaleSeq = chatRoomCreateReq.getUserFemaleSeq();

        if (userRepository.existsById(userMaleSeq) && userRepository.existsById(userFemaleSeq)) {

            if (!chatRoomRepository.existsByUserMaleSeqAndUserFemaleSeq(userMaleSeq, userFemaleSeq)) {
                ChatRoom chatRoom = ChatRoom.builder()
                        .userMale(userRepository.findById(userMaleSeq).get())
                        .userFemale(userRepository.findById(userFemaleSeq).get())
                        .build();

                ChatRoom save = chatRoomRepository.save(chatRoom);
                return save.getSeq();
            }
            else
            {
                ChatRoom byUserMaleSeqAndUserFemaleSeq = chatRoomRepository.findByUserMaleSeqAndUserFemaleSeq(userMaleSeq, userFemaleSeq);
                return byUserMaleSeqAndUserFemaleSeq.getSeq();
            }
        } else throw new NotFoundException("Wrong User Seq!");



    }
}
