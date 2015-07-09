package com.odcgroup.t24.enquiry.editor.part.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;

/**
 * @author satishnangi
 *
 */
public class FixedSortDeleteCommand extends Command {
	private Enquiry enquiry = null;
	private String fieldName = null;
	
	public FixedSortDeleteCommand(Enquiry enquiry, String fieldName) {
		this.enquiry = enquiry;
		this.fieldName = fieldName;
	}


	@Override
	public void execute() {
		List<FixedSort> fixedSortList = enquiry.getFixedSorts();
		FixedSort fixedSortToRemove = null;
		for(FixedSort fixedSort : fixedSortList) { 
			if(fixedSort.getField().equals(fieldName)){
				fixedSortToRemove = fixedSort;
				break;
			}
		}
		if(fixedSortToRemove !=null ){
		  enquiry.getFixedSorts().remove(fixedSortToRemove);
		}
	}

}
