package com.ssafy.a107.db.entity;

import java.sql.Timestamp;

public class ManyToManyMeetingRoom {
    public int meetingRoomSeq;
    public Byte status;
    public int woman1Seq;
    public int man1Seq;
    public int woman2Seq;
    public int man2Seq;
    public int woman3Seq;
    public int man3Seq;
    public Timestamp createdAt;
    public Timestamp updatedAt;
}
