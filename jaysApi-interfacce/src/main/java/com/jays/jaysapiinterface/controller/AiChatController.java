package com.jays.jaysapiinterface.controller;

import com.jays.jaysapicommon.client.AiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/AiChat")
public class AiChatController {
    @Resource
    private AiChatClient aiChatClient;
    @GetMapping("/sendQuestion")
    public String sendQuestion(@RequestParam("question") String question){
        return aiChatClient.sendQuestion(question);
    }
}
