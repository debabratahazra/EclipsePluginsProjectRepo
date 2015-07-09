package com.odcgroup.page.ui.properties.table.controls;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;
import com.odcgroup.page.ui.command.AddWidgetEventCommand;
import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.command.DeleteWidgetEventCommand;
import com.odcgroup.page.ui.command.ReplaceWidgetEventCommand;
import com.odcgroup.page.ui.dialog.EventDialogFactory;
import com.odcgroup.page.ui.dialog.IPageEventDefinitionDialog;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;
import com.odcgroup.page.util.AdaptableUtils;

/**
 * 
 * @author pkk
 * 
 */
public class EventListControl extends ListControl<Event, ITableGroup> {

//	/** The translation service. */
//	private TranslationService translationService;

	/**
	 * @param parent
	 * @param style
	 * @param sortable
	 */
	public EventListControl(Composite parent, int style, boolean sortable) {
		super(parent, style, sortable);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getAddCommand()
	 */
	@Override
	protected BaseCommand getAddCommand() {
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IPageEventDefinitionDialog dialog = getEventDialog(shell, getInputWidget(), null);
		int code = dialog.open();
		if (code == Window.OK) {
			return new AddWidgetEventCommand(getInputWidget(), dialog.getDefinedEvent());
		}
		return super.getAddCommand();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getEditCommand()
	 */
	@Override
	protected BaseCommand getEditCommand() {
		Event oldEvent = getSelection();
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IPageEventDefinitionDialog dialog = getEventDialog(shell, getInputWidget(),oldEvent);
		int code = dialog.open();
		if (code == Window.OK) {
			return new ReplaceWidgetEventCommand(oldEvent, dialog.getDefinedEvent());
		}
		return super.getEditCommand();
	}
	
	/**
	 * @param shell
	 * @param widget
	 * @param event
	 * @return IPageEventDefinitionDialog
	 */
	private IPageEventDefinitionDialog getEventDialog(Shell shell, Widget widget, Event event) {
		return EventDialogFactory.getInstance().createEventDialog(shell, widget, event);
	}

	/**
	 * @return Widget
	 */
	private Widget getInputWidget() {
		IWidgetAdapter widgetAdapter = (IWidgetAdapter) AdaptableUtils.getAdapter(getInput(), IWidgetAdapter.class);
		return widgetAdapter.getWidget();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getDeleteCommand(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	protected BaseCommand getDeleteCommand(ITableGroup input, Event element) {
		return new DeleteWidgetEventCommand(getInputWidget(), element);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveDownCommand(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveDownCommand(ITableGroup input, Event element) {
		// NOT REQUIRED
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getMoveUpCommand(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	protected BaseCommand getMoveUpCommand(ITableGroup input, Event element) {
		// NOT REQUIRED
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#getPopupDialog(boolean)
	 */
	@Override
	protected TableTransformDialog getPopupDialog(boolean edit) {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#configureColumns(java.util.List)
	 */
	@Override
	public void configureColumns(List<PropertyColumn> columns) {
		columns.add(new PropertyColumn("Event", 60, 15, false));
		columns.add(new PropertyColumn("Function", 90, 15, false));
		columns.add(new PropertyColumn("Description", 240, 30, false));
//		columns.add(new PropertyColumn("Parameters", 240, 35, false));
//		columns.add(new PropertyColumn("Properties", 240, 35, false));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getPropertyTableColumnText(java.lang.Object,
	 *      int)
	 */
	@Override
	public String getPropertyTableColumnText(Event element, int columnIndex) {
		if (element != null) {
			if (columnIndex == 0) {
				return element.getEventName();
			} else if (columnIndex == 1) {
				return element.getFunctionName();
//			} else if (columnIndex == 2) {
//				return formatParameters(element.getParameters());
//			} else {
//				return formatProperties(element.getProperties());
//			}
			} else {
				return element.getDescription();
			}
		}
		return null;
	}

//	/**
//	 * Format the parameter list.
//	 * 
//	 * @param parameters
//	 *            The list of parameter
//	 * @return String The formated parameter list in String format
//	 */
//	private String formatParameters(EList<Parameter> parameters) {
//		StringBuffer buf = new StringBuffer();
//		String separator = "";
//		for (Parameter p : parameters) {
//			buf.append(separator);
//			buf.append(p.getName());
//			buf.append("=[");
//			buf.append(p.getValue());
//			buf.append("]");
//			separator = ", ";
//		}
//		return buf.toString();
//	}

//	/**
//	 * Format the property list.
//	 * 
//	 * @param properties
//	 *            The list of property
//	 * @return String The formated property list in String format
//	 */
//	private String formatProperties(EList<Property> properties) {
//		StringBuffer buf = new StringBuffer();
//		String separator = "";
//		for (Property p : properties) {
//			buf.append(separator);
//			buf.append(p.getType().getDisplayName());
//			buf.append("=[");
//			buf.append(getTranslationService().translate(p.getValue()).getText());
//			buf.append("]");
//			separator = ", ";
//		}
//		return buf.toString();
//	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getSelection()
	 */
	@Override
	public Event getSelection() {
		IStructuredSelection selection = getListSelection();
		if (selection != null) {
			return (Event) selection.getFirstElement();
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableElements(java.util.List)
	 */
	@Override
	public Event[] getTableElements(List<Event> inputElement) {
		if (inputElement != null) {
			return inputElement.toArray(new Event[0]);
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableInput(java.lang.Object)
	 */
	@Override
	public List<Event> getTableInput(ITableGroup input) {
		return input.getEvents();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#getTableSorter()
	 */
	@Override
	public ViewerSorter getTableSorter() {
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.odcgroup.page.ui.properties.table.controls.ListControl#createOtherControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected SpecialControl<ITableGroup> createOtherControls(Composite parent) {
		return null;
	}

}
