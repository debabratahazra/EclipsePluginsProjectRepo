package com.odcgroup.pageflow.editor.diagram.custom.properties;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.gmf.runtime.notation.impl.EdgeImpl;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.pageflow.model.Action;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.State;
import com.odcgroup.pageflow.model.Transition;

/**
 * @author pkk
 *
 */
public class PageflowPropertyViewLabelProvider extends LabelProvider {

	/**
	 * 
	 */
	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element == null || element.equals(StructuredSelection.EMPTY)) {
			return null;
		}
		if (element instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) element;
			if (areDifferentTypes(structuredSelection)) {
				return null;  
			}
			element = structuredSelection.getFirstElement();
		}
		if (element instanceof EditPart) {

			EditPart part = (EditPart) element;
			if (part.getModel() instanceof NodeImpl) {
				element = ((NodeImpl) part.getModel()).getElement();
			} else if (part.getModel() instanceof EdgeImpl) {
				element = ((EdgeImpl) part.getModel()).getElement();
			} else if (part.getModel() instanceof DiagramImpl) {
				element = ((DiagramImpl) part.getModel()).getElement();
			}
			if (element instanceof EObject || element instanceof Resource) {
				return getAdapterFactoryLabelProvider().getImage(element);
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element == null || element.equals(StructuredSelection.EMPTY)) {
			return null;
		}
		if (element instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) element;
			if (areDifferentTypes(structuredSelection)) {
				return structuredSelection.size() + " items selected";//$NON-NLS-1$
			}
			element = structuredSelection.getFirstElement();
		}
		if (element instanceof EditPart) {
			EditPart part = (EditPart) element;
			if (part.getModel() instanceof NodeImpl) {
				element = ((NodeImpl) part.getModel()).getElement();
			} else if (part.getModel() instanceof EdgeImpl) {
				element = ((EdgeImpl) part.getModel()).getElement();
			} else if (part.getModel() instanceof DiagramImpl) {
				element = ((DiagramImpl) part.getModel()).getElement();
			}
		}
		
		if ((element instanceof EObject) && (element instanceof Pageflow)) {
			Pageflow graph = (Pageflow)element;
			String displayName = graph.getName();
			String val = getAdapterFactoryLabelProvider().getText(element);
			if (val != null && displayName != null){
				val.substring(val.indexOf(displayName));
				return val.substring(0, val.indexOf(displayName))+'\u005B'+" "+displayName+" "+'\u005D';
			} else {
				return "Pageflow";
			}
		} else if ((element instanceof EObject) && (element instanceof State)) {
			State item = (State)element;
			String displayName = item.getName();
			String desc = item.getDesc();
			String val = getAdapterFactoryLabelProvider().getText(element);
			if (desc != null && val.endsWith(desc)){
				val = val.substring(0, val.length()-desc.length());
			}
			return val+" "+'\u005B'+" "+displayName+" "+'\u005D';
		} else if ((element instanceof EObject) && (element instanceof Transition)){
			Transition transition = (Transition)element;
			String displayName = transition.getName();
			String val = getAdapterFactoryLabelProvider().getText(element);
			if (displayName != null){
				val.substring(val.indexOf(displayName));
				return val.substring(0, val.indexOf(displayName))+'\u005B'+" "+displayName+" "+'\u005D';
			} else {
				return val+" "+'\u005B'+"  "+'\u005D';
			}
		} else if ((element instanceof EObject) && (element instanceof Action)){
			Action action = (Action)element;
			String displayName = action.getName();
			String val = getAdapterFactoryLabelProvider().getText(element);
			if (displayName != null) {
				val.substring(val.indexOf(displayName));
				return val.substring(0, val.indexOf(displayName))+'\u005B'+" "+displayName+" "+'\u005D';
			} else {
				return val+" "+'\u005B'+" "+'\u005D';
			}
		} else if (element instanceof Resource) {
			return "\u00ABResource\u00BB";//$NON-NLS-1$
		}
		return null;
	}

	/**
	 * @return
	 */
	private AdapterFactoryLabelProvider getAdapterFactoryLabelProvider() {
		if (adapterFactoryLabelProvider == null) {
			adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(PageflowDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());
		}
		return adapterFactoryLabelProvider;
	}

	/**
	 * Determine there are objects in the structured selection of different
	 * types.
	 * 
	 * @param structuredSelection
	 *            the structured selection.
	 * @return true if there are objects of different types in the selection.
	 */
	private boolean areDifferentTypes(IStructuredSelection structuredSelection) {
		if (structuredSelection.size() == 1) {
			return false;
		}
		Iterator i = structuredSelection.iterator();
		Object element = i.next();
		for (; i.hasNext();) {
			if (i.next().getClass() != element.getClass()) {
				return true;
			}
		}

		return false;
	}
}

