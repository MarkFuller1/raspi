package lights.service.init.runners;

import lights.service.GpioControllerService;
import lights.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!local")
@Component
public class AppStartRunner implements ApplicationRunner {

    @Autowired
    GpioControllerService gpioControllerService;

    @Autowired
    TimerService timer;

    @Override
    public void run(ApplicationArguments args) {
        gpioControllerService.blink(5, 1000);
        System.err.println("Initialization complete");
        new Thread(() -> {
            while (true) {
                if (timer.isElapsed()) {
                    System.out.println("Timer complete");
                    gpioControllerService.blink(10, 200);
                    timer.setTimer(0, 0, 0, 0);
                }
            }
        }).start();

    }
}
