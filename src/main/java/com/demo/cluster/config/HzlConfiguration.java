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
        return config;
    }
}
