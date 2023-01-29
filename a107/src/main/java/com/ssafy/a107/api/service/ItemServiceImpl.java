package com.ssafy.a107.api.service;

import com.ssafy.a107.api.request.ItemCreateReq;
import com.ssafy.a107.api.request.ItemUpdateReq;
import com.ssafy.a107.api.request.UserItemReq;
import com.ssafy.a107.api.response.ItemRes;
import com.ssafy.a107.common.exception.NotFoundException;
import com.ssafy.a107.common.util.S3Uploader;
import com.ssafy.a107.db.entity.Item;
import com.ssafy.a107.db.entity.UserItem;
import com.ssafy.a107.db.repository.ItemRepository;
import com.ssafy.a107.db.repository.UserItemRepository;
import com.ssafy.a107.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;
    private final S3Uploader s3Uploader;

    @Override
    public List<ItemRes> getItemByUserSeq(Long userSeq) {
        List<Item> itemList = itemRepository.findItemsByUserSeq(userSeq);
        List<ItemRes> itemResList = itemList.stream()
                .map(item -> new ItemRes(item))
                .collect(Collectors.toList());

        return itemResList;
    }

    @Override
    public void createUserItem(UserItemReq userItemReq) throws NotFoundException {
        UserItem userItem = UserItem.builder()
                .user(userRepository.findById(userItemReq.getUserSeq())
                        .orElseThrow(() -> new NotFoundException("Wrong User Seq!")))
                .item(itemRepository.findById(userItemReq.getItemSeq())
                        .orElseThrow(() -> new NotFoundException("Wrong Item Seq!")))
                .build();

        userItemRepository.save(userItem);
    }

    @Override
    public List<ItemRes> getAllItems() {
        List<Item> itemList = itemRepository.findAll();
        List<ItemRes> itemResList = itemList.stream()
                .map(item -> new ItemRes(item))
                .collect(Collectors.toList());

        return itemResList;
    }

    @Override
    public Long createItem(ItemCreateReq itemCreateReq) throws IOException {
        String url = s3Uploader.upload(itemCreateReq.getImage(), "images");
        Item item = Item.builder()
                .type(itemCreateReq.getType())
                .name(itemCreateReq.getName())
                .url(url)
                .build();

        itemRepository.save(item);
        return item.getSeq();
    }

    @Override
    public Long updateItem(ItemUpdateReq itemUpdateReq) throws NotFoundException, IOException {
        String url = s3Uploader.upload(itemUpdateReq.getImage(), "images");
        Item item = itemRepository.findById(itemUpdateReq.getSeq())
                .orElseThrow(() -> new NotFoundException("Wrong Item Seq!"));

        item.update(itemUpdateReq.getName(), url);
        itemRepository.save(item);
        return item.getSeq();
    }

    @Override
    public void deleteItem(Long itemSeq) {
        itemRepository.deleteById(itemSeq);
    }
}
