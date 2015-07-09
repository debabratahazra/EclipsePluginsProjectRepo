/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.WidgetSnippet;
import com.odcgroup.page.metamodel.WidgetType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Widget Snippet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetSnippetImpl#getWidgetType <em>Widget Type</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.WidgetSnippetImpl#getSnippets <em>Snippets</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WidgetSnippetImpl extends MinimalEObjectImpl.Container implements WidgetSnippet {
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
	 * The cached value of the '{@link #getSnippets() <em>Snippets</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSnippets()
	 * @generated
	 * @ordered
	 */
	protected EList<SnippetType> snippets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WidgetSnippetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.WIDGET_SNIPPET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType getWidgetType() {
		if (widgetType != null && widgetType.eIsProxy()) {
			InternalEObject oldWidgetType = (InternalEObject)widgetType;
			widgetType = (WidgetType)eResolveProxy(oldWidgetType);
			if (widgetType != oldWidgetType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MetaModelPackage.WIDGET_SNIPPET__WIDGET_TYPE, oldWidgetType, widgetType));
			}
		}
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetType basicGetWidgetType() {
		return widgetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidgetType(WidgetType newWidgetType) {
		WidgetType oldWidgetType = widgetType;
		widgetType = newWidgetType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetaModelPackage.WIDGET_SNIPPET__WIDGET_TYPE, oldWidgetType, widgetType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SnippetType> getSnippets() {
		if (snippets == null) {
			snippets = new EObjectResolvingEList<SnippetType>(SnippetType.class, this, MetaModelPackage.WIDGET_SNIPPET__SNIPPETS);
		}
		return snippets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.WIDGET_SNIPPET__WIDGET_TYPE:
				if (resolve) return getWidgetType();
				return basicGetWidgetType();
			case MetaModelPackage.WIDGET_SNIPPET__SNIPPETS:
				return getSnippets();
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
			case MetaModelPackage.WIDGET_SNIPPET__WIDGET_TYPE:
				setWidgetType((WidgetType)newValue);
				return;
			case MetaModelPackage.WIDGET_SNIPPET__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends SnippetType>)newValue);
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
			case MetaModelPackage.WIDGET_SNIPPET__WIDGET_TYPE:
				setWidgetType((WidgetType)null);
				return;
			case MetaModelPackage.WIDGET_SNIPPET__SNIPPETS:
				getSnippets().clear();
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
			case MetaModelPackage.WIDGET_SNIPPET__WIDGET_TYPE:
				return widgetType != null;
			case MetaModelPackage.WIDGET_SNIPPET__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WidgetSnippetImpl
