package com.odcgroup.visualrules.integration.ui.action.mdf;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.mdf.generation.rules.RuleHelper;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.generation.VisualRulesCodeGenerator;
import com.odcgroup.visualrules.integration.init.RulesInitializer;
import com.odcgroup.visualrules.integration.template.Template;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.ui.OfsUICore;

public class RuleFromTemplateAction extends Action implements IWorkbenchWindowActionDelegate {

	protected IWorkbenchWindow window;
	protected Template template;
	
	public RuleFromTemplateAction(Template template) {
		this.template = template;
		setText(template.getName());
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				RulesIntegrationUICore.PLUGIN_ID, "icons/rule.gif"));
	}
	
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run() {
		run(this);
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	protected MdfModelElement getMdfModelElement() {
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
		final MdfModelElement modelElement = getMdfModelElement();
		if(modelElement instanceof EObject) {
			URIConverter converter = ((EObject)modelElement).eResource().getResourceSet().getURIConverter();
			if(!(converter instanceof ModelURIConverter)) {
				RulesIntegrationUICore.getDefault().logError("Domain model is not in an OfsProject ResourceSet!", null);
			}
 			ModelURIConverter modelConverter = (ModelURIConverter) converter;
 			final IProject project = modelConverter.getOfsProject().getProject();
			final String qualifiedRuleName = getQualifiedRuleName(modelElement);

			if(!RulesIntegrationPlugin.isRulesEnabled(project)) {
				boolean enableRules = MessageDialog.openQuestion(window.getShell(), "Cannot create rule", 
						"Rule support has not yet been enabled for this project." +
				"\n\nDo you want to enable it now?");
				if(enableRules) {
					IRunnableWithProgress runnable = new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException,
								InterruptedException {
							monitor.beginTask("Enabling rule support...", 3);
							monitor.subTask("Activating rule cartridge");
							ProjectPreferences preferences = new ProjectPreferences(project, GenerationCore.PLUGIN_ID);
					        preferences.putBoolean(VisualRulesCodeGenerator.CARTRIDGE_ID, true);
					        preferences.flush();
					        monitor.worked(1);
					        try {
								monitor.subTask("Updating core project configuration");
								(new OfsProjectInitializer()).updateConfiguration(project, monitor);
								monitor.worked(1);
								monitor.subTask("Updating rule-specific project configuration");
					        	(new RulesInitializer()).updateConfiguration(project, monitor);
								monitor.done();
							} catch (CoreException e) {
								throw new InvocationTargetException(e);
							}
						}
					};
					try {
						PlatformUI.getWorkbench().getProgressService().run(true, false, runnable);
					} catch (InvocationTargetException e) {
						RulesIntegrationUICore.getDefault().logError("Error enabling rule support", e);
						MessageDialog.openError(window.getShell(), "Error enabling rule support", e.getTargetException().getMessage() +
								"\n\nPlease check for details in the log file!");
						return;
					} catch (InterruptedException e) {
						// not cancellable, only on system shutdown
						return;
					}
				} else {
					return;
				}
			}
			if(RulesIntegrationPlugin.projectHasConfigurationErrors(project)) {
				MessageDialog.openError(window.getShell(), "Error creating/opening rule",
				"There are errors in your project configuration.\n\nPlease fix them before working with rules!");
				return;
			}

			try {
				RulesIntegrationUICore.openRule(project, qualifiedRuleName);
				return;
			} catch (CoreException e) {
				// most likely the rule does not exist, so it needs to be created first.
				// nothing to do here
			}
			
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					try {
						template.init(modelElement);
						RulesIntegrationPlugin.createRuleFromTemplate(
								project, 
								qualifiedRuleName,
								template.getDesc(),
								template.getQualifiedTemplateName(),
								template.getParams());
					} catch (CoreException e) {
						throw new InvocationTargetException(e, e.getMessage());
					}
				}
			};
			try {
				PlatformUI.getWorkbench().getProgressService().busyCursorWhile(runnable);
				RulesIntegrationUICore.openRule(project, qualifiedRuleName);
			} catch (InvocationTargetException e) {				
				RulesIntegrationUICore.getDefault().logError("Error creating new rule", e);
				MessageDialog.openError(window.getShell(), "Error creating new rule", e.getMessage() +
						"\n\nPlease check for details in the log file!");
			} catch (InterruptedException e) {
				// not cancellable, only on system shutdown
				return;
			} catch (CoreException e) {
				RulesIntegrationUICore.getDefault().logError("Error opening new rule", e);
				MessageDialog.openError(window.getShell(), "Error opening new rule", e.getMessage() +
						"\n\nPlease check for details in the log file!");
			}

		}
	}
	
	protected IFile getFile(URI uri) {
		String scheme = uri.scheme();
		if ("platform".equals(scheme) && uri.segmentCount() > 1 && "resource".equals(uri.segment(0))) {
			StringBuffer platformResourcePath = new StringBuffer();
			for (int j = 1, size = uri.segmentCount(); j < size; ++j) {
				platformResourcePath.append('/');
				platformResourcePath.append(uri.segment(j));
			}
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformResourcePath.toString()));
		}
		return null;
	}
	
	protected String getQualifiedRuleName(MdfModelElement modelElement) {
		return  modelElement.getQualifiedName().getDomain() + "/"  + 
				modelElement.getName() + "/" +
				template.getType() + "/" + RuleHelper.DEFAULT_RULE_NAME;
	}
}
