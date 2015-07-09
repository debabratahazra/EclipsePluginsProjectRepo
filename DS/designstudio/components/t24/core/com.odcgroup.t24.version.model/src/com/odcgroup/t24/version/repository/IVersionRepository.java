package com.odcgroup.t24.version.repository;

import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.t24.common.repository.IT24LanguageRepository;

public interface IVersionRepository extends IT24LanguageRepository {
	
	static final String VERSION_LANGUAGE_NAME = "version"; 
	
	Iterable<IEObjectDescription> getAllVersions();
	
}
