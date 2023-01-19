package com.ssafy.a107.db.entity;

import java.sql.Timestamp;

public class OneToOneMeetingRoom {
    public int meetingRoomSeq;
    public int status;
    public int manSeq;
    public int womanSeq;
    public Timestamp createdAt;
    public Timestamp updatedAt;
}
