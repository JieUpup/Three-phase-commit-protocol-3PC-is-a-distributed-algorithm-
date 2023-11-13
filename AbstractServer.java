package server;

import utils.Database;
import utils.Logger;

import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
/**
 * The class is an abstract class
 * create a logger for serverApp
 * create two type of string input
 */

abstract public class AbstractServer {
    // As we have shared database for both TCPServer and UDPServer.
    // Only created one global logger, so TCP server and UDP server could write to the same log file.
    protected static Logger logger = ServerApp.logger;

    // Shared database which will be passed through ServerApp, this is only the reference of the database.
    protected int port;

    private static final String INVALID_INPUT = "Invalid input";
    private static final String SUCCESS = "Success";

    public AbstractServer(int port) {
        this.port = port;
    }

}

