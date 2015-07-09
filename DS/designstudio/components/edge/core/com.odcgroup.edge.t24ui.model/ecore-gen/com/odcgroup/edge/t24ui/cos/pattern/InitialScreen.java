/**
 */
package com.odcgroup.edge.t24ui.cos.pattern;

import com.odcgroup.t24.version.versionDSL.Version;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Initial Screen</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.InitialScreen#getScreen <em>Screen</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getInitialScreen()
 * @model
 * @generated
 */
public interface InitialScreen extends ChildResourceSpec {
	/**
	 * Returns the value of the '<em><b>Screen</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Screen</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Screen</em>' reference.
	 * @see #setScreen(Version)
	 * @see com.odcgroup.edge.t24ui.cos.pattern.PatternPackage#getInitialScreen_Screen()
	 * @model
	 * @generated
	 */
	Version getScreen();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.pattern.InitialScreen#getScreen <em>Screen</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Screen</em>' reference.
	 * @see #getScreen()
	 * @generated
	 */
	void setScreen(Version value);

} // InitialScreen
