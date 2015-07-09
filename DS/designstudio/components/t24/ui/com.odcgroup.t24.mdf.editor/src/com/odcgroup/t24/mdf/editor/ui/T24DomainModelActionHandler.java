package com.odcgroup.t24.mdf.editor.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.editor.ui.actions.DeriveFromBaseClassAction;
import com.odcgroup.mdf.editor.ui.editors.CreateModelAction;
import com.odcgroup.mdf.editor.ui.editors.DomainModelActionHandler;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.mdf.editor.ui.actions.T24DomainNewAttributeAction;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

public class T24DomainModelActionHandler extends DomainModelActionHandler {

	private IEditorPart activeEditorPart = null;

	public T24DomainModelActionHandler(DeriveFromBaseClassAction copyBaseClassAction, IEditorPart activeEditorPart) {
		super(copyBaseClassAction, activeEditorPart);
		this.activeEditorPart = activeEditorPart;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Collection generateActions(Collection descriptors, ISelection selection) {
		MdfDomainImpl mdfDomain = null;
		if(selection instanceof TreeSelection) {
			TreePath[] paths = ((TreeSelection)selection).getPaths();
			for (TreePath treePath : paths) {
				if(treePath.getSegmentCount() > 2 && treePath.getSegment(treePath.getSegmentCount()-2) instanceof MdfClass) {
					return null;
				}
				if(mdfDomain == null)
					mdfDomain = (MdfDomainImpl)treePath.getFirstSegment();
			}
		}
		Collection actions = new ArrayList();
		if (descriptors != null) {
			for (Iterator i = descriptors.iterator(); i.hasNext();) {
				CommandParameter param = (CommandParameter)i.next();				
				if(param.getValue() instanceof MdfAttributeImpl && T24Aspect.getLocalRefApplications(mdfDomain)) {
						EditingDomain domain = ((IEditingDomainProvider) activeEditorPart).getEditingDomain();
						IOfsProject project = OfsResourceHelper.getOfsProject(domain.getResourceSet().getResources().get(0));
						T24DomainNewAttributeAction attributeAction = new T24DomainNewAttributeAction(activeEditorPart, selection, project, domain);
						actions.add(attributeAction);
				} else {				
					actions.add(new CreateModelAction(activeEditorPart , selection, param));					
				}
			}
		}
		return actions;
	}

}
