package com.odcgroup.page.ui.properties.sections.matrix;

import org.eclipse.swt.custom.CTabFolder;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 *
 * @author pkk
 *
 */
public class MatrixKeepFilterTab extends MatrixTransformTab {

	/**
	 * @param parent
	 * @param style
	 * @param label
	 * @param sortable
	 */
	public MatrixKeepFilterTab(CTabFolder parent, int style, String label, boolean sortable) {
		super(parent, style, label, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.TransformTableTab#createTabControl(org.eclipse.swt.custom.CTabFolder, int)
	 */
	@Override
	protected ListControl<ITableKeepFilter, IMatrix> createTabControl(CTabFolder parent, int style) {
		return new MatrixKeepFilterSection(parent, style, isSortable());
	}
	


}
