package com.example.chatTest.controller;

import com.example.chatTest.dto.ImageResponseDto;
import com.example.chatTest.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;


    @PostMapping
    @SneakyThrows
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        Long uploadedImageId = imageService.uploadImage(file);
        return ResponseEntity.ok(uploadedImageId.toString());
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(value = "file") Long fileId) {
        ImageResponseDto response = imageService.getImage(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFileName() + "\"") // 파일의 이름은 업로드 당시의 이름으로 리턴
                .contentType(MediaType.valueOf(response.getExt().getContentType())) // ContentType 은 업로드한 파일에 따라 변경되도록
                .body(new InputStreamResource(response.getFileData())); // InputStream -> InputStreamResource
    }
}