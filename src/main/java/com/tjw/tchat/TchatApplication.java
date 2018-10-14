package com.tjw.tchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tjw.tchat")
@MapperScan(basePackages = "com.tjw.tchat.mapper")
public class TchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TchatApplication.class, args);
    }
}
