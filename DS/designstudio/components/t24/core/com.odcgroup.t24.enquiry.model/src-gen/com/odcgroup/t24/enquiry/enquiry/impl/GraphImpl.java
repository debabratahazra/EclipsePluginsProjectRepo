/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.Axis;
import com.odcgroup.t24.enquiry.enquiry.Dimension;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Graph;
import com.odcgroup.t24.enquiry.enquiry.Label;
import com.odcgroup.t24.enquiry.enquiry.Legend;
import com.odcgroup.t24.enquiry.enquiry.Margins;
import com.odcgroup.t24.enquiry.enquiry.Scale;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getLabels <em>Labels</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getDimension <em>Dimension</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getMargins <em>Margins</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getScale <em>Scale</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getLegend <em>Legend</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getXAxis <em>XAxis</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getYAxis <em>YAxis</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.GraphImpl#getZAxis <em>ZAxis</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GraphImpl extends MinimalEObjectImpl.Container implements Graph
{
  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

  /**
   * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabels()
   * @generated
   * @ordered
   */
  protected EList<Label> labels;

  /**
   * The cached value of the '{@link #getDimension() <em>Dimension</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDimension()
   * @generated
   * @ordered
   */
  protected Dimension dimension;

  /**
   * The cached value of the '{@link #getMargins() <em>Margins</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMargins()
   * @generated
   * @ordered
   */
  protected Margins margins;

  /**
   * The cached value of the '{@link #getScale() <em>Scale</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getScale()
   * @generated
   * @ordered
   */
  protected Scale scale;

  /**
   * The cached value of the '{@link #getLegend() <em>Legend</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLegend()
   * @generated
   * @ordered
   */
  protected Legend legend;

  /**
   * The cached value of the '{@link #getXAxis() <em>XAxis</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getXAxis()
   * @generated
   * @ordered
   */
  protected Axis xAxis;

  /**
   * The cached value of the '{@link #getYAxis() <em>YAxis</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getYAxis()
   * @generated
   * @ordered
   */
  protected Axis yAxis;

  /**
   * The cached value of the '{@link #getZAxis() <em>ZAxis</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getZAxis()
   * @generated
   * @ordered
   */
  protected Axis zAxis;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected GraphImpl()
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
    return EnquiryPackage.Literals.GRAPH;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType)
  {
    String oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__TYPE, oldType, type));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Label> getLabels()
  {
    if (labels == null)
    {
      labels = new EObjectContainmentEList<Label>(Label.class, this, EnquiryPackage.GRAPH__LABELS);
    }
    return labels;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Dimension getDimension()
  {
    return dimension;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDimension(Dimension newDimension, NotificationChain msgs)
  {
    Dimension oldDimension = dimension;
    dimension = newDimension;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__DIMENSION, oldDimension, newDimension);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDimension(Dimension newDimension)
  {
    if (newDimension != dimension)
    {
      NotificationChain msgs = null;
      if (dimension != null)
        msgs = ((InternalEObject)dimension).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__DIMENSION, null, msgs);
      if (newDimension != null)
        msgs = ((InternalEObject)newDimension).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__DIMENSION, null, msgs);
      msgs = basicSetDimension(newDimension, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__DIMENSION, newDimension, newDimension));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Margins getMargins()
  {
    return margins;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetMargins(Margins newMargins, NotificationChain msgs)
  {
    Margins oldMargins = margins;
    margins = newMargins;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__MARGINS, oldMargins, newMargins);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMargins(Margins newMargins)
  {
    if (newMargins != margins)
    {
      NotificationChain msgs = null;
      if (margins != null)
        msgs = ((InternalEObject)margins).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__MARGINS, null, msgs);
      if (newMargins != null)
        msgs = ((InternalEObject)newMargins).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__MARGINS, null, msgs);
      msgs = basicSetMargins(newMargins, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__MARGINS, newMargins, newMargins));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Scale getScale()
  {
    return scale;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetScale(Scale newScale, NotificationChain msgs)
  {
    Scale oldScale = scale;
    scale = newScale;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__SCALE, oldScale, newScale);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setScale(Scale newScale)
  {
    if (newScale != scale)
    {
      NotificationChain msgs = null;
      if (scale != null)
        msgs = ((InternalEObject)scale).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__SCALE, null, msgs);
      if (newScale != null)
        msgs = ((InternalEObject)newScale).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__SCALE, null, msgs);
      msgs = basicSetScale(newScale, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__SCALE, newScale, newScale));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Legend getLegend()
  {
    return legend;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetLegend(Legend newLegend, NotificationChain msgs)
  {
    Legend oldLegend = legend;
    legend = newLegend;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__LEGEND, oldLegend, newLegend);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLegend(Legend newLegend)
  {
    if (newLegend != legend)
    {
      NotificationChain msgs = null;
      if (legend != null)
        msgs = ((InternalEObject)legend).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__LEGEND, null, msgs);
      if (newLegend != null)
        msgs = ((InternalEObject)newLegend).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__LEGEND, null, msgs);
      msgs = basicSetLegend(newLegend, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__LEGEND, newLegend, newLegend));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Axis getXAxis()
  {
    return xAxis;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetXAxis(Axis newXAxis, NotificationChain msgs)
  {
    Axis oldXAxis = xAxis;
    xAxis = newXAxis;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__XAXIS, oldXAxis, newXAxis);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setXAxis(Axis newXAxis)
  {
    if (newXAxis != xAxis)
    {
      NotificationChain msgs = null;
      if (xAxis != null)
        msgs = ((InternalEObject)xAxis).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__XAXIS, null, msgs);
      if (newXAxis != null)
        msgs = ((InternalEObject)newXAxis).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__XAXIS, null, msgs);
      msgs = basicSetXAxis(newXAxis, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__XAXIS, newXAxis, newXAxis));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Axis getYAxis()
  {
    return yAxis;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetYAxis(Axis newYAxis, NotificationChain msgs)
  {
    Axis oldYAxis = yAxis;
    yAxis = newYAxis;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__YAXIS, oldYAxis, newYAxis);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setYAxis(Axis newYAxis)
  {
    if (newYAxis != yAxis)
    {
      NotificationChain msgs = null;
      if (yAxis != null)
        msgs = ((InternalEObject)yAxis).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__YAXIS, null, msgs);
      if (newYAxis != null)
        msgs = ((InternalEObject)newYAxis).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__YAXIS, null, msgs);
      msgs = basicSetYAxis(newYAxis, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__YAXIS, newYAxis, newYAxis));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Axis getZAxis()
  {
    return zAxis;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetZAxis(Axis newZAxis, NotificationChain msgs)
  {
    Axis oldZAxis = zAxis;
    zAxis = newZAxis;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__ZAXIS, oldZAxis, newZAxis);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setZAxis(Axis newZAxis)
  {
    if (newZAxis != zAxis)
    {
      NotificationChain msgs = null;
      if (zAxis != null)
        msgs = ((InternalEObject)zAxis).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__ZAXIS, null, msgs);
      if (newZAxis != null)
        msgs = ((InternalEObject)newZAxis).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.GRAPH__ZAXIS, null, msgs);
      msgs = basicSetZAxis(newZAxis, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.GRAPH__ZAXIS, newZAxis, newZAxis));
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
      case EnquiryPackage.GRAPH__LABELS:
        return ((InternalEList<?>)getLabels()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.GRAPH__DIMENSION:
        return basicSetDimension(null, msgs);
      case EnquiryPackage.GRAPH__MARGINS:
        return basicSetMargins(null, msgs);
      case EnquiryPackage.GRAPH__SCALE:
        return basicSetScale(null, msgs);
      case EnquiryPackage.GRAPH__LEGEND:
        return basicSetLegend(null, msgs);
      case EnquiryPackage.GRAPH__XAXIS:
        return basicSetXAxis(null, msgs);
      case EnquiryPackage.GRAPH__YAXIS:
        return basicSetYAxis(null, msgs);
      case EnquiryPackage.GRAPH__ZAXIS:
        return basicSetZAxis(null, msgs);
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
      case EnquiryPackage.GRAPH__TYPE:
        return getType();
      case EnquiryPackage.GRAPH__LABELS:
        return getLabels();
      case EnquiryPackage.GRAPH__DIMENSION:
        return getDimension();
      case EnquiryPackage.GRAPH__MARGINS:
        return getMargins();
      case EnquiryPackage.GRAPH__SCALE:
        return getScale();
      case EnquiryPackage.GRAPH__LEGEND:
        return getLegend();
      case EnquiryPackage.GRAPH__XAXIS:
        return getXAxis();
      case EnquiryPackage.GRAPH__YAXIS:
        return getYAxis();
      case EnquiryPackage.GRAPH__ZAXIS:
        return getZAxis();
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
      case EnquiryPackage.GRAPH__TYPE:
        setType((String)newValue);
        return;
      case EnquiryPackage.GRAPH__LABELS:
        getLabels().clear();
        getLabels().addAll((Collection<? extends Label>)newValue);
        return;
      case EnquiryPackage.GRAPH__DIMENSION:
        setDimension((Dimension)newValue);
        return;
      case EnquiryPackage.GRAPH__MARGINS:
        setMargins((Margins)newValue);
        return;
      case EnquiryPackage.GRAPH__SCALE:
        setScale((Scale)newValue);
        return;
      case EnquiryPackage.GRAPH__LEGEND:
        setLegend((Legend)newValue);
        return;
      case EnquiryPackage.GRAPH__XAXIS:
        setXAxis((Axis)newValue);
        return;
      case EnquiryPackage.GRAPH__YAXIS:
        setYAxis((Axis)newValue);
        return;
      case EnquiryPackage.GRAPH__ZAXIS:
        setZAxis((Axis)newValue);
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
      case EnquiryPackage.GRAPH__TYPE:
        setType(TYPE_EDEFAULT);
        return;
      case EnquiryPackage.GRAPH__LABELS:
        getLabels().clear();
        return;
      case EnquiryPackage.GRAPH__DIMENSION:
        setDimension((Dimension)null);
        return;
      case EnquiryPackage.GRAPH__MARGINS:
        setMargins((Margins)null);
        return;
      case EnquiryPackage.GRAPH__SCALE:
        setScale((Scale)null);
        return;
      case EnquiryPackage.GRAPH__LEGEND:
        setLegend((Legend)null);
        return;
      case EnquiryPackage.GRAPH__XAXIS:
        setXAxis((Axis)null);
        return;
      case EnquiryPackage.GRAPH__YAXIS:
        setYAxis((Axis)null);
        return;
      case EnquiryPackage.GRAPH__ZAXIS:
        setZAxis((Axis)null);
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
      case EnquiryPackage.GRAPH__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
      case EnquiryPackage.GRAPH__LABELS:
        return labels != null && !labels.isEmpty();
      case EnquiryPackage.GRAPH__DIMENSION:
        return dimension != null;
      case EnquiryPackage.GRAPH__MARGINS:
        return margins != null;
      case EnquiryPackage.GRAPH__SCALE:
        return scale != null;
      case EnquiryPackage.GRAPH__LEGEND:
        return legend != null;
      case EnquiryPackage.GRAPH__XAXIS:
        return xAxis != null;
      case EnquiryPackage.GRAPH__YAXIS:
        return yAxis != null;
      case EnquiryPackage.GRAPH__ZAXIS:
        return zAxis != null;
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
    result.append(" (type: ");
    result.append(type);
    result.append(')');
    return result.toString();
  }

} //GraphImpl
