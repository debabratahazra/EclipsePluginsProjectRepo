package com.odcgroup.page.ui.action.edit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.ResourceEntityHandlerImpl;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.action.edit.IOfsModelResourcePaster;

/**
 * Provide some services to alter the page designer model just after it has been
 * pasted in its target container
 * 
 * @author atr
 */
public class PageModelResourcePaster implements IOfsModelResourcePaster {

	/** The OFS model resource that has been pasted */
	private IOfsModelResource ofsModelResource;
	
	/**
	 * @param event
	 */
	private void paste(Event event) {
		long tid = event.getTranslationId();
		if (tid > 0) {
			try {
				Thread.sleep(1); // ensure tid are all different
				event.setTranslationId(System.nanoTime());
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}

	/**
	 * @param widget
	 */
	private void paste(Widget widget, Map<String, String> idmap) {
		
		long tid = widget.getTranslationId();
		if (tid > 0) {
			try {
				Thread.sleep(1);
				widget.setTranslationId(System.nanoTime());
			} catch (InterruptedException e) {
				// ignore
			}
		}
		//DS-4068
		if (!widget.isDomainWidget()) {
			String oldId = widget.getID();
			UniqueIdGenerator.regenerateID(widget);
			if (widget.getTypeName().equals(WidgetTypeConstants.MATRIX_CELLITEM)) {
				if (idmap != null) {
					idmap.put(oldId, widget.getID());
				}
			}
		}
		
		for (Event event : widget.getEvents()) {
			paste(event);
		}		
		for (Widget child : widget.getContents()) {			
			paste(child, idmap);
			if (child.getTypeName().equals(WidgetTypeConstants.MATRIX_CELLITEM)) {
				String colName = MatrixHelper.getMatrixCellItem(child).getColumnName();
				if(colName == null || colName.startsWith("comp_")) {					
						MatrixHelper.getMatrixCellItem(child).setColumnName(
								"comp_" + child.getID());
				}
			}
		}
		if(widget.getTypeName().equals(WidgetTypeConstants.MATRIX)) {
			UniqueIdGenerator.regenerateID(widget);
			WidgetUtils.replaceAggregatesForAMatrix(widget, idmap);
		}
	}

	/**
	 * @throws InvalidMetamodelVersionException
	 * @throws IOException
	 * 
	 */
	public void paste() throws IOException, InvalidMetamodelVersionException {
		Map<String, String> idmap = new HashMap<String, String>();
		Model model = (Model) ofsModelResource.getEMFModel().get(0);
		Widget root = model.getWidget();
		if (root != null) {
			paste(root, idmap);
		}

		// set the metainformation to the root
		Resource resource = model.eResource();
		model.setMetamodelVersion(OfsCore.getCurrentMetaModelVersion(resource.getURI().fileExtension()));
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(XMLResource.OPTION_URI_HANDLER, new ResourceEntityHandlerImpl("")); //$NON-NLS-1$
		resource.save(options);

	}

	/**
	 * @param shell
	 * @param srcOfsProject
	 * @param dstOfsProject
	 * @param ofsModelResource
	 */
	public PageModelResourcePaster(Shell shell, IOfsProject srcOfsProject, IOfsProject dstOfsProject,
			IOfsModelResource ofsModelResource) {
		this.ofsModelResource = ofsModelResource;

	}

}
