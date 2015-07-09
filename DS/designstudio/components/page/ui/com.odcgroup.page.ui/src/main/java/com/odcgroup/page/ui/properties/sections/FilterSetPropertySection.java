package com.odcgroup.page.ui.properties.sections;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IFilterSet;
import com.odcgroup.page.ui.command.AddWidgetFilterSetCommand;
import com.odcgroup.page.ui.command.UpdateWidgetFilterSetCommand;
import com.odcgroup.page.ui.snippet.controls.WidgetFilterSetControl;
import com.odcgroup.page.ui.util.DomainObjectUtils;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * Property Section to manage the filter set for a widget
 *
 * @author pkk
 *
 */
public class FilterSetPropertySection extends AbstractPagePropertySection {	
	
	/** The selected widget. */
	private Widget widget;

	/** The control for managing filterset */
	private WidgetFilterSetControl control;
	
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		
		Composite body = getWidgetFactory().createFlatFormComposite(parent);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);
		
		this.control = new WidgetFilterSetControl(body, SWT.FILL, false) {
			@Override
			public void addFilterSet(Widget parent, IFilterSet filterSet) {
				Command command = new AddWidgetFilterSetCommand(widget, filterSet);
				getCommandStack().execute(command);			
			}

			@Override
			public void updateFilterSet(Widget parent, IFilterSet filterSet) {
				Command command = new UpdateWidgetFilterSetCommand(widget, filterSet);
				getCommandStack().execute(command);				
			}

			@Override
			public String getTargetDataset() {
				MdfDataset dataset = DomainObjectUtils.getDataset(widget.getRootWidget());
				if (dataset == null) {
					return null;
				}
				return dataset.getQualifiedName().getQualifiedName();
			}			
		};
	}	
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		Object input = ((IStructuredSelection) selection).getFirstElement();
		widget = (Widget) AdaptableUtils.getAdapter(input, Widget.class);
		if (widget != null) {
			setReflectiveInput(widget, part);
		} 
	}
	
	/**
	 * Gets the CommandStack.
	 * 
	 * @return CommandStack
	 */
	private CommandStack getCommandStack() {
	    return (CommandStack) getPart().getAdapter(CommandStack.class);
	}
	
	/**
	 * @param widget
	 * @param part
	 */
	private void setReflectiveInput(Widget widget, IWorkbenchPart part) {
		if (control != null && widget != null) {
			control.setInput(widget);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	public boolean shouldUseExtraSpace() {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	public void refresh() {
		super.refresh();
		if (control != null) {
			control.refresh();
		}
	}

}
