package lights.service;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class GpioControllerService {

    @Autowired
    DigitalOutput led;

    public String toggle() {
        led.blink(1, 1, TimeUnit.SECONDS);
    }
//        if (led.isHigh()) {
//            led.low();
//            return "is now off";
//        } else {
//            led.high();
//            return "is now on";
//        }
//    }
}
