package com.pet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.pet.mapper")
@EnableCaching // 激活SpringCache缓存
@SpringBootApplication
public class PetApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetApplication.class, args);
    }
}
