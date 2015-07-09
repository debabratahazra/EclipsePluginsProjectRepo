package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.properties.table.dialog.AppliesOnGroupSelectionDialog;

/**
 * Defines the context cell editor for the property context
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class ApplyOnGroupCellEditor extends DialogCellEditor {

	/** The Property */
	private Property property;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            The parent component
	 * @param property
	 *            The selected property
	 */
	public ApplyOnGroupCellEditor(Composite parent, Property property) {
		super(parent);
		this.property = property;
	}

	/**
	 * @return the domain object provider
	 */
	public String[] getTableGroupProvider() {
		Widget tableWidget = property.getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
		ITable table = TableHelper.getTable(tableWidget);
		List<ITableGroup> groups = table.getGroups();
		List<String> allTableGroups = new ArrayList<String>();
		for (ITableGroup tablGroup : groups) {
			if (tablGroup.getWidget().findProperty("group-column-name") != null) {
				Property dom = tablGroup.getWidget().findProperty(
						"group-column-name");
				if (dom.getValue() != null
						&& dom.getValue().trim().length() > 0 ) {
					allTableGroups.add(dom.getValue().trim());
				}
			}
		}
		return allTableGroups.toArray(new String[] {});
	}
	
	/**
	 * Open dialog window
	 * 
	 * @param cellEditorWindow
	 *            The cell editor window
	 * @return Object
	 */
	protected Object openDialogBox(Control cellEditorWindow) {
		String[] result = null;
		if(property.getValue().contains(",")){
			result = property.getValue().split(",");
		}
		else {
			result =  new String[] { property.getValue() };
		}
			
		String target = null;
		AppliesOnGroupSelectionDialog dialog = new AppliesOnGroupSelectionDialog(
				cellEditorWindow.getShell(), getTableGroupProvider(), result, "Grouping Level",
				"Select the grouping levels on which the checkbox must be displayed.");
		if (dialog.open() == Window.OK) {
			String[] selectedAttributes = dialog.getSelectedAttributes();
			String selectGroups = Arrays.toString(selectedAttributes); 
			target = selectGroups.substring(1, selectGroups.length()-1);
			property.setValue(target);
		}
		return target;
	}

}
