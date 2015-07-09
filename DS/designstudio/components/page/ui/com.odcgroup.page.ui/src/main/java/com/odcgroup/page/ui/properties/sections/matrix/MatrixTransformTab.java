package com.odcgroup.page.ui.properties.sections.matrix;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 *
 * @author pkk
 *
 */
public abstract class MatrixTransformTab extends CTabItem implements IMatrixTransformTab {
	
	/**	 */
	private ListControl<?, IMatrix> listControl = null;
	/**	 */
	private boolean sortable = true;

	/**
	 * @param parent
	 * @param style
	 * @param label 
	 * @param sortable 
	 */
	public MatrixTransformTab(CTabFolder parent, int style, String label, boolean sortable) {
		super(parent, style);
		this.sortable = sortable;
		setText("   " + label + "   ");
		listControl = createTabControl(parent, style);
		setControl(listControl.getControl());
	}
	
	/**
	 * @param parent
	 * @param style
	 * @return Composite
	 */
	protected abstract ListControl<?, IMatrix> createTabControl(CTabFolder parent, int style);

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.ITransformTableTab#setInput(com.odcgroup.page.model.widgets.table.ITable, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public final void setInput(IMatrix input, IWorkbenchPart part) {
		listControl.setInput(input, part);	
	}

	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.ITransformTableTab#refresh()
	 */
	@Override
	public void refresh() {
		listControl.refresh();
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.ITransformTableTab#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		listControl.setEnabled(enabled);		
	}
	


}
