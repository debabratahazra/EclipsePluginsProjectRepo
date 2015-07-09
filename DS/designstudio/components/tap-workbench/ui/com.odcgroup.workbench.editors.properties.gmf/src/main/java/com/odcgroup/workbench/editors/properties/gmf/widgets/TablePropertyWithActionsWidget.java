package com.odcgroup.workbench.editors.properties.gmf.widgets;

import org.eclipse.emf.ecore.EReference;

import com.odcgroup.workbench.editors.properties.internal.CopyAction;
import com.odcgroup.workbench.editors.properties.internal.PasteAction;
import com.odcgroup.workbench.editors.properties.widgets.TablePropertyWidget;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class TablePropertyWithActionsWidget extends TablePropertyWidget {

	/**
	 * @param reference
	 */
	public TablePropertyWithActionsWidget(EReference reference) {
		super(reference);

		String formatType = "eReferenceList"+getStructuralFeature().getName();
		addMenuAction(new CopyAction(this, "Copy", formatType));
		addMenuAction(new PasteAction(this, "Paste", formatType));
	}

}
