/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;

import com.odcgroup.translation.translationDsl.Translations;

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
 * An implementation of the model object '<em><b>Drill Down</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getDrill_name <em>Drill name</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getLabelField <em>Label Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getImage <em>Image</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getInfo <em>Info</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getCriteria <em>Criteria</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.odcgroup.t24.enquiry.enquiry.impl.DrillDownImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DrillDownImpl extends MinimalEObjectImpl.Container implements DrillDown
{
  /**
   * The default value of the '{@link #getDrill_name() <em>Drill name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDrill_name()
   * @generated
   * @ordered
   */
  protected static final String DRILL_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getDrill_name() <em>Drill name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDrill_name()
   * @generated
   * @ordered
   */
  protected String drill_name = DRILL_NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDescription()
   * @generated
   * @ordered
   */
  protected Translations description;

  /**
   * The default value of the '{@link #getLabelField() <em>Label Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabelField()
   * @generated
   * @ordered
   */
  protected static final String LABEL_FIELD_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLabelField() <em>Label Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabelField()
   * @generated
   * @ordered
   */
  protected String labelField = LABEL_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getImage() <em>Image</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImage()
   * @generated
   * @ordered
   */
  protected static final String IMAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getImage() <em>Image</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getImage()
   * @generated
   * @ordered
   */
  protected String image = IMAGE_EDEFAULT;

  /**
   * The default value of the '{@link #getInfo() <em>Info</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInfo()
   * @generated
   * @ordered
   */
  protected static final String INFO_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getInfo() <em>Info</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInfo()
   * @generated
   * @ordered
   */
  protected String info = INFO_EDEFAULT;

  /**
   * The cached value of the '{@link #getCriteria() <em>Criteria</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCriteria()
   * @generated
   * @ordered
   */
  protected EList<SelectionCriteria> criteria;

  /**
   * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getParameters()
   * @generated
   * @ordered
   */
  protected Parameters parameters;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected DrillDownType type;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DrillDownImpl()
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
    return EnquiryPackage.Literals.DRILL_DOWN;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getDrill_name()
  {
    return drill_name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDrill_name(String newDrill_name)
  {
    String oldDrill_name = drill_name;
    drill_name = newDrill_name;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__DRILL_NAME, oldDrill_name, drill_name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getDescription()
  {
    return description;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetDescription(Translations newDescription, NotificationChain msgs)
  {
    Translations oldDescription = description;
    description = newDescription;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__DESCRIPTION, oldDescription, newDescription);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDescription(Translations newDescription)
  {
    if (newDescription != description)
    {
      NotificationChain msgs = null;
      if (description != null)
        msgs = ((InternalEObject)description).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.DRILL_DOWN__DESCRIPTION, null, msgs);
      if (newDescription != null)
        msgs = ((InternalEObject)newDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.DRILL_DOWN__DESCRIPTION, null, msgs);
      msgs = basicSetDescription(newDescription, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__DESCRIPTION, newDescription, newDescription));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLabelField()
  {
    return labelField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLabelField(String newLabelField)
  {
    String oldLabelField = labelField;
    labelField = newLabelField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__LABEL_FIELD, oldLabelField, labelField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getImage()
  {
    return image;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setImage(String newImage)
  {
    String oldImage = image;
    image = newImage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__IMAGE, oldImage, image));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getInfo()
  {
    return info;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInfo(String newInfo)
  {
    String oldInfo = info;
    info = newInfo;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__INFO, oldInfo, info));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<SelectionCriteria> getCriteria()
  {
    if (criteria == null)
    {
      criteria = new EObjectContainmentEList<SelectionCriteria>(SelectionCriteria.class, this, EnquiryPackage.DRILL_DOWN__CRITERIA);
    }
    return criteria;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameters getParameters()
  {
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetParameters(Parameters newParameters, NotificationChain msgs)
  {
    Parameters oldParameters = parameters;
    parameters = newParameters;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__PARAMETERS, oldParameters, newParameters);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setParameters(Parameters newParameters)
  {
    if (newParameters != parameters)
    {
      NotificationChain msgs = null;
      if (parameters != null)
        msgs = ((InternalEObject)parameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.DRILL_DOWN__PARAMETERS, null, msgs);
      if (newParameters != null)
        msgs = ((InternalEObject)newParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.DRILL_DOWN__PARAMETERS, null, msgs);
      msgs = basicSetParameters(newParameters, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__PARAMETERS, newParameters, newParameters));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DrillDownType getType()
  {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetType(DrillDownType newType, NotificationChain msgs)
  {
    DrillDownType oldType = type;
    type = newType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__TYPE, oldType, newType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(DrillDownType newType)
  {
    if (newType != type)
    {
      NotificationChain msgs = null;
      if (type != null)
        msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.DRILL_DOWN__TYPE, null, msgs);
      if (newType != null)
        msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EnquiryPackage.DRILL_DOWN__TYPE, null, msgs);
      msgs = basicSetType(newType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, EnquiryPackage.DRILL_DOWN__TYPE, newType, newType));
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
      case EnquiryPackage.DRILL_DOWN__DESCRIPTION:
        return basicSetDescription(null, msgs);
      case EnquiryPackage.DRILL_DOWN__CRITERIA:
        return ((InternalEList<?>)getCriteria()).basicRemove(otherEnd, msgs);
      case EnquiryPackage.DRILL_DOWN__PARAMETERS:
        return basicSetParameters(null, msgs);
      case EnquiryPackage.DRILL_DOWN__TYPE:
        return basicSetType(null, msgs);
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
      case EnquiryPackage.DRILL_DOWN__DRILL_NAME:
        return getDrill_name();
      case EnquiryPackage.DRILL_DOWN__DESCRIPTION:
        return getDescription();
      case EnquiryPackage.DRILL_DOWN__LABEL_FIELD:
        return getLabelField();
      case EnquiryPackage.DRILL_DOWN__IMAGE:
        return getImage();
      case EnquiryPackage.DRILL_DOWN__INFO:
        return getInfo();
      case EnquiryPackage.DRILL_DOWN__CRITERIA:
        return getCriteria();
      case EnquiryPackage.DRILL_DOWN__PARAMETERS:
        return getParameters();
      case EnquiryPackage.DRILL_DOWN__TYPE:
        return getType();
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
      case EnquiryPackage.DRILL_DOWN__DRILL_NAME:
        setDrill_name((String)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__DESCRIPTION:
        setDescription((Translations)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__LABEL_FIELD:
        setLabelField((String)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__IMAGE:
        setImage((String)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__INFO:
        setInfo((String)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__CRITERIA:
        getCriteria().clear();
        getCriteria().addAll((Collection<? extends SelectionCriteria>)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__PARAMETERS:
        setParameters((Parameters)newValue);
        return;
      case EnquiryPackage.DRILL_DOWN__TYPE:
        setType((DrillDownType)newValue);
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
      case EnquiryPackage.DRILL_DOWN__DRILL_NAME:
        setDrill_name(DRILL_NAME_EDEFAULT);
        return;
      case EnquiryPackage.DRILL_DOWN__DESCRIPTION:
        setDescription((Translations)null);
        return;
      case EnquiryPackage.DRILL_DOWN__LABEL_FIELD:
        setLabelField(LABEL_FIELD_EDEFAULT);
        return;
      case EnquiryPackage.DRILL_DOWN__IMAGE:
        setImage(IMAGE_EDEFAULT);
        return;
      case EnquiryPackage.DRILL_DOWN__INFO:
        setInfo(INFO_EDEFAULT);
        return;
      case EnquiryPackage.DRILL_DOWN__CRITERIA:
        getCriteria().clear();
        return;
      case EnquiryPackage.DRILL_DOWN__PARAMETERS:
        setParameters((Parameters)null);
        return;
      case EnquiryPackage.DRILL_DOWN__TYPE:
        setType((DrillDownType)null);
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
      case EnquiryPackage.DRILL_DOWN__DRILL_NAME:
        return DRILL_NAME_EDEFAULT == null ? drill_name != null : !DRILL_NAME_EDEFAULT.equals(drill_name);
      case EnquiryPackage.DRILL_DOWN__DESCRIPTION:
        return description != null;
      case EnquiryPackage.DRILL_DOWN__LABEL_FIELD:
        return LABEL_FIELD_EDEFAULT == null ? labelField != null : !LABEL_FIELD_EDEFAULT.equals(labelField);
      case EnquiryPackage.DRILL_DOWN__IMAGE:
        return IMAGE_EDEFAULT == null ? image != null : !IMAGE_EDEFAULT.equals(image);
      case EnquiryPackage.DRILL_DOWN__INFO:
        return INFO_EDEFAULT == null ? info != null : !INFO_EDEFAULT.equals(info);
      case EnquiryPackage.DRILL_DOWN__CRITERIA:
        return criteria != null && !criteria.isEmpty();
      case EnquiryPackage.DRILL_DOWN__PARAMETERS:
        return parameters != null;
      case EnquiryPackage.DRILL_DOWN__TYPE:
        return type != null;
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
    result.append(" (drill_name: ");
    result.append(drill_name);
    result.append(", labelField: ");
    result.append(labelField);
    result.append(", image: ");
    result.append(image);
    result.append(", info: ");
    result.append(info);
    result.append(')');
    return result.toString();
  }

} //DrillDownImpl
