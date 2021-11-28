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
    String IP_ADDRESS;
    String timerDurationLeft;
    String state;
    String message;

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

        return Utils.toJson(this);
    }
}
