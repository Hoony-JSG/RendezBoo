package com.ssafy.a107.api.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadgeUpdateReq {
    private Long seq;
    private String name;
    private String description;
    private MultipartFile image;
}
