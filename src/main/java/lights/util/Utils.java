package lights.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
    public static ObjectMapper mapper;

    public static String toJson(Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to convert message to json:" + e.getMessage());
            return "{ \"IP_ADDRESS\" : " + Constants.IP_ADDRESS + ", timerDurationLeft: \"\", state: \"\",error: Failed to build payload}";
        }
    }
}
