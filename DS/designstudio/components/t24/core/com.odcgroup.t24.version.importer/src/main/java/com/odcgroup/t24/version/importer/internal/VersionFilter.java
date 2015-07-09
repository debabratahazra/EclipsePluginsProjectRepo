package com.odcgroup.t24.version.importer.internal;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.version.importer.IVersionFilter;

/**
 */
public class VersionFilter implements IVersionFilter {

	private static final String DEFAULT_CODE_PATTERN = "*"; //$NON-NLS-1$

	private String application;
	private String component;
	private String module;
	private String description;
	private String namePattern = DEFAULT_CODE_PATTERN;
	
	private boolean isApplicationFiltered(VersionDetail version) {
		String text = getApplication();
		return StringUtils.isNotBlank(text) &&
				!(StringMatch.match(version.getApplication(), text)) 
				//!text.equals(version.getApplication())
				;
	}

	private boolean isComponentFiltered(VersionDetail version) {
		String text = getComponent();
		return StringUtils.isNotBlank(text) && 
				//!text.equals(version.getComponent())
				!(StringMatch.match(version.getComponent(), text))
				;
	}

	private boolean isModuleFiltered(VersionDetail version) {
		String text = getModule();
		return StringUtils.isNotBlank(text)	&& 
				//!text.equals(version.getModule())
				!(StringMatch.match(version.getModule(), text))
				;
	}


	@Override
	public boolean accept(VersionDetail version) {

		if(!(StringMatch.match(version.getName(), getName()))){
			return false;
		}else {
			return ! (isModuleFiltered(version) || isComponentFiltered(version) || isApplicationFiltered(version)||isDescriptionFiltered(version));
		}
	}

	/**
	 * @param version
	 * @return
	 */
	private boolean isDescriptionFiltered(VersionDetail version) {
		String text = getDescription();
		return StringUtils.isNotBlank(text)	&& 
				!(StringMatch.match(version.getDescription(), text))
				;
	}

	public final String getApplication() {
		return application;
	}

	public final String getComponent() {
		return component;
	}

	public final String getModule() {
		return module;
	}

	public final String getName() {
		return namePattern;
	}

	public final void setApplication(String application) {
		this.application = application;
	}

	public final void setComponent(String component) {
		this.component = component;
	}

	public final void setModule(String module) {
		this.module = module;
	}

	public final void setName(String name) {
		this.namePattern = name;
	}

	public VersionFilter() {
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
