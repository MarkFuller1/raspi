package lights.controller;

import lights.service.GpioControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("!local")
@RestController
@RequestMapping("/gpio")
@CrossOrigin(origins = "*")
public class LightsController {

    @Autowired
    GpioControllerService gpioControllerService;

    @RequestMapping("/toggle")
    public String flash() {
        return gpioControllerService.toggle();
    }

    @RequestMapping("/toggle/{times}/{duration}")
    public String blink(@PathVariable int duration, @PathVariable int times) {
        return gpioControllerService.blink(times, duration);
    }
}
