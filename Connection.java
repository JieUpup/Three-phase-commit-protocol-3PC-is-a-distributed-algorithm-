package utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Connection {
    private OutputStream os;
    private InputStream is;
    private Socket client;
    public Connection(String target, int port) {
        try {
            client = new Socket(target, port);
            System.out.println("created client to target: " + target + " :" + port);
            // Client will be timeout in 1000ms if no response received
            client.setSoTimeout(5000);
            os = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String writeMessage(String message) {
        try {
            os.write(message.getBytes(StandardCharsets.UTF_8));
            // read response from socket
            byte[] readBuffer = new byte[1024];
            int off = 0;
            try {
                off = client.getInputStream().read(readBuffer);
            } catch (SocketTimeoutException timeoutException) {
                // catch and handle the socket read timeout exception

                //start next call
                System.out.println("Timeout");
                return "Timeout";
            }
            return new String(Arrays.copyOfRange(readBuffer, 0, off), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Exception";
    }

}
