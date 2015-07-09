/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.ExtractConversion;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extract Conversion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl#getFrom <em>From</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl#getTo <em>To</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl#getDelimiter <em>Delimiter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExtractConversionImpl extends ConversionImpl implements ExtractConversion
{
  /**
   * The default value of the '{@link #getFrom() <em>From</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFrom()
   * @generated
   * @ordered
   */
  protected static final int FROM_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getFrom() <em>From</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFrom()
   * @generated
   * @ordered
   */
  protected int from = FROM_EDEFAULT;

  /**
   * The default value of the '{@link #getTo() <em>To</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTo()
   * @generated
   * @ordered
   */
  protected static final int TO_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getTo() <em>To</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTo()
   * @generated
   * @ordered
   */
  protected int to = TO_EDEFAULT;

  /**
   * The default value of the '{@link #getDelimiter() <em>Delimiter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDelimiter()
   * @generated
   * @ordered
   */
  protected static final String DELIMITER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDelimiter() <em>Delimiter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDelimiter()
   * @generated
   * @ordered
   */
  protected String delimiter = DELIMITER_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExtractConversionImpl()
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
    return EnquiryPackage.Literals.EXTRACT_CONVERSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getFrom()
  {
    return from;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFrom(int newFrom)
  {
    int oldFrom = from;
    from = newFrom;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.EXTRACT_CONVERSION__FROM, oldFrom, from));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getTo()
  {
    return to;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTo(int newTo)
  {
    int oldTo = to;
    to = newTo;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.EXTRACT_CONVERSION__TO, oldTo, to));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDelimiter()
  {
    return delimiter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDelimiter(String newDelimiter)
  {
    String oldDelimiter = delimiter;
    delimiter = newDelimiter;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.EXTRACT_CONVERSION__DELIMITER, oldDelimiter, delimiter));
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
      case EnquiryPackage.EXTRACT_CONVERSION__FROM:
        return getFrom();
      case EnquiryPackage.EXTRACT_CONVERSION__TO:
        return getTo();
      case EnquiryPackage.EXTRACT_CONVERSION__DELIMITER:
        return getDelimiter();
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
      case EnquiryPackage.EXTRACT_CONVERSION__FROM:
        setFrom((Integer)newValue);
        return;
      case EnquiryPackage.EXTRACT_CONVERSION__TO:
        setTo((Integer)newValue);
        return;
      case EnquiryPackage.EXTRACT_CONVERSION__DELIMITER:
        setDelimiter((String)newValue);
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
      case EnquiryPackage.EXTRACT_CONVERSION__FROM:
        setFrom(FROM_EDEFAULT);
        return;
      case EnquiryPackage.EXTRACT_CONVERSION__TO:
        setTo(TO_EDEFAULT);
        return;
      case EnquiryPackage.EXTRACT_CONVERSION__DELIMITER:
        setDelimiter(DELIMITER_EDEFAULT);
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
      case EnquiryPackage.EXTRACT_CONVERSION__FROM:
        return from != FROM_EDEFAULT;
      case EnquiryPackage.EXTRACT_CONVERSION__TO:
        return to != TO_EDEFAULT;
      case EnquiryPackage.EXTRACT_CONVERSION__DELIMITER:
        return DELIMITER_EDEFAULT == null ? delimiter != null : !DELIMITER_EDEFAULT.equals(delimiter);
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
    result.append(" (from: ");
    result.append(from);
    result.append(", to: ");
    result.append(to);
    result.append(", delimiter: ");
    result.append(delimiter);
    result.append(')');
    return result.toString();
  }

} //ExtractConversionImpl
