/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.mdf.ecore.MdfPackage;

import com.odcgroup.t24.version.versionDSL.AssociatedVersionsPresentationPattern;
import com.odcgroup.t24.version.versionDSL.AtRoutine;
import com.odcgroup.t24.version.versionDSL.BusinessDayControl;
import com.odcgroup.t24.version.versionDSL.CaseConvention;
import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction;
import com.odcgroup.t24.version.versionDSL.DealSlipTrigger;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.DisplayType;
import com.odcgroup.t24.version.versionDSL.Expansion;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern;
import com.odcgroup.t24.version.versionDSL.Function;
import com.odcgroup.t24.version.versionDSL.InputBehaviour;
import com.odcgroup.t24.version.versionDSL.JBCRoutine;
import com.odcgroup.t24.version.versionDSL.JavaRoutine;
import com.odcgroup.t24.version.versionDSL.PopupBehaviour;
import com.odcgroup.t24.version.versionDSL.Routine;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.t24.version.versionDSL.YesNo;

import com.odcgroup.translation.translationDsl.TranslationDslPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VersionDSLPackageImpl extends EPackageImpl implements VersionDSLPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass versionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fieldEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass defaultEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass routineEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass atRoutineEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass valueOrAtRoutineEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass jbcRoutineEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass javaRoutineEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass dealSlipEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum yesNoEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum popupBehaviourEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum caseConventionEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum displayTypeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum dealSlipFormatFunctionEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum dealSlipTriggerEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum businessDayControlEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum functionEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum inputBehaviourEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum expansionEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum associatedVersionsPresentationPatternEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum fieldsLayoutPatternEEnum = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see com.odcgroup.t24.version.versionDSL.VersionDSLPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private VersionDSLPackageImpl()
  {
    super(eNS_URI, VersionDSLFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   * 
   * <p>This method is used to initialize {@link VersionDSLPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static VersionDSLPackage init()
  {
    if (isInited) return (VersionDSLPackage)EPackage.Registry.INSTANCE.getEPackage(VersionDSLPackage.eNS_URI);

    // Obtain or create and register package
    VersionDSLPackageImpl theVersionDSLPackage = (VersionDSLPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VersionDSLPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new VersionDSLPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    MdfPackage.eINSTANCE.eClass();
    TranslationDslPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theVersionDSLPackage.createPackageContents();

    // Initialize created meta-data
    theVersionDSLPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theVersionDSLPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(VersionDSLPackage.eNS_URI, theVersionDSLPackage);
    return theVersionDSLPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getVersion()
  {
    return versionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_ForApplication()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_ShortName()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_T24Name()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_MetamodelVersion()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_Group()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_NumberOfAuthorisers()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_Description()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_ExceptionProcessing()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_InterfaceException()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_BusinessDayControl()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_KeySequence()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_OtherCompanyAccess()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_AutoCompanyChange()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_OverrideApproval()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_DealSlipFormats()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_DealSlipTrigger()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_DealSlipStyleSheet()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_RecordsPerPage()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_FieldsPerLine()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_InitialCursorPosition()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_BrowserToolbar()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_LanguageLocale()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_Header1()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_Header2()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(23);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_Header()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(24);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_Footer()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(25);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_NextVersion()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(26);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_NextVersionFunction()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(27);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_NextTransactionReference()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(28);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_AssociatedVersions()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(29);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_IncludeVersionControl()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(30);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_AuthorizationRoutines()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(31);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_AuthorizationRoutinesAfterCommit()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(32);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_InputRoutines()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(33);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_InputRoutinesAfterCommit()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(34);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_KeyValidationRoutines()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(35);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_PreProcessValidationRoutines()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(36);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_WebValidationRoutines()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(37);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_ConfirmVersion()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(38);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_PreviewVersion()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(39);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_ChallengeResponse()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(40);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_Attributes()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(41);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_PublishWebService()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(42);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_WebServiceActivity()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(43);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_WebServiceFunction()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(44);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_WebServiceDescription()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(45);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_WebServiceNames()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(46);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_GenerateIFP()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(47);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_AssociatedVersionsPresentationPattern()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(48);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getVersion_FieldsLayoutPattern()
  {
    return (EAttribute)versionEClass.getEStructuralFeatures().get(49);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getVersion_Fields()
  {
    return (EReference)versionEClass.getEStructuralFeatures().get(50);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getField()
  {
    return fieldEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Name()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_DisplayType()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_InputBehaviour()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_CaseConvention()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_MaxLength()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_EnrichmentLength()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Expansion()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_RightAdjust()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Enrichment()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Column()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Row()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Mandatory()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_RekeyRequired()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Hyperlink()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_HotValidate()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_HotField()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_WebValidate()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_SelectionEnquiry()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_EnquiryParameter()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_PopupBehaviour()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_Defaults()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_Label()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_ToolTip()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_ValidationRoutines()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(23);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Attributes()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(24);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_MV()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(25);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_SV()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(26);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDefault()
  {
    return defaultEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDefault_Mv()
  {
    return (EAttribute)defaultEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDefault_Sv()
  {
    return (EAttribute)defaultEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDefault_DefaultIfOldValueEquals()
  {
    return (EAttribute)defaultEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDefault_DefaultNewValueOrAtRoutine()
  {
    return (EReference)defaultEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRoutine()
  {
    return routineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRoutine_Name()
  {
    return (EAttribute)routineEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAtRoutine()
  {
    return atRoutineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getValueOrAtRoutine()
  {
    return valueOrAtRoutineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getValueOrAtRoutine_Value()
  {
    return (EAttribute)valueOrAtRoutineEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getValueOrAtRoutine_AtRoutine()
  {
    return (EReference)valueOrAtRoutineEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJBCRoutine()
  {
    return jbcRoutineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJavaRoutine()
  {
    return javaRoutineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDealSlip()
  {
    return dealSlipEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDealSlip_Format()
  {
    return (EAttribute)dealSlipEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDealSlip_Function()
  {
    return (EAttribute)dealSlipEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getYesNo()
  {
    return yesNoEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getPopupBehaviour()
  {
    return popupBehaviourEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getCaseConvention()
  {
    return caseConventionEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getDisplayType()
  {
    return displayTypeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getDealSlipFormatFunction()
  {
    return dealSlipFormatFunctionEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getDealSlipTrigger()
  {
    return dealSlipTriggerEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getBusinessDayControl()
  {
    return businessDayControlEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getFunction()
  {
    return functionEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getInputBehaviour()
  {
    return inputBehaviourEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getExpansion()
  {
    return expansionEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getAssociatedVersionsPresentationPattern()
  {
    return associatedVersionsPresentationPatternEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getFieldsLayoutPattern()
  {
    return fieldsLayoutPatternEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionDSLFactory getVersionDSLFactory()
  {
    return (VersionDSLFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    versionEClass = createEClass(VERSION);
    createEReference(versionEClass, VERSION__FOR_APPLICATION);
    createEAttribute(versionEClass, VERSION__SHORT_NAME);
    createEAttribute(versionEClass, VERSION__T24_NAME);
    createEAttribute(versionEClass, VERSION__METAMODEL_VERSION);
    createEAttribute(versionEClass, VERSION__GROUP);
    createEAttribute(versionEClass, VERSION__NUMBER_OF_AUTHORISERS);
    createEReference(versionEClass, VERSION__DESCRIPTION);
    createEAttribute(versionEClass, VERSION__EXCEPTION_PROCESSING);
    createEAttribute(versionEClass, VERSION__INTERFACE_EXCEPTION);
    createEAttribute(versionEClass, VERSION__BUSINESS_DAY_CONTROL);
    createEAttribute(versionEClass, VERSION__KEY_SEQUENCE);
    createEAttribute(versionEClass, VERSION__OTHER_COMPANY_ACCESS);
    createEAttribute(versionEClass, VERSION__AUTO_COMPANY_CHANGE);
    createEAttribute(versionEClass, VERSION__OVERRIDE_APPROVAL);
    createEReference(versionEClass, VERSION__DEAL_SLIP_FORMATS);
    createEAttribute(versionEClass, VERSION__DEAL_SLIP_TRIGGER);
    createEAttribute(versionEClass, VERSION__DEAL_SLIP_STYLE_SHEET);
    createEAttribute(versionEClass, VERSION__RECORDS_PER_PAGE);
    createEAttribute(versionEClass, VERSION__FIELDS_PER_LINE);
    createEAttribute(versionEClass, VERSION__INITIAL_CURSOR_POSITION);
    createEAttribute(versionEClass, VERSION__BROWSER_TOOLBAR);
    createEAttribute(versionEClass, VERSION__LANGUAGE_LOCALE);
    createEReference(versionEClass, VERSION__HEADER1);
    createEReference(versionEClass, VERSION__HEADER2);
    createEReference(versionEClass, VERSION__HEADER);
    createEReference(versionEClass, VERSION__FOOTER);
    createEReference(versionEClass, VERSION__NEXT_VERSION);
    createEAttribute(versionEClass, VERSION__NEXT_VERSION_FUNCTION);
    createEAttribute(versionEClass, VERSION__NEXT_TRANSACTION_REFERENCE);
    createEReference(versionEClass, VERSION__ASSOCIATED_VERSIONS);
    createEAttribute(versionEClass, VERSION__INCLUDE_VERSION_CONTROL);
    createEReference(versionEClass, VERSION__AUTHORIZATION_ROUTINES);
    createEReference(versionEClass, VERSION__AUTHORIZATION_ROUTINES_AFTER_COMMIT);
    createEReference(versionEClass, VERSION__INPUT_ROUTINES);
    createEReference(versionEClass, VERSION__INPUT_ROUTINES_AFTER_COMMIT);
    createEReference(versionEClass, VERSION__KEY_VALIDATION_ROUTINES);
    createEReference(versionEClass, VERSION__PRE_PROCESS_VALIDATION_ROUTINES);
    createEReference(versionEClass, VERSION__WEB_VALIDATION_ROUTINES);
    createEReference(versionEClass, VERSION__CONFIRM_VERSION);
    createEReference(versionEClass, VERSION__PREVIEW_VERSION);
    createEAttribute(versionEClass, VERSION__CHALLENGE_RESPONSE);
    createEAttribute(versionEClass, VERSION__ATTRIBUTES);
    createEAttribute(versionEClass, VERSION__PUBLISH_WEB_SERVICE);
    createEAttribute(versionEClass, VERSION__WEB_SERVICE_ACTIVITY);
    createEAttribute(versionEClass, VERSION__WEB_SERVICE_FUNCTION);
    createEAttribute(versionEClass, VERSION__WEB_SERVICE_DESCRIPTION);
    createEAttribute(versionEClass, VERSION__WEB_SERVICE_NAMES);
    createEAttribute(versionEClass, VERSION__GENERATE_IFP);
    createEAttribute(versionEClass, VERSION__ASSOCIATED_VERSIONS_PRESENTATION_PATTERN);
    createEAttribute(versionEClass, VERSION__FIELDS_LAYOUT_PATTERN);
    createEReference(versionEClass, VERSION__FIELDS);

    fieldEClass = createEClass(FIELD);
    createEAttribute(fieldEClass, FIELD__NAME);
    createEAttribute(fieldEClass, FIELD__DISPLAY_TYPE);
    createEAttribute(fieldEClass, FIELD__INPUT_BEHAVIOUR);
    createEAttribute(fieldEClass, FIELD__CASE_CONVENTION);
    createEAttribute(fieldEClass, FIELD__MAX_LENGTH);
    createEAttribute(fieldEClass, FIELD__ENRICHMENT_LENGTH);
    createEAttribute(fieldEClass, FIELD__EXPANSION);
    createEAttribute(fieldEClass, FIELD__RIGHT_ADJUST);
    createEAttribute(fieldEClass, FIELD__ENRICHMENT);
    createEAttribute(fieldEClass, FIELD__COLUMN);
    createEAttribute(fieldEClass, FIELD__ROW);
    createEAttribute(fieldEClass, FIELD__MANDATORY);
    createEAttribute(fieldEClass, FIELD__REKEY_REQUIRED);
    createEAttribute(fieldEClass, FIELD__HYPERLINK);
    createEAttribute(fieldEClass, FIELD__HOT_VALIDATE);
    createEAttribute(fieldEClass, FIELD__HOT_FIELD);
    createEAttribute(fieldEClass, FIELD__WEB_VALIDATE);
    createEAttribute(fieldEClass, FIELD__SELECTION_ENQUIRY);
    createEAttribute(fieldEClass, FIELD__ENQUIRY_PARAMETER);
    createEAttribute(fieldEClass, FIELD__POPUP_BEHAVIOUR);
    createEReference(fieldEClass, FIELD__DEFAULTS);
    createEReference(fieldEClass, FIELD__LABEL);
    createEReference(fieldEClass, FIELD__TOOL_TIP);
    createEReference(fieldEClass, FIELD__VALIDATION_ROUTINES);
    createEAttribute(fieldEClass, FIELD__ATTRIBUTES);
    createEAttribute(fieldEClass, FIELD__MV);
    createEAttribute(fieldEClass, FIELD__SV);

    defaultEClass = createEClass(DEFAULT);
    createEAttribute(defaultEClass, DEFAULT__MV);
    createEAttribute(defaultEClass, DEFAULT__SV);
    createEAttribute(defaultEClass, DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS);
    createEReference(defaultEClass, DEFAULT__DEFAULT_NEW_VALUE_OR_AT_ROUTINE);

    routineEClass = createEClass(ROUTINE);
    createEAttribute(routineEClass, ROUTINE__NAME);

    atRoutineEClass = createEClass(AT_ROUTINE);

    valueOrAtRoutineEClass = createEClass(VALUE_OR_AT_ROUTINE);
    createEAttribute(valueOrAtRoutineEClass, VALUE_OR_AT_ROUTINE__VALUE);
    createEReference(valueOrAtRoutineEClass, VALUE_OR_AT_ROUTINE__AT_ROUTINE);

    jbcRoutineEClass = createEClass(JBC_ROUTINE);

    javaRoutineEClass = createEClass(JAVA_ROUTINE);

    dealSlipEClass = createEClass(DEAL_SLIP);
    createEAttribute(dealSlipEClass, DEAL_SLIP__FORMAT);
    createEAttribute(dealSlipEClass, DEAL_SLIP__FUNCTION);

    // Create enums
    yesNoEEnum = createEEnum(YES_NO);
    popupBehaviourEEnum = createEEnum(POPUP_BEHAVIOUR);
    caseConventionEEnum = createEEnum(CASE_CONVENTION);
    displayTypeEEnum = createEEnum(DISPLAY_TYPE);
    dealSlipFormatFunctionEEnum = createEEnum(DEAL_SLIP_FORMAT_FUNCTION);
    dealSlipTriggerEEnum = createEEnum(DEAL_SLIP_TRIGGER);
    businessDayControlEEnum = createEEnum(BUSINESS_DAY_CONTROL);
    functionEEnum = createEEnum(FUNCTION);
    inputBehaviourEEnum = createEEnum(INPUT_BEHAVIOUR);
    expansionEEnum = createEEnum(EXPANSION);
    associatedVersionsPresentationPatternEEnum = createEEnum(ASSOCIATED_VERSIONS_PRESENTATION_PATTERN);
    fieldsLayoutPatternEEnum = createEEnum(FIELDS_LAYOUT_PATTERN);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    MdfPackage theMdfPackage = (MdfPackage)EPackage.Registry.INSTANCE.getEPackage(MdfPackage.eNS_URI);
    TranslationDslPackage theTranslationDslPackage = (TranslationDslPackage)EPackage.Registry.INSTANCE.getEPackage(TranslationDslPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    routineEClass.getESuperTypes().add(this.getAtRoutine());
    jbcRoutineEClass.getESuperTypes().add(this.getRoutine());
    javaRoutineEClass.getESuperTypes().add(this.getRoutine());

    // Initialize classes and features; add operations and parameters
    initEClass(versionEClass, Version.class, "Version", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getVersion_ForApplication(), theMdfPackage.getMdfClass(), null, "forApplication", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_ShortName(), ecorePackage.getEString(), "shortName", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_T24Name(), ecorePackage.getEString(), "t24Name", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_MetamodelVersion(), ecorePackage.getEString(), "metamodelVersion", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_Group(), ecorePackage.getEString(), "group", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_NumberOfAuthorisers(), ecorePackage.getEIntegerObject(), "numberOfAuthorisers", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_Description(), theTranslationDslPackage.getTranslations(), null, "description", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_ExceptionProcessing(), ecorePackage.getEIntegerObject(), "exceptionProcessing", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_InterfaceException(), ecorePackage.getEIntegerObject(), "interfaceException", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_BusinessDayControl(), this.getBusinessDayControl(), "businessDayControl", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_KeySequence(), ecorePackage.getEString(), "keySequence", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_OtherCompanyAccess(), this.getYesNo(), "otherCompanyAccess", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_AutoCompanyChange(), this.getYesNo(), "autoCompanyChange", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_OverrideApproval(), this.getYesNo(), "overrideApproval", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_DealSlipFormats(), this.getDealSlip(), null, "dealSlipFormats", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_DealSlipTrigger(), this.getDealSlipTrigger(), "dealSlipTrigger", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_DealSlipStyleSheet(), ecorePackage.getEString(), "dealSlipStyleSheet", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_RecordsPerPage(), ecorePackage.getEString(), "recordsPerPage", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_FieldsPerLine(), ecorePackage.getEString(), "fieldsPerLine", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_InitialCursorPosition(), ecorePackage.getEString(), "initialCursorPosition", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_BrowserToolbar(), ecorePackage.getEString(), "browserToolbar", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_LanguageLocale(), ecorePackage.getEString(), "languageLocale", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_Header1(), theTranslationDslPackage.getTranslations(), null, "header1", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_Header2(), theTranslationDslPackage.getTranslations(), null, "header2", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_Header(), theTranslationDslPackage.getTranslations(), null, "header", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_Footer(), theTranslationDslPackage.getTranslations(), null, "footer", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_NextVersion(), this.getVersion(), null, "nextVersion", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_NextVersionFunction(), this.getFunction(), "nextVersionFunction", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_NextTransactionReference(), ecorePackage.getEString(), "nextTransactionReference", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_AssociatedVersions(), this.getVersion(), null, "associatedVersions", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_IncludeVersionControl(), this.getYesNo(), "includeVersionControl", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_AuthorizationRoutines(), this.getRoutine(), null, "authorizationRoutines", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_AuthorizationRoutinesAfterCommit(), this.getRoutine(), null, "authorizationRoutinesAfterCommit", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_InputRoutines(), this.getRoutine(), null, "inputRoutines", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_InputRoutinesAfterCommit(), this.getRoutine(), null, "inputRoutinesAfterCommit", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_KeyValidationRoutines(), this.getRoutine(), null, "keyValidationRoutines", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_PreProcessValidationRoutines(), this.getRoutine(), null, "preProcessValidationRoutines", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_WebValidationRoutines(), this.getRoutine(), null, "webValidationRoutines", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_ConfirmVersion(), this.getVersion(), null, "confirmVersion", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_PreviewVersion(), this.getVersion(), null, "previewVersion", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_ChallengeResponse(), ecorePackage.getEString(), "challengeResponse", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_Attributes(), ecorePackage.getEString(), "attributes", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_PublishWebService(), this.getYesNo(), "publishWebService", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_WebServiceActivity(), ecorePackage.getEString(), "webServiceActivity", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_WebServiceFunction(), this.getFunction(), "webServiceFunction", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_WebServiceDescription(), ecorePackage.getEString(), "webServiceDescription", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_WebServiceNames(), ecorePackage.getEString(), "webServiceNames", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_GenerateIFP(), this.getYesNo(), "generateIFP", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_AssociatedVersionsPresentationPattern(), this.getAssociatedVersionsPresentationPattern(), "associatedVersionsPresentationPattern", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getVersion_FieldsLayoutPattern(), this.getFieldsLayoutPattern(), "fieldsLayoutPattern", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getVersion_Fields(), this.getField(), null, "fields", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fieldEClass, Field.class, "Field", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getField_Name(), ecorePackage.getEString(), "name", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_DisplayType(), this.getDisplayType(), "displayType", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_InputBehaviour(), this.getInputBehaviour(), "inputBehaviour", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_CaseConvention(), this.getCaseConvention(), "caseConvention", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_MaxLength(), ecorePackage.getEIntegerObject(), "maxLength", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_EnrichmentLength(), ecorePackage.getEIntegerObject(), "enrichmentLength", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Expansion(), this.getExpansion(), "expansion", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_RightAdjust(), this.getYesNo(), "rightAdjust", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Enrichment(), this.getYesNo(), "enrichment", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Column(), ecorePackage.getEIntegerObject(), "column", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Row(), ecorePackage.getEIntegerObject(), "row", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Mandatory(), this.getYesNo(), "mandatory", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_RekeyRequired(), this.getYesNo(), "rekeyRequired", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Hyperlink(), ecorePackage.getEString(), "hyperlink", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_HotValidate(), this.getYesNo(), "hotValidate", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_HotField(), this.getYesNo(), "hotField", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_WebValidate(), this.getYesNo(), "webValidate", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_SelectionEnquiry(), ecorePackage.getEString(), "selectionEnquiry", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_EnquiryParameter(), ecorePackage.getEString(), "enquiryParameter", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_PopupBehaviour(), this.getPopupBehaviour(), "popupBehaviour", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Defaults(), this.getDefault(), null, "defaults", null, 0, -1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_ToolTip(), theTranslationDslPackage.getTranslations(), null, "toolTip", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_ValidationRoutines(), this.getRoutine(), null, "validationRoutines", null, 0, -1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Attributes(), ecorePackage.getEString(), "attributes", null, 0, -1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_MV(), ecorePackage.getEIntegerObject(), "MV", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_SV(), ecorePackage.getEIntegerObject(), "SV", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(defaultEClass, Default.class, "Default", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDefault_Mv(), ecorePackage.getEIntegerObject(), "mv", null, 0, 1, Default.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDefault_Sv(), ecorePackage.getEIntegerObject(), "sv", null, 0, 1, Default.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDefault_DefaultIfOldValueEquals(), ecorePackage.getEString(), "defaultIfOldValueEquals", null, 0, 1, Default.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDefault_DefaultNewValueOrAtRoutine(), this.getValueOrAtRoutine(), null, "defaultNewValueOrAtRoutine", null, 0, 1, Default.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(routineEClass, Routine.class, "Routine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRoutine_Name(), ecorePackage.getEString(), "name", null, 0, 1, Routine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(atRoutineEClass, AtRoutine.class, "AtRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(valueOrAtRoutineEClass, ValueOrAtRoutine.class, "ValueOrAtRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getValueOrAtRoutine_Value(), ecorePackage.getEString(), "value", null, 0, 1, ValueOrAtRoutine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getValueOrAtRoutine_AtRoutine(), this.getAtRoutine(), null, "atRoutine", null, 0, 1, ValueOrAtRoutine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jbcRoutineEClass, JBCRoutine.class, "JBCRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(javaRoutineEClass, JavaRoutine.class, "JavaRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(dealSlipEClass, DealSlip.class, "DealSlip", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDealSlip_Format(), ecorePackage.getEString(), "format", null, 0, 1, DealSlip.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDealSlip_Function(), this.getDealSlipFormatFunction(), "function", null, 0, 1, DealSlip.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(yesNoEEnum, YesNo.class, "YesNo");
    addEEnumLiteral(yesNoEEnum, YesNo.NULL);
    addEEnumLiteral(yesNoEEnum, YesNo.YES);
    addEEnumLiteral(yesNoEEnum, YesNo.NO);

    initEEnum(popupBehaviourEEnum, PopupBehaviour.class, "PopupBehaviour");
    addEEnumLiteral(popupBehaviourEEnum, PopupBehaviour.NONE);
    addEEnumLiteral(popupBehaviourEEnum, PopupBehaviour.CALENDAR);
    addEEnumLiteral(popupBehaviourEEnum, PopupBehaviour.CALCULATOR);
    addEEnumLiteral(popupBehaviourEEnum, PopupBehaviour.RATE_CONTROL);
    addEEnumLiteral(popupBehaviourEEnum, PopupBehaviour.RECURRENCE);

    initEEnum(caseConventionEEnum, CaseConvention.class, "CaseConvention");
    addEEnumLiteral(caseConventionEEnum, CaseConvention.NONE);
    addEEnumLiteral(caseConventionEEnum, CaseConvention.LOWERCASE);
    addEEnumLiteral(caseConventionEEnum, CaseConvention.UPPERCASE);
    addEEnumLiteral(caseConventionEEnum, CaseConvention.PROPERCASE);

    initEEnum(displayTypeEEnum, DisplayType.class, "DisplayType");
    addEEnumLiteral(displayTypeEEnum, DisplayType.NONE);
    addEEnumLiteral(displayTypeEEnum, DisplayType.NO_DISPLAY);
    addEEnumLiteral(displayTypeEEnum, DisplayType.COMBOBOX);
    addEEnumLiteral(displayTypeEEnum, DisplayType.VERTICAL_OPTIONS);
    addEEnumLiteral(displayTypeEEnum, DisplayType.TOGGLE);
    addEEnumLiteral(displayTypeEEnum, DisplayType.DROP_DOWN_NO_INPUT);

    initEEnum(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.class, "DealSlipFormatFunction");
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.NONE);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.I);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.A);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.C);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.R);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.D);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.H);
    addEEnumLiteral(dealSlipFormatFunctionEEnum, DealSlipFormatFunction.FINISH);

    initEEnum(dealSlipTriggerEEnum, DealSlipTrigger.class, "DealSlipTrigger");
    addEEnumLiteral(dealSlipTriggerEEnum, DealSlipTrigger.NONE);
    addEEnumLiteral(dealSlipTriggerEEnum, DealSlipTrigger.OL);
    addEEnumLiteral(dealSlipTriggerEEnum, DealSlipTrigger.RQ);

    initEEnum(businessDayControlEEnum, BusinessDayControl.class, "BusinessDayControl");
    addEEnumLiteral(businessDayControlEEnum, BusinessDayControl.NONE);
    addEEnumLiteral(businessDayControlEEnum, BusinessDayControl.NORMAL);
    addEEnumLiteral(businessDayControlEEnum, BusinessDayControl.RESTRICTED);
    addEEnumLiteral(businessDayControlEEnum, BusinessDayControl.CLOSED);

    initEEnum(functionEEnum, Function.class, "Function");
    addEEnumLiteral(functionEEnum, Function.NONE);
    addEEnumLiteral(functionEEnum, Function.INPUT);
    addEEnumLiteral(functionEEnum, Function.AUTHORISE);
    addEEnumLiteral(functionEEnum, Function.REVERSE);
    addEEnumLiteral(functionEEnum, Function.SEE);
    addEEnumLiteral(functionEEnum, Function.COPY);
    addEEnumLiteral(functionEEnum, Function.DELETE);
    addEEnumLiteral(functionEEnum, Function.HISTORY_RESTORE);
    addEEnumLiteral(functionEEnum, Function.VERIFY);
    addEEnumLiteral(functionEEnum, Function.AUDITOR_REVIEW);

    initEEnum(inputBehaviourEEnum, InputBehaviour.class, "InputBehaviour");
    addEEnumLiteral(inputBehaviourEEnum, InputBehaviour.NONE);
    addEEnumLiteral(inputBehaviourEEnum, InputBehaviour.NO_CHANGE);
    addEEnumLiteral(inputBehaviourEEnum, InputBehaviour.NO_INPUT);

    initEEnum(expansionEEnum, Expansion.class, "Expansion");
    addEEnumLiteral(expansionEEnum, Expansion.NONE);
    addEEnumLiteral(expansionEEnum, Expansion.NO);

    initEEnum(associatedVersionsPresentationPatternEEnum, AssociatedVersionsPresentationPattern.class, "AssociatedVersionsPresentationPattern");
    addEEnumLiteral(associatedVersionsPresentationPatternEEnum, AssociatedVersionsPresentationPattern.NONE);
    addEEnumLiteral(associatedVersionsPresentationPatternEEnum, AssociatedVersionsPresentationPattern.TABS);
    addEEnumLiteral(associatedVersionsPresentationPatternEEnum, AssociatedVersionsPresentationPattern.VERTICAL);
    addEEnumLiteral(associatedVersionsPresentationPatternEEnum, AssociatedVersionsPresentationPattern.ACCORDIONS);

    initEEnum(fieldsLayoutPatternEEnum, FieldsLayoutPattern.class, "FieldsLayoutPattern");
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.NONE);
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.ONE_ROW);
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.ONE_COLUMN);
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.TWO_COLUMN_HORIZONTAL);
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.TWO_COLUMN_VERTICAL);
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.THREE_COLUMN_HORIZONTAL);
    addEEnumLiteral(fieldsLayoutPatternEEnum, FieldsLayoutPattern.THREE_COLUMN_VERTICAL);

    // Create resource
    createResource(eNS_URI);
  }

} //VersionDSLPackageImpl
