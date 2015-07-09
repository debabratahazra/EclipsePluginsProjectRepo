package com.odcgroup.t24.version.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;

public class ProcessVersionReferenceScheme implements IImportingStep<VersionInfo> {
	
	
	private IImportModelReport report;
	
	public ProcessVersionReferenceScheme(IImportModelReport report){
		this.report = report;
	}

	@Override
	public boolean execute(VersionInfo model, IProgressMonitor monitor) {
		Version version = (Version) model.getModel();
		try {
			if (version != null) {
				// converting t24 scheme to name scheme.
				if (monitor != null) {
					monitor.subTask(getTransformingVersionRefMsg(model));
				}
				VersionUtils.replaceRefVersionT24SchemeToNameScheme(version);
			}
		} catch (Exception ex) {
			report.error(model, ex);
			return false;
		}
		return true;
	}

	private String getTransformingVersionRefMsg(VersionInfo model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Tranforming Reference Version Scheme"); //$NON-NLS-1$
		return b.toString();
		
	}

}
