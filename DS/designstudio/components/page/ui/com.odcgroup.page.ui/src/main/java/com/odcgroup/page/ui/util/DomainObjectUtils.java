package com.odcgroup.page.ui.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.model.util.ModelVisitor;
import com.odcgroup.mdf.model.util.ModelWalker;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.util.AdaptableUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * This class defines some services to open dialog related to the selection of
 * domain object(s).
 * 
 * @author atr
 */
public final class DomainObjectUtils {

	/**
	 * @param widget
	 * @return MdfDataset
	 */
	public static MdfDataset getDataset(Widget widget) {
		IOfsProject ofsProject = null;
		if (widget.eResource() != null)
			ofsProject = OfsResourceHelper.getOfsProject(widget.eResource());
		if (ofsProject == null)
			ofsProject = EclipseUtils.findCurrentProject();
		return DomainObjectUtils.getDataset(ofsProject, widget);
	}

	/**
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static MdfDataset getDataset(IOfsProject ofsProject, Widget widget) {
		String datasetName = widget.getRootWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
		DomainRepository repository = DomainRepository.getInstance(ofsProject);
		return repository.getDataset(MdfNameFactory.createMdfName(datasetName));
	}

	/**
	 * Given the MDF element this opens the DomainEditor with the item selected.
	 * 
	 * @param element the MDF element
	 */
	public static void openDomainEditor(MdfModelElement element) {
		IOfsProject ofsProject = EclipseUtils.findCurrentProject();
		new DomainEditorOpener().open(ofsProject, element);
	}

	/**
	 * I use an inner class here since we need to make the selectedElement
	 * available from the IModelVisitor.
	 * 
	 * @author Gary Hayes
	 */
	private static class DomainEditorOpener {

		/** The MdfModelElement in the DomainEditor. */
		private MdfModelElement selectedElement;
		
		/**
		 * @param ofsProject
		 * @param element
		 */
		public void open(IOfsProject ofsProject, final MdfModelElement element) {
			EObject eObj = (EObject) element;
			Resource resource = eObj.eResource();
			if (resource == null && eObj.eIsProxy()) {
				URI uri = ((InternalEObject)eObj).eProxyURI().trimFragment();
				resource = ofsProject.getModelResourceSet().getResource(uri, IOfsProject.SCOPE_ALL);
			}
			if (resource == null) {
				// The element must not exist (deleted in the main model?)
				Logger.error("Found domain element for [" + element.getQualifiedName() + "], but unable to find its resource");
				return;
			}
			
			URI resourceUri = resource.getURI();
			IEditorPart ep = OfsEditorUtil.openEditor(ofsProject, resourceUri);
			if (ep != null) {	
				/* 
				 * The MdfModelElement in our model is NOT the same one as in
				 * the DomainEditor. Here we look for the corresponding MdfModelElement 
				 * in the Domain Editor so that we can set the Selection to it
				 */
				MdfDomain md = (MdfDomain) AdaptableUtils.getAdapter(ep, MdfDomain.class);
				ModelVisitor mv = new ModelVisitor() {
					public boolean accept(MdfModelElement model) {
						if (model.getQualifiedName().equals(element.getQualifiedName())) {
							selectedElement = model;
							return false;
						}
						return true;
					}
				};
				ModelWalker mw = new ModelWalker(mv);
				mw.visit(md);
	
				// set the current selection
				if (selectedElement != null) {
					// Set the selected MdfModelElement
					ep.getSite().getSelectionProvider().setSelection(new StructuredSelection(selectedElement));
					Viewer cv = (Viewer) AdaptableUtils.getAdapter(ep, Viewer.class);
					cv.setSelection(new StructuredSelection(selectedElement));
				}
			}
			
		}
	}

	/** Cannot be instantiated */
	private DomainObjectUtils() {
	}

}
