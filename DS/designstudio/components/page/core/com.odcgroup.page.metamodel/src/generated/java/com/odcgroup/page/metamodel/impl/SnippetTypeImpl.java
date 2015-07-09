/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.SnippetType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Snippet Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.SnippetTypeImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.SnippetTypeImpl#getPropertyTypes <em>Property Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SnippetTypeImpl extends NamedTypeImpl implements SnippetType {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected SnippetType parent;

	/**
	 * The cached value of the '{@link #getPropertyTypes() <em>Property Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyType> propertyTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SnippetTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.SNIPPET_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetType getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (SnippetType)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.SNIPPET_TYPE__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SnippetType basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(SnippetType newParent) {
		SnippetType oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.SNIPPET_TYPE__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyType> getPropertyTypes() {
		if (propertyTypes == null) {
			propertyTypes = new EObjectResolvingEList<PropertyType>(PropertyType.class, this, MetaModelPackage.SNIPPET_TYPE__PROPERTY_TYPES);
		}
		return propertyTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.SNIPPET_TYPE__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case MetaModelPackage.SNIPPET_TYPE__PROPERTY_TYPES:
				return getPropertyTypes();
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
			case MetaModelPackage.SNIPPET_TYPE__PARENT:
				setParent((SnippetType)newValue);
				return;
			case MetaModelPackage.SNIPPET_TYPE__PROPERTY_TYPES:
				getPropertyTypes().clear();
				getPropertyTypes().addAll((Collection<? extends PropertyType>)newValue);
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
			case MetaModelPackage.SNIPPET_TYPE__PARENT:
				setParent((SnippetType)null);
				return;
			case MetaModelPackage.SNIPPET_TYPE__PROPERTY_TYPES:
				getPropertyTypes().clear();
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
			case MetaModelPackage.SNIPPET_TYPE__PARENT:
				return parent != null;
			case MetaModelPackage.SNIPPET_TYPE__PROPERTY_TYPES:
				return propertyTypes != null && !propertyTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SnippetTypeImpl
