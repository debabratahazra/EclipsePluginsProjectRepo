/**
 * Odyssey Financial Technologies
 *
 * $Id$
 */
package com.odcgroup.process.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.odcgroup.process.model.ComplexGateway;
import com.odcgroup.process.model.ProcessPackage;
import com.odcgroup.process.model.Rule;
import com.odcgroup.process.model.Script;
import com.odcgroup.process.model.Service;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Complex Gateway</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.process.model.impl.ComplexGatewayImpl#getService <em>Service</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ComplexGatewayImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.odcgroup.process.model.impl.ComplexGatewayImpl#getRule <em>Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComplexGatewayImpl extends GatewayImpl implements ComplexGateway {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Odyssey Financial Technologies";

	/**
	 * The cached value of the '{@link #getService() <em>Service</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected Service service;

	/**
	 * The cached value of the '{@link #getScript() <em>Script</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScript()
	 * @generated
	 * @ordered
	 */
	protected Script script;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComplexGatewayImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ProcessPackage.Literals.COMPLEX_GATEWAY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Service getService() {
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetService(Service newService, NotificationChain msgs) {
		Service oldService = service;
		service = newService;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.COMPLEX_GATEWAY__SERVICE, oldService, newService);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setService(Service newService) {
		if (newService != service) {
			NotificationChain msgs = null;
			if (service != null)
				msgs = ((InternalEObject)service).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.COMPLEX_GATEWAY__SERVICE, null, msgs);
			if (newService != null)
				msgs = ((InternalEObject)newService).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.COMPLEX_GATEWAY__SERVICE, null, msgs);
			msgs = basicSetService(newService, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.COMPLEX_GATEWAY__SERVICE, newService, newService));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScript(Script newScript, NotificationChain msgs) {
		Script oldScript = script;
		script = newScript;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.COMPLEX_GATEWAY__SCRIPT, oldScript, newScript);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScript(Script newScript) {
		if (newScript != script) {
			NotificationChain msgs = null;
			if (script != null)
				msgs = ((InternalEObject)script).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.COMPLEX_GATEWAY__SCRIPT, null, msgs);
			if (newScript != null)
				msgs = ((InternalEObject)newScript).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.COMPLEX_GATEWAY__SCRIPT, null, msgs);
			msgs = basicSetScript(newScript, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.COMPLEX_GATEWAY__SCRIPT, newScript, newScript));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(Rule newRule, NotificationChain msgs) {
		Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProcessPackage.COMPLEX_GATEWAY__RULE, oldRule, newRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null)
				msgs = ((InternalEObject)rule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.COMPLEX_GATEWAY__RULE, null, msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProcessPackage.COMPLEX_GATEWAY__RULE, null, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProcessPackage.COMPLEX_GATEWAY__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProcessPackage.COMPLEX_GATEWAY__SERVICE:
				return basicSetService(null, msgs);
			case ProcessPackage.COMPLEX_GATEWAY__SCRIPT:
				return basicSetScript(null, msgs);
			case ProcessPackage.COMPLEX_GATEWAY__RULE:
				return basicSetRule(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProcessPackage.COMPLEX_GATEWAY__SERVICE:
				return getService();
			case ProcessPackage.COMPLEX_GATEWAY__SCRIPT:
				return getScript();
			case ProcessPackage.COMPLEX_GATEWAY__RULE:
				return getRule();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProcessPackage.COMPLEX_GATEWAY__SERVICE:
				setService((Service)newValue);
				return;
			case ProcessPackage.COMPLEX_GATEWAY__SCRIPT:
				setScript((Script)newValue);
				return;
			case ProcessPackage.COMPLEX_GATEWAY__RULE:
				setRule((Rule)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProcessPackage.COMPLEX_GATEWAY__SERVICE:
				setService((Service)null);
				return;
			case ProcessPackage.COMPLEX_GATEWAY__SCRIPT:
				setScript((Script)null);
				return;
			case ProcessPackage.COMPLEX_GATEWAY__RULE:
				setRule((Rule)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProcessPackage.COMPLEX_GATEWAY__SERVICE:
				return service != null;
			case ProcessPackage.COMPLEX_GATEWAY__SCRIPT:
				return script != null;
			case ProcessPackage.COMPLEX_GATEWAY__RULE:
				return rule != null;
		}
		return super.eIsSet(featureID);
	}

} //ComplexGatewayImpl
