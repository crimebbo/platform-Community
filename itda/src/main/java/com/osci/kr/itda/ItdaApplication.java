package com.osci.kr.itda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(
        exclude = { RedisRepositoriesAutoConfiguration.class }
)
@EnableRedisHttpSession
public class ItdaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItdaApplication.class, args);
    }

}
