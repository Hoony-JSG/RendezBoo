package com.ssafy.a107.common.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileHandler {
    private final String rootPath = System.getProperty("user.dir");
    private final String folderPath = "/static/images/";

    /**
     * 파일을 저장한 뒤 url을 반환
     *
     * @param multipartFile
     * @return
     */
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return "";
        }

        String filePath = folderPath;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // jpeg, png, gif 파일들만 받아서 처리할 예정
        String contentType = multipartFile.getContentType();
        String originalFileExtension;
        // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
        if (ObjectUtils.isEmpty(contentType)) {
            return "";
        }

        if (contentType.contains("image/jpeg")) {
            originalFileExtension = ".jpg";
        } else if (contentType.contains("image/png")) {
            originalFileExtension = ".png";
        } else if (contentType.contains("image/gif")) {
            originalFileExtension = ".gif";
        } else {
            return "";
        }

        // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
        String fileName = folderPath + multipartFile.getName() + System.nanoTime() + originalFileExtension;
        // 생성 후 리스트에 추가

        // 저장된 파일로 변경하여 이를 보여주기 위함
        file = new File(rootPath + fileName);
        multipartFile.transferTo(file);

        return fileName;
    }
}
