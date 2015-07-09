package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;

/**
 * 
 * @author Gary Hayes
 */
public class PropertyComboBoxEditor extends AbstractPropertyEditor {

	/**
	 * Creates a new PropertyComboBoxEditor.
	 * 
	 * @param property The Property
	 */
	public PropertyComboBoxEditor(Property property) {
		super(property);
	}
	
	/**
	 * @param values
	 * @return
	 */
	protected List<DataValue> filter(List<DataValue> values) {
		Widget widget = getProperty().getWidget();
		if (WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName())) {
			List<DataValue> filterlist = new ArrayList<DataValue>();
			for (DataValue dataValue : values) {
				if ("searchOnly".equals(dataValue.getValue())) {
					filterlist.add(dataValue);
				}
			}
			values.removeAll(filterlist);
		}
		return values;
	}
	
	/**
	 * Gets the CellEditor for this Property.
	 * 
	 * @param parent The parent
	 * @param labelProvider The label provider
	 * @return CellEditor The cell editor
	 */
	public CellEditor getCellEditor(Composite parent, ILabelProvider labelProvider) {	

		PropertyType propType = getProperty().getType();
		DataType dt = propType.getDataType();		
		ILabelProvider lp = new DataValueLabelProvider();
		
		final Property prop = getProperty(); 
		
		CellEditor editor = new ExtendedComboBoxCellEditor(parent, filter(dt.getValues()), lp, false) {
			public void activate() {
				getControl().setEnabled(!prop.isReadonly());
				super.activate();
			}
		};
		String name = getProperty().getValidatorName();
		if (!StringUtils.isEmpty(name)) {
			editor.setValidator(makeCellValidator(name));
		}
		return editor;
	}

}
