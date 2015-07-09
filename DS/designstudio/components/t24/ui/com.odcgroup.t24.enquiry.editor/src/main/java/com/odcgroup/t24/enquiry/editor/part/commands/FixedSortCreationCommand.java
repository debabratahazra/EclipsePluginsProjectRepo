package com.odcgroup.t24.enquiry.editor.part.commands;

import org.eclipse.gef.commands.Command;

import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.FixedSort;

/**
 * @author satishnangi
 *
 */
public class FixedSortCreationCommand extends Command {
    private Enquiry enquiry = null;
	private FixedSort fsort = null;
	
	public FixedSortCreationCommand(Enquiry enquiry,FixedSort fsort) {
		this.enquiry = enquiry;
		this.fsort = fsort;
	}
	

	@Override
	public void execute() {
	   if(fsort !=null){	
		 enquiry.getFixedSorts().add(fsort);
	   }
	}

}
