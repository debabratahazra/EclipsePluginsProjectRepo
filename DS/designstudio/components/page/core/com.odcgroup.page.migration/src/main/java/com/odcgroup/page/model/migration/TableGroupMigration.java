package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * migration class for the table group's
 * 
 * @author snn
 * 
 */
public class TableGroupMigration extends AbstractContentMigration {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractContentMigration#doMigrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.ecore.resource.Resource, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			migrateTableGroups(rootWidget.getContents());
		}

	}

	/**
	 * @param widgets
	 */
	private void migrateTableGroups(EList<Widget> widgets) {
		for (Widget widget : widgets) {
			if (WidgetTypeConstants.TABLE_TREE.equals(widget.getTypeName())) {
				ITable table = TableHelper.getTable(widget);
				ITableColumn dispcol = table.getDisplayGroupingColumn();
				if (dispcol != null) {
					List<Widget> groups = collectTableGroups(widget);
					// add the groups to display grouping column
					// remove the same from table widget
					if (!groups.isEmpty()) {
						dispcol.getWidget().getContents().addAll(groups);
						widget.getContents().removeAll(groups);
					}
				}
			}
			migrateTableGroups(widget.getContents());
		}

	}

	/**
	 * @param widget
	 * @return
	 */
	private List<Widget> collectTableGroups(Widget widget) {
		List<Widget> groups = new ArrayList<Widget>();
		if (WidgetTypeConstants.TABLE_TREE.equals(widget.getTypeName())) {
			List<Widget> contents = widget.getContents();
			for (Widget child : contents) {
				if ("TableGroup".equals(child.getTypeName())) {
					groups.add(child);
				}
			}
		}
		return groups;
	}

}
