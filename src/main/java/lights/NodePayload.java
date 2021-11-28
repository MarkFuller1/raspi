package lights;

import lights.util.Utils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
        log.info(IP_ADDRESS + timerDurationLeft + state + message);
        return Utils.toJson(this);
    }
}
