package com.yaloostore.shop.member.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/csrf")
@RestController
@Slf4j
public class CsrfController {

    @GetMapping
    public void csrfGet(){
        log.info("get");
    }
    @PostMapping
    public void csrfPost(){
        log.info("post");
    }


}
