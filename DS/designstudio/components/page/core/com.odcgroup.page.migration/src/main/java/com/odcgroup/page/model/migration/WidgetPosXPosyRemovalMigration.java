package com.odcgroup.page.model.migration;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;
/*
 * DS-4092 - Remove the declaration of two PropertyTypes "posX" and "posY"
 */
public class WidgetPosXPosyRemovalMigration extends AbstractContentMigration {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.odcgroup.workbench.migration.IContentMigration#migrate(com.odcgroup
	 * .workbench.core.IOfsProject, org.eclipse.emf.ecore.resource.Resource,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doMigrate(IOfsProject ofsProject, Resource resource, IProgressMonitor monitor)
			throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if(contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			if (rootWidget != null) {
				traverse(rootWidget);
			} else {
				throw new MigrationException("Resource '" + resource.getURI() + "' does not contain any root widget!");
			}
		} else {
			throw new MigrationException("Resource '" + resource.getURI() + "' does not contain any content!");
		}
	}

	/*
	 * Build a set of property objects which will be passed into 
	 * to the Widget list for removal
	 */
	private Set<Property> buildSetForRemoval(Widget widget) {
		Set<Property> propertySet = new HashSet<Property>();
		Property propX = widget.findProperty("posX");
		Property propY = widget.findProperty("posY");
		if (propX != null) {
			propertySet.add(propX);
		}
		if (propY != null) {
			propertySet.add(propY);
		}
		return propertySet;
	}

	/* 
	 * Carry out a recursive search through the widgets
	 * looking for the properties to be removed, and remove them.
	 */
	private void traverse(Widget widget) {
		Set<Property> propList = buildSetForRemoval(widget);
		widget.getProperties().removeAll(propList);
		if (widget.getContents() == null) {
			return;
		} else {
			for (Widget child : widget.getContents()) {
				traverse(child);
			}
		}
	}
}
