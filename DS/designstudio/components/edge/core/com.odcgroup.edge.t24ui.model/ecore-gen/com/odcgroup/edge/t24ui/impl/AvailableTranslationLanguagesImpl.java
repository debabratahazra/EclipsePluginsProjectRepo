/**
 */
package com.odcgroup.edge.t24ui.impl;

import com.odcgroup.edge.t24ui.AvailableTranslationLanguages;
import com.odcgroup.edge.t24ui.T24UIPackage;

import com.odcgroup.edge.t24ui.common.Language;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Available Translation Languages</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.AvailableTranslationLanguagesImpl#getLanguages <em>Languages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AvailableTranslationLanguagesImpl extends EObjectImpl implements AvailableTranslationLanguages {
	/**
	 * The cached value of the '{@link #getLanguages() <em>Languages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguages()
	 * @generated
	 * @ordered
	 */
	protected EList<Language> languages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AvailableTranslationLanguagesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return T24UIPackage.Literals.AVAILABLE_TRANSLATION_LANGUAGES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Language> getLanguages() {
		if (languages == null) {
			languages = new EObjectContainmentEList<Language>(Language.class, this, T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES);
		}
		return languages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES:
				return ((InternalEList<?>)getLanguages()).basicRemove(otherEnd, msgs);
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
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES:
				return getLanguages();
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
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES:
				getLanguages().clear();
				getLanguages().addAll((Collection<? extends Language>)newValue);
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
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES:
				getLanguages().clear();
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
			case T24UIPackage.AVAILABLE_TRANSLATION_LANGUAGES__LANGUAGES:
				return languages != null && !languages.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AvailableTranslationLanguagesImpl
