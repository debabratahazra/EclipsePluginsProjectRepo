/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.cos.pattern.ChildResourceSpec;
import com.odcgroup.edge.t24ui.cos.pattern.InitialPanelContentSpec;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;
import com.odcgroup.edge.t24ui.cos.pattern.SingleComponentPanel;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Single Component Panel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.SingleComponentPanelImpl#getInitialContent <em>Initial Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SingleComponentPanelImpl extends COSPanelImpl implements SingleComponentPanel {
	/**
	 * The cached value of the '{@link #getInitialContent() <em>Initial Content</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialContent()
	 * @generated
	 * @ordered
	 */
	protected InitialPanelContentSpec initialContent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SingleComponentPanelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.SINGLE_COMPONENT_PANEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitialPanelContentSpec getInitialContent() {
		return initialContent;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetInitialContent(InitialPanelContentSpec newInitialContent, NotificationChain msgs) {
		InitialPanelContentSpec oldInitialContent = initialContent;
		initialContent = newInitialContent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT, oldInitialContent, newInitialContent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setInitialContent(InitialPanelContentSpec newInitialContent) {
		if (newInitialContent != initialContent) {
			NotificationChain msgs = null;
			if (initialContent != null)
				msgs = ((InternalEObject)initialContent).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT, null, msgs);
			if (newInitialContent != null)
				msgs = ((InternalEObject)newInitialContent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT, null, msgs);
			msgs = basicSetInitialContent(newInitialContent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT, newInitialContent, newInitialContent));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT:
				return basicSetInitialContent(null, msgs);
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
			case PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT:
				return getInitialContent();
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
			case PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT:
				setInitialContent((InitialPanelContentSpec)newValue);
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
			case PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT:
				setInitialContent((InitialPanelContentSpec)null);
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
			case PatternPackage.SINGLE_COMPONENT_PANEL__INITIAL_CONTENT:
				return initialContent != null;
		}
		return super.eIsSet(featureID);
	}

} //SingleComponentPanelImpl
