package client;

import utils.Logger;

/**
 * The class handle to validate of command input and create a static logger to read log from the client.
 */
public class ClientApp {

    private static final String TCP = "TCP";

    public static Logger logger = new Logger("client.log");

    public static void main(String[] args) {
        // start both TCP and UDP server at the same time
        // They're sharing the same database but should not have same port number

        // validate the command input
        if (args.length != 2) {
            logger.errLog("usage: ClientApp <targetHost> <port>");
            System.exit(-1);
        }

        String target = args[0];
        int port = Integer.parseInt(args[1]);
        if (port < 0 || port > 65536) {
            logger.errLog("Port number " + port +  " is not valid, it should be in the range [0, 65536]");
            System.exit(-2);
        }

        TCPClient tcpClient = new TCPClient(target, port);
        tcpClient.connect();

    }
}
