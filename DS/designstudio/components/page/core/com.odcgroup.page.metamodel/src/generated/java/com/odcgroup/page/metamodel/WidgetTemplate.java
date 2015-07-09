/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Widget Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetTemplate#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetTemplate#getContents <em>Contents</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetTemplate#getPropertyTemplates <em>Property Templates</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetTemplate#getEventTemplates <em>Event Templates</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetTemplate()
 * @model
 * @generated
 */
public interface WidgetTemplate extends NamedType {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(WidgetType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetTemplate_Type()
	 * @model
	 * @generated
	 */
	WidgetType getType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetTemplate#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(WidgetType value);

	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetTemplate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetTemplate_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<WidgetTemplate> getContents();

	/**
	 * Returns the value of the '<em><b>Property Templates</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.PropertyTemplate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Templates</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetTemplate_PropertyTemplates()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyTemplate> getPropertyTemplates();
	
	/**
	 * Returns the value of the '<em><b>Event Templates</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.EventTemplate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Templates</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetTemplate_EventTemplates()
	 * @model containment="true"
	 * @generated
	 */
	EList<EventTemplate> getEventTemplates();

	/**
	 * Gets the property template with the specified name. Returns null
	 * if the PropertyTemplate does not exist.
	 * 
	 * @param name The name of the property template
	 * @return PropertyTemplate The PropertyTemplate
	 */
	public PropertyTemplate getPropertyTemplate(String name);	
	
    /**
     * Sets the value of the PropertyTemplate.
     * 
     * @param propertyName The name of the property
     * @param value The value to set
     */
    public void setPropertyTemplateValue(String propertyName, String value);	

} // WidgetTemplate