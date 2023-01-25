package com.ssafy.a107.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BadgeCreateReq {
    private String name;
    private String url;

    @Builder
    public BadgeCreateReq(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
