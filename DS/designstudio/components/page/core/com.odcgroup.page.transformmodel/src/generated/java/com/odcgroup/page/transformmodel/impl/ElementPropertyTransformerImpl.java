/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.transformmodel.impl;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.transformmodel.ElementPropertyTransformer;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Property Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.ElementPropertyTransformerImpl#getElementName <em>Element Name</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.ElementPropertyTransformerImpl#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementPropertyTransformerImpl extends SimplePropertyTransformerImpl implements ElementPropertyTransformer {

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
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected Namespace namespace;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementPropertyTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.ELEMENT_PROPERTY_TRANSFORMER;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME, oldElementName, elementName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Namespace getNamespace() {
		if (namespace != null && namespace.eIsProxy()) {
			InternalEObject oldNamespace = (InternalEObject)namespace;
			namespace = (Namespace)eResolveProxy(oldNamespace);
			if (namespace != oldNamespace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE, oldNamespace, namespace));
			}
		}
		
		if (namespace == null) {
			return getModel().getDefaultNamespace();
		}
		
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace basicGetNamespace() {
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newNamespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(Namespace newNamespace) {
		Namespace oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME:
				return getElementName();
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE:
				if (resolve) return getNamespace();
				return basicGetNamespace();
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
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME:
				setElementName((String)newValue);
				return;
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE:
				setNamespace((Namespace)newValue);
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
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME:
				setElementName(ELEMENT_NAME_EDEFAULT);
				return;
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE:
				setNamespace((Namespace)null);
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
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__ELEMENT_NAME:
				return ELEMENT_NAME_EDEFAULT == null ? elementName != null : !ELEMENT_NAME_EDEFAULT.equals(elementName);
			case TransformModelPackage.ELEMENT_PROPERTY_TRANSFORMER__NAMESPACE:
				return namespace != null;
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
		
		Namespace ns = getNamespace();
		Element element = context.getDocument().createElementNS(ns.getUri(), getElementName());
		element.setPrefix(ns.getPrefix());		
		
//		if (translationExists(context, property)) {
//			// In this case we need to create an additional <i18n:text> element
//			context.getParentElement().appendChild(element);
//			String key = "TOBEGENERATED";
//			addI18nElement(context, element, key);
//		} else {
			context.getParentElement().appendChild(element);
			element.setTextContent(property.getValue());
//		}
	}	
	
	/**
	 * Creates an i18n element and adds it to the parent Element.
	 * The value of the property is the text content of the i18 element.
	 * Example: 
	 * If Property value = "toto"
	 * <i18n:text>toto</i18n:text>
	 * 
	 * @param context The WidgetTransformerContext
	 * @param parent The element to append the I18n element to
	 * @param key the message key
	 */
	private void addI18nElement(WidgetTransformerContext context, Element parent, String key) {
		Namespace ns = context.getTransformModel().findNamespace(I18N_NAMESPACE_URI);
		Element e = context.getDocument().createElementNS(ns.getUri(), I18N_TEXT_ELEMENT_NAME);
		e.setPrefix(ns.getPrefix());
		parent.appendChild(e);
		e.setTextContent(key);
	}

} //ElementPropertyTransformerImpl