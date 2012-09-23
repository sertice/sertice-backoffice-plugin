package com.sertice.plugins.jenkins.backoffice;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Launcher;
import hudson.Util;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import com.sertice.backoffice.entity.CoverLetterDetail;
import com.sertice.backoffice.entity.CoverLetterHeader;
import com.sertice.plugins.jenkins.AbstractBuilder;
import com.sertice.plugins.jenkins.utils.DateUtil;

public class CoverLetterBuilder extends AbstractBuilder {

	private String fileName;
	private String date;
	private String prefectureCode;
	private String address1;
	private String address2;
	private String companyName;
	private String to;
	private String from;
	private List<CoverLetterDetail> details;

	@DataBoundConstructor
	public CoverLetterBuilder(String fileName, String date,
			String prefectureCode, String address1, String address2,
			String companyName, String to, String from,
			List<CoverLetterDetail> details) {
		super();
		this.fileName = Util.fixEmptyAndTrim(fileName);
		this.date = Util.fixEmptyAndTrim(date);
		this.prefectureCode = Util.fixEmptyAndTrim(prefectureCode);
		this.address1 = Util.fixEmptyAndTrim(address1);
		this.address2 = Util.fixEmptyAndTrim(address2);
		this.companyName = Util.fixEmptyAndTrim(companyName);
		this.to = Util.fixEmptyAndTrim(to);
		this.from = Util.fixEmptyAndTrim(from);
		this.details = details;
	}

	public String getFileName() {
		return fileName;
	}

	public String getDate() {
		return date;
	}

	public String getPrefectureCode() {
		return prefectureCode;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public List<CoverLetterDetail> getDetails() {
		return details;
	}

	private CoverLetterHeader createHeader(EnvVars envVars) {
		CoverLetterHeader header = new CoverLetterHeader();
		if (StringUtils.isBlank(date)) {
			header.setDate(DateUtil.today().trim());
		} else {
			header.setDate(envVars.expand(date).trim());
		}
		if (StringUtils.isNotBlank(prefectureCode)) {
			header.setPrefectureCode(envVars.expand(prefectureCode).trim());
		}
		if (StringUtils.isNotBlank(address1)) {
			header.setAddress1(envVars.expand(address1).trim());
		}
		if (StringUtils.isNotBlank(address2)) {
			header.setAddress2(envVars.expand(address2).trim());
		}
		if (StringUtils.isNotBlank(companyName)) {
			header.setCompanyName(envVars.expand(companyName).trim());
		}
		if (StringUtils.isNotBlank(to)) {
			header.setTo(envVars.expand(to).trim());
		}
		if (StringUtils.isNotBlank(from)) {
			header.setFrom(envVars.expand(from).trim());
		}
		if (StringUtils.isBlank(fileName)) {
			StringBuilder sb = new StringBuilder();
			sb.append("送付状");
			if (StringUtils.isNotBlank(header.getTo())) {
				sb.append("_");
				sb.append(header.getTo());
				sb.append(" 様");
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

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {

		setMyCompany((BackOfficeProject) build.getProject());

		EnvVars envVars = createEnvVars(build, listener);

		CoverLetterHeader header = createHeader(envVars);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("COMPANY", getMyCompany());
		params.put("HEADER", header);

		String detailsParameter = build.getBuildVariables().get("DETAILS");

		List<CoverLetterDetail> expandedDetails;
		if (StringUtils.isNotBlank(detailsParameter)) {
			expandedDetails = expandedDetails(createDetails(detailsParameter),
					envVars);
		} else {
			expandedDetails = expandedDetails(details, envVars);
		}

		try {
			getReportGenerator().generateReportToArtifactsDir(
					CoverLetterHeader.JRXML, header.getFileName(), build,
					expandedDetails, params);
			return true;
		} catch (JRException e) {
			e.printStackTrace(listener.getLogger());
			return false;
		}
	}

	private List<CoverLetterDetail> expandedDetails(
			List<CoverLetterDetail> details, EnvVars envVars) {
		List<CoverLetterDetail> result = new ArrayList<CoverLetterDetail>();
		for (CoverLetterDetail detail : details) {
			result.add(new CoverLetterDetail(envVars.expand(detail.getTitle()),
					detail.getCount()));
		}
		return result;
	}

	private List<CoverLetterDetail> createDetails(String details) {
		List<CoverLetterDetail> result = new ArrayList<CoverLetterDetail>();
		if (StringUtils.isBlank(details)) {
			result.add(CoverLetterDetail.BLANK);
			return result;
		}
		for (String line : details.split("\n")) {
			String[] cell = line.split(",");
			if (cell.length >= 2) {
				result.add(new CoverLetterDetail(cell[0], Integer
						.parseInt(cell[1])));
			}
		}
		result.add(CoverLetterDetail.BLANK);
		return result;
	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension(ordinal = 9000)
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Builder> {

		public DescriptorImpl() {
			super(CoverLetterBuilder.class);
			super.load();
		}

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> jobType) {
			return BackOfficeProject.class.isAssignableFrom(jobType);
		}

		@Override
		public String getDisplayName() {
			return "送付状を作る";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData)
				throws FormException {
			req.bindJSON(this, formData);
			save();
			return super.configure(req, formData);
		}

		private List<CoverLetterDetail> getDefaultDetails() {
			List<CoverLetterDetail> result = new ArrayList<CoverLetterDetail>();
			result.add(CoverLetterDetail.BLANK);
			return result;
		}

		public List<CoverLetterDetail> getDetails(CoverLetterBuilder instance) {
			if (instance == null) {
				return getDefaultDetails();
			}
			return instance.getDetails();
		}

	}

}
