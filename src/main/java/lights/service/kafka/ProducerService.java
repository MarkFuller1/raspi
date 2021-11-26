package lights.service.kafka;

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
public class ProducerService<T> {


    @Value("${kafkaTopic}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public String produce(T payrollInfo) {
        log.info("Sending message:" + payrollInfo.toString());
        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(topicName, payrollInfo);
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, T>>() {

                    @Override
                    public void onSuccess(SendResult<String, T> result) {
                        log.info(
                                "Sent message to [{}], with content =[{}] with offset=[{}]", topicName, payrollInfo, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.info("Unable to send message=[{}] due to : {}", payrollInfo, ex.getMessage());
                    }
                });
        return "Success";
    }
}
