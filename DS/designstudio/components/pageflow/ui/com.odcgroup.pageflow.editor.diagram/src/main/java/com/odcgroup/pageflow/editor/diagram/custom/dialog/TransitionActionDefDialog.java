package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.CompoundBrowsePropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.RadioGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class TransitionActionDefDialog extends AbstractPropertyDefinitionDialog {
	
	private static final String VALIDATION = "Implements a Validation process Class ";
	private static final String PREV_STATE = "Go back to previous state";
	private static final String CONTINUE = "Continue";

	/**
	 * @param parentShell
	 * @param property
	 * @param update
	 */
	public TransitionActionDefDialog(Shell parentShell, EObject property, EObject parent, boolean update) {
		super(parentShell, property, parent, update);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyDefinitionDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(500, 600);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyDefinitionDialog#configureProperties()
	 */
	@Override
	protected void configureProperties() {
		PageflowPackage ePackage = PageflowPackage.eINSTANCE;
		
		SimpleGroupWidget group = new SimpleGroupWidget(" "+Messages.TransitionActionValidationGrp+" ");
		group.setFillBoth(false);
		CheckboxPropertyWidget chkProperty = new CheckboxPropertyWidget(ePackage.getTransitionAction_ProblemManagement(), 
				VALIDATION, true, ProblemManagement.VALIDATION_LITERAL, ProblemManagement.CONTINUE_LITERAL);
		group.addPropertyFeature(chkProperty);
		this.addPropertyFeature(group);
		
		BrowsePropertyWidget actionName = new BrowsePropertyWidget(ePackage.getAction_Name(), Messages.TransitionActionNameLabel);
		actionName.setBrowseOnly(false);
		actionName.setSelectionDialogCreator(new ProcessSelectionDialogCreator());
		
		CompoundBrowsePropertyWidget browseGroup = new CompoundBrowsePropertyWidget(" "+Messages.TransitionActionDefGroup+" ", null);
		browseGroup.setBrowseWidget(actionName);
		SimpleTextWidget actionUri = new SimpleTextWidget(ePackage.getAction_Uri(), Messages.TransitionActionURILabel);
		actionUri.setFillHorizontal(true);
		browseGroup.addPropertyFeature(actionUri);	
		
		SimpleTextWidget actionDesc = new SimpleTextWidget(ePackage.getAction_Desc(), Messages.TransitionActionDescLabel);
		actionDesc.setMultiline(true);
		browseGroup.addPropertyFeature(actionDesc);
		
		this.addPropertyFeature(browseGroup);
		
		SimpleGroupWidget propertiesGroup = new SimpleGroupWidget(" Properties ");
		TablePropertyWidget tProperty = new TablePropertyWidget(ePackage.getAction_Property(), "Properties");
		tProperty.setSortable(true);
		propertiesGroup.addPropertyFeature(tProperty);
		
		this.addPropertyFeature(propertiesGroup);
		
		SimpleGroupWidget group2 = new SimpleGroupWidget("");
		group.setFillBoth(false);
		CheckboxPropertyWidget chkStopOnFailureProperty = new CheckboxPropertyWidget(ePackage.getAction_StopOnFailure(), 
				"Stop on Failure", Boolean.TRUE);
		group2.addPropertyFeature(chkStopOnFailureProperty);
		this.addPropertyFeature(group2);
		
		RadioGroupWidget problemMgt = new RadioGroupWidget(ePackage.getTransitionAction_ProblemManagement(),
				" "+Messages.TransitionActionErrorGrp+" ", null);
		problemMgt.addRadio(PREV_STATE, ProblemManagement.BACK_LITERAL);
		problemMgt.addRadio(CONTINUE, ProblemManagement.CONTINUE_LITERAL);
		this.addPropertyFeature(problemMgt);
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog#validate(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean validate(EObject element) {
		Object obj = element.eGet(PageflowPackage.eINSTANCE.getAction_Name());
		if (obj == null) {
			setErrorMessage(Messages.TransitionActionDefNameAlert);
			return false;
		}
		obj = element.eGet(PageflowPackage.eINSTANCE.getAction_Uri());
		if (obj == null) {
			setErrorMessage(Messages.TransitionActionDefURIAlert);
			return false;
		}
		setErrorMessage(null);
		return true;
	}	

}
