package lights;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final GpioController gpioController = GpioFactory.getInstance();

        final GpioPinDigitalOutput pin = gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_14);
        pin.high();
        System.out.println("Pin high");
        Thread.sleep(2000);

        pin.low();

        pin.pulse(2000);

        gpioController.shutdown();
    }
}
