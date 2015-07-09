package com.odcgroup.pageflow.editor.diagram.custom.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

import com.odcgroup.pageflow.model.DecisionState;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.InitState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.workbench.editors.ui.ResourceUtil;

public class PageflowCreateElementCommand extends CreateElementCommand {

	/**
	 * The element type to be created.
	 */
	private final IElementType elementType;
	
	private String message = "";

	/**
	 * @param request
	 */
	public PageflowCreateElementCommand(CreateElementRequest request) {
		super(request);
		this.elementType = request.getElementType();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand#doDefaultElementCreation()
	 */
	protected EObject doDefaultElementCreation() {
		EReference containment = getContainmentFeature();
		EClass eClass = getElementType().getEClass();

		if (containment != null) {
			EObject element = getElementToEdit();
			if (element != null && element instanceof Pageflow){
				Pageflow pageflow = (Pageflow)element;
				EList states = pageflow.getStates();
				for(Iterator iter = states.iterator();iter.hasNext();){
					State state = (State)iter.next();
					// only one init state is allowed
					if (state instanceof InitState && eClass.getInstanceClass().equals(InitState.class)){
						message = "Only one init state is allowed per pageflow!!!";
						return null;
					} 
				}
				//added for issue OFSFMK-681
				String name = getUniqueId(states, eClass, "name");
				String id = getUniqueId(states, eClass, "id");
				String decisionID = getUniqueId(states, eClass, "decisionId");
				// creae a default key for each of the state
				EObject ret = EMFCoreUtil.create(element, containment, eClass);
				if (ret instanceof EndState){
					EndState endState = (EndState)ret;
					endState.setName(id);
					endState.setDisplayName(name);
					endState.setId(getEndStateId(getDomainName(pageflow),pageflow.getName(),endState.getName()));
					return endState;
				}  else if (ret instanceof InitState){
					InitState init = (InitState)ret;
					init.setName(eClass.getName());
					init.setDisplayName(eClass.getName());
					return init;
				} else if (ret instanceof DecisionState){
					DecisionState decision = (DecisionState)ret;
					decision.setName(decisionID);
					decision.setDisplayName(name);
					return decision;
				} else {
					State state = (State)ret;
					state.setName(id);
					state.setDisplayName(name);
					return state;
					
				}
			}		
				
		}

		return null;
	}
	// modified for issue OFSFMK-681
	private String getUniqueId(List states, EClass eclass, String type){
		int i = 0;
		for(Iterator iter = states.iterator();iter.hasNext();){
			State state = (State)iter.next();
			if (state.eClass().equals(eclass)) {
				i++;
			}
		}
		return checkUnique(states, i, eclass, type);
	}
	// modified for issue OFSFMK-681
	private String checkUnique(List states, int i, EClass eclass, String type){
		String name = "";
		if (type.equalsIgnoreCase("id")){
			name = eclass.getName()+i;
		}else if (type.equalsIgnoreCase("name")){
			name = eclass.getName()+" ("+i+")";
		}else if (type.equalsIgnoreCase("decisionId")){
			name = "Gateway"+i;
		}
		
		boolean exists = false;
		for(Iterator iter = states.iterator();iter.hasNext();){
			State state = (State)iter.next();
			if (state.getName().equals(name)){
				exists = true;
				break;
			}
		}
		if (exists){
			return checkUnique(states, i+1, eclass, type);
		} else {
			return name;
		}
	}
	
	
	/**
	 * @param domainName
	 * @param pageflowName
	 * @param key
	 * @return
	 */
	protected String getEndStateId(String domainName, String pageflowName, String key) {
		 return "EndState" + "/" + domainName + "/" + pageflowName + "/" + key;
	}
	
	protected String getDomainName(EObject obj){
		IFile pageflow = ResourceUtil.getFile(obj);
		IProject project = pageflow.getProject();
		return project.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor,
	 *      org.eclipse.core.runtime.IAdaptable)
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		EObject newElement = doDefaultElementCreation();
		
		if (newElement == null){
			MessageBox messageBox =
				   new MessageBox(Display
							.getCurrent().getActiveShell(),
				    SWT.OK|
				    SWT.ICON_ERROR);
				 messageBox.setMessage(message);
				 messageBox.open();

			monitor.setCanceled(true);
			return new CommandResult(undo(monitor, info), newElement);			
		}		

		// Configure the new element
		ConfigureRequest configureRequest = createConfigureRequest();

		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);

		IStatus configureStatus = null;

		if (configureCommand != null && configureCommand.canExecute()) {
			configureStatus = configureCommand.execute(monitor, info);
		}

		// Put the newly created element in the request so that the
		// 'after' commands have access to it.
		getCreateRequest().setNewElement(newElement);

		return (configureStatus == null) ? CommandResult
				.newOKCommandResult(newElement) : new CommandResult(
				configureStatus, newElement);
	}

}
