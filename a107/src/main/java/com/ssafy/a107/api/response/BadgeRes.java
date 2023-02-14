package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Badge;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class BadgeRes {

    private final Long seq;
    private final String name;
    private final String description;
    private final String url;

    public BadgeRes(Badge badge){
        this.seq = badge.getSeq();
        this.name = badge.getName();
        this.description = badge.getDescription();
        this.url = badge.getUrl();
    }
}
