package com.odcgroup.t24.common.repository;

import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.workbench.core.repository.ILanguageRepository;

public interface IT24LanguageRepository extends ILanguageRepository {

	String getT24Name(IEObjectDescription eObjectDescription);

	String getT24ComponentName(IEObjectDescription eObjectDescription);

	String getT24ModuleName(IEObjectDescription eObjectDescription);

	String getT24ProductName(IEObjectDescription eObjectDescription);

	String getT24ApplicationName(IEObjectDescription eObjectDescription);
}
