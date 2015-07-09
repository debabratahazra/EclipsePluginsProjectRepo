package com.odcgroup.t24.server.external.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author atripod
 */
public class VersionDetail extends ObjectDetail {
	
	private String application;
	private String component;
	private String module;
	private String description;

	public final String getModule() {
		return module;
	}

	public final String getApplication() {
		return application;
	}

	public final String getComponent() {
		return component;
	}
	
	public boolean isEmpty() {
		return StringUtils.isBlank(getName())
			&& StringUtils.isBlank(getModule())
			&& StringUtils.isBlank(getComponent());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (! (o instanceof VersionDetail)) return false;
		VersionDetail other = (VersionDetail)o;
		return StringUtils.equals(getName(), other.getName())
				&& StringUtils.equals(this.application, other.application)
				&& StringUtils.equals(this.component, other.component)
				&& StringUtils.equals(this.module, other.module);
	}
	
	public int hashCode() {
		return getName().hashCode()
				+ this.application.hashCode()
				+ this.component.hashCode()
				+ this.module.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("["); //$NON-NLS-1$
		buf.append(getApplication());
		buf.append(":"); //$NON-NLS-1$
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
	public VersionDetail(VersionDetail detail) {
		this(detail.getName(), detail.component,detail.module, detail.application ,detail.getDescription());
	}

	public VersionDetail(String name, String component, String module, String application){
		this(name, component, module, application, null);
	}
	/**
	 * @param name
	 * @param component
	 * @param module
	 * @param application
	 */
	public VersionDetail(String name, String component, String module, String application ,String description) {
		super(name);
		this.component = component;
		this.module = module;
		this.application = application;
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
