/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.odcgroup.page.transformmodel.AbstractPropertyTransformer;
import com.odcgroup.page.transformmodel.TransformModel;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.AbstractPropertyTransformerImpl#getModel <em>Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractPropertyTransformerImpl extends MinimalEObjectImpl.Container implements AbstractPropertyTransformer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractPropertyTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.ABSTRACT_PROPERTY_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return TransformModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformModel getModel() {
		if (eContainerFeatureID() != TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL) return null;
		return (TransformModel)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newModel
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModel(TransformModel newModel, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newModel, TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newModel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModel(TransformModel newModel) {
		if (newModel != eInternalContainer() || (eContainerFeatureID() != TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL && newModel != null)) {
			if (EcoreUtil.isAncestor(this, newModel))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newModel != null)
				msgs = ((InternalEObject)newModel).eInverseAdd(this, TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS, TransformModel.class, msgs);
			msgs = basicSetModel(newModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL, newModel, newModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetModel((TransformModel)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				return basicSetModel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				return eInternalContainer().eInverseRemove(this, TransformModelPackage.TRANSFORM_MODEL__PROPERTY_TRANSFORMERS, TransformModel.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				return getModel();
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
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				setModel((TransformModel)newValue);
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
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				setModel((TransformModel)null);
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
			case TransformModelPackage.ABSTRACT_PROPERTY_TRANSFORMER__MODEL:
				return getModel() != null;
		}
		return super.eIsSet(featureID);
	}

} //AbstractPropertyTransformerImpl