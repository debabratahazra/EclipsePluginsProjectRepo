package com.odcgroup.page.content.migration;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.model.Model;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

public class RemoveUnnecessaryIdsFromWidgetsMigration extends
		AbstractContentMigration {

	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			/*
			 * Calling getWidget on model will result in the update method in
			 * WidgetFactory being invoked. when this widget is saved it will be
			 * migrated
			 */
			if (model != null) {
				model.getWidget();
			}
		}
	}
}
