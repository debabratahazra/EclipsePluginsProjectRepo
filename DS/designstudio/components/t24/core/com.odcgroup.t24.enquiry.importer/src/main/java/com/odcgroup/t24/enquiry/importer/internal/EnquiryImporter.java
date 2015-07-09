package com.odcgroup.t24.enquiry.importer.internal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.t24.common.importer.IImportModelReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImportReport;
import com.odcgroup.t24.common.importer.internal.T24ModelImporter;
import com.odcgroup.t24.enquiry.importer.IEnquiryFilter;
import com.odcgroup.t24.enquiry.importer.IEnquiryImporter;
import com.odcgroup.t24.enquiry.importer.IEnquirySelector;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;

public class EnquiryImporter extends T24ModelImporter implements IEnquiryImporter, IEnquirySelector {
	private static final Logger logger = LoggerFactory.getLogger(EnquiryImporter.class);

	
	private List<EnquiryDetail> allEnquiries;
	private Set<EnquiryDetail> selectedEnquiries = new HashSet<EnquiryDetail>();
	
	private Set<String> applications;
	private Set<String> components;
	private Set<String> modules;
	private IExternalServer server1;
	private Set<String> description;
	
	private EnquiryFilter filter = new EnquiryFilter();  
	
	private IImportModelReport report;
	
	private @Inject EnquiryImportOperation operation;
	
	/**
	 * Ensure all enquiry has been loaded from the current server.
	 */
	private void loadAllEnquiries() throws T24ServerException {
		if (server1!=null && !server1.equals(getServer())) {
			allEnquiries = null; 
		}
		if (allEnquiries == null) {
			server1 = getServer(); 
			modules = new HashSet<String>();
			components = new HashSet<String>();
			applications = new HashSet<String>();
			description = new HashSet<String>();
			// reload all enquiry info from the current server
			allEnquiries = new ArrayList<EnquiryDetail>();
			if (isServerSet()) {
				IExternalLoader loader = getServer().getExternalLoader(EnquiryDetail.class);
				if (loader != null) {
					try {
						loader.open();
						allEnquiries = loader.getObjectDetails();
					} finally {
						loader.close();
					}
				}
			} 
			
			// extract modules, components, applications
			for (EnquiryDetail enquiry : allEnquiries) {
				modules.add(enquiry.getModule());
				components.add(enquiry.getComponent());
				description.add(enquiry.getDescription());
				
			}
		}
	}

	@Override
	public List<String> getModules() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllEnquiries();
			result = new ArrayList<String>(modules); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllEnquiries() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public List<String> getApplications() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllEnquiries();
			result = new ArrayList<String>(applications); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllEnquiries() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public List<String> getComponents() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllEnquiries();
			result = new ArrayList<String>(components); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllEnquiries() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public final IEnquiryFilter getFilter() {
		return filter;
	}

	@Override
	public List<EnquiryDetail> getFilteredModels() throws T24ServerException {
		loadAllEnquiries();
		List<EnquiryDetail> models = new ArrayList<EnquiryDetail>();
		for (EnquiryDetail enquiry : allEnquiries) {
			if (filter.accept(enquiry)) {
				models.add(enquiry);
			}
		}
		return models;
	}
	
	@Override
	public final IEnquirySelector getEnquirySelector() {
		return this;
	}

	@Override
	public final Set<EnquiryDetail> getSelectedModels() {
		return selectedEnquiries;
	}
	
	@Override
	public int level() {
		return -1;
	}
	
	@Override
	public int minSelectedLevel() {
		return 0;
	}

	
	@Override
	public String getModelType() {
		return "enquiry";
	}
	
	@Override
	public void importModels(IProgressMonitor monitor) throws T24ServerException {
		
		report = new T24ModelImportReport(getSelectedModels().size(), (IFolder)getContainer());
		
		IExternalLoader loader = null;
		try {
			loader = getServer().getExternalLoader(EnquiryDetail.class);
			loader.open();
	 		operation.setContainer(getContainer());
	 		operation.setExternalLoader(loader);
	 		operation.setInDebug(getServer().getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG);
	 		operation.setModels(getSelectedModels());
	 		operation.importModels(report, monitor); 	
		} catch (T24ServerException ex) {
			report.error(ex);
		} finally {
			if (loader != null) {
				loader.close();
				createEnquiriesDetailsFile();
			}
		}
 	}

	/**
	 * Generate rim/.report file during import of Enquiry
	 *  for getting the details of Component and Module name
	 *  for RIM Importer.
	 */
	private void createEnquiriesDetailsFile() {
		Set<EnquiryDetail> enquiryDetailsList = getSelectedModels();
		StringBuffer b = new StringBuffer();
		for (Iterator<EnquiryDetail> iterator = enquiryDetailsList.iterator(); iterator
				.hasNext();) {
			EnquiryDetail enquiryDetail = (EnquiryDetail) iterator.next();
			b.append(enquiryDetail.getName() + "#@");
			b.append(enquiryDetail.getComponent() + "#@");
			b.append(enquiryDetail.getModule() + "^&*");
		}

		IProject iProject = getContainer().getProject();
		IPath iPath = iProject.getFullPath();
		IFolder iFolder = iProject.getFolder(iPath);
		String projectPath = iFolder.getRawLocation().toOSString()
				.split(iProject.getName())[0];
		String rimFolderPath = projectPath + iProject.getName()
				+ File.separator + "rim" + File.separator
				+ ".report";

		File rimFileReport = new File(rimFolderPath);
		try {
			FileWriter fw = new FileWriter(rimFileReport.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(b.toString());
			bw.flush();
			bw.close();
			
			ResourcesPlugin.getWorkspace().getRoot()
					.getProject(iProject.getName())
					.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (IOException e) {			
		} catch (CoreException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public IImportModelReport getImportReport() {
		return report;
	}

	@Override
	public List<String> getDescriptions() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllEnquiries();
			result = new ArrayList<String>(description); 
		} catch (T24ServerException ex) {
			logger.warn("loadAllEnquiries() failed", ex);
			// ignore
		}
		return result;
	}



}
