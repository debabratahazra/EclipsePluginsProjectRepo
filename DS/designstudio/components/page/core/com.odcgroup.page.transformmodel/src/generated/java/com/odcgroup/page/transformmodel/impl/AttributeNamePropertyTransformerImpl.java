/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.AttributeNamePropertyTransformer;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.TransformerConstants;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.XSPConstants;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Name Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.AttributeNamePropertyTransformerImpl#getAttributeName <em>Attribute Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeNamePropertyTransformerImpl extends SimplePropertyTransformerImpl implements AttributeNamePropertyTransformer {
	
	/**
	 * The default value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected static final String ATTRIBUTE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributeName() <em>Attribute Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeName()
	 * @generated
	 * @ordered
	 */
	protected String attributeName = ATTRIBUTE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeNamePropertyTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newAttributeName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeName(String newAttributeName) {
		String oldAttributeName = attributeName;
		attributeName = newAttributeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME, oldAttributeName, attributeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME:
				return getAttributeName();
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
			case TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME:
				setAttributeName((String)newValue);
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
			case TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME:
				setAttributeName(ATTRIBUTE_NAME_EDEFAULT);
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
			case TransformModelPackage.ATTRIBUTE_NAME_PROPERTY_TRANSFORMER__ATTRIBUTE_NAME:
				return ATTRIBUTE_NAME_EDEFAULT == null ? attributeName != null : !ATTRIBUTE_NAME_EDEFAULT.equals(attributeName);
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
		result.append(" (attributeName: ");
		result.append(attributeName);
		result.append(')');
		return result.toString();
	}

	/**
	 * Transforms the Property.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param property The Property to transform
	 */
	public void transform(WidgetTransformerContext context, Property property) {
		if (TransformUtils.isDefaultOrEmpty(property)) {
			// Don't append the value if it has not changed otherwise the XSP pages will be huge
			return;
		}
		
		
		String name = getAttributeName();
		String value = property.getValue();
		
		// If the value contains a "<" we consider that it probably contains XML.
		// In this case we create an <xsp:attribute> element. Although this supposition
		// is not 100% correct it should work correctly at runtime.
		if (value.contains("<")) {
			// Create the element <xsp:attribute
			Namespace ns = context.getTransformModel().findNamespace(XSPConstants.XSP_NAMESPACE_URI);
			Element element = context.getDocument().createElementNS(ns.getUri(), TransformerConstants.ATTRIBUTE_ELEMENT_NAME);
			element.setPrefix(ns.getPrefix());
			TransformUtils.appendChild(context, element);	
			
			// Create the attribute name="toto"
			Attr a = context.getDocument().createAttribute(TransformerConstants.NAME_ATTRIBUTE_NAME);
			a.setValue(name);	
			element.setAttributeNode(a);	
			
			// Append the contents
			NodeList nl = TransformUtils.transformXmlStringToNodelist(value);
			TransformUtils.appendNodes(element, nl);
		} else {
			Attr a = context.getDocument().createAttribute(name);
			a.setValue(value);	
			context.getParentElement().setAttributeNode(a);	
		}		
	}	
	
} //AttributeNamePropertyTransformerImpl