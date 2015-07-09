package com.odcgroup.t24.application.repository;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.domain.repository.DomainRepositoryX;
import com.odcgroup.domain.repository.IDomainRepository;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.common.repository.T24LanguageRepositoryHelper;

public class T24DomainRepository extends DomainRepositoryX implements IT24DomainRepository {
	
	private static final String T24_APPLICATION_PACKAGE = "applications";
	
	@Override
	public void createUserData(EObject eObject, Map<String, String> userData) {
		super.createUserData(eObject, userData);
		if (eObject instanceof MdfClass) {
			MdfClass klass = (MdfClass) eObject;
			URI uri = eObject.eResource().getURI();
			if (uri.segmentCount() > 3 && T24_APPLICATION_PACKAGE.equals(uri.segment(3))) {
				// T24 name
				String t24Name = T24Aspect.getT24Name(klass);
				T24LanguageRepositoryHelper.setT24Name(userData, t24Name);
				// T24 Module
				String module = T24Aspect.getModule(klass.getParentDomain());
				T24LanguageRepositoryHelper.setT24ModuleName(userData, module);
				// T24 Component
				String domainName = klass.getParentDomain().getName();
				T24LanguageRepositoryHelper.setT24ComponentName(userData, domainName);
				// T24 Product
				String productName = uri.segmentCount() > 4 ? uri.segment(4) : "";
				T24LanguageRepositoryHelper.setT2ProductName(userData, productName);
			}
		}		
	}

	@Override
	public String getT24ApplicationName(IEObjectDescription eObjectDescription) {
		return T24LanguageRepositoryHelper.getT24ApplicationName(eObjectDescription);
	}

	@Override
	public String getT24ComponentName(IEObjectDescription eObjectDescription) {
		return T24LanguageRepositoryHelper.getT24ComponentName(eObjectDescription);
	}

	@Override
	public String getT24ModuleName(IEObjectDescription eObjectDescription) {
		return T24LanguageRepositoryHelper.getT24ModuleName(eObjectDescription);
	}

	@Override
	public String getT24Name(IEObjectDescription eObjectDescription) {
		return T24LanguageRepositoryHelper.getT24Name(eObjectDescription);
	}

	@Override
	public String getT24ProductName(IEObjectDescription eObjectDescription) {
		return T24LanguageRepositoryHelper.getT24ProductName(eObjectDescription);
	}


	public T24DomainRepository() {
		super(IDomainRepository.DOMAIN_LANGUAGE_NAME);
	}

	@Override
	public String getApplicationQualifiedNameFromT24Name(EObject context,String t24Name,EReference reference) {
		Iterable<IEObjectDescription> eObjectDescriptions = getAllClasses(context);
		for(IEObjectDescription eObjDesc : eObjectDescriptions){
			if(getT24Name(eObjDesc).equals(t24Name)){
				return getT24ComponentName(eObjDesc)+":"+getT24Name(eObjDesc);
			}
		}
		return t24Name;
	}

}
