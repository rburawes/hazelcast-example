package com.demo.cluster.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

/**
 * The Hazelcast configuration that will
 * be used by this application.
 */
@Configuration
public class HzlConfiguration {


    @Value("${hzl.instance.name}")
    private String instanceName;

    @Value("${map.config.name}")
    private String mapConfigName;

    @Value("${max.config.size}")
    private int maxConfigSize;

    @Value("${time.to-live.seconds}")
    private int timeToLive;

    @Value("${hzl.min.cluster-size:2}")
    private String requiredClusterSize;

    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName(instanceName)
                .addMapConfig(
                        new MapConfig()
                                .setName(mapConfigName)
                                .setMaxSizeConfig(new MaxSizeConfig(maxConfigSize, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(timeToLive));
        // When two or more nodes were started at the same time, it is likely that they will not see each other
        // and 'We are started' will be printed multiple times.
        // Having this property will make the Hazelcast instance waits for the enough nodes
        // to form the cluster before starting each member one after another giving time for the nodes to discover or becomes
        // aware of each other.
        config.setProperty("hazelcast.initial.min.cluster.size", requiredClusterSize);
        return config;
    }
}
