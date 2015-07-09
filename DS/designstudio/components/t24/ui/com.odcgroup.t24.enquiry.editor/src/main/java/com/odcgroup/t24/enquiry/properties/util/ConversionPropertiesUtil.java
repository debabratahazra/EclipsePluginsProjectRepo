package com.odcgroup.t24.enquiry.properties.util;

import com.odcgroup.t24.enquiry.enquiry.AbsConversion;
import com.odcgroup.t24.enquiry.enquiry.BasicConversion;
import com.odcgroup.t24.enquiry.enquiry.BasicIConversion;
import com.odcgroup.t24.enquiry.enquiry.BasicOConversion;
import com.odcgroup.t24.enquiry.enquiry.CalcFixedRateConversion;
import com.odcgroup.t24.enquiry.enquiry.CallRoutine;
import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.ConvertConversion;
import com.odcgroup.t24.enquiry.enquiry.DecryptConversion;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.enquiry.ExtractConversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.GetFixedCurrencyConversion;
import com.odcgroup.t24.enquiry.enquiry.GetFixedRateConversion;
import com.odcgroup.t24.enquiry.enquiry.GetFromConversion;
import com.odcgroup.t24.enquiry.enquiry.JBCRoutine;
import com.odcgroup.t24.enquiry.enquiry.JavaRoutine;
import com.odcgroup.t24.enquiry.enquiry.JulianConversion;
import com.odcgroup.t24.enquiry.enquiry.MatchField;
import com.odcgroup.t24.enquiry.enquiry.RateConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatEveryConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatOnNullConversion;
import com.odcgroup.t24.enquiry.enquiry.RepeatSubConversion;
import com.odcgroup.t24.enquiry.enquiry.ReplaceConversion;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.enquiry.ValueConversion;
import com.odcgroup.t24.enquiry.enquiry.impl.BasicIConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.BasicOConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.CalcFixedRateConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.ConvertConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.DecryptConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.EnquiryFactoryImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.ExtractConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.GetFixedCurrencyConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.GetFixedRateConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.GetFromConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.MatchFieldImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.ReplaceConversionImpl;
import com.odcgroup.t24.enquiry.enquiry.impl.ValueConversionImpl;

/**
 * TODO: Document me!
 * 
 * @author mumesh
 * 
 */
public final class ConversionPropertiesUtil {

