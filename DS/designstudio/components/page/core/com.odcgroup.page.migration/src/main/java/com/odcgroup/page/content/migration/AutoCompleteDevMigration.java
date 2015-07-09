package com.odcgroup.page.content.migration;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * @author pkk
 *
 */
public class AutoCompleteDevMigration extends AbstractContentMigration {

	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			if (rootWidget.getTypeName().equals(WidgetTypeConstants.MODULE)) {
				// make the search type property of the module read-only
				Property p = rootWidget.findProperty(PropertyTypeConstants.SEARCH);
				if (p != null) {
					p.setReadonly(true);
				} 
			}
			performAutoCompleteMigrationNeeds(rootWidget);
		}
		
	}
	
	/**
	 * @param widget
	 */
	private void performAutoCompleteMigrationNeeds(Widget widget) {
		if (!widget.getContents().isEmpty()) {
			for (Widget child : widget.getContents()) {
				if (child.getTypeName().equals(WidgetTypeConstants.SEARCH_FIELD)) {
					removeDeprecatedProperties(child);	
					setSearchType(child);
				}
				performAutoCompleteMigrationNeeds(child);
			}
		}
	}
	
	/**
	 * @param widget
	 */
	private void setSearchType(Widget widget) {
		Property search = widget.findProperty("searchType");
		search.setValue("searchOnly");
	}
	
	/**
	 * @param widget
	 */
	private void removeDeprecatedProperties(Widget widget) {
		Property access = widget.findProperty(PropertyTypeConstants.ACCESS_KEY);
		if (access != null)
			widget.getProperties().remove(access);
		Property tab = widget.findProperty(PropertyTypeConstants.TAB_INDEX);
		if (tab != null)
			widget.getProperties().remove(tab);
		
		Property param = widget.findProperty("param");
		if (param != null) {
			widget.getProperties().remove(param);
		}
	}

}
