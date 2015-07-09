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
 * A representation of the model object '<em><b>Accountability Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.AccountabilityRule#getChild <em>Child</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.AccountabilityRule#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.AccountabilityRule#getMinOccurs <em>Min Occurs</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.AccountabilityRule#getMaxOccurs <em>Max Occurs</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.AccountabilityRule#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountabilityRule()
 * @model
 * @generated
 */
public interface AccountabilityRule extends EObject {
	/**
	 * Returns the value of the '<em><b>Child</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child</em>' reference.
	 * @see #setChild(WidgetType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountabilityRule_Child()
	 * @model required="true"
	 * @generated
	 */
	WidgetType getChild();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.AccountabilityRule#getChild <em>Child</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Child</em>' reference.
	 * @see #getChild()
	 * @generated
	 */
	void setChild(WidgetType value);

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
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountabilityRule_Parent()
	 * @model required="true"
	 * @generated
	 */
	WidgetType getParent();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.AccountabilityRule#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(WidgetType value);

	/**
	 * Returns the value of the '<em><b>Min Occurs</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Occurs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Occurs</em>' attribute.
	 * @see #setMinOccurs(int)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountabilityRule_MinOccurs()
	 * @model default="0"
	 * @generated
	 */
	int getMinOccurs();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.AccountabilityRule#getMinOccurs <em>Min Occurs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Occurs</em>' attribute.
	 * @see #getMinOccurs()
	 * @generated
	 */
	void setMinOccurs(int value);

	/**
	 * Returns the value of the '<em><b>Max Occurs</b></em>' attribute.
	 * The default value is <code>"-1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Occurs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Occurs</em>' attribute.
	 * @see #setMaxOccurs(int)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountabilityRule_MaxOccurs()
	 * @model default="-1"
	 * @generated
	 */
	int getMaxOccurs();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.AccountabilityRule#getMaxOccurs <em>Max Occurs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Occurs</em>' attribute.
	 * @see #getMaxOccurs()
	 * @generated
	 */
	void setMaxOccurs(int value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getAccountabilityRule_Name()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	String getName();

} // AccountabilityRule