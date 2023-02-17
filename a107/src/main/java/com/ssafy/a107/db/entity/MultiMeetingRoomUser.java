package com.ssafy.a107.db.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
public class MultiMeetingRoomUser extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multi_meeting_room_seq")
    private MultiMeetingRoom multiMeetingRoom;

    //status 0: 대기중, 1: 미팅 진행중
    @Column(nullable = false)
    private Byte status;

    @Builder
    public MultiMeetingRoomUser(User user, MultiMeetingRoom multiMeetingRoom){
        this.user = user;
        this.multiMeetingRoom = multiMeetingRoom;
        this.status = 0;
    }
    public void start(){
        status = 1;
    }
}
