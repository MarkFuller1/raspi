package lights;

import lights.util.Utils;
import lombok.*;

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
        return Utils.toJson(this);
    }
}
