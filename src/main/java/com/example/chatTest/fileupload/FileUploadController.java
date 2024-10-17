package com.example.chatTest.fileupload;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor
@RestController
@RequestMapping("/images")
public class FileUploadController {

    private final FileUploadService fileUploadService;


    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        fileUploadService.uploadImage(file);
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> getImage(@RequestParam(value = "file") String fileName) throws IOException {
        InputStream imageStream = fileUploadService.getFile(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"") //다운로드할 파일 이름을 설정하는 것
                .contentType(MediaType.IMAGE_JPEG) // 혹은 png일 경우 IMAGE_PNG로 바꿔줘야함
                .body(new InputStreamResource(imageStream)); //imageStream로 바로 리턴안되므로 InputStreamResource로 감싸서 리턴함
    }
}