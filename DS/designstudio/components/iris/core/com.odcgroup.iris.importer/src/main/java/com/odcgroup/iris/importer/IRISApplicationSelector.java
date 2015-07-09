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

import com.odcgroup.t24.application.importer.IApplicationFilter;
import com.odcgroup.t24.application.importer.IApplicationSelector;
import com.odcgroup.t24.application.importer.internal.ApplicationFilter;
import com.odcgroup.t24.application.repository.IT24DomainRepository;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

public class IRISApplicationSelector implements IApplicationSelector {
	
	private static final Logger logger = LoggerFactory.getLogger(IRISApplicationSelector.class);

	private List<ApplicationDetail> allApplications;
	private Set<ApplicationDetail> selectedApplications = new HashSet<ApplicationDetail>();
	private ApplicationFilter filter = new ApplicationFilter();

	private Set<String> components;
	private Set<String> products;
	
	private void loadApplicationFromModels(final List<ApplicationDetail> allApplications) {
		
		final IT24DomainRepository domainRepository = 
				LanguageRepositoryProvider.getLanguageRepository(IT24DomainRepository.DOMAIN_LANGUAGE_NAME);
		
		Iterable<IEObjectDescription> candidates = domainRepository.getAllMdfClassesWithPrimaryKeys(); 

		// Register all T24 application
		final Procedure1<IEObjectDescription> register = new Procedure1<IEObjectDescription>() {
			public void apply(IEObjectDescription p) {
				String name = domainRepository.getT24Name(p);
				String component = domainRepository.getT24ComponentName(p); 
				String product = domainRepository.getT24ProductName(p); 
				ApplicationDetail detail = new ApplicationDetail(name, component, product);
				if (!detail.isEmpty()) {
					allApplications.add(detail);
				}
			}
		};
		IterableExtensions.<IEObjectDescription>forEach(candidates, register);
	}

	/**
	 * Ensure all applications has been loaded from the current server.
	 */
	private void loadAllApplications() throws T24ServerException {
		if (allApplications == null) {
			products = new HashSet<String>();
			components = new HashSet<String>();

			// reload all applications info from the current server
			allApplications = new ArrayList<ApplicationDetail>();
			
			loadApplicationFromModels(allApplications);

			// extract modules, components, applications
			for (ApplicationDetail application : allApplications) {
				products.add(application.getProduct());
				components.add(application.getComponent());
			}
		}
	}
	
	// this method must be overriden
	protected IProject getProject() {
		return null;
	}

	@Override
	public List<String> getProducts() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllApplications();
			result = new ArrayList<String>(products);
		} catch (T24ServerException ex) {
			logger.warn("loadAllApplications() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public List<String> getComponents() {
		List<String> result = new ArrayList<String>();
		try {
			loadAllApplications();
			result = new ArrayList<String>(components);
		} catch (T24ServerException ex) {
			logger.warn("loadAllApplications() failed", ex);
			// ignore
		}
		return result;
	}

	@Override
	public final IApplicationFilter getFilter() {
		return filter;
	}

	@Override
	public List<ApplicationDetail> getFilteredModels()
			throws T24ServerException {
		loadAllApplications();
		List<ApplicationDetail> models = new ArrayList<ApplicationDetail>();
		for (ApplicationDetail application : allApplications) {
			if (filter.accept(application)) {
				models.add(application);
			}
		}
		return models;
	}

	@Override
	public final Set<ApplicationDetail> getSelectedModels() {
		return selectedApplications;
	}

	@Override
	public String getModelName() {
		return "???";
	}

	@Override
	public List<String> getAppDescriptions() {
		List<String> result = new ArrayList<String>();
		return result;
	}

}
