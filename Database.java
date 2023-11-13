package utils;

import java.util.HashMap;

/*
 *  Database support three operations
 *  PUT    <KEY> <VALUE>
 *  GET    <KEY>
 * Return the value when everything went well.
 *  DELETE <KEY>
 */
public class Database {
    private HashMap<String, String> store;
    private Logger logger;

    public Database(Logger logger) {
        this.store = new HashMap<>();
        this.logger = logger;
    }

    public static String getKey(String input) {
        String[] arr = input.split("\s\s*");
        return arr[1];
    }

    public static String getOperation(String input) {
        String[] arr = input.split("\s\s*");
        return arr[0];
    }
    public  String parseInput(String input) {
        /*
         *  PUT    <KEY> <VALUE>
         *  GET    <KEY>
         *  DELETE <KEY>
         */

        //split string with spaces. use regex.
        String[] arr = input.split("\s\s*");

        if (arr.length < 2) {
            System.out.println("Invalid input");
            return "false, invalid input";
        }

        // start to parse the operation and key
        String operation = arr[0];
        String key = arr[1];

        if (operation.equals("GET")) {
            if (arr.length != 2) {
                System.out.println("Invalid input");

                return "false, invalid input";
            }

            return this.get(key);
        } else if (operation.equals("DELETE")) {
            if (arr.length != 2) {
                System.out.println("Invalid input");

                return "false, invalid input";
            }

            return this.delete(key);
        } else if (operation.equals("PUT")) {
            if (arr.length != 3) {
                System.out.println("Invalid input");

                return "false, invalid input";
            }

            String value = arr[2];
            return this.put(key, value);
        }

        return "unknown operation";
    }

    /*
     *  Return success when everything went well
     */
    public String put(String key, String value) {
        store.put(key, value);

        return "Success";
    }

    public String delete(String key) {
        store.remove(key);

        return "Success";
    }

    public String get(String key) {
        if (!store.containsKey(key)) {
            logger.debugLog("Key: " + key + " not existed");

            return "Failure";
        }

        String ret = store.get(key);

        return ret;
    }

}
