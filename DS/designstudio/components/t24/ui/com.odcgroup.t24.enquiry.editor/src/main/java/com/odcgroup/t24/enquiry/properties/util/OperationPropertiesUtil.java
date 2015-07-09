package com.odcgroup.t24.enquiry.properties.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;

import com.odcgroup.t24.enquiry.enquiry.ApplicationFieldNameOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakLineOperation;
import com.odcgroup.t24.enquiry.enquiry.BreakOnChangeOperation;
import com.odcgroup.t24.enquiry.enquiry.CalcOperation;
import com.odcgroup.t24.enquiry.enquiry.CompanyOperation;
import com.odcgroup.t24.enquiry.enquiry.ConstantOperation;
import com.odcgroup.t24.enquiry.enquiry.DecisionOperation;
import com.odcgroup.t24.enquiry.enquiry.DescriptorOperation;
import com.odcgroup.t24.enquiry.enquiry.EnquiryFactory;
import com.odcgroup.t24.enquiry.enquiry.FieldExtractOperation;
import com.odcgroup.t24.enquiry.enquiry.FieldNumberOperation;
import com.odcgroup.t24.enquiry.enquiry.LWDOperation;
import com.odcgroup.t24.enquiry.enquiry.LabelOperation;
import com.odcgroup.t24.enquiry.enquiry.LanguageOperation;
import com.odcgroup.t24.enquiry.enquiry.LocalCurrencyOperation;
import com.odcgroup.t24.enquiry.enquiry.NWDOperation;
import com.odcgroup.t24.enquiry.enquiry.Operation;
import com.odcgroup.t24.enquiry.enquiry.SelectionOperation;
import com.odcgroup.t24.enquiry.enquiry.TodayOperation;
import com.odcgroup.t24.enquiry.enquiry.TotalOperation;
import com.odcgroup.t24.enquiry.enquiry.UserOperation;
import com.odcgroup.translation.translationDsl.LocalTranslation;
import com.odcgroup.translation.translationDsl.LocalTranslations;
import com.odcgroup.translation.translationDsl.Translations;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public final class OperationPropertiesUtil {

	private static String double_quotes = "\"";
	public static final String getOperationSummary(Operation operation) {
		if (operation == null) {
			return "None";
		}
		else if (operation instanceof BreakLineOperation) {
			return "Break at line: " + double_quotes+((BreakLineOperation) operation).getLine()+double_quotes;
		}
		else if (operation instanceof BreakOnChangeOperation) {
			return "Break on change for field: " + double_quotes+((BreakOnChangeOperation) operation).getField()+double_quotes;
		}
		else if (operation instanceof ConstantOperation) {
			return "Constant: " +double_quotes+ ((ConstantOperation) operation).getValue()+double_quotes;
		}
		else if (operation instanceof TodayOperation) {
			return "Todays date ";
		}
		else if (operation instanceof LWDOperation) {
			return "Last working day";
		}
		else if (operation instanceof NWDOperation) {
			return "Next working day";
		}
		else if (operation instanceof DescriptorOperation) {
			return "Descriptor (IDESC): " +double_quotes+ ((DescriptorOperation) operation).getField()+double_quotes;
		}
		else if(operation instanceof DecisionOperation){
			DecisionOperation decisionOp = (DecisionOperation)operation;
			return "If \"" +decisionOp.getLeftField()+ "\" "+double_quotes+ decisionOp.getOperand().getLiteral()+double_quotes+" \""+ decisionOp.getRightField()+"\" then \""+
			decisionOp.getFirstField() +"\" else \""+decisionOp.getSecondField()+"\"";
		}
		else if (operation instanceof FieldExtractOperation) {
			return "Extract field: " + double_quotes+((FieldExtractOperation) operation).getField()+double_quotes;
		}
		else if (operation instanceof ApplicationFieldNameOperation) {
			return "Application field (FIELD.NAME): " + double_quotes+((ApplicationFieldNameOperation) operation).getField()+double_quotes;
		}
		else if (operation instanceof FieldNumberOperation) {
			return "Reference field number: " + double_quotes+ ((FieldNumberOperation) operation).getNumber()+double_quotes;
		}
		else if (operation instanceof SelectionOperation) {
			return "Selection field: " + double_quotes+((SelectionOperation) operation).getField()+double_quotes;
		}
		else if (operation instanceof UserOperation) {
			return "User Id system variable";
		}
		else if (operation instanceof CompanyOperation) {
			return "Company Id system variable";
		}
		else if (operation instanceof LanguageOperation) {
			return "Language code system variable";
		}
		else if (operation instanceof LocalCurrencyOperation) {
			return "Local currency code system variable";
		}
		else if (operation instanceof TotalOperation) {
			return "Total enquiry field: "+ double_quotes +((TotalOperation) operation).getField()+double_quotes;
		}
		else if(operation instanceof CalcOperation) {
			CalcOperation calcOperation = (CalcOperation) operation;
			EList<String> fields = calcOperation.getField();
			EList<String> operators = calcOperation.getOperator();
			String summary = "";
			if(fields.size() > 0){
				summary = fields.get(0);
			}
			if(operators.size() >0){
				for(int i=0; i<operators.size();i++){
					summary = summary +" "+operators.get(i)+" "+fields.get(i+1);
				}
			}
			return "Calculate: \""+summary+"\"";
		}
		else if(operation instanceof LabelOperation){
			LabelOperation labelOperation = (LabelOperation)operation;
			return "Label :\""+getTranslationText(labelOperation)+"\"";
		}
		return operation.eClass().getName();
	}

	public static Operation createOperation(String operationType) {
		if (operationType.equals("BreakOnChangeOperation")) {
			return EnquiryFactory.eINSTANCE.createBreakOnChangeOperation();
		} else if (operationType.equals("BreakLineOperation")) {
			return EnquiryFactory.eINSTANCE.createBreakLineOperation();
		} else if (operationType.equals("CalcOperation")) {
			return EnquiryFactory.eINSTANCE.createCalcOperation();
		} else if (operationType.equals("LabelOperation")) {
			return EnquiryFactory.eINSTANCE.createLabelOperation();
		} else if (operationType.equals("TodayOperation")) {
			return EnquiryFactory.eINSTANCE.createTodayOperation();
		} else if (operationType.equals("LWDOperation")) {
			return EnquiryFactory.eINSTANCE.createLWDOperation();
		} else if (operationType.equals("NWDOperation")) {
			return EnquiryFactory.eINSTANCE.createNWDOperation();
		} else if (operationType.equals("DecisionOperation")) {
			return EnquiryFactory.eINSTANCE.createDecisionOperation();
		} else if (operationType.equals("DescriptorOperation")) {
			return EnquiryFactory.eINSTANCE.createDescriptorOperation();
		} else if (operationType.equals("ApplicationFieldNameOperation")) {
			return EnquiryFactory.eINSTANCE.createApplicationFieldNameOperation();
		} else if (operationType.equals("FieldExtractOperation")) {
			return EnquiryFactory.eINSTANCE.createFieldExtractOperation();
		} else if (operationType.equals("FieldNumberOperation")) {
			return EnquiryFactory.eINSTANCE.createFieldNumberOperation();
		} else if (operationType.equals("SelectionOperation")) {
			return EnquiryFactory.eINSTANCE.createSelectionOperation();
		} else if (operationType.equals("UserOperation")) {
			return EnquiryFactory.eINSTANCE.createUserOperation();
		} else if (operationType.equals("CompanyOperation")) {
			return EnquiryFactory.eINSTANCE.createCompanyOperation();
		} else if (operationType.equals("LanguageOperation")) {
			return EnquiryFactory.eINSTANCE.createLanguageOperation();
		} else if (operationType.equals("LocalCurrencyOperation")) {
			return EnquiryFactory.eINSTANCE.createLocalCurrencyOperation();
		} else if (operationType.equals("TotalOperation")) {
			return EnquiryFactory.eINSTANCE.createTotalOperation();
		} else if (operationType.equals("ConstantOperation")){
			return EnquiryFactory.eINSTANCE.createConstantOperation();
		}

		return null;
	}

	/**
	 * @param calc
	 * @return
	 */
	public static List<String> getCalcFieldOperatorExpression(CalcOperation calc) {
		List<String> expression = new ArrayList<String>();
		EList<String> fields = calc.getField();
		EList<String> operators = calc.getOperator();
		if(operators.size() >0 && (fields.size() - 1) == operators.size())
		for(int i=0; i<operators.size();i++){
			expression.add(operators.get(i)+" "+fields.get(i+1));
		}
		return expression;
	}
	
	/**
	 * @param attribute
	 * @param calc
	 */
	public static Object getValueForCalcOperation(EAttribute attribute, CalcOperation calc) {
		if(attribute.getName().equals("field")){
			if(calc.getField().size()>0){
				return calc.getField().get(0);
			}
			return "";
		}
		if(attribute.getName().equals("operator")){
			if(calc.getOperator().size()>0){
			return (Object)OperationPropertiesUtil.getCalcFieldOperatorExpression(calc);
			}
			return "";
		}
		return null;
	}

	/**
	 * @param model 
	 * @return
	 */
	public static String getTranslationText(LabelOperation model) {
		Translations translation = model.getLabel();
		if (translation != null) {
			EList<LocalTranslation> localTranslationList = ((LocalTranslations) translation).getTranslations();
			for (LocalTranslation localTransltion : localTranslationList) {
				if (localTransltion != null) {
					if (localTransltion.getLocale().equals("en")) {
						return localTransltion.getText();
					}
				}
			}
		}
		return "";
	}

}
