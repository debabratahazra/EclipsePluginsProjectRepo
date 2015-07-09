package com.odcgroup.page.ui.properties.sections.matrix;

import org.eclipse.swt.custom.CTabFolder;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class MatrixExtraTab extends MatrixTransformTab {

	/**
	 * @param parent
	 * @param style
	 * @param label
	 * @param sortable
	 */
	public MatrixExtraTab(CTabFolder parent, int style, String label, boolean sortable) {
		super(parent, style, label, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.TransformTableTab#createTabControl(org.eclipse.swt.custom.CTabFolder, int)
	 */
	@Override
	protected ListControl<?, IMatrix> createTabControl(CTabFolder parent, int style) {
		return new MatrixExtraSection(parent, style, isSortable());
	}


}
