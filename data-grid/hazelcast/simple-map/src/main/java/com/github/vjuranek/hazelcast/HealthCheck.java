package com.github.vjuranek.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HealthCheck {

    public static void main(String[] args) {
        Config cfg = new Config();
        NetworkConfig netCfg = new NetworkConfig();
        netCfg.getRestApiConfig().setEnabled(true);
        cfg.setNetworkConfig(netCfg);

        HazelcastInstance hc = Hazelcast.newHazelcastInstance(cfg);
        int clusterSize = hc.getCluster().getMembers().size();
        System.out.println("Cluster size: " + clusterSize);
    }
}
