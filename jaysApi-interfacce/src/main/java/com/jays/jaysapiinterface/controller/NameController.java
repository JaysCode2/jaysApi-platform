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

        //begin，这些鉴权业务你觉得放在这合适吗
        //不合适，所以我们后续要把鉴权放在网关层
//        String accessKey = "jays";
//        String secretKey = "abcd";
//        String accessKeyHeader = httpServletRequest.getHeader("accessKey");
//        String secretKeyHeader = httpServletRequest.getHeader("secretKey");
//        String accessKeyMd5 = DigestUtils.md5DigestAsHex(accessKey.getBytes());
//        String secretKeyMd5 = DigestUtils.md5DigestAsHex(secretKey.getBytes());
//        if(!accessKeyHeader.equals(accessKeyMd5) || !secretKeyHeader.equals(secretKeyMd5)){
//            throw new RuntimeException("无权限");
//        }
        //end
        String result = "Post 你的名字是"+user.getUserName();
        return result;
    }
}
