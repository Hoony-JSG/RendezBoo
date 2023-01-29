package com.ssafy.a107.api.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserItemReq {

    private Long userSeq;
    private Long itemSeq;
}
