package com.journal.journalApp.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redisTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    @Disabled
    public void testRedis() {
        redisTemplate.opsForValue().set("email", "abc@email.com");
        String email = redisTemplate.opsForValue().get("email");
        Assertions.assertEquals("abc@email.com", email);

    }




}
