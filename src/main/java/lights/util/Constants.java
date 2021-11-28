package lights.util;

import lombok.extern.slf4j.Slf4j;

import java.net.NetworkInterface;
import java.util.Enumeration;

@Slf4j
public class Constants {
    public static String MAC_ADDRESS = "MAC ADDRESS ERROR";

    static {
        try {
            for (Enumeration enm = NetworkInterface.getNetworkInterfaces(); enm.hasMoreElements(); ) {
                NetworkInterface network = (NetworkInterface) enm.nextElement();
                if (null != network.getHardwareAddress()) {
                    StringBuilder sb = new StringBuilder(18);
                    for (byte b : network.getHardwareAddress()) {
                        if (sb.length() > 0)
                            sb.append(':');
                        sb.append(String.format("%02x", b));
                    }
                    MAC_ADDRESS = sb.toString();
                }
            }
        } catch (Exception e) {
            log.error("Failed to fetch mac address: " + e.getMessage());
        }
    }
}
