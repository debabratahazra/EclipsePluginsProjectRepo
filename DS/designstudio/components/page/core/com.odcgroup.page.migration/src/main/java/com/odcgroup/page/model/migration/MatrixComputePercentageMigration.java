package com.odcgroup.page.model.migration;

import java.util.Stack;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * migration class for the Compute Percentage in Matrix
 * The computation "compute-percentage" is replaced by "relative-percent"
 */
public class MatrixComputePercentageMigration extends AbstractContentMigration {

	private void migrate(Widget widget) {
		if (WidgetTypeConstants.MATRIX_CONTENTCELLITEM.equals(widget.getTypeName())) {
			Property p = widget.findProperty("column-computation");
			if (p != null) {
				if ("relative-percent".equals(p.getValue())) {
					p.setValue("compute-percentage");
				}
			}
		}
	}
	
	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Stack<Widget> stack = new Stack<Widget>();
			Model model = (Model) contents.get(0);
			stack.push(model.getWidget());
			while (! stack.isEmpty()) {
				Widget widget = stack.pop();
				migrate(widget);
				for (Widget containedWidget : widget.getContents()) {
					stack.push(containedWidget);
				}
			}
		}
	}

}
