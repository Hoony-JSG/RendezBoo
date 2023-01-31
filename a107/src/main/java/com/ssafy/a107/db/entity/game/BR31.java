package com.ssafy.a107.db.entity.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;


@Document(collection = "br31")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BR31 {
    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private Long sessionId;

    private Long meetingRoomSeq;

    private Integer point = 0;

    private List<Long> users;

    private Long nowUser;
}
