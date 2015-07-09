package com.odcgroup.t24.enquiry.editor.part.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.util.ConversionEnum;
import com.odcgroup.t24.enquiry.properties.util.ConversionPropertiesUtil;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class ConversionDeletionCommand extends Command {

	private Field field;
	private String conversionType;
	private String conversionSummary;
	/**
	 * @param field
	 * @param conversionType
	 * @param conversionSummary
	 */
	public ConversionDeletionCommand(Field field, String conversionType, String conversionSummary) {
		super();
		this.field = field;
		this.conversionType = conversionType;
		this.conversionSummary = conversionSummary;
	}
	
	@Override
	public void execute() {
		List<Conversion> conversionList = field.getConversion();
		Conversion conversionToRemove = null;
		for(Conversion conversion : conversionList) { 
			if(conversionType.equals(ConversionEnum.getConversionEnum(conversion).getDisplay()) && conversionSummary.equals(ConversionPropertiesUtil.getConversionSummary(conversion))){
				conversionToRemove = conversion;
				break;
			}
		}
		if(conversionToRemove !=null ){
			conversionList.remove(conversionToRemove);
		}
	;
	}
	
	
}
