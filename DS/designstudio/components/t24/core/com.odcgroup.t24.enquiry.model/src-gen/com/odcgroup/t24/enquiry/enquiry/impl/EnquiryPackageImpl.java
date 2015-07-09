/**
 */
package com.odcgroup.t24.enquiry.enquiry.impl;

import com.odcgroup.t24.enquiry.enquiry.AbsConversion;
import com.odcgroup.t24.enquiry.enquiry.AlignmentKind;
import com.odcgroup.t24.enquiry.enquiry.AndOr;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.ApplicationType;
import com.odcgroup.t24.enquiry.enquiry.Axis;
import com.odcgroup.t24.enquiry.enquiry.BasicConversion;
import com.odcgroup.t24.enquiry.enquiry.BasicIConversion;
import com.odcgroup.t24.enquiry.enquiry.BasicOConversion;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakKind;
import com.odcgroup.t24.enquiry.enquiry.BreakLineOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakOperation;
import com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion;
import com.odcgroup.t24.enquiry.enquiry.CalcOperation;
import com.odcgroup.t24.enquiry.enquiry.CallRoutine;
import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.CompanyOperation;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenType;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.ConvertConversion;
import com.odcgroup.t24.enquiry.enquiry.CriteriaOperator;
import com.odcgroup.t24.enquiry.enquiry.CurrencyPattern;
import com.odcgroup.t24.enquiry.enquiry.DateOperation;
import com.odcgroup.t24.enquiry.enquiry.DecisionOperand;
import com.odcgroup.t24.enquiry.enquiry.DecisionOperation;
import com.odcgroup.t24.enquiry.enquiry.DecryptConversion;
import com.odcgroup.t24.enquiry.enquiry.DescriptorOperation;
import com.odcgroup.t24.enquiry.enquiry.Dimension;
import com.odcgroup.t24.enquiry.enquiry.DisplaySectionKind;
import com.odcgroup.t24.enquiry.enquiry.DownloadType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownOption;
import com.odcgroup.t24.enquiry.enquiry.DrillDownStringType;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.EnquiryMode;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.EnquiryType;
import com.odcgroup.t24.enquiry.enquiry.EscapeSequence;
import com.odcgroup.t24.enquiry.enquiry.ExtractConversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldFormat;
import com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.FileVersion;
import com.odcgroup.t24.enquiry.enquiry.FileVersionOption;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.Format;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
import com.odcgroup.t24.enquiry.enquiry.FunctionKind;
import com.odcgroup.t24.enquiry.enquiry.GetFixedCurrencyConversion;
import com.odcgroup.t24.enquiry.enquiry.GetFixedRateConversion;
import com.odcgroup.t24.enquiry.enquiry.GetFromConversion;
import com.odcgroup.t24.enquiry.enquiry.Graph;
import com.odcgroup.t24.enquiry.enquiry.JBCRoutine;
import com.odcgroup.t24.enquiry.enquiry.JavaRoutine;
import com.odcgroup.t24.enquiry.enquiry.JavaScriptType;
import com.odcgroup.t24.enquiry.enquiry.JulianConversion;
import com.odcgroup.t24.enquiry.enquiry.LWDOperation;
import com.odcgroup.t24.enquiry.enquiry.Label;
import com.odcgroup.t24.enquiry.enquiry.LabelOperation;
import com.odcgroup.t24.enquiry.enquiry.LanguageOperation;
import com.odcgroup.t24.enquiry.enquiry.Legend;
import com.odcgroup.t24.enquiry.enquiry.LocalCurrencyOperation;
import com.odcgroup.t24.enquiry.enquiry.Margins;
import com.odcgroup.t24.enquiry.enquiry.MatchField;
import com.odcgroup.t24.enquiry.enquiry.NWDOperation;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.Orientation;
import com.odcgroup.t24.enquiry.enquiry.PWProcessType;
import com.odcgroup.t24.enquiry.enquiry.Parameter;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.Position;
import com.odcgroup.t24.enquiry.enquiry.ProcessingMode;
import com.odcgroup.t24.enquiry.enquiry.QuitSEEOption;
import com.odcgroup.t24.enquiry.enquiry.QuitSEEType;
import com.odcgroup.t24.enquiry.enquiry.RateConversion;
import com.odcgroup.t24.enquiry.enquiry.Reference;
import com.odcgroup.t24.enquiry.enquiry.RepeatConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion;
import com.odcgroup.t24.enquiry.enquiry.ReplaceConversion;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.RunType;
import com.odcgroup.t24.enquiry.enquiry.Scale;
import com.odcgroup.t24.enquiry.enquiry.ScreenType;
import com.odcgroup.t24.enquiry.enquiry.Security;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperation;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperator;
import com.odcgroup.t24.enquiry.enquiry.ServerMode;
import com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType;
import com.odcgroup.t24.enquiry.enquiry.SortOrder;
import com.odcgroup.t24.enquiry.enquiry.SystemOperation;
import com.odcgroup.t24.enquiry.enquiry.TabOption;
import com.odcgroup.t24.enquiry.enquiry.TabbedScreenType;
import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.enquiry.TodayOperation;
import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.enquiry.TotalOperation;
import com.odcgroup.t24.enquiry.enquiry.UserOperation;
import com.odcgroup.t24.enquiry.enquiry.UtilType;
import com.odcgroup.t24.enquiry.enquiry.ValueConversion;
import com.odcgroup.t24.enquiry.enquiry.ViewOption;
import com.odcgroup.t24.enquiry.enquiry.ViewType;
import com.odcgroup.t24.enquiry.enquiry.WebService;

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
public class EnquiryPackageImpl extends EPackageImpl implements EnquiryPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enquiryEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass companiesEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enquiryHeaderEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass targetEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass targetMappingEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parametersEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass drillDownEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass drillDownTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass drillDownStringTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass applicationTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass screenTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass enquiryTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fromFieldTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass compositeScreenTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass tabbedScreenTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass viewTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass quitSEETypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass blankTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass pwProcessTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass downloadTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass runTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass utilTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass javaScriptTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass shouldBeChangedTypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass drillDownOptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass compositeScreenOptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass tabOptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass viewOptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass quitSEEOptionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass referenceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass parameterEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass selectionCriteriaEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass securityEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass graphEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass axisEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass dimensionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass labelEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass positionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass legendEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass marginsEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass scaleEClass = null;

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
  private EClass fixedSelectionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fixedSortEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass selectionExpressionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass selectionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fileVersionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass operationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass breakOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass breakOnChangeOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass breakLineOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass calcOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass constantOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass labelOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass dateOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass decisionOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass descriptorOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass todayOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass lwdOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass nwdOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fieldOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass applicationFieldNameOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fieldNumberOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fieldExtractOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass selectionOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass systemOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass userOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass companyOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass languageOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass localCurrencyOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass totalOperationEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass conversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass extractConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass decryptConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass replaceConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass convertConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass valueConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass julianConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass basicConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass basicOConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass basicIConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass getFromConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rateConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass calcFixedRateConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass getFixedRateConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass getFixedCurrencyConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass absConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass matchFieldEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass callRoutineEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass repeatConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass repeatOnNullConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass repeatEveryConversionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass repeatSubConversionEClass = null;

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
  private EClass breakConditionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass fieldPositionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass formatEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass toolEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass webServiceEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum enquiryModeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum alignmentKindEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum breakKindEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum currencyPatternEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum decisionOperandEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum displaySectionKindEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum fieldFormatEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum functionKindEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum selectionOperatorEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum criteriaOperatorEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum processingModeEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum sortOrderEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum andOrEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum fileVersionOptionEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum escapeSequenceEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum orientationEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum serverModeEEnum = null;

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
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private EnquiryPackageImpl()
  {
    super(eNS_URI, EnquiryFactory.eINSTANCE);
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
   * <p>This method is used to initialize {@link EnquiryPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static EnquiryPackage init()
  {
    if (isInited) return (EnquiryPackage)EPackage.Registry.INSTANCE.getEPackage(EnquiryPackage.eNS_URI);

    // Obtain or create and register package
    EnquiryPackageImpl theEnquiryPackage = (EnquiryPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EnquiryPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EnquiryPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    TranslationDslPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theEnquiryPackage.createPackageContents();

    // Initialize created meta-data
    theEnquiryPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theEnquiryPackage.freeze();

  
    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(EnquiryPackage.eNS_URI, theEnquiryPackage);
    return theEnquiryPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnquiry()
  {
    return enquiryEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_Name()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_FileName()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_MetamodelVersion()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Header()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Description()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_ServerMode()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_EnquiryMode()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Companies()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_AccountField()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_CustomerField()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_ZeroRecordsDisplay()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_NoSelection()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_ShowAllBooks()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_StartLine()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_EndLine()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_BuildRoutines()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_FixedSelections()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_FixedSorts()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_CustomSelection()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Fields()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_Toolbar()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Tools()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Target()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_DrillDowns()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(23);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Security()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(24);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_Graph()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(25);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_WebService()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(26);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_GenerateIFP()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(27);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiry_FileVersion()
  {
    return (EReference)enquiryEClass.getEStructuralFeatures().get(28);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_Attributes()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(29);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiry_IntrospectionMessages()
  {
    return (EAttribute)enquiryEClass.getEStructuralFeatures().get(30);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCompanies()
  {
    return companiesEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCompanies_All()
  {
    return (EAttribute)companiesEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCompanies_Code()
  {
    return (EAttribute)companiesEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnquiryHeader()
  {
    return enquiryHeaderEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getEnquiryHeader_Label()
  {
    return (EReference)enquiryHeaderEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiryHeader_Column()
  {
    return (EAttribute)enquiryHeaderEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getEnquiryHeader_Line()
  {
    return (EAttribute)enquiryHeaderEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTarget()
  {
    return targetEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTarget_Application()
  {
    return (EAttribute)targetEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTarget_Screen()
  {
    return (EAttribute)targetEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTarget_Mappings()
  {
    return (EReference)targetEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTargetMapping()
  {
    return targetMappingEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTargetMapping_FromField()
  {
    return (EAttribute)targetMappingEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTargetMapping_ToField()
  {
    return (EAttribute)targetMappingEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParameters()
  {
    return parametersEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameters_Function()
  {
    return (EAttribute)parametersEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameters_Auto()
  {
    return (EAttribute)parametersEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameters_RunImmediately()
  {
    return (EAttribute)parametersEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameters_PwActivity()
  {
    return (EAttribute)parametersEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameters_FieldName()
  {
    return (EAttribute)parametersEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameters_Variable()
  {
    return (EAttribute)parametersEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDrillDown()
  {
    return drillDownEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDrillDown_Drill_name()
  {
    return (EAttribute)drillDownEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDrillDown_Description()
  {
    return (EReference)drillDownEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDrillDown_LabelField()
  {
    return (EAttribute)drillDownEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDrillDown_Image()
  {
    return (EAttribute)drillDownEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDrillDown_Info()
  {
    return (EAttribute)drillDownEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDrillDown_Criteria()
  {
    return (EReference)drillDownEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDrillDown_Parameters()
  {
    return (EReference)drillDownEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getDrillDown_Type()
  {
    return (EReference)drillDownEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDrillDownType()
  {
    return drillDownTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDrillDownType_Property()
  {
    return (EAttribute)drillDownTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDrillDownStringType()
  {
    return drillDownStringTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDrillDownStringType_Value()
  {
    return (EAttribute)drillDownStringTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getApplicationType()
  {
    return applicationTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getScreenType()
  {
    return screenTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getEnquiryType()
  {
    return enquiryTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFromFieldType()
  {
    return fromFieldTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCompositeScreenType()
  {
    return compositeScreenTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTabbedScreenType()
  {
    return tabbedScreenTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getViewType()
  {
    return viewTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getQuitSEEType()
  {
    return quitSEETypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBlankType()
  {
    return blankTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBlankType_Value()
  {
    return (EAttribute)blankTypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPWProcessType()
  {
    return pwProcessTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDownloadType()
  {
    return downloadTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRunType()
  {
    return runTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getUtilType()
  {
    return utilTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJavaScriptType()
  {
    return javaScriptTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getShouldBeChangedType()
  {
    return shouldBeChangedTypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDrillDownOption()
  {
    return drillDownOptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCompositeScreenOption()
  {
    return compositeScreenOptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCompositeScreenOption_CompositeScreen()
  {
    return (EAttribute)compositeScreenOptionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCompositeScreenOption_Reference()
  {
    return (EReference)compositeScreenOptionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCompositeScreenOption_FieldParameter()
  {
    return (EReference)compositeScreenOptionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTabOption()
  {
    return tabOptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTabOption_TabName()
  {
    return (EAttribute)tabOptionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTabOption_Reference()
  {
    return (EReference)tabOptionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTabOption_FieldParameter()
  {
    return (EReference)tabOptionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getViewOption()
  {
    return viewOptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getQuitSEEOption()
  {
    return quitSEEOptionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getReference()
  {
    return referenceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReference_File()
  {
    return (EAttribute)referenceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReference_Field()
  {
    return (EAttribute)referenceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getParameter()
  {
    return parameterEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getParameter_Field()
  {
    return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSelectionCriteria()
  {
    return selectionCriteriaEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelectionCriteria_Field()
  {
    return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelectionCriteria_Operand()
  {
    return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelectionCriteria_Values()
  {
    return (EAttribute)selectionCriteriaEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSecurity()
  {
    return securityEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSecurity_Application()
  {
    return (EAttribute)securityEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSecurity_Field()
  {
    return (EAttribute)securityEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSecurity_Abort()
  {
    return (EAttribute)securityEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGraph()
  {
    return graphEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGraph_Type()
  {
    return (EAttribute)graphEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_Labels()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_Dimension()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_Margins()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_Scale()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_Legend()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_XAxis()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_YAxis()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getGraph_ZAxis()
  {
    return (EReference)graphEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAxis()
  {
    return axisEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAxis_Field()
  {
    return (EAttribute)axisEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAxis_DisplayLegend()
  {
    return (EAttribute)axisEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getAxis_ShowGrid()
  {
    return (EAttribute)axisEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDimension()
  {
    return dimensionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDimension_Width()
  {
    return (EAttribute)dimensionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDimension_Height()
  {
    return (EAttribute)dimensionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDimension_Orientation()
  {
    return (EAttribute)dimensionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLabel()
  {
    return labelEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLabel_Description()
  {
    return (EReference)labelEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLabel_Position()
  {
    return (EReference)labelEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getPosition()
  {
    return positionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPosition_X()
  {
    return (EAttribute)positionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getPosition_Y()
  {
    return (EAttribute)positionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLegend()
  {
    return legendEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLegend_X()
  {
    return (EAttribute)legendEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getLegend_Y()
  {
    return (EAttribute)legendEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMargins()
  {
    return marginsEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMargins_Top()
  {
    return (EAttribute)marginsEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMargins_Bottom()
  {
    return (EAttribute)marginsEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMargins_Left()
  {
    return (EAttribute)marginsEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMargins_Right()
  {
    return (EAttribute)marginsEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getScale()
  {
    return scaleEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getScale_X()
  {
    return (EAttribute)scaleEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getScale_Y()
  {
    return (EAttribute)scaleEClass.getEStructuralFeatures().get(1);
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
  public EClass getFixedSelection()
  {
    return fixedSelectionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFixedSelection_Field()
  {
    return (EAttribute)fixedSelectionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFixedSelection_Operand()
  {
    return (EAttribute)fixedSelectionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFixedSelection_Values()
  {
    return (EAttribute)fixedSelectionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFixedSort()
  {
    return fixedSortEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFixedSort_Field()
  {
    return (EAttribute)fixedSortEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFixedSort_Order()
  {
    return (EAttribute)fixedSortEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSelectionExpression()
  {
    return selectionExpressionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSelectionExpression_Selection()
  {
    return (EReference)selectionExpressionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSelection()
  {
    return selectionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelection_Field()
  {
    return (EAttribute)selectionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelection_Mandatory()
  {
    return (EAttribute)selectionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelection_PopupDropDown()
  {
    return (EAttribute)selectionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSelection_Label()
  {
    return (EReference)selectionEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelection_Operands()
  {
    return (EAttribute)selectionEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelection_Operator()
  {
    return (EAttribute)selectionEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFileVersion()
  {
    return fileVersionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFileVersion_Values()
  {
    return (EAttribute)fileVersionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOperation()
  {
    return operationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBreakOperation()
  {
    return breakOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBreakOnChangeOperation()
  {
    return breakOnChangeOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBreakOnChangeOperation_Field()
  {
    return (EAttribute)breakOnChangeOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBreakLineOperation()
  {
    return breakLineOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBreakLineOperation_Line()
  {
    return (EAttribute)breakLineOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCalcOperation()
  {
    return calcOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCalcOperation_Field()
  {
    return (EAttribute)calcOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getCalcOperation_Operator()
  {
    return (EAttribute)calcOperationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConstantOperation()
  {
    return constantOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConstantOperation_Value()
  {
    return (EAttribute)constantOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLabelOperation()
  {
    return labelOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getLabelOperation_Label()
  {
    return (EReference)labelOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDateOperation()
  {
    return dateOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDecisionOperation()
  {
    return decisionOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDecisionOperation_LeftField()
  {
    return (EAttribute)decisionOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDecisionOperation_Operand()
  {
    return (EAttribute)decisionOperationEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDecisionOperation_RightField()
  {
    return (EAttribute)decisionOperationEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDecisionOperation_FirstField()
  {
    return (EAttribute)decisionOperationEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDecisionOperation_SecondField()
  {
    return (EAttribute)decisionOperationEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDescriptorOperation()
  {
    return descriptorOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDescriptorOperation_Field()
  {
    return (EAttribute)descriptorOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTodayOperation()
  {
    return todayOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLWDOperation()
  {
    return lwdOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getNWDOperation()
  {
    return nwdOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFieldOperation()
  {
    return fieldOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getApplicationFieldNameOperation()
  {
    return applicationFieldNameOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getApplicationFieldNameOperation_Field()
  {
    return (EAttribute)applicationFieldNameOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFieldNumberOperation()
  {
    return fieldNumberOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldNumberOperation_Number()
  {
    return (EAttribute)fieldNumberOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFieldExtractOperation()
  {
    return fieldExtractOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldExtractOperation_Field()
  {
    return (EAttribute)fieldExtractOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSelectionOperation()
  {
    return selectionOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSelectionOperation_Field()
  {
    return (EAttribute)selectionOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSystemOperation()
  {
    return systemOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getUserOperation()
  {
    return userOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCompanyOperation()
  {
    return companyOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLanguageOperation()
  {
    return languageOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getLocalCurrencyOperation()
  {
    return localCurrencyOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTotalOperation()
  {
    return totalOperationEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTotalOperation_Field()
  {
    return (EAttribute)totalOperationEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConversion()
  {
    return conversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getExtractConversion()
  {
    return extractConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExtractConversion_From()
  {
    return (EAttribute)extractConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExtractConversion_To()
  {
    return (EAttribute)extractConversionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getExtractConversion_Delimiter()
  {
    return (EAttribute)extractConversionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDecryptConversion()
  {
    return decryptConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getDecryptConversion_Field()
  {
    return (EAttribute)decryptConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getReplaceConversion()
  {
    return replaceConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReplaceConversion_OldData()
  {
    return (EAttribute)replaceConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getReplaceConversion_NewData()
  {
    return (EAttribute)replaceConversionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getConvertConversion()
  {
    return convertConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConvertConversion_OldData()
  {
    return (EAttribute)convertConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getConvertConversion_NewData()
  {
    return (EAttribute)convertConversionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getValueConversion()
  {
    return valueConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getValueConversion_Value()
  {
    return (EAttribute)valueConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getValueConversion_SubValue()
  {
    return (EAttribute)valueConversionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getJulianConversion()
  {
    return julianConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBasicConversion()
  {
    return basicConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBasicConversion_Instruction()
  {
    return (EAttribute)basicConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBasicOConversion()
  {
    return basicOConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBasicIConversion()
  {
    return basicIConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGetFromConversion()
  {
    return getFromConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGetFromConversion_Application()
  {
    return (EAttribute)getFromConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGetFromConversion_Field()
  {
    return (EAttribute)getFromConversionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getGetFromConversion_Language()
  {
    return (EAttribute)getFromConversionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRateConversion()
  {
    return rateConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getRateConversion_Field()
  {
    return (EAttribute)rateConversionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCalcFixedRateConversion()
  {
    return calcFixedRateConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGetFixedRateConversion()
  {
    return getFixedRateConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getGetFixedCurrencyConversion()
  {
    return getFixedCurrencyConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAbsConversion()
  {
    return absConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMatchField()
  {
    return matchFieldEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMatchField_Pattern()
  {
    return (EAttribute)matchFieldEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMatchField_Value()
  {
    return (EAttribute)matchFieldEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getCallRoutine()
  {
    return callRoutineEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getCallRoutine_Routine()
  {
    return (EReference)callRoutineEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRepeatConversion()
  {
    return repeatConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRepeatOnNullConversion()
  {
    return repeatOnNullConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRepeatEveryConversion()
  {
    return repeatEveryConversionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRepeatSubConversion()
  {
    return repeatSubConversionEClass;
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
  public EReference getField_Label()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Comments()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_DisplayType()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_Format()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_BreakCondition()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Length()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Alignment()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_CommaSeparator()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_NumberOfDecimals()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_EscapeSequence()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_FmtMask()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_DisplaySection()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(12);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_Position()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(13);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_ColumnWidth()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(14);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_SpoolBreak()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(15);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_SingleMulti()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(16);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Hidden()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(17);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_NoHeader()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(18);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_NoColumnLabel()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(19);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_Operation()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(20);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getField_Conversion()
  {
    return (EReference)fieldEClass.getEStructuralFeatures().get(21);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getField_Attributes()
  {
    return (EAttribute)fieldEClass.getEStructuralFeatures().get(22);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBreakCondition()
  {
    return breakConditionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBreakCondition_Break()
  {
    return (EAttribute)breakConditionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBreakCondition_Field()
  {
    return (EAttribute)breakConditionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFieldPosition()
  {
    return fieldPositionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldPosition_PageThrow()
  {
    return (EAttribute)fieldPositionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldPosition_Column()
  {
    return (EAttribute)fieldPositionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldPosition_Relative()
  {
    return (EAttribute)fieldPositionEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldPosition_Line()
  {
    return (EAttribute)fieldPositionEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFieldPosition_MultiLine()
  {
    return (EAttribute)fieldPositionEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getFormat()
  {
    return formatEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFormat_Format()
  {
    return (EAttribute)formatEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFormat_Field()
  {
    return (EAttribute)formatEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getFormat_Pattern()
  {
    return (EAttribute)formatEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getTool()
  {
    return toolEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTool_Name()
  {
    return (EAttribute)toolEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getTool_Label()
  {
    return (EReference)toolEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getTool_Command()
  {
    return (EAttribute)toolEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getWebService()
  {
    return webServiceEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWebService_PublishWebService()
  {
    return (EAttribute)webServiceEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWebService_WebServiceNames()
  {
    return (EAttribute)webServiceEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWebService_WebServiceActivity()
  {
    return (EAttribute)webServiceEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWebService_WebServiceDescription()
  {
    return (EAttribute)webServiceEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getEnquiryMode()
  {
    return enquiryModeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getAlignmentKind()
  {
    return alignmentKindEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getBreakKind()
  {
    return breakKindEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getCurrencyPattern()
  {
    return currencyPatternEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getDecisionOperand()
  {
    return decisionOperandEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getDisplaySectionKind()
  {
    return displaySectionKindEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getFieldFormat()
  {
    return fieldFormatEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getFunctionKind()
  {
    return functionKindEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getSelectionOperator()
  {
    return selectionOperatorEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getCriteriaOperator()
  {
    return criteriaOperatorEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getProcessingMode()
  {
    return processingModeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getSortOrder()
  {
    return sortOrderEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getAndOr()
  {
    return andOrEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getFileVersionOption()
  {
    return fileVersionOptionEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getEscapeSequence()
  {
    return escapeSequenceEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getOrientation()
  {
    return orientationEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getServerMode()
  {
    return serverModeEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryFactory getEnquiryFactory()
  {
    return (EnquiryFactory)getEFactoryInstance();
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
    enquiryEClass = createEClass(ENQUIRY);
    createEAttribute(enquiryEClass, ENQUIRY__NAME);
    createEAttribute(enquiryEClass, ENQUIRY__FILE_NAME);
    createEAttribute(enquiryEClass, ENQUIRY__METAMODEL_VERSION);
    createEReference(enquiryEClass, ENQUIRY__HEADER);
    createEReference(enquiryEClass, ENQUIRY__DESCRIPTION);
    createEAttribute(enquiryEClass, ENQUIRY__SERVER_MODE);
    createEAttribute(enquiryEClass, ENQUIRY__ENQUIRY_MODE);
    createEReference(enquiryEClass, ENQUIRY__COMPANIES);
    createEAttribute(enquiryEClass, ENQUIRY__ACCOUNT_FIELD);
    createEAttribute(enquiryEClass, ENQUIRY__CUSTOMER_FIELD);
    createEAttribute(enquiryEClass, ENQUIRY__ZERO_RECORDS_DISPLAY);
    createEAttribute(enquiryEClass, ENQUIRY__NO_SELECTION);
    createEAttribute(enquiryEClass, ENQUIRY__SHOW_ALL_BOOKS);
    createEAttribute(enquiryEClass, ENQUIRY__START_LINE);
    createEAttribute(enquiryEClass, ENQUIRY__END_LINE);
    createEReference(enquiryEClass, ENQUIRY__BUILD_ROUTINES);
    createEReference(enquiryEClass, ENQUIRY__FIXED_SELECTIONS);
    createEReference(enquiryEClass, ENQUIRY__FIXED_SORTS);
    createEReference(enquiryEClass, ENQUIRY__CUSTOM_SELECTION);
    createEReference(enquiryEClass, ENQUIRY__FIELDS);
    createEAttribute(enquiryEClass, ENQUIRY__TOOLBAR);
    createEReference(enquiryEClass, ENQUIRY__TOOLS);
    createEReference(enquiryEClass, ENQUIRY__TARGET);
    createEReference(enquiryEClass, ENQUIRY__DRILL_DOWNS);
    createEReference(enquiryEClass, ENQUIRY__SECURITY);
    createEReference(enquiryEClass, ENQUIRY__GRAPH);
    createEReference(enquiryEClass, ENQUIRY__WEB_SERVICE);
    createEAttribute(enquiryEClass, ENQUIRY__GENERATE_IFP);
    createEReference(enquiryEClass, ENQUIRY__FILE_VERSION);
    createEAttribute(enquiryEClass, ENQUIRY__ATTRIBUTES);
    createEAttribute(enquiryEClass, ENQUIRY__INTROSPECTION_MESSAGES);

    companiesEClass = createEClass(COMPANIES);
    createEAttribute(companiesEClass, COMPANIES__ALL);
    createEAttribute(companiesEClass, COMPANIES__CODE);

    enquiryHeaderEClass = createEClass(ENQUIRY_HEADER);
    createEReference(enquiryHeaderEClass, ENQUIRY_HEADER__LABEL);
    createEAttribute(enquiryHeaderEClass, ENQUIRY_HEADER__COLUMN);
    createEAttribute(enquiryHeaderEClass, ENQUIRY_HEADER__LINE);

    targetEClass = createEClass(TARGET);
    createEAttribute(targetEClass, TARGET__APPLICATION);
    createEAttribute(targetEClass, TARGET__SCREEN);
    createEReference(targetEClass, TARGET__MAPPINGS);

    targetMappingEClass = createEClass(TARGET_MAPPING);
    createEAttribute(targetMappingEClass, TARGET_MAPPING__FROM_FIELD);
    createEAttribute(targetMappingEClass, TARGET_MAPPING__TO_FIELD);

    parametersEClass = createEClass(PARAMETERS);
    createEAttribute(parametersEClass, PARAMETERS__FUNCTION);
    createEAttribute(parametersEClass, PARAMETERS__AUTO);
    createEAttribute(parametersEClass, PARAMETERS__RUN_IMMEDIATELY);
    createEAttribute(parametersEClass, PARAMETERS__PW_ACTIVITY);
    createEAttribute(parametersEClass, PARAMETERS__FIELD_NAME);
    createEAttribute(parametersEClass, PARAMETERS__VARIABLE);

    drillDownEClass = createEClass(DRILL_DOWN);
    createEAttribute(drillDownEClass, DRILL_DOWN__DRILL_NAME);
    createEReference(drillDownEClass, DRILL_DOWN__DESCRIPTION);
    createEAttribute(drillDownEClass, DRILL_DOWN__LABEL_FIELD);
    createEAttribute(drillDownEClass, DRILL_DOWN__IMAGE);
    createEAttribute(drillDownEClass, DRILL_DOWN__INFO);
    createEReference(drillDownEClass, DRILL_DOWN__CRITERIA);
    createEReference(drillDownEClass, DRILL_DOWN__PARAMETERS);
    createEReference(drillDownEClass, DRILL_DOWN__TYPE);

    drillDownTypeEClass = createEClass(DRILL_DOWN_TYPE);
    createEAttribute(drillDownTypeEClass, DRILL_DOWN_TYPE__PROPERTY);

    drillDownStringTypeEClass = createEClass(DRILL_DOWN_STRING_TYPE);
    createEAttribute(drillDownStringTypeEClass, DRILL_DOWN_STRING_TYPE__VALUE);

    applicationTypeEClass = createEClass(APPLICATION_TYPE);

    screenTypeEClass = createEClass(SCREEN_TYPE);

    enquiryTypeEClass = createEClass(ENQUIRY_TYPE);

    fromFieldTypeEClass = createEClass(FROM_FIELD_TYPE);

    compositeScreenTypeEClass = createEClass(COMPOSITE_SCREEN_TYPE);

    tabbedScreenTypeEClass = createEClass(TABBED_SCREEN_TYPE);

    viewTypeEClass = createEClass(VIEW_TYPE);

    quitSEETypeEClass = createEClass(QUIT_SEE_TYPE);

    blankTypeEClass = createEClass(BLANK_TYPE);
    createEAttribute(blankTypeEClass, BLANK_TYPE__VALUE);

    pwProcessTypeEClass = createEClass(PW_PROCESS_TYPE);

    downloadTypeEClass = createEClass(DOWNLOAD_TYPE);

    runTypeEClass = createEClass(RUN_TYPE);

    utilTypeEClass = createEClass(UTIL_TYPE);

    javaScriptTypeEClass = createEClass(JAVA_SCRIPT_TYPE);

    shouldBeChangedTypeEClass = createEClass(SHOULD_BE_CHANGED_TYPE);

    drillDownOptionEClass = createEClass(DRILL_DOWN_OPTION);

    compositeScreenOptionEClass = createEClass(COMPOSITE_SCREEN_OPTION);
    createEAttribute(compositeScreenOptionEClass, COMPOSITE_SCREEN_OPTION__COMPOSITE_SCREEN);
    createEReference(compositeScreenOptionEClass, COMPOSITE_SCREEN_OPTION__REFERENCE);
    createEReference(compositeScreenOptionEClass, COMPOSITE_SCREEN_OPTION__FIELD_PARAMETER);

    tabOptionEClass = createEClass(TAB_OPTION);
    createEAttribute(tabOptionEClass, TAB_OPTION__TAB_NAME);
    createEReference(tabOptionEClass, TAB_OPTION__REFERENCE);
    createEReference(tabOptionEClass, TAB_OPTION__FIELD_PARAMETER);

    viewOptionEClass = createEClass(VIEW_OPTION);

    quitSEEOptionEClass = createEClass(QUIT_SEE_OPTION);

    referenceEClass = createEClass(REFERENCE);
    createEAttribute(referenceEClass, REFERENCE__FILE);
    createEAttribute(referenceEClass, REFERENCE__FIELD);

    parameterEClass = createEClass(PARAMETER);
    createEAttribute(parameterEClass, PARAMETER__FIELD);

    selectionCriteriaEClass = createEClass(SELECTION_CRITERIA);
    createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__FIELD);
    createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__OPERAND);
    createEAttribute(selectionCriteriaEClass, SELECTION_CRITERIA__VALUES);

    securityEClass = createEClass(SECURITY);
    createEAttribute(securityEClass, SECURITY__APPLICATION);
    createEAttribute(securityEClass, SECURITY__FIELD);
    createEAttribute(securityEClass, SECURITY__ABORT);

    graphEClass = createEClass(GRAPH);
    createEAttribute(graphEClass, GRAPH__TYPE);
    createEReference(graphEClass, GRAPH__LABELS);
    createEReference(graphEClass, GRAPH__DIMENSION);
    createEReference(graphEClass, GRAPH__MARGINS);
    createEReference(graphEClass, GRAPH__SCALE);
    createEReference(graphEClass, GRAPH__LEGEND);
    createEReference(graphEClass, GRAPH__XAXIS);
    createEReference(graphEClass, GRAPH__YAXIS);
    createEReference(graphEClass, GRAPH__ZAXIS);

    axisEClass = createEClass(AXIS);
    createEAttribute(axisEClass, AXIS__FIELD);
    createEAttribute(axisEClass, AXIS__DISPLAY_LEGEND);
    createEAttribute(axisEClass, AXIS__SHOW_GRID);

    dimensionEClass = createEClass(DIMENSION);
    createEAttribute(dimensionEClass, DIMENSION__WIDTH);
    createEAttribute(dimensionEClass, DIMENSION__HEIGHT);
    createEAttribute(dimensionEClass, DIMENSION__ORIENTATION);

    labelEClass = createEClass(LABEL);
    createEReference(labelEClass, LABEL__DESCRIPTION);
    createEReference(labelEClass, LABEL__POSITION);

    positionEClass = createEClass(POSITION);
    createEAttribute(positionEClass, POSITION__X);
    createEAttribute(positionEClass, POSITION__Y);

    legendEClass = createEClass(LEGEND);
    createEAttribute(legendEClass, LEGEND__X);
    createEAttribute(legendEClass, LEGEND__Y);

    marginsEClass = createEClass(MARGINS);
    createEAttribute(marginsEClass, MARGINS__TOP);
    createEAttribute(marginsEClass, MARGINS__BOTTOM);
    createEAttribute(marginsEClass, MARGINS__LEFT);
    createEAttribute(marginsEClass, MARGINS__RIGHT);

    scaleEClass = createEClass(SCALE);
    createEAttribute(scaleEClass, SCALE__X);
    createEAttribute(scaleEClass, SCALE__Y);

    routineEClass = createEClass(ROUTINE);
    createEAttribute(routineEClass, ROUTINE__NAME);

    jbcRoutineEClass = createEClass(JBC_ROUTINE);

    javaRoutineEClass = createEClass(JAVA_ROUTINE);

    fixedSelectionEClass = createEClass(FIXED_SELECTION);
    createEAttribute(fixedSelectionEClass, FIXED_SELECTION__FIELD);
    createEAttribute(fixedSelectionEClass, FIXED_SELECTION__OPERAND);
    createEAttribute(fixedSelectionEClass, FIXED_SELECTION__VALUES);

    fixedSortEClass = createEClass(FIXED_SORT);
    createEAttribute(fixedSortEClass, FIXED_SORT__FIELD);
    createEAttribute(fixedSortEClass, FIXED_SORT__ORDER);

    selectionExpressionEClass = createEClass(SELECTION_EXPRESSION);
    createEReference(selectionExpressionEClass, SELECTION_EXPRESSION__SELECTION);

    selectionEClass = createEClass(SELECTION);
    createEAttribute(selectionEClass, SELECTION__FIELD);
    createEAttribute(selectionEClass, SELECTION__MANDATORY);
    createEAttribute(selectionEClass, SELECTION__POPUP_DROP_DOWN);
    createEReference(selectionEClass, SELECTION__LABEL);
    createEAttribute(selectionEClass, SELECTION__OPERANDS);
    createEAttribute(selectionEClass, SELECTION__OPERATOR);

    fileVersionEClass = createEClass(FILE_VERSION);
    createEAttribute(fileVersionEClass, FILE_VERSION__VALUES);

    operationEClass = createEClass(OPERATION);

    breakOperationEClass = createEClass(BREAK_OPERATION);

    breakOnChangeOperationEClass = createEClass(BREAK_ON_CHANGE_OPERATION);
    createEAttribute(breakOnChangeOperationEClass, BREAK_ON_CHANGE_OPERATION__FIELD);

    breakLineOperationEClass = createEClass(BREAK_LINE_OPERATION);
    createEAttribute(breakLineOperationEClass, BREAK_LINE_OPERATION__LINE);

    calcOperationEClass = createEClass(CALC_OPERATION);
    createEAttribute(calcOperationEClass, CALC_OPERATION__FIELD);
    createEAttribute(calcOperationEClass, CALC_OPERATION__OPERATOR);

    constantOperationEClass = createEClass(CONSTANT_OPERATION);
    createEAttribute(constantOperationEClass, CONSTANT_OPERATION__VALUE);

    labelOperationEClass = createEClass(LABEL_OPERATION);
    createEReference(labelOperationEClass, LABEL_OPERATION__LABEL);

    dateOperationEClass = createEClass(DATE_OPERATION);

    decisionOperationEClass = createEClass(DECISION_OPERATION);
    createEAttribute(decisionOperationEClass, DECISION_OPERATION__LEFT_FIELD);
    createEAttribute(decisionOperationEClass, DECISION_OPERATION__OPERAND);
    createEAttribute(decisionOperationEClass, DECISION_OPERATION__RIGHT_FIELD);
    createEAttribute(decisionOperationEClass, DECISION_OPERATION__FIRST_FIELD);
    createEAttribute(decisionOperationEClass, DECISION_OPERATION__SECOND_FIELD);

    descriptorOperationEClass = createEClass(DESCRIPTOR_OPERATION);
    createEAttribute(descriptorOperationEClass, DESCRIPTOR_OPERATION__FIELD);

    todayOperationEClass = createEClass(TODAY_OPERATION);

    lwdOperationEClass = createEClass(LWD_OPERATION);

    nwdOperationEClass = createEClass(NWD_OPERATION);

    fieldOperationEClass = createEClass(FIELD_OPERATION);

    applicationFieldNameOperationEClass = createEClass(APPLICATION_FIELD_NAME_OPERATION);
    createEAttribute(applicationFieldNameOperationEClass, APPLICATION_FIELD_NAME_OPERATION__FIELD);

    fieldNumberOperationEClass = createEClass(FIELD_NUMBER_OPERATION);
    createEAttribute(fieldNumberOperationEClass, FIELD_NUMBER_OPERATION__NUMBER);

    fieldExtractOperationEClass = createEClass(FIELD_EXTRACT_OPERATION);
    createEAttribute(fieldExtractOperationEClass, FIELD_EXTRACT_OPERATION__FIELD);

    selectionOperationEClass = createEClass(SELECTION_OPERATION);
    createEAttribute(selectionOperationEClass, SELECTION_OPERATION__FIELD);

    systemOperationEClass = createEClass(SYSTEM_OPERATION);

    userOperationEClass = createEClass(USER_OPERATION);

    companyOperationEClass = createEClass(COMPANY_OPERATION);

    languageOperationEClass = createEClass(LANGUAGE_OPERATION);

    localCurrencyOperationEClass = createEClass(LOCAL_CURRENCY_OPERATION);

    totalOperationEClass = createEClass(TOTAL_OPERATION);
    createEAttribute(totalOperationEClass, TOTAL_OPERATION__FIELD);

    conversionEClass = createEClass(CONVERSION);

    extractConversionEClass = createEClass(EXTRACT_CONVERSION);
    createEAttribute(extractConversionEClass, EXTRACT_CONVERSION__FROM);
    createEAttribute(extractConversionEClass, EXTRACT_CONVERSION__TO);
    createEAttribute(extractConversionEClass, EXTRACT_CONVERSION__DELIMITER);

    decryptConversionEClass = createEClass(DECRYPT_CONVERSION);
    createEAttribute(decryptConversionEClass, DECRYPT_CONVERSION__FIELD);

    replaceConversionEClass = createEClass(REPLACE_CONVERSION);
    createEAttribute(replaceConversionEClass, REPLACE_CONVERSION__OLD_DATA);
    createEAttribute(replaceConversionEClass, REPLACE_CONVERSION__NEW_DATA);

    convertConversionEClass = createEClass(CONVERT_CONVERSION);
    createEAttribute(convertConversionEClass, CONVERT_CONVERSION__OLD_DATA);
    createEAttribute(convertConversionEClass, CONVERT_CONVERSION__NEW_DATA);

    valueConversionEClass = createEClass(VALUE_CONVERSION);
    createEAttribute(valueConversionEClass, VALUE_CONVERSION__VALUE);
    createEAttribute(valueConversionEClass, VALUE_CONVERSION__SUB_VALUE);

    julianConversionEClass = createEClass(JULIAN_CONVERSION);

    basicConversionEClass = createEClass(BASIC_CONVERSION);
    createEAttribute(basicConversionEClass, BASIC_CONVERSION__INSTRUCTION);

    basicOConversionEClass = createEClass(BASIC_OCONVERSION);

    basicIConversionEClass = createEClass(BASIC_ICONVERSION);

    getFromConversionEClass = createEClass(GET_FROM_CONVERSION);
    createEAttribute(getFromConversionEClass, GET_FROM_CONVERSION__APPLICATION);
    createEAttribute(getFromConversionEClass, GET_FROM_CONVERSION__FIELD);
    createEAttribute(getFromConversionEClass, GET_FROM_CONVERSION__LANGUAGE);

    rateConversionEClass = createEClass(RATE_CONVERSION);
    createEAttribute(rateConversionEClass, RATE_CONVERSION__FIELD);

    calcFixedRateConversionEClass = createEClass(CALC_FIXED_RATE_CONVERSION);

    getFixedRateConversionEClass = createEClass(GET_FIXED_RATE_CONVERSION);

    getFixedCurrencyConversionEClass = createEClass(GET_FIXED_CURRENCY_CONVERSION);

    absConversionEClass = createEClass(ABS_CONVERSION);

    matchFieldEClass = createEClass(MATCH_FIELD);
    createEAttribute(matchFieldEClass, MATCH_FIELD__PATTERN);
    createEAttribute(matchFieldEClass, MATCH_FIELD__VALUE);

    callRoutineEClass = createEClass(CALL_ROUTINE);
    createEReference(callRoutineEClass, CALL_ROUTINE__ROUTINE);

    repeatConversionEClass = createEClass(REPEAT_CONVERSION);

    repeatOnNullConversionEClass = createEClass(REPEAT_ON_NULL_CONVERSION);

    repeatEveryConversionEClass = createEClass(REPEAT_EVERY_CONVERSION);

    repeatSubConversionEClass = createEClass(REPEAT_SUB_CONVERSION);

    fieldEClass = createEClass(FIELD);
    createEAttribute(fieldEClass, FIELD__NAME);
    createEReference(fieldEClass, FIELD__LABEL);
    createEAttribute(fieldEClass, FIELD__COMMENTS);
    createEAttribute(fieldEClass, FIELD__DISPLAY_TYPE);
    createEReference(fieldEClass, FIELD__FORMAT);
    createEReference(fieldEClass, FIELD__BREAK_CONDITION);
    createEAttribute(fieldEClass, FIELD__LENGTH);
    createEAttribute(fieldEClass, FIELD__ALIGNMENT);
    createEAttribute(fieldEClass, FIELD__COMMA_SEPARATOR);
    createEAttribute(fieldEClass, FIELD__NUMBER_OF_DECIMALS);
    createEAttribute(fieldEClass, FIELD__ESCAPE_SEQUENCE);
    createEAttribute(fieldEClass, FIELD__FMT_MASK);
    createEAttribute(fieldEClass, FIELD__DISPLAY_SECTION);
    createEReference(fieldEClass, FIELD__POSITION);
    createEAttribute(fieldEClass, FIELD__COLUMN_WIDTH);
    createEAttribute(fieldEClass, FIELD__SPOOL_BREAK);
    createEAttribute(fieldEClass, FIELD__SINGLE_MULTI);
    createEAttribute(fieldEClass, FIELD__HIDDEN);
    createEAttribute(fieldEClass, FIELD__NO_HEADER);
    createEAttribute(fieldEClass, FIELD__NO_COLUMN_LABEL);
    createEReference(fieldEClass, FIELD__OPERATION);
    createEReference(fieldEClass, FIELD__CONVERSION);
    createEAttribute(fieldEClass, FIELD__ATTRIBUTES);

    breakConditionEClass = createEClass(BREAK_CONDITION);
    createEAttribute(breakConditionEClass, BREAK_CONDITION__BREAK);
    createEAttribute(breakConditionEClass, BREAK_CONDITION__FIELD);

    fieldPositionEClass = createEClass(FIELD_POSITION);
    createEAttribute(fieldPositionEClass, FIELD_POSITION__PAGE_THROW);
    createEAttribute(fieldPositionEClass, FIELD_POSITION__COLUMN);
    createEAttribute(fieldPositionEClass, FIELD_POSITION__RELATIVE);
    createEAttribute(fieldPositionEClass, FIELD_POSITION__LINE);
    createEAttribute(fieldPositionEClass, FIELD_POSITION__MULTI_LINE);

    formatEClass = createEClass(FORMAT);
    createEAttribute(formatEClass, FORMAT__FORMAT);
    createEAttribute(formatEClass, FORMAT__FIELD);
    createEAttribute(formatEClass, FORMAT__PATTERN);

    toolEClass = createEClass(TOOL);
    createEAttribute(toolEClass, TOOL__NAME);
    createEReference(toolEClass, TOOL__LABEL);
    createEAttribute(toolEClass, TOOL__COMMAND);

    webServiceEClass = createEClass(WEB_SERVICE);
    createEAttribute(webServiceEClass, WEB_SERVICE__PUBLISH_WEB_SERVICE);
    createEAttribute(webServiceEClass, WEB_SERVICE__WEB_SERVICE_NAMES);
    createEAttribute(webServiceEClass, WEB_SERVICE__WEB_SERVICE_ACTIVITY);
    createEAttribute(webServiceEClass, WEB_SERVICE__WEB_SERVICE_DESCRIPTION);

    // Create enums
    enquiryModeEEnum = createEEnum(ENQUIRY_MODE);
    alignmentKindEEnum = createEEnum(ALIGNMENT_KIND);
    breakKindEEnum = createEEnum(BREAK_KIND);
    currencyPatternEEnum = createEEnum(CURRENCY_PATTERN);
    decisionOperandEEnum = createEEnum(DECISION_OPERAND);
    displaySectionKindEEnum = createEEnum(DISPLAY_SECTION_KIND);
    fieldFormatEEnum = createEEnum(FIELD_FORMAT);
    functionKindEEnum = createEEnum(FUNCTION_KIND);
    selectionOperatorEEnum = createEEnum(SELECTION_OPERATOR);
    criteriaOperatorEEnum = createEEnum(CRITERIA_OPERATOR);
    processingModeEEnum = createEEnum(PROCESSING_MODE);
    sortOrderEEnum = createEEnum(SORT_ORDER);
    andOrEEnum = createEEnum(AND_OR);
    fileVersionOptionEEnum = createEEnum(FILE_VERSION_OPTION);
    escapeSequenceEEnum = createEEnum(ESCAPE_SEQUENCE);
    orientationEEnum = createEEnum(ORIENTATION);
    serverModeEEnum = createEEnum(SERVER_MODE);
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
    TranslationDslPackage theTranslationDslPackage = (TranslationDslPackage)EPackage.Registry.INSTANCE.getEPackage(TranslationDslPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    drillDownStringTypeEClass.getESuperTypes().add(this.getDrillDownType());
    applicationTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    screenTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    enquiryTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    fromFieldTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    compositeScreenTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    tabbedScreenTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    viewTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    quitSEETypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    blankTypeEClass.getESuperTypes().add(this.getDrillDownType());
    pwProcessTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    downloadTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    runTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    utilTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    javaScriptTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    shouldBeChangedTypeEClass.getESuperTypes().add(this.getDrillDownStringType());
    compositeScreenOptionEClass.getESuperTypes().add(this.getDrillDownOption());
    tabOptionEClass.getESuperTypes().add(this.getDrillDownOption());
    viewOptionEClass.getESuperTypes().add(this.getDrillDownOption());
    quitSEEOptionEClass.getESuperTypes().add(this.getDrillDownOption());
    jbcRoutineEClass.getESuperTypes().add(this.getRoutine());
    javaRoutineEClass.getESuperTypes().add(this.getRoutine());
    breakOperationEClass.getESuperTypes().add(this.getOperation());
    breakOnChangeOperationEClass.getESuperTypes().add(this.getBreakOperation());
    breakLineOperationEClass.getESuperTypes().add(this.getBreakOperation());
    calcOperationEClass.getESuperTypes().add(this.getOperation());
    constantOperationEClass.getESuperTypes().add(this.getOperation());
    labelOperationEClass.getESuperTypes().add(this.getOperation());
    dateOperationEClass.getESuperTypes().add(this.getOperation());
    decisionOperationEClass.getESuperTypes().add(this.getOperation());
    descriptorOperationEClass.getESuperTypes().add(this.getOperation());
    todayOperationEClass.getESuperTypes().add(this.getDateOperation());
    lwdOperationEClass.getESuperTypes().add(this.getDateOperation());
    nwdOperationEClass.getESuperTypes().add(this.getDateOperation());
    fieldOperationEClass.getESuperTypes().add(this.getOperation());
    applicationFieldNameOperationEClass.getESuperTypes().add(this.getFieldOperation());
    fieldNumberOperationEClass.getESuperTypes().add(this.getFieldOperation());
    fieldExtractOperationEClass.getESuperTypes().add(this.getFieldOperation());
    selectionOperationEClass.getESuperTypes().add(this.getOperation());
    systemOperationEClass.getESuperTypes().add(this.getOperation());
    userOperationEClass.getESuperTypes().add(this.getSystemOperation());
    companyOperationEClass.getESuperTypes().add(this.getSystemOperation());
    languageOperationEClass.getESuperTypes().add(this.getSystemOperation());
    localCurrencyOperationEClass.getESuperTypes().add(this.getSystemOperation());
    totalOperationEClass.getESuperTypes().add(this.getOperation());
    extractConversionEClass.getESuperTypes().add(this.getConversion());
    decryptConversionEClass.getESuperTypes().add(this.getConversion());
    replaceConversionEClass.getESuperTypes().add(this.getConversion());
    convertConversionEClass.getESuperTypes().add(this.getConversion());
    valueConversionEClass.getESuperTypes().add(this.getConversion());
    julianConversionEClass.getESuperTypes().add(this.getConversion());
    basicConversionEClass.getESuperTypes().add(this.getConversion());
    basicOConversionEClass.getESuperTypes().add(this.getBasicConversion());
    basicIConversionEClass.getESuperTypes().add(this.getBasicConversion());
    getFromConversionEClass.getESuperTypes().add(this.getConversion());
    rateConversionEClass.getESuperTypes().add(this.getConversion());
    calcFixedRateConversionEClass.getESuperTypes().add(this.getRateConversion());
    getFixedRateConversionEClass.getESuperTypes().add(this.getRateConversion());
    getFixedCurrencyConversionEClass.getESuperTypes().add(this.getRateConversion());
    absConversionEClass.getESuperTypes().add(this.getConversion());
    matchFieldEClass.getESuperTypes().add(this.getConversion());
    callRoutineEClass.getESuperTypes().add(this.getConversion());
    repeatConversionEClass.getESuperTypes().add(this.getConversion());
    repeatOnNullConversionEClass.getESuperTypes().add(this.getRepeatConversion());
    repeatEveryConversionEClass.getESuperTypes().add(this.getRepeatConversion());
    repeatSubConversionEClass.getESuperTypes().add(this.getRepeatConversion());

    // Initialize classes and features; add operations and parameters
    initEClass(enquiryEClass, Enquiry.class, "Enquiry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getEnquiry_Name(), ecorePackage.getEString(), "name", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_MetamodelVersion(), ecorePackage.getEString(), "metamodelVersion", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Header(), this.getEnquiryHeader(), null, "header", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Description(), theTranslationDslPackage.getTranslations(), null, "description", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_ServerMode(), this.getServerMode(), "serverMode", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_EnquiryMode(), this.getEnquiryMode(), "enquiryMode", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Companies(), this.getCompanies(), null, "companies", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_AccountField(), ecorePackage.getEString(), "accountField", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_CustomerField(), ecorePackage.getEString(), "customerField", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_ZeroRecordsDisplay(), ecorePackage.getEBooleanObject(), "zeroRecordsDisplay", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_NoSelection(), ecorePackage.getEBooleanObject(), "noSelection", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_ShowAllBooks(), ecorePackage.getEBooleanObject(), "showAllBooks", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_StartLine(), ecorePackage.getEIntegerObject(), "startLine", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_EndLine(), ecorePackage.getEIntegerObject(), "endLine", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_BuildRoutines(), this.getRoutine(), null, "buildRoutines", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_FixedSelections(), this.getFixedSelection(), null, "fixedSelections", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_FixedSorts(), this.getFixedSort(), null, "fixedSorts", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_CustomSelection(), this.getSelectionExpression(), null, "customSelection", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Fields(), this.getField(), null, "fields", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_Toolbar(), ecorePackage.getEString(), "toolbar", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Tools(), this.getTool(), null, "tools", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Target(), this.getTarget(), null, "target", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_DrillDowns(), this.getDrillDown(), null, "drillDowns", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Security(), this.getSecurity(), null, "security", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_Graph(), this.getGraph(), null, "graph", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_WebService(), this.getWebService(), null, "webService", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_GenerateIFP(), ecorePackage.getEBooleanObject(), "generateIFP", null, 0, 1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getEnquiry_FileVersion(), this.getFileVersion(), null, "fileVersion", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_Attributes(), ecorePackage.getEString(), "attributes", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiry_IntrospectionMessages(), ecorePackage.getEString(), "introspectionMessages", null, 0, -1, Enquiry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(companiesEClass, Companies.class, "Companies", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCompanies_All(), ecorePackage.getEBooleanObject(), "all", null, 0, 1, Companies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCompanies_Code(), ecorePackage.getEString(), "code", null, 0, -1, Companies.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(enquiryHeaderEClass, EnquiryHeader.class, "EnquiryHeader", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getEnquiryHeader_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, EnquiryHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiryHeader_Column(), ecorePackage.getEInt(), "column", "0", 0, 1, EnquiryHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getEnquiryHeader_Line(), ecorePackage.getEInt(), "line", "0", 0, 1, EnquiryHeader.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(targetEClass, Target.class, "Target", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTarget_Application(), ecorePackage.getEString(), "application", null, 0, 1, Target.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTarget_Screen(), ecorePackage.getEString(), "screen", null, 0, 1, Target.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTarget_Mappings(), this.getTargetMapping(), null, "mappings", null, 0, -1, Target.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(targetMappingEClass, TargetMapping.class, "TargetMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTargetMapping_FromField(), ecorePackage.getEString(), "fromField", null, 0, 1, TargetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTargetMapping_ToField(), ecorePackage.getEString(), "toField", null, 0, 1, TargetMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parametersEClass, Parameters.class, "Parameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getParameters_Function(), this.getFunctionKind(), "function", null, 0, 1, Parameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameters_Auto(), ecorePackage.getEBoolean(), "auto", null, 0, 1, Parameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameters_RunImmediately(), ecorePackage.getEBoolean(), "runImmediately", null, 0, 1, Parameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameters_PwActivity(), ecorePackage.getEString(), "pwActivity", null, 0, 1, Parameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameters_FieldName(), ecorePackage.getEString(), "fieldName", null, 0, -1, Parameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getParameters_Variable(), ecorePackage.getEString(), "variable", null, 0, -1, Parameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(drillDownEClass, DrillDown.class, "DrillDown", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDrillDown_Drill_name(), ecorePackage.getEString(), "drill_name", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDrillDown_Description(), theTranslationDslPackage.getTranslations(), null, "description", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDrillDown_LabelField(), ecorePackage.getEString(), "labelField", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDrillDown_Image(), ecorePackage.getEString(), "image", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDrillDown_Info(), ecorePackage.getEString(), "info", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDrillDown_Criteria(), this.getSelectionCriteria(), null, "criteria", null, 0, -1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDrillDown_Parameters(), this.getParameters(), null, "parameters", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getDrillDown_Type(), this.getDrillDownType(), null, "type", null, 0, 1, DrillDown.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(drillDownTypeEClass, DrillDownType.class, "DrillDownType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDrillDownType_Property(), ecorePackage.getEString(), "property", null, 0, 1, DrillDownType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(drillDownStringTypeEClass, DrillDownStringType.class, "DrillDownStringType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDrillDownStringType_Value(), ecorePackage.getEString(), "value", null, 0, 1, DrillDownStringType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(applicationTypeEClass, ApplicationType.class, "ApplicationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(screenTypeEClass, ScreenType.class, "ScreenType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(enquiryTypeEClass, EnquiryType.class, "EnquiryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(fromFieldTypeEClass, FromFieldType.class, "FromFieldType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(compositeScreenTypeEClass, CompositeScreenType.class, "CompositeScreenType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(tabbedScreenTypeEClass, TabbedScreenType.class, "TabbedScreenType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(viewTypeEClass, ViewType.class, "ViewType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(quitSEETypeEClass, QuitSEEType.class, "QuitSEEType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(blankTypeEClass, BlankType.class, "BlankType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBlankType_Value(), ecorePackage.getEBooleanObject(), "value", null, 0, 1, BlankType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(pwProcessTypeEClass, PWProcessType.class, "PWProcessType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(downloadTypeEClass, DownloadType.class, "DownloadType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(runTypeEClass, RunType.class, "RunType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(utilTypeEClass, UtilType.class, "UtilType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(javaScriptTypeEClass, JavaScriptType.class, "JavaScriptType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(shouldBeChangedTypeEClass, ShouldBeChangedType.class, "ShouldBeChangedType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(drillDownOptionEClass, DrillDownOption.class, "DrillDownOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(compositeScreenOptionEClass, CompositeScreenOption.class, "CompositeScreenOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCompositeScreenOption_CompositeScreen(), ecorePackage.getEString(), "compositeScreen", null, 0, 1, CompositeScreenOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCompositeScreenOption_Reference(), this.getReference(), null, "reference", null, 0, -1, CompositeScreenOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getCompositeScreenOption_FieldParameter(), this.getParameter(), null, "fieldParameter", null, 0, -1, CompositeScreenOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(tabOptionEClass, TabOption.class, "TabOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTabOption_TabName(), ecorePackage.getEString(), "tabName", null, 0, 1, TabOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTabOption_Reference(), this.getReference(), null, "reference", null, 0, -1, TabOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTabOption_FieldParameter(), this.getParameter(), null, "fieldParameter", null, 0, -1, TabOption.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(viewOptionEClass, ViewOption.class, "ViewOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(quitSEEOptionEClass, QuitSEEOption.class, "QuitSEEOption", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(referenceEClass, Reference.class, "Reference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getReference_File(), ecorePackage.getEString(), "file", null, 0, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getReference_Field(), ecorePackage.getEString(), "field", null, 0, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getParameter_Field(), ecorePackage.getEString(), "field", null, 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(selectionCriteriaEClass, SelectionCriteria.class, "SelectionCriteria", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSelectionCriteria_Field(), ecorePackage.getEString(), "field", null, 0, 1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSelectionCriteria_Operand(), this.getCriteriaOperator(), "operand", null, 0, 1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSelectionCriteria_Values(), ecorePackage.getEString(), "values", null, 0, -1, SelectionCriteria.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(securityEClass, Security.class, "Security", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSecurity_Application(), ecorePackage.getEString(), "application", null, 0, 1, Security.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSecurity_Field(), ecorePackage.getEString(), "field", null, 0, 1, Security.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSecurity_Abort(), ecorePackage.getEBoolean(), "abort", null, 0, 1, Security.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(graphEClass, Graph.class, "Graph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGraph_Type(), ecorePackage.getEString(), "type", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_Labels(), this.getLabel(), null, "labels", null, 0, -1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_Dimension(), this.getDimension(), null, "dimension", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_Margins(), this.getMargins(), null, "margins", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_Scale(), this.getScale(), null, "scale", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_Legend(), this.getLegend(), null, "legend", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_XAxis(), this.getAxis(), null, "xAxis", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_YAxis(), this.getAxis(), null, "yAxis", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getGraph_ZAxis(), this.getAxis(), null, "zAxis", null, 0, 1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(axisEClass, Axis.class, "Axis", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getAxis_Field(), ecorePackage.getEString(), "field", null, 0, 1, Axis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAxis_DisplayLegend(), ecorePackage.getEBoolean(), "displayLegend", null, 0, 1, Axis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getAxis_ShowGrid(), ecorePackage.getEBoolean(), "showGrid", null, 0, 1, Axis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(dimensionEClass, Dimension.class, "Dimension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDimension_Width(), ecorePackage.getEInt(), "width", "0", 0, 1, Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDimension_Height(), ecorePackage.getEInt(), "height", "0", 0, 1, Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDimension_Orientation(), this.getOrientation(), "orientation", null, 0, 1, Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(labelEClass, Label.class, "Label", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLabel_Description(), theTranslationDslPackage.getTranslations(), null, "description", null, 0, 1, Label.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getLabel_Position(), this.getPosition(), null, "position", null, 0, 1, Label.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(positionEClass, Position.class, "Position", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getPosition_X(), ecorePackage.getEInt(), "x", "0", 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getPosition_Y(), ecorePackage.getEInt(), "y", "0", 0, 1, Position.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(legendEClass, Legend.class, "Legend", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getLegend_X(), ecorePackage.getEInt(), "x", "0", 0, 1, Legend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getLegend_Y(), ecorePackage.getEInt(), "y", "0", 0, 1, Legend.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(marginsEClass, Margins.class, "Margins", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMargins_Top(), ecorePackage.getEInt(), "top", "0", 0, 1, Margins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMargins_Bottom(), ecorePackage.getEInt(), "bottom", "0", 0, 1, Margins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMargins_Left(), ecorePackage.getEInt(), "left", "0", 0, 1, Margins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMargins_Right(), ecorePackage.getEInt(), "right", "0", 0, 1, Margins.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(scaleEClass, Scale.class, "Scale", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getScale_X(), ecorePackage.getEIntegerObject(), "x", null, 0, 1, Scale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getScale_Y(), ecorePackage.getEIntegerObject(), "y", null, 0, 1, Scale.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(routineEClass, Routine.class, "Routine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRoutine_Name(), ecorePackage.getEString(), "name", null, 0, 1, Routine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(jbcRoutineEClass, JBCRoutine.class, "JBCRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(javaRoutineEClass, JavaRoutine.class, "JavaRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(fixedSelectionEClass, FixedSelection.class, "FixedSelection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFixedSelection_Field(), ecorePackage.getEString(), "field", null, 0, 1, FixedSelection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFixedSelection_Operand(), this.getSelectionOperator(), "operand", null, 0, 1, FixedSelection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFixedSelection_Values(), ecorePackage.getEString(), "values", null, 0, -1, FixedSelection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fixedSortEClass, FixedSort.class, "FixedSort", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFixedSort_Field(), ecorePackage.getEString(), "field", null, 0, 1, FixedSort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFixedSort_Order(), this.getSortOrder(), "order", null, 0, 1, FixedSort.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(selectionExpressionEClass, SelectionExpression.class, "SelectionExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSelectionExpression_Selection(), this.getSelection(), null, "selection", null, 0, -1, SelectionExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(selectionEClass, Selection.class, "Selection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSelection_Field(), ecorePackage.getEString(), "field", null, 0, 1, Selection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSelection_Mandatory(), ecorePackage.getEBooleanObject(), "mandatory", null, 0, 1, Selection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSelection_PopupDropDown(), ecorePackage.getEBooleanObject(), "popupDropDown", null, 0, 1, Selection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSelection_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, Selection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSelection_Operands(), this.getSelectionOperator(), "operands", null, 0, -1, Selection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getSelection_Operator(), this.getAndOr(), "operator", null, 0, 1, Selection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fileVersionEClass, FileVersion.class, "FileVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFileVersion_Values(), this.getFileVersionOption(), "values", null, 0, -1, FileVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(operationEClass, Operation.class, "Operation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(breakOperationEClass, BreakOperation.class, "BreakOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(breakOnChangeOperationEClass, BreakOnChangeOperation.class, "BreakOnChangeOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBreakOnChangeOperation_Field(), ecorePackage.getEString(), "field", null, 0, 1, BreakOnChangeOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(breakLineOperationEClass, BreakLineOperation.class, "BreakLineOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBreakLineOperation_Line(), ecorePackage.getEInt(), "line", "0", 0, 1, BreakLineOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(calcOperationEClass, CalcOperation.class, "CalcOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getCalcOperation_Field(), ecorePackage.getEString(), "field", null, 0, -1, CalcOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getCalcOperation_Operator(), ecorePackage.getEString(), "operator", null, 0, -1, CalcOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(constantOperationEClass, ConstantOperation.class, "ConstantOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConstantOperation_Value(), ecorePackage.getEString(), "value", null, 0, 1, ConstantOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(labelOperationEClass, LabelOperation.class, "LabelOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getLabelOperation_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, LabelOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(dateOperationEClass, DateOperation.class, "DateOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(decisionOperationEClass, DecisionOperation.class, "DecisionOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDecisionOperation_LeftField(), ecorePackage.getEString(), "leftField", null, 0, 1, DecisionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDecisionOperation_Operand(), this.getDecisionOperand(), "operand", null, 0, 1, DecisionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDecisionOperation_RightField(), ecorePackage.getEString(), "rightField", null, 0, 1, DecisionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDecisionOperation_FirstField(), ecorePackage.getEString(), "firstField", null, 0, 1, DecisionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getDecisionOperation_SecondField(), ecorePackage.getEString(), "secondField", null, 0, 1, DecisionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(descriptorOperationEClass, DescriptorOperation.class, "DescriptorOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDescriptorOperation_Field(), ecorePackage.getEString(), "field", null, 0, 1, DescriptorOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(todayOperationEClass, TodayOperation.class, "TodayOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(lwdOperationEClass, LWDOperation.class, "LWDOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(nwdOperationEClass, NWDOperation.class, "NWDOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(fieldOperationEClass, FieldOperation.class, "FieldOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(applicationFieldNameOperationEClass, ApplicationFieldNameOperation.class, "ApplicationFieldNameOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getApplicationFieldNameOperation_Field(), ecorePackage.getEString(), "field", null, 0, 1, ApplicationFieldNameOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fieldNumberOperationEClass, FieldNumberOperation.class, "FieldNumberOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFieldNumberOperation_Number(), ecorePackage.getEInt(), "number", "0", 0, 1, FieldNumberOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fieldExtractOperationEClass, FieldExtractOperation.class, "FieldExtractOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFieldExtractOperation_Field(), ecorePackage.getEString(), "field", null, 0, 1, FieldExtractOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(selectionOperationEClass, SelectionOperation.class, "SelectionOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSelectionOperation_Field(), ecorePackage.getEString(), "field", null, 0, 1, SelectionOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(systemOperationEClass, SystemOperation.class, "SystemOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(userOperationEClass, UserOperation.class, "UserOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(companyOperationEClass, CompanyOperation.class, "CompanyOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(languageOperationEClass, LanguageOperation.class, "LanguageOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(localCurrencyOperationEClass, LocalCurrencyOperation.class, "LocalCurrencyOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(totalOperationEClass, TotalOperation.class, "TotalOperation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTotalOperation_Field(), ecorePackage.getEString(), "field", null, 0, 1, TotalOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(conversionEClass, Conversion.class, "Conversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(extractConversionEClass, ExtractConversion.class, "ExtractConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getExtractConversion_From(), ecorePackage.getEInt(), "from", "0", 0, 1, ExtractConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExtractConversion_To(), ecorePackage.getEInt(), "to", "0", 0, 1, ExtractConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getExtractConversion_Delimiter(), ecorePackage.getEString(), "delimiter", null, 0, 1, ExtractConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(decryptConversionEClass, DecryptConversion.class, "DecryptConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getDecryptConversion_Field(), ecorePackage.getEString(), "field", null, 0, 1, DecryptConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(replaceConversionEClass, ReplaceConversion.class, "ReplaceConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getReplaceConversion_OldData(), ecorePackage.getEString(), "oldData", null, 0, 1, ReplaceConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getReplaceConversion_NewData(), ecorePackage.getEString(), "newData", null, 0, 1, ReplaceConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(convertConversionEClass, ConvertConversion.class, "ConvertConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getConvertConversion_OldData(), ecorePackage.getEString(), "oldData", null, 0, 1, ConvertConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getConvertConversion_NewData(), ecorePackage.getEString(), "newData", null, 0, 1, ConvertConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(valueConversionEClass, ValueConversion.class, "ValueConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getValueConversion_Value(), ecorePackage.getEInt(), "value", "0", 0, 1, ValueConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getValueConversion_SubValue(), ecorePackage.getEIntegerObject(), "subValue", null, 0, 1, ValueConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(julianConversionEClass, JulianConversion.class, "JulianConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(basicConversionEClass, BasicConversion.class, "BasicConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBasicConversion_Instruction(), ecorePackage.getEString(), "instruction", null, 0, 1, BasicConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(basicOConversionEClass, BasicOConversion.class, "BasicOConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(basicIConversionEClass, BasicIConversion.class, "BasicIConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(getFromConversionEClass, GetFromConversion.class, "GetFromConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getGetFromConversion_Application(), ecorePackage.getEString(), "application", null, 0, 1, GetFromConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGetFromConversion_Field(), ecorePackage.getEString(), "field", null, 0, 1, GetFromConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getGetFromConversion_Language(), ecorePackage.getEBoolean(), "language", null, 0, 1, GetFromConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(rateConversionEClass, RateConversion.class, "RateConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getRateConversion_Field(), ecorePackage.getEString(), "field", null, 0, 1, RateConversion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(calcFixedRateConversionEClass, CalcFixedRateConversion.class, "CalcFixedRateConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(getFixedRateConversionEClass, GetFixedRateConversion.class, "GetFixedRateConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(getFixedCurrencyConversionEClass, GetFixedCurrencyConversion.class, "GetFixedCurrencyConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(absConversionEClass, AbsConversion.class, "AbsConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(matchFieldEClass, MatchField.class, "MatchField", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMatchField_Pattern(), ecorePackage.getEString(), "pattern", null, 0, 1, MatchField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMatchField_Value(), ecorePackage.getEString(), "value", null, 0, 1, MatchField.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(callRoutineEClass, CallRoutine.class, "CallRoutine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getCallRoutine_Routine(), this.getRoutine(), null, "routine", null, 0, 1, CallRoutine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(repeatConversionEClass, RepeatConversion.class, "RepeatConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(repeatOnNullConversionEClass, RepeatOnNullConversion.class, "RepeatOnNullConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(repeatEveryConversionEClass, RepeatEveryConversion.class, "RepeatEveryConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(repeatSubConversionEClass, RepeatSubConversion.class, "RepeatSubConversion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(fieldEClass, Field.class, "Field", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getField_Name(), ecorePackage.getEString(), "name", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Comments(), ecorePackage.getEString(), "comments", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_DisplayType(), ecorePackage.getEString(), "displayType", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Format(), this.getFormat(), null, "format", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_BreakCondition(), this.getBreakCondition(), null, "breakCondition", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Length(), ecorePackage.getEIntegerObject(), "length", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Alignment(), this.getAlignmentKind(), "alignment", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_CommaSeparator(), ecorePackage.getEBooleanObject(), "commaSeparator", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_NumberOfDecimals(), ecorePackage.getEIntegerObject(), "numberOfDecimals", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_EscapeSequence(), this.getEscapeSequence(), "escapeSequence", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_FmtMask(), ecorePackage.getEString(), "fmtMask", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_DisplaySection(), this.getDisplaySectionKind(), "displaySection", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Position(), this.getFieldPosition(), null, "position", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_ColumnWidth(), ecorePackage.getEIntegerObject(), "columnWidth", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_SpoolBreak(), ecorePackage.getEBooleanObject(), "spoolBreak", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_SingleMulti(), this.getProcessingMode(), "singleMulti", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Hidden(), ecorePackage.getEBooleanObject(), "hidden", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_NoHeader(), ecorePackage.getEBooleanObject(), "noHeader", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_NoColumnLabel(), ecorePackage.getEBooleanObject(), "noColumnLabel", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Operation(), this.getOperation(), null, "operation", null, 0, 1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getField_Conversion(), this.getConversion(), null, "conversion", null, 0, -1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getField_Attributes(), ecorePackage.getEString(), "attributes", null, 0, -1, Field.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(breakConditionEClass, BreakCondition.class, "BreakCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBreakCondition_Break(), this.getBreakKind(), "break", null, 0, 1, BreakCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBreakCondition_Field(), ecorePackage.getEString(), "field", null, 0, 1, BreakCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(fieldPositionEClass, FieldPosition.class, "FieldPosition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFieldPosition_PageThrow(), ecorePackage.getEBooleanObject(), "pageThrow", null, 0, 1, FieldPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFieldPosition_Column(), ecorePackage.getEIntegerObject(), "column", null, 0, 1, FieldPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFieldPosition_Relative(), ecorePackage.getEString(), "relative", null, 0, 1, FieldPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFieldPosition_Line(), ecorePackage.getEIntegerObject(), "line", null, 0, 1, FieldPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFieldPosition_MultiLine(), ecorePackage.getEBooleanObject(), "multiLine", null, 0, 1, FieldPosition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(formatEClass, Format.class, "Format", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getFormat_Format(), this.getFieldFormat(), "format", null, 0, 1, Format.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFormat_Field(), ecorePackage.getEString(), "field", null, 0, 1, Format.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getFormat_Pattern(), this.getCurrencyPattern(), "pattern", null, 0, 1, Format.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(toolEClass, Tool.class, "Tool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getTool_Name(), ecorePackage.getEString(), "name", null, 0, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getTool_Label(), theTranslationDslPackage.getTranslations(), null, "label", null, 0, 1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getTool_Command(), ecorePackage.getEString(), "command", null, 0, -1, Tool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(webServiceEClass, WebService.class, "WebService", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getWebService_PublishWebService(), ecorePackage.getEBooleanObject(), "publishWebService", null, 0, 1, WebService.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWebService_WebServiceNames(), ecorePackage.getEString(), "webServiceNames", null, 0, -1, WebService.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWebService_WebServiceActivity(), ecorePackage.getEString(), "webServiceActivity", null, 0, 1, WebService.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getWebService_WebServiceDescription(), ecorePackage.getEString(), "webServiceDescription", null, 0, 1, WebService.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(enquiryModeEEnum, EnquiryMode.class, "EnquiryMode");
    addEEnumLiteral(enquiryModeEEnum, EnquiryMode.T24);
    addEEnumLiteral(enquiryModeEEnum, EnquiryMode.DB);

    initEEnum(alignmentKindEEnum, AlignmentKind.class, "AlignmentKind");
    addEEnumLiteral(alignmentKindEEnum, AlignmentKind.UNSPECIFIED);
    addEEnumLiteral(alignmentKindEEnum, AlignmentKind.NONE);
    addEEnumLiteral(alignmentKindEEnum, AlignmentKind.LEFT);
    addEEnumLiteral(alignmentKindEEnum, AlignmentKind.RIGHT);

    initEEnum(breakKindEEnum, BreakKind.class, "BreakKind");
    addEEnumLiteral(breakKindEEnum, BreakKind.UNSPECIFIED);
    addEEnumLiteral(breakKindEEnum, BreakKind.NONE);
    addEEnumLiteral(breakKindEEnum, BreakKind.ONCE);
    addEEnumLiteral(breakKindEEnum, BreakKind.END);
    addEEnumLiteral(breakKindEEnum, BreakKind.NEW_PAGE);
    addEEnumLiteral(breakKindEEnum, BreakKind.PAGE);

    initEEnum(currencyPatternEEnum, CurrencyPattern.class, "CurrencyPattern");
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.UNSPECIFIED);
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.NONE);
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.NULL);
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.E);
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.M);
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.B);
    addEEnumLiteral(currencyPatternEEnum, CurrencyPattern.N);

    initEEnum(decisionOperandEEnum, DecisionOperand.class, "DecisionOperand");
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.NONE);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.EQUALS);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.NOT_EQUALS);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.GREATER);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.GREATER_OR_EQUALS);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.LESS_THAN);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.LESS_OR_EQUALS);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.BETWEEN);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.NOT_BETWEEN);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.MATCHES);
    addEEnumLiteral(decisionOperandEEnum, DecisionOperand.NOT_MATCHES);

    initEEnum(displaySectionKindEEnum, DisplaySectionKind.class, "DisplaySectionKind");
    addEEnumLiteral(displaySectionKindEEnum, DisplaySectionKind.UNSPECIFIED);
    addEEnumLiteral(displaySectionKindEEnum, DisplaySectionKind.NONE);
    addEEnumLiteral(displaySectionKindEEnum, DisplaySectionKind.HEADER);
    addEEnumLiteral(displaySectionKindEEnum, DisplaySectionKind.FOOTER);
    addEEnumLiteral(displaySectionKindEEnum, DisplaySectionKind.CAPTION);
    addEEnumLiteral(displaySectionKindEEnum, DisplaySectionKind.NO_DISPLAY);

    initEEnum(fieldFormatEEnum, FieldFormat.class, "FieldFormat");
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.UNSPECIFIED);
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.NONE);
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.DATE);
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.USER);
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.TRANSLATE);
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.RATE);
    addEEnumLiteral(fieldFormatEEnum, FieldFormat.CURRENCY);

    initEEnum(functionKindEEnum, FunctionKind.class, "FunctionKind");
    addEEnumLiteral(functionKindEEnum, FunctionKind.UNSPECIFIED);
    addEEnumLiteral(functionKindEEnum, FunctionKind.NONE);
    addEEnumLiteral(functionKindEEnum, FunctionKind.INPUT);
    addEEnumLiteral(functionKindEEnum, FunctionKind.AUTHORISE);
    addEEnumLiteral(functionKindEEnum, FunctionKind.REVERSE);
    addEEnumLiteral(functionKindEEnum, FunctionKind.SEE);
    addEEnumLiteral(functionKindEEnum, FunctionKind.COPY);
    addEEnumLiteral(functionKindEEnum, FunctionKind.DELETE);
    addEEnumLiteral(functionKindEEnum, FunctionKind.VERIFY);

    initEEnum(selectionOperatorEEnum, SelectionOperator.class, "SelectionOperator");
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.NONE);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.EQUALS);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.NOT_EQUALS);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.GREATER);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.GREATER_OR_EQUALS);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.LESS_THAN);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.LESS_OR_EQUALS);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.MATCHES);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.NOT_MATCHES);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.BETWEEN);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.NOT_BETWEEN);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.CONTAINS);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.DOES_NOT_CONTAIN);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.BEGINS_WITH);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.DOES_NOT_BEGIN_WITH);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.ENDS_WITH);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.DOES_NOT_END_WITH);
    addEEnumLiteral(selectionOperatorEEnum, SelectionOperator.SOUNDS_LIKE);

    initEEnum(criteriaOperatorEEnum, CriteriaOperator.class, "CriteriaOperator");
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.NONE);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.EQUALS);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.NOT_EQUALS);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.GREATER);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.GREATER_OR_EQUALS);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.LESS_THAN);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.LESS_OR_EQUALS);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.MATCHES);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.NOT_MATCHES);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.BETWEEN);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.NOT_BETWEEN);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.CONTAINS);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.DOES_NOT_CONTAIN);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.BEGINS_WITH);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.DOES_NOT_BEGIN_WITH);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.ENDS_WITH);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.DOES_NOT_END_WITH);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.SOUNDS_LIKE);
    addEEnumLiteral(criteriaOperatorEEnum, CriteriaOperator.GREATER_THAN_SYMBOL);

    initEEnum(processingModeEEnum, ProcessingMode.class, "ProcessingMode");
    addEEnumLiteral(processingModeEEnum, ProcessingMode.UNSPECIFIED);
    addEEnumLiteral(processingModeEEnum, ProcessingMode.NONE);
    addEEnumLiteral(processingModeEEnum, ProcessingMode.SINGLE);
    addEEnumLiteral(processingModeEEnum, ProcessingMode.MULTI);

    initEEnum(sortOrderEEnum, SortOrder.class, "SortOrder");
    addEEnumLiteral(sortOrderEEnum, SortOrder.UNSPECIFIED);
    addEEnumLiteral(sortOrderEEnum, SortOrder.NONE);
    addEEnumLiteral(sortOrderEEnum, SortOrder.ASCENDING);
    addEEnumLiteral(sortOrderEEnum, SortOrder.DESCENDING);

    initEEnum(andOrEEnum, AndOr.class, "AndOr");
    addEEnumLiteral(andOrEEnum, AndOr.NONE);
    addEEnumLiteral(andOrEEnum, AndOr.AND);
    addEEnumLiteral(andOrEEnum, AndOr.OR);

    initEEnum(fileVersionOptionEEnum, FileVersionOption.class, "FileVersionOption");
    addEEnumLiteral(fileVersionOptionEEnum, FileVersionOption.NONE);
    addEEnumLiteral(fileVersionOptionEEnum, FileVersionOption.LIVE);
    addEEnumLiteral(fileVersionOptionEEnum, FileVersionOption.HISTORY);
    addEEnumLiteral(fileVersionOptionEEnum, FileVersionOption.EXCEPTION);
    addEEnumLiteral(fileVersionOptionEEnum, FileVersionOption.SIMULATION);

    initEEnum(escapeSequenceEEnum, EscapeSequence.class, "EscapeSequence");
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.UNSPECIFIED);
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.NONE);
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.RV);
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.UL);
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.BL);
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.HI);
    addEEnumLiteral(escapeSequenceEEnum, EscapeSequence.DING);

    initEEnum(orientationEEnum, Orientation.class, "Orientation");
    addEEnumLiteral(orientationEEnum, Orientation.HORIZONTAL);
    addEEnumLiteral(orientationEEnum, Orientation.VERTICAL);

    initEEnum(serverModeEEnum, ServerMode.class, "ServerMode");
    addEEnumLiteral(serverModeEEnum, ServerMode.T24);
    addEEnumLiteral(serverModeEEnum, ServerMode.SOLR);

    // Create resource
    createResource(eNS_URI);
  }

} //EnquiryPackageImpl
