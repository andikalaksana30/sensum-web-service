package com.indosat.sensum.project.kafka;

import com.indosat.sensum.project.model.Meta;
import com.indosat.sensum.project.model.SensumRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private KafkaTemplate<String, SensumRq> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, SensumRq> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(SensumRq data) {

        if (data.getOrderType().equals("New Registration Prepaid")){

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_new_registration_prepaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_new_registration_prepaid_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_new_registration_prepaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_new_registration_prepaid_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("New Registration Postpaid")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_new_registration_postpaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_new_registration_postpaid_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_new_registration_postpaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_new_registration_postpaid_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Change Postpaid Plan")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_change_postpaid_plan_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_change_postpaid_plan_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_change_postpaid_plan_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_change_postpaid_plan_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Change SIM Card Attributes Prepaid")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_change_sim_card_attributes_prepaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_change_sim_card_attributes_prepaid_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_change_sim_card_attributes_prepaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_change_sim_card_attributes_prepaid_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Change SIM Card Attributes Postpaid")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_change_sim_card_attributes_postpaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_change_sim_card_attributes_postpaid_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_change_sim_card_attributes_postpaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_change_sim_card_attributes_postpaid_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Postpaid To Prepaid")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_postpaid_to_prepaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_postpaid_to_prepaid_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_postpaid_to_prepaid_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_postpaid_to_prepaid_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Migration")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_migration_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_migration_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_migration_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_migration_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Modify")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_modify_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_modify_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_modify_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_modify_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Prepaid Reactivation")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_prepaid_reactivation_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_prepaid_reactivation_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_prepaid_reactivation_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_prepaid_reactivation_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        } else if (data.getOrderType().equals("Bill Payment")) {

            if (data.getContactInfo().size() < 4){
                //transform request
                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");

                LOGGER.info(String.format("Message sent to topic ms_bill_payment_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_bill_payment_prod")
                        .build();

                kafkaTemplate.send(message);

            } else {
                //transform request
                //ambil gerai id
                Meta dataMeta = new Meta();
                dataMeta.setKey(data.getContactInfo().get(3).getKey());
                dataMeta.setValue(data.getContactInfo().get(3).getValue());
                List<Meta> dataListMeta = new ArrayList<>();
                dataListMeta.add(dataMeta);

                // set data gerai id
                data.setMetadata(dataListMeta);

                // transform semua key
                data.getContactInfo().get(0).setKey("vz_c_name");
                data.getContactInfo().get(1).setKey("vz_c_email");
                data.getContactInfo().get(2).setKey("vz_c_phone_number");
                data.getMetadata().get(0).setKey("vz_graiid");

                // hapus contacInfo geraiId
                data.getContactInfo().remove(3);

                LOGGER.info(String.format("Message sent to topic ms_bill_payment_prod -> %s", data.toString()));

                Message<SensumRq> message = MessageBuilder
                        .withPayload(data)
                        .setHeader(KafkaHeaders.TOPIC, "ms_bill_payment_prod")
                        .build();

                kafkaTemplate.send(message);
            }

        }


    }


}
