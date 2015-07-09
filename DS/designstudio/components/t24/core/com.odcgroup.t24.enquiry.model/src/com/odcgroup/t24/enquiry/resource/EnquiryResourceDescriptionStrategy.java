package com.odcgroup.t24.enquiry.resource;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.workbench.core.repository.ILanguageRepository;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;


/**
 * filter-out the eObjectDesc & refDesc temporarily
 * @author phanikumark
 *
 */
public class EnquiryResourceDescriptionStrategy extends	DefaultResourceDescriptionStrategy {

		private final static Logger _logger = Logger.getLogger(EnquiryResourceDescriptionStrategy.class);
		
		private ILanguageRepository languageRepository = 
				LanguageRepositoryProvider.getLanguageRepository("enquiry");
		
		@Override
		public boolean createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
			if (getQualifiedNameProvider() == null)
				return false;

			if (eObject instanceof Enquiry) {
				
				QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(eObject);
				if (qualifiedName != null) {

					Map<String, String> userData = new HashMap<String, String>();
					if (languageRepository != null) // This happens in non-OSGi Standalone @RunWith(XtextRunner.class) *Test (and it's OK)
						languageRepository.createUserData(eObject, userData);
			
					// create the object description
					try {
						if (userData.isEmpty()) {
							acceptor.accept(EObjectDescription.create(qualifiedName, eObject));
						} else {
							acceptor.accept(EObjectDescription.create(qualifiedName, eObject, userData));
						}
					} catch (Exception ex) {
						_logger.error(ex.getMessage() + "("+eObject.eClass()+")");
					}
				}
				
			}
		
			// the children of eObject (Enquiry) should not be traversed
			return false;
	}

}
