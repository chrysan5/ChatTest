package com.example.chatTest.dto;

import com.example.chatTest.model.ImageExtension;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.InputStream;

@Getter
@AllArgsConstructor
public class ImageResponseDto {
    Long fileId;
    String fileName;
    ImageExtension ext;
    InputStream fileData;
}