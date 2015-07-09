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

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.ElementNameWidgetTransformer;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Name Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl#getElementName <em>Element Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementNameWidgetTransformerImpl extends SimpleWidgetTransformerImpl implements ElementNameWidgetTransformer {
	
	/**
	 * The default value of the '{@link #getElementName() <em>Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementName() <em>Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementName()
	 * @generated
	 * @ordered
	 */
	protected String elementName = ELEMENT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementNameWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.ELEMENT_NAME_WIDGET_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getElementName() {
		return elementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newElementName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementName(String newElementName) {
		String oldElementName = elementName;
		elementName = newElementName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME, oldElementName, elementName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME:
				return getElementName();
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
			case TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME:
				setElementName((String)newValue);
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
			case TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME:
				setElementName(ELEMENT_NAME_EDEFAULT);
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
			case TransformModelPackage.ELEMENT_NAME_WIDGET_TRANSFORMER__ELEMENT_NAME:
				return ELEMENT_NAME_EDEFAULT == null ? elementName != null : !ELEMENT_NAME_EDEFAULT.equals(elementName);
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
		result.append(" (elementName: ");
		result.append(elementName);
		result.append(')');
		return result.toString();
	}

	/**
	 * Pre-transforms the Widget. This is an ideal place to create the 
	 * Element / Attr and update the WidgetTransformerContext ready
	 * for the next child.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#preTransform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void preTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		Namespace ns = getNamespace();
		Element element = context.getDocument().createElementNS(ns.getUri(), getElementName());
		element.setPrefix(ns.getPrefix());

		TransformUtils.appendChild(context, element);
		
		context.setParentElement(element);
	}

	/**
	 * Post-transforms the Widget. This is an ideal place to update the
	 * WidgetTransformerContext ready for the next-sibling.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#postTransform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void postTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		context.setParentElement((Element) context.getParentElement().getParentNode());
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
		return createElement(context, getNamespace().getUri(), getElementName());
	}
	
} //ElementNameWidgetTransformerImpl