package com.odcgroup.t24.version.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.version.utils.VersionUtils;
import com.odcgroup.t24.version.versionDSL.Version;

public class SetMetaModelVersion implements IImportingStep<VersionInfo> {

	private IImportModelReport report;

	private String getMetaModelVersionSettingMsg(IModelDetail model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Setting Metamodel version"); //$NON-NLS-1$
		return b.toString();
	}

	@Override
	public boolean execute(VersionInfo model, IProgressMonitor monitor) {
		
		Version version = (Version) model.getModel();

		try {
			if (version != null) {
				// setting the metamodel version.
				if (monitor != null) {
					monitor.subTask(getMetaModelVersionSettingMsg(model));
				}
				VersionUtils.setVersionMetaModelVersion(version);
			}
		} catch (Exception ex) {
			report.error(model, ex);
			return false;
		}
		
		return true;
		
	}

	public SetMetaModelVersion(IImportModelReport report) {
		this.report = report;
	}

}
