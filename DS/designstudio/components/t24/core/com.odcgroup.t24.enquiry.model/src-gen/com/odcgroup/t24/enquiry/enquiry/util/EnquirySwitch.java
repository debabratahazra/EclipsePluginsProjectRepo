/**
 */
package com.odcgroup.t24.enquiry.enquiry.util;

import com.odcgroup.t24.enquiry.enquiry.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage
 * @generated
 */
public class EnquirySwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static EnquiryPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquirySwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = EnquiryPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @parameter ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case EnquiryPackage.ENQUIRY:
      {
        Enquiry enquiry = (Enquiry)theEObject;
        T result = caseEnquiry(enquiry);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.COMPANIES:
      {
        Companies companies = (Companies)theEObject;
        T result = caseCompanies(companies);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.ENQUIRY_HEADER:
      {
        EnquiryHeader enquiryHeader = (EnquiryHeader)theEObject;
        T result = caseEnquiryHeader(enquiryHeader);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TARGET:
      {
        Target target = (Target)theEObject;
        T result = caseTarget(target);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TARGET_MAPPING:
      {
        TargetMapping targetMapping = (TargetMapping)theEObject;
        T result = caseTargetMapping(targetMapping);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.PARAMETERS:
      {
        Parameters parameters = (Parameters)theEObject;
        T result = caseParameters(parameters);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DRILL_DOWN:
      {
        DrillDown drillDown = (DrillDown)theEObject;
        T result = caseDrillDown(drillDown);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DRILL_DOWN_TYPE:
      {
        DrillDownType drillDownType = (DrillDownType)theEObject;
        T result = caseDrillDownType(drillDownType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DRILL_DOWN_STRING_TYPE:
      {
        DrillDownStringType drillDownStringType = (DrillDownStringType)theEObject;
        T result = caseDrillDownStringType(drillDownStringType);
        if (result == null) result = caseDrillDownType(drillDownStringType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.APPLICATION_TYPE:
      {
        ApplicationType applicationType = (ApplicationType)theEObject;
        T result = caseApplicationType(applicationType);
        if (result == null) result = caseDrillDownStringType(applicationType);
        if (result == null) result = caseDrillDownType(applicationType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SCREEN_TYPE:
      {
        ScreenType screenType = (ScreenType)theEObject;
        T result = caseScreenType(screenType);
        if (result == null) result = caseDrillDownStringType(screenType);
        if (result == null) result = caseDrillDownType(screenType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.ENQUIRY_TYPE:
      {
        EnquiryType enquiryType = (EnquiryType)theEObject;
        T result = caseEnquiryType(enquiryType);
        if (result == null) result = caseDrillDownStringType(enquiryType);
        if (result == null) result = caseDrillDownType(enquiryType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FROM_FIELD_TYPE:
      {
        FromFieldType fromFieldType = (FromFieldType)theEObject;
        T result = caseFromFieldType(fromFieldType);
        if (result == null) result = caseDrillDownStringType(fromFieldType);
        if (result == null) result = caseDrillDownType(fromFieldType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.COMPOSITE_SCREEN_TYPE:
      {
        CompositeScreenType compositeScreenType = (CompositeScreenType)theEObject;
        T result = caseCompositeScreenType(compositeScreenType);
        if (result == null) result = caseDrillDownStringType(compositeScreenType);
        if (result == null) result = caseDrillDownType(compositeScreenType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TABBED_SCREEN_TYPE:
      {
        TabbedScreenType tabbedScreenType = (TabbedScreenType)theEObject;
        T result = caseTabbedScreenType(tabbedScreenType);
        if (result == null) result = caseDrillDownStringType(tabbedScreenType);
        if (result == null) result = caseDrillDownType(tabbedScreenType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.VIEW_TYPE:
      {
        ViewType viewType = (ViewType)theEObject;
        T result = caseViewType(viewType);
        if (result == null) result = caseDrillDownStringType(viewType);
        if (result == null) result = caseDrillDownType(viewType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.QUIT_SEE_TYPE:
      {
        QuitSEEType quitSEEType = (QuitSEEType)theEObject;
        T result = caseQuitSEEType(quitSEEType);
        if (result == null) result = caseDrillDownStringType(quitSEEType);
        if (result == null) result = caseDrillDownType(quitSEEType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BLANK_TYPE:
      {
        BlankType blankType = (BlankType)theEObject;
        T result = caseBlankType(blankType);
        if (result == null) result = caseDrillDownType(blankType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.PW_PROCESS_TYPE:
      {
        PWProcessType pwProcessType = (PWProcessType)theEObject;
        T result = casePWProcessType(pwProcessType);
        if (result == null) result = caseDrillDownStringType(pwProcessType);
        if (result == null) result = caseDrillDownType(pwProcessType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DOWNLOAD_TYPE:
      {
        DownloadType downloadType = (DownloadType)theEObject;
        T result = caseDownloadType(downloadType);
        if (result == null) result = caseDrillDownStringType(downloadType);
        if (result == null) result = caseDrillDownType(downloadType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.RUN_TYPE:
      {
        RunType runType = (RunType)theEObject;
        T result = caseRunType(runType);
        if (result == null) result = caseDrillDownStringType(runType);
        if (result == null) result = caseDrillDownType(runType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.UTIL_TYPE:
      {
        UtilType utilType = (UtilType)theEObject;
        T result = caseUtilType(utilType);
        if (result == null) result = caseDrillDownStringType(utilType);
        if (result == null) result = caseDrillDownType(utilType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.JAVA_SCRIPT_TYPE:
      {
        JavaScriptType javaScriptType = (JavaScriptType)theEObject;
        T result = caseJavaScriptType(javaScriptType);
        if (result == null) result = caseDrillDownStringType(javaScriptType);
        if (result == null) result = caseDrillDownType(javaScriptType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SHOULD_BE_CHANGED_TYPE:
      {
        ShouldBeChangedType shouldBeChangedType = (ShouldBeChangedType)theEObject;
        T result = caseShouldBeChangedType(shouldBeChangedType);
        if (result == null) result = caseDrillDownStringType(shouldBeChangedType);
        if (result == null) result = caseDrillDownType(shouldBeChangedType);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DRILL_DOWN_OPTION:
      {
        DrillDownOption drillDownOption = (DrillDownOption)theEObject;
        T result = caseDrillDownOption(drillDownOption);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.COMPOSITE_SCREEN_OPTION:
      {
        CompositeScreenOption compositeScreenOption = (CompositeScreenOption)theEObject;
        T result = caseCompositeScreenOption(compositeScreenOption);
        if (result == null) result = caseDrillDownOption(compositeScreenOption);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TAB_OPTION:
      {
        TabOption tabOption = (TabOption)theEObject;
        T result = caseTabOption(tabOption);
        if (result == null) result = caseDrillDownOption(tabOption);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.VIEW_OPTION:
      {
        ViewOption viewOption = (ViewOption)theEObject;
        T result = caseViewOption(viewOption);
        if (result == null) result = caseDrillDownOption(viewOption);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.QUIT_SEE_OPTION:
      {
        QuitSEEOption quitSEEOption = (QuitSEEOption)theEObject;
        T result = caseQuitSEEOption(quitSEEOption);
        if (result == null) result = caseDrillDownOption(quitSEEOption);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.REFERENCE:
      {
        Reference reference = (Reference)theEObject;
        T result = caseReference(reference);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.PARAMETER:
      {
        Parameter parameter = (Parameter)theEObject;
        T result = caseParameter(parameter);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SELECTION_CRITERIA:
      {
        SelectionCriteria selectionCriteria = (SelectionCriteria)theEObject;
        T result = caseSelectionCriteria(selectionCriteria);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SECURITY:
      {
        Security security = (Security)theEObject;
        T result = caseSecurity(security);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.GRAPH:
      {
        Graph graph = (Graph)theEObject;
        T result = caseGraph(graph);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.AXIS:
      {
        Axis axis = (Axis)theEObject;
        T result = caseAxis(axis);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DIMENSION:
      {
        Dimension dimension = (Dimension)theEObject;
        T result = caseDimension(dimension);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.LABEL:
      {
        Label label = (Label)theEObject;
        T result = caseLabel(label);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.POSITION:
      {
        Position position = (Position)theEObject;
        T result = casePosition(position);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.LEGEND:
      {
        Legend legend = (Legend)theEObject;
        T result = caseLegend(legend);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.MARGINS:
      {
        Margins margins = (Margins)theEObject;
        T result = caseMargins(margins);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SCALE:
      {
        Scale scale = (Scale)theEObject;
        T result = caseScale(scale);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.ROUTINE:
      {
        Routine routine = (Routine)theEObject;
        T result = caseRoutine(routine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.JBC_ROUTINE:
      {
        JBCRoutine jbcRoutine = (JBCRoutine)theEObject;
        T result = caseJBCRoutine(jbcRoutine);
        if (result == null) result = caseRoutine(jbcRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.JAVA_ROUTINE:
      {
        JavaRoutine javaRoutine = (JavaRoutine)theEObject;
        T result = caseJavaRoutine(javaRoutine);
        if (result == null) result = caseRoutine(javaRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIXED_SELECTION:
      {
        FixedSelection fixedSelection = (FixedSelection)theEObject;
        T result = caseFixedSelection(fixedSelection);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIXED_SORT:
      {
        FixedSort fixedSort = (FixedSort)theEObject;
        T result = caseFixedSort(fixedSort);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SELECTION_EXPRESSION:
      {
        SelectionExpression selectionExpression = (SelectionExpression)theEObject;
        T result = caseSelectionExpression(selectionExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SELECTION:
      {
        Selection selection = (Selection)theEObject;
        T result = caseSelection(selection);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FILE_VERSION:
      {
        FileVersion fileVersion = (FileVersion)theEObject;
        T result = caseFileVersion(fileVersion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.OPERATION:
      {
        Operation operation = (Operation)theEObject;
        T result = caseOperation(operation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BREAK_OPERATION:
      {
        BreakOperation breakOperation = (BreakOperation)theEObject;
        T result = caseBreakOperation(breakOperation);
        if (result == null) result = caseOperation(breakOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BREAK_ON_CHANGE_OPERATION:
      {
        BreakOnChangeOperation breakOnChangeOperation = (BreakOnChangeOperation)theEObject;
        T result = caseBreakOnChangeOperation(breakOnChangeOperation);
        if (result == null) result = caseBreakOperation(breakOnChangeOperation);
        if (result == null) result = caseOperation(breakOnChangeOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BREAK_LINE_OPERATION:
      {
        BreakLineOperation breakLineOperation = (BreakLineOperation)theEObject;
        T result = caseBreakLineOperation(breakLineOperation);
        if (result == null) result = caseBreakOperation(breakLineOperation);
        if (result == null) result = caseOperation(breakLineOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.CALC_OPERATION:
      {
        CalcOperation calcOperation = (CalcOperation)theEObject;
        T result = caseCalcOperation(calcOperation);
        if (result == null) result = caseOperation(calcOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.CONSTANT_OPERATION:
      {
        ConstantOperation constantOperation = (ConstantOperation)theEObject;
        T result = caseConstantOperation(constantOperation);
        if (result == null) result = caseOperation(constantOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.LABEL_OPERATION:
      {
        LabelOperation labelOperation = (LabelOperation)theEObject;
        T result = caseLabelOperation(labelOperation);
        if (result == null) result = caseOperation(labelOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DATE_OPERATION:
      {
        DateOperation dateOperation = (DateOperation)theEObject;
        T result = caseDateOperation(dateOperation);
        if (result == null) result = caseOperation(dateOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DECISION_OPERATION:
      {
        DecisionOperation decisionOperation = (DecisionOperation)theEObject;
        T result = caseDecisionOperation(decisionOperation);
        if (result == null) result = caseOperation(decisionOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DESCRIPTOR_OPERATION:
      {
        DescriptorOperation descriptorOperation = (DescriptorOperation)theEObject;
        T result = caseDescriptorOperation(descriptorOperation);
        if (result == null) result = caseOperation(descriptorOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TODAY_OPERATION:
      {
        TodayOperation todayOperation = (TodayOperation)theEObject;
        T result = caseTodayOperation(todayOperation);
        if (result == null) result = caseDateOperation(todayOperation);
        if (result == null) result = caseOperation(todayOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.LWD_OPERATION:
      {
        LWDOperation lwdOperation = (LWDOperation)theEObject;
        T result = caseLWDOperation(lwdOperation);
        if (result == null) result = caseDateOperation(lwdOperation);
        if (result == null) result = caseOperation(lwdOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.NWD_OPERATION:
      {
        NWDOperation nwdOperation = (NWDOperation)theEObject;
        T result = caseNWDOperation(nwdOperation);
        if (result == null) result = caseDateOperation(nwdOperation);
        if (result == null) result = caseOperation(nwdOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIELD_OPERATION:
      {
        FieldOperation fieldOperation = (FieldOperation)theEObject;
        T result = caseFieldOperation(fieldOperation);
        if (result == null) result = caseOperation(fieldOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.APPLICATION_FIELD_NAME_OPERATION:
      {
        ApplicationFieldNameOperation applicationFieldNameOperation = (ApplicationFieldNameOperation)theEObject;
        T result = caseApplicationFieldNameOperation(applicationFieldNameOperation);
        if (result == null) result = caseFieldOperation(applicationFieldNameOperation);
        if (result == null) result = caseOperation(applicationFieldNameOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIELD_NUMBER_OPERATION:
      {
        FieldNumberOperation fieldNumberOperation = (FieldNumberOperation)theEObject;
        T result = caseFieldNumberOperation(fieldNumberOperation);
        if (result == null) result = caseFieldOperation(fieldNumberOperation);
        if (result == null) result = caseOperation(fieldNumberOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIELD_EXTRACT_OPERATION:
      {
        FieldExtractOperation fieldExtractOperation = (FieldExtractOperation)theEObject;
        T result = caseFieldExtractOperation(fieldExtractOperation);
        if (result == null) result = caseFieldOperation(fieldExtractOperation);
        if (result == null) result = caseOperation(fieldExtractOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SELECTION_OPERATION:
      {
        SelectionOperation selectionOperation = (SelectionOperation)theEObject;
        T result = caseSelectionOperation(selectionOperation);
        if (result == null) result = caseOperation(selectionOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.SYSTEM_OPERATION:
      {
        SystemOperation systemOperation = (SystemOperation)theEObject;
        T result = caseSystemOperation(systemOperation);
        if (result == null) result = caseOperation(systemOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.USER_OPERATION:
      {
        UserOperation userOperation = (UserOperation)theEObject;
        T result = caseUserOperation(userOperation);
        if (result == null) result = caseSystemOperation(userOperation);
        if (result == null) result = caseOperation(userOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.COMPANY_OPERATION:
      {
        CompanyOperation companyOperation = (CompanyOperation)theEObject;
        T result = caseCompanyOperation(companyOperation);
        if (result == null) result = caseSystemOperation(companyOperation);
        if (result == null) result = caseOperation(companyOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.LANGUAGE_OPERATION:
      {
        LanguageOperation languageOperation = (LanguageOperation)theEObject;
        T result = caseLanguageOperation(languageOperation);
        if (result == null) result = caseSystemOperation(languageOperation);
        if (result == null) result = caseOperation(languageOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.LOCAL_CURRENCY_OPERATION:
      {
        LocalCurrencyOperation localCurrencyOperation = (LocalCurrencyOperation)theEObject;
        T result = caseLocalCurrencyOperation(localCurrencyOperation);
        if (result == null) result = caseSystemOperation(localCurrencyOperation);
        if (result == null) result = caseOperation(localCurrencyOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TOTAL_OPERATION:
      {
        TotalOperation totalOperation = (TotalOperation)theEObject;
        T result = caseTotalOperation(totalOperation);
        if (result == null) result = caseOperation(totalOperation);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.CONVERSION:
      {
        Conversion conversion = (Conversion)theEObject;
        T result = caseConversion(conversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.EXTRACT_CONVERSION:
      {
        ExtractConversion extractConversion = (ExtractConversion)theEObject;
        T result = caseExtractConversion(extractConversion);
        if (result == null) result = caseConversion(extractConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.DECRYPT_CONVERSION:
      {
        DecryptConversion decryptConversion = (DecryptConversion)theEObject;
        T result = caseDecryptConversion(decryptConversion);
        if (result == null) result = caseConversion(decryptConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.REPLACE_CONVERSION:
      {
        ReplaceConversion replaceConversion = (ReplaceConversion)theEObject;
        T result = caseReplaceConversion(replaceConversion);
        if (result == null) result = caseConversion(replaceConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.CONVERT_CONVERSION:
      {
        ConvertConversion convertConversion = (ConvertConversion)theEObject;
        T result = caseConvertConversion(convertConversion);
        if (result == null) result = caseConversion(convertConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.VALUE_CONVERSION:
      {
        ValueConversion valueConversion = (ValueConversion)theEObject;
        T result = caseValueConversion(valueConversion);
        if (result == null) result = caseConversion(valueConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.JULIAN_CONVERSION:
      {
        JulianConversion julianConversion = (JulianConversion)theEObject;
        T result = caseJulianConversion(julianConversion);
        if (result == null) result = caseConversion(julianConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BASIC_CONVERSION:
      {
        BasicConversion basicConversion = (BasicConversion)theEObject;
        T result = caseBasicConversion(basicConversion);
        if (result == null) result = caseConversion(basicConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BASIC_OCONVERSION:
      {
        BasicOConversion basicOConversion = (BasicOConversion)theEObject;
        T result = caseBasicOConversion(basicOConversion);
        if (result == null) result = caseBasicConversion(basicOConversion);
        if (result == null) result = caseConversion(basicOConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BASIC_ICONVERSION:
      {
        BasicIConversion basicIConversion = (BasicIConversion)theEObject;
        T result = caseBasicIConversion(basicIConversion);
        if (result == null) result = caseBasicConversion(basicIConversion);
        if (result == null) result = caseConversion(basicIConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.GET_FROM_CONVERSION:
      {
        GetFromConversion getFromConversion = (GetFromConversion)theEObject;
        T result = caseGetFromConversion(getFromConversion);
        if (result == null) result = caseConversion(getFromConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.RATE_CONVERSION:
      {
        RateConversion rateConversion = (RateConversion)theEObject;
        T result = caseRateConversion(rateConversion);
        if (result == null) result = caseConversion(rateConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.CALC_FIXED_RATE_CONVERSION:
      {
        CalcFixedRateConversion calcFixedRateConversion = (CalcFixedRateConversion)theEObject;
        T result = caseCalcFixedRateConversion(calcFixedRateConversion);
        if (result == null) result = caseRateConversion(calcFixedRateConversion);
        if (result == null) result = caseConversion(calcFixedRateConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.GET_FIXED_RATE_CONVERSION:
      {
        GetFixedRateConversion getFixedRateConversion = (GetFixedRateConversion)theEObject;
        T result = caseGetFixedRateConversion(getFixedRateConversion);
        if (result == null) result = caseRateConversion(getFixedRateConversion);
        if (result == null) result = caseConversion(getFixedRateConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.GET_FIXED_CURRENCY_CONVERSION:
      {
        GetFixedCurrencyConversion getFixedCurrencyConversion = (GetFixedCurrencyConversion)theEObject;
        T result = caseGetFixedCurrencyConversion(getFixedCurrencyConversion);
        if (result == null) result = caseRateConversion(getFixedCurrencyConversion);
        if (result == null) result = caseConversion(getFixedCurrencyConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.ABS_CONVERSION:
      {
        AbsConversion absConversion = (AbsConversion)theEObject;
        T result = caseAbsConversion(absConversion);
        if (result == null) result = caseConversion(absConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.MATCH_FIELD:
      {
        MatchField matchField = (MatchField)theEObject;
        T result = caseMatchField(matchField);
        if (result == null) result = caseConversion(matchField);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.CALL_ROUTINE:
      {
        CallRoutine callRoutine = (CallRoutine)theEObject;
        T result = caseCallRoutine(callRoutine);
        if (result == null) result = caseConversion(callRoutine);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.REPEAT_CONVERSION:
      {
        RepeatConversion repeatConversion = (RepeatConversion)theEObject;
        T result = caseRepeatConversion(repeatConversion);
        if (result == null) result = caseConversion(repeatConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.REPEAT_ON_NULL_CONVERSION:
      {
        RepeatOnNullConversion repeatOnNullConversion = (RepeatOnNullConversion)theEObject;
        T result = caseRepeatOnNullConversion(repeatOnNullConversion);
        if (result == null) result = caseRepeatConversion(repeatOnNullConversion);
        if (result == null) result = caseConversion(repeatOnNullConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.REPEAT_EVERY_CONVERSION:
      {
        RepeatEveryConversion repeatEveryConversion = (RepeatEveryConversion)theEObject;
        T result = caseRepeatEveryConversion(repeatEveryConversion);
        if (result == null) result = caseRepeatConversion(repeatEveryConversion);
        if (result == null) result = caseConversion(repeatEveryConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.REPEAT_SUB_CONVERSION:
      {
        RepeatSubConversion repeatSubConversion = (RepeatSubConversion)theEObject;
        T result = caseRepeatSubConversion(repeatSubConversion);
        if (result == null) result = caseRepeatConversion(repeatSubConversion);
        if (result == null) result = caseConversion(repeatSubConversion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIELD:
      {
        Field field = (Field)theEObject;
        T result = caseField(field);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.BREAK_CONDITION:
      {
        BreakCondition breakCondition = (BreakCondition)theEObject;
        T result = caseBreakCondition(breakCondition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FIELD_POSITION:
      {
        FieldPosition fieldPosition = (FieldPosition)theEObject;
        T result = caseFieldPosition(fieldPosition);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.FORMAT:
      {
        Format format = (Format)theEObject;
        T result = caseFormat(format);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.TOOL:
      {
        Tool tool = (Tool)theEObject;
        T result = caseTool(tool);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EnquiryPackage.WEB_SERVICE:
      {
        WebService webService = (WebService)theEObject;
        T result = caseWebService(webService);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Enquiry</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Enquiry</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnquiry(Enquiry object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Companies</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Companies</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompanies(Companies object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Header</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Header</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnquiryHeader(EnquiryHeader object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Target</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Target</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTarget(Target object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Target Mapping</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Target Mapping</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTargetMapping(TargetMapping object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameters</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameters</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameters(Parameters object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Drill Down</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Drill Down</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDrillDown(DrillDown object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Drill Down Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Drill Down Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDrillDownType(DrillDownType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Drill Down String Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Drill Down String Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDrillDownStringType(DrillDownStringType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Application Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Application Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseApplicationType(ApplicationType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Screen Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Screen Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseScreenType(ScreenType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEnquiryType(EnquiryType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>From Field Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>From Field Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFromFieldType(FromFieldType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Composite Screen Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Composite Screen Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompositeScreenType(CompositeScreenType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Tabbed Screen Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Tabbed Screen Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTabbedScreenType(TabbedScreenType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>View Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>View Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseViewType(ViewType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Quit SEE Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Quit SEE Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseQuitSEEType(QuitSEEType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Blank Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Blank Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBlankType(BlankType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>PW Process Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>PW Process Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePWProcessType(PWProcessType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Download Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Download Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDownloadType(DownloadType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Run Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Run Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRunType(RunType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Util Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Util Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUtilType(UtilType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Java Script Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Java Script Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJavaScriptType(JavaScriptType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Should Be Changed Type</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Should Be Changed Type</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseShouldBeChangedType(ShouldBeChangedType object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Drill Down Option</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Drill Down Option</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDrillDownOption(DrillDownOption object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Composite Screen Option</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Composite Screen Option</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompositeScreenOption(CompositeScreenOption object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Tab Option</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Tab Option</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTabOption(TabOption object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>View Option</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>View Option</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseViewOption(ViewOption object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Quit SEE Option</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Quit SEE Option</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseQuitSEEOption(QuitSEEOption object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Reference</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Reference</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReference(Reference object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Parameter</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Parameter</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseParameter(Parameter object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Selection Criteria</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Selection Criteria</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSelectionCriteria(SelectionCriteria object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Security</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Security</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSecurity(Security object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Graph</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Graph</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGraph(Graph object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Axis</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Axis</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAxis(Axis object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Dimension</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Dimension</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDimension(Dimension object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Label</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Label</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLabel(Label object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Position</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Position</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T casePosition(Position object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Legend</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Legend</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLegend(Legend object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Margins</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Margins</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMargins(Margins object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Scale</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Scale</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseScale(Scale object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRoutine(Routine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>JBC Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>JBC Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJBCRoutine(JBCRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Java Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Java Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJavaRoutine(JavaRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fixed Selection</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fixed Selection</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFixedSelection(FixedSelection object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Fixed Sort</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Fixed Sort</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFixedSort(FixedSort object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Selection Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Selection Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSelectionExpression(SelectionExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Selection</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Selection</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSelection(Selection object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>File Version</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>File Version</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFileVersion(FileVersion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOperation(Operation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Break Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Break Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBreakOperation(BreakOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Break On Change Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Break On Change Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBreakOnChangeOperation(BreakOnChangeOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Break Line Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Break Line Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBreakLineOperation(BreakLineOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Calc Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Calc Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCalcOperation(CalcOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Constant Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Constant Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConstantOperation(ConstantOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Label Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Label Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLabelOperation(LabelOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Date Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Date Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDateOperation(DateOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Decision Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Decision Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDecisionOperation(DecisionOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Descriptor Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Descriptor Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDescriptorOperation(DescriptorOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Today Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Today Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTodayOperation(TodayOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>LWD Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>LWD Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLWDOperation(LWDOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>NWD Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>NWD Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseNWDOperation(NWDOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFieldOperation(FieldOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Application Field Name Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Application Field Name Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseApplicationFieldNameOperation(ApplicationFieldNameOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field Number Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field Number Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFieldNumberOperation(FieldNumberOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field Extract Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field Extract Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFieldExtractOperation(FieldExtractOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Selection Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Selection Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSelectionOperation(SelectionOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>System Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>System Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseSystemOperation(SystemOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>User Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>User Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUserOperation(UserOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Company Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Company Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCompanyOperation(CompanyOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Language Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Language Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLanguageOperation(LanguageOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Local Currency Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Local Currency Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLocalCurrencyOperation(LocalCurrencyOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Total Operation</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Total Operation</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTotalOperation(TotalOperation object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConversion(Conversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Extract Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Extract Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseExtractConversion(ExtractConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Decrypt Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Decrypt Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDecryptConversion(DecryptConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Replace Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Replace Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseReplaceConversion(ReplaceConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Convert Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Convert Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseConvertConversion(ConvertConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Value Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Value Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseValueConversion(ValueConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Julian Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Julian Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJulianConversion(JulianConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Basic Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Basic Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBasicConversion(BasicConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Basic OConversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Basic OConversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBasicOConversion(BasicOConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Basic IConversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Basic IConversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBasicIConversion(BasicIConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Get From Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Get From Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGetFromConversion(GetFromConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rate Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rate Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRateConversion(RateConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Calc Fixed Rate Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Calc Fixed Rate Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCalcFixedRateConversion(CalcFixedRateConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Get Fixed Rate Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Get Fixed Rate Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGetFixedRateConversion(GetFixedRateConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Get Fixed Currency Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Get Fixed Currency Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGetFixedCurrencyConversion(GetFixedCurrencyConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Abs Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Abs Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAbsConversion(AbsConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Match Field</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Match Field</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMatchField(MatchField object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Call Routine</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Call Routine</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseCallRoutine(CallRoutine object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Repeat Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Repeat Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRepeatConversion(RepeatConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Repeat On Null Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Repeat On Null Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRepeatOnNullConversion(RepeatOnNullConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Repeat Every Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Repeat Every Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRepeatEveryConversion(RepeatEveryConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Repeat Sub Conversion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Repeat Sub Conversion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRepeatSubConversion(RepeatSubConversion object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseField(Field object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Break Condition</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Break Condition</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBreakCondition(BreakCondition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Field Position</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Field Position</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFieldPosition(FieldPosition object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Format</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Format</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseFormat(Format object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Tool</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Tool</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTool(Tool object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Web Service</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Web Service</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseWebService(WebService object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //EnquirySwitch
