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
import org.springframework.transaction.annotation.Transactional;

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
    public List<ItemRes> getItemByUserSeq(Long userSeq) throws NotFoundException{
        if(!userRepository.existsById(userSeq)) throw new NotFoundException("Wrong user seq!");
        return itemRepository.findItemsByUserSeq(userSeq).stream()
                .map(ItemRes::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createUserItem(UserItemReq userItemReq) throws NotFoundException {
        UserItem userItem = UserItem.builder()
                .user(userRepository.findById(userItemReq.getUserSeq())
                        .orElseThrow(() -> new NotFoundException("Invalid user sequence!")))
                .item(itemRepository.findById(userItemReq.getItemSeq())
                        .orElseThrow(() -> new NotFoundException("Invalid item sequence!")))
                .build();

        userItemRepository.save(userItem);
    }

    @Override
    public List<ItemRes> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemRes::new)
                .collect(Collectors.toList());
    }

    @Transactional
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

    @Transactional
    @Override
    public Long updateItem(ItemUpdateReq itemUpdateReq) throws NotFoundException, IOException {
        String url = s3Uploader.upload(itemUpdateReq.getImage(), "images");
        Item item = itemRepository.findById(itemUpdateReq.getSeq())
                .orElseThrow(() -> new NotFoundException("Invalid item sequence!"));

        item.update(itemUpdateReq.getName(), url);
        itemRepository.save(item);
        return item.getSeq();
    }

    @Transactional
    @Override
    public void deleteItem(Long itemSeq) throws NotFoundException{
        if(!itemRepository.existsById(itemSeq)) throw new NotFoundException("Invalid Item sequence!");
        itemRepository.deleteById(itemSeq);
    }
}
