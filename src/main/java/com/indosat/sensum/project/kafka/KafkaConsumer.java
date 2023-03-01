package com.indosat.sensum.project.kafka;

import com.indosat.sensum.project.model.Contact;
import com.indosat.sensum.project.model.Meta;
import com.indosat.sensum.project.model.SensumRq;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "ms_new_registration_prepaid_prod", groupId = "sensum")
    public void consumeTopic1(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_new_registration_postpaid_prod", groupId = "sensum")
    public void consumeTopic2(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_change_postpaid_plan_prod", groupId = "sensum")
    public void consumeTopic3(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_change_sim_card_attributes_prepaid_prod", groupId = "sensum")
    public void consumeTopic4(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_change_sim_card_attributes_postpaid_prod", groupId = "sensum")
    public void consumeTopic5(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_postpaid_to_prepaid_prod", groupId = "sensum")
    public void consumeTopic6(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_migration_prod", groupId = "sensum")
    public void consumeTopic7(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_modify_prod", groupId = "sensum")
    public void consumeTopic8(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_prepaid_reactivation_prod", groupId = "sensum")
    public void consumeTopic9(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

    @KafkaListener(topics = "ms_bill_payment_prod", groupId = "sensum")
    public void consumeTopic10(SensumRq sensumRq){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<SensumRq> entity = new HttpEntity<SensumRq>(sensumRq, headers);

        if (sensumRq.getContactInfo().get(1).getValue().equals("")){
            // untuk hit api sensum chanel sms
            sensumRq.getContactInfo().get(1).setValue("tes" + Math.random() + "@gmail.com");
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        } else {
            // untuk hit api sensum chanel email
            sensumRq.setChannelType("Email");
            String urlEmailChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenEmailChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenEmailChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resEmail = restTemplate.exchange(urlEmailChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel Email -> %s", resEmail));
            LOGGER.info(String.format("Message send to sensum chanel Email -> %s", sensumRq));
            // akhir

            // untuk hit api sensum chanel sms
            sensumRq.setChannelType("Sms");
            String urlSmsChanel = "https://open-api.surveysensum.com/api/v2/Share/shareSurvey?surveyGuid=32a914a9-9bf9-4485-a49e-af513df70b8e";
            String tokenSmsChanel = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImEyNTYwZTZmMmYyMjU1NGIzOWM3NTllNGFiOWVmYWIwIiwidHlwIjoiSldUIn0.eyJuYmYiOjE2NzM5NDM2NTksImV4cCI6MTcwNTQ3OTY1OSwiaXNzIjoiaHR0cHM6Ly9wcm9kLXN0cy5zdXJ2ZXlzZW5zdW0uY29tIiwiYXVkIjpbImh0dHBzOi8vcHJvZC1zdHMuc3VydmV5c2Vuc3VtLmNvbS9yZXNvdXJjZXMiLCJ2el9vcGVuX2FwaSJdLCJjbGllbnRfaWQiOiJhYmE0YzJjMi1hYzEzLTQyZTUtYTZhNS00MThhMjJkYjc4MWIiLCJzdWIiOiIyODE3ODdkYS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJhdXRoX3RpbWUiOjE2NzM5NDM2NTksImlkcCI6ImxvY2FsIiwiSXNQYW5lbEFsbG93ZWQiOiJGYWxzZSIsInN1YnNjcmlwdGlvbkd1aWQiOiIyYWIzNThmOS00YjhiLTExZWQtOTZkYy0wNjFmOGJjN2Y1ZGMiLCJ1c2VySWQiOiJhZGl0eWEucml6YWxkaUBpb2guY28uaWQiLCJGdWxseU1hbmFnZWRVc2VyIjoiZmFsc2UiLCJBZ2VudFVzZXIiOiJmYWxzZSIsImZvclphcHBlciI6ImZhbHNlIiwic3ViSWQiOiI0Njc1IiwidXNlclN0YXR1cyI6IjQiLCJQZXJtaXNzaW9uIjpbImNyZWF0ZV9jb250YWN0X2xpc3RzIiwidmlld19jb250YWN0X2xpc3RzIiwidXBkYXRlX2NvbnRhY3RfbGlzdHMiLCJkZWxldGVfY29udGFjdF9saXN0cyIsImNyZWF0ZV9jb250YWN0Iiwidmlld19jb250YWN0IiwidXBkYXRlX2NvbnRhY3QiLCJkZWxldGVfY29udGFjdCIsImNyZWF0ZV9zdXJ2ZXlzIiwidmlld19zdXJ2ZXlzIiwidXBkYXRlX3N1cnZleSIsImRlbGV0ZV9zdXJ2ZXkiLCJjcmVhdGVfc2hhcmVzIiwidmlld19zaGFyZXMiLCJkZWxldGVfc2hhcmVzIiwidmlld193ZWJob29rcyIsImNyZWF0ZV93ZWJob29rcyIsInVwZGF0ZV93ZWJob29rcyIsImRlbGV0ZV93ZWJob29rcyIsInZpZXdfcmVzcG9uc2VzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiXSwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsIm1hbmFnZV9jb250YWN0X2xpc3RzIiwibWFuYWdlX2NvbnRhY3RzIiwibWFuYWdlX3N1cnZleXMiLCJtYW5hZ2Vfd2ViaG9va3MiLCJzdXJ2ZXlfc2hhcmUiLCJ2aWV3X2NvbnRhY3RfbGlzdHMiLCJ2aWV3X2NvbnRhY3RzIiwidmlld19lbWFpbF90ZW1wbGF0ZXMiLCJ2aWV3X3Jlc3BvbnNlcyIsInZpZXdfc3VydmV5cyIsInZpZXdfd2ViaG9va3MiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.v_skKt6vLA52wBb0ZzFNiLAINzROHPpaSPr9t9z19egBsloMeXM2DQKhuw_ocBHN2ujepWXgRgxLDyxWcEVjPUQQjWQFHUXDBhmO8hjx51a--RbEl_-JHV4pNk3gpqeKVlOwv8sPyLSQJDlMaBNgvRYx3Lh0Zkv6gEeSy9fCamiwjN24N0rup51mZbZGsqw316I8IOMICMvTGNbcpbdU9MbXFq_PhsfgXztw3liAiqs6CkY97KotIhXEAI9mnaoZ0KL-hg6boUk2z-deRiOp4ZxjT96bUu7MCtuDTgZrBiw3EyTcq7yAuktxE42LD22RjPZwBou2JoKT2Vvo1L5slQ";
            headers.set("Authorization", "Bearer " + tokenSmsChanel);
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> resSms = restTemplate.exchange(urlSmsChanel, HttpMethod.POST, entity, String.class);
            LOGGER.info(String.format("Return response sensum chanel SMS -> %s", resSms));
            LOGGER.info(String.format("Message send to sensum chanel SMS -> %s", sensumRq));
            // akhir

        }

    }

}
