/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Includeability Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.IncludeabilityRule#getInclusionType <em>Inclusion Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.IncludeabilityRule#isLimitedAccountability <em>Limited Accountability</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.odcgroup.page.metamodel.MetaModelPackage#getIncludeabilityRule()
 * @model
 * @generated
 */
public interface IncludeabilityRule extends AccountabilityRule {
	/**
	 * Returns the value of the '<em><b>Inclusion Type</b></em>' attribute.
	 * The default value is <code>"XINCLUDE"</code>.
	 * The literals are from the enumeration {@link com.odcgroup.page.metamodel.InclusionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inclusion Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inclusion Type</em>' attribute.
	 * @see com.odcgroup.page.metamodel.InclusionType
	 * @see #setInclusionType(InclusionType)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getIncludeabilityRule_InclusionType()
	 * @model default="XINCLUDE"
	 * @generated
	 */
	InclusionType getInclusionType();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.IncludeabilityRule#getInclusionType <em>Inclusion Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inclusion Type</em>' attribute.
	 * @see com.odcgroup.page.metamodel.InclusionType
	 * @see #getInclusionType()
	 * @generated
	 */
	void setInclusionType(InclusionType value);

	/**
	 * Returns the value of the '<em><b>Limited Accountability</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Limited Accountability</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Limited Accountability</em>' attribute.
	 * @see #setLimitedAccountability(boolean)
	 * @see com.odcgroup.page.metamodel.MetaModelPackage#getIncludeabilityRule_LimitedAccountability()
	 * @model default="false"
	 * @generated
	 */
	boolean isLimitedAccountability();

	/**
	 * Sets the value of the '{@link com.odcgroup.page.metamodel.IncludeabilityRule#isLimitedAccountability <em>Limited Accountability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Limited Accountability</em>' attribute.
	 * @see #isLimitedAccountability()
	 * @generated
	 */
	void setLimitedAccountability(boolean value);

} // IncludeabilityRule