	public static final String CONVERSION_TYPE = "ConversionType";
    public static String[] conversionTypes = ConversionEnum.getConversionTypes().toArray(new String[0]);

	
	public static Conversion createConversion(String conversionType) {
		Conversion conversion = null;
		if(conversionType.equals("ExtractConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createExtractConversion();
		}
		else if(conversionType.equals("DecryptConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createDecryptConversion();
		}
		else if(conversionType.equals("ReplaceConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createReplaceConversion();
		}
		else if(conversionType.equals("ReplaceConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createReplaceConversion();
		}
		else if(conversionType.equals("ConvertConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createConvertConversion();
		}
		else if(conversionType.equals("ValueConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createValueConversion();
		}
		else if(conversionType.equals("JulianConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createJulianConversion();
		}
		else if(conversionType.equals("BasicOConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createBasicOConversion();
		}
		else if(conversionType.equals("BasicIConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createBasicIConversion();
		}
		else if(conversionType.equals("GetFromConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createGetFromConversion();
		}
		else if(conversionType.equals("CalcFixedRateConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createCalcFixedRateConversion();
		}
		else if(conversionType.equals("GetFixedRateConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createGetFixedRateConversion();
		}
		else if(conversionType.equals("GetFixedCurrencyConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createGetFixedCurrencyConversion();
		}
		else if(conversionType.equals("AbsConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createAbsConversion();
		}
		else if(conversionType.equals("MatchField")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createMatchField();
		}
		else if(conversionType.equals("JavaRoutine")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createCallRoutine();
			JavaRoutine jRoutine = EnquiryFactoryImpl.eINSTANCE.createJavaRoutine();
			jRoutine.setName("");
			((CallRoutine) conversion).setRoutine(jRoutine);
		}
		else if(conversionType.equals("JBCRoutine")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createCallRoutine();
			((CallRoutine) conversion).setRoutine(EnquiryFactoryImpl.eINSTANCE.createJBCRoutine());
		}
		else if(conversionType.equals("RepeatOnNullConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createRepeatOnNullConversion();
		}
		else if(conversionType.equals("RepeatEveryConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createRepeatEveryConversion();
		}
		else if(conversionType.equals("RepeatSubConversion")){
			conversion = EnquiryFactoryImpl.eINSTANCE.createRepeatSubConversion();
		}
		return conversion;
	}

	public static String getConversionSummary(Conversion conversion) {
		if (conversion instanceof ExtractConversion) {
			ExtractConversionImpl extract = (ExtractConversionImpl) conversion;
			return "Extract from \"" + extract.getFrom() + "\" to \"" + extract.getTo() + "\" delimited by \""
					+ extract.getDelimiter()+"\"";
		}
		if (conversion instanceof DecryptConversion) {
			return "Decrypt field: \"" + ((DecryptConversionImpl) conversion).getField()+"\"";
		}
		if (conversion instanceof ReplaceConversion) {
			return "Replace old data \"" + ((ReplaceConversionImpl) conversion).getOldData() + "\" by new data \""
					+ ((ReplaceConversionImpl) conversion).getNewData() +"\"";
		}
		if (conversion instanceof ConvertConversion) {
			return "Convert old data \""+ ((ConvertConversionImpl) conversion).getOldData() + "\" by new data \""
					+ ((ConvertConversionImpl) conversion).getNewData() +"\"";
		}
		if (conversion instanceof ValueConversion) {
			return "Extract value \"" + ((ValueConversionImpl) conversion).getValue() + "\" sub-value \""
					+ ((ValueConversionImpl) conversion).getSubValue()+"\"";
		}
		if (conversion instanceof JulianConversion) {
			return "Julian ";
		}
		if (conversion instanceof BasicOConversion) {
			return "OCONV  instruction\"" + ((BasicOConversionImpl) conversion).getInstruction()+"\"";
		}
		if (conversion instanceof BasicIConversion) {
			return "ICONV  instruction\""+ ((BasicIConversionImpl) conversion).getInstruction()+"\"";
		}
		if (conversion instanceof GetFromConversion) {
			return "Get from application \"" + ((GetFromConversionImpl) conversion).getApplication() + "\" field \""
					+ ((GetFromConversionImpl) conversion).getField() +"\" , "+ "language \""
					+ ((GetFromConversionImpl) conversion).isLanguage()+"\"";
		}
		if (conversion instanceof CalcFixedRateConversion) {
			return "Calculate fixed rate from \"" + ((CalcFixedRateConversionImpl) conversion).getField()+"\"";
		}
		if (conversion instanceof GetFixedRateConversion) {
			return "Get fixed rate from \"" + ((GetFixedRateConversionImpl) conversion).getField()+"\"";
		}
		if (conversion instanceof GetFixedCurrencyConversion) {
			return "Get fixed currency from \"" + ((GetFixedCurrencyConversionImpl) conversion).getField()+"\"";
		}
		if (conversion instanceof AbsConversion) {
			return "Absolute value";
		}
		if (conversion instanceof MatchField) {
			return "Match pattern \"" + ((MatchFieldImpl) conversion).getPattern() + "\" on value \""
					+ ((MatchFieldImpl) conversion).getValue()+"\"";
		}
		if (conversion instanceof CallRoutine) {
			Routine routine = ((CallRoutine) conversion).getRoutine();
			if(routine instanceof JavaRoutine){
				return " Call Java routine \"" + routine.getName()+"\"";
			}
			if(routine instanceof JBCRoutine){
				return " Call JBC routine \"" + routine.getName()+"\"";
			}
		}

		if (conversion instanceof RepeatOnNullConversion) {
			return "Repeat when null";
		}
		if (conversion instanceof RepeatEveryConversion) {
			return "Repeat Every";
		}
		if (conversion instanceof RepeatSubConversion) {
			return "Repeat sub-value when null";
		}
		return "";
	}

	public static boolean isConversionId(Object id, Conversion conversion) {
		if (conversion instanceof ExtractConversion) {
			if (id.equals(EnquiryPackage.eINSTANCE.getExtractConversion_To().getName())
					|| id.equals(EnquiryPackage.eINSTANCE.getExtractConversion_From().getName())
					|| id.equals(EnquiryPackage.eINSTANCE.getExtractConversion_Delimiter().getName())) {
				return true;
			}
		}
		if (conversion instanceof CallRoutine) {
			if (id.equals(EnquiryPackage.eINSTANCE.getRoutine_Name().getName())) {
				return true;
			}
		}
		if (conversion instanceof ValueConversion) {
			if (id.equals(EnquiryPackage.eINSTANCE.getValueConversion_Value().getName())) {
				return true;
			}
		}
		if (conversion instanceof ReplaceConversion) {
			if (id.equals(EnquiryPackage.eINSTANCE.getReplaceConversion_OldData().getName())
					|| id.equals(EnquiryPackage.eINSTANCE.getReplaceConversion_NewData().getName())) {
				return true;
			}
		}
		if (conversion instanceof ConvertConversion) {
			if (id.equals(EnquiryPackage.eINSTANCE.getConvertConversion_OldData().getName())
					|| id.equals(EnquiryPackage.eINSTANCE.getConvertConversion_NewData().getName())) {
				return true;
			}
		}
		if (conversion instanceof DecryptConversion) {
			if (id.equals(EnquiryPackage.DECRYPT_CONVERSION__FIELD)) {
				return true;
			}
		}
		if (conversion instanceof BasicConversion) {
			if (id.equals(EnquiryPackage.eINSTANCE.getBasicConversion_Instruction().getName())) {
				return true;
			}
		}
		if (conversion instanceof GetFromConversion) {
			if (id.equals(EnquiryPackage.eINSTANCE.getGetFromConversion_Application().getName())
					|| id.equals(EnquiryPackage.GET_FROM_CONVERSION__FIELD)
					|| id.equals(EnquiryPackage.eINSTANCE.getGetFromConversion_Language().getName())) {
				return true;
			}
		}
		if (conversion instanceof RateConversion) {
			if (id.equals(EnquiryPackage.RATE_CONVERSION__FIELD)) {
				return true;
			}
		}
		if (conversion instanceof MatchField) {
			if (id.equals(EnquiryPackage.MATCH_FIELD__PATTERN)
					|| id.equals(EnquiryPackage.eINSTANCE.getMatchField_Value().getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param id
	 * @param conversion
	 * @return
	 */
	public static Object getConversionId(Object id, Conversion conversion) {
		if ((conversion instanceof DecryptConversion) && id.equals(EnquiryPackage.DECRYPT_CONVERSION__FIELD)) {
			id = EnquiryPackage.eINSTANCE.getDecryptConversion_Field().getName();
		} else if ((conversion instanceof GetFromConversion) && id.equals(EnquiryPackage.GET_FROM_CONVERSION__FIELD)) {
			id = EnquiryPackage.eINSTANCE.getGetFromConversion_Field().getName();
		} else if ((conversion instanceof RateConversion) && id.equals(EnquiryPackage.RATE_CONVERSION__FIELD)) {
			id = EnquiryPackage.eINSTANCE.getRateConversion_Field().getName();
		} else if ((conversion instanceof MatchField) && id.equals(EnquiryPackage.MATCH_FIELD__PATTERN)) {
			id = EnquiryPackage.eINSTANCE.getMatchField_Pattern().getName();
		}
		return id;
	}
	
	public static Conversion getFirstConversion(Field field ){
		if(field.getConversion() != null && !field.getConversion().isEmpty()){
			return field.getConversion().get(0);
		}
		return null;
	}

}
