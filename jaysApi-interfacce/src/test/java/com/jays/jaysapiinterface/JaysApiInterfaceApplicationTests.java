package com.jays.jaysapiinterface;

import com.jays.client.JaysApiClient;
import com.jays.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class JaysApiInterfaceApplicationTests {
    @Resource
    private JaysApiClient jaysApiClient;

    @Test
    void contextLoads() {
        String result = jaysApiClient.getNameByGet("jays1");
        User user = new User();
        user.setUserName("jaysSet");
        String userNameByPost = jaysApiClient.getUserNameByPost(user);
        System.out.println(result);
        System.out.println(userNameByPost);
    }

}
