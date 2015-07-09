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

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.SimplePropertyTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.SimplePropertyTransformerImpl#getPropertyType <em>Property Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SimplePropertyTransformerImpl extends AbstractPropertyTransformerImpl implements SimplePropertyTransformer {
	/**
	 * The cached value of the '{@link #getPropertyType() <em>Property Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyType()
	 * @generated
	 * @ordered
	 */
	protected PropertyType propertyType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimplePropertyTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.SIMPLE_PROPERTY_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType getPropertyType() {
		if (propertyType != null && propertyType.eIsProxy()) {
			InternalEObject oldPropertyType = (InternalEObject)propertyType;
			propertyType = (PropertyType)eResolveProxy(oldPropertyType);
			if (propertyType != oldPropertyType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE, oldPropertyType, propertyType));
			}
		}
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return PropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType basicGetPropertyType() {
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newPropertyType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyType(PropertyType newPropertyType) {
		PropertyType oldPropertyType = propertyType;
		propertyType = newPropertyType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE, oldPropertyType, propertyType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE:
				if (resolve) return getPropertyType();
				return basicGetPropertyType();
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
			case TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE:
				setPropertyType((PropertyType)newValue);
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
			case TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE:
				setPropertyType((PropertyType)null);
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
			case TransformModelPackage.SIMPLE_PROPERTY_TRANSFORMER__PROPERTY_TYPE:
				return propertyType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Returns true if the PropertyTransformer is designed to transform the specified PropertyType.
	 * 
	 * @param property The Property to test
	 * @return boolean True if the PropertyTransformer is designed to transform the specified PropertyType
	 */
	public boolean isTransformer(Property property) {
		return getPropertyType().equals(property.getType());
	}	

} //SimplePropertyTransformerImpl