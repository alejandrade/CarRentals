package com.techisgood.carrentals.global;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {
    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public void upload(String key, InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                key, inputStream, new ObjectMetadata());
        amazonS3.putObject(putObjectRequest);
    }

    @Override
    public InputStream download(String key) {
        S3Object s3Object = amazonS3.getObject(bucketName, key);
        return s3Object.getObjectContent();
    }
}
