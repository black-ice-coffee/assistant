package com.assistant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messenger/")
public class MessengerController {

    @RequestMapping("hi")
    public String hi(){
        return "hello world";
    }

}
