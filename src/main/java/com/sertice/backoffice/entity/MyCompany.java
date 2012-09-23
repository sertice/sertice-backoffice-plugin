package com.sertice.backoffice.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class MyCompany implements Serializable {

	private static final long serialVersionUID = -6727968561723786743L;

	private String companyName;
	private String prefectureCode;
	private String address1;
	private String address2;
	private String tel;
	private String fax;
	private String homepageUrl;
	private String mailAddress;
	private String presidentName;
	private String logoPath;
	private String stampPath;
	private String bankName;
	private String branchName;
	private String accountType;
	private String accountNo;
	private String accountName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPrefectureCode() {
		return prefectureCode;
	}

	public void setPrefectureCode(String prefectureCode) {
		this.prefectureCode = prefectureCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHomepageUrl() {
		return homepageUrl;
	}

	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPresidentName() {
		return presidentName;
	}

	public void setPresidentName(String presidentName) {
		this.presidentName = presidentName;
	}

	public String getLogoPath() {
		if (StringUtils.isBlank(logoPath)) {
			return "com/sertice/images/logo_blank.png";
		}
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getStampPath() {
		if (StringUtils.isBlank(stampPath)) {
			return "com/sertice/images/stamp_blank.png";
		}
		return stampPath;
	}

	public void setStampPath(String stampPath) {
		this.stampPath = stampPath;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
