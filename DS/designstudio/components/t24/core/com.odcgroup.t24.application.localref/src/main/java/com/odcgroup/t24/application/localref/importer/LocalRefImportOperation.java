package com.odcgroup.t24.application.localref.importer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportOperation;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.internal.ConvertXmlToEmfResource;
import com.odcgroup.t24.common.importer.internal.LoadXmlFromServer;
import com.odcgroup.t24.common.importer.internal.T24ModelImportOperation;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.LocalRefDetail;

/**
 * @author ssreekanth
 *
 */
public class LocalRefImportOperation extends T24ModelImportOperation<LocalRefInfo, LocalRefDetail> implements IImportOperation {
	
	public static final String DESTINATION_FOLDER = "/domain/" + LocalRefUtils.LOCAL_FIELD_PACKAGE+ "/";
	
	private IExternalLoader loader;
	
	protected IFolder getLocalRefFolder() {
		return getContainer().getParent().getFolder(new Path(DESTINATION_FOLDER));
	}
	
	@Override
	protected String getImportMessage() {
		return "Processing LocalRefs Import";
	}
	
	@Override
	protected LocalRefInfo makeModelInfoFromDetail(LocalRefDetail detail) {
		return new LocalRefInfo(detail);
	}
	
	@Override
	protected List<IImportingStep<LocalRefInfo>> getImportingSteps(IImportModelReport report) {
		SaveLocalRefs localRef = new SaveLocalRefs(); 
		List<IImportingStep<LocalRefInfo>> steps = new ArrayList<IImportingStep<LocalRefInfo>>();
		IFolder folder = getInDebug() ? getFolder() : null;
		localRef.setInput(report, getLocalRefFolder());
		steps.add(new LoadXmlFromServer<LocalRefInfo>(report, this.loader, folder));
		steps.add(new ConvertXmlToEmfResource<LocalRefInfo>(report, new ResourceSetImpl()));
		steps.add(new ProcessLocalRefEMFReferenceScheme(report));
		steps.add(localRef);
		return steps;
	}
	
	public void setExternalLoader(IExternalLoader loader) {
		this.loader = loader;
	}

}
