package lights.service.kafka;

import lights.NodePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class ProducerService {


    @Value("${kafkaTopic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String produce(NodePayload message) {

        log.info("Sending message:" + message.toString());
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message.toString());
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, String>>() {

                    @Override
                    public void onSuccess(SendResult<String, String> result) {
                        log.info(
                                "Sent message to [{}], with content =[{}] with offset=[{}]", topicName, message, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
                    }
                });
        return "Success";
    }
}
