package com.nft;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nft.mapper")
public class NftApplication {

    public static void main(String[] args) {
        SpringApplication.run(NftApplication.class, args);
    }

}
