package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.properties.util.ConversionPropertiesUtil;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class ConversionEditCommand extends Command {
	
	Field field;
	String conversionSummary;
	Conversion conversion;
	/**
	 * @param field
	 * @param conversionSummary
	 * @param conversion
	 */
	public ConversionEditCommand(Field field, String conversionSummary, Conversion conversion) {
		super();
		this.field = field;
		this.conversionSummary = conversionSummary;
		this.conversion = conversion;
	}

	@Override
	public boolean canExecute() {
		if(conversionSummary.equals(ConversionPropertiesUtil.getConversionSummary(conversion))){
			return false;
		}
		return true;
	}
	
}
