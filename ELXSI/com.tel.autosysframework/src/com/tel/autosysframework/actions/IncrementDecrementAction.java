package com.tel.autosysframework.actions;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import com.tel.autosysframework.AutosysPlugin;
import com.tel.autosysframework.editpart.AutosysDiagramEditPart;
import com.tel.autosysframework.views.Configure;
import com.tel.autosysframework.views.GeneralConfigure;
//import com.tel.autosysframework.views.TestVector;


public class IncrementDecrementAction extends org.eclipse.gef.ui.actions.SelectionAction
{
	private static final String
	INCREMENT_REQUEST = "Increment",  //$NON-NLS-1$
	DECREMENT_REQUEST = "Decrement";  //$NON-NLS-1$

	public static final String
	INCREMENT = "Increment",   //$NON-NLS-1$
	DECREMENT = "Decrement";   //$NON-NLS-1$

	Request request;
	AutosysDiagramEditPart adep;

	private static boolean run = false;

	public IncrementDecrementAction(IWorkbenchPart part, boolean increment) {
		super(part);
		if (increment) {
			request = new Request(INCREMENT_REQUEST);
			setText("Increment");
			setId(INCREMENT);
			setToolTipText("Increment EditPart");
			setImageDescriptor(
					ImageDescriptor.createFromFile(AutosysPlugin.class,"icons/plus.gif")); //$NON-NLS-1$
		} else {
			request = new Request(DECREMENT_REQUEST);
			setText("Decrement");
			setId(DECREMENT);
			setToolTipText("Decrement EditPart");
			setImageDescriptor(
					ImageDescriptor.createFromFile(AutosysPlugin.class,"icons/minus.gif")); //$NON-NLS-1$
		}
		setHoverImageDescriptor(getImageDescriptor());
	}

	protected boolean calculateEnabled() {		
		return canPerformAction();
	}

	private boolean canPerformAction() {
		EditPart part = null;
		if (getSelectedObjects().isEmpty())	{		
			return false;
		}		

		List parts = getSelectedObjects();

		for (int i=0; i<parts.size(); i++){
			Object o = parts.get(i);
			if (!(o instanceof EditPart))			
				return false;	
			part = (EditPart)o;		
		}	

		if(Configure.comp != null) {
			Configure.count = !Configure.count;
			if(Configure.count == true)
				new Configure().createPartControl(Configure.comp, part, true);
		}
		run = !run;
		/*if(run){
			if(TestVector.composite != null){
				new TestVector().createPartControl(TestVector.composite, part);
			}

			if(GeneralConfigure.composite != null){
				GeneralConfigure.count = !GeneralConfigure.count;
				if(GeneralConfigure.count == true)
					new GeneralConfigure().createLTEPartControl(GeneralConfigure.composite, true);
			}
		}*/



		return true;
	}

	private Command getCommand() {
		List editparts = getSelectedObjects();
		CompoundCommand cc = new CompoundCommand();
		cc.setDebugLabel("Increment/Decrement EdirParts");//$NON-NLS-1$
		for (int i=0; i < editparts.size(); i++) {
			EditPart part = (EditPart)editparts.get(i);
			cc.add(part.getCommand(request));
		}
		return cc;
	}

	public void run() {
		execute(getCommand());
	}

}

