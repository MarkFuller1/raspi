package lights.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConsumerService {
    public List<String> messages = new ArrayList<String>();

//    @KafkaListener(topics = "${kafkaTopic}", groupId = "${kafkaGroupId}")
    public void consumer(String message) {
        messages.add(message);
        log.info("Consumed:" + message);
    }
}
