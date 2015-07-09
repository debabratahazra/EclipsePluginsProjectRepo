package com.odcgroup.page.ui.properties.table.controls;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;

/**
 * a basic List Control to manage add/delete/edit of a reference(list)<E> of an element<T>
 * which returns the managed list for further action
 *
 * @author pkk
 * @param <E> 
 * @param <T> 
 *
 */
public abstract class BasicListControl<E, T> implements IPropertyTableItem<E, T>, SelectionListener, MouseListener, ICellModifier {
	
	/** property Table Control */
	protected PropertyTable propertyTable;
	/**	 */
	private List<PropertyColumn> columns = new ArrayList<PropertyColumn>();
	/**	 */
	private T input;
	/**	 */
	private Composite control;
	/** */
	private boolean specialControl;
	/** */
	private SpecialControl<T> otherControl;
	/** */	
	private boolean enableCellEditing = false;

	/**
	 * @param parent
	 * @param style
	 * @param specialControl
	 * @param enableCellEdit 
	 */
	public BasicListControl(Composite parent, int style, boolean specialControl, boolean enableCellEdit) {
		this.specialControl = specialControl;
		this.control = createListControl(parent, enableCellEdit);
		this.enableCellEditing = enableCellEdit;
	}
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		propertyTable.setEnabled(enabled);
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
	 */
	public BasicListControl(Composite parent, int style) {
		this(parent, style, false, false);
	}
	
	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.IPropertyTableItem#setInput(java.lang.Object, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setInput(T input, IWorkbenchPart part) {
		this.input = input;
		if (otherControl != null) {
			otherControl.setInput(input);
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
	public void refreshView(boolean reload) {
		if (reload) {
			setTableInput();			
		}
		propertyTable.getTableViewer().refresh();
		
		if (propertyTable.getTableViewer().getSelection() == null 
				|| propertyTable.getTableViewer().getTable().getItemCount() == 0) {
			propertyTable.getDeleteButton().setEnabled(false);
			propertyTable.getModifyButton().setEnabled(false);
		}
	}
	
	/**
	 * 
	 */
	private void setTableInput() {
		List<E> list = getTableInput(input);
		propertyTable.getTableViewer().setInput(list);
	}
	
	/**
	 * @param parent
	 * @param enableCellEdit 
	 * @return Composite
	 */
	private Composite createListControl(Composite parent, boolean enableCellEdit) {
		configureColumns(columns);
		Composite tab = new Composite(parent, SWT.FILL);
		GridLayout layout = new GridLayout(1, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		tab.setLayout(layout);
		tab.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		if (specialControl)
			otherControl = createOtherControls(tab);
		propertyTable = new PropertyTable(tab, columns, false);
		addSelectionListeners();
		configurePropertyTable(enableCellEdit);
		return tab;		
	}	
	
	/**
	 * @param enableCellEdit 
	 * 
	 */
	private void configurePropertyTable(boolean enableCellEdit) {
		// Selection Listener
		propertyTable.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
				// enable the delete button
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection != null) {
					propertyTable.getDeleteButton().setEnabled(true);
					propertyTable.getModifyButton().setEnabled(true);
					if (selection.size() > 1)
						propertyTable.getModifyButton().setEnabled(false);
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
		
		if (enableCellEdit) {
			CellEditor[] editors = new CellEditor[columns.size()];
			for (int ii = 0; ii < columns.size();ii++) {
				editors[ii] = new TextCellEditor(propertyTable.table);
			}
			String[] columnNames = new String[columns.size()];
			int ii = 0;
			for (PropertyColumn column : columns) {
				columnNames[ii] = column.getName();
				ii++;
			}
			propertyTable.getTableViewer().setColumnProperties(columnNames);
	
			propertyTable.getTableViewer().setCellEditors(editors);
			propertyTable.getTableViewer().setCellModifier(this);
		}
	
	}	
	
	/**
	 * 
	 */
	private void addSelectionListeners() {
		propertyTable.getAddButton().addSelectionListener(this);
		propertyTable.getDeleteButton().addSelectionListener(this);
		propertyTable.getModifyButton().addSelectionListener(this);
		propertyTable.table.addMouseListener(this);
	}
	
	/**
	 * 
	 */
	public void dispose() {
		propertyTable.getAddButton().removeSelectionListener(this);
		propertyTable.getDeleteButton().removeSelectionListener(this);
		propertyTable.getModifyButton().removeSelectionListener(this);
		propertyTable.table.removeMouseListener(this);
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
		if (e.widget == propertyTable.getAddButton()) { // ADD
			ReferenceDefinitionDialog<E> dialog = getPopupDialog(false);
			if (dialog != null) {
				int status = dialog.open();
				if (status == TitleAreaDialog.OK){
					addReference(dialog.getOutput());			
					refreshView(true);
				}
			}

		} 
		if (getSelection() == null) {
			return;
		}
		boolean reloadRequired = false;
		ISelection tableSelection = propertyTable.getTableViewer().getSelection();
		if (e.widget == propertyTable.getDeleteButton()) { // DELETE			
			boolean okpressed = MessageDialog.openConfirm(getShell(), "Confirm",
					"Are you sure you want to delete the selection?");
			if (okpressed) {
				deleteReference(getSelection());	
				reloadRequired = true;
			}
			tableSelection = null;
		} else if (e.widget == propertyTable.getModifyButton()) { 
			ReferenceDefinitionDialog<E> dialog = getPopupDialog(true);
			int status = dialog.open();
			if (status == TitleAreaDialog.OK){// EDIT	
				updateReference(dialog.getOutput());
			} 
		}
		propertyTable.getTableViewer().setSelection(tableSelection, true);
		refreshView(reloadRequired);
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
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent event) {		
		if (propertyTable.table.getItem(new Point(event.x, event.y)) == null) {
			addReference(createReference());
			refreshView(true);
		}
		
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		return enableCellEditing;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public final Object getValue(Object element, String property) {
		return getCellValue((E) element, property);
	}
	
	/**
	 * @param element
	 * @param property
	 * @return object
	 */
	public abstract Object getCellValue(E element, String property);
	
	/**
	 * @param element
	 * @param property
	 * @param value
	 */
	public abstract void modifyCellValue(E element, String property, Object value);

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public final void modify(Object element, String property, Object value) {
		E reference = (E)((TableItem) element).getData();
		modifyCellValue(reference, property, value);
		updateReference(reference);	
		refreshView(true);
	}

	/**
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent e) {		
	}

	/**
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent e) {		
	}

	/**
	 * @param edit 
	 * @return TableTransformDialog
	 */
	protected abstract ReferenceDefinitionDialog<E> getPopupDialog(boolean edit);	
	
	/**
	 * @param reference
	 */
	protected abstract void addReference(E reference);
	/**
	 * @param reference
	 */
	protected abstract void updateReference(E reference);
	/**
	 * @param reference
	 */
	protected abstract void deleteReference(E reference);
	
	/**
	 * @return reference
	 */
	protected abstract E createReference();


}
