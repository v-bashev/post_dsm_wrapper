package su.nsk.iae.post.dsm.infrastructure;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerUtils {

    public static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Could not find a free TCP/IP port");
    }
}