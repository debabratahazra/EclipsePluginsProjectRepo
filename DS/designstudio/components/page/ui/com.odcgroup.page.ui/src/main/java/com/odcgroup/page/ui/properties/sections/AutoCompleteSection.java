package com.odcgroup.page.ui.properties.sections;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.ui.edit.WidgetEditPart;

public class AutoCompleteSection extends AbstractSection {

	/**	 */
	public AutoCompleteSection() {
		super(PropertyCategory.AUTOCOMPLETE_LITERAL);
	}
	
	/**
	 * 
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object input = ((IStructuredSelection) selection).getFirstElement();
			if (input instanceof WidgetEditPart) {
				Widget widget = ((WidgetEditPart) input).getWidget();
				if (widget.getTypeName().equals(WidgetTypeConstants.SEARCH_FIELD)) {
					if(WidgetHelper.getSearchField(widget).isSearchOnly()) {
						parent.getParent().setEnabled(false);
					} else {
						parent.getParent().setEnabled(true);
					}
				}
			}
		}
		super.setInput(part, selection);
	}

}
