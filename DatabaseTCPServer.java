package server;

import paxos.Acceptor;
import paxos.Constant;
import paxos.Proposer;
import utils.Database;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is subclass for AbstractTCPServer
 * Three main roles will be referenced in this class.
 * Add three servers to the target
 * logic operation
 */
public class DatabaseTCPServer extends AbstractTCPServer {
    private Database database;
    private Acceptor acceptor;
    private Proposer proposer;

    public DatabaseTCPServer(int tcpPort) {
        super(tcpPort);
        this.acceptor = new Acceptor(Constant.POXOS_PORT);
    }

    public void setDatabase(Database database) {
        this.database = database;
        acceptor.setDatabase(database);
        new Thread(acceptor).start();

        this.proposer = new Proposer();
        List<String> targets = new ArrayList<>();
        targets.add("my-server");
        targets.add("my-server-2");
        targets.add("my-server-3");
        proposer.setTargetHosts(targets);
    }
    @Override
    public void handler(Socket socket) {
        try {
            String inputString = readMessage(socket);
            logger.debugLog("from: " + socket.getRemoteSocketAddress().toString() + " " + inputString);

            String op = Database.getOperation(inputString);
            if (op.equals("DELETE") || op.equals("PUT")) {
                boolean status = proposer.commit(inputString);
                writeResponse(socket, String.valueOf(status));
            } else {
                String outputString = database.parseInput(inputString);
                logger.debugLog("response: " + outputString);
                writeResponse(socket, outputString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
