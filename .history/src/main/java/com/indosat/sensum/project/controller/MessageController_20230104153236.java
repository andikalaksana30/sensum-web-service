package com.indosat.sensum.project.controller;

import com.indosat.sensum.project.kafka.KafkaProducer;
import com.indosat.sensum.project.model.Response;
import com.indosat.sensum.project.model.SensumRq;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/api/v1/sensum/publish", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Response> publish(@RequestBody SensumRq sensumRq){

        Response res = new Response();
        res.setStatus("200");
        res.setDescription("successfuly send to kafka");

        kafkaProducer.sendMessage(sensumRq);

        System.out.println(res);

        return ResponseEntity.ok(res);


    }


}
