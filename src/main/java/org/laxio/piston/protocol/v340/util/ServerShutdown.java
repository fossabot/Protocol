package org.laxio.piston.protocol.v340.util;

public class ServerShutdown extends Thread {

    @Override
    public void run() {
        // TODO: figure out a process that works here, system exits before shutdown finishes
        /*
        for (PistonServer server : ServerManager.getInstance().getServers()) {
            server.stop();
        }
        */
    }

}
