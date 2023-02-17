package com.ssafy.a107.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinRes {

    private String email;
    private Integer type; // 0 - 네이버, 1 - 카카오
}
