/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelPackage;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetFactory;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.model.impl.ModelImpl#getWidget <em>Widget</em>}</li>
 *   <li>{@link com.odcgroup.page.model.impl.ModelImpl#getMetamodelVersion <em>Metamodel Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model {
	
	/** True if the Widget Tree has been updated to take into account changes in the metamodel. */
	private transient boolean updated;
	
	/**
	 * The cached value of the '{@link #getWidget() <em>Widget</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidget()
	 * @generated
	 * @ordered
	 */
	protected Widget widget;

	/**
	 * The default value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String METAMODEL_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMetamodelVersion() <em>Metamodel Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelVersion()
	 * @generated
	 * @ordered
	 */
	protected String metamodelVersion = METAMODEL_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.MODEL;
	}


    /**
     * <!-- begin-user-doc -->
     * @return Widget
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public synchronized Widget getWidget() {
        if (updated == false) {
            // Update the Widget in case some properties have been added / removed
            new WidgetFactory().update(widget);
            updated = true;
        }       
        return widget;
    }

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidget
	 * @param msgs
	 * @return NotificationChain
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWidget(Widget newWidget, NotificationChain msgs) {
		Widget oldWidget = widget;
		widget = newWidget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.MODEL__WIDGET, oldWidget, newWidget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newWidget
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidget(Widget newWidget) {
		if (newWidget != widget) {
			NotificationChain msgs = null;
			if (widget != null)
				msgs = ((InternalEObject)widget).eInverseRemove(this, ModelPackage.WIDGET__MODEL, Widget.class, msgs);
			if (newWidget != null)
				msgs = ((InternalEObject)newWidget).eInverseAdd(this, ModelPackage.WIDGET__MODEL, Widget.class, msgs);
			msgs = basicSetWidget(newWidget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MODEL__WIDGET, newWidget, newWidget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMetamodelVersion() {
		return metamodelVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetamodelVersion(String newMetamodelVersion) {
		String oldMetamodelVersion = metamodelVersion;
		metamodelVersion = newMetamodelVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MODEL__METAMODEL_VERSION, oldMetamodelVersion, metamodelVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.MODEL__WIDGET:
				if (widget != null)
					msgs = ((InternalEObject)widget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.MODEL__WIDGET, null, msgs);
				return basicSetWidget((Widget)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.MODEL__WIDGET:
				return basicSetWidget(null, msgs);
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
			case ModelPackage.MODEL__WIDGET:
				return getWidget();
			case ModelPackage.MODEL__METAMODEL_VERSION:
				return getMetamodelVersion();
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
			case ModelPackage.MODEL__WIDGET:
				setWidget((Widget)newValue);
				return;
			case ModelPackage.MODEL__METAMODEL_VERSION:
				setMetamodelVersion((String)newValue);
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
			case ModelPackage.MODEL__WIDGET:
				setWidget((Widget)null);
				return;
			case ModelPackage.MODEL__METAMODEL_VERSION:
				setMetamodelVersion(METAMODEL_VERSION_EDEFAULT);
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
			case ModelPackage.MODEL__WIDGET:
				return widget != null;
			case ModelPackage.MODEL__METAMODEL_VERSION:
				return METAMODEL_VERSION_EDEFAULT == null ? metamodelVersion != null : !METAMODEL_VERSION_EDEFAULT.equals(metamodelVersion);
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
		result.append(" (metamodelVersion: ");
		result.append(metamodelVersion);
		result.append(')');
		return result.toString();
	}
	
	/**
	 * Gets the xmi:id of the model. This method returns null if the
	 * resource is NOT an XMLResource.
	 * 
	 * @return String The xmi:id of the Model
	 */
	public String getXmiId() {
		Resource r = eResource();
		if (r == null || ! (r instanceof XMLResource)) {
			return null;
		}
		XMLResource xmlr = (XMLResource) r;
		return xmlr.getID(this);
	}

} //ModelImpl