/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.visualrules.generation.rulesbinding.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.visualrules.generation.rulesbinding.MdfBinding;
import com.odcgroup.visualrules.generation.rulesbinding.Rule;
import com.odcgroup.visualrules.generation.rulesbinding.RulesBindingPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mdf Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.visualrules.generation.rulesbinding.impl.MdfBindingImpl#getEntity <em>Entity</em>}</li>
 *   <li>{@link com.odcgroup.visualrules.generation.rulesbinding.impl.MdfBindingImpl#getRules <em>Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MdfBindingImpl extends MinimalEObjectImpl.Container implements MdfBinding {
	/**
	 * The cached value of the '{@link #getEntity() <em>Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntity()
	 * @generated
	 * @ordered
	 */
	protected MdfEntity entity;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MdfBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulesBindingPackage.Literals.MDF_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfEntity getEntity() {
		if (entity != null && ((EObject)entity).eIsProxy()) {
			InternalEObject oldEntity = (InternalEObject)entity;
			entity = (MdfEntity)eResolveProxy(oldEntity);
			if (entity != oldEntity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RulesBindingPackage.MDF_BINDING__ENTITY, oldEntity, entity));
			}
		}
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MdfEntity basicGetEntity() {
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntity(MdfEntity newEntity) {
		MdfEntity oldEntity = entity;
		entity = newEntity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulesBindingPackage.MDF_BINDING__ENTITY, oldEntity, entity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRules() {
		if (rules == null) {
			rules = new EObjectResolvingEList<Rule>(Rule.class, this, RulesBindingPackage.MDF_BINDING__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulesBindingPackage.MDF_BINDING__ENTITY:
				if (resolve) return getEntity();
				return basicGetEntity();
			case RulesBindingPackage.MDF_BINDING__RULES:
				return getRules();
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
			case RulesBindingPackage.MDF_BINDING__ENTITY:
				setEntity((MdfEntity)newValue);
				return;
			case RulesBindingPackage.MDF_BINDING__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Rule>)newValue);
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
			case RulesBindingPackage.MDF_BINDING__ENTITY:
				setEntity((MdfEntity)null);
				return;
			case RulesBindingPackage.MDF_BINDING__RULES:
				getRules().clear();
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
			case RulesBindingPackage.MDF_BINDING__ENTITY:
				return entity != null;
			case RulesBindingPackage.MDF_BINDING__RULES:
				return rules != null && !rules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MdfBindingImpl
