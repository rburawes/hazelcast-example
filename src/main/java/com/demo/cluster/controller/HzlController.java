package com.demo.cluster.controller;


import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.demo.cluster.message.Messages;
import com.hazelcast.core.HazelcastInstance;

/**
 * The API that will handle writing and reading of
 * data to/from Hazelcast map.
 */
@RestController
@RequestMapping("/api")
public class HzlController {

    private final Logger logger = LoggerFactory.getLogger(HzlController.class);
    private final HazelcastInstance hzlInstance;

    @Autowired
    private Messages messages;
    @Value("${hzl.map.name}")
    private String hzlMapName;
    @Value("${hzl.map.key-name}")
    private String hzlMapKeyName;
    @Value("${hzl.map.value-name}")
    private String hzlMapValueName;
    @Value("${hzl.members.start-key}")
    private String startedFlag;

    HzlController(@Qualifier("hazelcastInstance") HazelcastInstance hzlInstance) {
        this.hzlInstance = hzlInstance;
    }

    /**
     * Checks the distributed map for 'started' key.
     * If the value is 'false' then 'We are started' will be printed indicating that the cluster has just started,
     * then the value will be added to the map.  Else, the number of nodes running will be printed in the console.
     */
    @PostConstruct
    public void init(){
        Map<String, String> hzlMap = hzlInstance.getMap(hzlMapName);
        int memberSize = hzlInstance.getCluster().getMembers().size();
        if (!hzlMap.containsKey(startedFlag) || (hzlMap.containsKey(startedFlag) && !Boolean.valueOf(hzlMap.get(startedFlag)))) {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> We are started <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            hzlMap.put(startedFlag, "true");
        } else {
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>> There are {} node(s) running <<<<<<<<<<<<<<<<<<<<<<<<<<", memberSize);
        }
    }

    /**
     * Writes data to a Hazelcast map.
     *
     * @param data
     * @return
     */
    @PostMapping("/write")
    public String writeData(@RequestBody String data) {
        Map<String, String> hzlMap = hzlInstance.getMap(hzlMapName);
        try {
            JSONObject jsObject = new JSONObject(data);
            hzlMap.put(jsObject.getString(hzlMapKeyName), jsObject.getString(hzlMapValueName));
        } catch (Exception e) {
            String message = String.format(messages.get("cluster.post.error"), hzlMapName);
            logger.error("{}. {}.", message, e.getMessage());
            return message;
        }

        return messages.get("cluster.post.success");
    }

    /**
     * Find a value from Hazelcast map using the given key.
     *
     * @param key
     * @return
     */
    @GetMapping("/read")
    public String read(@RequestParam String key) {
        Map<String, String> hzlMap = hzlInstance.getMap(hzlMapName);
        try {
            return hzlMap.get(key);
        } catch (Exception e) {
            String message = String.format(messages.get("cluster.get.error"), hzlMapName);
            logger.error("{}. {}.", message, e.getMessage());
            return message;
        }
    }

    /**
     * Reads all the values of the Hazelcast map.
     *
     * @return
     */
    @GetMapping("/all")
    public Map<String, String> readAll() {
        return hzlInstance.getMap(hzlMapName);
    }
}
