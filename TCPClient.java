package client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**

 *      create TCP client and connect it to the TCP server
 *      read Key value operations from system.in.
 *      read response from server and log the response.
 *       compare: TCP and UDP
 */
public class TCPClient extends AbstractClient {

    public TCPClient(String targetHost, int port) {
        super(targetHost, port);
    }


    public void connect() {
        Socket client = null;

        try {
            client = new Socket(targetHost, port);
            // Client will be timeout in 1000ms if no response received
            client.setSoTimeout(5000);
            OutputStream os = new DataOutputStream(client.getOutputStream());

            // read string from terminal and write to server through socket
            while (true) {
                // Get input command from system in
                String input = getInputFromStdin();
                if (!inputCheck(input)) {
                    // not valid and just continue and take another input
                    continue;
                }

                logger.debugLog("Input request: " + input);
                os.write(input.getBytes(StandardCharsets.UTF_8));

                // read response from socket
                byte[] readBuffer = new byte[1024];
                int off = 0;
                try {
                    off = client.getInputStream().read(readBuffer);
                } catch ( SocketTimeoutException timeoutException) {
                    // catch and handle the socket read timeout exception
                    logger.errLog("Read timeout after 1000ms");

                    //start next call
                    continue;
                }
                String ret = new String(Arrays.copyOfRange(readBuffer, 0, off), StandardCharsets.UTF_8);
                logger.debugLog("Response from server: " + ret);
            }
            //client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
