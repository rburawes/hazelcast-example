package com.demo.cluster.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

/**
 * Monitors the {@link HazelcastInstance} of this application.
 */
@Component
public class HzlMonitor {

    @Value("${hzl.map.name}")
    private String hzlMapName;

    @Value("${hzl.members.start-key}")
    private String startedFlag;

    @Autowired
    private ApplicationContext context;

    /**
     * Checks the distributed map for 'started' key.
     * If the value is 'false' then 'We are started' will be printed indicating that the cluster has just started,
     * then the value will be added to the map.  Else, the number of nodes running will be printed in the console.
     */
    public void monitorInstance() {
        HazelcastInstance hazelcastInstance = context.getBean(HazelcastInstance.class);
        int memberSize = hazelcastInstance.getCluster().getMembers().size();
        IMap<String, Object> hzlMap = hazelcastInstance.getMap(hzlMapName);

        // Only the member who has the lock can read and modify the value of the key
        // in the map. Only the first member can set the value of the key to TRUE and will
        // make the application print the legendary 'We are started' once.
        hzlMap.lock(startedFlag);

        try {
            Boolean startedFlagValue = hzlMap.get(startedFlag) != null ? (Boolean) hzlMap.get(startedFlag) : Boolean.FALSE;
            if (!startedFlagValue) {
                hzlMap.put(startedFlag, Boolean.TRUE);
                System.out.println(String.format("THREAD: [ %s ] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> <<<[ WE ARE STARTED ]>>> <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", Thread.currentThread().getName()));
            }

        } catch (Exception ex) {
            System.out.println(String.format("An error has occurred while doing transaction on %s. %s", hzlMapName, ex.getMessage()));
        } finally {
            hzlMap.unlock(startedFlag);
            System.out.println(String.format("THREAD: [ %s ] >>>>>>>>>>>>>>>>>>>>>>>>>> <<<[ There are %s member(s) running on this cluster ]>>> <<<<<<<<<<<<<<<<<<<<<<<<<<<<", Thread.currentThread().getName(), memberSize));
        }

    }

}
