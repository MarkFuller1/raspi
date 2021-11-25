package lights.service;

import com.pi4j.io.gpio.digital.DigitalOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpioControllerService {

    @Autowired
    DigitalOutput led;

    public String toggle() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            led.high();
            Thread.sleep(1000);
            led.low();
            Thread.sleep(1000);
        }
        return "done";
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
