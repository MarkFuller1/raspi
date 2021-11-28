package lights.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lights.NodePayload;
import lights.service.kafka.ConsumerService;
import lights.service.kafka.ProducerService;
import lights.util.Utils;
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
    public String produce(@PathVariable String value) throws JsonProcessingException {
        return producerService.produce(Utils.mapper.readValue(value, NodePayload.class));
    }

    @GetMapping("/messages")
    public String consume(){
        return consumerService.messages.toString();
    }

}
