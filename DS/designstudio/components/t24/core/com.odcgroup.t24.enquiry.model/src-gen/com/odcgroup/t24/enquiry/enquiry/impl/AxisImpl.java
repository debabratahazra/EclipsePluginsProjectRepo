/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.Axis;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Axis</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl#getField <em>Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl#isDisplayLegend <em>Display Legend</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.AxisImpl#isShowGrid <em>Show Grid</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AxisImpl extends MinimalEObjectImpl.Container implements Axis
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
   * The default value of the '{@link #isDisplayLegend() <em>Display Legend</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDisplayLegend()
   * @generated
   * @ordered
   */
  protected static final boolean DISPLAY_LEGEND_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isDisplayLegend() <em>Display Legend</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isDisplayLegend()
   * @generated
   * @ordered
   */
  protected boolean displayLegend = DISPLAY_LEGEND_EDEFAULT;

  /**
   * The default value of the '{@link #isShowGrid() <em>Show Grid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isShowGrid()
   * @generated
   * @ordered
   */
  protected static final boolean SHOW_GRID_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isShowGrid() <em>Show Grid</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isShowGrid()
   * @generated
   * @ordered
   */
  protected boolean showGrid = SHOW_GRID_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AxisImpl()
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
    return EnquiryPackage.Literals.AXIS;
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
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.AXIS__FIELD, oldField, field));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isDisplayLegend()
  {
    return displayLegend;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDisplayLegend(boolean newDisplayLegend)
  {
    boolean oldDisplayLegend = displayLegend;
    displayLegend = newDisplayLegend;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.AXIS__DISPLAY_LEGEND, oldDisplayLegend, displayLegend));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isShowGrid()
  {
    return showGrid;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setShowGrid(boolean newShowGrid)
  {
    boolean oldShowGrid = showGrid;
    showGrid = newShowGrid;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.AXIS__SHOW_GRID, oldShowGrid, showGrid));
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
      case EnquiryPackage.AXIS__FIELD:
        return getField();
      case EnquiryPackage.AXIS__DISPLAY_LEGEND:
        return isDisplayLegend();
      case EnquiryPackage.AXIS__SHOW_GRID:
        return isShowGrid();
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
      case EnquiryPackage.AXIS__FIELD:
        setField((String)newValue);
        return;
      case EnquiryPackage.AXIS__DISPLAY_LEGEND:
        setDisplayLegend((Boolean)newValue);
        return;
      case EnquiryPackage.AXIS__SHOW_GRID:
        setShowGrid((Boolean)newValue);
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
      case EnquiryPackage.AXIS__FIELD:
        setField(FIELD_EDEFAULT);
        return;
      case EnquiryPackage.AXIS__DISPLAY_LEGEND:
        setDisplayLegend(DISPLAY_LEGEND_EDEFAULT);
        return;
      case EnquiryPackage.AXIS__SHOW_GRID:
        setShowGrid(SHOW_GRID_EDEFAULT);
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
      case EnquiryPackage.AXIS__FIELD:
        return FIELD_EDEFAULT == null ? field != null : !FIELD_EDEFAULT.equals(field);
      case EnquiryPackage.AXIS__DISPLAY_LEGEND:
        return displayLegend != DISPLAY_LEGEND_EDEFAULT;
      case EnquiryPackage.AXIS__SHOW_GRID:
        return showGrid != SHOW_GRID_EDEFAULT;
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
    result.append(", displayLegend: ");
    result.append(displayLegend);
    result.append(", showGrid: ");
    result.append(showGrid);
    result.append(')');
    return result.toString();
  }

} //AxisImpl
