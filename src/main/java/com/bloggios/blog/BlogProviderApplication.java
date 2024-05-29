package com.bloggios.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.bloggios.blog",
        "com.bloggios.authenticationconfig",
        "com.bloggios.elasticsearch.configuration"
})
public class BlogProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogProviderApplication.class, args);
    }

}
