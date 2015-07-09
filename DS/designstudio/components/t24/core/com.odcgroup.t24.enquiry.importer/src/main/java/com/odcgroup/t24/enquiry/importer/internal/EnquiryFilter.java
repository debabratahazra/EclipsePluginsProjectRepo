package com.odcgroup.t24.enquiry.importer.internal;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.t24.enquiry.importer.IEnquiryFilter;
import com.odcgroup.t24.server.external.model.EnquiryDetail;

/**
 */
public class EnquiryFilter implements IEnquiryFilter {

	private static final String DEFAULT_CODE_PATTERN = "*"; //$NON-NLS-1$

	private String application;
	private String component;
	private String module;
	private String namePattern = DEFAULT_CODE_PATTERN;
	private String description;
	

	private boolean isComponentFiltered(EnquiryDetail enquiry) {
		String text = getComponent();
		return StringUtils.isNotBlank(text)
				&& !(StringMatch.match(enquiry.getComponent(), text));
	}

	private boolean isModuleFiltered(EnquiryDetail enquiry) {
		String text = getModule();
		return StringUtils.isNotBlank(text)
				&& !(StringMatch.match(enquiry.getModule(), text));
	}

	private boolean isDescriptionFiltered(EnquiryDetail enquiry) {
		String text = getDescription();
		return StringUtils.isNotBlank(text)	&& 
				!(StringMatch.match(enquiry.getDescription(), text))
				;
	}
	@Override
	public boolean accept(EnquiryDetail enquiry) {

		if (!(StringMatch.match(enquiry.getName(), getName()))) {
			return false;
		} else {
			return !(isModuleFiltered(enquiry) || isComponentFiltered(enquiry)||isDescriptionFiltered(enquiry));
		}
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

	public EnquiryFilter() {
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
