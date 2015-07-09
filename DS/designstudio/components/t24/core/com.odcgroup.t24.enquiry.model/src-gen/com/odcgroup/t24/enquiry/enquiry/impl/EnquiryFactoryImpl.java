/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.*;

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
public class EnquiryFactoryImpl extends EFactoryImpl implements EnquiryFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static EnquiryFactory init()
  {
    try
    {
      EnquiryFactory theEnquiryFactory = (EnquiryFactory)EPackage.Registry.INSTANCE.getEFactory(EnquiryPackage.eNS_URI);
      if (theEnquiryFactory != null)
      {
        return theEnquiryFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new EnquiryFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryFactoryImpl()
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
      case EnquiryPackage.ENQUIRY: return createEnquiry();
      case EnquiryPackage.COMPANIES: return createCompanies();
      case EnquiryPackage.ENQUIRY_HEADER: return createEnquiryHeader();
      case EnquiryPackage.TARGET: return createTarget();
      case EnquiryPackage.TARGET_MAPPING: return createTargetMapping();
      case EnquiryPackage.PARAMETERS: return createParameters();
      case EnquiryPackage.DRILL_DOWN: return createDrillDown();
      case EnquiryPackage.DRILL_DOWN_TYPE: return createDrillDownType();
      case EnquiryPackage.DRILL_DOWN_STRING_TYPE: return createDrillDownStringType();
      case EnquiryPackage.APPLICATION_TYPE: return createApplicationType();
      case EnquiryPackage.SCREEN_TYPE: return createScreenType();
      case EnquiryPackage.ENQUIRY_TYPE: return createEnquiryType();
      case EnquiryPackage.FROM_FIELD_TYPE: return createFromFieldType();
      case EnquiryPackage.COMPOSITE_SCREEN_TYPE: return createCompositeScreenType();
      case EnquiryPackage.TABBED_SCREEN_TYPE: return createTabbedScreenType();
      case EnquiryPackage.VIEW_TYPE: return createViewType();
      case EnquiryPackage.QUIT_SEE_TYPE: return createQuitSEEType();
      case EnquiryPackage.BLANK_TYPE: return createBlankType();
      case EnquiryPackage.PW_PROCESS_TYPE: return createPWProcessType();
      case EnquiryPackage.DOWNLOAD_TYPE: return createDownloadType();
      case EnquiryPackage.RUN_TYPE: return createRunType();
      case EnquiryPackage.UTIL_TYPE: return createUtilType();
      case EnquiryPackage.JAVA_SCRIPT_TYPE: return createJavaScriptType();
      case EnquiryPackage.SHOULD_BE_CHANGED_TYPE: return createShouldBeChangedType();
      case EnquiryPackage.DRILL_DOWN_OPTION: return createDrillDownOption();
      case EnquiryPackage.COMPOSITE_SCREEN_OPTION: return createCompositeScreenOption();
      case EnquiryPackage.TAB_OPTION: return createTabOption();
      case EnquiryPackage.VIEW_OPTION: return createViewOption();
      case EnquiryPackage.QUIT_SEE_OPTION: return createQuitSEEOption();
      case EnquiryPackage.REFERENCE: return createReference();
      case EnquiryPackage.PARAMETER: return createParameter();
      case EnquiryPackage.SELECTION_CRITERIA: return createSelectionCriteria();
      case EnquiryPackage.SECURITY: return createSecurity();
      case EnquiryPackage.GRAPH: return createGraph();
      case EnquiryPackage.AXIS: return createAxis();
      case EnquiryPackage.DIMENSION: return createDimension();
      case EnquiryPackage.LABEL: return createLabel();
      case EnquiryPackage.POSITION: return createPosition();
      case EnquiryPackage.LEGEND: return createLegend();
      case EnquiryPackage.MARGINS: return createMargins();
      case EnquiryPackage.SCALE: return createScale();
      case EnquiryPackage.ROUTINE: return createRoutine();
      case EnquiryPackage.JBC_ROUTINE: return createJBCRoutine();
      case EnquiryPackage.JAVA_ROUTINE: return createJavaRoutine();
      case EnquiryPackage.FIXED_SELECTION: return createFixedSelection();
      case EnquiryPackage.FIXED_SORT: return createFixedSort();
      case EnquiryPackage.SELECTION_EXPRESSION: return createSelectionExpression();
      case EnquiryPackage.SELECTION: return createSelection();
      case EnquiryPackage.FILE_VERSION: return createFileVersion();
      case EnquiryPackage.OPERATION: return createOperation();
      case EnquiryPackage.BREAK_OPERATION: return createBreakOperation();
      case EnquiryPackage.BREAK_ON_CHANGE_OPERATION: return createBreakOnChangeOperation();
      case EnquiryPackage.BREAK_LINE_OPERATION: return createBreakLineOperation();
      case EnquiryPackage.CALC_OPERATION: return createCalcOperation();
      case EnquiryPackage.CONSTANT_OPERATION: return createConstantOperation();
      case EnquiryPackage.LABEL_OPERATION: return createLabelOperation();
      case EnquiryPackage.DATE_OPERATION: return createDateOperation();
      case EnquiryPackage.DECISION_OPERATION: return createDecisionOperation();
      case EnquiryPackage.DESCRIPTOR_OPERATION: return createDescriptorOperation();
      case EnquiryPackage.TODAY_OPERATION: return createTodayOperation();
      case EnquiryPackage.LWD_OPERATION: return createLWDOperation();
      case EnquiryPackage.NWD_OPERATION: return createNWDOperation();
      case EnquiryPackage.FIELD_OPERATION: return createFieldOperation();
      case EnquiryPackage.APPLICATION_FIELD_NAME_OPERATION: return createApplicationFieldNameOperation();
      case EnquiryPackage.FIELD_NUMBER_OPERATION: return createFieldNumberOperation();
      case EnquiryPackage.FIELD_EXTRACT_OPERATION: return createFieldExtractOperation();
      case EnquiryPackage.SELECTION_OPERATION: return createSelectionOperation();
      case EnquiryPackage.SYSTEM_OPERATION: return createSystemOperation();
      case EnquiryPackage.USER_OPERATION: return createUserOperation();
      case EnquiryPackage.COMPANY_OPERATION: return createCompanyOperation();
      case EnquiryPackage.LANGUAGE_OPERATION: return createLanguageOperation();
      case EnquiryPackage.LOCAL_CURRENCY_OPERATION: return createLocalCurrencyOperation();
      case EnquiryPackage.TOTAL_OPERATION: return createTotalOperation();
      case EnquiryPackage.CONVERSION: return createConversion();
      case EnquiryPackage.EXTRACT_CONVERSION: return createExtractConversion();
      case EnquiryPackage.DECRYPT_CONVERSION: return createDecryptConversion();
      case EnquiryPackage.REPLACE_CONVERSION: return createReplaceConversion();
      case EnquiryPackage.CONVERT_CONVERSION: return createConvertConversion();
      case EnquiryPackage.VALUE_CONVERSION: return createValueConversion();
      case EnquiryPackage.JULIAN_CONVERSION: return createJulianConversion();
      case EnquiryPackage.BASIC_CONVERSION: return createBasicConversion();
      case EnquiryPackage.BASIC_OCONVERSION: return createBasicOConversion();
      case EnquiryPackage.BASIC_ICONVERSION: return createBasicIConversion();
      case EnquiryPackage.GET_FROM_CONVERSION: return createGetFromConversion();
      case EnquiryPackage.RATE_CONVERSION: return createRateConversion();
      case EnquiryPackage.CALC_FIXED_RATE_CONVERSION: return createCalcFixedRateConversion();
      case EnquiryPackage.GET_FIXED_RATE_CONVERSION: return createGetFixedRateConversion();
      case EnquiryPackage.GET_FIXED_CURRENCY_CONVERSION: return createGetFixedCurrencyConversion();
      case EnquiryPackage.ABS_CONVERSION: return createAbsConversion();
      case EnquiryPackage.MATCH_FIELD: return createMatchField();
      case EnquiryPackage.CALL_ROUTINE: return createCallRoutine();
      case EnquiryPackage.REPEAT_CONVERSION: return createRepeatConversion();
      case EnquiryPackage.REPEAT_ON_NULL_CONVERSION: return createRepeatOnNullConversion();
      case EnquiryPackage.REPEAT_EVERY_CONVERSION: return createRepeatEveryConversion();
      case EnquiryPackage.REPEAT_SUB_CONVERSION: return createRepeatSubConversion();
      case EnquiryPackage.FIELD: return createField();
      case EnquiryPackage.BREAK_CONDITION: return createBreakCondition();
      case EnquiryPackage.FIELD_POSITION: return createFieldPosition();
      case EnquiryPackage.FORMAT: return createFormat();
      case EnquiryPackage.TOOL: return createTool();
      case EnquiryPackage.WEB_SERVICE: return createWebService();
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
      case EnquiryPackage.ENQUIRY_MODE:
        return createEnquiryModeFromString(eDataType, initialValue);
      case EnquiryPackage.ALIGNMENT_KIND:
        return createAlignmentKindFromString(eDataType, initialValue);
      case EnquiryPackage.BREAK_KIND:
        return createBreakKindFromString(eDataType, initialValue);
      case EnquiryPackage.CURRENCY_PATTERN:
        return createCurrencyPatternFromString(eDataType, initialValue);
      case EnquiryPackage.DECISION_OPERAND:
        return createDecisionOperandFromString(eDataType, initialValue);
      case EnquiryPackage.DISPLAY_SECTION_KIND:
        return createDisplaySectionKindFromString(eDataType, initialValue);
      case EnquiryPackage.FIELD_FORMAT:
        return createFieldFormatFromString(eDataType, initialValue);
      case EnquiryPackage.FUNCTION_KIND:
        return createFunctionKindFromString(eDataType, initialValue);
      case EnquiryPackage.SELECTION_OPERATOR:
        return createSelectionOperatorFromString(eDataType, initialValue);
      case EnquiryPackage.CRITERIA_OPERATOR:
        return createCriteriaOperatorFromString(eDataType, initialValue);
      case EnquiryPackage.PROCESSING_MODE:
        return createProcessingModeFromString(eDataType, initialValue);
      case EnquiryPackage.SORT_ORDER:
        return createSortOrderFromString(eDataType, initialValue);
      case EnquiryPackage.AND_OR:
        return createAndOrFromString(eDataType, initialValue);
      case EnquiryPackage.FILE_VERSION_OPTION:
        return createFileVersionOptionFromString(eDataType, initialValue);
      case EnquiryPackage.ESCAPE_SEQUENCE:
        return createEscapeSequenceFromString(eDataType, initialValue);
      case EnquiryPackage.ORIENTATION:
        return createOrientationFromString(eDataType, initialValue);
      case EnquiryPackage.SERVER_MODE:
        return createServerModeFromString(eDataType, initialValue);
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
      case EnquiryPackage.ENQUIRY_MODE:
        return convertEnquiryModeToString(eDataType, instanceValue);
      case EnquiryPackage.ALIGNMENT_KIND:
        return convertAlignmentKindToString(eDataType, instanceValue);
      case EnquiryPackage.BREAK_KIND:
        return convertBreakKindToString(eDataType, instanceValue);
      case EnquiryPackage.CURRENCY_PATTERN:
        return convertCurrencyPatternToString(eDataType, instanceValue);
      case EnquiryPackage.DECISION_OPERAND:
        return convertDecisionOperandToString(eDataType, instanceValue);
      case EnquiryPackage.DISPLAY_SECTION_KIND:
        return convertDisplaySectionKindToString(eDataType, instanceValue);
      case EnquiryPackage.FIELD_FORMAT:
        return convertFieldFormatToString(eDataType, instanceValue);
      case EnquiryPackage.FUNCTION_KIND:
        return convertFunctionKindToString(eDataType, instanceValue);
      case EnquiryPackage.SELECTION_OPERATOR:
        return convertSelectionOperatorToString(eDataType, instanceValue);
      case EnquiryPackage.CRITERIA_OPERATOR:
        return convertCriteriaOperatorToString(eDataType, instanceValue);
      case EnquiryPackage.PROCESSING_MODE:
        return convertProcessingModeToString(eDataType, instanceValue);
      case EnquiryPackage.SORT_ORDER:
        return convertSortOrderToString(eDataType, instanceValue);
      case EnquiryPackage.AND_OR:
        return convertAndOrToString(eDataType, instanceValue);
      case EnquiryPackage.FILE_VERSION_OPTION:
        return convertFileVersionOptionToString(eDataType, instanceValue);
      case EnquiryPackage.ESCAPE_SEQUENCE:
        return convertEscapeSequenceToString(eDataType, instanceValue);
      case EnquiryPackage.ORIENTATION:
        return convertOrientationToString(eDataType, instanceValue);
      case EnquiryPackage.SERVER_MODE:
        return convertServerModeToString(eDataType, instanceValue);
      default:
        throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Enquiry createEnquiry()
  {
    EnquiryImpl enquiry = new EnquiryImpl();
    return enquiry;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Companies createCompanies()
  {
    CompaniesImpl companies = new CompaniesImpl();
    return companies;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryHeader createEnquiryHeader()
  {
    EnquiryHeaderImpl enquiryHeader = new EnquiryHeaderImpl();
    return enquiryHeader;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Target createTarget()
  {
    TargetImpl target = new TargetImpl();
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TargetMapping createTargetMapping()
  {
    TargetMappingImpl targetMapping = new TargetMappingImpl();
    return targetMapping;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameters createParameters()
  {
    ParametersImpl parameters = new ParametersImpl();
    return parameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DrillDown createDrillDown()
  {
    DrillDownImpl drillDown = new DrillDownImpl();
    return drillDown;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DrillDownType createDrillDownType()
  {
    DrillDownTypeImpl drillDownType = new DrillDownTypeImpl();
    return drillDownType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DrillDownStringType createDrillDownStringType()
  {
    DrillDownStringTypeImpl drillDownStringType = new DrillDownStringTypeImpl();
    return drillDownStringType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ApplicationType createApplicationType()
  {
    ApplicationTypeImpl applicationType = new ApplicationTypeImpl();
    return applicationType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScreenType createScreenType()
  {
    ScreenTypeImpl screenType = new ScreenTypeImpl();
    return screenType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryType createEnquiryType()
  {
    EnquiryTypeImpl enquiryType = new EnquiryTypeImpl();
    return enquiryType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FromFieldType createFromFieldType()
  {
    FromFieldTypeImpl fromFieldType = new FromFieldTypeImpl();
    return fromFieldType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompositeScreenType createCompositeScreenType()
  {
    CompositeScreenTypeImpl compositeScreenType = new CompositeScreenTypeImpl();
    return compositeScreenType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TabbedScreenType createTabbedScreenType()
  {
    TabbedScreenTypeImpl tabbedScreenType = new TabbedScreenTypeImpl();
    return tabbedScreenType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ViewType createViewType()
  {
    ViewTypeImpl viewType = new ViewTypeImpl();
    return viewType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QuitSEEType createQuitSEEType()
  {
    QuitSEETypeImpl quitSEEType = new QuitSEETypeImpl();
    return quitSEEType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BlankType createBlankType()
  {
    BlankTypeImpl blankType = new BlankTypeImpl();
    return blankType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public PWProcessType createPWProcessType()
  {
    PWProcessTypeImpl pwProcessType = new PWProcessTypeImpl();
    return pwProcessType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DownloadType createDownloadType()
  {
    DownloadTypeImpl downloadType = new DownloadTypeImpl();
    return downloadType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RunType createRunType()
  {
    RunTypeImpl runType = new RunTypeImpl();
    return runType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UtilType createUtilType()
  {
    UtilTypeImpl utilType = new UtilTypeImpl();
    return utilType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JavaScriptType createJavaScriptType()
  {
    JavaScriptTypeImpl javaScriptType = new JavaScriptTypeImpl();
    return javaScriptType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ShouldBeChangedType createShouldBeChangedType()
  {
    ShouldBeChangedTypeImpl shouldBeChangedType = new ShouldBeChangedTypeImpl();
    return shouldBeChangedType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DrillDownOption createDrillDownOption()
  {
    DrillDownOptionImpl drillDownOption = new DrillDownOptionImpl();
    return drillDownOption;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompositeScreenOption createCompositeScreenOption()
  {
    CompositeScreenOptionImpl compositeScreenOption = new CompositeScreenOptionImpl();
    return compositeScreenOption;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TabOption createTabOption()
  {
    TabOptionImpl tabOption = new TabOptionImpl();
    return tabOption;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ViewOption createViewOption()
  {
    ViewOptionImpl viewOption = new ViewOptionImpl();
    return viewOption;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public QuitSEEOption createQuitSEEOption()
  {
    QuitSEEOptionImpl quitSEEOption = new QuitSEEOptionImpl();
    return quitSEEOption;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Reference createReference()
  {
    ReferenceImpl reference = new ReferenceImpl();
    return reference;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SelectionCriteria createSelectionCriteria()
  {
    SelectionCriteriaImpl selectionCriteria = new SelectionCriteriaImpl();
    return selectionCriteria;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Security createSecurity()
  {
    SecurityImpl security = new SecurityImpl();
    return security;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Graph createGraph()
  {
    GraphImpl graph = new GraphImpl();
    return graph;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Axis createAxis()
  {
    AxisImpl axis = new AxisImpl();
    return axis;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Dimension createDimension()
  {
    DimensionImpl dimension = new DimensionImpl();
    return dimension;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Label createLabel()
  {
    LabelImpl label = new LabelImpl();
    return label;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Position createPosition()
  {
    PositionImpl position = new PositionImpl();
    return position;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Legend createLegend()
  {
    LegendImpl legend = new LegendImpl();
    return legend;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Margins createMargins()
  {
    MarginsImpl margins = new MarginsImpl();
    return margins;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Scale createScale()
  {
    ScaleImpl scale = new ScaleImpl();
    return scale;
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
  public FixedSelection createFixedSelection()
  {
    FixedSelectionImpl fixedSelection = new FixedSelectionImpl();
    return fixedSelection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FixedSort createFixedSort()
  {
    FixedSortImpl fixedSort = new FixedSortImpl();
    return fixedSort;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SelectionExpression createSelectionExpression()
  {
    SelectionExpressionImpl selectionExpression = new SelectionExpressionImpl();
    return selectionExpression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Selection createSelection()
  {
    SelectionImpl selection = new SelectionImpl();
    return selection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FileVersion createFileVersion()
  {
    FileVersionImpl fileVersion = new FileVersionImpl();
    return fileVersion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Operation createOperation()
  {
    OperationImpl operation = new OperationImpl();
    return operation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BreakOperation createBreakOperation()
  {
    BreakOperationImpl breakOperation = new BreakOperationImpl();
    return breakOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BreakOnChangeOperation createBreakOnChangeOperation()
  {
    BreakOnChangeOperationImpl breakOnChangeOperation = new BreakOnChangeOperationImpl();
    return breakOnChangeOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BreakLineOperation createBreakLineOperation()
  {
    BreakLineOperationImpl breakLineOperation = new BreakLineOperationImpl();
    return breakLineOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CalcOperation createCalcOperation()
  {
    CalcOperationImpl calcOperation = new CalcOperationImpl();
    return calcOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConstantOperation createConstantOperation()
  {
    ConstantOperationImpl constantOperation = new ConstantOperationImpl();
    return constantOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LabelOperation createLabelOperation()
  {
    LabelOperationImpl labelOperation = new LabelOperationImpl();
    return labelOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DateOperation createDateOperation()
  {
    DateOperationImpl dateOperation = new DateOperationImpl();
    return dateOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DecisionOperation createDecisionOperation()
  {
    DecisionOperationImpl decisionOperation = new DecisionOperationImpl();
    return decisionOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DescriptorOperation createDescriptorOperation()
  {
    DescriptorOperationImpl descriptorOperation = new DescriptorOperationImpl();
    return descriptorOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TodayOperation createTodayOperation()
  {
    TodayOperationImpl todayOperation = new TodayOperationImpl();
    return todayOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LWDOperation createLWDOperation()
  {
    LWDOperationImpl lwdOperation = new LWDOperationImpl();
    return lwdOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NWDOperation createNWDOperation()
  {
    NWDOperationImpl nwdOperation = new NWDOperationImpl();
    return nwdOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldOperation createFieldOperation()
  {
    FieldOperationImpl fieldOperation = new FieldOperationImpl();
    return fieldOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ApplicationFieldNameOperation createApplicationFieldNameOperation()
  {
    ApplicationFieldNameOperationImpl applicationFieldNameOperation = new ApplicationFieldNameOperationImpl();
    return applicationFieldNameOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldNumberOperation createFieldNumberOperation()
  {
    FieldNumberOperationImpl fieldNumberOperation = new FieldNumberOperationImpl();
    return fieldNumberOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldExtractOperation createFieldExtractOperation()
  {
    FieldExtractOperationImpl fieldExtractOperation = new FieldExtractOperationImpl();
    return fieldExtractOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SelectionOperation createSelectionOperation()
  {
    SelectionOperationImpl selectionOperation = new SelectionOperationImpl();
    return selectionOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SystemOperation createSystemOperation()
  {
    SystemOperationImpl systemOperation = new SystemOperationImpl();
    return systemOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public UserOperation createUserOperation()
  {
    UserOperationImpl userOperation = new UserOperationImpl();
    return userOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompanyOperation createCompanyOperation()
  {
    CompanyOperationImpl companyOperation = new CompanyOperationImpl();
    return companyOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LanguageOperation createLanguageOperation()
  {
    LanguageOperationImpl languageOperation = new LanguageOperationImpl();
    return languageOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LocalCurrencyOperation createLocalCurrencyOperation()
  {
    LocalCurrencyOperationImpl localCurrencyOperation = new LocalCurrencyOperationImpl();
    return localCurrencyOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TotalOperation createTotalOperation()
  {
    TotalOperationImpl totalOperation = new TotalOperationImpl();
    return totalOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Conversion createConversion()
  {
    ConversionImpl conversion = new ConversionImpl();
    return conversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExtractConversion createExtractConversion()
  {
    ExtractConversionImpl extractConversion = new ExtractConversionImpl();
    return extractConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DecryptConversion createDecryptConversion()
  {
    DecryptConversionImpl decryptConversion = new DecryptConversionImpl();
    return decryptConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ReplaceConversion createReplaceConversion()
  {
    ReplaceConversionImpl replaceConversion = new ReplaceConversionImpl();
    return replaceConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ConvertConversion createConvertConversion()
  {
    ConvertConversionImpl convertConversion = new ConvertConversionImpl();
    return convertConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ValueConversion createValueConversion()
  {
    ValueConversionImpl valueConversion = new ValueConversionImpl();
    return valueConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public JulianConversion createJulianConversion()
  {
    JulianConversionImpl julianConversion = new JulianConversionImpl();
    return julianConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BasicConversion createBasicConversion()
  {
    BasicConversionImpl basicConversion = new BasicConversionImpl();
    return basicConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BasicOConversion createBasicOConversion()
  {
    BasicOConversionImpl basicOConversion = new BasicOConversionImpl();
    return basicOConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BasicIConversion createBasicIConversion()
  {
    BasicIConversionImpl basicIConversion = new BasicIConversionImpl();
    return basicIConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GetFromConversion createGetFromConversion()
  {
    GetFromConversionImpl getFromConversion = new GetFromConversionImpl();
    return getFromConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RateConversion createRateConversion()
  {
    RateConversionImpl rateConversion = new RateConversionImpl();
    return rateConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CalcFixedRateConversion createCalcFixedRateConversion()
  {
    CalcFixedRateConversionImpl calcFixedRateConversion = new CalcFixedRateConversionImpl();
    return calcFixedRateConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GetFixedRateConversion createGetFixedRateConversion()
  {
    GetFixedRateConversionImpl getFixedRateConversion = new GetFixedRateConversionImpl();
    return getFixedRateConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GetFixedCurrencyConversion createGetFixedCurrencyConversion()
  {
    GetFixedCurrencyConversionImpl getFixedCurrencyConversion = new GetFixedCurrencyConversionImpl();
    return getFixedCurrencyConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AbsConversion createAbsConversion()
  {
    AbsConversionImpl absConversion = new AbsConversionImpl();
    return absConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public MatchField createMatchField()
  {
    MatchFieldImpl matchField = new MatchFieldImpl();
    return matchField;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CallRoutine createCallRoutine()
  {
    CallRoutineImpl callRoutine = new CallRoutineImpl();
    return callRoutine;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RepeatConversion createRepeatConversion()
  {
    RepeatConversionImpl repeatConversion = new RepeatConversionImpl();
    return repeatConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RepeatOnNullConversion createRepeatOnNullConversion()
  {
    RepeatOnNullConversionImpl repeatOnNullConversion = new RepeatOnNullConversionImpl();
    return repeatOnNullConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RepeatEveryConversion createRepeatEveryConversion()
  {
    RepeatEveryConversionImpl repeatEveryConversion = new RepeatEveryConversionImpl();
    return repeatEveryConversion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public RepeatSubConversion createRepeatSubConversion()
  {
    RepeatSubConversionImpl repeatSubConversion = new RepeatSubConversionImpl();
    return repeatSubConversion;
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
  public BreakCondition createBreakCondition()
  {
    BreakConditionImpl breakCondition = new BreakConditionImpl();
    return breakCondition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldPosition createFieldPosition()
  {
    FieldPositionImpl fieldPosition = new FieldPositionImpl();
    return fieldPosition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Format createFormat()
  {
    FormatImpl format = new FormatImpl();
    return format;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Tool createTool()
  {
    ToolImpl tool = new ToolImpl();
    return tool;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public WebService createWebService()
  {
    WebServiceImpl webService = new WebServiceImpl();
    return webService;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryMode createEnquiryModeFromString(EDataType eDataType, String initialValue)
  {
    EnquiryMode result = EnquiryMode.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertEnquiryModeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AlignmentKind createAlignmentKindFromString(EDataType eDataType, String initialValue)
  {
    AlignmentKind result = AlignmentKind.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertAlignmentKindToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public BreakKind createBreakKindFromString(EDataType eDataType, String initialValue)
  {
    BreakKind result = BreakKind.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertBreakKindToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CurrencyPattern createCurrencyPatternFromString(EDataType eDataType, String initialValue)
  {
    CurrencyPattern result = CurrencyPattern.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertCurrencyPatternToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DecisionOperand createDecisionOperandFromString(EDataType eDataType, String initialValue)
  {
    DecisionOperand result = DecisionOperand.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDecisionOperandToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DisplaySectionKind createDisplaySectionKindFromString(EDataType eDataType, String initialValue)
  {
    DisplaySectionKind result = DisplaySectionKind.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertDisplaySectionKindToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FieldFormat createFieldFormatFromString(EDataType eDataType, String initialValue)
  {
    FieldFormat result = FieldFormat.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFieldFormatToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FunctionKind createFunctionKindFromString(EDataType eDataType, String initialValue)
  {
    FunctionKind result = FunctionKind.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFunctionKindToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SelectionOperator createSelectionOperatorFromString(EDataType eDataType, String initialValue)
  {
    SelectionOperator result = SelectionOperator.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertSelectionOperatorToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CriteriaOperator createCriteriaOperatorFromString(EDataType eDataType, String initialValue)
  {
    CriteriaOperator result = CriteriaOperator.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertCriteriaOperatorToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProcessingMode createProcessingModeFromString(EDataType eDataType, String initialValue)
  {
    ProcessingMode result = ProcessingMode.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertProcessingModeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public SortOrder createSortOrderFromString(EDataType eDataType, String initialValue)
  {
    SortOrder result = SortOrder.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertSortOrderToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AndOr createAndOrFromString(EDataType eDataType, String initialValue)
  {
    AndOr result = AndOr.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertAndOrToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FileVersionOption createFileVersionOptionFromString(EDataType eDataType, String initialValue)
  {
    FileVersionOption result = FileVersionOption.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertFileVersionOptionToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EscapeSequence createEscapeSequenceFromString(EDataType eDataType, String initialValue)
  {
    EscapeSequence result = EscapeSequence.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertEscapeSequenceToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Orientation createOrientationFromString(EDataType eDataType, String initialValue)
  {
    Orientation result = Orientation.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertOrientationToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ServerMode createServerModeFromString(EDataType eDataType, String initialValue)
  {
    ServerMode result = ServerMode.get(initialValue);
    if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
    return result;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String convertServerModeToString(EDataType eDataType, Object instanceValue)
  {
    return instanceValue == null ? null : instanceValue.toString();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryPackage getEnquiryPackage()
  {
    return (EnquiryPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static EnquiryPackage getPackage()
  {
    return EnquiryPackage.eINSTANCE;
  }

} //EnquiryFactoryImpl
