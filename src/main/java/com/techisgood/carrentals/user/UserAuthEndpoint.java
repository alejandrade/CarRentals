package com.techisgood.carrentals.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/user/v1")
public class UserAuthEndpoint {

    @PostMapping
    public String test() {
        return "";
    }

}
