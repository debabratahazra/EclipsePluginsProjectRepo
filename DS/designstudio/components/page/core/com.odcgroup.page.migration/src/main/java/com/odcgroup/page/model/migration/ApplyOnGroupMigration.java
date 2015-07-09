package com.odcgroup.page.model.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * migration class for the table group's
 * 
 * @author ramapriyamn
 * 
 */
public class ApplyOnGroupMigration extends AbstractContentMigration {

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.migration.AbstractContentMigration#doMigrate(com.odcgroup.workbench.core.IOfsProject, org.eclipse.emf.ecore.resource.Resource, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			migrateCheckBoxInTableGroups(rootWidget.getContents());
		}

	}

	/**
	 * @param widgets
	 */
	private void migrateCheckBoxInTableGroups(EList<Widget> widgets) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (Widget widget : widgets) {
			WidgetType treeType = metamodel.findWidgetType(WidgetLibraryConstants.XGUI,WidgetTypeConstants.TABLE_TREE);
			List<Widget> tableWidgets = widget.getWidgets(treeType, true);
			if (!tableWidgets.isEmpty()) {
				groupTableWidgetsHavingCheckbox(tableWidgets);
			}
		}
	}

	private void groupTableWidgetsHavingCheckbox(List<Widget> tableWidgets) {
		for (Widget treeWidget: tableWidgets) {
			ITable table = TableHelper.getTable(treeWidget);
			List<ITableGroup> groups = table.getGroups();
			if (!groups.isEmpty()) {
				if (table.displayCheckboxOnTreeNodes()) 
				{
					List<Widget> childWidgets = WidgetUtils.getChildren(table.getWidget().getContents());
					saveGroupNames(groups, childWidgets);
				}
			}
		   	migrateCheckBoxInTableGroups(treeWidget.getContents());
		}
	}

	private void saveGroupNames(List<ITableGroup> groups,
			List<Widget> childWidgets) {
		for (Widget childWid : childWidgets) {
			if (childWid.getType().getName().equals(WidgetTypeConstants.CHECKBOX)) {
				if (childWid.findProperty("checkbox-group-names") != null) {
					Property chkGrpName = childWid.findProperty("checkbox-group-names");
					List<String> groupNames = new ArrayList<String>();
					collectGroupNames(groups, groupNames);
					chkGrpName.setValue(groupNames.toString().substring(1,groupNames.toString().length() - 1));
				}
			}
		}
	}

	private void collectGroupNames(List<ITableGroup> groups,
			List<String> groupNames) {
		for (ITableGroup tablGroup : groups) {
			if (tablGroup.getWidget().findProperty("group-column-name") != null) {
				Property dom = tablGroup.getWidget().findProperty("group-column-name");
				if (dom.getValue() != null && dom.getValue().trim().length() > 0) {
					groupNames.add(dom.getValue().trim());
				}
			}
		}
	}

}
