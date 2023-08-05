package com.techisgood.carrentals.global;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public String upload(String key, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
            amazonS3.putObject(putObjectRequest);
            return key;
        } catch (IOException e) {
            // Handle any exceptions that might occur during the upload
            e.printStackTrace();
            return null; // Return null to indicate failure
        }
    }

    @Override
    public InputStream download(String key) {
        S3Object s3Object = amazonS3.getObject(bucketName, key);
        return s3Object.getObjectContent();
    }

    @Override
    public URL temporaryUrl(String keyName) {
        Instant expiryTime = Instant.now().plus(1, ChronoUnit.MINUTES);
        return amazonS3.generatePresignedUrl(bucketName, keyName, Date.from(expiryTime));
    }
}
