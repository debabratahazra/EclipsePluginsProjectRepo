/**
 */
package com.odcgroup.t24.enquiry.enquiry.util;

import com.odcgroup.t24.enquiry.enquiry.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.odcgroup.t24.enquiry.enquiry.EnquiryPackage
 * @generated
 */
public class EnquiryAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static EnquiryPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EnquiryAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = EnquiryPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EnquirySwitch<Adapter> modelSwitch =
    new EnquirySwitch<Adapter>()
    {
      @Override
      public Adapter caseEnquiry(Enquiry object)
      {
        return createEnquiryAdapter();
      }
      @Override
      public Adapter caseCompanies(Companies object)
      {
        return createCompaniesAdapter();
      }
      @Override
      public Adapter caseEnquiryHeader(EnquiryHeader object)
      {
        return createEnquiryHeaderAdapter();
      }
      @Override
      public Adapter caseTarget(Target object)
      {
        return createTargetAdapter();
      }
      @Override
      public Adapter caseTargetMapping(TargetMapping object)
      {
        return createTargetMappingAdapter();
      }
      @Override
      public Adapter caseParameters(Parameters object)
      {
        return createParametersAdapter();
      }
      @Override
      public Adapter caseDrillDown(DrillDown object)
      {
        return createDrillDownAdapter();
      }
      @Override
      public Adapter caseDrillDownType(DrillDownType object)
      {
        return createDrillDownTypeAdapter();
      }
      @Override
      public Adapter caseDrillDownStringType(DrillDownStringType object)
      {
        return createDrillDownStringTypeAdapter();
      }
      @Override
      public Adapter caseApplicationType(ApplicationType object)
      {
        return createApplicationTypeAdapter();
      }
      @Override
      public Adapter caseScreenType(ScreenType object)
      {
        return createScreenTypeAdapter();
      }
      @Override
      public Adapter caseEnquiryType(EnquiryType object)
      {
        return createEnquiryTypeAdapter();
      }
      @Override
      public Adapter caseFromFieldType(FromFieldType object)
      {
        return createFromFieldTypeAdapter();
      }
      @Override
      public Adapter caseCompositeScreenType(CompositeScreenType object)
      {
        return createCompositeScreenTypeAdapter();
      }
      @Override
      public Adapter caseTabbedScreenType(TabbedScreenType object)
      {
        return createTabbedScreenTypeAdapter();
      }
      @Override
      public Adapter caseViewType(ViewType object)
      {
        return createViewTypeAdapter();
      }
      @Override
      public Adapter caseQuitSEEType(QuitSEEType object)
      {
        return createQuitSEETypeAdapter();
      }
      @Override
      public Adapter caseBlankType(BlankType object)
      {
        return createBlankTypeAdapter();
      }
      @Override
      public Adapter casePWProcessType(PWProcessType object)
      {
        return createPWProcessTypeAdapter();
      }
      @Override
      public Adapter caseDownloadType(DownloadType object)
      {
        return createDownloadTypeAdapter();
      }
      @Override
      public Adapter caseRunType(RunType object)
      {
        return createRunTypeAdapter();
      }
      @Override
      public Adapter caseUtilType(UtilType object)
      {
        return createUtilTypeAdapter();
      }
      @Override
      public Adapter caseJavaScriptType(JavaScriptType object)
      {
        return createJavaScriptTypeAdapter();
      }
      @Override
      public Adapter caseShouldBeChangedType(ShouldBeChangedType object)
      {
        return createShouldBeChangedTypeAdapter();
      }
      @Override
      public Adapter caseDrillDownOption(DrillDownOption object)
      {
        return createDrillDownOptionAdapter();
      }
      @Override
      public Adapter caseCompositeScreenOption(CompositeScreenOption object)
      {
        return createCompositeScreenOptionAdapter();
      }
      @Override
      public Adapter caseTabOption(TabOption object)
      {
        return createTabOptionAdapter();
      }
      @Override
      public Adapter caseViewOption(ViewOption object)
      {
        return createViewOptionAdapter();
      }
      @Override
      public Adapter caseQuitSEEOption(QuitSEEOption object)
      {
        return createQuitSEEOptionAdapter();
      }
      @Override
      public Adapter caseReference(Reference object)
      {
        return createReferenceAdapter();
      }
      @Override
      public Adapter caseParameter(Parameter object)
      {
        return createParameterAdapter();
      }
      @Override
      public Adapter caseSelectionCriteria(SelectionCriteria object)
      {
        return createSelectionCriteriaAdapter();
      }
      @Override
      public Adapter caseSecurity(Security object)
      {
        return createSecurityAdapter();
      }
      @Override
      public Adapter caseGraph(Graph object)
      {
        return createGraphAdapter();
      }
      @Override
      public Adapter caseAxis(Axis object)
      {
        return createAxisAdapter();
      }
      @Override
      public Adapter caseDimension(Dimension object)
      {
        return createDimensionAdapter();
      }
      @Override
      public Adapter caseLabel(Label object)
      {
        return createLabelAdapter();
      }
      @Override
      public Adapter casePosition(Position object)
      {
        return createPositionAdapter();
      }
      @Override
      public Adapter caseLegend(Legend object)
      {
        return createLegendAdapter();
      }
      @Override
      public Adapter caseMargins(Margins object)
      {
        return createMarginsAdapter();
      }
      @Override
      public Adapter caseScale(Scale object)
      {
        return createScaleAdapter();
      }
      @Override
      public Adapter caseRoutine(Routine object)
      {
        return createRoutineAdapter();
      }
      @Override
      public Adapter caseJBCRoutine(JBCRoutine object)
      {
        return createJBCRoutineAdapter();
      }
      @Override
      public Adapter caseJavaRoutine(JavaRoutine object)
      {
        return createJavaRoutineAdapter();
      }
      @Override
      public Adapter caseFixedSelection(FixedSelection object)
      {
        return createFixedSelectionAdapter();
      }
      @Override
      public Adapter caseFixedSort(FixedSort object)
      {
        return createFixedSortAdapter();
      }
      @Override
      public Adapter caseSelectionExpression(SelectionExpression object)
      {
        return createSelectionExpressionAdapter();
      }
      @Override
      public Adapter caseSelection(Selection object)
      {
        return createSelectionAdapter();
      }
      @Override
      public Adapter caseFileVersion(FileVersion object)
      {
        return createFileVersionAdapter();
      }
      @Override
      public Adapter caseOperation(Operation object)
      {
        return createOperationAdapter();
      }
      @Override
      public Adapter caseBreakOperation(BreakOperation object)
      {
        return createBreakOperationAdapter();
      }
      @Override
      public Adapter caseBreakOnChangeOperation(BreakOnChangeOperation object)
      {
        return createBreakOnChangeOperationAdapter();
      }
      @Override
      public Adapter caseBreakLineOperation(BreakLineOperation object)
      {
        return createBreakLineOperationAdapter();
      }
      @Override
      public Adapter caseCalcOperation(CalcOperation object)
      {
        return createCalcOperationAdapter();
      }
      @Override
      public Adapter caseConstantOperation(ConstantOperation object)
      {
        return createConstantOperationAdapter();
      }
      @Override
      public Adapter caseLabelOperation(LabelOperation object)
      {
        return createLabelOperationAdapter();
      }
      @Override
      public Adapter caseDateOperation(DateOperation object)
      {
        return createDateOperationAdapter();
      }
      @Override
      public Adapter caseDecisionOperation(DecisionOperation object)
      {
        return createDecisionOperationAdapter();
      }
      @Override
      public Adapter caseDescriptorOperation(DescriptorOperation object)
      {
        return createDescriptorOperationAdapter();
      }
      @Override
      public Adapter caseTodayOperation(TodayOperation object)
      {
        return createTodayOperationAdapter();
      }
      @Override
      public Adapter caseLWDOperation(LWDOperation object)
      {
        return createLWDOperationAdapter();
      }
      @Override
      public Adapter caseNWDOperation(NWDOperation object)
      {
        return createNWDOperationAdapter();
      }
      @Override
      public Adapter caseFieldOperation(FieldOperation object)
      {
        return createFieldOperationAdapter();
      }
      @Override
      public Adapter caseApplicationFieldNameOperation(ApplicationFieldNameOperation object)
      {
        return createApplicationFieldNameOperationAdapter();
      }
      @Override
      public Adapter caseFieldNumberOperation(FieldNumberOperation object)
      {
        return createFieldNumberOperationAdapter();
      }
      @Override
      public Adapter caseFieldExtractOperation(FieldExtractOperation object)
      {
        return createFieldExtractOperationAdapter();
      }
      @Override
      public Adapter caseSelectionOperation(SelectionOperation object)
      {
        return createSelectionOperationAdapter();
      }
      @Override
      public Adapter caseSystemOperation(SystemOperation object)
      {
        return createSystemOperationAdapter();
      }
      @Override
      public Adapter caseUserOperation(UserOperation object)
      {
        return createUserOperationAdapter();
      }
      @Override
      public Adapter caseCompanyOperation(CompanyOperation object)
      {
        return createCompanyOperationAdapter();
      }
      @Override
      public Adapter caseLanguageOperation(LanguageOperation object)
      {
        return createLanguageOperationAdapter();
      }
      @Override
      public Adapter caseLocalCurrencyOperation(LocalCurrencyOperation object)
      {
        return createLocalCurrencyOperationAdapter();
      }
      @Override
      public Adapter caseTotalOperation(TotalOperation object)
      {
        return createTotalOperationAdapter();
      }
      @Override
      public Adapter caseConversion(Conversion object)
      {
        return createConversionAdapter();
      }
      @Override
      public Adapter caseExtractConversion(ExtractConversion object)
      {
        return createExtractConversionAdapter();
      }
      @Override
      public Adapter caseDecryptConversion(DecryptConversion object)
      {
        return createDecryptConversionAdapter();
      }
      @Override
      public Adapter caseReplaceConversion(ReplaceConversion object)
      {
        return createReplaceConversionAdapter();
      }
      @Override
      public Adapter caseConvertConversion(ConvertConversion object)
      {
        return createConvertConversionAdapter();
      }
      @Override
      public Adapter caseValueConversion(ValueConversion object)
      {
        return createValueConversionAdapter();
      }
      @Override
      public Adapter caseJulianConversion(JulianConversion object)
      {
        return createJulianConversionAdapter();
      }
      @Override
      public Adapter caseBasicConversion(BasicConversion object)
      {
        return createBasicConversionAdapter();
      }
      @Override
      public Adapter caseBasicOConversion(BasicOConversion object)
      {
        return createBasicOConversionAdapter();
      }
      @Override
      public Adapter caseBasicIConversion(BasicIConversion object)
      {
        return createBasicIConversionAdapter();
      }
      @Override
      public Adapter caseGetFromConversion(GetFromConversion object)
      {
        return createGetFromConversionAdapter();
      }
      @Override
      public Adapter caseRateConversion(RateConversion object)
      {
        return createRateConversionAdapter();
      }
      @Override
      public Adapter caseCalcFixedRateConversion(CalcFixedRateConversion object)
      {
        return createCalcFixedRateConversionAdapter();
      }
      @Override
      public Adapter caseGetFixedRateConversion(GetFixedRateConversion object)
      {
        return createGetFixedRateConversionAdapter();
      }
      @Override
      public Adapter caseGetFixedCurrencyConversion(GetFixedCurrencyConversion object)
      {
        return createGetFixedCurrencyConversionAdapter();
      }
      @Override
      public Adapter caseAbsConversion(AbsConversion object)
      {
        return createAbsConversionAdapter();
      }
      @Override
      public Adapter caseMatchField(MatchField object)
      {
        return createMatchFieldAdapter();
      }
      @Override
      public Adapter caseCallRoutine(CallRoutine object)
      {
        return createCallRoutineAdapter();
      }
      @Override
      public Adapter caseRepeatConversion(RepeatConversion object)
      {
        return createRepeatConversionAdapter();
      }
      @Override
      public Adapter caseRepeatOnNullConversion(RepeatOnNullConversion object)
      {
        return createRepeatOnNullConversionAdapter();
      }
      @Override
      public Adapter caseRepeatEveryConversion(RepeatEveryConversion object)
      {
        return createRepeatEveryConversionAdapter();
      }
      @Override
      public Adapter caseRepeatSubConversion(RepeatSubConversion object)
      {
        return createRepeatSubConversionAdapter();
      }
      @Override
      public Adapter caseField(Field object)
      {
        return createFieldAdapter();
      }
      @Override
      public Adapter caseBreakCondition(BreakCondition object)
      {
        return createBreakConditionAdapter();
      }
      @Override
      public Adapter caseFieldPosition(FieldPosition object)
      {
        return createFieldPositionAdapter();
      }
      @Override
      public Adapter caseFormat(Format object)
      {
        return createFormatAdapter();
      }
      @Override
      public Adapter caseTool(Tool object)
      {
        return createToolAdapter();
      }
      @Override
      public Adapter caseWebService(WebService object)
      {
        return createWebServiceAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Enquiry <em>Enquiry</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Enquiry
   * @generated
   */
  public Adapter createEnquiryAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Companies <em>Companies</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Companies
   * @generated
   */
  public Adapter createCompaniesAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryHeader <em>Header</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryHeader
   * @generated
   */
  public Adapter createEnquiryHeaderAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Target <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Target
   * @generated
   */
  public Adapter createTargetAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.TargetMapping <em>Target Mapping</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.TargetMapping
   * @generated
   */
  public Adapter createTargetMappingAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Parameters <em>Parameters</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameters
   * @generated
   */
  public Adapter createParametersAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDown <em>Drill Down</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDown
   * @generated
   */
  public Adapter createDrillDownAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownType <em>Drill Down Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownType
   * @generated
   */
  public Adapter createDrillDownTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownStringType <em>Drill Down String Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownStringType
   * @generated
   */
  public Adapter createDrillDownStringTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ApplicationType <em>Application Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ApplicationType
   * @generated
   */
  public Adapter createApplicationTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ScreenType <em>Screen Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ScreenType
   * @generated
   */
  public Adapter createScreenTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.EnquiryType <em>Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.EnquiryType
   * @generated
   */
  public Adapter createEnquiryTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FromFieldType <em>From Field Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FromFieldType
   * @generated
   */
  public Adapter createFromFieldTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenType <em>Composite Screen Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenType
   * @generated
   */
  public Adapter createCompositeScreenTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.TabbedScreenType <em>Tabbed Screen Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.TabbedScreenType
   * @generated
   */
  public Adapter createTabbedScreenTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ViewType <em>View Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ViewType
   * @generated
   */
  public Adapter createViewTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.QuitSEEType <em>Quit SEE Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.QuitSEEType
   * @generated
   */
  public Adapter createQuitSEETypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BlankType <em>Blank Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BlankType
   * @generated
   */
  public Adapter createBlankTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.PWProcessType <em>PW Process Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.PWProcessType
   * @generated
   */
  public Adapter createPWProcessTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DownloadType <em>Download Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DownloadType
   * @generated
   */
  public Adapter createDownloadTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.RunType <em>Run Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.RunType
   * @generated
   */
  public Adapter createRunTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.UtilType <em>Util Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.UtilType
   * @generated
   */
  public Adapter createUtilTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.JavaScriptType <em>Java Script Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.JavaScriptType
   * @generated
   */
  public Adapter createJavaScriptTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType <em>Should Be Changed Type</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType
   * @generated
   */
  public Adapter createShouldBeChangedTypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DrillDownOption <em>Drill Down Option</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DrillDownOption
   * @generated
   */
  public Adapter createDrillDownOptionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption <em>Composite Screen Option</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption
   * @generated
   */
  public Adapter createCompositeScreenOptionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.TabOption <em>Tab Option</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.TabOption
   * @generated
   */
  public Adapter createTabOptionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ViewOption <em>View Option</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ViewOption
   * @generated
   */
  public Adapter createViewOptionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.QuitSEEOption <em>Quit SEE Option</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.QuitSEEOption
   * @generated
   */
  public Adapter createQuitSEEOptionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Reference <em>Reference</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Reference
   * @generated
   */
  public Adapter createReferenceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Parameter
   * @generated
   */
  public Adapter createParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.SelectionCriteria <em>Selection Criteria</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionCriteria
   * @generated
   */
  public Adapter createSelectionCriteriaAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Security <em>Security</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Security
   * @generated
   */
  public Adapter createSecurityAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Graph <em>Graph</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Graph
   * @generated
   */
  public Adapter createGraphAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Axis <em>Axis</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Axis
   * @generated
   */
  public Adapter createAxisAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Dimension <em>Dimension</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Dimension
   * @generated
   */
  public Adapter createDimensionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Label <em>Label</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Label
   * @generated
   */
  public Adapter createLabelAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Position <em>Position</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Position
   * @generated
   */
  public Adapter createPositionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Legend <em>Legend</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Legend
   * @generated
   */
  public Adapter createLegendAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Margins <em>Margins</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Margins
   * @generated
   */
  public Adapter createMarginsAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Scale <em>Scale</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Scale
   * @generated
   */
  public Adapter createScaleAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Routine <em>Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Routine
   * @generated
   */
  public Adapter createRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.JBCRoutine <em>JBC Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.JBCRoutine
   * @generated
   */
  public Adapter createJBCRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.JavaRoutine <em>Java Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.JavaRoutine
   * @generated
   */
  public Adapter createJavaRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FixedSelection <em>Fixed Selection</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSelection
   * @generated
   */
  public Adapter createFixedSelectionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FixedSort <em>Fixed Sort</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FixedSort
   * @generated
   */
  public Adapter createFixedSortAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.SelectionExpression <em>Selection Expression</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionExpression
   * @generated
   */
  public Adapter createSelectionExpressionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Selection <em>Selection</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Selection
   * @generated
   */
  public Adapter createSelectionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FileVersion <em>File Version</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FileVersion
   * @generated
   */
  public Adapter createFileVersionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Operation <em>Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Operation
   * @generated
   */
  public Adapter createOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BreakOperation <em>Break Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakOperation
   * @generated
   */
  public Adapter createBreakOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation <em>Break On Change Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation
   * @generated
   */
  public Adapter createBreakOnChangeOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BreakLineOperation <em>Break Line Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakLineOperation
   * @generated
   */
  public Adapter createBreakLineOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.CalcOperation <em>Calc Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.CalcOperation
   * @generated
   */
  public Adapter createCalcOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ConstantOperation <em>Constant Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ConstantOperation
   * @generated
   */
  public Adapter createConstantOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.LabelOperation <em>Label Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.LabelOperation
   * @generated
   */
  public Adapter createLabelOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DateOperation <em>Date Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DateOperation
   * @generated
   */
  public Adapter createDateOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DecisionOperation <em>Decision Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DecisionOperation
   * @generated
   */
  public Adapter createDecisionOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DescriptorOperation <em>Descriptor Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DescriptorOperation
   * @generated
   */
  public Adapter createDescriptorOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.TodayOperation <em>Today Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.TodayOperation
   * @generated
   */
  public Adapter createTodayOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.LWDOperation <em>LWD Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.LWDOperation
   * @generated
   */
  public Adapter createLWDOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.NWDOperation <em>NWD Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.NWDOperation
   * @generated
   */
  public Adapter createNWDOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FieldOperation <em>Field Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldOperation
   * @generated
   */
  public Adapter createFieldOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation <em>Application Field Name Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation
   * @generated
   */
  public Adapter createApplicationFieldNameOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation <em>Field Number Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation
   * @generated
   */
  public Adapter createFieldNumberOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation <em>Field Extract Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation
   * @generated
   */
  public Adapter createFieldExtractOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.SelectionOperation <em>Selection Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.SelectionOperation
   * @generated
   */
  public Adapter createSelectionOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.SystemOperation <em>System Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.SystemOperation
   * @generated
   */
  public Adapter createSystemOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.UserOperation <em>User Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.UserOperation
   * @generated
   */
  public Adapter createUserOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.CompanyOperation <em>Company Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.CompanyOperation
   * @generated
   */
  public Adapter createCompanyOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.LanguageOperation <em>Language Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.LanguageOperation
   * @generated
   */
  public Adapter createLanguageOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.LocalCurrencyOperation <em>Local Currency Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.LocalCurrencyOperation
   * @generated
   */
  public Adapter createLocalCurrencyOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.TotalOperation <em>Total Operation</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.TotalOperation
   * @generated
   */
  public Adapter createTotalOperationAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Conversion <em>Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Conversion
   * @generated
   */
  public Adapter createConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ExtractConversion <em>Extract Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ExtractConversion
   * @generated
   */
  public Adapter createExtractConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.DecryptConversion <em>Decrypt Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.DecryptConversion
   * @generated
   */
  public Adapter createDecryptConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ReplaceConversion <em>Replace Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ReplaceConversion
   * @generated
   */
  public Adapter createReplaceConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ConvertConversion <em>Convert Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ConvertConversion
   * @generated
   */
  public Adapter createConvertConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.ValueConversion <em>Value Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.ValueConversion
   * @generated
   */
  public Adapter createValueConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.JulianConversion <em>Julian Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.JulianConversion
   * @generated
   */
  public Adapter createJulianConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BasicConversion <em>Basic Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicConversion
   * @generated
   */
  public Adapter createBasicConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BasicOConversion <em>Basic OConversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicOConversion
   * @generated
   */
  public Adapter createBasicOConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BasicIConversion <em>Basic IConversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BasicIConversion
   * @generated
   */
  public Adapter createBasicIConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.GetFromConversion <em>Get From Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFromConversion
   * @generated
   */
  public Adapter createGetFromConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.RateConversion <em>Rate Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.RateConversion
   * @generated
   */
  public Adapter createRateConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion <em>Calc Fixed Rate Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion
   * @generated
   */
  public Adapter createCalcFixedRateConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.GetFixedRateConversion <em>Get Fixed Rate Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFixedRateConversion
   * @generated
   */
  public Adapter createGetFixedRateConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.GetFixedCurrencyConversion <em>Get Fixed Currency Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.GetFixedCurrencyConversion
   * @generated
   */
  public Adapter createGetFixedCurrencyConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.AbsConversion <em>Abs Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.AbsConversion
   * @generated
   */
  public Adapter createAbsConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.MatchField <em>Match Field</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.MatchField
   * @generated
   */
  public Adapter createMatchFieldAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.CallRoutine <em>Call Routine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.CallRoutine
   * @generated
   */
  public Adapter createCallRoutineAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatConversion <em>Repeat Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatConversion
   * @generated
   */
  public Adapter createRepeatConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion <em>Repeat On Null Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion
   * @generated
   */
  public Adapter createRepeatOnNullConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion <em>Repeat Every Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion
   * @generated
   */
  public Adapter createRepeatEveryConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion <em>Repeat Sub Conversion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion
   * @generated
   */
  public Adapter createRepeatSubConversionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Field <em>Field</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Field
   * @generated
   */
  public Adapter createFieldAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.BreakCondition <em>Break Condition</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.BreakCondition
   * @generated
   */
  public Adapter createBreakConditionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.FieldPosition <em>Field Position</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.FieldPosition
   * @generated
   */
  public Adapter createFieldPositionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Format <em>Format</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Format
   * @generated
   */
  public Adapter createFormatAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.Tool <em>Tool</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.Tool
   * @generated
   */
  public Adapter createToolAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link com.odcgroup.t24.enquiry.enquiry.WebService <em>Web Service</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see com.odcgroup.t24.enquiry.enquiry.WebService
   * @generated
   */
  public Adapter createWebServiceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //EnquiryAdapterFactory
