package com.example.apiplatform.controller;

import com.example.apiplatform.service.UserService;
import com.jays.client.JaysApiClient;
import com.jays.jaysapicommon.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/newAiChat")
public class NewAiChatController {
    @Resource
    private UserService userService;

    /**
     * 注意了，在knife4j里传经过http调用传String参数要写如：question=hello
     * @param question
     * @param request
     * @return
     */
    @GetMapping("/sendQuestion")
    public String sendQuestion(@RequestParam("question") String question, HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        //这个为真是业务
        JaysApiClient tempClient = new JaysApiClient(accessKey, secretKey);
        String answer = tempClient.sendQuestion(question);
        return answer;
    }
}
