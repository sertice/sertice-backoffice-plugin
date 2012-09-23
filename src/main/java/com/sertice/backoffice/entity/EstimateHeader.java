package com.sertice.backoffice.entity;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class EstimateHeader implements Serializable {

	private static final long serialVersionUID = 2588379368404561436L;

	public static final String JRXML = "com/sertice/backoffice/report/Estimate.jrxml";

	private static final FastDateFormat formatter = FastDateFormat
			.getInstance("yyyy年MM月dd日");

	private String fileName;
	private String documentNo;
	private String date;
	private String companyName;
	private String to;
	private String title;
	private String from;
	private Long total;
	private Long subTotal;
	private Long discount;
	private Long tax;
	private String comment;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDate() {
		if (StringUtils.isBlank(date)) {
			Calendar calendar = Calendar.getInstance();
			return formatter.format(calendar.getTime());
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Long subTotal) {
		this.subTotal = subTotal;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
