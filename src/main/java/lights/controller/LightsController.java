package lights.controller;

import lights.service.GpioControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpio")
public class LightsController {

    @Autowired
    GpioControllerService gpioControllerService;

    @RequestMapping("/toggle")
    public String flash() throws InterruptedException {
        return gpioControllerService.toggle();
    }

    @RequestMapping("/toggle/{times}/{duration}")
    public String blink(@RequestParam int duration, @RequestParam int times) {
        return gpioControllerService.blink(times, duration);
    }

}
