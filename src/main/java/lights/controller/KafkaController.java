package lights.controller;

import lights.service.kafka.ConsumerService;
import lights.service.kafka.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class KafkaController {
    @Autowired
    ProducerService producerService;

    @Autowired
    ConsumerService consumerService;

    @PostMapping("/produce/{value}")
    public String produce(@PathVariable String value) {
        return producerService.produce(value);
    }

    @GetMapping("/messages")
    public String consume(){
        return consumerService.messages.toString();
    }

}
