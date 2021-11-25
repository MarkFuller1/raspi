package lights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartRunner implements ApplicationRunner {
    @Autowired
    GpioControllerService gpioControllerService;

    @Override
    public void run(ApplicationArguments args) {
        gpioControllerService.blink(5, 1000);
        System.err.println("Initialization complete");

    }
}
