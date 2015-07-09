/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Position</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl#getPageThrow <em>Page Throw</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl#getColumn <em>Column</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl#getRelative <em>Relative</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl#getLine <em>Line</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.FieldPositionImpl#getMultiLine <em>Multi Line</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldPositionImpl extends MinimalEObjectImpl.Container implements FieldPosition
{
  /**
   * The default value of the '{@link #getPageThrow() <em>Page Throw</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPageThrow()
   * @generated
   * @ordered
   */
  protected static final Boolean PAGE_THROW_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPageThrow() <em>Page Throw</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPageThrow()
   * @generated
   * @ordered
   */
  protected Boolean pageThrow = PAGE_THROW_EDEFAULT;

  /**
   * The default value of the '{@link #getColumn() <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getColumn()
   * @generated
   * @ordered
   */
  protected static final Integer COLUMN_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getColumn() <em>Column</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getColumn()
   * @generated
   * @ordered
   */
  protected Integer column = COLUMN_EDEFAULT;

  /**
   * The default value of the '{@link #getRelative() <em>Relative</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelative()
   * @generated
   * @ordered
   */
  protected static final String RELATIVE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRelative() <em>Relative</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelative()
   * @generated
   * @ordered
   */
  protected String relative = RELATIVE_EDEFAULT;

  /**
   * The default value of the '{@link #getLine() <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLine()
   * @generated
   * @ordered
   */
  protected static final Integer LINE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLine() <em>Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLine()
   * @generated
   * @ordered
   */
  protected Integer line = LINE_EDEFAULT;

  /**
   * The default value of the '{@link #getMultiLine() <em>Multi Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMultiLine()
   * @generated
   * @ordered
   */
  protected static final Boolean MULTI_LINE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMultiLine() <em>Multi Line</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMultiLine()
   * @generated
   * @ordered
   */
  protected Boolean multiLine = MULTI_LINE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FieldPositionImpl()
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
    return EnquiryPackage.Literals.FIELD_POSITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getPageThrow()
  {
    return pageThrow;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPageThrow(Boolean newPageThrow)
  {
    Boolean oldPageThrow = pageThrow;
    pageThrow = newPageThrow;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD_POSITION__PAGE_THROW, oldPageThrow, pageThrow));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getColumn()
  {
    return column;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setColumn(Integer newColumn)
  {
    Integer oldColumn = column;
    column = newColumn;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD_POSITION__COLUMN, oldColumn, column));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getRelative()
  {
    return relative;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRelative(String newRelative)
  {
    String oldRelative = relative;
    relative = newRelative;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD_POSITION__RELATIVE, oldRelative, relative));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getLine()
  {
    return line;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLine(Integer newLine)
  {
    Integer oldLine = line;
    line = newLine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD_POSITION__LINE, oldLine, line));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Boolean getMultiLine()
  {
    return multiLine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMultiLine(Boolean newMultiLine)
  {
    Boolean oldMultiLine = multiLine;
    multiLine = newMultiLine;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.FIELD_POSITION__MULTI_LINE, oldMultiLine, multiLine));
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
      case EnquiryPackage.FIELD_POSITION__PAGE_THROW:
        return getPageThrow();
      case EnquiryPackage.FIELD_POSITION__COLUMN:
        return getColumn();
      case EnquiryPackage.FIELD_POSITION__RELATIVE:
        return getRelative();
      case EnquiryPackage.FIELD_POSITION__LINE:
        return getLine();
      case EnquiryPackage.FIELD_POSITION__MULTI_LINE:
        return getMultiLine();
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
      case EnquiryPackage.FIELD_POSITION__PAGE_THROW:
        setPageThrow((Boolean)newValue);
        return;
      case EnquiryPackage.FIELD_POSITION__COLUMN:
        setColumn((Integer)newValue);
        return;
      case EnquiryPackage.FIELD_POSITION__RELATIVE:
        setRelative((String)newValue);
        return;
      case EnquiryPackage.FIELD_POSITION__LINE:
        setLine((Integer)newValue);
        return;
      case EnquiryPackage.FIELD_POSITION__MULTI_LINE:
        setMultiLine((Boolean)newValue);
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
      case EnquiryPackage.FIELD_POSITION__PAGE_THROW:
        setPageThrow(PAGE_THROW_EDEFAULT);
        return;
      case EnquiryPackage.FIELD_POSITION__COLUMN:
        setColumn(COLUMN_EDEFAULT);
        return;
      case EnquiryPackage.FIELD_POSITION__RELATIVE:
        setRelative(RELATIVE_EDEFAULT);
        return;
      case EnquiryPackage.FIELD_POSITION__LINE:
        setLine(LINE_EDEFAULT);
        return;
      case EnquiryPackage.FIELD_POSITION__MULTI_LINE:
        setMultiLine(MULTI_LINE_EDEFAULT);
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
      case EnquiryPackage.FIELD_POSITION__PAGE_THROW:
        return PAGE_THROW_EDEFAULT == null ? pageThrow != null : !PAGE_THROW_EDEFAULT.equals(pageThrow);
      case EnquiryPackage.FIELD_POSITION__COLUMN:
        return COLUMN_EDEFAULT == null ? column != null : !COLUMN_EDEFAULT.equals(column);
      case EnquiryPackage.FIELD_POSITION__RELATIVE:
        return RELATIVE_EDEFAULT == null ? relative != null : !RELATIVE_EDEFAULT.equals(relative);
      case EnquiryPackage.FIELD_POSITION__LINE:
        return LINE_EDEFAULT == null ? line != null : !LINE_EDEFAULT.equals(line);
      case EnquiryPackage.FIELD_POSITION__MULTI_LINE:
        return MULTI_LINE_EDEFAULT == null ? multiLine != null : !MULTI_LINE_EDEFAULT.equals(multiLine);
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
    result.append(" (pageThrow: ");
    result.append(pageThrow);
    result.append(", column: ");
    result.append(column);
    result.append(", relative: ");
    result.append(relative);
    result.append(", line: ");
    result.append(line);
    result.append(", multiLine: ");
    result.append(multiLine);
    result.append(')');
    return result.toString();
  }

} //FieldPositionImpl
