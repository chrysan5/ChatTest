package com.example.chatTest.service;

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
public class S3UploadService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String s3Path;

    public S3UploadService(
            S3Client s3Client,
            @Value("${s3.bucket-name}") String bucketName,
            @Value("${s3.path}") String s3Path
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.s3Path = s3Path;
    }


    @SneakyThrows
    public void uploadImageToS3(String uploadFileName, MultipartFile file) {
        String uploadUrl = s3Path + "/" + uploadFileName;

        //PutObjectRequest는 s3에 객체 업로드 요청 사용하는 클래스
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uploadUrl)
                .build();

        // fromInputStream 메서드는 InputStream에서 데이터를 읽어 RequestBody 객체로 변환하는 역할
        //주로 HTTP 요청을 보낼 때, 파일이나 스트림과 같은 데이터를 요청 본문에 담아 전송할 수 있도록 처리함
        RequestBody content = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

        s3Client.putObject(request, content);
    }


    //InputStream은 Java에서 바이트 스트림을 처리하기 위한 추상 클래스이다.
    // 주로 파일, 네트워크 소켓, 메모리와 같은 데이터 소스에서 데이터를 읽어오는 데 사용된다
    // InputStream은 Java I/O(Input/Output) 시스템의 중요한 구성 요소로, 다양한 형태의 데이터를 바이트 단위로 읽기 위한 기본 클래스이다
    public InputStream getFileFromS3(String uploadFileName) {
        String uploadUrl = s3Path + "/" + uploadFileName;

        //GetObjectRequest s3에 객체 다운로드 요청시 사용하는 클래스
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(uploadUrl)
                .build();

        return s3Client.getObject(request);
    }
}