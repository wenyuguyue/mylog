package com.banma.web.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;

/**
 * @author lichaofu
 * @create 2021-04-27 11:38
 */
@Data
public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String brokerList;
    private String topic;
    private KafkaTemplate kafkaTemplate;

    @Override
    public void start() {
        HashMap props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10240);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 102400);
        kafkaTemplate = new KafkaTemplate(new DefaultKafkaProducerFactory(props));
        super.start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        kafkaTemplate.send(topic, eventObject.getMessage());
    }

}
