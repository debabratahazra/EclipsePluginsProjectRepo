package com.temenos.t24.tools.eclipse.basic.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.macrotemplate.TemplateActions;

/**
 * A wizard for selecting a BASIC module template
 */
public class SelectTemplateWizard extends Wizard implements INewWizard {
   private IStructuredSelection initialSelection;

   private SelectTemplateWizardPage selectTemplatePage;
   private IT24ViewItem templateSelected = null; 
   private String projectFullPath = null;
   
   /**
    * Construct a new instance and initialise the dialog settings
    * for this instance.
    */
   public SelectTemplateWizard() {
   }
   
   /**
    * Initialises this creation wizard using the passed workbench and
    * object selection. This method is called after the no argument
    * constructor and before other methods are called.
    * @param workbench the current workbench
    * @param selection the current object selection
    */
   public void init(IWorkbench workbench, IStructuredSelection selection){
      initialSelection = selection;
      getSelectedProject();
      
   }

   /**
    * Adds the wizard pages that comprise this wizard
    */
   @Override
   public void addPages() {
      setWindowTitle("Extract");
      selectTemplatePage = new SelectTemplateWizardPage();
      addPage(selectTemplatePage);
      selectTemplatePage.init(initialSelection);
   }

   /**
    * This method is called by the wizard framework when the user
    * presses the Finish button.
    */
   @Override
   public boolean performFinish() {
       // If a template has been selected and has not yet been opened then open it. 
       if(!selectTemplatePage.isTemplateAlreadyOpenend()){
           templateSelected = selectTemplatePage.getTemplateSelected();
           TemplateActions ta = new TemplateActions();
           ta.openTemplate(templateSelected,projectFullPath);
       }
       return true;
   }
   
   
   /**
    * Gets the project path where the TEMPLATE files are to be
    * created.    */
    private void getSelectedProject() {
        if (initialSelection != null && initialSelection instanceof IStructuredSelection) {
            Object objectSelected = (Object) ((IStructuredSelection) initialSelection).getFirstElement();
            if (objectSelected == null) {
                projectFullPath = EclipseUtil.getTemporaryProject().toOSString();
            }
            if (objectSelected != null) {
                if (objectSelected instanceof IResource) {
                    IResource resource = ((IResource) objectSelected);
                    if (resource instanceof IFolder) {
                        projectFullPath = resource.getLocation().toOSString();
                    } else if (resource instanceof IProject) {
                        projectFullPath = resource.getProject().getLocation().toOSString();
                    } else {
                        projectFullPath = resource.getLocation().removeLastSegments(1).toOSString();
                    }
                } else if (objectSelected instanceof IPackageFragmentRoot) {
                    projectFullPath = ((IPackageFragmentRoot) objectSelected).getResource().getLocation().toOSString();
                } else if (objectSelected instanceof IPackageFragment) {
                    projectFullPath = ((IPackageFragment) objectSelected).getResource().getLocation().toOSString();
                }
            }
        }
    }
   
   
 
}
