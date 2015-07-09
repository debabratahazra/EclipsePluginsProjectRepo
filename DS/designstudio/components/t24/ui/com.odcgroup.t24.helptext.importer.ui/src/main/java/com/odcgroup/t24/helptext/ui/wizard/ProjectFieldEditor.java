package com.odcgroup.t24.helptext.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.t24.helptext.ui.providers.ModelProjectLabelProvider;

public class ProjectFieldEditor extends StringButtonFieldEditor {
	private IProject selectedProject =null;
	private IProject[] projectList;
	protected String changePressed() {
		ProjectSelectionDialog dialog= new ProjectSelectionDialog(getShell() , new ModelProjectLabelProvider());
		dialog.setTitle("Project Selection");
		dialog.setMessage("Select the Target Project from the list of Models Projects");
		projectList=ResourcesPlugin.getWorkspace().getRoot().getProjects();
		dialog.setElements(projectList);
		dialog.open();
		selectedProject=(IProject)dialog.getFirstResult();
		if(selectedProject!=null){
		return selectedProject.getLocation().toOSString();
		}
		return "";
	}

	public ProjectFieldEditor(String name , String labelText , Composite parent) {
	    super(name , labelText , parent);

	}
	
        public void setSelectedProject(IProject project){
            this.selectedProject = project;
        }
	public IProject getSelectedProject() {
		return selectedProject;
	}
}
