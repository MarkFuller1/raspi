package lights.controller;

import lights.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timer")
@CrossOrigin(origins = "*")
public class TimerController {

    @Autowired
    TimerService timer;

    @GetMapping
    public String getTimer() {
        return timer.getTimer();
    }

    @PostMapping("/{time}")
    public String setTimer(@PathVariable String time) {
        String[] parsedTime = time.split(":");
        return timer.setTimer(Integer.parseInt(parsedTime[0]), Integer.parseInt(parsedTime[1]),
                Integer.parseInt(parsedTime[2]), Long.parseLong(parsedTime[3]));
    }

    @GetMapping("/systemTime")
    public String systemTime() {
        return timer.getSystemTime();
    }

    @PostMapping("/start")
    public String startTimer() {
        return timer.startTimer();
    }

    @GetMapping("/left")
    public String elapsed() {
        return timer.getTimeLeft();
    }
}
