package com.odcgroup.t24.enquiry.importer.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.internal.ConvertXmlToEmfResource;
import com.odcgroup.t24.common.importer.internal.LoadXmlFromServer;
import com.odcgroup.t24.common.importer.internal.ProcessEmfReferences;
import com.odcgroup.t24.common.importer.internal.SaveEmfResource;
import com.odcgroup.t24.common.importer.internal.T24ModelImportOperation;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;

public class EnquiryImportOperation extends T24ModelImportOperation<EnquiryInfo, EnquiryDetail> {
	
	private IExternalLoader loader;
	
	@Override
	protected String getImportMessage() {
		return "Processing Enquiries Import";
	}
	
	@Override
	protected EnquiryInfo makeModelInfoFromDetail(EnquiryDetail detail) {
		return new EnquiryInfo(detail);
	}
	
	@Override
	protected List<IImportingStep<EnquiryInfo>> getImportingSteps(IImportModelReport report) {
		List<IImportingStep<EnquiryInfo>> steps = new ArrayList<IImportingStep<EnquiryInfo>>();
		IFolder folder = getInDebug() ? getFolder() : null; 
		steps.add(new LoadXmlFromServer<EnquiryInfo>(report, this.loader, folder));
		steps.add(new ModifyXml<EnquiryInfo>(report));
		steps.add(new ConvertXmlToEmfResource<EnquiryInfo>(report, getResourceSet()));
		steps.add(new ProcessEmfReferences<EnquiryInfo>(report, getNameURISwapper()));
		steps.add(new MetaModelVersionForEnquiry(report));
		//TODO: DS-8293 - commenting out the field sorting for time-being 
		//steps.add(new RelativeFieldArrangementForEnquiry(report));
		steps.add(new SaveEmfResource<EnquiryInfo>(report, getEIO(), getResourceSet(), getFolder()));
		return steps;
	}
	
	public void setExternalLoader(IExternalLoader loader) {
		this.loader = loader;
	}
}
