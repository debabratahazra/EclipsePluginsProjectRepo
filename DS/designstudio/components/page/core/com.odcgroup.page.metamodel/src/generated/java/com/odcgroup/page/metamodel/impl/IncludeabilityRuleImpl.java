/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.page.metamodel.IncludeabilityRule;
import com.odcgroup.page.metamodel.InclusionType;
import com.odcgroup.page.metamodel.MetaModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Includeability Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.IncludeabilityRuleImpl#getInclusionType <em>Inclusion Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.IncludeabilityRuleImpl#isLimitedAccountability <em>Limited Accountability</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IncludeabilityRuleImpl extends AccountabilityRuleImpl implements IncludeabilityRule {
	/**
	 * The default value of the '{@link #getInclusionType() <em>Inclusion Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInclusionType()
	 * @generated
	 * @ordered
	 */
	protected static final InclusionType INCLUSION_TYPE_EDEFAULT = InclusionType.XINCLUDE_LITERAL;

	/**
	 * The cached value of the '{@link #getInclusionType() <em>Inclusion Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInclusionType()
	 * @generated
	 * @ordered
	 */
	protected InclusionType inclusionType = INCLUSION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isLimitedAccountability() <em>Limited Accountability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLimitedAccountability()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LIMITED_ACCOUNTABILITY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLimitedAccountability() <em>Limited Accountability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLimitedAccountability()
	 * @generated
	 * @ordered
	 */
	protected boolean limitedAccountability = LIMITED_ACCOUNTABILITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncludeabilityRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.INCLUDEABILITY_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return InclusionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InclusionType getInclusionType() {
		return inclusionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newInclusionType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInclusionType(InclusionType newInclusionType) {
		InclusionType oldInclusionType = inclusionType;
		inclusionType = newInclusionType == null ? INCLUSION_TYPE_EDEFAULT : newInclusionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.INCLUDEABILITY_RULE__INCLUSION_TYPE, oldInclusionType, inclusionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLimitedAccountability() {
		return limitedAccountability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLimitedAccountability(boolean newLimitedAccountability) {
		boolean oldLimitedAccountability = limitedAccountability;
		limitedAccountability = newLimitedAccountability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY, oldLimitedAccountability, limitedAccountability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.INCLUDEABILITY_RULE__INCLUSION_TYPE:
				return getInclusionType();
			case MetaModelPackage.INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY:
				return isLimitedAccountability();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetaModelPackage.INCLUDEABILITY_RULE__INCLUSION_TYPE:
				setInclusionType((InclusionType)newValue);
				return;
			case MetaModelPackage.INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY:
				setLimitedAccountability((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MetaModelPackage.INCLUDEABILITY_RULE__INCLUSION_TYPE:
				setInclusionType(INCLUSION_TYPE_EDEFAULT);
				return;
			case MetaModelPackage.INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY:
				setLimitedAccountability(LIMITED_ACCOUNTABILITY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MetaModelPackage.INCLUDEABILITY_RULE__INCLUSION_TYPE:
				return inclusionType != INCLUSION_TYPE_EDEFAULT;
			case MetaModelPackage.INCLUDEABILITY_RULE__LIMITED_ACCOUNTABILITY:
				return limitedAccountability != LIMITED_ACCOUNTABILITY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (inclusionType: ");
		result.append(inclusionType);
		result.append(", limitedAccountability: ");
		result.append(limitedAccountability);
		result.append(')');
		return result.toString();
	}

} //IncludeabilityRuleImpl