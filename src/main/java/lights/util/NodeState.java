package lights.util;

import lombok.Getter;

@Getter
public enum NodeState {
    BOOTED("The node has started successfully"),
    IDLE("There is no timer set and no timer running"),
    TICKING("There is a timer counting down"),
    FINISHED("The timer has been stopped, the process is complete"),
    EXPIRED("The timer finished before the process was complete"),
    LATE("The process is finished, but after the timer expired"),
    NEXT("The timer has begun"),
    SET("The timer was set"),
    ERROR("There was an error, view message for info");


    private String meaning;

    NodeState(String meaning) {
        this.meaning = meaning;
    }

}
