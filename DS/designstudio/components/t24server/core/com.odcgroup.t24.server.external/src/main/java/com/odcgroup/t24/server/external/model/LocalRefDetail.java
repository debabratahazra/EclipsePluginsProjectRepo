package com.odcgroup.t24.server.external.model;

/**
 * @author ssreekanth
 *
 */
public class LocalRefDetail extends ObjectDetail {

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
	
	public LocalRefDetail(String name) {
		super(name);
	}

	public LocalRefDetail(LocalRefDetail detail) {
		this(detail.getName());
	}
}
