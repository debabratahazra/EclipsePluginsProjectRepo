package com.odcgroup.menu.editor.dialog;

import java.net.URL;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertySelectionDialog;
import com.odcgroup.workbench.editors.properties.util.AdapterUtils;
import com.odcgroup.workbench.ui.repository.ModelSelectionDialog;

public class PageflowSelectionDialog extends ModelSelectionDialog implements IPropertySelectionDialog {
	
	 /**
	 * @param parent
	 * @param elements
	 * @return
	 */
	public static PageflowSelectionDialog createDialog(Shell parent, Object[] elements) {

        ILabelProvider qualifierRenderer = new LabelProvider() {

            public Image getImage(Object element) {
                EObject eObject = (EObject) element;
                Object obj = AdapterUtils.getItemProviderImage(eObject);
                if (obj instanceof URL) {
                	URL url = (URL)obj;
					return ImageDescriptor.createFromURL(url).createImage();
                } else {
                	return (Image) obj;
                }
                
            }

            public String getText(Object element) {
                EObject eObject = (EObject) element;
                return AdapterUtils.getItemProviderText(eObject);
            }
        };

        PageflowSelectionDialog dialog = new PageflowSelectionDialog(parent, qualifierRenderer, qualifierRenderer);
        dialog.setElements(elements);
        return dialog;
	 }

	/**
	 * @param parent
	 * @param elementRenderer
	 * @param qualifierRenderer
	 */
	public PageflowSelectionDialog(Shell parent,
			ILabelProvider elementRenderer, ILabelProvider qualifierRenderer) {
		super(parent, elementRenderer, qualifierRenderer);
	}

	
	public Object getSelection() {
		EObject eobj = (EObject) getFirstResult();
		return eobj.eResource().getURI().toString();
	}

	
	public Object getResultByProperty(IPropertyFeature property) {
		return null;
	}

}
