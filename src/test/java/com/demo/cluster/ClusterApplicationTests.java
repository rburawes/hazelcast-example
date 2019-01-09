package com.demo.cluster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This runs the main application during build process.
 * Proper tests should be written for better coverage.
 * This uses 'test.properties' file.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ClusterApplicationTests {

    @Test
    public void contextLoads() {
    }

}

