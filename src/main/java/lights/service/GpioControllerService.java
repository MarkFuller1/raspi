package lights.service;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpioControllerService {

    @Autowired
    DigitalOutput led;

    public void toggle() {
        if (led.isHigh()) {
            led.low();
        } else {
            led.high();
        }
    }
}
