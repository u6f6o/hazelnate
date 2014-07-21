package com.u6f6o.apps.hazelnate.database;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseBootstrap {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseBootstrap.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting h2 database...");
        Server.createTcpServer(new String[] {"-tcpPort", "9501"}).start();
        LOGGER.info("Database started");
    }
}
