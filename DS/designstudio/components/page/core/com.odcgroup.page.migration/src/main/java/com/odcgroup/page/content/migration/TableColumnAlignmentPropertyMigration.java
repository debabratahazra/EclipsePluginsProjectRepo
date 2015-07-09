package com.odcgroup.page.content.migration;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * @author pkk
 *
 */
public class TableColumnAlignmentPropertyMigration extends AbstractContentMigration {

	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			migrateTableColumns(rootWidget.getContents());
		}			
	}
	
	/**
	 * @param widgets
	 */
	private void migrateTableColumns(List<Widget> widgets) {
		for (Widget widget : widgets) {
			if (WidgetTypeConstants.TABLE_COLUMN.equals(widget.getTypeName())) {
				ITableColumn column = TableHelper.getTableColumn(widget);
				if (column != null) {
					// migrate format
					Property format = widget.findProperty(PropertyTypeConstants.FORMAT);
					if (format != null) {
						ITableColumnItem item = findMatchingItem(column);
						if (item != null) {
							Widget itemwidget = item.getWidget();
							Property prop = itemwidget.findProperty(PropertyTypeConstants.FORMAT);
							if (prop == null) {
								prop = createProperty(PropertyTypeConstants.FORMAT);
								itemwidget.getProperties().add(prop);
							}
							itemwidget.setPropertyValue(PropertyTypeConstants.FORMAT, format.getValue());
						}
						widget.getProperties().remove(format);
					}
					// migrate halign
					Property halign = widget.findProperty("table-column-halign");
					if (halign != null) {
						String val = halign.getValue();						
						if ("trail".equals(val) || "center".equals(val) || "lead".equals(val)) {
							ITableColumnItem item = findMatchingItem(column);
							if (item != null) {
								Widget itemwidget = item.getWidget();
								Property prop = itemwidget.findProperty("item-halign");
								if (prop == null) {
									prop = createProperty("item-halign");
									itemwidget.getProperties().add(prop);
								}
								itemwidget.setPropertyValue("item-halign", halign.getValue());
							} else {
								Widget firstChild = getFirstChild(widget);
								if (firstChild != null) {
									firstChild.setPropertyValue("horizontalAlignment", halign.getValue());								
								}
							}	
						}
						widget.getProperties().remove(halign);
					}
				}
			}
			migrateTableColumns(widget.getContents());
		}
	}
	
	/**
	 * @param type
	 * @return
	 */
	private Property createProperty(String type) {
		Property p = ModelFactory.eINSTANCE.createProperty();		
		p.setTypeName(type);
		p.setLibraryName("xgui");
		return p;
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private Widget getFirstChild(Widget widget) {
		if (!widget.getContents().isEmpty()) {
			return widget.getContents().get(0);
		}
		return null;
	}
	
	/**
	 * @param column
	 * @return
	 */
	private ITableColumnItem findMatchingItem(ITableColumn column) {
		List<ITableColumnItem> items = column.getItems();
		String colname = column.getColumnName();
		if (!StringUtils.isEmpty(colname)) {			
			for (ITableColumnItem item : items) {
				if (colname.equals(item.getColumn())) {
					return item;
				}
			}
		}
		if (!items.isEmpty()) {
			return items.get(0);
		}
		return null;
	}

}
