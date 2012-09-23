package com.sertice.plugins.jenkins.utils;

import hudson.model.AbstractBuild;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportGenerator {

	public void generateReportToArtifactsDir(String jrxmlPath,
			String outputFileName, AbstractBuild<?, ?> build,
			List<?> dataSource, Map<String, Object> params) throws JRException {
		JRDataSource ds = new JRBeanCollectionDataSource(dataSource);

		generateReportToArtifactsDir(jrxmlPath, outputFileName, build, params,
				ds);
	}

	public void generateReportToArtifactsDir(String jrxmlPath,
			String outputFileName, AbstractBuild<?, ?> build,
			Map<String, Object> params) throws JRException {
		generateReportToArtifactsDir(jrxmlPath, outputFileName, build, params,
				new JREmptyDataSource());
	}

	private void generateReportToArtifactsDir(String jrxmlPath,
			String outputFileName, AbstractBuild<?, ?> build,
			Map<String, Object> params, JRDataSource ds) throws JRException {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(
				jrxmlPath);
		JasperReport jasperReport = JasperCompileManager.compileReport(stream);

		File dir = build.getArtifactsDir();
		dir.mkdirs();

		JasperPrint print = JasperFillManager.fillReport(jasperReport, params,
				ds);
		JasperExportManager.exportReportToPdfFile(print, new File(dir,
				outputFileName).getPath());
	}

}
