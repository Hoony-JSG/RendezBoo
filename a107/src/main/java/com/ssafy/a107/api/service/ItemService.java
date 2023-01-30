package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ItemCreateReq;
import com.ssafy.a107.api.request.ItemUpdateReq;
import com.ssafy.a107.api.request.UserItemReq;
import com.ssafy.a107.api.response.ItemRes;
import com.ssafy.a107.common.exception.NotFoundException;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    List<ItemRes> getItemByUserSeq(Long userSeq) throws NotFoundException;

    void createUserItem(UserItemReq userItemReq) throws NotFoundException;

    List<ItemRes> getAllItems();

    Long createItem(ItemCreateReq itemCreateReq) throws IOException;

    Long updateItem(ItemUpdateReq itemUpdateReq) throws NotFoundException, IOException;

    void deleteItem(Long itemSeq) throws NotFoundException;
}
