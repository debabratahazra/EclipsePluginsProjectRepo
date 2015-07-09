/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.integration.model.ruletranslation.impl;

import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Translation Repo</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.visualrules.integration.model.ruletranslation.impl.RuleTranslationRepoImpl#getCodeMap <em>Code Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleTranslationRepoImpl extends EObjectImpl implements RuleTranslationRepo {
	/**
	 * The cached value of the '{@link #getCodeMap() <em>Code Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, RuleMessageProxy> codeMap;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleTranslationRepoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuletranslationPackage.Literals.RULE_TRANSLATION_REPO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, RuleMessageProxy> getCodeMap() {
		if (codeMap == null) {
			codeMap = new EcoreEMap<String,RuleMessageProxy>(RuletranslationPackage.Literals.RULE_TRANSLATION_MAP, RuleTranslationMapImpl.class, this, RuletranslationPackage.RULE_TRANSLATION_REPO__CODE_MAP);
		}
		return codeMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RuletranslationPackage.RULE_TRANSLATION_REPO__CODE_MAP:
				return ((InternalEList<?>)getCodeMap()).basicRemove(otherEnd, msgs);
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
			case RuletranslationPackage.RULE_TRANSLATION_REPO__CODE_MAP:
				if (coreType) return getCodeMap();
				else return getCodeMap().map();
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
			case RuletranslationPackage.RULE_TRANSLATION_REPO__CODE_MAP:
				((EStructuralFeature.Setting)getCodeMap()).set(newValue);
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
			case RuletranslationPackage.RULE_TRANSLATION_REPO__CODE_MAP:
				getCodeMap().clear();
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
			case RuletranslationPackage.RULE_TRANSLATION_REPO__CODE_MAP:
				return codeMap != null && !codeMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RuleTranslationRepoImpl
