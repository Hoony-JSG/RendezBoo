package com.ssafy.a107.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
public class ChatRoom extends BaseEntity{

//    남자 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_male_id")
    private User userMale;

//    여자 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_female_id")
    private User userFemale;

    @CreatedDate
    private LocalDateTime createdAt;
}
