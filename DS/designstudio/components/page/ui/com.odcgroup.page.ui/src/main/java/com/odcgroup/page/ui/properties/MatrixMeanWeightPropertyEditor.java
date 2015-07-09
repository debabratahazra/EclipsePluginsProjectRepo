package com.odcgroup.page.ui.properties;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.properties.table.TableDomainObjectUtil;

/**
 * @author pkk
 *
 */
public class MatrixMeanWeightPropertyEditor extends AbstractPropertyEditor {

	public MatrixMeanWeightPropertyEditor(Property property) {
		super(property);
	}

	@Override
	public CellEditor getCellEditor(Composite parent,
			ILabelProvider labelProvider) {
		Property p = getProperty();		
		String propertyValue = p.getWidget().getPropertyValue("aggregationType");
		if (propertyValue.equals("weighted-mean")) {
			Widget itemWidget = getProperty().getWidget();
			IMatrix matrix = MatrixHelper.getMatrixContentCellItem(itemWidget).getMatrix();
			List<String> names = TableDomainObjectUtil.getDomainAttributeNames(matrix);
			//Add an empty element to the list
			names.add(0, "");
			CellEditor editor = new ExtendedComboBoxCellEditor(parent, names, labelProvider, false);
			String name = p.getValidatorName();
			if (!StringUtils.isEmpty(name)) {
				editor.setValidator(makeCellValidator(name));
			}
			return editor;
		} else {
			return null;
		}
	}
	
	

}
