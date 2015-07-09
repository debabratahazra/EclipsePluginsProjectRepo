package com.odcgroup.domain.edmx;

public class EDMXDomainDetail {

	private String name;

	/**
	 * @param name
	 */
	public EDMXDomainDetail(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("["); //$NON-NLS-1$
		buf.append(getName());
		buf.append("]"); //$NON-NLS-1$
		return buf.toString();
	}
	

}
