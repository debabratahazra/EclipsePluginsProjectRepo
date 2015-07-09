package com.odcgroup.t24.enquiry.editor.part.commands;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;

/**
 * @author satishnangi
 *
 */
public class FixedSortMoveDownCommand extends Command {
	private Enquiry enquiry = null;
	private String fieldName = null;
	
	public FixedSortMoveDownCommand(Enquiry enquiry ,String fieldName){
		this.enquiry = enquiry;
		this.fieldName = fieldName;
	}
  
	@Override
	public void execute() {
		List<FixedSort> fixedSortList = enquiry.getFixedSorts();
		int oldPosition =fixedSortList.size()-1;
		for(FixedSort fixedSort : fixedSortList) { 
			if(fixedSort.getField().equals(fieldName)){
				 oldPosition = enquiry.getFixedSorts().indexOf(fixedSort);
			}
		}
		if(oldPosition !=fixedSortList.size()-1){
		   enquiry.getFixedSorts().move(oldPosition+1, oldPosition);
		}
	}
}
