package com.odcgroup.t24.server.external.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author atripod
 */
public class EnquiryDetail extends ObjectDetail {
	
	private String component;
	private String module;
	private String description;


	public boolean isEmpty() {
		return StringUtils.isBlank(getName())
			&& StringUtils.isBlank(getModule())
			&& StringUtils.isBlank(getComponent());
	}
	
	public final String getModule() {
		return module;
	}


	public final String getComponent() {
		return component;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("["); //$NON-NLS-1$
		buf.append(getName());
		buf.append("] Module:["); //$NON-NLS-1$
		buf.append(getModule());
		buf.append("] Component:["); //$NON-NLS-1$
		buf.append(getComponent());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}
	
	/**
	 * @param detail
	 */
	public EnquiryDetail(EnquiryDetail detail) {
		this(detail.getName(), detail.component,detail.module ,detail.description);
	}

	public EnquiryDetail(String name, String component, String module){
		this(name, component, module, null);
	}
	/**
	 * @param name
	 * @param component
	 * @param module
	 * @param application
	 */
	public EnquiryDetail(String name, String component, String module , String description) {
		super(name);
		this.component = component;
		this.module = module;
		if(description != null){
			this.description = description;
		}
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
