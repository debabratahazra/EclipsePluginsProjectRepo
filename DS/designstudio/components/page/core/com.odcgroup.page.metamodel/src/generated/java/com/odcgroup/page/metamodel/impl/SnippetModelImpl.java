/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.metamodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.page.metamodel.EventSnippet;
import com.odcgroup.page.metamodel.MetaModelPackage;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.SnippetModel;
import com.odcgroup.page.metamodel.SnippetType;
import com.odcgroup.page.metamodel.WidgetSnippet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Snippet Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.metamodel.impl.SnippetModelImpl#getSnippets <em>Snippets</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.SnippetModelImpl#getPropertyTypes <em>Property Types</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.SnippetModelImpl#getWidgets <em>Widgets</em>}</li>
 *   <li>{@link com.odcgroup.page.metamodel.impl.SnippetModelImpl#getEvents <em>Events</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SnippetModelImpl extends MinimalEObjectImpl.Container implements SnippetModel {
	/**
	 * The cached value of the '{@link #getSnippets() <em>Snippets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSnippets()
	 * @generated
	 * @ordered
	 */
	protected EList<SnippetType> snippets;

	/**
	 * The cached value of the '{@link #getPropertyTypes() <em>Property Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyType> propertyTypes;

	/**
	 * The cached value of the '{@link #getWidgets() <em>Widgets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidgets()
	 * @generated
	 * @ordered
	 */
	protected EList<WidgetSnippet> widgets;

	/**
	 * The cached value of the '{@link #getEvents() <em>Events</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvents()
	 * @generated
	 * @ordered
	 */
	protected EList<EventSnippet> events;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SnippetModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetaModelPackage.Literals.SNIPPET_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SnippetType> getSnippets() {
		if (snippets == null) {
			snippets = new EObjectContainmentEList<SnippetType>(SnippetType.class, this, MetaModelPackage.SNIPPET_MODEL__SNIPPETS);
		}
		return snippets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyType> getPropertyTypes() {
		if (propertyTypes == null) {
			propertyTypes = new EObjectContainmentEList<PropertyType>(PropertyType.class, this, MetaModelPackage.SNIPPET_MODEL__PROPERTY_TYPES);
		}
		return propertyTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<WidgetSnippet> getWidgets() {
		if (widgets == null) {
			widgets = new EObjectContainmentEList<WidgetSnippet>(WidgetSnippet.class, this, MetaModelPackage.SNIPPET_MODEL__WIDGETS);
		}
		return widgets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EventSnippet> getEvents() {
		if (events == null) {
			events = new EObjectContainmentEList<EventSnippet>(EventSnippet.class, this, MetaModelPackage.SNIPPET_MODEL__EVENTS);
		}
		return events;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetaModelPackage.SNIPPET_MODEL__SNIPPETS:
				return ((InternalEList<?>)getSnippets()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.SNIPPET_MODEL__PROPERTY_TYPES:
				return ((InternalEList<?>)getPropertyTypes()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.SNIPPET_MODEL__WIDGETS:
				return ((InternalEList<?>)getWidgets()).basicRemove(otherEnd, msgs);
			case MetaModelPackage.SNIPPET_MODEL__EVENTS:
				return ((InternalEList<?>)getEvents()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetaModelPackage.SNIPPET_MODEL__SNIPPETS:
				return getSnippets();
			case MetaModelPackage.SNIPPET_MODEL__PROPERTY_TYPES:
				return getPropertyTypes();
			case MetaModelPackage.SNIPPET_MODEL__WIDGETS:
				return getWidgets();
			case MetaModelPackage.SNIPPET_MODEL__EVENTS:
				return getEvents();
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
			case MetaModelPackage.SNIPPET_MODEL__SNIPPETS:
				getSnippets().clear();
				getSnippets().addAll((Collection<? extends SnippetType>)newValue);
				return;
			case MetaModelPackage.SNIPPET_MODEL__PROPERTY_TYPES:
				getPropertyTypes().clear();
				getPropertyTypes().addAll((Collection<? extends PropertyType>)newValue);
				return;
			case MetaModelPackage.SNIPPET_MODEL__WIDGETS:
				getWidgets().clear();
				getWidgets().addAll((Collection<? extends WidgetSnippet>)newValue);
				return;
			case MetaModelPackage.SNIPPET_MODEL__EVENTS:
				getEvents().clear();
				getEvents().addAll((Collection<? extends EventSnippet>)newValue);
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
			case MetaModelPackage.SNIPPET_MODEL__SNIPPETS:
				getSnippets().clear();
				return;
			case MetaModelPackage.SNIPPET_MODEL__PROPERTY_TYPES:
				getPropertyTypes().clear();
				return;
			case MetaModelPackage.SNIPPET_MODEL__WIDGETS:
				getWidgets().clear();
				return;
			case MetaModelPackage.SNIPPET_MODEL__EVENTS:
				getEvents().clear();
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
			case MetaModelPackage.SNIPPET_MODEL__SNIPPETS:
				return snippets != null && !snippets.isEmpty();
			case MetaModelPackage.SNIPPET_MODEL__PROPERTY_TYPES:
				return propertyTypes != null && !propertyTypes.isEmpty();
			case MetaModelPackage.SNIPPET_MODEL__WIDGETS:
				return widgets != null && !widgets.isEmpty();
			case MetaModelPackage.SNIPPET_MODEL__EVENTS:
				return events != null && !events.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SnippetModelImpl
