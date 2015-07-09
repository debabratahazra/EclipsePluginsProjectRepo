package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Conversion;
import com.odcgroup.t24.enquiry.enquiry.Field;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class ConversionCreationCommand extends Command {

	private Field field;
	private Conversion conversion;
	/**
	 * @param field
	 * @param conversion
	 */
	public ConversionCreationCommand(Field field, Conversion conversion) {
		super();
		this.field = field;
		this.conversion = conversion;
	}
	
	@Override
	public void execute() {
	   if(conversion !=null){	
		field.getConversion().add(conversion);
	   }
	}
	
}
