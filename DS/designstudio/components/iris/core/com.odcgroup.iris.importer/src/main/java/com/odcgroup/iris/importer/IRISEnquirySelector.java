package com.odcgroup.iris.importer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.enquiry.importer.IEnquiryFilter;
import com.odcgroup.t24.enquiry.importer.IEnquirySelector;
import com.odcgroup.t24.enquiry.importer.internal.EnquiryFilter;
import com.odcgroup.t24.enquiry.repository.IEnquiryRepository;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

public class IRISEnquirySelector implements IEnquirySelector {
	
	private static final Logger logger = LoggerFactory.getLogger(IRISEnquirySelector.class);
	
	private List<EnquiryDetail> allEnquiries;
	private Set<EnquiryDetail> selectedEnquiries = new HashSet<EnquiryDetail>();
	
	private Set<String> applications;
	private Set<String> components;
	private Set<String> modules;
	
	private EnquiryFilter filter = new EnquiryFilter();  

	private void loadEnquiriesFromModels(final List<EnquiryDetail> allEnquiries) {
		
		final IEnquiryRepository enquiryRepository = 
				LanguageRepositoryProvider.getLanguageRepository(IEnquiryRepository.ENQUIRY_LANGUAGE_NAME);
		
		Iterable<IEObjectDescription> candidates = enquiryRepository.getAllEnquiries();

		IterableExtensions.<IEObjectDescription>forEach(candidates, 
				new Procedure1<IEObjectDescription>() {
					public void apply(IEObjectDescription p) {
						String name = enquiryRepository.getT24Name(p);
						String component = enquiryRepository.getT24ComponentName(p); 
						String module = enquiryRepository.getT24ModuleName(p); 
						EnquiryDetail detail = new EnquiryDetail(name, component, module);
						if (!detail.isEmpty()) {
							allEnquiries.add(detail);
						}
					}
				});
	}

	/**
	 * Ensure all enquiries has been loaded from the current server.
	 */
	private void loadAllEnquiries() throws T24ServerException {
		if (allEnquiries == null) {
			modules = new HashSet<String>();
			components = new HashSet<String>();
			applications = new HashSet<String>();

			// reload all enquiries info from the current server
			allEnquiries = new ArrayList<EnquiryDetail>();
			loadEnquiriesFromModels(allEnquiries);
			
			// extract modules, components, applications
			for (EnquiryDetail enquiry : allEnquiries) {
				modules.add(enquiry.getModule());
				components.add(enquiry.getComponent());			
			}
		}
	}
	
	protected IProject getProject() {
		return null;
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
	public final Set<EnquiryDetail> getSelectedModels() {
		return selectedEnquiries;
	}

	@Override
	public String getModelName() {
		return "???";
	}

	@Override
	public List<String> getDescriptions() {
		List<String> result = new ArrayList<String>();
		return result;
	}
	
}
