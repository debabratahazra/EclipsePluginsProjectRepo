package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.EndState;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.Transition;
import com.odcgroup.pageflow.model.TransitionMapping;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class TransitionMappingDefinitionDialog extends AbstractTitleAreaDialog {
	
	protected TransitionMapping transitionMapping;
	protected Combo endStateCombo;
	protected Combo transitionCombo;
	protected SubPageflowState subPageflow;
	protected Pageflow pageflow;
	
	private static final String COMBO_DEFAULT = "--";		
	
	/**
	 * @param parentShell
	 * @param addCommand
	 */
	public TransitionMappingDefinitionDialog(Shell parentShell, AddCommand addCommand) {
		super(parentShell);
		Collection collection = addCommand.getCollection();
		this.subPageflow = (SubPageflowState)addCommand.getOwner();
		this.pageflow = (Pageflow) command.getOwner().eContainer();
		this.transitionMapping = (TransitionMapping) collection.iterator().next();
		this.domain = addCommand.getDomain();
	}
	
	/**
	 * @param parentShell
	 */
	public TransitionMappingDefinitionDialog(Shell parentShell){
		super(parentShell);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog#setCommand(org.eclipse.emf.edit.command.AddCommand)
	 */
	public void setCommand(AddCommand command) {
		Collection collection = command.getCollection();
		this.pageflow = (Pageflow) command.getOwner().eContainer();
		this.subPageflow = (SubPageflowState)command.getOwner();
		this.transitionMapping = (TransitionMapping) collection.iterator().next();
		this.domain = command.getDomain();
	}
	/**
	 * @param parentShell
	 * @param addCommand
	 * @param update
	 */
	public TransitionMappingDefinitionDialog(Shell parentShell, AddCommand addCommand, boolean update){
		this(parentShell, addCommand);
		this.update = update;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(Messages.TransitionMappingDialogTitle);
		this.setMessage(Messages.TransitionMappingDialogMsg);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.TransitionMappingDialogTxt);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		 // create the composite to hold the widgets
		GridData gridData;
		Composite body = new Composite(composite, SWT.NULL);
		
		if (subPageflow.getSubPageflow() == null){			
			GridLayout layout = new GridLayout();
			int ncol = 1;
			layout.numColumns = ncol;
			layout.marginHeight = 10;
			layout.marginWidth = 15;
			body.setLayout(layout);
			new Label (body, SWT.NONE).setText(Messages.TransitionMappingNoSubPageflowAlert);
			return composite;
		}
	
	    // create the desired layout for this wizard page
		GridLayout layout = new GridLayout();
		int ncol = 2;
		layout.numColumns = ncol;
		layout.marginHeight = 10;
		layout.marginWidth = 15;
		body.setLayout(layout);
		
		// end states combo
		new Label (body, SWT.NONE).setText(Messages.TransitionMappingEndStateLabel);
		endStateCombo = new Combo(body, SWT.DROP_DOWN | SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		endStateCombo.setLayoutData(gridData);
		
		// set the endstates combo content
		try {
			subPageflow.getSubPageflow().eResource().unload();
			subPageflow.getSubPageflow().eResource().load(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List list = subPageflow.getSubPageflow().getStates();
		List<EndState> endStates = new ArrayList<EndState>();
		Object state = null;
		for(Iterator iter = list.iterator(); iter.hasNext();){
			state = iter.next();
			if (state instanceof EndState){
				endStates.add((EndState)state);				
			}
		}	

		String[] states = new String[endStates.size()+1];
		states[0] = COMBO_DEFAULT;
		for(int ii=0;ii<endStates.size();ii++){
			states[ii+1] = ((EndState)endStates.get(ii)).getDisplayName();
		}
		endStateCombo.setItems(states);
		if (transitionMapping != null && transitionMapping.getEndState() != null){
			endStateCombo.setText(transitionMapping.getEndState().getDisplayName());
		} else {
			endStateCombo.select(0);
		}
		
		// transitions combo
		new Label (body, SWT.NONE).setText(Messages.TransitionMappingOutTransitionLabel);
		transitionCombo = new Combo(body, SWT.DROP_DOWN | SWT.READ_ONLY);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		transitionCombo.setLayoutData(gridData);
		List<Transition> outTrans = getSubPageflowTransitions();
		String[] trans = new String[outTrans.size()+1];
		trans[0]= COMBO_DEFAULT;
		for(int ii=0;ii<outTrans.size();ii++){
			trans[ii+1] = ((Transition)outTrans.get(ii)).getDisplayName();
		}
		transitionCombo.setItems(trans);
		if (transitionMapping != null && transitionMapping.getTransition() != null){
			transitionCombo.setText(transitionMapping.getTransition().getDisplayName());
		} else {
			transitionCombo.select(0);
		}
		
		return composite;
	}
	
	/**
	 * @return
	 */
	private List<Transition> getSubPageflowTransitions() {
		List<Transition> transitions = pageflow.getTransitions();
		List<Transition> outTrans = new ArrayList<Transition>();
		for (Transition transition : transitions) {
			if (subPageflow.equals(transition.getFromState())){
				outTrans.add(transition);
			}
		}
		return outTrans;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (subPageflow.getSubPageflow() == null){
			return;
		}
		if (validate()) {		
			if (!update){
				transitionMapping.setEndState(getSelectedEndState());
				transitionMapping.setTransition(getSelectedTransition());
			} else {
				
				TransactionalEditingDomain domain = (TransactionalEditingDomain) this.domain;
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					protected void doExecute() {
						if (!transitionMapping.getEndState().equals(getSelectedEndState())){
							transitionMapping.setEndState(getSelectedEndState());
						}
						if (!transitionMapping.getTransition().equals(getSelectedTransition())){
							transitionMapping.setTransition(getSelectedTransition());
						}
					}
				});
				
			}
			super.okPressed();
		}
	}
	
	/**
	 * @return
	 */
	protected EndState getSelectedEndState() {
		List list = subPageflow.getSubPageflow().getStates();
		for (Object state : list) {
			if (state instanceof EndState){
				EndState end = (EndState)state;
				if (endStateCombo.getText().equals(end.getDisplayName())){
					return end;
				}
			}
		}
		return null;
	}
	
	/**
	 * @return
	 */
	protected Transition getSelectedTransition() {
		List<Transition> transitions = getSubPageflowTransitions();
		for (Transition transition : transitions) {
			if (transitionCombo.getText().equals(transition.getDisplayName())){
				return transition;
			}
		}
		return null;
	}
	
	/**
	 * @return
	 */
	private boolean validate() {
		if (endStateCombo.getText().equals(COMBO_DEFAULT)){
			setErrorMessage(Messages.TransitionMappingEndStateErrorMsg);
			return false;
		}
		if (transitionCombo.getText().equals(COMBO_DEFAULT)){
			setErrorMessage(Messages.TransitionMappingTransErrorMsg);
			return false;
		}
		EList mappings = subPageflow.getTransitionMappings();
		for (Object object : mappings) {
			if (object instanceof TransitionMapping){
				TransitionMapping mapping = (TransitionMapping)object;
				if (mapping.getEndState().equals(getSelectedEndState()) 
						&& mapping.getTransition().equals(getSelectedTransition())){
					setErrorMessage(Messages.TransitionMappingExistsMsg);
					return false;
				}
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		if (subPageflow.getSubPageflow() == null){
			createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		}else {
			super.createButtonsForButtonBar(parent);			
		}
	}
	

}
