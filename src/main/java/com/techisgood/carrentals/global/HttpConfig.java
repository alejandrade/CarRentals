package com.techisgood.carrentals.global;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class HttpConfig {

    @Bean
    public HttpClient httpClient(){
        return HttpClient.newHttpClient();
    }
}
