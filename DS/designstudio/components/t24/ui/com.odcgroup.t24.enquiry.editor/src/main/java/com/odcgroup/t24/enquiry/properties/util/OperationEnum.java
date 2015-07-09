package com.odcgroup.t24.enquiry.properties.util;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public enum OperationEnum {
	None("None"), 
	BreakOnChangeOperation("Break On Change"), 
	BreakLineOperation("Break Line"),
	CalcOperation("Calculate"),
	LabelOperation("Label"),
	TodayOperation("Date Today"),
	LWDOperation("Date LWD"),
	NWDOperation("Date NWD"), 
	DecisionOperation("Decision"),
	DescriptorOperation("Description"), 
	ApplicationFieldNameOperation("Application Field Name"), 
	FieldExtractOperation("Field Extract"),
	FieldNumberOperation("Field Number"),
	SelectionOperation("Selection"),
	UserOperation("System User"),
	CompanyOperation("System Company"),
	LanguageOperation("System Language"),
	LocalCurrencyOperation("System Local Currency"),
	TotalOperation("Total"),
	ConstantOperation("Constant");
	
	private String display;

	/**
	 * @param display
	 */
	OperationEnum(String display) {
		this.display = display;
	}

	public static List<String> getOperationTypes(){
		List<String> operationList = new ArrayList<String>();
		for(OperationEnum operationEnum : values()){
			operationList.add(operationEnum.display);
		}
		return operationList;
	}
	
	public static OperationEnum getOperationEnum(String display){
		for(OperationEnum operationEnum : values()){
			if(display.equals(operationEnum.display)){
				return operationEnum;
			}
		}
		return null;
	}

	
}
