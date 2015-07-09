package com.odcgroup.t24.enquiry.properties.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.editor.model.MdfUtility;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.IContentAssistProvider;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.MdfEntitySelectionDialog;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.workbench.core.xtext.XtextResourceSetProviderDS;

public final class EnquiryDialogUtil {

	private EnquiryDialogUtil() {
	}

	private static IProject getActiveProject() {
		IProject project = null;
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart != null) {
			IFileEditorInput input = (IFileEditorInput) editorPart.getEditorInput();
			IFile file = input.getFile();
			project = file.getProject();
		}
		return project;
	}
	
	public static MdfClass selectApplication(Shell shell) {
		ResourceSet rs = XtextResourceSetProviderDS.newXtextResourceSet(XtextResourceSet.class, getActiveProject());
		IContentAssistProvider contentAssistProvider = MdfUtility.getContentAssistProviderForDomainClasses(rs);
		MdfEntitySelectionDialog dialog = MdfEntitySelectionDialog.createDialog(shell, contentAssistProvider,true,"enquiry");
		MdfClass application = null;
		if (Window.OK == dialog.open()) {
			application = (MdfClass) dialog.getFirstResult();
			application = (MdfClassImpl) EcoreUtil2.resolve((EObject) application, rs);
		}
		return application;
	}

	public static String selectApplicationName(Shell shell, boolean fullName) {
		MdfClass application = selectApplication(shell);
		String name = null;
		if (application != null) {
			if (fullName) {
				name = application.getParentDomain().getName() + ":"+ T24Aspect.getT24Name(application);
			} else {
				name = T24Aspect.getT24Name(application);
			}
		}
		return name;
	}

}
