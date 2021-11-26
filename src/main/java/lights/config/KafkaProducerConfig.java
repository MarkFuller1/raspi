package lights.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
public class KafkaProducerConfig<T> {

    @Value(value = "${kafkaServer}")
    private String bootstrapAddress;

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
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put("sasl.mechanism", saslMechanism);
//        configProps.put("security.protocol", securityProtocol);
//        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username ='" + kafkaUser + "' password = '" + kafkaPassword + "';");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}