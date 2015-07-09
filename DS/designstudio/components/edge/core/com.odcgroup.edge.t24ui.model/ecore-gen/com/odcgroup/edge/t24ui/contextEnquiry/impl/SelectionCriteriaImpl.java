/**
 */
package com.odcgroup.edge.t24ui.contextEnquiry.impl;

import com.odcgroup.edge.t24ui.contextEnquiry.ContextEnquiryPackage;
import com.odcgroup.edge.t24ui.contextEnquiry.Operand;
import com.odcgroup.edge.t24ui.contextEnquiry.SelectionCriteria;
import com.odcgroup.edge.t24ui.contextEnquiry.SortOrder;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selection Criteria</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl#getAppField <em>App Field</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.edge.t24ui.contextEnquiry.impl.SelectionCriteriaImpl#getSortOrder <em>Sort Order</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SelectionCriteriaImpl extends EObjectImpl implements SelectionCriteria {
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
	 * The default value of the '{@link #getAppField() <em>App Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppField()
	 * @generated
	 * @ordered
	 */
	protected static final String APP_FIELD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAppField() <em>App Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppField()
	 * @generated
	 * @ordered
	 */
	protected String appField = APP_FIELD_EDEFAULT;

	/**
	 * The default value of the '{@link #getOperand() <em>Operand</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperand()
	 * @generated
	 * @ordered
	 */
	protected static final Operand OPERAND_EDEFAULT = Operand.NONE;

	/**
	 * The cached value of the '{@link #getOperand() <em>Operand</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperand()
	 * @generated
	 * @ordered
	 */
	protected Operand operand = OPERAND_EDEFAULT;

	/**
	 * The default value of the '{@link #getSortOrder() <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSortOrder()
	 * @generated
	 * @ordered
	 */
	protected static final SortOrder SORT_ORDER_EDEFAULT = SortOrder.ASCENDING;

	/**
	 * The cached value of the '{@link #getSortOrder() <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSortOrder()
	 * @generated
	 * @ordered
	 */
	protected SortOrder sortOrder = SORT_ORDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SelectionCriteriaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextEnquiryPackage.Literals.SELECTION_CRITERIA;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.SELECTION_CRITERIA__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAppField() {
		return appField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAppField(String newAppField) {
		String oldAppField = appField;
		appField = newAppField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.SELECTION_CRITERIA__APP_FIELD, oldAppField, appField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operand getOperand() {
		return operand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperand(Operand newOperand) {
		Operand oldOperand = operand;
		operand = newOperand == null ? OPERAND_EDEFAULT : newOperand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.SELECTION_CRITERIA__OPERAND, oldOperand, operand));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSortOrder(SortOrder newSortOrder) {
		SortOrder oldSortOrder = sortOrder;
		sortOrder = newSortOrder == null ? SORT_ORDER_EDEFAULT : newSortOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextEnquiryPackage.SELECTION_CRITERIA__SORT_ORDER, oldSortOrder, sortOrder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContextEnquiryPackage.SELECTION_CRITERIA__NAME:
				return getName();
			case ContextEnquiryPackage.SELECTION_CRITERIA__APP_FIELD:
				return getAppField();
			case ContextEnquiryPackage.SELECTION_CRITERIA__OPERAND:
				return getOperand();
			case ContextEnquiryPackage.SELECTION_CRITERIA__SORT_ORDER:
				return getSortOrder();
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
			case ContextEnquiryPackage.SELECTION_CRITERIA__NAME:
				setName((String)newValue);
				return;
			case ContextEnquiryPackage.SELECTION_CRITERIA__APP_FIELD:
				setAppField((String)newValue);
				return;
			case ContextEnquiryPackage.SELECTION_CRITERIA__OPERAND:
				setOperand((Operand)newValue);
				return;
			case ContextEnquiryPackage.SELECTION_CRITERIA__SORT_ORDER:
				setSortOrder((SortOrder)newValue);
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
			case ContextEnquiryPackage.SELECTION_CRITERIA__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ContextEnquiryPackage.SELECTION_CRITERIA__APP_FIELD:
				setAppField(APP_FIELD_EDEFAULT);
				return;
			case ContextEnquiryPackage.SELECTION_CRITERIA__OPERAND:
				setOperand(OPERAND_EDEFAULT);
				return;
			case ContextEnquiryPackage.SELECTION_CRITERIA__SORT_ORDER:
				setSortOrder(SORT_ORDER_EDEFAULT);
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
			case ContextEnquiryPackage.SELECTION_CRITERIA__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ContextEnquiryPackage.SELECTION_CRITERIA__APP_FIELD:
				return APP_FIELD_EDEFAULT == null ? appField != null : !APP_FIELD_EDEFAULT.equals(appField);
			case ContextEnquiryPackage.SELECTION_CRITERIA__OPERAND:
				return operand != OPERAND_EDEFAULT;
			case ContextEnquiryPackage.SELECTION_CRITERIA__SORT_ORDER:
				return sortOrder != SORT_ORDER_EDEFAULT;
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
		result.append(", appField: ");
		result.append(appField);
		result.append(", operand: ");
		result.append(operand);
		result.append(", sortOrder: ");
		result.append(sortOrder);
		result.append(')');
		return result.toString();
	}

} //SelectionCriteriaImpl
