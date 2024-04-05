package com.jays.jaysapiinterface.controller;


import com.jays.model.User;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 伪造接口
 * 测试用
 */
@RestController
@RequestMapping("name")
public class NameController {
    @GetMapping("/1")
    public String getNameByGet(String name){
        return "GET 你的名字是"+name;
    }

    @PostMapping("/2")
    public String getNameByPost(@RequestParam String name){
        return "Post 你的名字是"+name;
    }

    /**
     * 注意了，这里导入的是我自己sdk里的user类
     * @param user
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest httpServletRequest){
        String accessKey = "jays";
        String secretKey = "abcd";
        String accessKeyHeader = httpServletRequest.getHeader("accessKey");
        String secretKeyHeader = httpServletRequest.getHeader("secretKey");
        String accessKeyMd5 = DigestUtils.md5DigestAsHex(accessKey.getBytes());
        String secretKeyMd5 = DigestUtils.md5DigestAsHex(secretKey.getBytes());
        if(!accessKeyHeader.equals(accessKeyMd5) || !secretKeyHeader.equals(secretKeyMd5)){
            throw new RuntimeException("无权限");
        }
        String result = "Post 你的名字是"+user.getUserName();
        return result;
    }
}
