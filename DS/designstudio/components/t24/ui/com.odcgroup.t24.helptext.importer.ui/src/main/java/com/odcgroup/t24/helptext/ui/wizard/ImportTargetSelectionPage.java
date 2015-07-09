package com.odcgroup.t24.helptext.ui.wizard;



import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.workbench.core.OfsCore;

public class ImportTargetSelectionPage extends WizardPage {
    
	private ProjectFieldEditor editor=null;
    
	public ImportTargetSelectionPage(String pageName) {
		super(pageName);
		
	}
   
	public void createControl(Composite parent) {
	    Composite targetSelectionArea = new Composite(parent , SWT.NONE);
	    GridData targetSelectionData = new GridData(GridData.FILL_BOTH);
	    targetSelectionArea.setLayoutData(targetSelectionData);
	    GridLayout folderSelectionLayout = new GridLayout();
	    folderSelectionLayout.numColumns = 1;
	    folderSelectionLayout.makeColumnsEqualWidth = true;
	    folderSelectionLayout.marginWidth = 0;
	    folderSelectionLayout.marginHeight = 0;
	    targetSelectionArea.setLayout(folderSelectionLayout);
	    editor=new ProjectFieldEditor("Select Target Project", "Select Target Project :" , targetSelectionArea);
	    editor.setChangeButtonText("Browse...");
	    final Text text=editor.getTextControl(targetSelectionArea);
	    text.addModifyListener(new ModifyListener() {
		public void modifyText(ModifyEvent e) {
		    if(!text.getText().isEmpty()) {
			setPageComplete(true);
			getContainer().updateButtons();
		    }else {
			setPageComplete(false);
			getContainer().updateButtons();
		    }
		}
	    });
	    IProject[] projectList=ResourcesPlugin.getWorkspace().getRoot().getProjects();
	    for(IProject project:projectList){
		if(OfsCore.isOfsProject(project)){
		    text.setText(project.getLocation().toString());
		    editor.setSelectedProject(project);
		    break;
		}
	    }
	    setDescription("Select the Target Project from the list of Models Projects");
	    setControl(targetSelectionArea);
	    text.setEditable(false);
	    if(StringUtils.isNotEmpty(text.getText())){
	      setPageComplete(true);
	    }else {
	     setPageComplete(false);
	    }
	}
        
	public IProject getTargetProject(){
		return editor.getSelectedProject();
	}
}
