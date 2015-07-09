package com.odcgroup.pageflow.editor.diagram.custom.pageintegration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.model.Model;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.ui.navigator.pageflow.PageflowAdapterFactoryProvider;
import com.odcgroup.workbench.ui.repository.ModelSelectionDialog;

/**
 * @author pkk
 *
 */
public class PageSelectionDialog extends ModelSelectionDialog  implements IPropertySelectionDialog {

	/**
	 * @param parent
	 * @param elementRenderer
	 * @param qualifierRenderer
	 */
	private PageSelectionDialog(Shell parent, ILabelProvider elementRenderer,
			ILabelProvider qualifierRenderer) {
		super(parent, elementRenderer, qualifierRenderer);
	}

    /**
     * @param parent
     * @param elements
     * @param adapterFactory
     * @return
     */
    public static PageSelectionDialog createDialog(Shell parent,
            Object[] elements) {
    	ComposedAdapterFactory adapterFactory = PageflowAdapterFactoryProvider.getAdapterFactory();
        final ILabelProvider elementRenderer = new AdapterFactoryLabelProvider(
                adapterFactory){
        	public String getText(Object element) {
        		if (element instanceof EObject){
        			EObject eobject = (EObject)element;
        			return eobject.eResource().getURI().lastSegment();
        		} else if (element instanceof XMIResource){
        			return super.getText(element);
        		}
        		return super.getText(element);
        	}
        	
        	public Image getImage(Object element) {
        		String str = getText(element);
        		if (str.endsWith(".page")){
        			return PageflowDiagramEditorPlugin.getInstance().getBundledImage("icons/page.png");
        		} else if (str.endsWith(".module")){
        			return PageflowDiagramEditorPlugin.getInstance().getBundledImage("icons/module.png");        			
        		}
        		return super.getImage(element);
        	}
        };

        ILabelProvider qualifierRenderer = new LabelProvider() {

            public Image getImage(Object element) {
                EObject eObject = (EObject) element;
                return elementRenderer.getImage(eObject.eResource());
            }

            public String getText(Object element) {
                EObject eObject = (EObject) element;
                return elementRenderer.getText(eObject.eResource());
            }
        };

        PageSelectionDialog dialog = new PageSelectionDialog(parent, elementRenderer, qualifierRenderer);
        dialog.setElements(elements);
        return dialog;
    }

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog#getResultByProperty(com.odcgroup.workbench.editors.properties.model.IPropertyFeature)
	 */
	@Override
	public Object getResultByProperty(IPropertyFeature property) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog#getSelection()
	 */
	@Override
	public Object getSelection() {
		Model model = (Model) getFirstResult();
		return model.eResource().getURI().toString();
	}

}
