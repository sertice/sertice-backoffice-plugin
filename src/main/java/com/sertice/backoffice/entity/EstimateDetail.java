package com.sertice.backoffice.entity;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

public class EstimateDetail implements Serializable {

	private static final long serialVersionUID = 7991935640954833102L;

	public static final EstimateDetail BLANK = new EstimateDetail("", "", 0L);

	private String no;

	private String specifications;

	private Long amount;

	public EstimateDetail() {
	}

	@DataBoundConstructor
	public EstimateDetail(String no, String specifications, Long amount) {
		this.no = no;
		this.specifications = specifications;
		this.amount = amount;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
