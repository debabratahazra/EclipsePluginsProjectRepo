package com.odcgroup.page.ui.edit.handler;

import org.eclipse.emf.common.util.URI;
import org.eclipse.gef.commands.CommandStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * Tries to open an editor containing the included file.
 * 
 * @author atr
 */
public class IncludeOpenRequestHandler implements WidgetRequestHandler {

	private static final Logger logger = LoggerFactory.getLogger(IncludeOpenRequestHandler.class);
	
	/**
	 * Creates a new IncludeOpenRequestPerformer.
	 */
	public IncludeOpenRequestHandler() {
	}

	/**
	 * @see com.odcgroup.page.ui.edit.handler.WidgetRequestHandler#handle(com.odcgroup.workbench.core.IOfsProject, org.eclipse.gef.commands.CommandStack, com.odcgroup.page.model.Widget)
	 * DS-1387
	 */
	public void handle(IOfsProject ofsProject, CommandStack commandStack, Widget widget) {
		assert ofsProject != null;
		assert widget != null;

		Model model = IncludeOpenRequestHandler.getModelFromWidget(widget);
		if (model != null && model.eResource() != null) {			
			URI resourceUri = model.eResource().getURI();
			OfsEditorUtil.openEditor(ofsProject, resourceUri);			
		} else {
			String wid = widget.getTypeName();
			logger.error("Unable to find the included model for the widget '[]'", wid);
		}
	}

	/**
	 * @return The model of the designated widget.
	 * @param widget
	 */
	private static Model getModelFromWidget(Widget widget) {
		Model model = null;
		Property include = widget.findProperty(PropertyTypeConstants.INCLUDE_SOURCE);
		if (include != null) {
			model = include.getModel();
		}
		return model;
	}

}