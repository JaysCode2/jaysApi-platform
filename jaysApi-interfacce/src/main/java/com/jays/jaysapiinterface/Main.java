package com.jays.jaysapiinterface;


import com.jays.client.JaysApiClient;
import com.jays.model.User;

/**
 * 注意，这里的客户端也换成我sdk里的了
 */
public class Main {
    public static void main(String[] args) {
        String accessKey = "jays";
        String secretKey = "abcd";
        JaysApiClient jaysClient = new JaysApiClient(accessKey,secretKey);
        String result1 = jaysClient.getNameByGet("jays1");
        String result2 = jaysClient.getNameByPost("jays2");
        User user = new User();
        user.setUserName("jays3");
        String result3 = jaysClient.getUserNameByPost(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }
}
