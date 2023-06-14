package com.example.collect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *  Main
 */

@EnableCaching
@EnableAsync
@SpringBootApplication
@MapperScan("com.example.collect.dao")
public class CollectApplication {

    public static void main(String[] args){
        SpringApplication.run(CollectApplication.class, args);
    }

}
