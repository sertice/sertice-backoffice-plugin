package com.sertice.backoffice.entity;

import java.io.Serializable;

public class EstimateMessage implements Serializable {

	private static final long serialVersionUID = 3481880228373577837L;

	private String title;
	private String documentNoType;
	private String greeting1;
	private String greeting2;
	private String totalType;
	private String specificationsType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocumentNoType() {
		return documentNoType;
	}

	public void setDocumentNoType(String documentNoType) {
		this.documentNoType = documentNoType;
	}

	public String getGreeting1() {
		return greeting1;
	}

	public void setGreeting1(String greeting1) {
		this.greeting1 = greeting1;
	}

	public String getGreeting2() {
		return greeting2;
	}

	public void setGreeting2(String greeting2) {
		this.greeting2 = greeting2;
	}

	public String getTotalType() {
		return totalType;
	}

	public void setTotalType(String totalType) {
		this.totalType = totalType;
	}

	public String getSpecificationsType() {
		return specificationsType;
	}

	public void setSpecificationsType(String specificationsType) {
		this.specificationsType = specificationsType;
	}

}
