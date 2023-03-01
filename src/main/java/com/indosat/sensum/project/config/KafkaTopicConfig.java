//package com.indosat.sensum.project.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//
//@Configuration
//public class KafkaTopicConfig {
//
//    @Bean
//    public NewTopic topic1() {
//        return TopicBuilder.name("ms_new_registration_prepaid_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic2() {
//        return TopicBuilder.name("ms_new_registration_postpaid_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic3() {
//        return TopicBuilder.name("ms_change_postpaid_plan_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic4() {
//        return TopicBuilder.name("ms_change_sim_card_attributes_prepaid_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic5() {
//        return TopicBuilder.name("ms_change_sim_card_attributes_postpaid_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic6() {
//        return TopicBuilder.name("ms_postpaid_to_prepaid_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic7() {
//        return TopicBuilder.name("ms_migration_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic8() {
//        return TopicBuilder.name("ms_modify_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic9() {
//        return TopicBuilder.name("ms_prepaid_reactivation_prod")
//                .build();
//    }
//
//    @Bean
//    public NewTopic topic10() {
//        return TopicBuilder.name("ms_bill_payment_prod")
//                .build();
//    }
//}
