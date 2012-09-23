package com.sertice.plugins.jenkins.backoffice;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Launcher;
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

import com.sertice.backoffice.entity.EstimateDetail;
import com.sertice.backoffice.entity.EstimateHeader;

public class EstimateBuilder extends AbstractEstimateBuilder {

	@DataBoundConstructor
	public EstimateBuilder(String fileName, String documentNo, String date,
			String companyName, String to, String title, String from,
			Long discount, String comment, List<EstimateDetail> details) {
		super(fileName, documentNo, date, companyName, to, title, from,
				discount, comment, details);
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {

		setMyCompany((BackOfficeProject) build.getProject());

		EnvVars envVars = createEnvVars(build, listener);

		EstimateHeader header = createHeader("御見積書", envVars);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("COMPANY", getMyCompany());
		params.put("HEADER", header);
		params.put("MESSAGE", createEstimateMessage());

		List<EstimateDetail> expandedDetails;
		String detailsParameter = build.getBuildVariables().get("DETAILS");
		if (StringUtils.isNotBlank(detailsParameter)) {
			expandedDetails = expandedDetails(createDetails(detailsParameter),
					envVars);
		} else {
			expandedDetails = expandedDetails(details, envVars);
		}

		tally(expandedDetails, header);

		try {
			getReportGenerator().generateReportToArtifactsDir(
					EstimateHeader.JRXML, header.getFileName(), build,
					expandedDetails, params);
			return true;
		} catch (JRException e) {
			e.printStackTrace(listener.getLogger());
			return false;
		}

	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension(ordinal = 10000)
	public static final class DescriptorImpl extends
			BuildStepDescriptor<Builder> {

		public DescriptorImpl() {
			super(EstimateBuilder.class);
			super.load();
		}

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> jobType) {
			return BackOfficeProject.class.isAssignableFrom(jobType);
		}

		@Override
		public String getDisplayName() {
			return "見積書を作る";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData)
				throws FormException {
			req.bindJSON(this, formData);
			save();
			return super.configure(req, formData);
		}

		private List<EstimateDetail> getDefaultDetails() {
			List<EstimateDetail> result = new ArrayList<EstimateDetail>();
			result.add(EstimateDetail.BLANK);
			return result;
		}

		public List<EstimateDetail> getDetails(AbstractEstimateBuilder instance) {
			if (instance == null) {
				return getDefaultDetails();
			}
			return instance.getDetails();
		}

	}

}
