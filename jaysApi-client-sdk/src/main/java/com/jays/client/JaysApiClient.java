package com.jays.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import com.jays.model.User;
import org.springframework.util.DigestUtils;


import java.util.HashMap;

public class JaysApiClient {
    private String accessKey;
    private String secretKey;

    private static final String GATEWAY_HOST = "http://localhost:8090";

    public JaysApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("name",name);
        String result = HttpUtil.get(GATEWAY_HOST+"/api/name/1",paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("name",name);
        String result = HttpUtil.post(GATEWAY_HOST+"/api/name/2",paramMap);
        System.out.println(result);
        return result;
    }

    /**
     * 密钥加密后放入map
     */
    public HashMap<String,String> getHeaderMap(){
        HashMap<String,String> map = new HashMap();
        // md5加密
        String accessKeyMd5 = DigestUtils.md5DigestAsHex(accessKey.getBytes());
        String secretKeyMd5 = DigestUtils.md5DigestAsHex(secretKey.getBytes());
        map.put("accessKey",accessKeyMd5);
        map.put("secretKey",secretKeyMd5);
        return map;
    }

    /**
     * 测试api签名认证
     * @param user
     * @return
     */
    public String getUserNameByPost(User user){
        String userJson = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST+"/api/name/user")
                .addHeaders(getHeaderMap())
                        .body(userJson)
                                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
