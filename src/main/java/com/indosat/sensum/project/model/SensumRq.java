package com.indosat.sensum.project.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@XmlRootElement(name = "sensum")
@XmlAccessorType(XmlAccessType.FIELD)
public class SensumRq {

    private String orderType;
    private String channelType;
    private List<Contact> contactInfo = new ArrayList<>();
    private List<Meta> metadata = new ArrayList<>();
    private Integer delayMinutes;

//    @Override
//    public String toString() {
//        return "{" +
//                "channelType: " + channelType +
//                ", contactInfo: " + contactInfo +
//                ", metadata: " + metadata +
//                ", delayMinutes: " + delayMinutes +
//                '}';
//    }


    @Override
    public String toString() {
        return "{" +
                "channelType: '" + channelType + '\'' +
                ", contactInfo: " + contactInfo +
                ", metadata: " + metadata +
                ", delayMinutes: " + delayMinutes +
                '}';
    }

}
