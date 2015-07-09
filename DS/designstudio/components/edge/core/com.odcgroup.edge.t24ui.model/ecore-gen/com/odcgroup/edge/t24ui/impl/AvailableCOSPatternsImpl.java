/**
 */
package com.odcgroup.edge.t24ui.impl;

import com.odcgroup.edge.t24ui.AvailableCOSPatterns;
import com.odcgroup.edge.t24ui.T24UIPackage;

import com.odcgroup.edge.t24ui.cos.pattern.COSPattern;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Available COS Patterns</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.AvailableCOSPatternsImpl#getPatterns <em>Patterns</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AvailableCOSPatternsImpl extends EObjectImpl implements AvailableCOSPatterns {
	/**
	 * The cached value of the '{@link #getPatterns() <em>Patterns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<COSPattern> patterns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AvailableCOSPatternsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return T24UIPackage.Literals.AVAILABLE_COS_PATTERNS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<COSPattern> getPatterns() {
		if (patterns == null) {
			patterns = new EObjectContainmentEList<COSPattern>(COSPattern.class, this, T24UIPackage.AVAILABLE_COS_PATTERNS__PATTERNS);
		}
		return patterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case T24UIPackage.AVAILABLE_COS_PATTERNS__PATTERNS:
				return ((InternalEList<?>)getPatterns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case T24UIPackage.AVAILABLE_COS_PATTERNS__PATTERNS:
				return getPatterns();
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
			case T24UIPackage.AVAILABLE_COS_PATTERNS__PATTERNS:
				getPatterns().clear();
				getPatterns().addAll((Collection<? extends COSPattern>)newValue);
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
			case T24UIPackage.AVAILABLE_COS_PATTERNS__PATTERNS:
				getPatterns().clear();
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
			case T24UIPackage.AVAILABLE_COS_PATTERNS__PATTERNS:
				return patterns != null && !patterns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AvailableCOSPatternsImpl
