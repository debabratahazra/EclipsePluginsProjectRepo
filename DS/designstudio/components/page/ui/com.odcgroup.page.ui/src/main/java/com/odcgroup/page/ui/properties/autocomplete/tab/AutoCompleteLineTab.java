package com.odcgroup.page.ui.properties.autocomplete.tab;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 * @author pkk
 *
 */
public class AutoCompleteLineTab extends CTabItem implements
		IAutoCompleteLineTab {
	/**	 */
	private boolean sortable = true;
	
	/**	 */
	private ListControl<?, ILineSnippet> listControl = null;

	/**
	 * @param parent
	 * @param style
	 */
	public AutoCompleteLineTab(CTabFolder parent, int style, String label, boolean sortable) {
		super(parent, style);
		this.sortable = sortable;
		setText("   " + label + "   ");
		listControl = createTabControl(parent, style);
		setControl(listControl.getControl());
	}
	
	/**
	 * @param parent
	 * @param style
	 * @return
	 */
	private ListControl<?, ILineSnippet> createTabControl(CTabFolder parent, int style) {
		return  new LineItemListControl(parent, style, sortable);
	}

	@Override
	public void setInput(ILineSnippet input, IWorkbenchPart part) {
		listControl.setInput(input, part);	
	}

	@Override
	public void refresh() {
		listControl.refresh();
	}

	@Override
	public void setEnabled(boolean enabled) {
		listControl.setEnabled(enabled);		
	}

	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}

}
