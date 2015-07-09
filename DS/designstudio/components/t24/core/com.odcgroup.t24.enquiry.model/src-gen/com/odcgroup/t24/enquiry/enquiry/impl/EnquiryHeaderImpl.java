/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import com.odcgroup.translation.translationDsl.Translations;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl#getColumn <em>Column</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.EnquiryHeaderImpl#getLine <em>Line</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnquiryHeaderImpl extends MinimalEObjectImpl.Container implements EnquiryHeader
{
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
   * The default value of the '{@link #getColumn() <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getColumn()
   * @generated
   * @ordered
   */
  protected static final int COLUMN_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getColumn() <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getColumn()
   * @generated
   * @ordered
   */
  protected int column = COLUMN_EDEFAULT;

  /**
   * The default value of the '{@link #getLine() <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLine()
   * @generated
   * @ordered
   */
  protected static final int LINE_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getLine() <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLine()
   * @generated
   * @ordered
   */
  protected int line = LINE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnquiryHeaderImpl()
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
    return EnquiryPackage.Literals.ENQUIRY_HEADER;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY_HEADER__LABEL, oldLabel, newLabel);
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
        msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY_HEADER__LABEL, null, msgs);
      if (newLabel != null)
        msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.ENQUIRY_HEADER__LABEL, null, msgs);
      msgs = basicSetLabel(newLabel, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY_HEADER__LABEL, newLabel, newLabel));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getColumn()
  {
    return column;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setColumn(int newColumn)
  {
    int oldColumn = column;
    column = newColumn;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY_HEADER__COLUMN, oldColumn, column));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getLine()
  {
    return line;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLine(int newLine)
  {
    int oldLine = line;
    line = newLine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.ENQUIRY_HEADER__LINE, oldLine, line));
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
      case EnquiryPackage.ENQUIRY_HEADER__LABEL:
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
      case EnquiryPackage.ENQUIRY_HEADER__LABEL:
        return getLabel();
      case EnquiryPackage.ENQUIRY_HEADER__COLUMN:
        return getColumn();
      case EnquiryPackage.ENQUIRY_HEADER__LINE:
        return getLine();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case EnquiryPackage.ENQUIRY_HEADER__LABEL:
        setLabel((Translations)newValue);
        return;
      case EnquiryPackage.ENQUIRY_HEADER__COLUMN:
        setColumn((Integer)newValue);
        return;
      case EnquiryPackage.ENQUIRY_HEADER__LINE:
        setLine((Integer)newValue);
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
      case EnquiryPackage.ENQUIRY_HEADER__LABEL:
        setLabel((Translations)null);
        return;
      case EnquiryPackage.ENQUIRY_HEADER__COLUMN:
        setColumn(COLUMN_EDEFAULT);
        return;
      case EnquiryPackage.ENQUIRY_HEADER__LINE:
        setLine(LINE_EDEFAULT);
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
      case EnquiryPackage.ENQUIRY_HEADER__LABEL:
        return label != null;
      case EnquiryPackage.ENQUIRY_HEADER__COLUMN:
        return column != COLUMN_EDEFAULT;
      case EnquiryPackage.ENQUIRY_HEADER__LINE:
        return line != LINE_EDEFAULT;
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
    result.append(" (column: ");
    result.append(column);
    result.append(", line: ");
    result.append(line);
    result.append(')');
    return result.toString();
  }

} //EnquiryHeaderImpl
