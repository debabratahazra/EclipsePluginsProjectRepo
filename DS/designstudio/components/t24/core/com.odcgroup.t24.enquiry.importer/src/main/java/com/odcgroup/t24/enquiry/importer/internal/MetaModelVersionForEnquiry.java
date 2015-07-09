package com.odcgroup.t24.enquiry.importer.internal;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.IModelDetail;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.workbench.core.OfsCore;

/**
 * @author satishnangi
 *
 */
public class MetaModelVersionForEnquiry implements IImportingStep<EnquiryInfo> {

	private IImportModelReport report;

	private String getMetaModelVersionSettingMsg(IModelDetail model) {
		StringBuffer b = new StringBuffer(64);
		b.append(model.getModelType());
		b.append(": "); //$NON-NLS-1$
		b.append(model.getDescription());
		b.append(": "); //$NON-NLS-1$
		b.append("Setting Metamodel enquiry"); //$NON-NLS-1$
		return b.toString();
	}

	@Override
	public boolean execute(EnquiryInfo model, IProgressMonitor monitor) {
		Enquiry enquiry = (Enquiry) model.getModel();
		try {
			if (enquiry != null) {
				// setting the metamodel enquiry.
				if (monitor != null) {
					monitor.subTask(getMetaModelVersionSettingMsg(model));
				}
				enquiry.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion("enquiry"));
			}
		} catch (Exception ex) {
			report.error(model, ex);
			return false;
		}
		
		return true;
		
	}

	public MetaModelVersionForEnquiry(IImportModelReport report) {
		this.report = report;
	}
}
