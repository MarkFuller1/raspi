package lights.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppStartRunner {
    @Autowired
    GpioControllerService gpioControllerService;

    @PostConstruct
    public void postConstruct() {
        gpioControllerService.blink(5, 1000);
    }
}
