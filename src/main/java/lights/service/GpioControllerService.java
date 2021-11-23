package lights.service;
import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GpioControllerService {

    @Autowired
    GpioController gpioController;

    public void flash(){
       GpioPinDigitalOutput pin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_14, PinState.LOW);

       pin.toggle();
    }

}
