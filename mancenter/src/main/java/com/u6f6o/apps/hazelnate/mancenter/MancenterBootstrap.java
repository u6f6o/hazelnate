package com.u6f6o.apps.hazelnate.mancenter;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

public class MancenterBootstrap {
    private static final int PORT = 9510;
    private static final String CONTEXT_PATH = "/mancenter";

    public static void main(String[] args) throws Exception {
        if (!checkParameters(args)){
            System.exit(1);
        }
        String pathToWarFile = args[0];

        Server server = new Server(PORT);
        server.setHandler(createWebAppContext(pathToWarFile));

        server.start();
        server.join();
    }

    private static WebAppContext createWebAppContext(String pathToWarFile) {
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(CONTEXT_PATH);
        webapp.setWar(pathToWarFile);
        return webapp;
    }

    private static boolean checkParameters(String [] args){
        if (args.length != 1) {
            return false;
        }
        if (!new File(args[0]).isFile()) {
            return false;
        }
        return true;
    }
}
