package com.odcgroup.t24.enquiry.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.odcgroup.t24.enquiry.enquiry.AbsConversion;
import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.ApplicationType;
import com.odcgroup.t24.enquiry.enquiry.Axis;
import com.odcgroup.t24.enquiry.enquiry.BasicIConversion;
import com.odcgroup.t24.enquiry.enquiry.BasicOConversion;
import com.odcgroup.t24.enquiry.enquiry.BlankType;
import com.odcgroup.t24.enquiry.enquiry.BreakCondition;
import com.odcgroup.t24.enquiry.enquiry.BreakLineOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion;
import com.odcgroup.t24.enquiry.enquiry.CalcOperation;
import com.odcgroup.t24.enquiry.enquiry.CallRoutine;
import com.odcgroup.t24.enquiry.enquiry.Companies;
import com.odcgroup.t24.enquiry.enquiry.CompanyOperation;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenOption;
import com.odcgroup.t24.enquiry.enquiry.CompositeScreenType;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.ConvertConversion;
import com.odcgroup.t24.enquiry.enquiry.DecisionOperation;
import com.odcgroup.t24.enquiry.enquiry.DecryptConversion;
import com.odcgroup.t24.enquiry.enquiry.DescriptorOperation;
import com.odcgroup.t24.enquiry.enquiry.Dimension;
import com.odcgroup.t24.enquiry.enquiry.DownloadType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.EnquiryType;
import com.odcgroup.t24.enquiry.enquiry.ExtractConversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldPosition;
import com.odcgroup.t24.enquiry.enquiry.FileVersion;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;
import com.odcgroup.t24.enquiry.enquiry.Format;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
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
import com.odcgroup.t24.enquiry.enquiry.PWProcessType;
import com.odcgroup.t24.enquiry.enquiry.Parameter;
import com.odcgroup.t24.enquiry.enquiry.Parameters;
import com.odcgroup.t24.enquiry.enquiry.Position;
import com.odcgroup.t24.enquiry.enquiry.QuitSEEOption;
import com.odcgroup.t24.enquiry.enquiry.QuitSEEType;
import com.odcgroup.t24.enquiry.enquiry.Reference;
import com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion;
import com.odcgroup.t24.enquiry.enquiry.ReplaceConversion;
import com.odcgroup.t24.enquiry.enquiry.RunType;
import com.odcgroup.t24.enquiry.enquiry.Scale;
import com.odcgroup.t24.enquiry.enquiry.ScreenType;
import com.odcgroup.t24.enquiry.enquiry.Security;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperation;
import com.odcgroup.t24.enquiry.enquiry.ShouldBeChangedType;
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
import com.odcgroup.t24.enquiry.services.EnquiryGrammarAccess;
import com.odcgroup.translation.serializer.TranslationDslSemanticSequencer;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.TranslationDslPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class EnquirySemanticSequencer extends TranslationDslSemanticSequencer {

	@Inject
	private EnquiryGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == EnquiryPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case EnquiryPackage.ABS_CONVERSION:
				if(context == grammarAccess.getAbsConversionRule() ||
				   context == grammarAccess.getConversionRule()) {
					sequence_AbsConversion(context, (AbsConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.APPLICATION_FIELD_NAME_OPERATION:
				if(context == grammarAccess.getApplicationFieldNameOperationRule() ||
				   context == grammarAccess.getFieldOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_ApplicationFieldNameOperation(context, (ApplicationFieldNameOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.APPLICATION_TYPE:
				if(context == grammarAccess.getApplicationTypeRule() ||
				   context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule()) {
					sequence_ApplicationType(context, (ApplicationType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.AXIS:
				if(context == grammarAccess.getAxisRule()) {
					sequence_Axis(context, (Axis) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.BASIC_ICONVERSION:
				if(context == grammarAccess.getBasicConversionRule() ||
				   context == grammarAccess.getBasicIConversionRule() ||
				   context == grammarAccess.getConversionRule()) {
					sequence_BasicIConversion(context, (BasicIConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.BASIC_OCONVERSION:
				if(context == grammarAccess.getBasicConversionRule() ||
				   context == grammarAccess.getBasicOConversionRule() ||
				   context == grammarAccess.getConversionRule()) {
					sequence_BasicOConversion(context, (BasicOConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.BLANK_TYPE:
				if(context == grammarAccess.getBlankTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule()) {
					sequence_BlankType(context, (BlankType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.BREAK_CONDITION:
				if(context == grammarAccess.getBreakConditionRule()) {
					sequence_BreakCondition(context, (BreakCondition) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.BREAK_LINE_OPERATION:
				if(context == grammarAccess.getBreakLineOperationRule() ||
				   context == grammarAccess.getBreakOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_BreakLineOperation(context, (BreakLineOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.BREAK_ON_CHANGE_OPERATION:
				if(context == grammarAccess.getBreakOnChangeOperationRule() ||
				   context == grammarAccess.getBreakOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_BreakOnChangeOperation(context, (BreakOnChangeOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.CALC_FIXED_RATE_CONVERSION:
				if(context == grammarAccess.getCalcFixedRateConversionRule() ||
				   context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getRateConversionRule()) {
					sequence_CalcFixedRateConversion(context, (CalcFixedRateConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.CALC_OPERATION:
				if(context == grammarAccess.getCalcOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_CalcOperation(context, (CalcOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.CALL_ROUTINE:
				if(context == grammarAccess.getCallRoutineRule() ||
				   context == grammarAccess.getConversionRule()) {
					sequence_CallRoutine(context, (CallRoutine) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.COMPANIES:
				if(context == grammarAccess.getCompaniesRule()) {
					sequence_Companies(context, (Companies) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.COMPANY_OPERATION:
				if(context == grammarAccess.getCompanyOperationRule() ||
				   context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getSystemOperationRule()) {
					sequence_CompanyOperation(context, (CompanyOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.COMPOSITE_SCREEN_OPTION:
				if(context == grammarAccess.getCompositeScreenOptionRule() ||
				   context == grammarAccess.getDrillDownOptionRule()) {
					sequence_CompositeScreenOption(context, (CompositeScreenOption) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.COMPOSITE_SCREEN_TYPE:
				if(context == grammarAccess.getCompositeScreenTypeRule() ||
				   context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule()) {
					sequence_CompositeScreenType(context, (CompositeScreenType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.CONSTANT_OPERATION:
				if(context == grammarAccess.getConstantOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_ConstantOperation(context, (ConstantOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.CONVERT_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getConvertConversionRule()) {
					sequence_ConvertConversion(context, (ConvertConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.DECISION_OPERATION:
				if(context == grammarAccess.getDecisionOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_DecisionOperation(context, (DecisionOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.DECRYPT_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getDecryptConversionRule()) {
					sequence_DecryptConversion(context, (DecryptConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.DESCRIPTOR_OPERATION:
				if(context == grammarAccess.getDescriptorOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_DescriptorOperation(context, (DescriptorOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.DIMENSION:
				if(context == grammarAccess.getDimensionRule()) {
					sequence_Dimension(context, (Dimension) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.DOWNLOAD_TYPE:
				if(context == grammarAccess.getDownloadTypeRule() ||
				   context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule()) {
					sequence_DownloadType(context, (DownloadType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.DRILL_DOWN:
				if(context == grammarAccess.getDrillDownRule()) {
					sequence_DrillDown(context, (DrillDown) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.ENQUIRY:
				if(context == grammarAccess.getEnquiryRule()) {
					sequence_Enquiry(context, (Enquiry) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.ENQUIRY_HEADER:
				if(context == grammarAccess.getEnquiryHeaderRule()) {
					sequence_EnquiryHeader(context, (EnquiryHeader) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.ENQUIRY_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getEnquiryTypeRule()) {
					sequence_EnquiryType(context, (EnquiryType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.EXTRACT_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getExtractConversionRule()) {
					sequence_ExtractConversion(context, (ExtractConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FIELD:
				if(context == grammarAccess.getFieldRule()) {
					sequence_Field(context, (Field) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FIELD_EXTRACT_OPERATION:
				if(context == grammarAccess.getFieldExtractOperationRule() ||
				   context == grammarAccess.getFieldOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_FieldExtractOperation(context, (FieldExtractOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FIELD_NUMBER_OPERATION:
				if(context == grammarAccess.getFieldNumberOperationRule() ||
				   context == grammarAccess.getFieldOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_FieldNumberOperation(context, (FieldNumberOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FIELD_POSITION:
				if(context == grammarAccess.getFieldPositionRule()) {
					sequence_FieldPosition(context, (FieldPosition) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FILE_VERSION:
				if(context == grammarAccess.getFileVersionRule()) {
					sequence_FileVersion(context, (FileVersion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FIXED_SELECTION:
				if(context == grammarAccess.getFixedSelectionRule()) {
					sequence_FixedSelection(context, (FixedSelection) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FIXED_SORT:
				if(context == grammarAccess.getFixedSortRule()) {
					sequence_FixedSort(context, (FixedSort) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FORMAT:
				if(context == grammarAccess.getFormatRule()) {
					sequence_Format(context, (Format) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.FROM_FIELD_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getFromFieldTypeRule()) {
					sequence_FromFieldType(context, (FromFieldType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.GET_FIXED_CURRENCY_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getGetFixedCurrencyConversionRule() ||
				   context == grammarAccess.getRateConversionRule()) {
					sequence_GetFixedCurrencyConversion(context, (GetFixedCurrencyConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.GET_FIXED_RATE_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getGetFixedRateConversionRule() ||
				   context == grammarAccess.getRateConversionRule()) {
					sequence_GetFixedRateConversion(context, (GetFixedRateConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.GET_FROM_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getGetFromConversionRule()) {
					sequence_GetFromConversion(context, (GetFromConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.GRAPH:
				if(context == grammarAccess.getGraphRule()) {
					sequence_Graph(context, (Graph) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.JBC_ROUTINE:
				if(context == grammarAccess.getJBCRoutineRule() ||
				   context == grammarAccess.getRoutineRule()) {
					sequence_JBCRoutine(context, (JBCRoutine) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.JAVA_ROUTINE:
				if(context == grammarAccess.getJavaRoutineRule() ||
				   context == grammarAccess.getRoutineRule()) {
					sequence_JavaRoutine(context, (JavaRoutine) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.JAVA_SCRIPT_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getJavaScriptTypeRule()) {
					sequence_JavaScriptType(context, (JavaScriptType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.JULIAN_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getJulianConversionRule()) {
					sequence_JulianConversion(context, (JulianConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.LWD_OPERATION:
				if(context == grammarAccess.getDateOperationRule() ||
				   context == grammarAccess.getLWDOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_LWDOperation(context, (LWDOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.LABEL:
				if(context == grammarAccess.getLabelRule()) {
					sequence_Label(context, (Label) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.LABEL_OPERATION:
				if(context == grammarAccess.getLabelOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_LabelOperation(context, (LabelOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.LANGUAGE_OPERATION:
				if(context == grammarAccess.getLanguageOperationRule() ||
				   context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getSystemOperationRule()) {
					sequence_LanguageOperation(context, (LanguageOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.LEGEND:
				if(context == grammarAccess.getLegendRule()) {
					sequence_Legend(context, (Legend) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.LOCAL_CURRENCY_OPERATION:
				if(context == grammarAccess.getLocalCurrencyOperationRule() ||
				   context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getSystemOperationRule()) {
					sequence_LocalCurrencyOperation(context, (LocalCurrencyOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.MARGINS:
				if(context == grammarAccess.getMarginsRule()) {
					sequence_Margins(context, (Margins) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.MATCH_FIELD:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getMatchFieldRule()) {
					sequence_MatchField(context, (MatchField) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.NWD_OPERATION:
				if(context == grammarAccess.getDateOperationRule() ||
				   context == grammarAccess.getNWDOperationRule() ||
				   context == grammarAccess.getOperationRule()) {
					sequence_NWDOperation(context, (NWDOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.PW_PROCESS_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getPWProcessTypeRule()) {
					sequence_PWProcessType(context, (PWProcessType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.PARAMETER:
				if(context == grammarAccess.getParameterRule()) {
					sequence_Parameter(context, (Parameter) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.PARAMETERS:
				if(context == grammarAccess.getParametersRule()) {
					sequence_Parameters(context, (Parameters) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.POSITION:
				if(context == grammarAccess.getPositionRule()) {
					sequence_Position(context, (Position) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.QUIT_SEE_OPTION:
				if(context == grammarAccess.getDrillDownOptionRule() ||
				   context == grammarAccess.getQuitSEEOptionRule()) {
					sequence_QuitSEEOption(context, (QuitSEEOption) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.QUIT_SEE_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getQuitSEETypeRule()) {
					sequence_QuitSEEType(context, (QuitSEEType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.REFERENCE:
				if(context == grammarAccess.getReferenceRule()) {
					sequence_Reference(context, (Reference) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.REPEAT_EVERY_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getRepeatConversionRule() ||
				   context == grammarAccess.getRepeatEveryConversionRule()) {
					sequence_RepeatEveryConversion(context, (RepeatEveryConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.REPEAT_ON_NULL_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getRepeatConversionRule() ||
				   context == grammarAccess.getRepeatOnNullConversionRule()) {
					sequence_RepeatOnNullConversion(context, (RepeatOnNullConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.REPEAT_SUB_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getRepeatConversionRule() ||
				   context == grammarAccess.getRepeatSubConversionRule()) {
					sequence_RepeatSubConversion(context, (RepeatSubConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.REPLACE_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getReplaceConversionRule()) {
					sequence_ReplaceConversion(context, (ReplaceConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.RUN_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getRunTypeRule()) {
					sequence_RunType(context, (RunType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SCALE:
				if(context == grammarAccess.getScaleRule()) {
					sequence_Scale(context, (Scale) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SCREEN_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getScreenTypeRule()) {
					sequence_ScreenType(context, (ScreenType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SECURITY:
				if(context == grammarAccess.getSecurityRule()) {
					sequence_Security(context, (Security) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SELECTION:
				if(context == grammarAccess.getSelectionRule()) {
					sequence_Selection(context, (Selection) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SELECTION_CRITERIA:
				if(context == grammarAccess.getSelectionCriteriaRule()) {
					sequence_SelectionCriteria(context, (SelectionCriteria) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SELECTION_EXPRESSION:
				if(context == grammarAccess.getSelectionExpressionRule()) {
					sequence_SelectionExpression(context, (SelectionExpression) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SELECTION_OPERATION:
				if(context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getSelectionOperationRule()) {
					sequence_SelectionOperation(context, (SelectionOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.SHOULD_BE_CHANGED_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getShouldBeChangedTypeRule()) {
					sequence_ShouldBeChangedType(context, (ShouldBeChangedType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TAB_OPTION:
				if(context == grammarAccess.getDrillDownOptionRule() ||
				   context == grammarAccess.getTabOptionRule()) {
					sequence_TabOption(context, (TabOption) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TABBED_SCREEN_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getTabbedScreenTypeRule()) {
					sequence_TabbedScreenType(context, (TabbedScreenType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TARGET:
				if(context == grammarAccess.getTargetRule()) {
					sequence_Target(context, (Target) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TARGET_MAPPING:
				if(context == grammarAccess.getTargetMappingRule()) {
					sequence_TargetMapping(context, (TargetMapping) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TODAY_OPERATION:
				if(context == grammarAccess.getDateOperationRule() ||
				   context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getTodayOperationRule()) {
					sequence_TodayOperation(context, (TodayOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TOOL:
				if(context == grammarAccess.getToolRule()) {
					sequence_Tool(context, (Tool) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.TOTAL_OPERATION:
				if(context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getTotalOperationRule()) {
					sequence_TotalOperation(context, (TotalOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.USER_OPERATION:
				if(context == grammarAccess.getOperationRule() ||
				   context == grammarAccess.getSystemOperationRule() ||
				   context == grammarAccess.getUserOperationRule()) {
					sequence_UserOperation(context, (UserOperation) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.UTIL_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getUtilTypeRule()) {
					sequence_UtilType(context, (UtilType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.VALUE_CONVERSION:
				if(context == grammarAccess.getConversionRule() ||
				   context == grammarAccess.getValueConversionRule()) {
					sequence_ValueConversion(context, (ValueConversion) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.VIEW_OPTION:
				if(context == grammarAccess.getDrillDownOptionRule() ||
				   context == grammarAccess.getViewOptionRule()) {
					sequence_ViewOption(context, (ViewOption) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.VIEW_TYPE:
				if(context == grammarAccess.getDrillDownStringTypeRule() ||
				   context == grammarAccess.getDrillDownTypeRule() ||
				   context == grammarAccess.getViewTypeRule()) {
					sequence_ViewType(context, (ViewType) semanticObject); 
					return; 
				}
				else break;
			case EnquiryPackage.WEB_SERVICE:
				if(context == grammarAccess.getWebServiceRule()) {
					sequence_WebService(context, (WebService) semanticObject); 
					return; 
				}
				else break;
			}
		else if(semanticObject.eClass().getEPackage() == TranslationDslPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case TranslationDslPackage.LOCAL_TRANSLATION:
				if(context == grammarAccess.getLocalTranslationRule()) {
					sequence_LocalTranslation(context, (LocalTranslation) semanticObject); 
					return; 
				}
				else break;
			case TranslationDslPackage.LOCAL_TRANSLATIONS:
				if(context == grammarAccess.getLocalTranslationsRule() ||
				   context == grammarAccess.getTranslationsRule()) {
					sequence_LocalTranslations(context, (LocalTranslations) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     {AbsConversion}
	 */
	protected void sequence_AbsConversion(EObject context, AbsConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_ApplicationFieldNameOperation(EObject context, ApplicationFieldNameOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.APPLICATION_FIELD_NAME_OPERATION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.APPLICATION_FIELD_NAME_OPERATION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getApplicationFieldNameOperationAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (property='application:' value=STRING)
	 */
	protected void sequence_ApplicationType(EObject context, ApplicationType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getApplicationTypeAccess().getPropertyApplicationKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getApplicationTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (field=STRING displayLegend=Boolean? showGrid=Boolean?)
	 */
	protected void sequence_Axis(EObject context, Axis semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     instruction=STRING
	 */
	protected void sequence_BasicIConversion(EObject context, BasicIConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.BASIC_CONVERSION__INSTRUCTION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.BASIC_CONVERSION__INSTRUCTION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBasicIConversionAccess().getInstructionSTRINGTerminalRuleCall_2_0(), semanticObject.getInstruction());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     instruction=STRING
	 */
	protected void sequence_BasicOConversion(EObject context, BasicOConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.BASIC_CONVERSION__INSTRUCTION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.BASIC_CONVERSION__INSTRUCTION));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBasicOConversionAccess().getInstructionSTRINGTerminalRuleCall_2_0(), semanticObject.getInstruction());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (property='blank' value=BooleanObject)
	 */
	protected void sequence_BlankType(EObject context, BlankType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.BLANK_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.BLANK_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBlankTypeAccess().getPropertyBlankKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getBlankTypeAccess().getValueBooleanObjectParserRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (break=BreakKind | field=STRING)
	 */
	protected void sequence_BreakCondition(EObject context, BreakCondition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     line=INT
	 */
	protected void sequence_BreakLineOperation(EObject context, BreakLineOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.BREAK_LINE_OPERATION__LINE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.BREAK_LINE_OPERATION__LINE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBreakLineOperationAccess().getLineINTTerminalRuleCall_2_0(), semanticObject.getLine());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_BreakOnChangeOperation(EObject context, BreakOnChangeOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.BREAK_ON_CHANGE_OPERATION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.BREAK_ON_CHANGE_OPERATION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getBreakOnChangeOperationAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_CalcFixedRateConversion(EObject context, CalcFixedRateConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.RATE_CONVERSION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.RATE_CONVERSION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getCalcFixedRateConversionAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (field+=STRING ((operator+='+' | operator+='-' | operator+='/' | operator+='*' | operator+=':') field+=STRING)*)
	 */
	protected void sequence_CalcOperation(EObject context, CalcOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     routine=Routine
	 */
	protected void sequence_CallRoutine(EObject context, CallRoutine semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.CALL_ROUTINE__ROUTINE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.CALL_ROUTINE__ROUTINE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getCallRoutineAccess().getRoutineRoutineParserRuleCall_2_0(), semanticObject.getRoutine());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (all=BooleanObject | (code+=STRING code+=STRING*))
	 */
	protected void sequence_Companies(EObject context, Companies semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {CompanyOperation}
	 */
	protected void sequence_CompanyOperation(EObject context, CompanyOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (compositeScreen=STRING? reference+=Reference* fieldParameter+=Parameter*)
	 */
	protected void sequence_CompositeScreenOption(EObject context, CompositeScreenOption semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='composite-screen:' value=STRING)
	 */
	protected void sequence_CompositeScreenType(EObject context, CompositeScreenType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getCompositeScreenTypeAccess().getPropertyCompositeScreenKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getCompositeScreenTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     value=STRING
	 */
	protected void sequence_ConstantOperation(EObject context, ConstantOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.CONSTANT_OPERATION__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.CONSTANT_OPERATION__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getConstantOperationAccess().getValueSTRINGTerminalRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (oldData=STRING? newData=STRING?)
	 */
	protected void sequence_ConvertConversion(EObject context, ConvertConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (leftField=STRING operand=DecisionOperand rightField=STRING firstField=STRING secondField=STRING?)
	 */
	protected void sequence_DecisionOperation(EObject context, DecisionOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_DecryptConversion(EObject context, DecryptConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DECRYPT_CONVERSION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DECRYPT_CONVERSION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDecryptConversionAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_DescriptorOperation(EObject context, DescriptorOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DESCRIPTOR_OPERATION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DESCRIPTOR_OPERATION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDescriptorOperationAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (width=INT height=INT orientation=Orientation?)
	 */
	protected void sequence_Dimension(EObject context, Dimension semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='download:' value=STRING)
	 */
	protected void sequence_DownloadType(EObject context, DownloadType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getDownloadTypeAccess().getPropertyDownloadKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getDownloadTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         drill_name=STRING? 
	 *         description=Translations? 
	 *         labelField=STRING? 
	 *         image=STRING? 
	 *         info=STRING? 
	 *         criteria+=SelectionCriteria* 
	 *         parameters=Parameters? 
	 *         type=DrillDownType?
	 *     )
	 */
	protected void sequence_DrillDown(EObject context, DrillDown semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (label=Translations? column=INT line=INT)
	 */
	protected void sequence_EnquiryHeader(EObject context, EnquiryHeader semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='enquiry:' value=STRING)
	 */
	protected void sequence_EnquiryType(EObject context, EnquiryType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getEnquiryTypeAccess().getPropertyEnquiryKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getEnquiryTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=STRING 
	 *         fileName=STRING 
	 *         metamodelVersion=STRING? 
	 *         header+=EnquiryHeader* 
	 *         description=Translations? 
	 *         serverMode=ServerMode? 
	 *         enquiryMode=EnquiryMode? 
	 *         companies=Companies? 
	 *         accountField=STRING? 
	 *         customerField=STRING? 
	 *         zeroRecordsDisplay=BooleanObject? 
	 *         noSelection=BooleanObject? 
	 *         showAllBooks=BooleanObject? 
	 *         startLine=INTEGER_OBJECT? 
	 *         endLine=INTEGER_OBJECT? 
	 *         buildRoutines+=Routine* 
	 *         fixedSelections+=FixedSelection* 
	 *         fixedSorts+=FixedSort* 
	 *         customSelection=SelectionExpression? 
	 *         fields+=Field* 
	 *         toolbar=STRING? 
	 *         tools+=Tool* 
	 *         target=Target? 
	 *         drillDowns+=DrillDown* 
	 *         security=Security? 
	 *         graph=Graph? 
	 *         webService=WebService? 
	 *         generateIFP=BooleanObject? 
	 *         fileVersion+=FileVersion? 
	 *         (attributes+=STRING attributes+=STRING*)? 
	 *         introspectionMessages+=STRING*
	 *     )
	 */
	protected void sequence_Enquiry(EObject context, Enquiry semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (from=INT to=INT delimiter=STRING?)
	 */
	protected void sequence_ExtractConversion(EObject context, ExtractConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_FieldExtractOperation(EObject context, FieldExtractOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.FIELD_EXTRACT_OPERATION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.FIELD_EXTRACT_OPERATION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getFieldExtractOperationAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     number=INT
	 */
	protected void sequence_FieldNumberOperation(EObject context, FieldNumberOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.FIELD_NUMBER_OPERATION__NUMBER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.FIELD_NUMBER_OPERATION__NUMBER));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getFieldNumberOperationAccess().getNumberINTTerminalRuleCall_2_0(), semanticObject.getNumber());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (pageThrow=BooleanObject | (column=INTEGER_OBJECT ((relative='+' | relative='-')? line=INTEGER_OBJECT multiLine=BooleanObject?)?))
	 */
	protected void sequence_FieldPosition(EObject context, FieldPosition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=STRING 
	 *         label=Translations? 
	 *         comments=STRING? 
	 *         displayType=STRING? 
	 *         format=Format? 
	 *         breakCondition=BreakCondition? 
	 *         length=INTEGER_OBJECT? 
	 *         alignment=AlignmentKind? 
	 *         commaSeparator=BooleanObject? 
	 *         numberOfDecimals=INTEGER_OBJECT? 
	 *         escapeSequence=EscapeSequence? 
	 *         fmtMask=STRING? 
	 *         displaySection=DisplaySectionKind? 
	 *         position=FieldPosition? 
	 *         columnWidth=INTEGER_OBJECT? 
	 *         spoolBreak=BooleanObject? 
	 *         singleMulti=ProcessingMode? 
	 *         hidden=BooleanObject? 
	 *         noHeader=BooleanObject? 
	 *         noColumnLabel=BooleanObject? 
	 *         operation=Operation? 
	 *         conversion+=Conversion* 
	 *         (attributes+=STRING attributes+=STRING*)?
	 *     )
	 */
	protected void sequence_Field(EObject context, Field semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (values+=FileVersionOption values+=FileVersionOption*)
	 */
	protected void sequence_FileVersion(EObject context, FileVersion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (field=STRING (operand=SelectionOperator values+=STRING values+=STRING*)?)
	 */
	protected void sequence_FixedSelection(EObject context, FixedSelection semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (field=STRING order=SortOrder)
	 */
	protected void sequence_FixedSort(EObject context, FixedSort semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.FIXED_SORT__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.FIXED_SORT__FIELD));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.FIXED_SORT__ORDER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.FIXED_SORT__ORDER));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getFixedSortAccess().getFieldSTRINGTerminalRuleCall_1_0(), semanticObject.getField());
		feeder.accept(grammarAccess.getFixedSortAccess().getOrderSortOrderEnumRuleCall_2_0(), semanticObject.getOrder());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (format=FieldFormat (field=STRING pattern=CurrencyPattern?)?)
	 */
	protected void sequence_Format(EObject context, Format semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='from-field:' value=STRING)
	 */
	protected void sequence_FromFieldType(EObject context, FromFieldType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getFromFieldTypeAccess().getPropertyFromFieldKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getFromFieldTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_GetFixedCurrencyConversion(EObject context, GetFixedCurrencyConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.RATE_CONVERSION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.RATE_CONVERSION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getGetFixedCurrencyConversionAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_GetFixedRateConversion(EObject context, GetFixedRateConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.RATE_CONVERSION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.RATE_CONVERSION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getGetFixedRateConversionAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (application=STRING field=STRING language=Boolean)
	 */
	protected void sequence_GetFromConversion(EObject context, GetFromConversion semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.GET_FROM_CONVERSION__APPLICATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.GET_FROM_CONVERSION__APPLICATION));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.GET_FROM_CONVERSION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.GET_FROM_CONVERSION__FIELD));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.GET_FROM_CONVERSION__LANGUAGE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.GET_FROM_CONVERSION__LANGUAGE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getGetFromConversionAccess().getApplicationSTRINGTerminalRuleCall_2_0(), semanticObject.getApplication());
		feeder.accept(grammarAccess.getGetFromConversionAccess().getFieldSTRINGTerminalRuleCall_3_0(), semanticObject.getField());
		feeder.accept(grammarAccess.getGetFromConversionAccess().getLanguageBooleanParserRuleCall_4_0(), semanticObject.isLanguage());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         type=STRING 
	 *         labels+=Label* 
	 *         dimension=Dimension 
	 *         margins=Margins 
	 *         scale=Scale? 
	 *         legend=Legend? 
	 *         xAxis=Axis 
	 *         yAxis=Axis 
	 *         zAxis=Axis?
	 *     )
	 */
	protected void sequence_Graph(EObject context, Graph semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=STRING
	 */
	protected void sequence_JBCRoutine(EObject context, JBCRoutine semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.ROUTINE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.ROUTINE__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJBCRoutineAccess().getNameSTRINGTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     name=STRING
	 */
	protected void sequence_JavaRoutine(EObject context, JavaRoutine semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.ROUTINE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.ROUTINE__NAME));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJavaRoutineAccess().getNameSTRINGTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (property='javascript:' value=STRING)
	 */
	protected void sequence_JavaScriptType(EObject context, JavaScriptType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getJavaScriptTypeAccess().getPropertyJavascriptKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getJavaScriptTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {JulianConversion}
	 */
	protected void sequence_JulianConversion(EObject context, JulianConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {LWDOperation}
	 */
	protected void sequence_LWDOperation(EObject context, LWDOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     label=Translations
	 */
	protected void sequence_LabelOperation(EObject context, LabelOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.LABEL_OPERATION__LABEL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.LABEL_OPERATION__LABEL));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getLabelOperationAccess().getLabelTranslationsParserRuleCall_3_0(), semanticObject.getLabel());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (description=Translations position=Position)
	 */
	protected void sequence_Label(EObject context, Label semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {LanguageOperation}
	 */
	protected void sequence_LanguageOperation(EObject context, LanguageOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (x=INT y=INT)
	 */
	protected void sequence_Legend(EObject context, Legend semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {LocalCurrencyOperation}
	 */
	protected void sequence_LocalCurrencyOperation(EObject context, LocalCurrencyOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (top=INT bottom=INT left=INT right=INT)
	 */
	protected void sequence_Margins(EObject context, Margins semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (pattern=STRING value=STRING)
	 */
	protected void sequence_MatchField(EObject context, MatchField semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.MATCH_FIELD__PATTERN) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.MATCH_FIELD__PATTERN));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.MATCH_FIELD__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.MATCH_FIELD__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getMatchFieldAccess().getPatternSTRINGTerminalRuleCall_2_0(), semanticObject.getPattern());
		feeder.accept(grammarAccess.getMatchFieldAccess().getValueSTRINGTerminalRuleCall_3_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {NWDOperation}
	 */
	protected void sequence_NWDOperation(EObject context, NWDOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='pw-process:' value=STRING)
	 */
	protected void sequence_PWProcessType(EObject context, PWProcessType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getPWProcessTypeAccess().getPropertyPwProcessKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getPWProcessTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_Parameter(EObject context, Parameter semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.PARAMETER__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.PARAMETER__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getParameterAccess().getFieldSTRINGTerminalRuleCall_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         function=FunctionKind? 
	 *         auto=Boolean? 
	 *         runImmediately=Boolean? 
	 *         pwActivity=STRING? 
	 *         fieldName+=STRING* 
	 *         variable+=STRING*
	 *     )
	 */
	protected void sequence_Parameters(EObject context, Parameters semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (x=INT y=INT)
	 */
	protected void sequence_Position(EObject context, Position semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {QuitSEEOption}
	 */
	protected void sequence_QuitSEEOption(EObject context, QuitSEEOption semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='quit-SEE:' value=STRING)
	 */
	protected void sequence_QuitSEEType(EObject context, QuitSEEType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getQuitSEETypeAccess().getPropertyQuitSEEKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getQuitSEETypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (file=STRING field=STRING)
	 */
	protected void sequence_Reference(EObject context, Reference semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.REFERENCE__FILE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.REFERENCE__FILE));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.REFERENCE__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.REFERENCE__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getReferenceAccess().getFileSTRINGTerminalRuleCall_0_0(), semanticObject.getFile());
		feeder.accept(grammarAccess.getReferenceAccess().getFieldSTRINGTerminalRuleCall_1_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {RepeatEveryConversion}
	 */
	protected void sequence_RepeatEveryConversion(EObject context, RepeatEveryConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {RepeatOnNullConversion}
	 */
	protected void sequence_RepeatOnNullConversion(EObject context, RepeatOnNullConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {RepeatSubConversion}
	 */
	protected void sequence_RepeatSubConversion(EObject context, RepeatSubConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (oldData=STRING? newData=STRING?)
	 */
	protected void sequence_ReplaceConversion(EObject context, ReplaceConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='run:' value=STRING)
	 */
	protected void sequence_RunType(EObject context, RunType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getRunTypeAccess().getPropertyRunKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getRunTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (x=INTEGER_OBJECT? y=INTEGER_OBJECT?)
	 */
	protected void sequence_Scale(EObject context, Scale semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='screen:' value=STRING)
	 */
	protected void sequence_ScreenType(EObject context, ScreenType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getScreenTypeAccess().getPropertyScreenKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getScreenTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (application=STRING field=STRING abort=Boolean?)
	 */
	protected void sequence_Security(EObject context, Security semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (field=STRING (operand=CriteriaOperator values+=STRING values+=STRING*)?)
	 */
	protected void sequence_SelectionCriteria(EObject context, SelectionCriteria semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (selection+=Selection selection+=Selection*)
	 */
	protected void sequence_SelectionExpression(EObject context, SelectionExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_SelectionOperation(EObject context, SelectionOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.SELECTION_OPERATION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.SELECTION_OPERATION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getSelectionOperationAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         field=STRING 
	 *         mandatory=BooleanObject? 
	 *         popupDropDown=BooleanObject? 
	 *         label=Translations? 
	 *         (operands+=SelectionOperator operands+=SelectionOperator*)? 
	 *         operator=AndOr
	 *     )
	 */
	protected void sequence_Selection(EObject context, Selection semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='should-be-changed:' value=STRING)
	 */
	protected void sequence_ShouldBeChangedType(EObject context, ShouldBeChangedType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getShouldBeChangedTypeAccess().getPropertyShouldBeChangedKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getShouldBeChangedTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (tabName=STRING? reference+=Reference* fieldParameter+=Parameter*)
	 */
	protected void sequence_TabOption(EObject context, TabOption semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='tab:' value=STRING)
	 */
	protected void sequence_TabbedScreenType(EObject context, TabbedScreenType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTabbedScreenTypeAccess().getPropertyTabKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getTabbedScreenTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (fromField=STRING toField=STRING)
	 */
	protected void sequence_TargetMapping(EObject context, TargetMapping semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.TARGET_MAPPING__FROM_FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.TARGET_MAPPING__FROM_FIELD));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.TARGET_MAPPING__TO_FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.TARGET_MAPPING__TO_FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTargetMappingAccess().getFromFieldSTRINGTerminalRuleCall_1_0(), semanticObject.getFromField());
		feeder.accept(grammarAccess.getTargetMappingAccess().getToFieldSTRINGTerminalRuleCall_3_0(), semanticObject.getToField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (application=STRING screen=STRING? mappings+=TargetMapping+)
	 */
	protected void sequence_Target(EObject context, Target semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {TodayOperation}
	 */
	protected void sequence_TodayOperation(EObject context, TodayOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID_WITHDOT label=Translations? command+=STRING*)
	 */
	protected void sequence_Tool(EObject context, Tool semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     field=STRING
	 */
	protected void sequence_TotalOperation(EObject context, TotalOperation semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.TOTAL_OPERATION__FIELD) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.TOTAL_OPERATION__FIELD));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getTotalOperationAccess().getFieldSTRINGTerminalRuleCall_2_0(), semanticObject.getField());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     {UserOperation}
	 */
	protected void sequence_UserOperation(EObject context, UserOperation semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='util' value=STRING)
	 */
	protected void sequence_UtilType(EObject context, UtilType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getUtilTypeAccess().getPropertyUtilKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getUtilTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (value=INT subValue=INTEGER_OBJECT?)
	 */
	protected void sequence_ValueConversion(EObject context, ValueConversion semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {ViewOption}
	 */
	protected void sequence_ViewOption(EObject context, ViewOption semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (property='view:' value=STRING)
	 */
	protected void sequence_ViewType(EObject context, ViewType semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_TYPE__PROPERTY));
			if(transientValues.isValueTransient(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, EnquiryPackage.Literals.DRILL_DOWN_STRING_TYPE__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider(semanticObject);
		SequenceFeeder feeder = createSequencerFeeder(semanticObject, nodes);
		feeder.accept(grammarAccess.getViewTypeAccess().getPropertyViewKeyword_0_0(), semanticObject.getProperty());
		feeder.accept(grammarAccess.getViewTypeAccess().getValueSTRINGTerminalRuleCall_1_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     (publishWebService=BooleanObject? (webServiceNames+=STRING webServiceNames+=STRING*)? webServiceActivity=STRING? webServiceDescription=STRING?)
	 */
	protected void sequence_WebService(EObject context, WebService semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
