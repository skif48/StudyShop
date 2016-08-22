package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
@SpringBootApplication
public class ShopApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ShopApplication.class);
    }
}
