package com.odcgroup.page.model.migration;

import java.util.List;

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
 * DS-5095 - radio widget ID property is no more read-only
 * 
 * @author phanikumark
 * 
 */
public class RadioWidgetIDPropertyMigration extends AbstractContentMigration {

	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			migrateRadioButtonIDProperty(rootWidget.getContents());
		}
	}
	
	/**
	 * @param widgets
	 */
	private void migrateRadioButtonIDProperty(List<Widget> widgets) {
		for (Widget widget : widgets) {
			if (WidgetTypeConstants.RADIO_BUTTON.equals(widget.getTypeName())) {
				Property property = widget.findProperty(PropertyTypeConstants.ID);
				if (property != null) {
					property.setReadonly(false);
				}
			} else {
				migrateRadioButtonIDProperty(widget.getContents());
			}
		}
	}

}
