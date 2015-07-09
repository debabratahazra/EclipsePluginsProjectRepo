/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fixed Selection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl#getOperand <em>Operand</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FixedSelectionImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FixedSelectionImpl extends MinimalEObjectImpl.Container implements FixedSelection
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
  protected static final SelectionOperator OPERAND_EDEFAULT = SelectionOperator.NONE;

  /**
   * The cached value of the '{@link #getOperand() <em>Operand</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperand()
   * @generated
   * @ordered
   */
  protected SelectionOperator operand = OPERAND_EDEFAULT;

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
  protected FixedSelectionImpl()
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
    return EnquiryPackage.Literals.FIXED_SELECTION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIXED_SELECTION__FIELD, oldField, field));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SelectionOperator getOperand()
  {
    return operand;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperand(SelectionOperator newOperand)
  {
    SelectionOperator oldOperand = operand;
    operand = newOperand == null ? OPERAND_EDEFAULT : newOperand;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIXED_SELECTION__OPERAND, oldOperand, operand));
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
      values = new EDataTypeEList<String>(String.class, this, EnquiryPackage.FIXED_SELECTION__VALUES);
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
      case EnquiryPackage.FIXED_SELECTION__FIELD:
        return getField();
      case EnquiryPackage.FIXED_SELECTION__OPERAND:
        return getOperand();
      case EnquiryPackage.FIXED_SELECTION__VALUES:
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
      case EnquiryPackage.FIXED_SELECTION__FIELD:
        setField((String)newValue);
        return;
      case EnquiryPackage.FIXED_SELECTION__OPERAND:
        setOperand((SelectionOperator)newValue);
        return;
      case EnquiryPackage.FIXED_SELECTION__VALUES:
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
      case EnquiryPackage.FIXED_SELECTION__FIELD:
        setField(FIELD_EDEFAULT);
        return;
      case EnquiryPackage.FIXED_SELECTION__OPERAND:
        setOperand(OPERAND_EDEFAULT);
        return;
      case EnquiryPackage.FIXED_SELECTION__VALUES:
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
      case EnquiryPackage.FIXED_SELECTION__FIELD:
        return FIELD_EDEFAULT == null ? field != null : !FIELD_EDEFAULT.equals(field);
      case EnquiryPackage.FIXED_SELECTION__OPERAND:
        return operand != OPERAND_EDEFAULT;
      case EnquiryPackage.FIXED_SELECTION__VALUES:
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

} //FixedSelectionImpl
