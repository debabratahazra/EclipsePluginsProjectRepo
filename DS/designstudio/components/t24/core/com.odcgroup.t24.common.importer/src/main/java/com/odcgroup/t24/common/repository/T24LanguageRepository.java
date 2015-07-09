package com.odcgroup.t24.common.repository;

import java.util.Map;

import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.workbench.core.repository.LanguageRepository;

public abstract class T24LanguageRepository extends LanguageRepository implements IT24LanguageRepository {

	protected void setT24ApplicationName(Map<String, String> userData, String t24Application) {
		T24LanguageRepositoryHelper.setT24ApplicationName(userData, t24Application);
	}

	protected void setT24ComponentName(Map<String, String> userData, String t24Component) {
		T24LanguageRepositoryHelper.setT24ComponentName(userData, t24Component);
	}
	
	protected void setT24ModuleName(Map<String, String> userData, String t24Module) {
		T24LanguageRepositoryHelper.setT24ModuleName(userData, t24Module);
	}

	protected void setT24Name(Map<String, String> userData, String t24Name) {
		T24LanguageRepositoryHelper.setT24Name(userData, t24Name);
	}

	protected void setT24ProductName(Map<String, String> userData, String t24Product) {
		T24LanguageRepositoryHelper.setT2ProductName(userData, t24Product);
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

	/**
	 * @param languageName
	 */
	public T24LanguageRepository(String languageName) {
		super(languageName);
	}

}
