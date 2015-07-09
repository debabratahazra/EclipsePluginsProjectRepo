package com.odcgroup.t24.version.importer.internal;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.CaseConvention;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;

public class ProcessCaseConvention implements IImportingStep<VersionInfo> {

	private IImportModelReport report;

	private String getTranformingFieldCaseConventionMsg(IModelDetail model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Tranforming Field CaseConvention"); //$NON-NLS-1$
		return b.toString();
	}

	/**
	 * Transform the import caseConvention to the DSl CaseConvention.
	 * 
	 * @param Version
	 * @throws IOException
	 */
	private void transformCaseConventionValuesToDsl(Version version) {
		EList<Field> fieldList = version.getFields();
		for (Field field : fieldList) {
			CaseConvention importCaseConvention = field.getCaseConvention();
			if (importCaseConvention != null) {
				CaseConvention caseConvention = VersionUtils
						.getCaseConvention(importCaseConvention.getName());
				if (caseConvention != null) {
					field.setCaseConvention(caseConvention);
				}
			}
		}
	}

	@Override
	public boolean execute(VersionInfo model, IProgressMonitor monitor) {

		Version version = (Version) model.getModel();
		
		try {
			if (version != null) {
				// converting case conventional value
				if (monitor != null) {
					monitor.subTask(getTranformingFieldCaseConventionMsg(model));
				}
				transformCaseConventionValuesToDsl(version);
			}
		} catch (Exception ex) {
			report.error(model, ex);
			return false;
		}
		
		return true;
		
	}

	public ProcessCaseConvention(IImportModelReport report) {
		this.report = report;
	}

}
