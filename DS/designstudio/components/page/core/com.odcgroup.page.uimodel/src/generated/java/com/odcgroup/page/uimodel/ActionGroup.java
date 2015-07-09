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
 * A representation of the model object '<em><b>Action Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.ActionGroup#getActions <em>Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getActionGroup()
 * @model
 * @generated
 */
public interface ActionGroup extends NamedType {

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getActionGroup_Actions()
	 * @model
	 * @generated
	 */
	EList<Action> getActions();
} // ActionGroup
