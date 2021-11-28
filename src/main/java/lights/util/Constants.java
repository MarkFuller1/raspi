package lights.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

@Slf4j
public class Constants {
    public static String MAC_ADDRESS = "MAC ADDRESS ERROR";

    static {
        try {
            for (Enumeration enm = NetworkInterface.getNetworkInterfaces(); enm.hasMoreElements(); ) {
                NetworkInterface network = (NetworkInterface) enm.nextElement();
                if (null != network.getHardwareAddress()) {
                    MAC_ADDRESS = Arrays.toString(network.getHardwareAddress());
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch mac address: " + e.getMessage());
        }
    }
}
