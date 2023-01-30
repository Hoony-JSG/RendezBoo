package com.ssafy.a107.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRes {

    private Boolean gender;
    private String email;
    private String phoneNumber;
    private String name;
}
