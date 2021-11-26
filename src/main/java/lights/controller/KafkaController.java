package lights.controller;

import lights.service.kafka.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class KafkaController {
    @Autowired
    ProducerService producerService;

    @PostMapping("/produce/{value}")
    public String produce(@PathVariable String value) {
        return producerService.produce(value);
    }

}
