package com.odcgroup.t24.server.external.model;


/**
 * @author hdebabrata
 * @param name
 */
public class LocalRefApplicationDetail extends ObjectDetail {

	public String getDescription() {
		StringBuffer buf = new StringBuffer();
		buf.append("["); //$NON-NLS-1$
		buf.append(getName());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("["); //$NON-NLS-1$
		buf.append(getName());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}
	
	public LocalRefApplicationDetail(String name) {
		super(name);
	}

	public LocalRefApplicationDetail(LocalRefApplicationDetail detail) {
		this(detail.getName());
	}


}
