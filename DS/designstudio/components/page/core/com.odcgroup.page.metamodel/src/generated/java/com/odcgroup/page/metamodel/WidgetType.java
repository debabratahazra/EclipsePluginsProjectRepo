/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Widget Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#getPropertyTypes <em>Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#getLibrary <em>Library</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#getExcludedPropertyTypes <em>Excluded Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#getPropertyFilterClass <em>Property Filter Class</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#isDerivable <em>Derivable</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#isStrictAccountability <em>Strict Accountability</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#isDomainWidget <em>Domain Widget</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#isCanDropDomainElement <em>Can Drop Domain Element</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.WidgetType#isRichtext <em>Richtext</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType()
 * @model
 * @generated
 */
public interface WidgetType extends NamedType {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(WidgetType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_Parent()
	 * @model
	 * @generated
	 */
	WidgetType getParent();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(WidgetType value);

	/**
	 * Returns the value of the '<em><b>Property Types</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Types</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Types</em>' reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_PropertyTypes()
	 * @model
	 * @generated
	 */
	EList<PropertyType> getPropertyTypes();

	/**
	 * Returns the value of the '<em><b>Library</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTypes <em>Widget Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' container reference.
	 * @see #setLibrary(WidgetLibrary)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_Library()
	 * @see com.odcgroup.page.metamodel.WidgetLibrary#getWidgetTypes
	 * @model opposite="widgetTypes" transient="false"
	 * @generated
	 */
	WidgetLibrary getLibrary();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#getLibrary <em>Library</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' container reference.
	 * @see #getLibrary()
	 * @generated
	 */
	void setLibrary(WidgetLibrary value);

	/**
	 * Returns the value of the '<em><b>Excluded Property Types</b></em>' reference list.
	 * The list contents are of type {@link com.odcgroup.page.metamodel.PropertyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excluded Property Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excluded Property Types</em>' reference list.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_ExcludedPropertyTypes()
	 * @model
	 * @generated
	 */
	EList<PropertyType> getExcludedPropertyTypes();

	/**
	 * Returns the value of the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Display Name</em>' attribute.
	 * @see #setDisplayName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_DisplayName()
	 * @model
	 * @generated
	 */
	String getDisplayName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#getDisplayName <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Display Name</em>' attribute.
	 * @see #getDisplayName()
	 * @generated
	 */
	void setDisplayName(String value);

	/**
	 * Returns the value of the '<em><b>Property Filter Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Filter Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Filter Class</em>' attribute.
	 * @see #setPropertyFilterClass(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_PropertyFilterClass()
	 * @model
	 * @generated
	 */
	String getPropertyFilterClass();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#getPropertyFilterClass <em>Property Filter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Filter Class</em>' attribute.
	 * @see #getPropertyFilterClass()
	 * @generated
	 */
	void setPropertyFilterClass(String value);

	/**
	 * Returns the value of the '<em><b>Derivable</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Derivable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Derivable</em>' attribute.
	 * @see #setDerivable(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_Derivable()
	 * @model default="false"
	 * @generated
	 */
	boolean isDerivable();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#isDerivable <em>Derivable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Derivable</em>' attribute.
	 * @see #isDerivable()
	 * @generated
	 */
	void setDerivable(boolean value);

	/**
	 * Returns the value of the '<em><b>Strict Accountability</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strict Accountability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strict Accountability</em>' attribute.
	 * @see #setStrictAccountability(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_StrictAccountability()
	 * @model default="false"
	 * @generated
	 */
	boolean isStrictAccountability();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#isStrictAccountability <em>Strict Accountability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strict Accountability</em>' attribute.
	 * @see #isStrictAccountability()
	 * @generated
	 */
	void setStrictAccountability(boolean value);

	/**
	 * Returns the value of the '<em><b>Domain Widget</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Widget</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Widget</em>' attribute.
	 * @see #setDomainWidget(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_DomainWidget()
	 * @model default="false"
	 * @generated
	 */
	boolean isDomainWidget();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#isDomainWidget <em>Domain Widget</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Widget</em>' attribute.
	 * @see #isDomainWidget()
	 * @generated
	 */
	void setDomainWidget(boolean value);

	/**
	 * Returns the value of the '<em><b>Can Drop Domain Element</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can Drop Domain Element</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Can Drop Domain Element</em>' attribute.
	 * @see #setCanDropDomainElement(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_CanDropDomainElement()
	 * @model default="false"
	 * @generated
	 */
	boolean isCanDropDomainElement();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#isCanDropDomainElement <em>Can Drop Domain Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Can Drop Domain Element</em>' attribute.
	 * @see #isCanDropDomainElement()
	 * @generated
	 */
	void setCanDropDomainElement(boolean value);

	/**
	 * Returns the value of the '<em><b>Richtext</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Richtext</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Richtext</em>' attribute.
	 * @see #setRichtext(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getWidgetType_Richtext()
	 * @model default="false"
	 * @generated
	 */
	boolean isRichtext();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.WidgetType#isRichtext <em>Richtext</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Richtext</em>' attribute.
	 * @see #isRichtext()
	 * @generated
	 */
	void setRichtext(boolean value);

	/**
	 * Gets all the PropertyTypes of this Widget. This includes the PropertyTypes
	 * of all the supertypes (parents) of this WidgetType. Excluded PropertyTypes
	 * are not added to the Map. Note that this can be fairly complex. In order
	 * to determine if we should include / exclude a PropertyType we remount the
	 * ancestor tree looking for the excluded PropertyType. If the excluded PropertyType
	 * has a larger index, in other words, is defined in a more distant ancestor
	 * than the PropertyType is included in the result otherwise it is excluded.
	 * 
	 * For example:
	 * 
	 * GreatGrandparent - Include PropertyType
	 * GreatParent - Exclude PropertyType
	 * Parent - Not defined
	 * Child - Not defined
	 * In this case the PropertyType is EXCLUDED from the child.
	 * 
	 * GreatGrandparent - Include PropertyType
	 * GreatParent - Exclude PropertyType
	 * Parent - Include PropertyType
	 * Child - Not defined
	 * In this case the PropertyType is INCLUDED from the child.
	 * 
	 * @return Map An unmodifiable Map of PropertyTypes
	 *		Key - Name of the PropertyType
	 *		Value - The PropertyType
	 */
	public Map<String, PropertyType> getAllPropertyTypes();	
	
	/**
	 * Returns true if the WidgetType is an instance of the specified WidgetType.
	 * This method attempts to simulate the instanceof keyword in Java.
	 * 
	 * @param type The WidgetType to test
	 * @return boolean True if the WidgetType is an instance of the specified WidgetType
	 */
	public boolean isInstanceOf(WidgetType type);
	
	/**
	 * Finds the PropertyType given the name.
	 * 
	 * @param name The name of the PropertyType
	 * @param ignoreCase True if we should ignore the case in the comparison
	 * @return PropertyType The PropertyType or null if no PropertyType can be found
	 */
	public PropertyType findPropertyType(String name, boolean ignoreCase);	
	
} // WidgetType