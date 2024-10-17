package com.example.chatTest.fileupload;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;


@Service
public class FileUploadService  {

    private final S3Client s3Client;
    private final String bucketName;
    private final String s3Path;

    public FileUploadService(
            S3Client s3Client,
            @Value("${s3.bucket-name}") String bucketName,
            @Value("${s3.path}") String s3Path
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.s3Path = s3Path;
    }

    @SneakyThrows
    public String uploadImage(MultipartFile multipartFile) {
        String uploadUrl = s3Path + "/" + multipartFile.getOriginalFilename();

        //PutObjectRequest는 s3에 객체 업로드 요청 사용하는 클래스이다.
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uploadUrl)
                .build();

        RequestBody content = RequestBody.fromInputStream(
                multipartFile.getInputStream(),
                multipartFile.getSize()
        );

        s3Client.putObject(request, content);
        return uploadUrl;
    }

    private String getS3UploadUrl(String fileName) {
        return s3Path + "/" + fileName;
    }


    public InputStream getFile(String fileName) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Path + "/" +fileName)
                .build();

        return s3Client.getObject(request);
    }
}