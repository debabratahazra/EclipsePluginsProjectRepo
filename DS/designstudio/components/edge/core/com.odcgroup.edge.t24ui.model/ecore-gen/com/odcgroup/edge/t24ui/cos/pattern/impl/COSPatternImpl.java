/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>COS Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.COSPatternImpl#getPatternSpecificAttributeNames <em>Pattern Specific Attribute Names</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class COSPatternImpl extends EObjectImpl implements COSPattern {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPatternSpecificAttributeNames() <em>Pattern Specific Attribute Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatternSpecificAttributeNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> patternSpecificAttributeNames;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected COSPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.COS_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.COS_PATTERN__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPatternSpecificAttributeNames() {
		if (patternSpecificAttributeNames == null) {
			patternSpecificAttributeNames = new EDataTypeUniqueEList<String>(String.class, this, PatternPackage.COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES);
		}
		return patternSpecificAttributeNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PatternPackage.COS_PATTERN__NAME:
				return getName();
			case PatternPackage.COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES:
				return getPatternSpecificAttributeNames();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PatternPackage.COS_PATTERN__NAME:
				setName((String)newValue);
				return;
			case PatternPackage.COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES:
				getPatternSpecificAttributeNames().clear();
				getPatternSpecificAttributeNames().addAll((Collection<? extends String>)newValue);
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
			case PatternPackage.COS_PATTERN__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PatternPackage.COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES:
				getPatternSpecificAttributeNames().clear();
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
			case PatternPackage.COS_PATTERN__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PatternPackage.COS_PATTERN__PATTERN_SPECIFIC_ATTRIBUTE_NAMES:
				return patternSpecificAttributeNames != null && !patternSpecificAttributeNames.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", patternSpecificAttributeNames: ");
		result.append(patternSpecificAttributeNames);
		result.append(')');
		return result.toString();
	}

} //COSPatternImpl
