package com.ssafy.a107.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class UserFriend extends BaseEntity{

    //    남자 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_male_seq")
    private User userMale;

    //    여자 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_female_seq")
    private User userFemale;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;


}
