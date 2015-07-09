/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel;

import com.odcgroup.page.metamodel.PropertyType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Palette</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.Palette#getGroups <em>Groups</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Palette#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Palette#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.Palette#getPropertyValue <em>Property Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.uimodel.UIModelPackage#getPalette()
 * @model
 * @generated
 */
public interface Palette extends EObject {
	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.uimodel.PaletteGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPalette_Groups()
	 * @model containment="true"
	 * @generated
	 */
	EList<PaletteGroup> getGroups();

	/**
	 * Returns the value of the '<em><b>Widget Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Type</em>' reference.
	 * @see #setWidgetType(WidgetType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPalette_WidgetType()
	 * @model
	 * @generated
	 */
	WidgetType getWidgetType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Palette#getWidgetType <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Widget Type</em>' reference.
	 * @see #getWidgetType()
	 * @generated
	 */
	void setWidgetType(WidgetType value);

	/**
	 * Returns the value of the '<em><b>Property Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Type</em>' reference.
	 * @see #setPropertyType(PropertyType)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPalette_PropertyType()
	 * @model
	 * @generated
	 */
	PropertyType getPropertyType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Palette#getPropertyType <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' reference.
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(PropertyType value);

	/**
	 * Returns the value of the '<em><b>Property Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Value</em>' attribute.
	 * @see #setPropertyValue(String)
	 * @see com.odcgroup.page.uimodel.UIModelPackage#getPalette_PropertyValue()
	 * @model
	 * @generated
	 */
	String getPropertyValue();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.uimodel.Palette#getPropertyValue <em>Property Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Value</em>' attribute.
	 * @see #getPropertyValue()
	 * @generated
	 */
	void setPropertyValue(String value);

} // Palette