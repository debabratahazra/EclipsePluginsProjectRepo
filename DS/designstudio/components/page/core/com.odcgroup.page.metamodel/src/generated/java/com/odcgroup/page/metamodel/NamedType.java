/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Named Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.NamedType#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.NamedType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.NamedType#getTranslationSupport <em>Translation Support</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.NamedType#getPropertiesHelpID <em>Properties Help ID</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.NamedType#getConceptHelpID <em>Concept Help ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getNamedType()
 * @model abstract="true"
 * @generated
 */
public interface NamedType extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getNamedType_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.NamedType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getNamedType_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.NamedType#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Translation Support</b></em>' attribute.
	 * The default value is <code>"NONE"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.page.metamodel.TranslationSupport}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Translation Support</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Translation Support</em>' attribute.
	 * @see com.odcgroup.page.metamodel.TranslationSupport
	 * @see #setTranslationSupport(TranslationSupport)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getNamedType_TranslationSupport()
	 * @model default="NONE" required="true"
	 * @generated
	 */
	TranslationSupport getTranslationSupport();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.NamedType#getTranslationSupport <em>Translation Support</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Translation Support</em>' attribute.
	 * @see com.odcgroup.page.metamodel.TranslationSupport
	 * @see #getTranslationSupport()
	 * @generated
	 */
	void setTranslationSupport(TranslationSupport value);
	
	/**
	 * Returns the value of the '<em><b>Properties Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties Help ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties Help ID</em>' attribute.
	 * @see #setPropertiesHelpID(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getNamedType_PropertiesHelpID()
	 * @model
	 * @generated
	 */
	String getPropertiesHelpID();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.NamedType#getPropertiesHelpID <em>Properties Help ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties Help ID</em>' attribute.
	 * @see #getPropertiesHelpID()
	 * @generated
	 */
	void setPropertiesHelpID(String value);

	/**
	 * Returns the value of the '<em><b>Concept Help ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concept Help ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concept Help ID</em>' attribute.
	 * @see #setConceptHelpID(String)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getNamedType_ConceptHelpID()
	 * @model
	 * @generated
	 */
	String getConceptHelpID();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.NamedType#getConceptHelpID <em>Concept Help ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concept Help ID</em>' attribute.
	 * @see #getConceptHelpID()
	 * @generated
	 */
	void setConceptHelpID(String value);

	/**
	 * @return <code>true</code> if instances of this widget type 
	 * could have translations, otherwise it returns <code>false</code>
	 * 
	 * @generated NOT
	 */
	boolean translationSupported();
	
} // NamedType