package com.assistant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messenger/")
public class MessengerController {

    @RequestMapping("hi")
    public String hi(){
        return "hello world";
    }

    @RequestMapping("webhook")
    public String webhook(@RequestParam(value="hub.verify_token", defaultValue="") String token,
                          @RequestParam(value="hub.challenge", defaultValue="") String challenge){

        if("my_voice_is_my_password_verify_me".equalsIgnoreCase(token)){
            return challenge;
        }
        return "error";
    }


}
