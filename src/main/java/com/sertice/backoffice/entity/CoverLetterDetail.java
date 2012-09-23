package com.sertice.backoffice.entity;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

public class CoverLetterDetail implements Serializable {

	private static final long serialVersionUID = -2659974901856141778L;

	public static final CoverLetterDetail BLANK = new CoverLetterDetail("", 1);

	private String title;
	private Integer count;

	public CoverLetterDetail() {
	}

	@DataBoundConstructor
	public CoverLetterDetail(String title, Integer count) {
		this.title = title;
		this.count = count;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
