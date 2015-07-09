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
import org.w3c.dom.Element;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.GenericWidgetTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformer;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.util.ClassUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.GenericWidgetTransformerImpl#getImplementationClass <em>Implementation Class</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenericWidgetTransformerImpl extends SimpleWidgetTransformerImpl implements GenericWidgetTransformer {

	/** The implementation of the WidgetTransformer. */
	private WidgetTransformer widgetTransformer;
	
	/**
	 * The default value of the '{@link #getImplementationClass() <em>Implementation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationClass()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationClass() <em>Implementation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationClass()
	 * @generated
	 * @ordered
	 */
	protected String implementationClass = IMPLEMENTATION_CLASS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.GENERIC_WIDGET_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationClass() {
		return implementationClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newImplementationClass
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationClass(String newImplementationClass) {
		String oldImplementationClass = implementationClass;
		implementationClass = newImplementationClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS, oldImplementationClass, implementationClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS:
				return getImplementationClass();
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
			case TransformModelPackage.GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS:
				setImplementationClass((String)newValue);
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
			case TransformModelPackage.GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS:
				setImplementationClass(IMPLEMENTATION_CLASS_EDEFAULT);
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
			case TransformModelPackage.GENERIC_WIDGET_TRANSFORMER__IMPLEMENTATION_CLASS:
				return IMPLEMENTATION_CLASS_EDEFAULT == null ? implementationClass != null : !IMPLEMENTATION_CLASS_EDEFAULT.equals(implementationClass);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		WidgetTransformer at = getWidgetTransformer();
		at.transform(context, widget);		
	}

	/**
	 * Gets the WidgetTransformer.
	 * 
	 * @return WidgetTransformer The WidgetTransformer
	 */
	private WidgetTransformer getWidgetTransformer() {
		if (widgetTransformer == null) {
			try {
				ClassLoader cl = getClass().getClassLoader();
				Object[] arguments = {getWidgetType()};
				widgetTransformer = (WidgetTransformer) 
					ClassUtils.newInstance(
							cl, 
							getImplementationClass(), 
							arguments, 
							WidgetTransformer.class);
			} catch (Exception ex) {
				Logger.error("This widget transformer class doesn't exist: "+getImplementationClass(), ex);
			}
		}
		return widgetTransformer;
	}	

	/**
	 * Returns true if the WidgetTransformer is designed to transform the specified WidgetType.
	 * 
	 * @param widget The Widget to test
	 * @return boolean True if the WidgetTransformer is designed to transform the specified WidgetType
	 */
	public boolean isTransformer(Widget widget) {
		WidgetTransformer wt = getWidgetTransformer();
		if (wt != null) {
			return wt.isTransformer(widget);
		}
		return false;
	}
	
	/**
	 * Gets the Xml element which represents the parent Element to which children will be attached.
	 * Note that this does not return all the XML that this transformer will generate. It is essentially
	 * used to help in the content-assist and auto-completion facilities.
	 *  
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to get the Element for
	 * @return Element The Element
	 */
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {	
		return getWidgetTransformer().getParentElement(context, widget);
	}	

} //GenericWidgetTransformerImpl