package com.odcgroup.domain.resource;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy;
import org.eclipse.xtext.util.IAcceptor;

import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.workbench.core.repository.ILanguageRepository;
import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

public class DomainResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
	// NOT extends JvmTypesResourceDescriptionStrategy, even though originally suggested by Itemis/Anton, but: 
	// "I thought it can be useful but it seems only to slow down build because of jvm types resolution"
	
	private final static Logger _logger = Logger.getLogger(DomainResourceDescriptionStrategy.class);

	private ILanguageRepository languageRepository = 
			LanguageRepositoryProvider.getLanguageRepository("domain");

	@Override
	public boolean createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {

		// Entities: Primitive, BusinessType, Class, Dataset, Enumeration
		if (eObject instanceof MdfEntity) {
			if (getQualifiedNameProvider() == null)
				return false;
			QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(eObject);
			
			if (qualifiedName != null) {
				Map<String, String> userData = new HashMap<String, String>();
				if (languageRepository != null)
					// can be null e.g. in a JavaSE (non-OSGi/non-plugin) test launch..
					languageRepository.createUserData(eObject, userData);
				
				// create the object description
				try {
					if (userData.isEmpty()) {
						acceptor.accept(EObjectDescription.create(qualifiedName, eObject));
					} else {
						acceptor.accept(EObjectDescription.create(qualifiedName, eObject, userData));
					}
				} catch (Exception ex) {
					_logger.error(ex.getMessage() + " ("+eObject.eClass()+")", ex);
				}
			}
			// do not traverse children
			return false;

		// Domain
		} else if (eObject instanceof MdfDomain) {
				if (getQualifiedNameProvider() == null)
					return false;
				QualifiedName qualifiedName = getQualifiedNameProvider().getFullyQualifiedName(eObject);
				acceptor.accept(EObjectDescription.create(qualifiedName, eObject));
				return true;

		} else if (eObject instanceof JvmDeclaredType) {
			super.createEObjectDescriptions(eObject, acceptor);
			return false;
		
		// others
		} else {
			// do not traverse children
			return false;
		}
	}
	
	@Override
	protected boolean isResolvedAndExternal(EObject from, EObject to) {
		if (to == null)
			return false;
		if (!to.eIsProxy()) {
			if (to.eResource() == null) {
				_logger.error("Reference from " + EcoreUtil.getURI(from) + " to " + to
						+ " cannot be exported as the target is not contained in a resource.");
				return false;
			}
			return from.eResource() != to.eResource();
		}
		String frag = ((InternalEObject) to).eProxyURI().fragment();
		frag = frag != null ? frag : "";
		return !getLazyURIEncoder()
				.isCrossLinkFragment(from.eResource(), frag);
	}
	
}
