package BugiSquad.HaksikMatnam.util.s3;


import BugiSquad.HaksikMatnam.common.exception.CustomException;
import BugiSquad.HaksikMatnam.common.exception.ErrorCode;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;


    private final AmazonS3Client amazonS3Client;

    public String upload(MultipartFile multipartFile, String extension) {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String originalName = uuid + "." + extension;
        long size = multipartFile.getSize(); // 파일 크기

        String path = S3Bucket;

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(size);

        // S3에 업로드
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(path, originalName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IOException);
        }

        String fileFath = amazonS3Client.getUrl(path, originalName).toString();

        return fileFath;
    }
}

