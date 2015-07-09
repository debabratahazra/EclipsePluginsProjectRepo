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

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.Namespace;
import com.odcgroup.page.transformmodel.SimpleWidgetTransformer;
import com.odcgroup.page.transformmodel.TransformModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Widget Transformer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.SimpleWidgetTransformerImpl#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.transformmodel.impl.SimpleWidgetTransformerImpl#getNamespace <em>Namespace</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SimpleWidgetTransformerImpl extends AbstractWidgetTransformerImpl implements SimpleWidgetTransformer {
	/**
	 * The cached value of the '{@link #getWidgetType() <em>Widget Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgetType()
	 * @generated
	 * @ordered
	 */
	protected WidgetType widgetType;

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
	protected SimpleWidgetTransformerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransformModelPackage.Literals.SIMPLE_WIDGET_TRANSFORMER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getWidgetType() {
		if (widgetType != null && widgetType.eIsProxy()) {
			InternalEObject oldWidgetType = (InternalEObject)widgetType;
			widgetType = (WidgetType)eResolveProxy(oldWidgetType);
			if (widgetType != oldWidgetType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE, oldWidgetType, widgetType));
			}
		}
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return WidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetWidgetType() {
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidgetType
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidgetType(WidgetType newWidgetType) {
		WidgetType oldWidgetType = widgetType;
		widgetType = newWidgetType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE, oldWidgetType, widgetType));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__NAMESPACE, oldNamespace, namespace));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE:
				if (resolve) return getWidgetType();
				return basicGetWidgetType();
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__NAMESPACE:
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
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE:
				setWidgetType((WidgetType)newValue);
				return;
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__NAMESPACE:
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
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE:
				setWidgetType((WidgetType)null);
				return;
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__NAMESPACE:
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
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__WIDGET_TYPE:
				return widgetType != null;
			case TransformModelPackage.SIMPLE_WIDGET_TRANSFORMER__NAMESPACE:
				return namespace != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * Returns true if the WidgetTransformer is designed to transform the specified WidgetType.
	 * 
	 * @param widget The Widget to test
	 * @return boolean True if the WidgetTransformer is designed to transform the specified WidgetType
	 */
	public boolean isTransformer(Widget widget) {
		return getWidgetType().equals(widget.getType());
	}	

} //SimpleWidgetTransformerImpl