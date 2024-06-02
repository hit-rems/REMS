package com.fj.project_heima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ProjectHeimaApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testjwt(){
        Map<String,Object> clians=new HashMap<>();
        clians.put("id",1);
        clians.put("username","张三");
        String token=JWT.create()
                .withClaim("user",clians)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//过期时间
                .sign(Algorithm.HMAC256("fj"));//密钥
        System.out.println(token);
    }

}
