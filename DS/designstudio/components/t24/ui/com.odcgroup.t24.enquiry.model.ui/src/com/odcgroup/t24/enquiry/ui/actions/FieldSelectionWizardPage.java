package com.odcgroup.t24.enquiry.ui.actions;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.mdf.editor.ui.dialogs.ModelElementMultiTreeSelectionUI;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.ui.util.FieldHelper;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeContentProvider;
import com.odcgroup.t24.mdf.editor.ui.ApplicationTreeLabelProvider;

public class FieldSelectionWizardPage extends WizardPage{
	private ModelElementMultiTreeSelectionUI ui=null;
	private MdfEntity entity;
	/**
	 * @param pageName
	 */
	protected FieldSelectionWizardPage(String pageName) {
		super(pageName);
		setPageComplete(true);
		setTitle("Add fields to enquiry");
		setDescription("Add fields:");
	}

	@Override
	public void createControl(Composite parent) {
		ui =  new ModelElementMultiTreeSelectionUI(true);
		ui.setContentProvider(new ApplicationTreeContentProvider());
		Control container = ui.createUI(parent);
    	setControl(container);
	}
   
	public void setApplication(MdfEntity entity){
		this.entity = entity;
		ui.setLabelProvider(new ApplicationTreeLabelProvider((MdfClass)entity));
		ui.setSelectionLabelProvider(new ApplicationTreeLabelProvider((MdfClass)entity));
		ui.setInput(entity);
	}
	
	@SuppressWarnings("unchecked")
	public void addFields(Enquiry enquiry, IProject project) {
		IStructuredSelection selection = ui.getSelection();
		if (selection != null) {
			List<MdfProperty> selectedProperties = (selection).toList();
			FieldHelper helper = new FieldHelper(project);
			helper.manageFields(enquiry, (MdfClass) entity, selectedProperties.toArray(new MdfProperty[0]));
		}
	}	
	
	@Override
	public boolean isPageComplete() {
        return true;
	}
}
