package lights.controller;

import lights.NodePayload;
import lights.service.kafka.ProducerService;
import lights.util.Constants;
import lights.util.NodeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    ProducerService producerService;

    @PostMapping("/restart")
    public void reboot() throws IOException {
        producerService.produce(new NodePayload(Constants.IP_ADDRESS, "", NodeState.RESTARTED.name(), NodeState.BOOTED.getMeaning()));
        Runtime.getRuntime().exec("reboot");
    }
}
