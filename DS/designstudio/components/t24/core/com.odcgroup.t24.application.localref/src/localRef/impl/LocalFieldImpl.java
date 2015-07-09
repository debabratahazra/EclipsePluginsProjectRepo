/**
 */
package localRef.impl;

import localRef.LocalField;
import localRef.LocalRefPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Local Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link localRef.impl.LocalFieldImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link localRef.impl.LocalFieldImpl#getToolTip <em>Tool Tip</em>}</li>
 *   <li>{@link localRef.impl.LocalFieldImpl#getForApplication <em>For Application</em>}</li>
 *   <li>{@link localRef.impl.LocalFieldImpl#getGroupName <em>Group Name</em>}</li>
 *   <li>{@link localRef.impl.LocalFieldImpl#getLocalRefId <em>Local Ref Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocalFieldImpl extends MinimalEObjectImpl.Container implements LocalField
{
  /**
   * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected static final String LABEL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected String label = LABEL_EDEFAULT;

  /**
   * The default value of the '{@link #getToolTip() <em>Tool Tip</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolTip()
   * @generated
   * @ordered
   */
  protected static final String TOOL_TIP_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getToolTip() <em>Tool Tip</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolTip()
   * @generated
   * @ordered
   */
  protected String toolTip = TOOL_TIP_EDEFAULT;

  /**
   * The default value of the '{@link #getForApplication() <em>For Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getForApplication()
   * @generated
   * @ordered
   */
  protected static final String FOR_APPLICATION_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getForApplication() <em>For Application</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getForApplication()
   * @generated
   * @ordered
   */
  protected String forApplication = FOR_APPLICATION_EDEFAULT;

  /**
   * The default value of the '{@link #getGroupName() <em>Group Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGroupName()
   * @generated
   * @ordered
   */
  protected static final String GROUP_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getGroupName() <em>Group Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGroupName()
   * @generated
   * @ordered
   */
  protected String groupName = GROUP_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getLocalRefId() <em>Local Ref Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocalRefId()
   * @generated
   * @ordered
   */
  protected static final String LOCAL_REF_ID_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLocalRefId() <em>Local Ref Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLocalRefId()
   * @generated
   * @ordered
   */
  protected String localRefID = LOCAL_REF_ID_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected LocalFieldImpl()
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
    return LocalRefPackage.Literals.LOCAL_FIELD;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLabel()
  {
    return label;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLabel(String newLabel)
  {
    String oldLabel = label;
    label = newLabel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LocalRefPackage.LOCAL_FIELD__LABEL, oldLabel, label));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getToolTip()
  {
    return toolTip;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setToolTip(String newToolTip)
  {
    String oldToolTip = toolTip;
    toolTip = newToolTip;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LocalRefPackage.LOCAL_FIELD__TOOL_TIP, oldToolTip, toolTip));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getForApplication()
  {
    return forApplication;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setForApplication(String newForApplication)
  {
    String oldForApplication = forApplication;
    forApplication = newForApplication;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LocalRefPackage.LOCAL_FIELD__FOR_APPLICATION, oldForApplication, forApplication));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getGroupName()
  {
    return groupName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setGroupName(String newGroupName)
  {
    String oldGroupName = groupName;
    groupName = newGroupName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LocalRefPackage.LOCAL_FIELD__GROUP_NAME, oldGroupName, groupName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLocalRefID()
  {
    return localRefID;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLocalRefID(String newLocalRefId)
  {
    String oldLocalRefId = localRefId;
    localRefId = newLocalRefId;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, LocalRefPackage.LOCAL_FIELD__LOCAL_REF_ID, oldLocalRefId, localRefId));
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
      case LocalRefPackage.LOCAL_FIELD__LABEL:
        return getLabel();
      case LocalRefPackage.LOCAL_FIELD__TOOL_TIP:
        return getToolTip();
      case LocalRefPackage.LOCAL_FIELD__FOR_APPLICATION:
        return getForApplication();
      case LocalRefPackage.LOCAL_FIELD__GROUP_NAME:
        return getGroupName();
      case LocalRefPackage.LOCAL_FIELD__LOCAL_REF_ID:
        return getLocalRefId();
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
      case LocalRefPackage.LOCAL_FIELD__LABEL:
        setLabel((String)newValue);
        return;
      case LocalRefPackage.LOCAL_FIELD__TOOL_TIP:
        setToolTip((String)newValue);
        return;
      case LocalRefPackage.LOCAL_FIELD__FOR_APPLICATION:
        setForApplication((String)newValue);
        return;
      case LocalRefPackage.LOCAL_FIELD__GROUP_NAME:
        setGroupName((String)newValue);
        return;
      case LocalRefPackage.LOCAL_FIELD__LOCAL_REF_ID:
        setLocalRefId((String)newValue);
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
      case LocalRefPackage.LOCAL_FIELD__LABEL:
        setLabel(LABEL_EDEFAULT);
        return;
      case LocalRefPackage.LOCAL_FIELD__TOOL_TIP:
        setToolTip(TOOL_TIP_EDEFAULT);
        return;
      case LocalRefPackage.LOCAL_FIELD__FOR_APPLICATION:
        setForApplication(FOR_APPLICATION_EDEFAULT);
        return;
      case LocalRefPackage.LOCAL_FIELD__GROUP_NAME:
        setGroupName(GROUP_NAME_EDEFAULT);
        return;
      case LocalRefPackage.LOCAL_FIELD__LOCAL_REF_ID:
        setLocalRefId(LOCAL_REF_ID_EDEFAULT);
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
      case LocalRefPackage.LOCAL_FIELD__LABEL:
        return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
      case LocalRefPackage.LOCAL_FIELD__TOOL_TIP:
        return TOOL_TIP_EDEFAULT == null ? toolTip != null : !TOOL_TIP_EDEFAULT.equals(toolTip);
      case LocalRefPackage.LOCAL_FIELD__FOR_APPLICATION:
        return FOR_APPLICATION_EDEFAULT == null ? forApplication != null : !FOR_APPLICATION_EDEFAULT.equals(forApplication);
      case LocalRefPackage.LOCAL_FIELD__GROUP_NAME:
        return GROUP_NAME_EDEFAULT == null ? groupName != null : !GROUP_NAME_EDEFAULT.equals(groupName);
      case LocalRefPackage.LOCAL_FIELD__LOCAL_REF_ID:
        return LOCAL_REF_ID_EDEFAULT == null ? localRefId != null : !LOCAL_REF_ID_EDEFAULT.equals(localRefId);
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
    result.append(" (label: ");
    result.append(label);
    result.append(", toolTip: ");
    result.append(toolTip);
    result.append(", forApplication: ");
    result.append(forApplication);
    result.append(", groupName: ");
    result.append(groupName);
    result.append(", localRefID: ");
    result.append(localRefID);
    result.append(')');
    return result.toString();
  }

} //LocalFieldImpl
