package com.odcgroup.page.ui.properties.sections.matrix;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.util.AdaptableUtils;

/**
 *
 * @author pkk
 *
 */
public class MatrixComputationSectionFilter implements IFilter {	
	

	/** 
	 * @param object the object to be filtered
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	public boolean select(Object object) {
		if (object instanceof EditPart) {
			Widget w = (Widget) AdaptableUtils.getAdapter(object, Widget.class);
			if (w.getTypeName().equals("MatrixContentCellItem")) {
				IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(w);
				if (item.isComputed())
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Constructor
	 */
	public MatrixComputationSectionFilter() {
	}
}
