package com.odcgroup.t24.application.importer.internal;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.t24.application.importer.IApplicationFilter;
import com.odcgroup.t24.server.external.model.ApplicationDetail;

/**
 */
public class ApplicationFilter implements IApplicationFilter {

	private static final String DEFAULT_CODE_PATTERN = "*"; //$NON-NLS-1$

	private String component;
	private String product;
	private String description;
	private String namePattern = DEFAULT_CODE_PATTERN;
	


	private boolean isComponentFiltered(ApplicationDetail application) {
		String text = getComponent();
		return StringUtils.isNotBlank(text)
				&& !(StringMatch.match(application.getComponent(), text));
	}

	private boolean isProductFiltered(ApplicationDetail application) {
		String text = getProduct();
		return StringUtils.isNotBlank(text)
				&& !(StringMatch.match(application.getProduct(), text));
	}

	/**
	 * @param application
	 * @return
	 */
	private boolean isDescriptionFiltered(ApplicationDetail application) {
		String text = getDescription();
		return StringUtils.isNotBlank(text)	&& 
				!(StringMatch.match(application.getApplicaitonDescription(), text))
				;
	}
	@Override
	public boolean accept(ApplicationDetail application) {

		if (!(StringMatch.match(application.getName(), getName()))) {
			return false;
		} else {
			return !(isProductFiltered(application) || isComponentFiltered(application)||isDescriptionFiltered(application));
		}
	}

	public final String getComponent() {
		return component;
	}

	public final String getProduct() {
		return product;
	}

	public final String getName() {
		return namePattern;
	}

	public final void setComponent(String component) {
		this.component = component;
	}

	public final void setProduct(String product) {
		this.product = product;
	}

	public final void setName(String name) {
		this.namePattern = name;
	}

	public ApplicationFilter() {
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
