package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**

 *       create UDP client and connect it to the UDP server
 *       read Key value operations from system.in.
 *       read response from server and log the response.
 *       */

public class UDPClient extends AbstractClient {
    public UDPClient(String targetHost, int port) {
        super(targetHost, port);
    }


    @Override
    public void connect() {
        try {
            //InetAddress is system library class to represent IP address.
            InetAddress address = InetAddress.getByName(targetHost);
            DatagramSocket client = new DatagramSocket();

            while (true) {
                // Get input command from system in
                String input = getInputFromStdin();
                if (!inputCheck(input)) {
                    // not valid and just continue and take another input
                    continue;
                }

                byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);

                // get InetAddress through target hostname
                //set up the byte to send the server,
                DatagramPacket request = new DatagramPacket(inputBytes, inputBytes.length, address, port);

                // send the request to server
                client.send(request);

                // send timeout to 1000ms
                client.setSoTimeout(1000);

                // try to get response from server
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                try {
                    client.receive(response);
                } catch (SocketTimeoutException exception) {
                    logger.errLog("Connection timeout after 1000ms");
                }

                String responseString = new String(buffer, 0, response.getLength());
                System.out.println(responseString);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
