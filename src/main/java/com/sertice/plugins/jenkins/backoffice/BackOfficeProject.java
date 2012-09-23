package com.sertice.plugins.jenkins.backoffice;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.TopLevelItem;
import hudson.model.Project;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

public class BackOfficeProject extends
		Project<BackOfficeProject, BackOfficeBuild> implements TopLevelItem {

	public BackOfficeProject(ItemGroup parent, String name) {
		super(parent, name);
	}

	@Override
	protected Class<BackOfficeBuild> getBuildClass() {
		return BackOfficeBuild.class;
	}

	public DescriptorImpl getDescriptor() {
		return DESCRIPTOR;
	}

	@Extension(ordinal = 9000)
	public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

	public static final class DescriptorImpl extends AbstractProjectDescriptor {

		private String myCompanyName;
		private String myPrefectureCode;
		private String myAddress1;
		private String myAddress2;
		private String myTel;
		private String myFax;
		private String myHomepageUrl;
		private String myMailAddress;
		private String myPresidentName;
		private String myLogoPath;
		private String myStampPath;
		private String myBankName;
		private String myBranchName;
		private String myAccountType;
		private String myAccountNo;
		private String myAccountName;

		public DescriptorImpl() {
			super.load();
		}

		@Override
		public String getDisplayName() {
			return "バックオフィス業務の自動化";
		}

		@Override
		public TopLevelItem newInstance(ItemGroup parent, String name) {
			return new BackOfficeProject(parent, name);
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData)
				throws FormException {
			myCompanyName = formData.getString("myCompanyName");
			myPrefectureCode = formData.getString("myPrefectureCode");
			myAddress1 = formData.getString("myAddress1");
			myAddress2 = formData.getString("myAddress2");
			myTel = formData.getString("myTel");
			myFax = formData.getString("myFax");
			myHomepageUrl = formData.getString("myHomepageUrl");
			myMailAddress = formData.getString("myMailAddress");
			myPresidentName = formData.getString("myPresidentName");
			myLogoPath = formData.getString("myLogoPath");
			myStampPath = formData.getString("myStampPath");
			myBankName = formData.getString("myBankName");
			myBranchName = formData.getString("myBranchName");
			myAccountType = formData.getString("myAccountType");
			myAccountNo = formData.getString("myAccountNo");
			myAccountName = formData.getString("myAccountName");

			req.bindJSON(this, formData);
			save();
			return super.configure(req, formData);
		}

		public String getMyCompanyName() {
			return myCompanyName;
		}

		public String getMyPrefectureCode() {
			return myPrefectureCode;
		}

		public String getMyAddress1() {
			return myAddress1;
		}

		public String getMyAddress2() {
			return myAddress2;
		}

		public String getMyTel() {
			return myTel;
		}

		public String getMyFax() {
			return myFax;
		}

		public String getMyHomepageUrl() {
			return myHomepageUrl;
		}

		public String getMyMailAddress() {
			return myMailAddress;
		}

		public String getMyPresidentName() {
			return myPresidentName;
		}

		public String getMyLogoPath() {
			return myLogoPath;
		}

		public String getMyStampPath() {
			return myStampPath;
		}

		public String getMyBankName() {
			return myBankName;
		}

		public String getMyBranchName() {
			return myBranchName;
		}

		public String getMyAccountType() {
			return myAccountType;
		}

		public String getMyAccountNo() {
			return myAccountNo;
		}

		public String getMyAccountName() {
			return myAccountName;
		}

	}

}
