package com.odcgroup.page.transformmodel.ui.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.page.model.Widget;

/**
 * Builds the Widgets for an MdfAssociation.
 * 
 * @author atr
 */
public class MdfAssociationOneByRefWidgetBuilder extends AbstractMdfAssociationWidgetBuilder {

	/**
	 * Creates a new MdfAssociationOneByRefWidgetBuilder.
	 * 
	 * @param modelElement
	 *            The MdfModelElement to create
	 * @param parentWidget
	 *            The parent Widget to which the child Widgets will be added
	 * @param builderContext
	 *            The context
	 */
	public MdfAssociationOneByRefWidgetBuilder(MdfAssociation modelElement, Widget parentWidget,
			WidgetBuilderContext builderContext) {
		super(modelElement, parentWidget, builderContext);
	}

	/**
	 * Creates the Widgets. These Widgets may contain child Widgets.
	 * 
	 * @return List The newly created Widgets. These Widgets may contain child
	 *         Widgets
	 */
	public List<Widget> buildWidgets() {
		String name = getAssociatedClass().getName();

		Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		Dialog d = new MessageDialog(
				s,
				"Error",
				null,
				"Please create a separate dataset based on the "
						+ name
						+ " entity and link this dataset in the domain model. Refer to the help documentation for more details",
				0, new String[] { "OK" }, 0);
		d.open();
		return new ArrayList<Widget>();
	}
}