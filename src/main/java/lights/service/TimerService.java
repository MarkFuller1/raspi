package lights.service;

import lights.NodePayload;
import lights.service.kafka.ProducerService;
import lights.util.Constants;
import lights.util.NodeState;
import lights.util.StateException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@Getter
public class TimerService {

    @Autowired
    ProducerService producerService;

    private LocalDateTime startTime = LocalDateTime.now();

    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private long nanos = 0;

    private NodeState state = NodeState.ERROR;

    public String getSystemTime() {
        return LocalTime.now().toString();
    }

    public Duration getTimeLeft() {
        if (state == NodeState.STARTED || state == NodeState.EXPIRED) {
            LocalDateTime endTime = startTime.plusHours(hours).plusMinutes(minutes).plusSeconds(seconds).plusNanos(nanos);

            return Duration.between(LocalDateTime.now(), endTime);
        }
        return null;
    }

    public String setTimer(int hr, int min, int sec, long nan) {
        try {
            state = NodeState.changeState(state, NodeState.SET);
        } catch (StateException e) {
            log.error(e.getMessage());
            return "";
        }
        hours = hr;
        minutes = min;
        seconds = sec;
        nanos = nan;

        producerService.produce(new NodePayload(Constants.IP_ADDRESS,"", state.name(), LocalTime.of(hr, min, sec, (int) nan).toString()));
        return LocalTime.of(hr, min, sec, (int) nan).toString();
    }

    public String startTimer() {
        try {
            state = NodeState.changeState(state, NodeState.STARTED);
        } catch (StateException e) {
            log.error(e.getMessage());
            return "";
        }

        if (hours == 0 && minutes == 0 && seconds == 0 && nanos == 0) {
            log.info("Tried to start a timer that does not exist");
        } else {
            startTime = LocalDateTime.now();

            producerService.produce(new NodePayload(Constants.IP_ADDRESS, "", state.name(), LocalTime.now().toString()));
        }

        return LocalTime.now().toString();
    }

    public String getTimer() {
        return LocalTime.of(hours, minutes, seconds, (int) nanos).toString();
    }

    public boolean isElapsed() {
        if (state == NodeState.STARTED) {
            Duration timeLeft = getTimeLeft();
            log.info("Time left: " + timeLeft.toString());
            return timeLeft.isNegative();
        }
        return false;
    }

    public void stopTimer() {
        boolean failed = false;
        try {
            // if the state cant be changed to either of these we are in the wrong state
            NodeState.changeState(state, NodeState.DONE);
        } catch (StateException e) {
            failed = true;
            log.error(e.getMessage());
        }
        if (!failed) {
            try {
                // if the state cant be changed to either of these we are in the wrong state
                NodeState.changeState(state, NodeState.LATE);
            } catch (StateException e) {
                log.error(e.getMessage());
                if (failed) return;
            }
        }

        Duration left = getTimeLeft();

        if (!(left.isNegative())) {
            // payload already matching
            log.info("Stopping timer before expiration");
            state = NodeState.DONE;
        } else if (left.isNegative()) {
            log.info("Stopping timer after expiration");
            state = NodeState.LATE;
        }

        NodePayload payload = new NodePayload(Constants.IP_ADDRESS, left.toString(), state.name(), state.getMeaning());

        producerService.produce(payload);
    }

    public void init() {
        try {
            state = NodeState.changeState(state, NodeState.BOOTED);
        } catch (StateException e) {
            log.error(e.getMessage());
        }
    }

    public void expired() {
        try {
            state = NodeState.changeState(state, NodeState.EXPIRED);
        } catch (StateException e) {
            log.error(e.getMessage());
        }
    }

    public void idle() {
        try {
            state = NodeState.changeState(state, NodeState.IDLE);
        } catch (StateException e) {
            log.error(e.getMessage());
        }
    }
}
