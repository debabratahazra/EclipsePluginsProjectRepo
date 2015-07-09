package com.odcgroup.mdf.ecore.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelMatcher;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;

/**
 * This class provides means to lookup domain models / resources.
 * It is the successor of DomainModelLookup, which often causes problems as it holds a cache
 * internally to speed up things.
 * Please note that this class does not cache any results - on every request, it will traverse
 * the whole model project and its dependencies; so beware of calling these methods too often!
 *
 * @author Kai Kreuzer
 * @author Michael Vorburger Significant Optimizations
 */
// intentionally package-local; used only by DomainRepository
class DomainLookup extends ModelResourceLookup {

	static final private Logger logger = LoggerFactory.getLogger(DomainLookup.class);
	
    static final String[] EXTENSIONS = { "domain" };

	DomainLookup(IOfsProject ofsProject) {
		super(ofsProject, EXTENSIONS);
	}

	// intentionally package-local; used only by DomainRepository
	// clients would use DomainRepository's getDomains() instead! [which is NOT optimized, it just calls this; just for clarity]
	Collection<MdfDomain> getAllDomainModels(int scope) {
		long t = System.currentTimeMillis();
    	//logger.info("getAllDomainModels() will now start (re?)loading *ALL* *.domain...");

		List domains = new ArrayList();
		for(IOfsModelResource modelResource : getAllOfsModelResources(scope)) {
			try {
				EObject eObj = modelResource.getEMFModel().get(0);
				if(eObj instanceof MdfDomain) {
					domains.add(eObj);
				} else {
					logger.warn("getAllDomainModels() modelResource.getEMFModel() didn't get MdfDomain but: " + eObj);
				}
			} catch (IOException e) {
				// we'll ignore models that cannot be read.. but log it just as it's a bit surprising 
				logger.warn("getAllDomainModels() could not getEMFModel() for " + modelResource.getURI(), e);
			} catch (InvalidMetamodelVersionException e) {
				// we'll ignore models that cannot be read.. but log it just as it's a bit surprising 
				logger.warn("getAllDomainModels() could not getEMFModel() for " + modelResource.getURI(), e);
			}
		}
		
		t = System.currentTimeMillis() - t;
		if (t > 100)
			// The t > 100 is there because due to our cache, this log is only relevant if the Domain are not already loaded - else above is super-fast, usually (Dev) 3-4ms.. 
			logger.info("getAllDomainModels() finished (re?)loading *ALL* *.domain; it took {}ms", t);

		return domains;
	}

	// intentionally package-local; used only by DomainRepository
	// clients would use DomainRepository's optimized getDomain() instead!
	MdfDomain getDomain(String domainName) throws IOException, InvalidMetamodelVersionException {
		for(IOfsModelResource modelResource : getAllOfsModelResources()) {
			if(modelResource.getResource()!=null && !modelResource.getResource().exists()) {
				//This might be a newly created resource which is not yet serialized to disk
				continue;
			}
			String emfModelRootEObjectName = fetchDomainName(modelResource);
			if (emfModelRootEObjectName != null && domainName.equals(emfModelRootEObjectName)) {
				return getMdfDomain(modelResource);
//			} else if (emfModelRootEObjectName == null) {
//				logger.warn("getEMFModelRootEObjectName() returned null, so I'm trying the old fashioned and very slow parse-em-all approach for: " + modelResource.getURI());
//				final MdfDomain domain = getMdfDomain(modelResource);
//				emfModelRootEObjectName = domain.getName(); 
//				if(domainName.equals(emfModelRootEObjectName)) return domain;
			}
		}		
		return null;
	}

	/**
	 * Don't call this unnecessarily; this is EXPENSIVE, because it uses getEMFModel(). 
	 */
	private MdfDomain getMdfDomain(IOfsModelResource modelResource) throws IOException, InvalidMetamodelVersionException {
		final EObject eObj = modelResource.getEMFModel().get(0);
		if(eObj instanceof MdfDomain) {
			MdfDomain domain = (MdfDomain) eObj;
			return domain;
		} else {
			final String msg = "Expected IOfsModelResource to contain MdfDomain, but it didn't: " + modelResource.getURI();
			logger.error(msg);
			throw new IOException(msg);
		}
	}

	// intentionally package-local; used only by DomainRepository
	// clients would use DomainRepository's getDomains() instead! [which is NOT optimized, it just calls this; just for clarity]
	Set<MdfDomain> getAll(ModelMatcher matcher) {
		Set<MdfDomain> domains = new HashSet<MdfDomain>();
		for(MdfDomain domain : getAllDomainModels(IOfsProject.SCOPE_ALL)) {
			if(matcher.match(domain)) domains.add(domain);
		}
		return domains;
	}
	
	// intentionally package-local; used only by DomainRepository and this class - why would you need it outside?!
	Map<String, URI> getDomainURIMap() {
		Map<String, URI> map = new HashMap<String, URI>();
		for(IOfsModelResource modelResource : getAllOfsModelResources()) {
			if(modelResource.getResource()!=null && !modelResource.getResource().exists()) {
				//This might be a newly created resource which is not yet serialized to disk
				continue;
			}
			String domainName = fetchDomainName(modelResource);
			if (!StringUtils.isEmpty(domainName)) {
				map.put(domainName, modelResource.getURI());
			}
		}
		return map;
	}

	private String fetchDomainName(IOfsModelResource modelResource) {
		String domainName = null;
		InputStream is = null;
		try {
			is = modelResource.getContents();
			LineIterator it = IOUtils.lineIterator(is, "UTF-8");
			int i = 30;
			while (--i > 0 && it.hasNext()) {
				final String line = it.nextLine().trim();
				final String tokenAfterDomain = StringUtils.substringAfter(line, "Domain").trim();
				if (!tokenAfterDomain.isEmpty()) {
					domainName = tokenAfterDomain;
					break;
				} 
			}
		} catch (IOException e) {
			// no valid file to read, can be ignored.. but log it just as it's a bit surprising 
			logger.warn("fetchDomainName() failed for " + modelResource.getURI(), e);
		} catch (CoreException e) {
			// some other exception.. can be ignored.. but log it just as it's a bit surprising
			logger.warn("fetchDomainName() failed for " + modelResource.getURI(), e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return domainName;
	}
}
