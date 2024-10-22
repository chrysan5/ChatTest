package com.example.chatTest.service;

import com.example.chatTest.dto.ImageResponseDto;
import com.example.chatTest.model.ImageExtension;
import com.example.chatTest.model.domain.Image;
import com.example.chatTest.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final S3UploadService s3UploadService;
    private final ImageRepository imageRepository;


    @Transactional
    @SneakyThrows
    public Long uploadImage(MultipartFile file) {
        // 업로드 파일명을 불러옴
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null) throw new Exception("잘못된 파일입니다.");

        // test_image.jpg -> [0]:test_image, [1]:jpg
        String[] fileInfos = splitFileName(originalFileName);

        // ImageExtension Enum 에 설정한 확장자가 아닌 경우는 업로드 하지 않음
        ImageExtension ext = ImageExtension.findByKey(fileInfos[1]).orElseThrow(() -> new Exception("지원하지 않는 확장자 입니다."));

        // S3 에 업로드 할때는 원본 이미지의 이름이 아닌 랜덤한 이미지 명으로 업로드 함 (이름이 중복되면 Overwrite 되기 때문)
        String uploadFileName = UUID.randomUUID().toString() + "." + ext.getKey();

        s3UploadService.uploadImageToS3(uploadFileName, file);

        // 이미지 Entity 객체 생성
        Image image = Image.create(originalFileName, ext, uploadFileName);

        imageRepository.save(image);
        return image.getImageId();
    }


    // 이미지 조회 로직
    //ImageResponse는 이미지 데이터를 HTTP 응답으로 전송할 때 사용되는 객체이다.
    // 일반적으로 웹 애플리케이션에서 서버가 이미지를 클라이언트에게 응답으로 제공할 때 이 개념을 사용한다.
    public ImageResponseDto getImage(Long imageId) {
        Image image = imageRepository.findById(imageId).orElseThrow();
        InputStream fileData = s3UploadService.getFileFromS3(image.getUploadFileName());
        return new ImageResponseDto(image.getImageId(), image.getFileName(), image.getExt(), fileData);
    }



    public String[] splitFileName(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return new String[]{fileName, ""}; // 확장자가 없는 경우
        }

        String name = fileName.substring(0, lastDotIndex);
        String ext = fileName.substring(lastDotIndex + 1);
        return new String[]{name, ext};
    }
}