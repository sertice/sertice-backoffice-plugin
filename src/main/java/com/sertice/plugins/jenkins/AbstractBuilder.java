package com.sertice.plugins.jenkins;

import hudson.EnvVars;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.Builder;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sertice.backoffice.entity.MyCompany;
import com.sertice.plugins.jenkins.backoffice.BackOfficeProject;
import com.sertice.plugins.jenkins.backoffice.BackOfficeProject.DescriptorImpl;
import com.sertice.plugins.jenkins.utils.ReportGenerator;

public abstract class AbstractBuilder extends Builder {

	private ReportGenerator reportGenerator;
	private MyCompany myCompany;

	protected AbstractBuilder() {
		reportGenerator = new ReportGenerator();
		myCompany = new MyCompany();
	}

	protected ReportGenerator getReportGenerator() {
		return reportGenerator;
	}

	protected MyCompany getMyCompany() {
		return myCompany;
	}

	protected void setMyCompany(BackOfficeProject project) {
		DescriptorImpl descriptor = project.getDescriptor();
		if (StringUtils.isNotBlank(descriptor.getMyCompanyName())) {
			myCompany.setCompanyName(descriptor.getMyCompanyName().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyPrefectureCode())) {
			myCompany
					.setPrefectureCode(descriptor.getMyPrefectureCode().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyAddress1())) {
			myCompany.setAddress1(descriptor.getMyAddress1().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyAddress2())) {
			myCompany.setAddress2(descriptor.getMyAddress2().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyTel())) {
			myCompany.setTel(descriptor.getMyTel().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyFax())) {
			myCompany.setFax(descriptor.getMyFax().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyHomepageUrl())) {
			myCompany.setHomepageUrl(descriptor.getMyHomepageUrl().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyMailAddress())) {
			myCompany.setMailAddress(descriptor.getMyMailAddress().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyPresidentName())) {
			myCompany.setPresidentName(descriptor.getMyPresidentName().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyLogoPath())) {
			myCompany.setLogoPath(descriptor.getMyLogoPath().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyStampPath())) {
			myCompany.setStampPath(descriptor.getMyStampPath().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyBankName())) {
			myCompany.setBankName(descriptor.getMyBankName().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyBranchName())) {
			myCompany.setBranchName(descriptor.getMyBranchName().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyAccountType())) {
			myCompany.setAccountType(descriptor.getMyAccountType().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyAccountNo())) {
			myCompany.setAccountNo(descriptor.getMyAccountNo().trim());
		}
		if (StringUtils.isNotBlank(descriptor.getMyAccountName())) {
			myCompany.setAccountName(descriptor.getMyAccountName().trim());
		}
	}

	protected EnvVars createEnvVars(AbstractBuild<?, ?> build,
			BuildListener listener) throws InterruptedException, IOException {
		EnvVars envVars = build.getEnvironment(listener);
		for (Map.Entry<String, String> e : build.getBuildVariables().entrySet()) {
			envVars.put(e.getKey(), e.getValue());
		}
		return envVars;
	}

}
