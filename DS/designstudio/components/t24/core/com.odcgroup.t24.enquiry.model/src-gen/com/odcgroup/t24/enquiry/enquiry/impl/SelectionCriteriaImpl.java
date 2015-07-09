/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.CriteriaOperator;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selection Criteria</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionCriteriaImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SelectionCriteriaImpl extends MinimalEObjectImpl.Container implements SelectionCriteria
{
  /**
   * The default value of the '{@link #getField() <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getField()
   * @generated
   * @ordered
   */
  protected static final String FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getField() <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getField()
   * @generated
   * @ordered
   */
  protected String field = FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getOperand() <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperand()
   * @generated
   * @ordered
   */
  protected static final CriteriaOperator OPERAND_EDEFAULT = CriteriaOperator.NONE;

  /**
   * The cached value of the '{@link #getOperand() <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperand()
   * @generated
   * @ordered
   */
  protected CriteriaOperator operand = OPERAND_EDEFAULT;

  /**
   * The cached value of the '{@link #getValues() <em>Values</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValues()
   * @generated
   * @ordered
   */
  protected EList<String> values;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SelectionCriteriaImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return EnquiryPackage.Literals.SELECTION_CRITERIA;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getField()
  {
    return field;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setField(String newField)
  {
    String oldField = field;
    field = newField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION_CRITERIA__FIELD, oldField, field));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CriteriaOperator getOperand()
  {
    return operand;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperand(CriteriaOperator newOperand)
  {
    CriteriaOperator oldOperand = operand;
    operand = newOperand == null ? OPERAND_EDEFAULT : newOperand;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION_CRITERIA__OPERAND, oldOperand, operand));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getValues()
  {
    if (values == null)
    {
      values = new EDataTypeEList<String>(String.class, this, EnquiryPackage.SELECTION_CRITERIA__VALUES);
    }
    return values;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case EnquiryPackage.SELECTION_CRITERIA__FIELD:
        return getField();
      case EnquiryPackage.SELECTION_CRITERIA__OPERAND:
        return getOperand();
      case EnquiryPackage.SELECTION_CRITERIA__VALUES:
        return getValues();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EnquiryPackage.SELECTION_CRITERIA__FIELD:
        setField((String)newValue);
        return;
      case EnquiryPackage.SELECTION_CRITERIA__OPERAND:
        setOperand((CriteriaOperator)newValue);
        return;
      case EnquiryPackage.SELECTION_CRITERIA__VALUES:
        getValues().clear();
        getValues().addAll((Collection<? extends String>)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case EnquiryPackage.SELECTION_CRITERIA__FIELD:
        setField(FIELD_EDEFAULT);
        return;
      case EnquiryPackage.SELECTION_CRITERIA__OPERAND:
        setOperand(OPERAND_EDEFAULT);
        return;
      case EnquiryPackage.SELECTION_CRITERIA__VALUES:
        getValues().clear();
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case EnquiryPackage.SELECTION_CRITERIA__FIELD:
        return FIELD_EDEFAULT == null ? field != null : !FIELD_EDEFAULT.equals(field);
      case EnquiryPackage.SELECTION_CRITERIA__OPERAND:
        return operand != OPERAND_EDEFAULT;
      case EnquiryPackage.SELECTION_CRITERIA__VALUES:
        return values != null && !values.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (field: ");
    result.append(field);
    result.append(", operand: ");
    result.append(operand);
    result.append(", values: ");
    result.append(values);
    result.append(')');
    return result.toString();
  }

} //SelectionCriteriaImpl
