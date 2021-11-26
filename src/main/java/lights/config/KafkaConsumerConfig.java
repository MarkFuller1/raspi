package lights.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${kafkaServer}")
    private String bootstrapAddress;

    @Value(value = "${kafkaGroupId}")
    private String groupId;

//    @Value(value = "${saslMechanism}")
//    private String saslMechanism;
//
//    @Value(value = "${KAFKA_REGISTRY_USERNAME}")
//    private String kafkaUser;
//
//    @Value(value = "${KAFKA_REGISTRY_PASSWORD}")
//    private String kafkaPassword;
//
//    @Value(value = "${securityProtocol}")
//    private String securityProtocol;


    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put("sasl.mechanism", saslMechanism);
//        props.put("security.protocol", securityProtocol);
//        props.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username ='" + kafkaUser + "' password = '" + kafkaPassword + "';");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
