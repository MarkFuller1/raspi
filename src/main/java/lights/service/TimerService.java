package lights.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TimerService {

    private LocalDateTime startTime = LocalDateTime.now();

    private int hour = 0;
    private int minute = 0;
    private int seconds = 0;
    private long nanos = 0;

    public String getSystemTime() {
        return LocalTime.now().toString();
    }

    public String getTimeLeft() {
        LocalDateTime endTime = startTime.plusHours(hour).plusMinutes(minute).plusSeconds(seconds).plusNanos(nanos);

        Duration diff = Duration.between(LocalDateTime.now(), endTime);
        return diff.toString();

    }

    public String setTimer(int hr, int min, int sec, long nan) {
        hour = hr;
        minute = min;
        seconds = sec;
        nanos = nan;

        return LocalTime.of(hr, min, sec, (int) nan).toString();
    }

    public String startTimer() {
        startTime = LocalDateTime.now();
        return LocalTime.now().toString();
    }

    public String getTimer() {
        return LocalTime.of(hour, minute, seconds, (int) nanos).toString();
    }
}
