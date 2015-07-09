/**
 */
package com.odcgroup.edge.t24ui.impl;

import com.odcgroup.edge.t24ui.BespokeCompositeScreen;
import com.odcgroup.edge.t24ui.T24UIPackage;

import com.odcgroup.edge.t24ui.cos.bespoke.Section;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bespoke Composite Screen</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.BespokeCompositeScreenImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.impl.BespokeCompositeScreenImpl#getSections <em>Sections</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BespokeCompositeScreenImpl extends EObjectImpl implements BespokeCompositeScreen {
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
	 * The cached value of the '{@link #getSections() <em>Sections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSections()
	 * @generated
	 * @ordered
	 */
	protected EList<Section> sections;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BespokeCompositeScreenImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return T24UIPackage.Literals.BESPOKE_COMPOSITE_SCREEN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, T24UIPackage.BESPOKE_COMPOSITE_SCREEN__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Section> getSections() {
		if (sections == null) {
			sections = new EObjectContainmentEList<Section>(Section.class, this, T24UIPackage.BESPOKE_COMPOSITE_SCREEN__SECTIONS);
		}
		return sections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__SECTIONS:
				return ((InternalEList<?>)getSections()).basicRemove(otherEnd, msgs);
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
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__NAME:
				return getName();
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__SECTIONS:
				return getSections();
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
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__NAME:
				setName((String)newValue);
				return;
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__SECTIONS:
				getSections().clear();
				getSections().addAll((Collection<? extends Section>)newValue);
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
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__NAME:
				setName(NAME_EDEFAULT);
				return;
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__SECTIONS:
				getSections().clear();
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
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case T24UIPackage.BESPOKE_COMPOSITE_SCREEN__SECTIONS:
				return sections != null && !sections.isEmpty();
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

} //BespokeCompositeScreenImpl
