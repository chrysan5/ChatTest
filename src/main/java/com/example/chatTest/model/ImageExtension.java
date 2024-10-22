package com.example.chatTest.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public enum ImageExtension {
    // 파일 업로드시 파일의 확장자를 제한하기 위해 & 미디어 타입으로 치환하기 위해 작성합니다.
    JPG(List.of("jpg", "jpeg"), "image/jpeg"),
    PNG(List.of("png"), "image/png");

    final List<String> key;
    final String contentType;

    ImageExtension(List<String> key, String contentType) {
        this.key = key;
        this.contentType = contentType;
    }

    public static Optional<ImageExtension> findByKey(String key) {
        return Arrays.stream(ImageExtension.values())
                .filter(ext -> ext.key.contains(key))
                .findAny();
    }

    public String getKey() {
        return this.key.get(0);
    }
}