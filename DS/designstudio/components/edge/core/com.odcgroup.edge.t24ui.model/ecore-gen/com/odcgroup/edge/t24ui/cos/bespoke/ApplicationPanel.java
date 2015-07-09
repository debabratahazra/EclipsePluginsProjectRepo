/**
 */
package com.odcgroup.edge.t24ui.cos.bespoke;

import com.odcgroup.mdf.metamodel.MdfClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Panel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel#getApplication <em>Application</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage#getApplicationPanel()
 * @model
 * @generated
 */
public interface ApplicationPanel extends Panel {
	/**
	 * Returns the value of the '<em><b>Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application</em>' reference.
	 * @see #setApplication(MdfClass)
	 * @see com.odcgroup.edge.t24ui.cos.bespoke.BespokePackage#getApplicationPanel_Application()
	 * @model
	 * @generated
	 */
	MdfClass getApplication();

	/**
	 * Sets the value of the '{@link com.odcgroup.edge.t24ui.cos.bespoke.ApplicationPanel#getApplication <em>Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application</em>' reference.
	 * @see #getApplication()
	 * @generated
	 */
	void setApplication(MdfClass value);

} // ApplicationPanel
