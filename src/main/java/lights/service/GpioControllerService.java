package lights.service;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
                System.out.println("Stopped sleep");
            }
        }).start();
        return "blinking";
    }
}
