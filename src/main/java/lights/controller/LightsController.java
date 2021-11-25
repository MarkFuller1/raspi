package lights.controller;

import lights.service.GpioControllerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpio")
public class LightsController {

    @Autowired
    GpioControllerService gpioControllerService;

    @RequestMapping("/flash")
    public String flash() {
        return gpioControllerService.toggle();
    }

}
