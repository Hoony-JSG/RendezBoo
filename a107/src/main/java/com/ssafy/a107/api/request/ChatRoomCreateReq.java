package com.ssafy.a107.api.request;

import com.ssafy.a107.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomCreateReq {

    private User userMale;
    private User userFemale;

}
