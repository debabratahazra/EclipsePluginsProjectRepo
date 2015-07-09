package com.odcgroup.page.ui.properties.sections;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.ISearchField;
import com.odcgroup.page.model.widgets.impl.SearchField;
import com.odcgroup.page.ui.snippet.controls.ParameterListControl;
import com.odcgroup.page.util.AdaptableUtils;

public class ParameterPropertySection extends AbstractPagePropertySection {	

	/** widget factory */
	private TabbedPropertySheetWidgetFactory widgetFactory;
	private ParameterListControl paramControl;
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection
	 * #createControls(org.eclipse.swt.widgets.Composite, 
	 * org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		widgetFactory = getWidgetFactory();
		
		Composite container = widgetFactory.createComposite(parent);
		container.setLayout(new GridLayout(1, false));
		
		paramControl = new ParameterListControl(container, SWT.FILL);
		
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.sections.AbstractPagePropertySection#setInput(
	 * org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}

		Object input = ((IStructuredSelection) selection).getFirstElement();
		Widget widget = (Widget) AdaptableUtils.getAdapter(input, Widget.class);
		if (widget != null && 
				(WidgetTypeConstants.SEARCH_FIELD.equals(widget.getTypeName()) || 
						WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM.equals(widget.getTypeName()))) {			
			ISearchField search = new SearchField(widget);			
			if (paramControl != null) {
				paramControl.setInput(search, part);
			}
		}

		super.setInput(part, selection);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
}
