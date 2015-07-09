package com.odcgroup.t24.server.external.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author atripod
 *
 */
public class ApplicationDetail extends ObjectDetail {

	private String product;
	private String component;
	private String applicaitonDescription;
	
	public boolean isEmpty() {
		return StringUtils.isBlank(getName())
			&& StringUtils.isBlank(getProduct())
			&& StringUtils.isBlank(getComponent());
	}

	public final String getProduct() {
		return product;
	}

	public final String getComponent() {
		return component;
	}
	
	public String getDescription() {
		StringBuffer buf = new StringBuffer();
		buf.append("["); //$NON-NLS-1$
		buf.append(getProduct());
		buf.append(":"); //$NON-NLS-1$
		buf.append(getName());
		buf.append("]"); //$NON-NLS-1$
		buf.append(" Component:["); //$NON-NLS-1$
		buf.append(getComponent());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("["); //$NON-NLS-1$
		buf.append(getProduct());
		buf.append(":"); //$NON-NLS-1$
		buf.append(getName());
		buf.append("] Component:["); //$NON-NLS-1$
		buf.append(getComponent());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}
	
	public ApplicationDetail(String name, String component, String product) {
		this(name, component, product, null);
	}
	
	public ApplicationDetail(String name, String component, String product ,String applicationDescription) {
		super(name);
		this.component = (component != null) ? component : "";
		this.product = (product != null) ? product : "";
		if(applicationDescription != null){
			this.applicaitonDescription = applicationDescription;
		}
	}

	public ApplicationDetail(ApplicationDetail detail) {
		this(detail.getName(), detail.component, detail.product ,detail.getApplicaitonDescription());
	}
	
	/**
	 * @return the applicaitonDescription
	 */
	public String getApplicaitonDescription() {
		return applicaitonDescription;
	}
}
