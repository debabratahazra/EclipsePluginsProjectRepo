/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.edge.t24ui;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.Version#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.T24UIPackage#getVersion()
 * @model
 * @generated
 */
public interface Version extends Resource {

	/**
	 * Returns the value of the '<em><b>Version</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' reference.
	 * @see #setVersion(com.odcgroup.t24.version.versionDSL.Version)
	 * @see com.odcgroup.edge.t24ui.T24UIPackage#getVersion_Version()
	 * @model
	 * @generated
	 */
	com.odcgroup.t24.version.versionDSL.Version getVersion();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.Version#getVersion <em>Version</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' reference.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(com.odcgroup.t24.version.versionDSL.Version value);
} // Version
