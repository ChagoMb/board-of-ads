package com.board_of_ads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BoardOfAdsApplication {
    private static final Logger logger = LoggerFactory.getLogger(BoardOfAdsApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(BoardOfAdsApplication.class, args);
    }

}
