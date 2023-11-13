package utils;

public class Utils {

    // Extract the port parsing to a function so it could be reused in both Client and Server side
    public static int parsePort(String portString, Logger logger) {
        int port = Integer.parseInt(portString);
        if (port < 0 || port > 65536) {
            logger.errLog("Port number " + port +  " is not valid, it should be in the range [0, 65536]");
            System.exit(-2);
        }

        return port;
    }
}
