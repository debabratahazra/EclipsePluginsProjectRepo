/**
 */
package com.odcgroup.t24.version.versionDSL.impl;

import com.odcgroup.t24.version.versionDSL.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class VersionDSLFactoryImpl extends EFactoryImpl implements VersionDSLFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static VersionDSLFactory init()
  {
    try
    {
      VersionDSLFactory theVersionDSLFactory = (VersionDSLFactory)EPackage.Registry.INSTANCE.getEFactory(VersionDSLPackage.eNS_URI);
      if (theVersionDSLFactory != null)
      {
        return theVersionDSLFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new VersionDSLFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionDSLFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case VersionDSLPackage.VERSION: return createVersion();
      case VersionDSLPackage.FIELD: return createField();
      case VersionDSLPackage.DEFAULT: return createDefault();
      case VersionDSLPackage.ROUTINE: return createRoutine();
      case VersionDSLPackage.AT_ROUTINE: return createAtRoutine();
      case VersionDSLPackage.VALUE_OR_AT_ROUTINE: return createValueOrAtRoutine();
      case VersionDSLPackage.JBC_ROUTINE: return createJBCRoutine();
      case VersionDSLPackage.JAVA_ROUTINE: return createJavaRoutine();
      case VersionDSLPackage.DEAL_SLIP: return createDealSlip();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object createFromString(EDataType eDataType, String initialValue)
  {
    switch (eDataType.getClassifierID())
    {
      case VersionDSLPackage.YES_NO:
        return createYesNoFromString(eDataType, initialValue);
      case VersionDSLPackage.POPUP_BEHAVIOUR:
        return createPopupBehaviourFromString(eDataType, initialValue);
      case VersionDSLPackage.CASE_CONVENTION:
        return createCaseConventionFromString(eDataType, initialValue);
      case VersionDSLPackage.DISPLAY_TYPE:
        return createDisplayTypeFromString(eDataType, initialValue);
      case VersionDSLPackage.DEAL_SLIP_FORMAT_FUNCTION:
        return createDealSlipFormatFunctionFromString(eDataType, initialValue);
      case VersionDSLPackage.DEAL_SLIP_TRIGGER:
        return createDealSlipTriggerFromString(eDataType, initialValue);
      case VersionDSLPackage.BUSINESS_DAY_CONTROL:
        return createBusinessDayControlFromString(eDataType, initialValue);
      case VersionDSLPackage.FUNCTION:
        return createFunctionFromString(eDataType, initialValue);
      case VersionDSLPackage.INPUT_BEHAVIOUR:
        return createInputBehaviourFromString(eDataType, initialValue);
      case VersionDSLPackage.EXPANSION:
        return createExpansionFromString(eDataType, initialValue);
      case VersionDSLPackage.ASSOCIATED_VERSIONS_PRESENTATION_PATTERN:
        return createAssociatedVersionsPresentationPatternFromString(eDataType, initialValue);
      case VersionDSLPackage.FIELDS_LAYOUT_PATTERN:
        return createFieldsLayoutPatternFromString(eDataType, initialValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String convertToString(EDataType eDataType, Object instanceValue)
  {
    switch (eDataType.getClassifierID())
    {
      case VersionDSLPackage.YES_NO:
        return convertYesNoToString(eDataType, instanceValue);
      case VersionDSLPackage.POPUP_BEHAVIOUR:
        return convertPopupBehaviourToString(eDataType, instanceValue);
      case VersionDSLPackage.CASE_CONVENTION:
        return convertCaseConventionToString(eDataType, instanceValue);
      case VersionDSLPackage.DISPLAY_TYPE:
        return convertDisplayTypeToString(eDataType, instanceValue);
      case VersionDSLPackage.DEAL_SLIP_FORMAT_FUNCTION:
        return convertDealSlipFormatFunctionToString(eDataType, instanceValue);
      case VersionDSLPackage.DEAL_SLIP_TRIGGER:
        return convertDealSlipTriggerToString(eDataType, instanceValue);
      case VersionDSLPackage.BUSINESS_DAY_CONTROL:
        return convertBusinessDayControlToString(eDataType, instanceValue);
      case VersionDSLPackage.FUNCTION:
        return convertFunctionToString(eDataType, instanceValue);
      case VersionDSLPackage.INPUT_BEHAVIOUR:
        return convertInputBehaviourToString(eDataType, instanceValue);
      case VersionDSLPackage.EXPANSION:
        return convertExpansionToString(eDataType, instanceValue);
      case VersionDSLPackage.ASSOCIATED_VERSIONS_PRESENTATION_PATTERN:
        return convertAssociatedVersionsPresentationPatternToString(eDataType, instanceValue);
      case VersionDSLPackage.FIELDS_LAYOUT_PATTERN:
        return convertFieldsLayoutPatternToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Version createVersion()
  {
    VersionImpl version = new VersionImpl();
    return version;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Field createField()
  {
    FieldImpl field = new FieldImpl();
    return field;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Default createDefault()
  {
    DefaultImpl default_ = new DefaultImpl();
    return default_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Routine createRoutine()
  {
    RoutineImpl routine = new RoutineImpl();
    return routine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AtRoutine createAtRoutine()
  {
    AtRoutineImpl atRoutine = new AtRoutineImpl();
    return atRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ValueOrAtRoutine createValueOrAtRoutine()
  {
    ValueOrAtRoutineImpl valueOrAtRoutine = new ValueOrAtRoutineImpl();
    return valueOrAtRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JBCRoutine createJBCRoutine()
  {
    JBCRoutineImpl jbcRoutine = new JBCRoutineImpl();
    return jbcRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JavaRoutine createJavaRoutine()
  {
    JavaRoutineImpl javaRoutine = new JavaRoutineImpl();
    return javaRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DealSlip createDealSlip()
  {
    DealSlipImpl dealSlip = new DealSlipImpl();
    return dealSlip;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public YesNo createYesNoFromString(EDataType eDataType, String initialValue)
  {
    YesNo result = YesNo.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertYesNoToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PopupBehaviour createPopupBehaviourFromString(EDataType eDataType, String initialValue)
  {
    PopupBehaviour result = PopupBehaviour.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertPopupBehaviourToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CaseConvention createCaseConventionFromString(EDataType eDataType, String initialValue)
  {
    CaseConvention result = CaseConvention.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertCaseConventionToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DisplayType createDisplayTypeFromString(EDataType eDataType, String initialValue)
  {
    DisplayType result = DisplayType.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDisplayTypeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DealSlipFormatFunction createDealSlipFormatFunctionFromString(EDataType eDataType, String initialValue)
  {
    DealSlipFormatFunction result = DealSlipFormatFunction.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDealSlipFormatFunctionToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DealSlipTrigger createDealSlipTriggerFromString(EDataType eDataType, String initialValue)
  {
    DealSlipTrigger result = DealSlipTrigger.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDealSlipTriggerToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BusinessDayControl createBusinessDayControlFromString(EDataType eDataType, String initialValue)
  {
    BusinessDayControl result = BusinessDayControl.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertBusinessDayControlToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Function createFunctionFromString(EDataType eDataType, String initialValue)
  {
    Function result = Function.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFunctionToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public InputBehaviour createInputBehaviourFromString(EDataType eDataType, String initialValue)
  {
    InputBehaviour result = InputBehaviour.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertInputBehaviourToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expansion createExpansionFromString(EDataType eDataType, String initialValue)
  {
    Expansion result = Expansion.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertExpansionToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AssociatedVersionsPresentationPattern createAssociatedVersionsPresentationPatternFromString(EDataType eDataType, String initialValue)
  {
    AssociatedVersionsPresentationPattern result = AssociatedVersionsPresentationPattern.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertAssociatedVersionsPresentationPatternToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldsLayoutPattern createFieldsLayoutPatternFromString(EDataType eDataType, String initialValue)
  {
    FieldsLayoutPattern result = FieldsLayoutPattern.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFieldsLayoutPatternToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public VersionDSLPackage getVersionDSLPackage()
  {
    return (VersionDSLPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static VersionDSLPackage getPackage()
  {
    return VersionDSLPackage.eINSTANCE;
  }

} //VersionDSLFactoryImpl
