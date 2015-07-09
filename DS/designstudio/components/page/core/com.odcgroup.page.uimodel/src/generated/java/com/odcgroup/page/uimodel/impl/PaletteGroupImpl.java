/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.odcgroup.page.uimodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.odcgroup.page.uimodel.PaletteGroup;
import com.odcgroup.page.uimodel.PaletteGroupItem;
import com.odcgroup.page.uimodel.UIModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Palette Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupImpl#getItems <em>Items</em>}</li>
 *   <li>{@link com.odcgroup.page.uimodel.impl.PaletteGroupImpl#isExpanded <em>Expanded</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PaletteGroupImpl extends MinimalEObjectImpl.Container implements PaletteGroup {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<PaletteGroupItem> items;

	/**
	 * The default value of the '{@link #isExpanded() <em>Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExpanded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXPANDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExpanded() <em>Expanded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExpanded()
	 * @generated
	 * @ordered
	 */
	protected boolean expanded = EXPANDED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PaletteGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UIModelPackage.Literals.PALETTE_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return String
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newLabel
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return EList
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PaletteGroupItem> getItems() {
		if (items == null) {
			items = new EObjectResolvingEList<PaletteGroupItem>(PaletteGroupItem.class, this, UIModelPackage.PALETTE_GROUP__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @return boolean
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExpanded() {
		return expanded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * @param newExpanded
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpanded(boolean newExpanded) {
		boolean oldExpanded = expanded;
		expanded = newExpanded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UIModelPackage.PALETTE_GROUP__EXPANDED, oldExpanded, expanded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UIModelPackage.PALETTE_GROUP__LABEL:
				return getLabel();
			case UIModelPackage.PALETTE_GROUP__ITEMS:
				return getItems();
			case UIModelPackage.PALETTE_GROUP__EXPANDED:
				return isExpanded();
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
			case UIModelPackage.PALETTE_GROUP__LABEL:
				setLabel((String)newValue);
				return;
			case UIModelPackage.PALETTE_GROUP__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends PaletteGroupItem>)newValue);
				return;
			case UIModelPackage.PALETTE_GROUP__EXPANDED:
				setExpanded((Boolean)newValue);
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
			case UIModelPackage.PALETTE_GROUP__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case UIModelPackage.PALETTE_GROUP__ITEMS:
				getItems().clear();
				return;
			case UIModelPackage.PALETTE_GROUP__EXPANDED:
				setExpanded(EXPANDED_EDEFAULT);
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
			case UIModelPackage.PALETTE_GROUP__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case UIModelPackage.PALETTE_GROUP__ITEMS:
				return items != null && !items.isEmpty();
			case UIModelPackage.PALETTE_GROUP__EXPANDED:
				return expanded != EXPANDED_EDEFAULT;
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
		result.append(" (label: ");
		result.append(label);
		result.append(", expanded: ");
		result.append(expanded);
		result.append(')');
		return result.toString();
	}

} //PaletteGroupImpl