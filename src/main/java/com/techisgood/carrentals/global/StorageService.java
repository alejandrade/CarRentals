package com.techisgood.carrentals.global;

import java.io.InputStream;

public interface StorageService {
    void upload(String key, InputStream inputStream);

    InputStream download(String key);
}

