package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 *
 * @author pkk
 *
 */
public class TableGroupListControl extends AbstractTypeListControl<ITableGroup, ITable> {

	/**
	 * @param parent
	 */
	public TableGroupListControl(Composite parent) {
		super(parent);
	}

	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.AbstractTypeListControl#getContents(java.lang.Object)
	 */
	@Override
	public ITableGroup[] getContents(ITable input) {
		List<ITableGroup> content = new ArrayList<ITableGroup>();
		for (ITableGroup group : input.getGroups()) {
			if (!group.isUsedForDynamicColumn()) {
				content.add(group);
			}
		}
		return content.toArray(new ITableGroup[0]);   
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.AbstractTypeListControl#getDisplayText(java.lang.Object)
	 */
	@Override
	public String getDisplayText(ITableGroup element) {
		return "GP_"+element.getColumnName();
	}
	
	/**
	 * @return names[]
	 */
	public String[] getSelectedGroupNames() {
		ITableGroup[] groups = getSelection().toArray(new ITableGroup[0]);
		String[] names = new String[groups.length];
 		for (int i = 0; i < groups.length;i++) {
			names[i] = groups[i].getColumnName();
		}
 		return names;
	}
	
	/**
	 * @param groupNames
	 */
	public void setSelection(String[] groupNames) {
		for (String groupName : groupNames) {
			int index = getIndex("GP_"+groupName);
			if (index  > -1)
				getViewer().getList().select(index);
		}
	}
	
	/**
	 * @param item
	 * @return int
	 */
	private int getIndex(String item) {
		String[] items = getViewer().getList().getItems();
		for(int i=0;i<items.length;i++) {
			if (item.equals(items[i])) {
				return i;
			}
		}
		return -1;
	}

}
