package utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

/*
 * Class to write the client log which include both the access log and work log.
 *
 * Client log should have time stamp with millisecond precision
 */
public class Logger implements Closeable {
    private File loggerFile;
    private FileWriter writer;

    public Logger(String loggerFileName) {
        if (!Files.exists(Path.of(loggerFileName))) {
            try {
                loggerFile = new File(loggerFileName);
                loggerFile.createNewFile();
                System.out.println("Create access log file " + loggerFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loggerFile = new File(loggerFileName);

        try {
            writer = new FileWriter(loggerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String request, String response) {
        StringBuilder sb = new StringBuilder();
        sb.append("Time=" + new Date().toInstant().toString())
                .append(" Request=" + request)
                .append(" Response=" + response);

        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void errLog(String log) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Date().toInstant().toString())
                .append(" ")
                .append("ERROR")
                .append(" ")
                .append(log);
        System.err.println(sb.toString());
    }

    public void debugLog(String log) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Date().toInstant().toString())
                .append(" ")
                .append("DEBUG")
                .append(" ")
                .append(log);
        System.out.println(sb.toString());
    }

    @Override
    public void close() {
        try {
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String file = args[0];
        Logger logger = new Logger(file);
        logger.log("PUT ABC AAA", "SUCCESS");

        logger.debugLog("Test");

        logger.close();
    }
}

