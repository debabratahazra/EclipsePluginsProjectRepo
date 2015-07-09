/**
 * ODCGROUP
 *
 * $Id$
 */
package com.odcgroup.pageflow.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.pageflow.model.View#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.pageflow.model.PageflowPackage#getView()
 * @model
 * @generated
 */
public interface View extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "ODCGROUP";

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see com.odcgroup.pageflow.model.PageflowPackage#getView_Url()
	 * @model default="" unique="false"
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link com.odcgroup.pageflow.model.View#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

} // View
