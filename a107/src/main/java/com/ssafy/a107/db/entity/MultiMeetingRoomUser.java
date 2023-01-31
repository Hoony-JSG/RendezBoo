package com.ssafy.a107.db.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Entity
public class MultiMeetingRoomUser extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "multi_meeting_room_seq")
    private MultiMeetingRoom multiMeetingRoom;

    @Builder
    public MultiMeetingRoomUser(User user, MultiMeetingRoom multiMeetingRoom){
        this.user = user;
        this.multiMeetingRoom = multiMeetingRoom;
    }
}
