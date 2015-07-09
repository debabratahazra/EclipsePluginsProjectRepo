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
 * A representation of the model object '<em><b>Palette Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroup#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroup#getItems <em>Items</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.PaletteGroup#isExpanded <em>Expanded</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroup()
 * @model
 * @generated
 */
public interface PaletteGroup extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroup_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroup#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Items</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.PaletteGroupItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroup_Items()
	 * @model
	 * @generated
	 */
	EList<PaletteGroupItem> getItems();

	/**
	 * Returns the value of the '<em><b>Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expanded</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expanded</em>' attribute.
	 * @see #setExpanded(boolean)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPaletteGroup_Expanded()
	 * @model
	 * @generated
	 */
	boolean isExpanded();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.PaletteGroup#isExpanded <em>Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expanded</em>' attribute.
	 * @see #isExpanded()
	 * @generated
	 */
	void setExpanded(boolean value);

} // PaletteGroup