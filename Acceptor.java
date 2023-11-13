package paxos;

import server.AbstractTCPServer;
import utils.Database;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * The class is a subclass for the  AbstractTCPServer
 * create a hashmap as a buffer
 * set database and pass message
 */
public class Acceptor extends AbstractTCPServer {
    HashMap<Integer, String> buffer = new HashMap<>();

    Database database;

    String hostanme;

    public Acceptor(int tcpPort) {
        super(tcpPort);
        try {
            this.hostanme = InetAddress.getLocalHost().getHostName();
            logger.debugLog("Hostname:" + this.hostanme);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
    @Override
    public void handler(Socket socket) {
        String message = readMessage(socket);
        if (hostanme.equals("my-server-2")) {
            logger.debugLog("Do nothing in my-server-2 to mock the failure");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            writeResponse(socket, Constant.DENIAL);

            return;
        }
        Message m = Message.parseMessage(message);
        if (m.getMessageType().equals(MessageType.PREPARE)) {
            if (buffer.get(m.getTransactionId()) != null) {
                writeResponse(socket, Constant.DENIAL);
            } else {
                buffer.put(m.getTransactionId(), m.getUpdate());
                writeResponse(socket, Constant.PROMISE);
            }

            return;
        }

        if (m.getMessageType().equals(MessageType.ACCEPT)) {
            database.parseInput(m.getUpdate());
            buffer.remove(m.getTransactionId());
            writeResponse(socket, Constant.ACCEPTED);

            return;
        }

        return;
    }
}
