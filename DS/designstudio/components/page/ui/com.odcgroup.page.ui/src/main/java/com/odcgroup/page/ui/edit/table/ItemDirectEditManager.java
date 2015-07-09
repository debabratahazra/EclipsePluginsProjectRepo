package com.odcgroup.page.ui.edit.table;

import java.util.List;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.edit.WidgetCellEditorLocator;
import com.odcgroup.page.ui.figure.IWidgetFigure;

/**
 * The DirectEditManager for Items. This initializes the CellEditor with the first
 * value in the List of comma-separated previewValues. See ColumnBodyDirectEditManager
 * for further information.
 * 
 * @author Gary Hayes
 */
public class ItemDirectEditManager extends DirectEditManager {
		
	/**
	 * Constructs a new ColumnBodyDirectEditManager for the given source edit part. 
	 * 
	 * @param source the source edit part
	 * @param request The LocationRequest
	 */
	public ItemDirectEditManager(GraphicalEditPart source, LocationRequest request) {		
		super(source, TextCellEditor.class, new WidgetCellEditorLocator(
				(IWidgetFigure) source.getFigure(), request));
	}
	
	/**
	 * Initialises the CellEditor.
	 */
	protected void initCellEditor() {
		Widget w =  (Widget) getEditPart().getModel();
		List<String> pvs = WidgetUtils.getPreviewValues(w);
		String s = "";
		if (pvs.size() > 0) {
			s = pvs.get(0);
		}
		
		getCellEditor().setValue(s);
	}	
}
