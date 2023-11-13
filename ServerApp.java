package server;

import utils.Database;
import utils.Logger;
import utils.Utils;

/** use multi-thread to start both TCP and UDP server in the same application
 *
 */

public class ServerApp {
    public static Logger logger = new Logger("server.log");
    private static Database database = new Database(logger);

    public static void main(String[] args) {
        // validate the input
        if (args.length != 1) {
            logger.errLog("Usage: ServerApp <tcpPort>");
            System.exit(-1);
        }

        // start both TCP and UDP server at the same time
        // They're sharing the same database but should not have same port number
        int tcpPort = Utils.parsePort(args[0], logger);

        //create new thread to run the tcp server
        DatabaseTCPServer tcpServer = new DatabaseTCPServer(tcpPort);
        tcpServer.setDatabase(database);
        new Thread(tcpServer).start();
    }
}
