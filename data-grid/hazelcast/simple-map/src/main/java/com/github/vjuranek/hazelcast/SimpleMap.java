package com.github.vjuranek.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.*;

import java.util.Map;

public class SimpleMap {

    private static Map<Integer, String> testMap;

    public static void main(String[] args) {
        Config cfg = new Config();
        NetworkConfig netCfg = new NetworkConfig();
        netCfg.setPort(2222);
        cfg.setNetworkConfig(netCfg);
        cfg.addListenerConfig(new ListenerConfig(ClusterListener.class.getName()));
        HazelcastInstance hc = Hazelcast.newHazelcastInstance(cfg);
        testMap = hc.getMap("test");

        int clusterSize = hc.getCluster().getMembers().size();
        System.out.println("Cluster size: " + clusterSize);
        testMap.put(1, clusterSize == 1 ? "hello" : "hello from another member");
        System.out.println("Map[1]: "+ testMap.get(1));
    }

    private static class ClusterListener implements MembershipListener {
        @Override
        public void memberAdded(MembershipEvent membershipEvent) {
            if (testMap != null) { // in initial cluster member map is not created yet
                System.out.println("Map[1]: "+ testMap.get(1));
            }
        }

        @Override
        public void memberRemoved(MembershipEvent membershipEvent) {
            System.out.println("Map[1]: "+ testMap.get(1));
        }

        @Override
        public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
            // no-op
        }
    }
}