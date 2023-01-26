package com.ssafy.a107.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity{

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String city;

    /**
     * 남자: 1 여자: 0
     */
    @Column(nullable = false)
    private Boolean gender;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 100)
    private String profileImagePath;

    @Column(nullable = false, length = 4)
    private String MBTI;

    @Column
    private Long point;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;

    @OneToOne(fetch =FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "badge_seq")
    private Badge badge;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserInterestRelation> userInterestRelations = new ArrayList<>();

}
