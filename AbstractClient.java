package client;

import utils.DBCommandParser;
import utils.Logger;
import java.util.Scanner;

/**

 * Client should also report malformed request in a human-readable way
 *
 * Client should be able to handle timeout in case of server failure.
 */
abstract public class AbstractClient {
    protected String targetHost;
    protected int port;

    protected Logger logger = ClientApp.logger;

    AbstractClient(String targetHost, int port) {
        this.targetHost = targetHost;
        this.port = port;
    }

    abstract public void connect();

    /*
     * Check if the input from user is valid or not before send the requests to server
     *
     * return true means it's a valid operation
     * return false means it's not valid
     */
    public boolean inputCheck(String input) {
        return DBCommandParser.validateCommand(input, logger);
    }

    public String getInputFromStdin() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        return input;
    }
}
