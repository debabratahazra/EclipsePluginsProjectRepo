/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.transformmodel.SnippetTransformation;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Snippet Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.SnippetTransformationImpl#getSnippetType <em>Snippet Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SnippetTransformationImpl extends MinimalEObjectImpl.Container implements SnippetTransformation {
	/**
	 * The cached value of the '{@link #getSnippetType() <em>Snippet Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSnippetType()
	 * @generated
	 * @ordered
	 */
	protected SnippetType snippetType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SnippetTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.SNIPPET_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetType getSnippetType() {
		if (snippetType != null && snippetType.eIsProxy()) {
			InternalEObject oldSnippetType = (InternalEObject)snippetType;
			snippetType = (SnippetType)eResolveProxy(oldSnippetType);
			if (snippetType != oldSnippetType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.SNIPPET_TRANSFORMATION__SNIPPET_TYPE, oldSnippetType, snippetType));
			}
		}
		return snippetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetType basicGetSnippetType() {
		return snippetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSnippetType(SnippetType newSnippetType) {
		SnippetType oldSnippetType = snippetType;
		snippetType = newSnippetType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.SNIPPET_TRANSFORMATION__SNIPPET_TYPE, oldSnippetType, snippetType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.SNIPPET_TRANSFORMATION__SNIPPET_TYPE:
				if (resolve) return getSnippetType();
				return basicGetSnippetType();
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
			case TransformModelPackage.SNIPPET_TRANSFORMATION__SNIPPET_TYPE:
				setSnippetType((SnippetType)newValue);
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
			case TransformModelPackage.SNIPPET_TRANSFORMATION__SNIPPET_TYPE:
				setSnippetType((SnippetType)null);
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
			case TransformModelPackage.SNIPPET_TRANSFORMATION__SNIPPET_TYPE:
				return snippetType != null;
		}
		return super.eIsSet(featureID);
	}

} //SnippetTransformationImpl
