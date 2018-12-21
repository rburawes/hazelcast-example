package com.demo.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the app, everything starts from here.
 */
@SpringBootApplication
public class ClusterApplication {

    private static Logger logger = LoggerFactory.getLogger(ClusterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClusterApplication.class, args);
    }

}
