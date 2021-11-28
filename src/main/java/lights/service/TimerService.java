package lights.service;

import lights.NodePayload;
import lights.service.kafka.ProducerService;
import lights.util.Constants;
import lights.util.NodeState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
public class TimerService {

    @Autowired
    ProducerService producerService;

    private LocalDateTime startTime = LocalDateTime.now();
    private boolean active = false;

    private int hour = 0;
    private int minute = 0;
    private int seconds = 0;
    private long nanos = 0;

    public String getSystemTime() {
        return LocalTime.now().toString();
    }

    public String getTimeLeft() {
        if (active) {
            LocalDateTime endTime = startTime.plusHours(hour).plusMinutes(minute).plusSeconds(seconds).plusNanos(nanos);

            Duration diff = Duration.between(LocalDateTime.now(), endTime);
            return diff.toString();
        }
        return "timer not started";
    }

    public String setTimer(int hr, int min, int sec, long nan) {
        active = false;
        hour = hr;
        minute = min;
        seconds = sec;
        nanos = nan;

        producerService.produce(new NodePayload(Constants.IP_ADDRESS, getTimeLeft(), NodeState.SET.name(), LocalTime.of(hr, min, sec, (int) nan).toString()));
        return LocalTime.of(hr, min, sec, (int) nan).toString();
    }

    public String startTimer() {
        startTime = LocalDateTime.now();
        active = true;

        producerService.produce(new NodePayload(Constants.IP_ADDRESS, getTimeLeft(), NodeState.NEXT.name(), LocalTime.now().toString()));

        return LocalTime.now().toString();
    }

    public String getTimer() {
        return LocalTime.of(hour, minute, seconds, (int) nanos).toString();
    }

    public boolean isElapsed() {
        if (active) {
            Duration timeLeft = Duration.parse(getTimeLeft());
            log.info("Time left: " + timeLeft.toString());
            return timeLeft.isNegative();
        }
        return false;
    }

    public void stopTimer() {
        Duration left = Duration.parse(getTimeLeft());
        NodePayload payload = new NodePayload(Constants.IP_ADDRESS, left.toString(), NodeState.FINISHED.name(), NodeState.FINISHED.getMeaning());

        if (active && !(left.isNegative())) {
            // payload already matching
            active = false;
        } else if (active && left.isNegative()) {
            payload.setState(NodeState.LATE.name());
            payload.setMessage(NodeState.LATE.getMeaning());
        } else if (!active) {
            // start timer if the button is pressed again
            startTimer();
            // start timer sends it own message, return so we dont send our own;
            return;
        }

        producerService.produce(payload);
    }
}
