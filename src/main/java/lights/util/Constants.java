package lights.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;

@Slf4j
public class Constants {
    public static String MAC_ADDRESS = "MAC ADDRESS ERROR";

    static {
        try {
            MAC_ADDRESS = Arrays.toString(NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress());
        } catch (Exception e) {
            log.error("Failed to fetch mac address: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
