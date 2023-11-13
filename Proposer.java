package paxos;

import utils.Connection;

import java.util.HashMap;
import java.util.List;
/**
 * The class represent the connection between acceptor and target host
 */

public class Proposer {
    private int transactionId;

    // use fixed number here
    private int Quorum = 2;

    // network
    HashMap<String, Connection> connections;
    List<String> targetHosts;
    public Proposer() {
        connections = new HashMap<>();
        transactionId = 1;
    }

    public void setTargetHosts(List<String> hostList) {
        this.targetHosts = hostList;

    }

    public boolean commit(String update) {
        // increase the transaction id for every transaction no matter exceution status
        this.transactionId++;

        // send message to learners as well
        boolean abort = false;
        for (String target: targetHosts) {
            if (!connections.containsKey(target)) {
                connections.put(target, new Connection(target, paxos.Constant.POXOS_PORT));
            }
        }

        // propose first
        // only need to contact the Quorum number acceptor
        int promised = 0;
        for (Connection connection : connections.values()) {
            String response = connection.writeMessage(new Message(MessageType.PREPARE, this.transactionId, update).toString());
            if (response.equals(Constant.PROMISE)) {
                promised++;
                // move to next step
                if (promised >= this.Quorum) {
                    break;
                }
            }
        }
        // failed if not enough promise
        if (promised < this.Quorum) {
            return false;
        }

        // send out accept message
        // verity the response

        for (Connection connection : connections.values()) {
            connection.writeMessage(new Message(MessageType.ACCEPT, this.transactionId, update).toString());
        }


        return true;
    }

}
