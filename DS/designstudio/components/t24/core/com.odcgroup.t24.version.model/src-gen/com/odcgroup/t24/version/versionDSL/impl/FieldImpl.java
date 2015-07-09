/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.t24.version.versionDSL.CaseConvention;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.DisplayType;
import com.odcgroup.t24.version.versionDSL.Expansion;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.InputBehaviour;
import com.odcgroup.t24.version.versionDSL.PopupBehaviour;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.t24.version.versionDSL.YesNo;

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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getDisplayType <em>Display Type</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getInputBehaviour <em>Input Behaviour</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getCaseConvention <em>Case Convention</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getMaxLength <em>Max Length</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getEnrichmentLength <em>Enrichment Length</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getExpansion <em>Expansion</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getRightAdjust <em>Right Adjust</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getEnrichment <em>Enrichment</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getColumn <em>Column</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getRow <em>Row</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getMandatory <em>Mandatory</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getRekeyRequired <em>Rekey Required</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getHyperlink <em>Hyperlink</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getHotValidate <em>Hot Validate</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getHotField <em>Hot Field</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getWebValidate <em>Web Validate</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getSelectionEnquiry <em>Selection Enquiry</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getEnquiryParameter <em>Enquiry Parameter</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getPopupBehaviour <em>Popup Behaviour</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getDefaults <em>Defaults</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getToolTip <em>Tool Tip</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getValidationRoutines <em>Validation Routines</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getMV <em>MV</em>}</li>
 *   <li>{@link com.odcgroup.t24.version.versionDSL.impl.FieldImpl#getSV <em>SV</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldImpl extends MinimalEObjectImpl.Container implements Field
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getDisplayType() <em>Display Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplayType()
   * @generated
   * @ordered
   */
  protected static final DisplayType DISPLAY_TYPE_EDEFAULT = DisplayType.NONE;

  /**
   * The cached value of the '{@link #getDisplayType() <em>Display Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDisplayType()
   * @generated
   * @ordered
   */
  protected DisplayType displayType = DISPLAY_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #getInputBehaviour() <em>Input Behaviour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputBehaviour()
   * @generated
   * @ordered
   */
  protected static final InputBehaviour INPUT_BEHAVIOUR_EDEFAULT = InputBehaviour.NONE;

  /**
   * The cached value of the '{@link #getInputBehaviour() <em>Input Behaviour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInputBehaviour()
   * @generated
   * @ordered
   */
  protected InputBehaviour inputBehaviour = INPUT_BEHAVIOUR_EDEFAULT;

  /**
   * The default value of the '{@link #getCaseConvention() <em>Case Convention</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCaseConvention()
   * @generated
   * @ordered
   */
  protected static final CaseConvention CASE_CONVENTION_EDEFAULT = CaseConvention.NONE;

  /**
   * The cached value of the '{@link #getCaseConvention() <em>Case Convention</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getCaseConvention()
   * @generated
   * @ordered
   */
  protected CaseConvention caseConvention = CASE_CONVENTION_EDEFAULT;

  /**
   * The default value of the '{@link #getMaxLength() <em>Max Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxLength()
   * @generated
   * @ordered
   */
  protected static final Integer MAX_LENGTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMaxLength() <em>Max Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaxLength()
   * @generated
   * @ordered
   */
  protected Integer maxLength = MAX_LENGTH_EDEFAULT;

  /**
   * The default value of the '{@link #getEnrichmentLength() <em>Enrichment Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnrichmentLength()
   * @generated
   * @ordered
   */
  protected static final Integer ENRICHMENT_LENGTH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEnrichmentLength() <em>Enrichment Length</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnrichmentLength()
   * @generated
   * @ordered
   */
  protected Integer enrichmentLength = ENRICHMENT_LENGTH_EDEFAULT;

  /**
   * The default value of the '{@link #getExpansion() <em>Expansion</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpansion()
   * @generated
   * @ordered
   */
  protected static final Expansion EXPANSION_EDEFAULT = Expansion.NONE;

  /**
   * The cached value of the '{@link #getExpansion() <em>Expansion</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpansion()
   * @generated
   * @ordered
   */
  protected Expansion expansion = EXPANSION_EDEFAULT;

  /**
   * The default value of the '{@link #getRightAdjust() <em>Right Adjust</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRightAdjust()
   * @generated
   * @ordered
   */
  protected static final YesNo RIGHT_ADJUST_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getRightAdjust() <em>Right Adjust</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRightAdjust()
   * @generated
   * @ordered
   */
  protected YesNo rightAdjust = RIGHT_ADJUST_EDEFAULT;

  /**
   * The default value of the '{@link #getEnrichment() <em>Enrichment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnrichment()
   * @generated
   * @ordered
   */
  protected static final YesNo ENRICHMENT_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getEnrichment() <em>Enrichment</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnrichment()
   * @generated
   * @ordered
   */
  protected YesNo enrichment = ENRICHMENT_EDEFAULT;

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
   * The default value of the '{@link #getRow() <em>Row</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRow()
   * @generated
   * @ordered
   */
  protected static final Integer ROW_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getRow() <em>Row</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRow()
   * @generated
   * @ordered
   */
  protected Integer row = ROW_EDEFAULT;

  /**
   * The default value of the '{@link #getMandatory() <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMandatory()
   * @generated
   * @ordered
   */
  protected static final YesNo MANDATORY_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getMandatory() <em>Mandatory</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMandatory()
   * @generated
   * @ordered
   */
  protected YesNo mandatory = MANDATORY_EDEFAULT;

  /**
   * The default value of the '{@link #getRekeyRequired() <em>Rekey Required</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRekeyRequired()
   * @generated
   * @ordered
   */
  protected static final YesNo REKEY_REQUIRED_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getRekeyRequired() <em>Rekey Required</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRekeyRequired()
   * @generated
   * @ordered
   */
  protected YesNo rekeyRequired = REKEY_REQUIRED_EDEFAULT;

  /**
   * The default value of the '{@link #getHyperlink() <em>Hyperlink</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHyperlink()
   * @generated
   * @ordered
   */
  protected static final String HYPERLINK_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getHyperlink() <em>Hyperlink</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHyperlink()
   * @generated
   * @ordered
   */
  protected String hyperlink = HYPERLINK_EDEFAULT;

  /**
   * The default value of the '{@link #getHotValidate() <em>Hot Validate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHotValidate()
   * @generated
   * @ordered
   */
  protected static final YesNo HOT_VALIDATE_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getHotValidate() <em>Hot Validate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHotValidate()
   * @generated
   * @ordered
   */
  protected YesNo hotValidate = HOT_VALIDATE_EDEFAULT;

  /**
   * The default value of the '{@link #getHotField() <em>Hot Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHotField()
   * @generated
   * @ordered
   */
  protected static final YesNo HOT_FIELD_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getHotField() <em>Hot Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHotField()
   * @generated
   * @ordered
   */
  protected YesNo hotField = HOT_FIELD_EDEFAULT;

  /**
   * The default value of the '{@link #getWebValidate() <em>Web Validate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebValidate()
   * @generated
   * @ordered
   */
  protected static final YesNo WEB_VALIDATE_EDEFAULT = YesNo.NULL;

  /**
   * The cached value of the '{@link #getWebValidate() <em>Web Validate</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getWebValidate()
   * @generated
   * @ordered
   */
  protected YesNo webValidate = WEB_VALIDATE_EDEFAULT;

  /**
   * The default value of the '{@link #getSelectionEnquiry() <em>Selection Enquiry</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSelectionEnquiry()
   * @generated
   * @ordered
   */
  protected static final String SELECTION_ENQUIRY_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSelectionEnquiry() <em>Selection Enquiry</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSelectionEnquiry()
   * @generated
   * @ordered
   */
  protected String selectionEnquiry = SELECTION_ENQUIRY_EDEFAULT;

  /**
   * The default value of the '{@link #getEnquiryParameter() <em>Enquiry Parameter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnquiryParameter()
   * @generated
   * @ordered
   */
  protected static final String ENQUIRY_PARAMETER_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getEnquiryParameter() <em>Enquiry Parameter</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEnquiryParameter()
   * @generated
   * @ordered
   */
  protected String enquiryParameter = ENQUIRY_PARAMETER_EDEFAULT;

  /**
   * The default value of the '{@link #getPopupBehaviour() <em>Popup Behaviour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPopupBehaviour()
   * @generated
   * @ordered
   */
  protected static final PopupBehaviour POPUP_BEHAVIOUR_EDEFAULT = PopupBehaviour.NONE;

  /**
   * The cached value of the '{@link #getPopupBehaviour() <em>Popup Behaviour</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPopupBehaviour()
   * @generated
   * @ordered
   */
  protected PopupBehaviour popupBehaviour = POPUP_BEHAVIOUR_EDEFAULT;

  /**
   * The cached value of the '{@link #getDefaults() <em>Defaults</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDefaults()
   * @generated
   * @ordered
   */
  protected EList<Default> defaults;

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
   * The cached value of the '{@link #getToolTip() <em>Tool Tip</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getToolTip()
   * @generated
   * @ordered
   */
  protected Translations toolTip;

  /**
   * The cached value of the '{@link #getValidationRoutines() <em>Validation Routines</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getValidationRoutines()
   * @generated
   * @ordered
   */
  protected EList<Routine> validationRoutines;

  /**
   * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttributes()
   * @generated
   * @ordered
   */
  protected EList<String> attributes;

  /**
   * The default value of the '{@link #getMV() <em>MV</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMV()
   * @generated
   * @ordered
   */
  protected static final Integer MV_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMV() <em>MV</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMV()
   * @generated
   * @ordered
   */
  protected Integer mv = MV_EDEFAULT;

  /**
   * The default value of the '{@link #getSV() <em>SV</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSV()
   * @generated
   * @ordered
   */
  protected static final Integer SV_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSV() <em>SV</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSV()
   * @generated
   * @ordered
   */
  protected Integer sv = SV_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FieldImpl()
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
    return VersionDSLPackage.Literals.FIELD;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DisplayType getDisplayType()
  {
    return displayType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setDisplayType(DisplayType newDisplayType)
  {
    DisplayType oldDisplayType = displayType;
    displayType = newDisplayType == null ? DISPLAY_TYPE_EDEFAULT : newDisplayType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__DISPLAY_TYPE, oldDisplayType, displayType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InputBehaviour getInputBehaviour()
  {
    return inputBehaviour;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInputBehaviour(InputBehaviour newInputBehaviour)
  {
    InputBehaviour oldInputBehaviour = inputBehaviour;
    inputBehaviour = newInputBehaviour == null ? INPUT_BEHAVIOUR_EDEFAULT : newInputBehaviour;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__INPUT_BEHAVIOUR, oldInputBehaviour, inputBehaviour));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CaseConvention getCaseConvention()
  {
    return caseConvention;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCaseConvention(CaseConvention newCaseConvention)
  {
    CaseConvention oldCaseConvention = caseConvention;
    caseConvention = newCaseConvention == null ? CASE_CONVENTION_EDEFAULT : newCaseConvention;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__CASE_CONVENTION, oldCaseConvention, caseConvention));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getMaxLength()
  {
    return maxLength;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMaxLength(Integer newMaxLength)
  {
    Integer oldMaxLength = maxLength;
    maxLength = newMaxLength;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__MAX_LENGTH, oldMaxLength, maxLength));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getEnrichmentLength()
  {
    return enrichmentLength;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnrichmentLength(Integer newEnrichmentLength)
  {
    Integer oldEnrichmentLength = enrichmentLength;
    enrichmentLength = newEnrichmentLength;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__ENRICHMENT_LENGTH, oldEnrichmentLength, enrichmentLength));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expansion getExpansion()
  {
    return expansion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpansion(Expansion newExpansion)
  {
    Expansion oldExpansion = expansion;
    expansion = newExpansion == null ? EXPANSION_EDEFAULT : newExpansion;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__EXPANSION, oldExpansion, expansion));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getRightAdjust()
  {
    return rightAdjust;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRightAdjust(YesNo newRightAdjust)
  {
    YesNo oldRightAdjust = rightAdjust;
    rightAdjust = newRightAdjust == null ? RIGHT_ADJUST_EDEFAULT : newRightAdjust;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__RIGHT_ADJUST, oldRightAdjust, rightAdjust));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getEnrichment()
  {
    return enrichment;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnrichment(YesNo newEnrichment)
  {
    YesNo oldEnrichment = enrichment;
    enrichment = newEnrichment == null ? ENRICHMENT_EDEFAULT : newEnrichment;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__ENRICHMENT, oldEnrichment, enrichment));
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
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__COLUMN, oldColumn, column));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getRow()
  {
    return row;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRow(Integer newRow)
  {
    Integer oldRow = row;
    row = newRow;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__ROW, oldRow, row));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getMandatory()
  {
    return mandatory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMandatory(YesNo newMandatory)
  {
    YesNo oldMandatory = mandatory;
    mandatory = newMandatory == null ? MANDATORY_EDEFAULT : newMandatory;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__MANDATORY, oldMandatory, mandatory));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getRekeyRequired()
  {
    return rekeyRequired;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRekeyRequired(YesNo newRekeyRequired)
  {
    YesNo oldRekeyRequired = rekeyRequired;
    rekeyRequired = newRekeyRequired == null ? REKEY_REQUIRED_EDEFAULT : newRekeyRequired;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__REKEY_REQUIRED, oldRekeyRequired, rekeyRequired));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getHyperlink()
  {
    return hyperlink;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHyperlink(String newHyperlink)
  {
    String oldHyperlink = hyperlink;
    hyperlink = newHyperlink;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__HYPERLINK, oldHyperlink, hyperlink));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getHotValidate()
  {
    return hotValidate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHotValidate(YesNo newHotValidate)
  {
    YesNo oldHotValidate = hotValidate;
    hotValidate = newHotValidate == null ? HOT_VALIDATE_EDEFAULT : newHotValidate;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__HOT_VALIDATE, oldHotValidate, hotValidate));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getHotField()
  {
    return hotField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHotField(YesNo newHotField)
  {
    YesNo oldHotField = hotField;
    hotField = newHotField == null ? HOT_FIELD_EDEFAULT : newHotField;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__HOT_FIELD, oldHotField, hotField));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo getWebValidate()
  {
    return webValidate;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setWebValidate(YesNo newWebValidate)
  {
    YesNo oldWebValidate = webValidate;
    webValidate = newWebValidate == null ? WEB_VALIDATE_EDEFAULT : newWebValidate;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__WEB_VALIDATE, oldWebValidate, webValidate));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSelectionEnquiry()
  {
    return selectionEnquiry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSelectionEnquiry(String newSelectionEnquiry)
  {
    String oldSelectionEnquiry = selectionEnquiry;
    selectionEnquiry = newSelectionEnquiry;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__SELECTION_ENQUIRY, oldSelectionEnquiry, selectionEnquiry));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getEnquiryParameter()
  {
    return enquiryParameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setEnquiryParameter(String newEnquiryParameter)
  {
    String oldEnquiryParameter = enquiryParameter;
    enquiryParameter = newEnquiryParameter;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__ENQUIRY_PARAMETER, oldEnquiryParameter, enquiryParameter));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PopupBehaviour getPopupBehaviour()
  {
    return popupBehaviour;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPopupBehaviour(PopupBehaviour newPopupBehaviour)
  {
    PopupBehaviour oldPopupBehaviour = popupBehaviour;
    popupBehaviour = newPopupBehaviour == null ? POPUP_BEHAVIOUR_EDEFAULT : newPopupBehaviour;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__POPUP_BEHAVIOUR, oldPopupBehaviour, popupBehaviour));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Default> getDefaults()
  {
    if (defaults == null)
    {
      defaults = new EObjectContainmentEList<Default>(Default.class, this, VersionDSLPackage.FIELD__DEFAULTS);
    }
    return defaults;
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
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__LABEL, oldLabel, newLabel);
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
        msgs = ((InternalEObject)label).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.FIELD__LABEL, null, msgs);
      if (newLabel != null)
        msgs = ((InternalEObject)newLabel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.FIELD__LABEL, null, msgs);
      msgs = basicSetLabel(newLabel, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__LABEL, newLabel, newLabel));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Translations getToolTip()
  {
    return toolTip;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetToolTip(Translations newToolTip, NotificationChain msgs)
  {
    Translations oldToolTip = toolTip;
    toolTip = newToolTip;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__TOOL_TIP, oldToolTip, newToolTip);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setToolTip(Translations newToolTip)
  {
    if (newToolTip != toolTip)
    {
      NotificationChain msgs = null;
      if (toolTip != null)
        msgs = ((InternalEObject)toolTip).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.FIELD__TOOL_TIP, null, msgs);
      if (newToolTip != null)
        msgs = ((InternalEObject)newToolTip).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - VersionDSLPackage.FIELD__TOOL_TIP, null, msgs);
      msgs = basicSetToolTip(newToolTip, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__TOOL_TIP, newToolTip, newToolTip));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Routine> getValidationRoutines()
  {
    if (validationRoutines == null)
    {
      validationRoutines = new EObjectContainmentEList<Routine>(Routine.class, this, VersionDSLPackage.FIELD__VALIDATION_ROUTINES);
    }
    return validationRoutines;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<String> getAttributes()
  {
    if (attributes == null)
    {
      attributes = new EDataTypeEList<String>(String.class, this, VersionDSLPackage.FIELD__ATTRIBUTES);
    }
    return attributes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getMV()
  {
    return mv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMV(Integer newMV)
  {
    Integer oldMV = mv;
    mv = newMV;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__MV, oldMV, mv));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Integer getSV()
  {
    return sv;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSV(Integer newSV)
  {
    Integer oldSV = sv;
    sv = newSV;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, VersionDSLPackage.FIELD__SV, oldSV, sv));
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
      case VersionDSLPackage.FIELD__DEFAULTS:
        return ((InternalEList<?>)getDefaults()).basicRemove(otherEnd, msgs);
      case VersionDSLPackage.FIELD__LABEL:
        return basicSetLabel(null, msgs);
      case VersionDSLPackage.FIELD__TOOL_TIP:
        return basicSetToolTip(null, msgs);
      case VersionDSLPackage.FIELD__VALIDATION_ROUTINES:
        return ((InternalEList<?>)getValidationRoutines()).basicRemove(otherEnd, msgs);
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
      case VersionDSLPackage.FIELD__NAME:
        return getName();
      case VersionDSLPackage.FIELD__DISPLAY_TYPE:
        return getDisplayType();
      case VersionDSLPackage.FIELD__INPUT_BEHAVIOUR:
        return getInputBehaviour();
      case VersionDSLPackage.FIELD__CASE_CONVENTION:
        return getCaseConvention();
      case VersionDSLPackage.FIELD__MAX_LENGTH:
        return getMaxLength();
      case VersionDSLPackage.FIELD__ENRICHMENT_LENGTH:
        return getEnrichmentLength();
      case VersionDSLPackage.FIELD__EXPANSION:
        return getExpansion();
      case VersionDSLPackage.FIELD__RIGHT_ADJUST:
        return getRightAdjust();
      case VersionDSLPackage.FIELD__ENRICHMENT:
        return getEnrichment();
      case VersionDSLPackage.FIELD__COLUMN:
        return getColumn();
      case VersionDSLPackage.FIELD__ROW:
        return getRow();
      case VersionDSLPackage.FIELD__MANDATORY:
        return getMandatory();
      case VersionDSLPackage.FIELD__REKEY_REQUIRED:
        return getRekeyRequired();
      case VersionDSLPackage.FIELD__HYPERLINK:
        return getHyperlink();
      case VersionDSLPackage.FIELD__HOT_VALIDATE:
        return getHotValidate();
      case VersionDSLPackage.FIELD__HOT_FIELD:
        return getHotField();
      case VersionDSLPackage.FIELD__WEB_VALIDATE:
        return getWebValidate();
      case VersionDSLPackage.FIELD__SELECTION_ENQUIRY:
        return getSelectionEnquiry();
      case VersionDSLPackage.FIELD__ENQUIRY_PARAMETER:
        return getEnquiryParameter();
      case VersionDSLPackage.FIELD__POPUP_BEHAVIOUR:
        return getPopupBehaviour();
      case VersionDSLPackage.FIELD__DEFAULTS:
        return getDefaults();
      case VersionDSLPackage.FIELD__LABEL:
        return getLabel();
      case VersionDSLPackage.FIELD__TOOL_TIP:
        return getToolTip();
      case VersionDSLPackage.FIELD__VALIDATION_ROUTINES:
        return getValidationRoutines();
      case VersionDSLPackage.FIELD__ATTRIBUTES:
        return getAttributes();
      case VersionDSLPackage.FIELD__MV:
        return getMV();
      case VersionDSLPackage.FIELD__SV:
        return getSV();
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
      case VersionDSLPackage.FIELD__NAME:
        setName((String)newValue);
        return;
      case VersionDSLPackage.FIELD__DISPLAY_TYPE:
        setDisplayType((DisplayType)newValue);
        return;
      case VersionDSLPackage.FIELD__INPUT_BEHAVIOUR:
        setInputBehaviour((InputBehaviour)newValue);
        return;
      case VersionDSLPackage.FIELD__CASE_CONVENTION:
        setCaseConvention((CaseConvention)newValue);
        return;
      case VersionDSLPackage.FIELD__MAX_LENGTH:
        setMaxLength((Integer)newValue);
        return;
      case VersionDSLPackage.FIELD__ENRICHMENT_LENGTH:
        setEnrichmentLength((Integer)newValue);
        return;
      case VersionDSLPackage.FIELD__EXPANSION:
        setExpansion((Expansion)newValue);
        return;
      case VersionDSLPackage.FIELD__RIGHT_ADJUST:
        setRightAdjust((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__ENRICHMENT:
        setEnrichment((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__COLUMN:
        setColumn((Integer)newValue);
        return;
      case VersionDSLPackage.FIELD__ROW:
        setRow((Integer)newValue);
        return;
      case VersionDSLPackage.FIELD__MANDATORY:
        setMandatory((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__REKEY_REQUIRED:
        setRekeyRequired((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__HYPERLINK:
        setHyperlink((String)newValue);
        return;
      case VersionDSLPackage.FIELD__HOT_VALIDATE:
        setHotValidate((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__HOT_FIELD:
        setHotField((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__WEB_VALIDATE:
        setWebValidate((YesNo)newValue);
        return;
      case VersionDSLPackage.FIELD__SELECTION_ENQUIRY:
        setSelectionEnquiry((String)newValue);
        return;
      case VersionDSLPackage.FIELD__ENQUIRY_PARAMETER:
        setEnquiryParameter((String)newValue);
        return;
      case VersionDSLPackage.FIELD__POPUP_BEHAVIOUR:
        setPopupBehaviour((PopupBehaviour)newValue);
        return;
      case VersionDSLPackage.FIELD__DEFAULTS:
        getDefaults().clear();
        getDefaults().addAll((Collection<? extends Default>)newValue);
        return;
      case VersionDSLPackage.FIELD__LABEL:
        setLabel((Translations)newValue);
        return;
      case VersionDSLPackage.FIELD__TOOL_TIP:
        setToolTip((Translations)newValue);
        return;
      case VersionDSLPackage.FIELD__VALIDATION_ROUTINES:
        getValidationRoutines().clear();
        getValidationRoutines().addAll((Collection<? extends Routine>)newValue);
        return;
      case VersionDSLPackage.FIELD__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends String>)newValue);
        return;
      case VersionDSLPackage.FIELD__MV:
        setMV((Integer)newValue);
        return;
      case VersionDSLPackage.FIELD__SV:
        setSV((Integer)newValue);
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
      case VersionDSLPackage.FIELD__NAME:
        setName(NAME_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__DISPLAY_TYPE:
        setDisplayType(DISPLAY_TYPE_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__INPUT_BEHAVIOUR:
        setInputBehaviour(INPUT_BEHAVIOUR_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__CASE_CONVENTION:
        setCaseConvention(CASE_CONVENTION_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__MAX_LENGTH:
        setMaxLength(MAX_LENGTH_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__ENRICHMENT_LENGTH:
        setEnrichmentLength(ENRICHMENT_LENGTH_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__EXPANSION:
        setExpansion(EXPANSION_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__RIGHT_ADJUST:
        setRightAdjust(RIGHT_ADJUST_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__ENRICHMENT:
        setEnrichment(ENRICHMENT_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__COLUMN:
        setColumn(COLUMN_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__ROW:
        setRow(ROW_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__MANDATORY:
        setMandatory(MANDATORY_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__REKEY_REQUIRED:
        setRekeyRequired(REKEY_REQUIRED_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__HYPERLINK:
        setHyperlink(HYPERLINK_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__HOT_VALIDATE:
        setHotValidate(HOT_VALIDATE_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__HOT_FIELD:
        setHotField(HOT_FIELD_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__WEB_VALIDATE:
        setWebValidate(WEB_VALIDATE_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__SELECTION_ENQUIRY:
        setSelectionEnquiry(SELECTION_ENQUIRY_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__ENQUIRY_PARAMETER:
        setEnquiryParameter(ENQUIRY_PARAMETER_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__POPUP_BEHAVIOUR:
        setPopupBehaviour(POPUP_BEHAVIOUR_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__DEFAULTS:
        getDefaults().clear();
        return;
      case VersionDSLPackage.FIELD__LABEL:
        setLabel((Translations)null);
        return;
      case VersionDSLPackage.FIELD__TOOL_TIP:
        setToolTip((Translations)null);
        return;
      case VersionDSLPackage.FIELD__VALIDATION_ROUTINES:
        getValidationRoutines().clear();
        return;
      case VersionDSLPackage.FIELD__ATTRIBUTES:
        getAttributes().clear();
        return;
      case VersionDSLPackage.FIELD__MV:
        setMV(MV_EDEFAULT);
        return;
      case VersionDSLPackage.FIELD__SV:
        setSV(SV_EDEFAULT);
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
      case VersionDSLPackage.FIELD__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case VersionDSLPackage.FIELD__DISPLAY_TYPE:
        return displayType != DISPLAY_TYPE_EDEFAULT;
      case VersionDSLPackage.FIELD__INPUT_BEHAVIOUR:
        return inputBehaviour != INPUT_BEHAVIOUR_EDEFAULT;
      case VersionDSLPackage.FIELD__CASE_CONVENTION:
        return caseConvention != CASE_CONVENTION_EDEFAULT;
      case VersionDSLPackage.FIELD__MAX_LENGTH:
        return MAX_LENGTH_EDEFAULT == null ? maxLength != null : !MAX_LENGTH_EDEFAULT.equals(maxLength);
      case VersionDSLPackage.FIELD__ENRICHMENT_LENGTH:
        return ENRICHMENT_LENGTH_EDEFAULT == null ? enrichmentLength != null : !ENRICHMENT_LENGTH_EDEFAULT.equals(enrichmentLength);
      case VersionDSLPackage.FIELD__EXPANSION:
        return expansion != EXPANSION_EDEFAULT;
      case VersionDSLPackage.FIELD__RIGHT_ADJUST:
        return rightAdjust != RIGHT_ADJUST_EDEFAULT;
      case VersionDSLPackage.FIELD__ENRICHMENT:
        return enrichment != ENRICHMENT_EDEFAULT;
      case VersionDSLPackage.FIELD__COLUMN:
        return COLUMN_EDEFAULT == null ? column != null : !COLUMN_EDEFAULT.equals(column);
      case VersionDSLPackage.FIELD__ROW:
        return ROW_EDEFAULT == null ? row != null : !ROW_EDEFAULT.equals(row);
      case VersionDSLPackage.FIELD__MANDATORY:
        return mandatory != MANDATORY_EDEFAULT;
      case VersionDSLPackage.FIELD__REKEY_REQUIRED:
        return rekeyRequired != REKEY_REQUIRED_EDEFAULT;
      case VersionDSLPackage.FIELD__HYPERLINK:
        return HYPERLINK_EDEFAULT == null ? hyperlink != null : !HYPERLINK_EDEFAULT.equals(hyperlink);
      case VersionDSLPackage.FIELD__HOT_VALIDATE:
        return hotValidate != HOT_VALIDATE_EDEFAULT;
      case VersionDSLPackage.FIELD__HOT_FIELD:
        return hotField != HOT_FIELD_EDEFAULT;
      case VersionDSLPackage.FIELD__WEB_VALIDATE:
        return webValidate != WEB_VALIDATE_EDEFAULT;
      case VersionDSLPackage.FIELD__SELECTION_ENQUIRY:
        return SELECTION_ENQUIRY_EDEFAULT == null ? selectionEnquiry != null : !SELECTION_ENQUIRY_EDEFAULT.equals(selectionEnquiry);
      case VersionDSLPackage.FIELD__ENQUIRY_PARAMETER:
        return ENQUIRY_PARAMETER_EDEFAULT == null ? enquiryParameter != null : !ENQUIRY_PARAMETER_EDEFAULT.equals(enquiryParameter);
      case VersionDSLPackage.FIELD__POPUP_BEHAVIOUR:
        return popupBehaviour != POPUP_BEHAVIOUR_EDEFAULT;
      case VersionDSLPackage.FIELD__DEFAULTS:
        return defaults != null && !defaults.isEmpty();
      case VersionDSLPackage.FIELD__LABEL:
        return label != null;
      case VersionDSLPackage.FIELD__TOOL_TIP:
        return toolTip != null;
      case VersionDSLPackage.FIELD__VALIDATION_ROUTINES:
        return validationRoutines != null && !validationRoutines.isEmpty();
      case VersionDSLPackage.FIELD__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
      case VersionDSLPackage.FIELD__MV:
        return MV_EDEFAULT == null ? mv != null : !MV_EDEFAULT.equals(mv);
      case VersionDSLPackage.FIELD__SV:
        return SV_EDEFAULT == null ? sv != null : !SV_EDEFAULT.equals(sv);
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
    result.append(" (name: ");
    result.append(name);
    result.append(", displayType: ");
    result.append(displayType);
    result.append(", inputBehaviour: ");
    result.append(inputBehaviour);
    result.append(", caseConvention: ");
    result.append(caseConvention);
    result.append(", maxLength: ");
    result.append(maxLength);
    result.append(", enrichmentLength: ");
    result.append(enrichmentLength);
    result.append(", expansion: ");
    result.append(expansion);
    result.append(", rightAdjust: ");
    result.append(rightAdjust);
    result.append(", enrichment: ");
    result.append(enrichment);
    result.append(", column: ");
    result.append(column);
    result.append(", row: ");
    result.append(row);
    result.append(", mandatory: ");
    result.append(mandatory);
    result.append(", rekeyRequired: ");
    result.append(rekeyRequired);
    result.append(", hyperlink: ");
    result.append(hyperlink);
    result.append(", hotValidate: ");
    result.append(hotValidate);
    result.append(", hotField: ");
    result.append(hotField);
    result.append(", webValidate: ");
    result.append(webValidate);
    result.append(", selectionEnquiry: ");
    result.append(selectionEnquiry);
    result.append(", enquiryParameter: ");
    result.append(enquiryParameter);
    result.append(", popupBehaviour: ");
    result.append(popupBehaviour);
    result.append(", attributes: ");
    result.append(attributes);
    result.append(", MV: ");
    result.append(mv);
    result.append(", SV: ");
    result.append(sv);
    result.append(')');
    return result.toString();
  }

} //FieldImpl
