package com.u6f6o.apps.hazelnate.node;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.core.Hazelcast;

public class HazelcastServerBootstrap {
    private static final String HAZELCAST_CONFIGURATION_FILE = "hazelcast-config.xml";

    public static void main(String[] args) {
        ClasspathXmlConfig xmlConfig = new ClasspathXmlConfig(HAZELCAST_CONFIGURATION_FILE);
        Hazelcast.newHazelcastInstance(xmlConfig);
    }
}
