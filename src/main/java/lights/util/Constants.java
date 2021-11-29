package lights.util;

import lombok.extern.slf4j.Slf4j;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@Slf4j
public class Constants {
    public static String IP_ADDRESS = "";

    static {
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            IP_ADDRESS = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            log.error("Failed to fetch mac address: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
