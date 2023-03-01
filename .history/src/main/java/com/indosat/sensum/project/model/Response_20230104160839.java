package com.indosat.sensum.project.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sensum")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
	private String status;
	private String description;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "{" +
				"status: '" + status + '\'' +
				", description: '" + description + '\'' +
				'}';
	}
}
