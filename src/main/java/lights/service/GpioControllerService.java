package lights.service;

import com.pi4j.io.gpio.digital.DigitalOutput;
import lights.config.GpioConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Profile("!local")
@Service
@Slf4j
public class GpioControllerService {

    @Autowired
    DigitalOutput led;

    public String toggle() {
        if (led.isHigh()) {
            led.low();
            return "is now off";
        } else {
            led.high();
            return "is now on";
        }
    }

    public String blink(int times, int duration) {
        new Thread(() -> {
            try {

                for (int i = 0; i < times; i++) {
                    led.high();
                    Thread.sleep(duration);
                    led.low();
                    Thread.sleep(duration);
                }
            } catch (InterruptedException e) {
                log.info("Stopped sleep");
            }
        }).start();
        return "blinking";
    }

    @PreDestroy
    public static void shutdown() {
        GpioConfig.shutdown();
    }
}
