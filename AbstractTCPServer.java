package server;

import utils.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * implement runnable interface. override run function
 * use read buffer to receive message from client, set up read 1024 byte once time.
 * construct the string thought the byte."off" means offset inside the buffer.
 *  encoding: standard
 */


public abstract class AbstractTCPServer extends AbstractServer implements Runnable {
    public AbstractTCPServer(int tcpPort) {
        super(tcpPort);
    }

    abstract public void handler(Socket socket);

    public String readMessage(Socket socket) {
        String message;
        try {
            byte[] readBuffer = new byte[1024];
            int off = socket.getInputStream().read(readBuffer);
            message = new String(Arrays.copyOfRange(readBuffer, 0, off), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    public void writeResponse(Socket socket, String response) {
        try {
            //write response to client through OutputStream(convert output string with UTF_8 encoding)
            socket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
            //flush to make sure the output has been sent back to the client.
            socket.getOutputStream().flush();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);


            while (true) {
                final Socket socket = server.accept();
                // create thread for every new connections
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            handler(socket);
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } finally {
            try {
                if (server != null) server.close();
            } catch (IOException e) {
                // swallow the exceptions
                e.printStackTrace();
            }
            logger.close();
        }
    }
}
