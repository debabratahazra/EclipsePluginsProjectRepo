/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.GenericPropertyTransformer;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.util.ClassUtils;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Generic Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.GenericPropertyTransformerImpl#getImplementationClass <em>Implementation Class</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenericPropertyTransformerImpl extends SimplePropertyTransformerImpl implements GenericPropertyTransformer {

	/** The implementation of the PropertyTransformer. */
	private PropertyTransformer propertyTransformer;

	/**
	 * The default value of the '{@link #getImplementationClass() <em>Implementation Class</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getImplementationClass()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationClass() <em>Implementation Class</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getImplementationClass()
	 * @generated
	 * @ordered
	 */
	protected String implementationClass = IMPLEMENTATION_CLASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericPropertyTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.GENERIC_PROPERTY_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @return String <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationClass() {
		return implementationClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * 
	 * @param newImplementationClass
	 *            <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationClass(String newImplementationClass) {
		String oldImplementationClass = implementationClass;
		implementationClass = newImplementationClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS, oldImplementationClass, implementationClass));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS:
				return getImplementationClass();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS:
				setImplementationClass((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS:
				setImplementationClass(IMPLEMENTATION_CLASS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TransformModelPackage.GENERIC_PROPERTY_TRANSFORMER__IMPLEMENTATION_CLASS:
				return IMPLEMENTATION_CLASS_EDEFAULT == null ? implementationClass != null : !IMPLEMENTATION_CLASS_EDEFAULT.equals(implementationClass);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (implementationClass: ");
		result.append(implementationClass);
		result.append(')');
		return result.toString();
	}

	/**
	 * Returns true if the PropertyTransformer is designed to transform the specified PropertyType. This is handled by
	 * the GenericPropertyTransformer.
	 * 
	 * @param property
	 *            The Property to test
	 * @return boolean True if the PropertyTransformer is designed to transform the specified PropertyType
	 */
	public boolean isTransformer(Property property) {
		PropertyTransformer pt = getPropertyTransformer();
		if (pt != null) {
			return pt.isTransformer(property);
		}
		return false;
	}

	/**
	 * Transforms the Property.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param property
	 *            The Property to transform
	 * @exception CoreException
	 */
	public void transform(WidgetTransformerContext context, Property property) throws CoreException {
		PropertyTransformer pt = getPropertyTransformer();
		pt.transform(context, property);
	}

	/**
	 * Gets the PropertyTransformer.
	 * 
	 * @return PropertyTransformer The PropertyTransformer
	 */
	private PropertyTransformer getPropertyTransformer() {
		if (propertyTransformer == null) {
			try {
				ClassLoader cl = getClass().getClassLoader();
				Object[] arguments = { getPropertyType() };
				propertyTransformer = (PropertyTransformer) ClassUtils.newInstance(cl, getImplementationClass(),
						arguments, PropertyTransformer.class);
			} catch (Exception ex) {
				Logger.error("This property transformer class doesn't exist: " + getImplementationClass(), ex);
			}
		}
		return propertyTransformer;
	}

} // GenericPropertyTransformerImpl
