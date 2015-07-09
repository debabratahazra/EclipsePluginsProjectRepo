package com.odcgroup.integrationfwk.ui.decorators.core;

import org.eclipse.core.runtime.QualifiedName;

/**
 * 
 * 
 * Utility class to maintain persistent property names and assosiated Qualified
 * names
 * 
 */
public class PersistentPropertyTypes {
	private static PersistentPropertyTypes instance_;

	/**
	 * Busy Qualified Name
	 */
	private QualifiedName busyQualifiedName_;

	/**
	 * Suffix Qualified Name
	 */
	private QualifiedName suffixQualifiedName_;

	/**
	 * Constructor for DemoResourcePersistentProperty.
	 */
	private PersistentPropertyTypes() {
		// Allocate memory for all the qualified name
		busyQualifiedName_ = new QualifiedName("DemoDecorator", "Busy");
		suffixQualifiedName_ = new QualifiedName("DemoDecorator", "Suffix");
	}

	public static PersistentPropertyTypes getInstance() {
		if (instance_ == null) {
			instance_ = new PersistentPropertyTypes();
		}
		return instance_;
	}

	/**
	 * Get the Busy Qualified name
	 */
	public QualifiedName getBusyQualifiedName() {
		return busyQualifiedName_;
	}

	/**
	 * Get the Suffix Qualifier name
	 */
	public QualifiedName getSuffixQualifiedName() {
		return suffixQualifiedName_;
	}

	/**
	 * Get the qualified name given the local name
	 * 
	 * @param localName
	 *            local name of the qualified name
	 * @return Qualified Name
	 * 
	 */
	public QualifiedName getQualifiedName(String localName) {
		if (localName.equals("Busy")) {
			return busyQualifiedName_;
		} else {
			if (localName.equals("Suffix")) {
				return suffixQualifiedName_;
			}
		}
		return null;
	}
}
