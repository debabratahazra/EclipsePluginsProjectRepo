package com.odcgroup.t24.server.external.model;


/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class AAProductsDetails extends ObjectDetail {
	
	private int propertiesCount;

	public String getDescription() {
		StringBuffer buf = new StringBuffer();
		buf.append(" ( "); //$NON-NLS-1$
		buf.append(getPropertiesCount());
		buf.append(" Property classes)"); //$NON-NLS-1$
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
	
	public AAProductsDetails(String name, int propertiesCount) {
		super(name);
		this.propertiesCount = propertiesCount;
	}

	public AAProductsDetails(AAProductsDetails detail) {
		this(detail.getName(), detail.getPropertiesCount());
	}

	/**
	 * @return the propertiesCount
	 */
	public int getPropertiesCount() {
		return propertiesCount;
	}

	/**
	 * @param propertiesCount the propertiesCount to set
	 */
	public void setPropertiesCount(int propertiesCount) {
		this.propertiesCount = propertiesCount;
	}
}
