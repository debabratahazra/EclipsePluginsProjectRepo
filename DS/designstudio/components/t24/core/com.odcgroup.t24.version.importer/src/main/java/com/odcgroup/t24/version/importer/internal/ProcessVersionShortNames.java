package com.odcgroup.t24.version.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;

public class ProcessVersionShortNames implements IImportingStep<VersionInfo> {
	
	private IImportModelReport report;

	public ProcessVersionShortNames(IImportModelReport report) {
		this.report = report;
	}

	@Override
	public boolean execute(VersionInfo model, IProgressMonitor monitor) {
		Version version = (Version) model.getModel();
		if (version != null) {
			try {
				// convert the special characters to underscore in version short name.
				if (monitor != null) {
					monitor.subTask(getVersionShortNameConversionMsg(model));
				}
				VersionUtils.convertSpecialCharInShortName(version);
			} catch(Exception ex) {
				report.error(model, ex);
				return false;
			}
		}
		return true;
	}

	private String getVersionShortNameConversionMsg(VersionInfo model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Converting special charatcter in version short name."); //$NON-NLS-1$
		return b.toString();
	}

}
