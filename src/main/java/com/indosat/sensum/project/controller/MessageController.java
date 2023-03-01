package com.indosat.sensum.project.controller;

import com.indosat.sensum.project.kafka.KafkaProducer;
import com.indosat.sensum.project.model.Response;
import com.indosat.sensum.project.model.SensumRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    
    @PostMapping(value = "/api/v1/sensum/publish", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public Response publish(@RequestBody SensumRq sensumRq) throws InterruptedException, ExecutionException, TimeoutException {

        LOGGER.info(String.format("Message recieved -> %s", sensumRq.toString()));

        Response res = new Response();

        if (sensumRq.getOrderType().equals("")){
            res.setStatus("400");
            res.setDescription("orderType is Mandatory Field");
            return res;
        }

        String[] topics = {"New Registration Prepaid", "New Registration Postpaid", "Change Postpaid Plan", "Change SIM Card Attributes Prepaid", "Change SIM Card Attributes Postpaid", "Postpaid To Prepaid", "Migration", "Modify", "Prepaid Reactivation", "Bill Payment"};
        boolean contains = Arrays.stream(topics).noneMatch(sensumRq.getOrderType()::equals);
        if (contains){
            res.setStatus("400");
            res.setDescription("does not match the type of order");
            return res;
        }

//        String[] numbers = {"6285725720974", "6285725720959", "6285725720929", "6285725720923", "6285725720913", "6285811473003", "628156555955"};
//        boolean num = Arrays.stream(numbers).noneMatch(sensumRq.getContactInfo().get(2).getValue()::equals);
//        if (num){
//            res.setStatus("400");
//            res.setDescription("does not tes number");
//            return res;
//        }

        if (sensumRq.getOrderType().equals("Bill Payment")){
            String oldStr = sensumRq.getContactInfo().get(1).getValue();
            String newStr = oldStr.replace(";","");
            sensumRq.getContactInfo().get(1).setValue(newStr);
        }

        if (sensumRq.getOrderType().equals("Bill Payment")){
            String oldStr1 = sensumRq.getContactInfo().get(1).getValue();
            String newStr1 = oldStr1.replace("|","");
            sensumRq.getContactInfo().get(1).setValue(newStr1);
        }

        if (sensumRq.getContactInfo().equals("")){
            res.setStatus("400");
            res.setDescription("contacInfo is Mandatory Field");
            return res;
        }

        if (sensumRq.getContactInfo().get(0).getKey().equals("")){
            res.setStatus("400");
            res.setDescription("name is Mandatory Field");
            return res;
        }

        if (sensumRq.getContactInfo().get(0).getValue().equals("")){
            res.setStatus("400");
            res.setDescription("name is Mandatory Field");
            return res;
        }

        if (sensumRq.getContactInfo().get(2).getKey().equals("")){
            res.setStatus("400");
            res.setDescription("phone number is Mandatory Field");
            return res;
        }

        if (sensumRq.getContactInfo().get(2).getValue().equals("")){
            res.setStatus("400");
            res.setDescription("phone number is Mandatory Field");
            return res;
        }

        // kirim request ke kafka topic
        kafkaProducer.sendMessage(sensumRq);

        res.setStatus("0");
        res.setDescription("successfuly send to kafka");
        return res;

    }


}
