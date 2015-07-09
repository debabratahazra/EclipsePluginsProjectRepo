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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.w3c.dom.Element;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.ExtraParentWidgetTransformer;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.TransformModelPackage;
import com.odcgroup.page.transformmodel.TransformerConstants;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.util.MetaInfoRendererUtil;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extra Parent Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl#getParentElementName <em>Parent Element Name</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl#isOnlyAddIfRoot <em>Only Add If Root</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.ExtraParentWidgetTransformerImpl#getParentNamespace <em>Parent Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExtraParentWidgetTransformerImpl extends ElementNameWidgetTransformerImpl implements ExtraParentWidgetTransformer {
	/**
	 * The default value of the '{@link #getParentElementName() <em>Parent Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentElementName()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_ELEMENT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentElementName() <em>Parent Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentElementName()
	 * @generated
	 * @ordered
	 */
	protected String parentElementName = PARENT_ELEMENT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isOnlyAddIfRoot() <em>Only Add If Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOnlyAddIfRoot()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ONLY_ADD_IF_ROOT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOnlyAddIfRoot() <em>Only Add If Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOnlyAddIfRoot()
	 * @generated
	 * @ordered
	 */
	protected boolean onlyAddIfRoot = ONLY_ADD_IF_ROOT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParentNamespace() <em>Parent Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentNamespace()
	 * @generated
	 * @ordered
	 */
	protected Namespace parentNamespace;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtraParentWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.EXTRA_PARENT_WIDGET_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getParentElementName() {
		return parentElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParentElementName
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentElementName(String newParentElementName) {
		String oldParentElementName = parentElementName;
		parentElementName = newParentElementName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME, oldParentElementName, parentElementName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOnlyAddIfRoot() {
		return onlyAddIfRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newOnlyAddIfRoot
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnlyAddIfRoot(boolean newOnlyAddIfRoot) {
		boolean oldOnlyAddIfRoot = onlyAddIfRoot;
		onlyAddIfRoot = newOnlyAddIfRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT, oldOnlyAddIfRoot, onlyAddIfRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace getParentNamespace() {
		if (parentNamespace != null && parentNamespace.eIsProxy()) {
			InternalEObject oldParentNamespace = (InternalEObject)parentNamespace;
			parentNamespace = (Namespace)eResolveProxy(oldParentNamespace);
			if (parentNamespace != oldParentNamespace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE, oldParentNamespace, parentNamespace));
			}
		}
		return parentNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return Namespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Namespace basicGetParentNamespace() {
		return parentNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newParentNamespace
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentNamespace(Namespace newParentNamespace) {
		Namespace oldParentNamespace = parentNamespace;
		parentNamespace = newParentNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE, oldParentNamespace, parentNamespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME:
				return getParentElementName();
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT:
				return isOnlyAddIfRoot();
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE:
				if (resolve) return getParentNamespace();
				return basicGetParentNamespace();
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
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME:
				setParentElementName((String)newValue);
				return;
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT:
				setOnlyAddIfRoot((Boolean)newValue);
				return;
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE:
				setParentNamespace((Namespace)newValue);
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
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME:
				setParentElementName(PARENT_ELEMENT_NAME_EDEFAULT);
				return;
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT:
				setOnlyAddIfRoot(ONLY_ADD_IF_ROOT_EDEFAULT);
				return;
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE:
				setParentNamespace((Namespace)null);
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
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_ELEMENT_NAME:
				return PARENT_ELEMENT_NAME_EDEFAULT == null ? parentElementName != null : !PARENT_ELEMENT_NAME_EDEFAULT.equals(parentElementName);
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__ONLY_ADD_IF_ROOT:
				return onlyAddIfRoot != ONLY_ADD_IF_ROOT_EDEFAULT;
			case TransformModelPackage.EXTRA_PARENT_WIDGET_TRANSFORMER__PARENT_NAMESPACE:
				return parentNamespace != null;
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
		result.append(" (parentElementName: ");
		result.append(parentElementName);
		result.append(", onlyAddIfRoot: ");
		result.append(onlyAddIfRoot);
		result.append(')');
		return result.toString();
	}

	/**
	 * Pre-transforms the Widget. This is an ideal place to create the 
	 * Element / Attr and update the WidgetTransformerContext ready
	 * for the next child.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl#preTransform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void preTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		Element pe = context.getParentElement();
		if (isOnlyAddIfRoot() == false || pe.getParentNode() == null && isOnlyAddIfRoot() == true) {
			Namespace pns = getParentNamespace();
			Element newParent = context.getDocument().createElementNS(pns.getUri(), getParentElementName());
			newParent.setPrefix(pns.getPrefix());			
			TransformUtils.appendChild(context, newParent);
			
			context.setParentElement(newParent);
	        // add DS Header
	        MetaInfoRendererUtil.addComment(context, widget, newParent);
		}

		super.preTransform(context, widget);
	} 
	
	
	
	/**
	 * Post-transforms the Widget. This is an ideal place to update the
	 * WidgetTransformerContext ready for the next-sibling.
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.ElementNameWidgetTransformerImpl#postTransform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void postTransform(WidgetTransformerContext context, Widget widget) throws CoreException {
		context.setParentElement((Element) context.getParentElement().getParentNode());
	}	
	
	/**
	 * DS-2896 requires new nav:managerequest element for all the modules
	 * 
	 * @see com.odcgroup.page.transformmodel.impl.AbstractWidgetTransformerImpl#transformProperties(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException {
		super.transformProperties(context, widget);
		Element nav = createElement(context, TransformerConstants.NAV_NAMESPACE_URI, "managerequest");
		TransformUtils.convertToAttribute(context, nav, "nav-id", widget.getID());
		TransformUtils.appendChild(context.getParentElement(), nav);
		context.setParentElement(nav);
	}

} //ExtraParentWidgetTransformerImpl