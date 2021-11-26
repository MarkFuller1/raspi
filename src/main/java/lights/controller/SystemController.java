package lights.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/system")
public class SystemController {

    @PostMapping("/restart")
    public void reboot() throws IOException {
        Runtime.getRuntime().exec("reboot");
    }
}
