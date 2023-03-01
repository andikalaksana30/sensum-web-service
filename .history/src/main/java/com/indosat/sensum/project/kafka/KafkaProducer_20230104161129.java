package com.indosat.sensum.project.kafka;

import com.indosat.sensum.project.model.SensumRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, SensumRq> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, SensumRq> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(SensumRq data) {

        if (data.getOrderType().equals("New Registration Prepaid")){
            LOGGER.info(String.format("Message sent to topic New_Registration_Prepaid -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "New_Registration_Prepaid_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("New Registration Postpaid")) {
            LOGGER.info(String.format("Message sent to topic New_Registration_Postpaid -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "New_Registration_Postpaid_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Change Postpaid Plan")) {
            LOGGER.info(String.format("Message sent to topic Change_Postpaid_Plan_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Change_Postpaid_Plan_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Change SIM Card Attributes Prepaid")) {
            LOGGER.info(String.format("Message sent to topic Change_SIM_Card_Attributes_Prepaid_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Change_SIM_Card_Attributes_Prepaid_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Change SIM Card Attributes Postpaid")) {
            LOGGER.info(String.format("Message sent to topic Change_SIM_Card_Attributes_Postpaid_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Change_SIM_Card_Attributes_Postpaid_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Postpaid To Prepaid")) {
            LOGGER.info(String.format("Message sent to topic Postpaid_To_Prepaid_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Postpaid_To_Prepaid_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Migration")) {
            LOGGER.info(String.format("Message sent to topic Migration_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Migration_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Modify")) {
            LOGGER.info(String.format("Message sent to topic Modify_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Modify_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Prepaid Reactivation")) {
            LOGGER.info(String.format("Message sent to topic Prepaid_Reactivation_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Prepaid_Reactivation_Dev")
                    .build();

            kafkaTemplate.send(message);

        } else if (data.getOrderType().equals("Bill Payment")) {
            LOGGER.info(String.format("Message sent to topic Bill_Payment_Dev -> %s", data.toString()));

            Message<SensumRq> message = MessageBuilder
                    .withPayload(data)
                    .setHeader(KafkaHeaders.TOPIC, "Bill_Payment_Dev")
                    .build();

            kafkaTemplate.send(message);

        }


    }


}
