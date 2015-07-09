package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import com.odcgroup.page.ui.command.BaseCommand;
import com.odcgroup.page.ui.properties.table.dialog.TableTransformDialog;

/**
 *
 * @author pkk
 * @param <E> 
 * @param <T> 
 *
 */
public abstract class ListControl<E, T> implements IPropertyTableItem<E, T>, SelectionListener {
	
	/** property Table Control */
	protected PropertyTable propertyTable;
	/**	list elements are sortable */
	private boolean sortable = true;
	/**	 */
	private List<PropertyColumn> columns = new ArrayList<PropertyColumn>();
	/**	 */
	private T input;
	/**	 */
	private IWorkbenchPart part;
	/**	 */
	private Composite control;
	/** */
	private CommandStack commandStack;
	/** */
	private boolean specialControl;
	/** */
	private SpecialControl<T> otherControl;	
	/** */
	private boolean readOnly = false;
	
	/**
	 * @param parent
	 * @param style
	 * @param sortable
	 * @param specialControl
	 */
	public ListControl(Composite parent, int style, boolean sortable, boolean specialControl) {
		this.sortable = sortable;
		this.specialControl = specialControl;
		this.control = createListControl(parent);
	}
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		propertyTable.setEnabled(enabled);
		if (otherControl != null) {
			otherControl.setEnabled(enabled);
		}
		readOnly = !enabled;
		refreshView(false);
	}
	
	/**
	 * @param parent
	 * @return other control
	 */
	protected abstract SpecialControl<T> createOtherControls(Composite parent);
	
	/**
	 * @param parent
	 * @param style
	 * @param sortable 
	 */
	public ListControl(Composite parent, int style, boolean sortable) {
		this(parent, style, sortable, false);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#setInput(java.lang.Object, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setInput(T input, IWorkbenchPart part) {
		this.input = input;
		this.part = part;
		if (otherControl != null) {
			otherControl.setInput(input);
			otherControl.setCommandStack(getCommandStack());
		}
		setTableInput();
		refreshView(false);
	}
	
	/**
	 * @return Composite
	 */
	public Composite getControl() {
		return control;
	}
 	
	/**
	 * @return object
	 */
	public T getInput() {
		return input;
	}
	
	/**
	 * @param reload 
	 * 
	 */
	protected void refreshView(boolean reload) {
		if (reload) {
			setTableInput();			
		}
		propertyTable.getTableViewer().refresh();
		
		if (propertyTable.getTableViewer().getSelection() == null 
				|| propertyTable.getTableViewer().getTable().getItemCount() == 0) {
			propertyTable.getDeleteButton().setEnabled(false);
			propertyTable.getModifyButton().setEnabled(false);
			if (isSortable()) {
				propertyTable.getMoveUpButton().setEnabled(false);
				propertyTable.getMoveDownButton().setEnabled(false);
			}
		}
	}
	
	/**
	 * 
	 */
	private void setTableInput() {
		propertyTable.getTableViewer().setInput(getTableInput(input));
	}
	
	/**
	 * @param parent
	 * @return Composite
	 */
	private Composite createListControl(Composite parent) {
		configureColumns(columns);
		Composite tab = new Composite(parent, SWT.FILL);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		tab.setLayout(layout);
		tab.setLayoutData(new GridData(GridData.FILL_BOTH));
		tab.setBackground(parent.getBackground());
		if (specialControl)
			otherControl = createOtherControls(tab);
		propertyTable = new PropertyTable(tab, columns, isSortable());
		addSelectionListeners();
		configurePropertyTable();
		return tab;		
	}
	
	/**
	 * 
	 */
	private void configurePropertyTable() {
		// Selection Listener
		propertyTable.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (readOnly) {
					return;
				}
				// enable the delete button
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection != null) {
					propertyTable.getDeleteButton().setEnabled(true);
					propertyTable.getModifyButton().setEnabled(true);
					if (selection.size() > 1)
						propertyTable.getModifyButton().setEnabled(false);
				}
				if (selection != null && selection.toList().size() == 1 && getTableInput(input).size() > 1
						&& isSortable()) {
					propertyTable.getMoveUpButton().setEnabled(true);
					propertyTable.getMoveDownButton().setEnabled(true);
				}
			}

		});
		
		// Content Provider
		propertyTable.getTableViewer().setContentProvider(new IStructuredContentProvider() {

			@SuppressWarnings("unchecked")
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof List<?>) {
					return getTableElements((List<E>)inputElement);
				}
				return null;
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer, Object obj, Object obj1) {
			}

		});

		// Label Provider
		propertyTable.getTableViewer().setLabelProvider(new ITableLabelProvider() {

			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			@SuppressWarnings("unchecked")
			public String getColumnText(Object element, int columnIndex) {
				return getPropertyTableColumnText((E) element, columnIndex);
			}

			public void addListener(ILabelProviderListener arg0) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object arg0, String arg1) {
				return true;
			}

			public void removeListener(ILabelProviderListener arg0) {
			}

		});
		
		if (getTableSorter() != null) {
			propertyTable.getTableViewer().setSorter(getTableSorter());
		}
		
		propertyTable.getTableViewer().addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
				if (readOnly) {
					return;
				}
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				BaseCommand cmd = null;
				if (selection != null) {
					if (getPopupDialog(true) != null) {
						getPopupDialog(true).open();	// EDIT	
					} else {
						cmd = getEditCommand();
					}
					executeCommand(cmd);
					refreshView(true);
				}
			}			
		});
	
	}	
	
	/**
	 * 
	 */
	private void addSelectionListeners() {
		propertyTable.getAddButton().addSelectionListener(this);
		propertyTable.getDeleteButton().addSelectionListener(this);
		propertyTable.getModifyButton().addSelectionListener(this);
		if (isSortable()) {
			propertyTable.getMoveUpButton().addSelectionListener(this);
			propertyTable.getMoveDownButton().addSelectionListener(this);
		}
	}
	
	/**
	 * 
	 */
	public void dispose() {
		propertyTable.getAddButton().removeSelectionListener(this);
		propertyTable.getDeleteButton().removeSelectionListener(this);
		propertyTable.getModifyButton().removeSelectionListener(this);
		if (isSortable()) {
			propertyTable.getMoveUpButton().removeSelectionListener(this);
			propertyTable.getMoveDownButton().removeSelectionListener(this);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public final void widgetDefaultSelected(SelectionEvent arg0) {
		// do-nothing
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {		
		BaseCommand cmd = null;
		if (e.widget == propertyTable.getAddButton()) { // ADD
			TableTransformDialog dialog = getPopupDialog(false);
			if (dialog != null) {
				dialog.open();
			} else {
				cmd = getAddCommand();
			}
			executeCommand(cmd);
			refreshView(true);
		} 
		if (getSelection() == null) {
			return;
		}
		ISelection tableSelection = propertyTable.getTableViewer().getSelection();
		if (e.widget == propertyTable.getDeleteButton()) { // DELETE			
			boolean okpressed = MessageDialog.openConfirm(getShell(), "Confirm",
					"Are you sure you want to delete the selection?");
			if (okpressed) {
				cmd = getDeleteCommand(input, getSelection());	
			}
			tableSelection = null;
		} else if (e.widget == propertyTable.getModifyButton()) { 
			TableTransformDialog dialog = getPopupDialog(true);
			if (dialog != null) {
				dialog.open();	// EDIT	
			} else {
				cmd = getEditCommand();
			}
		} else if (isSortable() && e.widget == propertyTable.getMoveUpButton()) {
			cmd = getMoveUpCommand(input, getSelection());	 // MOVE UP				
		} else if (isSortable() && e.widget == propertyTable.getMoveDownButton()) {
			cmd = getMoveDownCommand(input, getSelection()); // MOVE DOWN
		}
		executeCommand(cmd);
		propertyTable.getTableViewer().setSelection(tableSelection, true);
		refreshView(true);
	}	

	/**
	 * @return BaseCommand
	 * 
	 */
	protected BaseCommand getAddCommand() {
		// let the subclasses override
		return null;
	}
	
	/**
	 * @return BaseCommand 
	 */
	protected BaseCommand getEditCommand() {
		// let the subclasses override	
		return null;
	}
	
	/**
	 * @return IStructuredSelection
	 */
	protected IStructuredSelection getListSelection() {
		IStructuredSelection selection = (IStructuredSelection) propertyTable.getTableViewer().getSelection();
		if (selection == null) {
			return null;
		}
		return selection;
	}	
	
	/**
	 * Gets the Shell.
	 * 
	 * @return Shell
	 */
	protected Shell getShell() {
	    return Display.getCurrent().getActiveShell();
	}
	
	/**
	 * @return CommandStack
	 */
	protected CommandStack getCommandStack() {
		if (part != null) {
			return (CommandStack) part.getAdapter(CommandStack.class);
		} else {
			return this.commandStack;
		}
	}	
	
	/**
	 * @param commandStack
	 */
	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}
	
	/**
	 * @param command
	 */
	protected void executeCommand(BaseCommand command) {
		if (command != null && command.canExecute()) {
			getCommandStack().execute(command);
		}	
	}
	
	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}
	
	/**
	 * 
	 */
	public void refresh() {
		if (propertyTable != null) {
			propertyTable.refresh();
		}
	}
	
	/**
	 * @param edit 
	 * @return TableTransformDialog
	 */
	protected abstract TableTransformDialog getPopupDialog(boolean edit);
	
	/**
	 * @param input 
	 * @param element
	 * @return BaseCommand
	 */
	protected abstract BaseCommand getDeleteCommand(T input, E element);
	
	/**
	 * @param input
	 * @param element
	 * @return BaseCommand
	 */
	protected abstract BaseCommand getMoveUpCommand(T input, E element);
	
	/**
	 * @param input
	 * @param element
	 * @return BaseCommand
	 */
	protected abstract BaseCommand getMoveDownCommand(T input, E element);
	
}
