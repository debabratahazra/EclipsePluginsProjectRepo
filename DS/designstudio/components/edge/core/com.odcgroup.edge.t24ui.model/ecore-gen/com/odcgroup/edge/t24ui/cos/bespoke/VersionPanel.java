/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke;

import com.odcgroup.t24.version.versionDSL.Version;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage#getVersionPanel()
 * @model
 * @generated
 */
public interface VersionPanel extends Panel {
	/**
	 * Returns the value of the '<em><b>Version</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' reference.
	 * @see #setVersion(Version)
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage#getVersionPanel_Version()
	 * @model
	 * @generated
	 */
	Version getVersion();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.bespoke.VersionPanel#getVersion <em>Version</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' reference.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(Version value);

} // VersionPanel
