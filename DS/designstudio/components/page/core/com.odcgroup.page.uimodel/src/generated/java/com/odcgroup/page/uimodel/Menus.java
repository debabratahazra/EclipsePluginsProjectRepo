/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Menus</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.Menus#getMenus <em>Menus</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getMenus()
 * @model
 * @generated
 */
public interface Menus extends EObject {
	/**
	 * Returns the value of the '<em><b>Menus</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.WidgetMenu}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Menus</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Menus</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getMenus_Menus()
	 * @model containment="true"
	 * @generated
	 */
	EList<WidgetMenu> getMenus();

} // Menus
