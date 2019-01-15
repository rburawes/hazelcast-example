package com.demo.cluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.demo.cluster.monitor.HzlMonitor;

/**
 * The main class of the app, everything starts from here.
 */
@SpringBootApplication
public class ClusterApplication {

    /**
     * Injects the bean that will monitor the
     * {@link com.hazelcast.core.HazelcastInstance}'s map.
     */
    @Autowired
    private HzlMonitor hzlMonitor;

    public static void main(String[] args) {
        SpringApplication.run(ClusterApplication.class, args);
    }

    /**
     * When the application is ready, check the values of
     * the {@link com.hazelcast.core.HazelcastInstance}'s map.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void clusterEventListener() {
        hzlMonitor.monitorInstance();
    }
}





