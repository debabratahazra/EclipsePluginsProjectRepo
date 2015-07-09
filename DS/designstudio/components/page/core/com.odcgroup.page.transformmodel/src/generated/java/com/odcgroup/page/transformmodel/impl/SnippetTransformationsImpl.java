/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.transformmodel.SnippetTransformation;
import com.odcgroup.page.transformmodel.SnippetTransformations;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Snippet Transformations</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.SnippetTransformationsImpl#getTransformations <em>Transformations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SnippetTransformationsImpl extends MinimalEObjectImpl.Container implements SnippetTransformations {
	/**
	 * The cached value of the '{@link #getTransformations() <em>Transformations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformations()
	 * @generated
	 * @ordered
	 */
	protected EList<SnippetTransformation> transformations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SnippetTransformationsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.SNIPPET_TRANSFORMATIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SnippetTransformation> getTransformations() {
		if (transformations == null) {
			transformations = new EObjectContainmentEList<SnippetTransformation>(SnippetTransformation.class, this, TransformModelPackage.SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS);
		}
		return transformations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS:
				return ((InternalEList<?>)getTransformations()).basicRemove(otherEnd, msgs);
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
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS:
				return getTransformations();
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
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS:
				getTransformations().clear();
				getTransformations().addAll((Collection<? extends SnippetTransformation>)newValue);
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
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS:
				getTransformations().clear();
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
			case TransformModelPackage.SNIPPET_TRANSFORMATIONS__TRANSFORMATIONS:
				return transformations != null && !transformations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SnippetTransformationsImpl
