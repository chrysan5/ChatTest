package com.example.chatTest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;

    String fileName; // 파일명
    String uploadFileName; // S3에 저장된 파일의 이름
    ImageExtension ext; // 파일 확장자 정보
    LocalDateTime createdAt; // 생성일

    public static Image create(String fileName, ImageExtension ext, String uploadFileName) {
        return Image.builder()
                .fileName(fileName)
                .ext(ext)
                .uploadFileName(uploadFileName)
                .createdAt(LocalDateTime.now())
                .build();
    }
}