package com.odcgroup.domain.repository;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.workbench.core.repository.ILanguageRepository;

public interface IDomainRepository extends ILanguageRepository {
	
	static final String DOMAIN_LANGUAGE_NAME = "domain";
	
	boolean isClassWithPrimaryKey(IEObjectDescription eObjectDescription);
	
	Iterable<IEObjectDescription> getAllMdfClassesWithPrimaryKeys();
	
	Iterable<IEObjectDescription> getAllClasses(EObject context);

}
