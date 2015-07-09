package com.odcgroup.visualrules.integration.ui.action.mdf;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.mdf.generation.rules.RuleHelper;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.template.Template;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class AbstractRuleDeleteAction extends Action implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	private Template template;
	
	public AbstractRuleDeleteAction(Template template) {
		this.template = template;
		setText(template.getName());
	}

	private MdfModelElement getMdfModelElement() {
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if(structuredSelection.size()==1) {
					if(structuredSelection.getFirstElement() instanceof MdfModelElement) {
						MdfModelElement element = (MdfModelElement) structuredSelection.getFirstElement();
						return element;
					}
				}
			}
		}
		return null;
	}

	public void run(IAction action) {

		if(window==null) {
			window = RulesIntegrationUICore.getDefault().getWorkbench().getActiveWorkbenchWindow(); 
		}
		MdfModelElement modelElement = getMdfModelElement();
		if(modelElement instanceof EObject) {
			URIConverter converter = ((EObject)modelElement).eResource().getResourceSet().getURIConverter();
			if(!(converter instanceof ModelURIConverter)) {
				RulesIntegrationUICore.getDefault().logError("Domain model is not in an OfsProject ResourceSet!", null);
			}
 			ModelURIConverter modelConverter = (ModelURIConverter) converter;
 			final IProject project = modelConverter.getOfsProject().getProject();
			final String qualifiedRuleName = getQualifiedRuleName(modelElement);

			try {
				RulesIntegrationPlugin.deleteRule(project, qualifiedRuleName);
				return;
			} catch (CoreException e) {
				RulesIntegrationUICore.getDefault().logError("Error deleting rule", e);
				MessageDialog.openError(window.getShell(), "Error deleting rule", e.getMessage() +
						"\n\nPlease check for details in the log file!");
			}
		}
	}

	public void run() {
		run(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	protected String getQualifiedRuleName(MdfModelElement modelElement) {
		return  modelElement.getQualifiedName().getDomain() + "/"  + 
				modelElement.getName() + "/" +
				template.getType() + "/" + RuleHelper.DEFAULT_RULE_NAME;
	}

}
