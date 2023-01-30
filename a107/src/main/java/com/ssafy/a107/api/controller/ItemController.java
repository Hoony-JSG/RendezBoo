package com.ssafy.a107.api.controller;

import com.ssafy.a107.api.request.ItemCreateReq;
import com.ssafy.a107.api.request.ItemUpdateReq;
import com.ssafy.a107.api.request.UserItemReq;
import com.ssafy.a107.api.response.ItemRes;
import com.ssafy.a107.api.service.ItemService;
import com.ssafy.a107.common.exception.NotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(value = "아이템 API", tags = {"Item"})
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @ApiOperation(value = "전체 아이템 리스트 조회")
    public ResponseEntity<?> getAllItemList() {
        List<ItemRes> items = itemService.getAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/{userSeq}")
    @ApiOperation(value = "특정 유저의 아이템 리스트 조회", notes = "유저 시퀀스로 아이템 리스트 제공")
    public ResponseEntity<?> getItemListByUserSeq(@PathVariable Long userSeq) throws NotFoundException {
        List<ItemRes> itemList = itemService.getItemByUserSeq(userSeq);
        return ResponseEntity.status(HttpStatus.OK).body(itemList);
    }

    @PostMapping("/userItem")
    @ApiOperation(value = "특정 유저에게 아이템을 생성함")
    public ResponseEntity<?> addItemToUser(@RequestBody UserItemReq userItemReq) throws NotFoundException{
        itemService.createUserItem(userItemReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping
    @ApiOperation(value = "새로운 아이템 생성 - 관리자만 사용 가능")
    public ResponseEntity<?> createItem(@RequestPart MultipartFile multipartFile, @RequestParam Byte type, @RequestParam String name) throws IOException{
        ItemCreateReq itemCreateReq = new ItemCreateReq(type, name, multipartFile);
        Long createdSeq = itemService.createItem(itemCreateReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeq);

    }

    @PutMapping("/{itemSeq}")
    @ApiOperation(value = "아이템 업데이트 - 관리자만 사용 가능")
    public ResponseEntity<?> updateItem(@PathVariable Long itemSeq, @RequestPart MultipartFile multipartFile, @RequestParam String name) throws NotFoundException, IOException{
        ItemUpdateReq itemUpdateReq = new ItemUpdateReq(itemSeq, name, multipartFile);
        Long updatedSeq = itemService.updateItem(itemUpdateReq);

        return ResponseEntity.status(HttpStatus.CREATED).body(updatedSeq);
    }

    @DeleteMapping("/{itemSeq}")
    @ApiOperation(value = "아이템 삭제 - 관리자만 사용 가능")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemSeq) throws NotFoundException{
        itemService.deleteItem(itemSeq);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
