package com.odcgroup.t24.aa.product.importer.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;

import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.IImportOperation;
import com.odcgroup.t24.common.importer.IImportingStep;
import com.odcgroup.t24.common.importer.internal.LoadXmlFromServer;
import com.odcgroup.t24.common.importer.internal.T24ModelImportOperation;
import com.odcgroup.t24.server.external.model.AAProductsDetails;
import com.odcgroup.t24.server.external.model.IExternalLoader;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAProductsImportOperation extends T24ModelImportOperation<AAProductsInfo, AAProductsDetails> implements IImportOperation {
	
	public static final String DESTINATION_FOLDER = "products";
	public static final String DOMAIN_FILENAME = "ProductLines.domain";
	
	private IExternalLoader loader;
	
	protected IFolder getFolder() {
		return getContainer().getParent().getFolder(new Path("domain/" + DESTINATION_FOLDER + "/"));
	}

	@Override
	protected List<IImportingStep<AAProductsInfo>> getImportingSteps(IImportModelReport report) {
		SaveAAProducts saveAAProducts = new SaveAAProducts();
		List<IImportingStep<AAProductsInfo>> steps = new ArrayList<IImportingStep<AAProductsInfo>>();
		IFolder folder = getInDebug() ? getFolder() : null; 
		saveAAProducts.setInput(report, getFolder());
		steps.add(new LoadXmlFromServer<AAProductsInfo>(report, this.loader, folder));
		steps.add(saveAAProducts);
		return steps;
	}

	@Override
	protected AAProductsInfo makeModelInfoFromDetail(AAProductsDetails detail) {
		return new AAProductsInfo(detail);
	}

	@Override
	protected String getImportMessage() {
		return "Processing T24 AA Products Import";
	}
	
	@Override
	protected IImportingStep<AAProductsInfo> getFinalStep(IImportModelReport report) {
		return super.getFinalStep(report);
	}
	
	public void setExternalLoader(IExternalLoader loader) {
		this.loader = loader;
	}

}
