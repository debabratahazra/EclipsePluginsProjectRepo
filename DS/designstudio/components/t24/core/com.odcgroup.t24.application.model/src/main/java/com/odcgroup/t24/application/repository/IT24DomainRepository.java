package com.odcgroup.t24.application.repository;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.odcgroup.domain.repository.IDomainRepository;
import com.odcgroup.t24.common.repository.IT24LanguageRepository;

public interface IT24DomainRepository extends IDomainRepository, IT24LanguageRepository {
	
	String getApplicationQualifiedNameFromT24Name(EObject context,String t24Name,EReference reference);

}
