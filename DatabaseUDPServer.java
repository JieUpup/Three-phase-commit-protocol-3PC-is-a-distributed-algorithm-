package server;

import utils.Database;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * This is subclass for AbstractTCPServer and use Runnable interface
 */
public class DatabaseUDPServer extends AbstractServer implements Runnable {
    public DatabaseUDPServer(int udpPort) {
        super(udpPort);
    }

    private Database database;
    public void setDatabase(Database database) {
        this.database = database;
    }
    @Override
    public void run() {
        try {
            //create UDP server socket.
            DatagramSocket server = new DatagramSocket(port);
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                server.receive(request);

                // process the request from client and handle the key-value operation
                String keyValueStoreOperation = new String(Arrays.copyOfRange(request.getData(),
                        0, request.getLength()), StandardCharsets.UTF_8);
                logger.debugLog("from: "  + request.getAddress().getCanonicalHostName() + ":" + request.getPort() + keyValueStoreOperation);
                String serverResponse = database.parseInput(keyValueStoreOperation);
                logger.debugLog("response: " + serverResponse);

                // get client's address to send response back
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                byte[] serverResponseBytes = serverResponse.getBytes(StandardCharsets.UTF_8);
                DatagramPacket response = new DatagramPacket(serverResponseBytes, serverResponseBytes.length,
                        clientAddress, clientPort);

                server.send(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.close();
        }
    }
}
