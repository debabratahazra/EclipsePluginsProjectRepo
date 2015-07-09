package com.odcgroup.t24.version.importer.internal;

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
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.VersionDetail;

public class VersionImportOperation extends T24ModelImportOperation<VersionInfo, VersionDetail> {

	private IExternalLoader loader;
	
	@Override
	protected String getImportMessage() {
		return "Processing Screens Import";
	}
	
	@Override
	protected VersionInfo makeModelInfoFromDetail(VersionDetail detail) {
		return new VersionInfo(detail);
	}
	
	protected List<IImportingStep<VersionInfo>> getImportingSteps(IImportModelReport report) {
		List<IImportingStep<VersionInfo>> steps = new ArrayList<IImportingStep<VersionInfo>>();
		IFolder folder = getInDebug() ? getFolder() : null; 
		steps.add(new LoadXmlFromServer<VersionInfo>(report, this.loader, folder));
		steps.add(new ConvertXmlToEmfResource<VersionInfo>(report, getResourceSet()));
		steps.add(new ProcessVersionReferenceScheme(report));
		steps.add(new ProcessEmfReferences<VersionInfo>(report, getNameURISwapper()));
		steps.add(new ProcessCaseConvention(report));
		steps.add(new SetMetaModelVersion(report));
		steps.add(new ProcessVersionShortNames(report));
		steps.add(new SaveEmfResource<VersionInfo>(report, getEIO(), getResourceSet(), getFolder())); 
		steps.add(new ProcessT24Names(report));
//		{
//			protected IFolder getDestinationFolder(VersionInfo model) {
//				Version version = (Version)model.getModel();
//				String path = VersionUtils.getVersionApplicationName(version).replace(":","/");
//				return getRootFolder().getFolder(new Path(path));
//			}
//		});
		return steps;
	}
	
	public void setExternalLoader(IExternalLoader loader) {
		this.loader = loader;
	}

}
