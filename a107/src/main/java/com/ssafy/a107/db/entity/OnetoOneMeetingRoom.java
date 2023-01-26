package com.ssafy.a107.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
public class OnetoOneMeetingRoom extends BaseEntity{

    @Column(nullable = false)
    private Byte status = 0;

    private Long manSeq;
    private Long womanSeq;

    @CreatedDate
    @JsonProperty("created_at")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public void addMan(Long manSeq) {
        this.manSeq = manSeq;
    }
    public void addWoman(Long womanSeq){
        this.womanSeq = womanSeq;
    }
}
