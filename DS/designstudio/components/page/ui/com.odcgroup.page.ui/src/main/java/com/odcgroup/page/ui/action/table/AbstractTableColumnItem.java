package com.odcgroup.page.ui.action.table;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.action.AbstractGenericAction;
import com.odcgroup.page.ui.action.ActionParameters;

/**
 * This class holds the following functionnality:
 * <ul><li>select a widget (useful right after the replacement)</li>
 * <li>determine if a widget is in a domain table column</li></ul>
 */
public abstract class AbstractTableColumnItem extends AbstractGenericAction {

	/** The active viewer. */
	private EditPartViewer viewer;

	protected AbstractTableColumnItem(ActionParameters parameters, EditPartViewer viewer) {
		super(parameters);
		this.viewer = viewer;
	}

	protected void selectWidget(Widget widget) {
		// Since the Command removes and then adds the Widget GEF removes the old 
		// edit part and then creates a new one. We need to find the new one 
		// and reselect it
		
		EditPart ep = (EditPart) viewer.getEditPartRegistry().get(widget);
		viewer.deselectAll();
		if (ep!=null) {
			ep.setSelected(EditPart.SELECTED_PRIMARY);
			ep.setFocus(true);
			viewer.setFocus(ep);
			viewer.select(ep);
		}
	}
	
	protected boolean isDomainTableColumn(Widget widget) {
		Widget tableColumnWidget = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
		if (tableColumnWidget != null) {
			ITableColumn column = TableHelper.getTableColumn(tableColumnWidget);
			return column.isDomain() && !column.isDisplayGrouping();
		}
		return false;
	}
	
	protected boolean isRegularModule(Widget widget) {
		Widget root = widget.getRootWidget();
		if (WidgetTypeConstants.MODULE.equals(root.getTypeName())) {
			String searchtype = root.getPropertyValue(PropertyTypeConstants.SEARCH);
			if ("none".equals(searchtype)) {
				return true;
			}
		}
		return false;
	}

	
}
