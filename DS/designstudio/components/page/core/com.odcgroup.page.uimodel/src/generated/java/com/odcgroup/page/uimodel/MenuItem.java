/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import org.eclipse.emf.common.util.EList;

import com.odcgroup.page.metamodel.NamedType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Menu Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.MenuItem#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.MenuItem#getGroups <em>Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getMenuItem()
 * @model
 * @generated
 */
public interface MenuItem extends NamedType {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(MenuItem)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getMenuItem_Parent()
	 * @model
	 * @generated
	 */
	MenuItem getParent();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.MenuItem#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(MenuItem value);

	/**
	 * Returns the value of the '<em><b>Groups</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.ActionGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groups</em>' reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getMenuItem_Groups()
	 * @model
	 * @generated
	 */
	EList<ActionGroup> getGroups();

} // MenuItem
