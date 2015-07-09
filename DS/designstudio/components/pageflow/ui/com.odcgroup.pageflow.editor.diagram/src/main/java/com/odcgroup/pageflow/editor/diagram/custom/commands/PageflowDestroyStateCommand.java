package com.odcgroup.pageflow.editor.diagram.custom.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;

public class PageflowDestroyStateCommand extends DestroyElementCommand {
	
	private EditPart host;

	public PageflowDestroyStateCommand(DestroyElementRequest req, EditPart host) {
		super(req);
		this.host = host;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand#getElementToDestroy()
	 */
	protected EObject getElementToDestroy() {
		View view = (View) host.getModel();
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation != null) {
			return view;
		}
		return super.getElementToDestroy();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
    	throws ExecutionException {
		
		EObject destructee = getElementToDestroy();
		
		// get the associated transitions of the state to be destroyed
		if (destructee instanceof State){
			State state = (State)destructee;
			Pageflow pageflow = (Pageflow)state.eContainer();
			EList transitions = pageflow.getTransitions();
			Transition transition = null;
			List<Transition> transDel = new ArrayList<Transition>();
			for(Iterator iter= transitions.iterator();iter.hasNext();){
				transition = (Transition)iter.next();
				if (transition.getFromState().equals(state) || transition.getToState().equals(state)){
					transDel.add(transition);
				}				
			}
			// remove the associated transitions from the model
			if (transDel.size()>0){
				try {
					RemoveCommand.create(getEditingDomain(), transDel).execute();									
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return super.doExecuteWithResult(monitor, info);
	}



}
