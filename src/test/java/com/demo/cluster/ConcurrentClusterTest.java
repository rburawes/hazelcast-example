package com.demo.cluster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test the application by running the instances on separate threads
 * simultaneously.
 */
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class ConcurrentClusterTest {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentClusterTest.class);

    /**
     * Launch the application on port 8080.
     */
    @Test
    public final void testLaunchNodeOne() {
        logger.info("Launch node [8080]: ");
        SpringApplicationBuilder node = new SpringApplicationBuilder(ClusterApplication.class)
                .properties("server.port=8080", "spring.profiles.active=dev");
        node.run();
    }

    /**
     * Launch the application on port 8081.
     */
    @Test
    public final void testLaunchNodeTwo() {
        logger.info("Launch node [8081]: ");
        SpringApplicationBuilder node = new SpringApplicationBuilder(ClusterApplication.class)
                .properties("server.port=8081", "spring.profiles.active=dev");
        node.run();
    }

    /**
     * Launch the application on port 8082.
     */
    @Test
    public final void testLaunchNodeThree() {
        logger.info("Launch node [8082]: ");
        SpringApplicationBuilder node = new SpringApplicationBuilder(ClusterApplication.class)
                .properties("server.port=8082", "spring.profiles.active=dev");
        node.run();
    }

}
