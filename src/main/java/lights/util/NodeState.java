package lights.util;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum NodeState {
    ERROR("There was an error, or the state has not been initialized, view message for info"),
    BOOTED("The node has started successfully"),
    IDLE("There is no timer set and no timer running"),
    SET("The timer was set"),
    STARTED("The timer has started"),
    DONE("The button was pressed, the operation is complete before the timer expired"),
    EXPIRED("The timer finished before the process was complete"),
    LATE("The timer expired before the button was pressed");

    private static final Map<NodeState, List<NodeState>> STATE_DIAGRAM = new HashMap<>() {{
        put(ERROR, List.of(ERROR, BOOTED, IDLE, SET));
        put(BOOTED, List.of(BOOTED, ERROR, IDLE));
        put(IDLE, List.of(IDLE, ERROR, SET));
        put(SET, List.of(SET, ERROR, STARTED));
        put(STARTED, List.of(STARTED, ERROR, DONE, EXPIRED));
        put(EXPIRED, List.of(EXPIRED, ERROR, SET, STARTED));
        put(DONE, List.of(DONE, ERROR, SET, STARTED));
        put(LATE, List.of(LATE, ERROR, SET, STARTED));
    }};

    private String meaning;

    NodeState(String meaning) {
        this.meaning = meaning;
    }

    public static NodeState changeState(NodeState state, NodeState set) throws StateException {
        if (STATE_DIAGRAM.get(state).contains(set)) {
            return set;
        }
        throw new StateException("Cannot change from state:" + state + ", to state " + set);
    }
}
