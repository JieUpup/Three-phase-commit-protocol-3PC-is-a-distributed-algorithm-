package paxos;

import server.AbstractTCPServer;

import java.net.Socket;
/**
 * The class is a subclass for the  AbstractTCPServer
 * Override the method handler
 */

public class Learner extends AbstractTCPServer {
    //TODO: finish learner as well
    public Learner(int tcpPort) {
        super(tcpPort);
    }

    @Override
    public void handler(Socket socket) {

    }
}
