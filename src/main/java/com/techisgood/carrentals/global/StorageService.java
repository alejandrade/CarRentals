package com.techisgood.carrentals.global;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

public interface StorageService {
    String upload(String key, MultipartFile inputStream);

    InputStream download(String key);

    URL temporaryUrl(String key);
}

