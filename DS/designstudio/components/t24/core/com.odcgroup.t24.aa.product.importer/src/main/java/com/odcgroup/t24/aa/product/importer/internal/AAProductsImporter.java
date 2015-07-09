package com.odcgroup.t24.aa.product.importer.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.aa.product.importer.IAAProductsFilter;
import com.odcgroup.t24.aa.product.importer.IAAProductsImporter;
import com.odcgroup.t24.aa.product.importer.IAAProductsSelector;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.server.external.model.AAProductsDetails;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAProductsImporter extends T24ModelImporter implements IAAProductsImporter, IAAProductsSelector {
	
	private List<AAProductsDetails> allProductLines;
	private Set<AAProductsDetails> selectedAAProducts = new HashSet<AAProductsDetails>();
	
	private Set<String> productLines;
	private IExternalServer extServer;
	
	private AAProductsFilter filter = new AAProductsFilter();  
	
	private IImportModelReport report;
	
	private AAProductsImportOperation operation = new AAProductsImportOperation();
	
	private void loadAllAAProductLines() throws T24ServerException {
		if (extServer!=null && !extServer.equals(getServer())) {
			allProductLines = null; 
		}
		if (allProductLines == null) {
			extServer = getServer(); 
			productLines = new HashSet<String>();

			// reload all product Lines info from the current server
			allProductLines = new ArrayList<AAProductsDetails>();
			if (isServerSet()) {
				IExternalLoader loader = getServer().getExternalLoader(AAProductsDetails.class);
				if (loader != null) {
					try {
						loader.open();
						allProductLines = loader.getObjectDetails();
					} finally {
						loader.close();
					}
				}
			} 
			
			for (AAProductsDetails produclLine : allProductLines) {
				productLines.add(produclLine.getName());
			}
		}
	}

	@Override
	public void importModels(IProgressMonitor monitor) throws T24ServerException {
		IFolder folder = getContainer().getParent().getFolder(new Path("domain/" + AAProductsImportOperation.DESTINATION_FOLDER));
		report = new AAModelImportReport(selectedAAProducts.size(), folder);

		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(AAProductsDetails.class);
			loader.open();
			operation.setContainer(getContainer());
			operation.setExternalLoader(loader);
			operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
			operation.setModels(selectedAAProducts);
			operation.importModels(report, monitor);
		} catch (T24ServerException ex) {
			report.error(ex);
		} finally {
			if (loader != null) {
				loader.close();
			}
		}
	}

	@Override
	public IImportModelReport getImportReport() {
		return report;
	}

	@Override
	public int level() {
		return 0;
	}

	@Override
	public int minSelectedLevel() {
		if(selectedAAProducts.isEmpty()){
			return 0;
		}
		return 2; // Project level selection
	}

	@Override
	public String getModelType() {
		return "domain";
	}

	@Override
	public IAAProductsFilter getFilter() {
		return filter;
	}

	@Override
	public List<AAProductsDetails> getFilteredModels() throws T24ServerException {
		loadAllAAProductLines();
		List<AAProductsDetails> models = new ArrayList<AAProductsDetails>();
		for (AAProductsDetails aaProducts : allProductLines) {
			if (filter.accept(aaProducts)) {
				models.add(aaProducts);
			}
		}
		return models;
	}

	@Override
	public Set<AAProductsDetails> getSelectedModels() {
		return selectedAAProducts;
	}

	@Override
	public IAAProductsSelector getAAProductsSelector() {
		return this;
	}
}
