package lights;

import lights.util.Utils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NodePayload {
    public String IP_ADDRESS;
    public String timerDurationLeft;
    public String state;
    public String message;

    @Override
    public String toString() {
        if (!StringUtils.hasText(timerDurationLeft)) {
            timerDurationLeft = "";
        }
        if (!StringUtils.hasText(state)) {
            state = "";
        }
        if (!StringUtils.hasText(message)) {
            message = "";
        }

        log.info(IP_ADDRESS + timerDurationLeft + state + message);

        return Utils.toJson(this);
    }
}
