package utils;

public class DBCommandParser {
    /*
     * Check if the input from user is valid or not before send the requests to server
     *
     * return true means it's a valid operation
     * return false means it's not valid
     */

    public static boolean validateCommand(String command, Logger logger) {

        // Trim the leading spaces and trailing spaces
        command.trim();

        String[] arr = command.split("\s\s*");

        String op = arr[0].toUpperCase();
        if (!op.equals("PUT") && !op.equals("GET") && !op.equals("DELETE")) {
            logger.errLog("Invalid operation");

            return false;
        }

        if (op.equals("PUT") && arr.length != 3) {
            logger.errLog("Invalid put command, usage: PUT <KEY> <VALUE>");

            return false;
        }

        if ((op.equals("GET") || op.equals("DELETE")) && arr.length != 2) {
            logger.errLog("Invalid GET/DELETE command, usage: GET/DELETE <KEY>");

            return false;
        }

        return true;
    }
}
