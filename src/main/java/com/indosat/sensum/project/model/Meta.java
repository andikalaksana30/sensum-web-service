package com.indosat.sensum.project.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@XmlRootElement(name = "metadata")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meta {

    private String key;
    private String value;

    @Override
    public String toString() {
        return "{" +
                "key: '" + key + '\'' +
                ", value: '" + value + '\'' +
                '}';
    }
}
