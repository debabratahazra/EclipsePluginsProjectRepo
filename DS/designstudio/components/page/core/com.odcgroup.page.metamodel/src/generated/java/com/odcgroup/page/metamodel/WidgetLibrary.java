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
 * A representation of the model object '<em><b>Widget Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTypes <em>Widget Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetLibrary#getPropertyTypes <em>Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTemplates <em>Widget Templates</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetLibrary()
 * @model
 * @generated
 */
public interface WidgetLibrary extends NamedType {
	/**
	 * Returns the value of the '<em><b>Widget Types</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetType}.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.metamodel.WidgetType#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Types</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetLibrary_WidgetTypes()
	 * @see com.odcgroup.page.metamodel.WidgetType#getLibrary
	 * @model opposite="library" containment="true"
	 * @generated
	 */
	EList<WidgetType> getWidgetTypes();

	/**
	 * Returns the value of the '<em><b>Property Types</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.PropertyType}.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.metamodel.PropertyType#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Types</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetLibrary_PropertyTypes()
	 * @see com.odcgroup.page.metamodel.PropertyType#getLibrary
	 * @model opposite="library" containment="true"
	 * @generated
	 */
	EList<PropertyType> getPropertyTypes();

	/**
	 * Returns the value of the '<em><b>Widget Templates</b></em>' containment reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.WidgetTemplate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widget Templates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widget Templates</em>' containment reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetLibrary_WidgetTemplates()
	 * @model containment="true"
	 * @generated
	 */
	EList<WidgetTemplate> getWidgetTemplates();

	/**
	 * Finds the WidgetType specified by its name.
	 * 
	 * @param name The name of the WidgetType
	 * @return WidgetType The WidgetType or null if no WidgetType of this name was found
	 */
	public WidgetType findWidgetType(String name);
	
	/**
	 * Finds the WidgetType specified by its name.
	 * 
	 * @param name The name of the WidgetType
	 * @param ignoreCase True if we should ignore the case in the comparison
	 * @return WidgetType The WidgetType or null if no WidgetType of this name was found
	 */
	public WidgetType findWidgetType(String name, boolean ignoreCase);	
	
	/**
	 * Finds the WidgetTemplate specified by its name.
	 * 
	 * @param name The name of the WidgetTemplate
	 * @return WidgetTemplate The WidgetTemplate or null if no WidgetTemplate of this name was found
	 */
	public WidgetTemplate findWidgetTemplate(String name);	
	
	/**
	 * Finds the PropertyType specified by its name.
	 * 
	 * @param name The name of the PropertyType
	 * @return PropertyType The PropertyType or null if no PropertyType of this name was found
	 */
	public PropertyType findPropertyType(String name);
	
	/**
	 * Finds the PropertyType specified by its name.
	 * 
	 * @param name The name of the PropertyType
	 * @param ignoreCase True if we should ignore the case in the comparison
	 * @return PropertyType The PropertyType or null if no WidgetType of this name was found
	 */
	public PropertyType findPropertyType(String name, boolean ignoreCase);

} // WidgetLibrary