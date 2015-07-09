/**
 */
package com.odcgroup.edge.t24ui.cos.pattern.impl;

import com.odcgroup.edge.t24ui.common.Translation;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.odcgroup.edge.t24ui.cos.pattern.AbstractCOS;
import com.odcgroup.edge.t24ui.cos.pattern.COSPanel;
import com.odcgroup.edge.t24ui.cos.pattern.PatternPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract COS</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl#getPanels <em>Panels</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.cos.pattern.impl.AbstractCOSImpl#getTitle <em>Title</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractCOSImpl extends COSPatternContainerImpl implements AbstractCOS {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPanels() <em>Panels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPanels()
	 * @generated
	 * @ordered
	 */
	protected EList<COSPanel> panels;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected EList<Translation> title;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractCOSImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PatternPackage.Literals.ABSTRACT_COS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PatternPackage.ABSTRACT_COS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<COSPanel> getPanels() {
		if (panels == null) {
			panels = new EObjectContainmentEList<COSPanel>(COSPanel.class, this, PatternPackage.ABSTRACT_COS__PANELS);
		}
		return panels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Translation> getTitle() {
		if (title == null) {
			title = new EObjectContainmentEList<Translation>(Translation.class, this, PatternPackage.ABSTRACT_COS__TITLE);
		}
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PatternPackage.ABSTRACT_COS__PANELS:
				return ((InternalEList<?>)getPanels()).basicRemove(otherEnd, msgs);
			case PatternPackage.ABSTRACT_COS__TITLE:
				return ((InternalEList<?>)getTitle()).basicRemove(otherEnd, msgs);
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
			case PatternPackage.ABSTRACT_COS__NAME:
				return getName();
			case PatternPackage.ABSTRACT_COS__PANELS:
				return getPanels();
			case PatternPackage.ABSTRACT_COS__TITLE:
				return getTitle();
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
			case PatternPackage.ABSTRACT_COS__NAME:
				setName((String)newValue);
				return;
			case PatternPackage.ABSTRACT_COS__PANELS:
				getPanels().clear();
				getPanels().addAll((Collection<? extends COSPanel>)newValue);
				return;
			case PatternPackage.ABSTRACT_COS__TITLE:
				getTitle().clear();
				getTitle().addAll((Collection<? extends Translation>)newValue);
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
			case PatternPackage.ABSTRACT_COS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PatternPackage.ABSTRACT_COS__PANELS:
				getPanels().clear();
				return;
			case PatternPackage.ABSTRACT_COS__TITLE:
				getTitle().clear();
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
			case PatternPackage.ABSTRACT_COS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PatternPackage.ABSTRACT_COS__PANELS:
				return panels != null && !panels.isEmpty();
			case PatternPackage.ABSTRACT_COS__TITLE:
				return title != null && !title.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //AbstractCOSImpl
