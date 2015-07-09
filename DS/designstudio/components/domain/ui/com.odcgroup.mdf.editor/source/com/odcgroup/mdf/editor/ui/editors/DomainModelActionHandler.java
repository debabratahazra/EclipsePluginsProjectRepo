package com.odcgroup.mdf.editor.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.editor.security.MdfPermission;
import com.odcgroup.mdf.editor.ui.actions.DeriveFromBaseClassAction;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.security.DSAuthorizationResult;
import com.odcgroup.workbench.security.SecurityCore;

public class DomainModelActionHandler {
	
	
	private DeriveFromBaseClassAction copyBaseClassAction = null;
	private IEditorPart activeEditorPart = null;

	public DomainModelActionHandler(DeriveFromBaseClassAction copyBaseClassAction, IEditorPart activeEditorPart) {
		this.copyBaseClassAction = copyBaseClassAction;
		this.activeEditorPart = activeEditorPart;
	}

	protected Collection generateActions(Collection descriptors, ISelection selection) {
		Collection actions = new ArrayList();
		if (descriptors != null) {
			for (Iterator i = descriptors.iterator(); i.hasNext();) {
				actions.add(new CreateModelAction(activeEditorPart, selection, i.next()));
			}
		}
		return actions;
	}
	
	protected Collection defaultActions(Collection descriptors, ISelection selection, Collection actions) {
		if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1) {
			Object object = ((IStructuredSelection) selection).getFirstElement();
			EditingDomain domain = ((IEditingDomainProvider) activeEditorPart).getEditingDomain();
			if (object instanceof MdfDataset) {
				copyBaseClassAction = new DeriveFromBaseClassAction(activeEditorPart, domain, selection);
				actions.add(copyBaseClassAction);
			} else {
				copyBaseClassAction = null;
			}
		}

		// DS-1773 check if actions need to be disabled
		EditingDomain domain = ((IEditingDomainProvider) activeEditorPart).getEditingDomain();
		URIConverter uriConverter = domain.getResourceSet().getURIConverter();
		if (uriConverter instanceof ModelURIConverter) {
			ModelURIConverter modelUriConverter = (ModelURIConverter) uriConverter;
			IProject project = modelUriConverter.getOfsProject().getProject();
			Object object = ((IStructuredSelection) selection).getFirstElement();
			if (object instanceof EObject) {
				EObject eObj = (EObject) object;
				if (eObj.eResource() != null) {
					URI uri = eObj.eResource().getURI();
					if(domain.isReadOnly(eObj.eResource()) ||
							SecurityCore.getAuthorizationManager().permissionGranted(project, uri,
							MdfPermission.CONTEXT_MENU_NEW, null) == DSAuthorizationResult.REJECTED) {
						for (Object action : actions) {
							((IAction) action).setEnabled(false);
						}
					}
				}
			}
		}
		return actions;
	}
	
	

}
