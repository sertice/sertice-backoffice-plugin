package com.sertice.plugins.jenkins.backoffice;

import hudson.EnvVars;
import hudson.Util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sertice.backoffice.entity.EstimateDetail;
import com.sertice.backoffice.entity.EstimateHeader;
import com.sertice.backoffice.entity.EstimateMessage;
import com.sertice.backoffice.entity.MyCompany;
import com.sertice.plugins.jenkins.AbstractBuilder;

public abstract class AbstractEstimateBuilder extends AbstractBuilder {

	protected String fileName;
	protected String documentNo;
	protected String date;
	protected String companyName;
	protected String to;
	protected String title;
	protected String from;
	protected Long discount;
	protected String comment;
	protected List<EstimateDetail> details;

	protected AbstractEstimateBuilder(String fileName, String documentNo,
			String date, String companyName, String to, String title,
			String from, Long discount, String comment,
			List<EstimateDetail> details) {
		super();
		this.fileName = Util.fixEmptyAndTrim(fileName);
		this.documentNo = Util.fixEmptyAndTrim(documentNo);
		this.date = Util.fixEmptyAndTrim(date);
		this.companyName = Util.fixEmptyAndTrim(companyName);
		this.to = Util.fixEmptyAndTrim(to);
		this.title = Util.fixEmptyAndTrim(title);
		this.from = Util.fixEmptyAndTrim(from);
		this.discount = discount;
		this.comment = Util.fixEmptyAndTrim(comment);
		this.details = details;
	}

	public String getFileName() {
		return fileName;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public String getDate() {
		return date;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getTo() {
		return to;
	}

	public String getTitle() {
		return title;
	}

	public String getFrom() {
		return from;
	}

	public Long getDiscount() {
		return discount;
	}

	public String getComment() {
		return comment;
	}

	public List<EstimateDetail> getDetails() {
		return details;
	}

	protected EstimateMessage createEstimateMessage() {
		EstimateMessage message = new EstimateMessage();
		message.setTitle("御 見 積 書");
		message.setDocumentNoType("見積番号");
		message.setGreeting1("下記の通り御見積申し上げます。\n何卒ご用命賜りますようお願い申し上げます。");
		message.setGreeting2("見積有効期限：作成日より2週間");
		message.setTotalType("御見積金額");
		message.setSpecificationsType("見　積　明　細");
		return message;
	}

	protected EstimateMessage createDeliveryMessage() {
		EstimateMessage message = new EstimateMessage();
		message.setTitle("納 品 書");
		message.setDocumentNoType("納品番号");
		message.setGreeting1("平素は格別のご高配を賜り、厚く御礼申し上げます。\n下記の通り納品いたしましたのでご査収ください。");
		message.setGreeting2("");
		message.setTotalType("");
		message.setSpecificationsType("納　品　明　細");
		return message;
	}

	protected EstimateMessage createInvoiceMessage(MyCompany myCompany) {
		EstimateMessage message = new EstimateMessage();
		message.setTitle("御 請 求 書");
		message.setDocumentNoType("請求番号");
		message.setGreeting1("平素は格別のご高配を賜り、厚く御礼申し上げます。\n下記の通り御請求申し上げます。");
		message.setGreeting2(createBankInfo(myCompany));
		message.setTotalType("御請求金額");
		message.setSpecificationsType("請　求　明　細");
		return message;
	}

	private String createBankInfo(MyCompany myCompany) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(myCompany.getBankName())) {
			sb.append(myCompany.getBankName());
			sb.append(" ");
		}
		if (StringUtils.isNotBlank(myCompany.getBranchName())) {
			sb.append(myCompany.getBranchName());
			sb.append(" ");
		}
		if (StringUtils.isNotBlank(myCompany.getAccountType())) {
			sb.append(myCompany.getAccountType());
			sb.append(" ");
		}
		if (StringUtils.isNotBlank(myCompany.getAccountNo())) {
			sb.append(myCompany.getAccountNo());
		}
		if (StringUtils.isNotBlank(myCompany.getAccountName())) {
			sb.append(" \n口座名：");
			sb.append(myCompany.getAccountName());
		}
		return sb.toString();
	}

	protected List<EstimateDetail> createDetails(String details) {
		List<EstimateDetail> result = new ArrayList<EstimateDetail>();
		if (StringUtils.isBlank(details)) {
			result.add(EstimateDetail.BLANK);
			return result;
		}
		for (String line : details.split("\n")) {
			String[] cell = line.split(",");
			if (cell.length >= 3) {
				result.add(new EstimateDetail(cell[0], cell[1], Long
						.valueOf(cell[2])));
			}
		}
		result.add(EstimateDetail.BLANK);
		return result;
	}

	protected List<EstimateDetail> expandedDetails(
			List<EstimateDetail> details, EnvVars envVars) {
		List<EstimateDetail> expandedDetails = new ArrayList<EstimateDetail>();
		for (EstimateDetail detail : details) {
			expandedDetails.add(new EstimateDetail(envVars.expand(detail
					.getNo()), envVars.expand(detail.getSpecifications()),
					detail.getAmount()));
		}
		return expandedDetails;
	}

	protected EstimateHeader createHeader(String filePrefix, EnvVars envVars) {
		EstimateHeader header = new EstimateHeader();
		if (StringUtils.isNotBlank(documentNo)) {
			header.setDocumentNo(envVars.expand(documentNo).trim());
		}
		if (StringUtils.isNotBlank(date)) {
			header.setDate(envVars.expand(date).trim());
		}
		if (StringUtils.isNotBlank(companyName)) {
			header.setCompanyName(envVars.expand(companyName).trim());
		}
		if (StringUtils.isNotBlank(to)) {
			header.setTo(envVars.expand(to).trim());
		}
		if (StringUtils.isNotBlank(title)) {
			header.setTitle(envVars.expand(title).trim());
		}
		if (StringUtils.isNotBlank(from)) {
			header.setFrom(envVars.expand(from).trim());
		}
		header.setDiscount(discount);
		if (StringUtils.isNotBlank(comment)) {
			header.setComment(envVars.expand(comment).trim());
		}
		if (StringUtils.isBlank(fileName)) {
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(header.getCompanyName())) {
				sb.append(header.getCompanyName());
				sb.append("様_");
			}
			sb.append(filePrefix);
			if (StringUtils.isNotBlank(header.getDocumentNo())) {
				sb.append("(");
				sb.append(header.getDocumentNo());
				sb.append(")");
			}
			sb.append(".pdf");
			header.setFileName(sb.toString());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(envVars.expand(fileName).trim());
			sb.append(".pdf");
			header.setFileName(sb.toString());
		}
		return header;
	}

	protected void tally(List<EstimateDetail> expandedDetails,
			EstimateHeader header) {
		Long subTotal = 0L;
		for (EstimateDetail detail : expandedDetails) {
			subTotal += detail.getAmount();
		}
		header.setSubTotal(subTotal);
		header.setTax((long) ((subTotal - discount) * 0.05));
		header.setTotal((long) ((subTotal - discount) * 1.05));
	}

}
