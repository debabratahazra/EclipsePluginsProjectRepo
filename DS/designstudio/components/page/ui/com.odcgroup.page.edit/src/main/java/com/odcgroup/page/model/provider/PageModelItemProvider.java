package com.odcgroup.page.model.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;

import com.odcgroup.page.model.Model;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

/**
 * A Property provider used within the Page Designer. We do not touch the generated version since it might be useful in
 * other situations, for example, for editing files.
 * 
 * @author Gary Hayes
 */
public class PageModelItemProvider extends ModelItemProvider {

	/**
	 * Creates a new PageModelItemProvider.
	 * 
	 * @param adapterFactory
	 *            The AdapterFactory
	 */
	public PageModelItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * Gets the text of the Property.
	 * 
	 * @param object
	 *            The Property
	 * @return String The text
	 */
	public String getText(Object object) {
		Model model = (Model) object;
		URI uri = EcoreUtil.getURI(model);
		//String name = p.eResource().getURI().lastSegment();
		String name = uri.lastSegment();
		int pos = name.indexOf('.');
		if (pos != -1) {
			name = name.substring(0,pos);
		}
		return name;
	}

	/**
	 * Gets the image.
	 * 
	 * @param element
	 *            The element
	 * @return String The image
	 */
	public Image getImage(Object element) {
		EObject eObject = (EObject) element;
		URI uri = EcoreUtil.getURI(eObject);
		//String fe = eObject.eResource().getURI().fileExtension();
		String fe = uri.fileExtension();
		return PageModelEditPlugin.getImageFromFileExtension(fe);
	}

	/**
	 * Override the base class version. We return null since we don't want the Model displayed in the results.
	 * 
	 * @param object
	 *            The Object to get the parent for
	 * @return Object The parent Object
	 */
	public Object getParent(Object object) {
		Object parent = super.getParent(object);
		if (parent == null) {
	    	URI uri = EcoreUtil.getURI((EObject)object);
			for (IOfsProject ofsProject : OfsCore.getOfsProjectManager().getAllOfsProjects()) {
				IOfsModelResource ofsResource;
				try {
					ofsResource = ofsProject.getOfsModelResource(uri);
					if (ofsResource != null) {
						parent = ofsResource.getResource().getParent();
					}
				} catch (ModelNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return parent;
	}
}
