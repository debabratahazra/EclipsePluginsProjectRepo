package com.odcgroup.t24.scoping;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ecore.util.MdfNameURIUtil;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * Actually, in the Enquiry grammar most of the domain cross references are
 * defined as String, i.e not a true XText cross-ref.
 * 
 * <p>
 * The aim of this helper class is to provide services to resolve these
 * reference.
 * 
 * @author atripod
 */
public final class EnquiryCrossReferenceResolver {

	final private static Logger logger = LoggerFactory.getLogger(DomainRepository.class);

	/**
	 * must not be instantiable.
	 */
	private EnquiryCrossReferenceResolver() {
	}
	
	private static void logBaseClassNotFound(Enquiry enquiry) {
		logBaseClassNotFound(enquiry, null);
	}

	private static void logBaseClassNotFound(Enquiry enquiry, Exception ex) {
		URI uri = EcoreUtil.getURI(enquiry);
		String name = (uri == null) ? (enquiry.getName()) : uri.toFileString();
		String message = "Cannot find the base class for the Enquiry ["+name+"]"; 
		if (ex != null) {
			logger.error(message, ex);
		} else {
			logger.error(message);
		}
	}

	/**
	 * Returns the base class (application for the given enquiry, or
	 * <code>null</code> if it cannot be found (it does not exist in the domain,
	 * the enquiry is not contained in a resourceSet)
	 * 
	 * @param enquiry
	 *            the enquiry
	 * @return the base class (application)
	 */
	public static MdfClass getBaseClassFor(Enquiry enquiry) {
		MdfClass application = null;
		EObject root = enquiry; 
		if (root != null) {
			Resource resource = root.eResource();
			try {
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(resource);
				DomainRepository repository = DomainRepository.getInstance(ofsProject);
				if (repository != null) {
					String filename = StringUtils.replace(enquiry.getFileName(), ".", "_");
					MdfName mdfName = MdfNameURIUtil.getMdfName(URI.createURI(filename));
					application = (MdfClass) repository.getEntity(mdfName);
				}
			} catch (Exception ex) {
				logBaseClassNotFound(enquiry, ex);
			}
		}
		if (application == null) {
			logBaseClassNotFound(enquiry);
		}
		return application;
	}

}
