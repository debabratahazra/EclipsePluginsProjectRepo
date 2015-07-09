/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.AndOr;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;

import com.odcgroup.translation.translationDsl.Translations;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Selection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl#getMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl#getPopupDropDown <em>Popup Drop Down</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl#getOperands <em>Operands</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SelectionImpl extends MinimalEObjectImpl.Container implements Selection
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
   * The default value of the '{@link #getMandatory() <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMandatory()
   * @generated
   * @ordered
   */
  protected static final Boolean MANDATORY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMandatory() <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMandatory()
   * @generated
   * @ordered
   */
  protected Boolean mandatory = MANDATORY_EDEFAULT;

  /**
   * The default value of the '{@link #getPopupDropDown() <em>Popup Drop Down</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPopupDropDown()
   * @generated
   * @ordered
   */
  protected static final Boolean POPUP_DROP_DOWN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPopupDropDown() <em>Popup Drop Down</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPopupDropDown()
   * @generated
   * @ordered
   */
  protected Boolean popupDropDown = POPUP_DROP_DOWN_EDEFAULT;

  /**
   * The cached value of the '{@link #getLabel() <em>Label</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected Translations label;

  /**
   * The cached value of the '{@link #getOperands() <em>Operands</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperands()
   * @generated
   * @ordered
   */
  protected EList<SelectionOperator> operands;

  /**
   * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperator()
   * @generated
   * @ordered
   */
  protected static final AndOr OPERATOR_EDEFAULT = AndOr.NONE;

  /**
   * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperator()
   * @generated
   * @ordered
   */
  protected AndOr operator = OPERATOR_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SelectionImpl()
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
    return EnquiryPackage.Literals.SELECTION;
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION__FIELD, oldField, field));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getMandatory()
  {
    return mandatory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMandatory(Boolean newMandatory)
  {
    Boolean oldMandatory = mandatory;
    mandatory = newMandatory;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION__MANDATORY, oldMandatory, mandatory));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getPopupDropDown()
  {
    return popupDropDown;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPopupDropDown(Boolean newPopupDropDown)
  {
    Boolean oldPopupDropDown = popupDropDown;
    popupDropDown = newPopupDropDown;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION__POPUP_DROP_DOWN, oldPopupDropDown, popupDropDown));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getLabel()
  {
    return label;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLabel(Translations newLabel, NotificationChain msgs)
  {
    Translations oldLabel = label;
    label = newLabel;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION__LABEL, oldLabel, newLabel);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLabel(Translations newLabel)
  {
    if (newLabel != label)
    {
      NotificationChain msgs = null;
      if (label != null)
        msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.SELECTION__LABEL, null, msgs);
      if (newLabel != null)
        msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.SELECTION__LABEL, null, msgs);
      msgs = basicSetLabel(newLabel, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION__LABEL, newLabel, newLabel));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<SelectionOperator> getOperands()
  {
    if (operands == null)
    {
      operands = new EDataTypeEList<SelectionOperator>(SelectionOperator.class, this, EnquiryPackage.SELECTION__OPERANDS);
    }
    return operands;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AndOr getOperator()
  {
    return operator;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOperator(AndOr newOperator)
  {
    AndOr oldOperator = operator;
    operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.SELECTION__OPERATOR, oldOperator, operator));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case EnquiryPackage.SELECTION__LABEL:
        return basicSetLabel(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case EnquiryPackage.SELECTION__FIELD:
        return getField();
      case EnquiryPackage.SELECTION__MANDATORY:
        return getMandatory();
      case EnquiryPackage.SELECTION__POPUP_DROP_DOWN:
        return getPopupDropDown();
      case EnquiryPackage.SELECTION__LABEL:
        return getLabel();
      case EnquiryPackage.SELECTION__OPERANDS:
        return getOperands();
      case EnquiryPackage.SELECTION__OPERATOR:
        return getOperator();
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
      case EnquiryPackage.SELECTION__FIELD:
        setField((String)newValue);
        return;
      case EnquiryPackage.SELECTION__MANDATORY:
        setMandatory((Boolean)newValue);
        return;
      case EnquiryPackage.SELECTION__POPUP_DROP_DOWN:
        setPopupDropDown((Boolean)newValue);
        return;
      case EnquiryPackage.SELECTION__LABEL:
        setLabel((Translations)newValue);
        return;
      case EnquiryPackage.SELECTION__OPERANDS:
        getOperands().clear();
        getOperands().addAll((Collection<? extends SelectionOperator>)newValue);
        return;
      case EnquiryPackage.SELECTION__OPERATOR:
        setOperator((AndOr)newValue);
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
      case EnquiryPackage.SELECTION__FIELD:
        setField(FIELD_EDEFAULT);
        return;
      case EnquiryPackage.SELECTION__MANDATORY:
        setMandatory(MANDATORY_EDEFAULT);
        return;
      case EnquiryPackage.SELECTION__POPUP_DROP_DOWN:
        setPopupDropDown(POPUP_DROP_DOWN_EDEFAULT);
        return;
      case EnquiryPackage.SELECTION__LABEL:
        setLabel((Translations)null);
        return;
      case EnquiryPackage.SELECTION__OPERANDS:
        getOperands().clear();
        return;
      case EnquiryPackage.SELECTION__OPERATOR:
        setOperator(OPERATOR_EDEFAULT);
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
      case EnquiryPackage.SELECTION__FIELD:
        return FIELD_EDEFAULT == null ? field != null : !FIELD_EDEFAULT.equals(field);
      case EnquiryPackage.SELECTION__MANDATORY:
        return MANDATORY_EDEFAULT == null ? mandatory != null : !MANDATORY_EDEFAULT.equals(mandatory);
      case EnquiryPackage.SELECTION__POPUP_DROP_DOWN:
        return POPUP_DROP_DOWN_EDEFAULT == null ? popupDropDown != null : !POPUP_DROP_DOWN_EDEFAULT.equals(popupDropDown);
      case EnquiryPackage.SELECTION__LABEL:
        return label != null;
      case EnquiryPackage.SELECTION__OPERANDS:
        return operands != null && !operands.isEmpty();
      case EnquiryPackage.SELECTION__OPERATOR:
        return operator != OPERATOR_EDEFAULT;
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
    result.append(", mandatory: ");
    result.append(mandatory);
    result.append(", popupDropDown: ");
    result.append(popupDropDown);
    result.append(", operands: ");
    result.append(operands);
    result.append(", operator: ");
    result.append(operator);
    result.append(')');
    return result.toString();
  }

} //SelectionImpl
