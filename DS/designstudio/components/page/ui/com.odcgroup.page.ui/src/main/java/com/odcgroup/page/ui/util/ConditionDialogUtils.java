package com.odcgroup.page.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.common.types.access.jdt.IJavaProjectProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.ConditionUtils;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.workbench.el.engine.DSELContext;
import com.odcgroup.workbench.el.ui.dialog.DSELDialog;

public class ConditionDialogUtils {

	private static final URI dsexURI = URI.createURI("ConditionDialogUtils.dsex");
	private static final IResourceServiceProvider rsp = IResourceServiceProvider.Registry.INSTANCE.getResourceServiceProvider(dsexURI);
	private static final IJavaProjectProvider javaProjectProvider = rsp.get(IJavaProjectProvider.class); 

	public static String editCondition(Shell shell, final Property property) {
		if (property.eIsProxy())
			throw new IllegalArgumentException("Property still (or again) is an EMF proxy - when was it unloaded() why?");
			
		String result = property.getValue();

		String conditionLang = property.getWidget().getPropertyValue(PropertyTypeConstants.CONDITION_LANG);
		if("XSP".equals(conditionLang)) {
			MultiLineDialog dialog = new MultiLineDialog(shell, property);
			if (Dialog.OK == dialog.open()) {
				result = dialog.getText();
				//DS-5596 Changes to DS Script do not dirty the containing model
				setToModel(property, result);
			}
		} else if("DSEL".equals(conditionLang)) {
			Widget w = property.getWidget();
			
			DSELContext variables = ConditionUtils.getVariables(w);
			
			ResourceSet resourceSet = EcoreUtil2.getResourceSet(w);
			IJavaProject javaProject = getJavaProjectProvider().getJavaProject(resourceSet);
			IProject iProject = javaProject.getProject();
			
			DSELDialog dialog = new DSELDialog(shell, result, variables, iProject);
			if (Dialog.OK == dialog.open()) {
				result = dialog.getText();
				//DS-5596 Changes to DS Script do not dirty the containing model
				setToModel(property, result);
			}
		}
		return result;
	}

	private static IJavaProjectProvider getJavaProjectProvider() {
		if (javaProjectProvider == null)
			throw new IllegalStateException("IJavaProjectProvider not available in Injector");
		return javaProjectProvider;
	}

	/**
	 * Saves the result to the property
	 * @param property
	 * @param result
	 */
	private static void setToModel(final Property property, String result) {
		if (EclipseUtils.findActiveEditor().getEditorSite()!= null && EclipseUtils.findActiveEditor().getEditorSite().getPart()!=null) {
			CommandStack stack = (CommandStack) (EclipseUtils
					.findActiveEditor().getEditorSite().getPart())
					.getAdapter(CommandStack.class);
			if (!result.equals(property.getValue())) {
				stack.execute(new UpdatePropertyCommand(property, result));
			}
		}
	}

}
