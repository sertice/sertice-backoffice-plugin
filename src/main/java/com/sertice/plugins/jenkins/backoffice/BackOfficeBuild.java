package com.sertice.plugins.jenkins.backoffice;

import hudson.model.Build;

import java.io.File;
import java.io.IOException;

public class BackOfficeBuild extends Build<BackOfficeProject, BackOfficeBuild> {

	public BackOfficeBuild(BackOfficeProject project) throws IOException {
		super(project);
	}

	public BackOfficeBuild(BackOfficeProject project, File buildDir)
			throws IOException {
		super(project, buildDir);
	}

}
