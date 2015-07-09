package com.odcgroup.t24.enquiry.properties.util;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.t24.enquiry.enquiry.CallRoutine;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.JBCRoutine;
import com.odcgroup.t24.enquiry.enquiry.JavaRoutine;


/**
 * @author satishnangi
 *
 */
public enum ConversionEnum  {
	ExtractConversion("Extract From") ,
	DecryptConversion("Decrypt") ,
	ReplaceConversion("Replace") ,
	ConvertConversion("Convert") ,
	ValueConversion("Value")   ,
	JulianConversion("Julian") ,
	BasicOConversion("BasicO")  ,
	BasicIConversion("BasicI")  ,
	GetFromConversion("Get From") ,
	CalcFixedRateConversion("Calc Fixed Rate") ,
	GetFixedRateConversion("Get Fixed Rate") ,
	GetFixedCurrencyConversion("Get Fixed Currency") ,
	AbsConversion("Absolute")     ,
	MatchField("Match Field")        ,
	JavaRoutine("Java Routine"),
	JBCRoutine("JBC Routine")        , 
	RepeatOnNullConversion("Repeat On Null"),
	RepeatEveryConversion("Repeat Every"),
	RepeatSubConversion("Repeat Sub");
	
	private String display;
	/**
	 * @param arg0
	 * @param arg1
	 */
	ConversionEnum(String display) {
		this.display = display;
	}

	public String getDisplay(){
		return display;
	}
	public static List<String> getConversionTypes(){
		List<String> conversionList = new ArrayList<String>();
		for(ConversionEnum conversionEnum : values()){
			conversionList.add(conversionEnum.display);
		}
		return conversionList;
	}
	
	public static ConversionEnum getConversionEnum(String display){
		for(ConversionEnum conversionEnum : values()){
			if(display.equals(conversionEnum.display)){
				return conversionEnum;
			}
		}
		return null;
	}
	
	public static ConversionEnum getConversionEnum(Conversion conversion){
		if(conversion instanceof CallRoutine){
			CallRoutine callRoutine = (CallRoutine) conversion;
			if(callRoutine.getRoutine() instanceof JavaRoutine){
				return ConversionEnum.JavaRoutine;
			}
			if(callRoutine.getRoutine() instanceof JBCRoutine){
				return ConversionEnum.JBCRoutine;
			}
		}
		String name = conversion.eClass().getName();
		for(ConversionEnum conversionEnum : values()){
			if(name.equals(conversionEnum.name())){
				return conversionEnum;
			}
		}
		return null;
	}
}
