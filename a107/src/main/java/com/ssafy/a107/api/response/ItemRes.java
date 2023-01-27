package com.ssafy.a107.api.response;

import com.ssafy.a107.db.entity.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemRes {

    private final Long seq;
    private final Byte type;
    private final String name;
    private final String url;

    public ItemRes(Item item) {
        this.seq = item.getSeq();
        this.type = item.getType();
        this.name = item.getName();
        this.url = item.getUrl();
    }
}
