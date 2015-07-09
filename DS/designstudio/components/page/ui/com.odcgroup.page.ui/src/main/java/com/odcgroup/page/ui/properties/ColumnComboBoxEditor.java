package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;

/**
 * This class represent a combo box editor for the column data type.
 * 
 * @author Alexandre Jaquet
 *
 */
public class ColumnComboBoxEditor extends AbstractPropertyEditor {

	/**
     * Creates a new ColumnComboBoxEditor.
     * 
     * @param property The property
     */
    public ColumnComboBoxEditor(Property property) {
        super(property);
    }
	
	/**
	 * Gets the CellEditor for this Property.
	 * 
	 * @param parent The parent
	 * @param labelProvider The label provider
	 * @return CellEditor The cell editor
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider) {	
		//Get the Grand Parent of the ColumnHeader
		Widget table = getProperty().getWidget().getParent().getParent();
		List<Widget> headers = getColumnHeaders(table);
		List<String> names = getColumnHeadersName(headers);
		//Add an empty element to the list
		names.add(0, "");
		CellEditor editor = new ExtendedComboBoxCellEditor(parent, names, labelProvider, false);
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}
	
	/**
	 * Gets the ColumnHeaders of the Widget. The Widget structure is assumed to be as follows:
	 * 
	 * MainWidget -> Column -> Column Header
	 * 
	 * @param widget
	 *            The main Widget being transformed
	 * @return List of ColumnHeader's
	 */
	private List<Widget> getColumnHeaders(Widget widget) {
		// First find the Columns
		List<Widget> columns = WidgetUtils.filter(widget.getContents(), WidgetLibraryConstants.XGUI, WidgetTypeConstants.COLUMN);
		List allChildren = WidgetUtils.getChildren(columns);
		List<Widget> headers = WidgetUtils.filter(allChildren, WidgetLibraryConstants.XGUI, WidgetTypeConstants.COLUMN_HEADER);
		return headers;
	}

	/**
	 * Gets the list of the ColumnHeader's name.
	 * 
	 * @param headers
	 * 			The list of ColumnHeader's
	 * @return List return the list of the ColumnHeader's name
	 */
	private List<String> getColumnHeadersName(List<Widget> headers) {
		List<String> names = new ArrayList<String>();
		for (Widget header : headers) {
			String name = header.getPropertyValue(PropertyTypeConstants.COLUMN_NAME);
			names.add(name);
		}
		return names;
	}
}
