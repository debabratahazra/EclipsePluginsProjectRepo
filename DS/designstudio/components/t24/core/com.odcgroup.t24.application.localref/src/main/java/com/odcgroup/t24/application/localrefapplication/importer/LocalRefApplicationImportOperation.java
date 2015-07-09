package com.odcgroup.t24.application.localrefapplication.importer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.odcgroup.t24.application.localref.importer.LocalRefUtils;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportOperation;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.internal.ConvertXmlToEmfResource;
import com.odcgroup.t24.common.importer.internal.LoadXmlFromServer;
import com.odcgroup.t24.common.importer.internal.T24ModelImportOperation;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class LocalRefApplicationImportOperation extends T24ModelImportOperation<LocalRefApplicationInfo, LocalRefApplicationDetail> implements IImportOperation {
	
	public static final String DESTINATION_FOLDER = "/domain/" + LocalRefUtils.LOCAL_REF_APPLICATION_PACKAGE + "/";
	
	private IExternalLoader loader;
	

	protected IFolder getLocalRefFolder() {
		return getContainer().getParent().getFolder(new Path(DESTINATION_FOLDER));
	}

	@Override
	protected List<IImportingStep<LocalRefApplicationInfo>> getImportingSteps(
			IImportModelReport report) {
		
		SaveLocalRefApplication localRefApplication = new SaveLocalRefApplication(); 
		List<IImportingStep<LocalRefApplicationInfo>> steps = new ArrayList<IImportingStep<LocalRefApplicationInfo>>();
		IFolder folder = getInDebug() ? getFolder() : null;
		localRefApplication.setInput(report, getLocalRefFolder());
		steps.add(new LoadXmlFromServer<LocalRefApplicationInfo>(report, this.loader, folder));
		steps.add(new ConvertXmlToEmfResource<LocalRefApplicationInfo>(report, new ResourceSetImpl()));
		steps.add(new ProcessLocalRefEMFReferenceScheme(report));
		steps.add(localRefApplication);
		return steps;
	}
	
	@Override
	protected IImportingStep<LocalRefApplicationInfo> getFinalStep(
			IImportModelReport report) {
		// TODO Auto-generated method stub
		return super.getFinalStep(report);
	}

	@Override
	protected LocalRefApplicationInfo makeModelInfoFromDetail(
			LocalRefApplicationDetail detail) {
		return new LocalRefApplicationInfo(detail);
	}

	@Override
	protected String getImportMessage() {
		return "Processing Local Ref Applications Import";
	}
	
	public void setExternalLoader(IExternalLoader loader) {
		this.loader = loader;
	}
}
