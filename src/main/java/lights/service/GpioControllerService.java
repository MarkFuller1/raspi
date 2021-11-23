package lights.service;

import com.pi4j.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpioControllerService {

    @Autowired
    Context pi4j;

    public void flash() {

        // create a digital output instance using the default digital output provider
        var output = pi4j.dout().create(4, "my-led");

        if (output.isHigh()) {
            output.low();
        } else {
            output.high();
        }
    }
}
