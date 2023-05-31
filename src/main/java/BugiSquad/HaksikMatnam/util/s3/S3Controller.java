package BugiSquad.HaksikMatnam.util.s3;

import BugiSquad.HaksikMatnam.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {

    private final S3Service s3service;


    @PostMapping("")
    public ResponseEntity<DataResponse<Map<String, String>>> fileUpload(
            @RequestPart(value = "file") MultipartFile file
            ) {
        log.info("in");
        String url = s3service.upload(file, "png");
        return new ResponseEntity<>(DataResponse.response(201,Map.of("url",url)),HttpStatus.CREATED);
    }
}

