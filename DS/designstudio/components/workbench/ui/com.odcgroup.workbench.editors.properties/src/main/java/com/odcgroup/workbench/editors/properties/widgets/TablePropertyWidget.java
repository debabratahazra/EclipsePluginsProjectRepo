package com.odcgroup.workbench.editors.properties.widgets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.odcgroup.workbench.editors.properties.internal.GenericPropertyDefinitionDialogCreator;
import com.odcgroup.workbench.editors.properties.internal.PropertyTable;
import com.odcgroup.workbench.editors.properties.internal.SWTWidgetFactory;
import com.odcgroup.workbench.editors.properties.item.AbstractPropertyReferenceWidget;
import com.odcgroup.workbench.editors.properties.item.PropertyTableColumn;
import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialogCreator;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;
import com.odcgroup.workbench.editors.properties.model.IPropertyTableWidget;
import com.odcgroup.workbench.editors.properties.model.IPropertyWidgetAction;

/**
 *
 * @author pkk
 *
 */
public class TablePropertyWidget extends AbstractPropertyReferenceWidget
		implements IPropertyTableWidget, SelectionListener, MouseListener, KeyListener, ControlListener {
	
	private List<PropertyTableColumn> tableColumns = new ArrayList<PropertyTableColumn>();
	private List<IPropertyWidgetAction> menuActions = new ArrayList<IPropertyWidgetAction>();
	
	private PropertyTable propertyTable;
	private IPropertyDefinitionDialogCreator dialogCreator = null;
	private boolean sortable = false;
	private boolean grouped = false;
	
	/**
	 * @param reference
	 */
	public TablePropertyWidget(EReference reference) {
		this(reference, null);
	}


	/**
	 * @param body
	 * @param property
	 */
	public TablePropertyWidget(EReference reference, String label) {
		super(reference, label, null);
	}
	
	/**
	 * @param reference
	 * @param label
	 * @param grouped
	 */
	public TablePropertyWidget(EReference reference, String label, boolean grouped) {
		this(reference, label);
		this.grouped = grouped;
		
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#createPropertyControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPropertyControl(Composite body) {	
		if (getTableColumns().isEmpty()) {
			tableColumns = configureColumns(getReference().getEReferenceType());
		}
		if (getLabel() != null && !grouped) {
			SWTWidgetFactory.createLabel(body, getLabel(), getTooltip(), false);
		}
		propertyTable = new PropertyTable(body, tableColumns, isSortable());

		propertyTable.getTableViewer().setContentProvider(new TableContentProvider());
		propertyTable.getTableViewer().setLabelProvider(new TableLabelProvider());
		
		configureListeners();		

		hookContextualMenu();
	}
	
	/**
	 * hook popup menu to the table control
	 */
	public void hookContextualMenu() {
		if (!getMenuActions().isEmpty()){
			MenuManager popupMenu = new MenuManager();
			for (IAction action : getMenuActions()) {
				popupMenu.add(action);
			}
			popupMenu.addMenuListener(new IMenuListener(){

				public void menuAboutToShow(IMenuManager manager) {
					IContributionItem[] items = manager.getItems();
					for (IContributionItem contributionItem : items) {
						contributionItem.update();
					}					
				}
				
			});
			Menu menu = popupMenu.createContextMenu(getTableControl());
			getTableControl().setMenu(menu);
		}
	}	
	
	
	/**
	 * configure listeners to the table control
	 */
	private void configureListeners() {		
		
		propertyTable.getTableViewer().addSelectionChangedListener(new TableSelectionChangedListener());
		getTableControl().addMouseListener(this);
		getTableControl().addKeyListener(this);
		getTableControl().addControlListener(this);
		
		addButtonSelectionListeners();
	}
	
	/**
	 * add selection listeners to the action buttons
	 */
	private void addButtonSelectionListeners() {
		propertyTable.getAddButton().addSelectionListener(this);
		propertyTable.getDeleteButton().addSelectionListener(this);
		propertyTable.getModifyButton().addSelectionListener(this);
		if (isSortable()) {
			propertyTable.getMoveUpButton().addSelectionListener(this);
			propertyTable.getMoveDownButton().addSelectionListener(this);
		}
	}
	
	/**
	 * @param element
	 * @param columnIndex
	 * @return
	 */
	public String getPropertyTableColumnText(Object element, int columnIndex) {
		EStructuralFeature feature = tableColumns.get(columnIndex).getFeature();
		if (feature != null) {
			Object obj = ((EObject) element).eGet(feature);
			if (obj != null) {
				return obj.toString();
			}
		}	
		return "";
	}
	
	/**
	 * @return
	 */
	public Object getTableInput() {
		return getElement().eGet(getReference());
	}
	
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#initPropertyControls()
	 */
	@Override
	protected void initPropertyControls() {
		propertyTable.getTableViewer().setInput(getTableInput());			
	}
	
	/**
	 * @return the tableColumns
	 */
	public List<PropertyTableColumn> getTableColumns() {
		return tableColumns;
	}
		
	/**
	 * @param dialogCreator the dialogCreator to set
	 */
	public void setDialogCreator(IPropertyDefinitionDialogCreator dialogCreator) {
		this.dialogCreator = dialogCreator;
	}
	
	/**
	 * @param tableColumn
	 */
	public void addTableColumn(PropertyTableColumn tableColumn) {
		tableColumns.add(tableColumn);
	}
	
	/**
	 * @param listReference
	 * @return
	 */
	private List<PropertyTableColumn> configureColumns(EClass refClass) {
		List<PropertyTableColumn> columns = new ArrayList<PropertyTableColumn>();
		List<EStructuralFeature> features = refClass.getEAllStructuralFeatures();
		PropertyTableColumn column = null;
		for (EStructuralFeature sfeature : features) {
			column = new PropertyTableColumn(sfeature.getName(), sfeature, 3);
			columns.add(column);
		}	
		return columns;
	}
	
	/**
	 * @return
	 */
	private IPropertyDefinitionDialogCreator getDialogCreator() {
		if (dialogCreator != null) {
			return dialogCreator;
		} else {
			return new GenericPropertyDefinitionDialogCreator();
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void handleDelete() {
		boolean okpressed = MessageDialog.openConfirm(getShell(), "Confirm", 
			"Are you sure you want to delete the selected "
			+getReference().getEType().getName()+"(s)?");
		if (okpressed){
			IStructuredSelection sel = getTableSelection();
			Object obj = getElement().eGet(getReference());
			List newList = new ArrayList();
			if (obj instanceof List<?>) {
				newList.addAll((List)obj);
				newList.removeAll(sel.toList());
			}
			notifyPropertyFeatureChange(newList);
		}
	}
	
	/**
	 *  handle create
	 */
	@SuppressWarnings("unchecked")
	private void handleCreate() {
		EClass refClass = getReference().getEReferenceType();
		EObject newObj = EcoreUtil.create(refClass);
		IPropertyDefinitionDialog dialog = getDialogCreator().createDialog(getShell(), newObj, getElement(), false);
		dialog.open();
		if (dialog.getReturnCode() == Dialog.OK) {
			Object obj = getElement().eGet(getReference());
			List newList = new ArrayList();
			if (obj instanceof List<?>) {
				newList.addAll((List)obj);
				newList.add(dialog.getDefinedProperty());
			}
			notifyPropertyFeatureChange(newList);
		}
	}
	
	/**
	 * handle update
	 */
	@SuppressWarnings("unchecked")
	private void handleUpdate() {
		IStructuredSelection sel = getTableSelection();
		if (sel != null && !sel.isEmpty()) {
			EObject updateObj = EcoreUtil.copy((EObject) sel.getFirstElement());
			IPropertyDefinitionDialog dialog = getDialogCreator().createDialog(getShell(), updateObj, getElement(), true);
			dialog.open();
			if (dialog.getReturnCode() == Dialog.OK) {
				Object obj = getElement().eGet(getReference());
				List newList = new ArrayList();
				if (obj instanceof List<?>) {
					newList.addAll((List)obj);
					int index = newList.indexOf(sel.getFirstElement());
					newList.set(index, dialog.getDefinedProperty());
					getTableControl().select(index);
				}
				notifyPropertyFeatureChange(newList);
			}
		}
	}
	
	/**
	 * handle move up the list
	 */
	@SuppressWarnings("unchecked")
	private void handleMoveUp() {
		IStructuredSelection sel = getTableSelection();
		if (sel.size()> 1) {
			return;
		}
		Object obj = getElement().eGet(getReference());
		List newList = new ArrayList();
		if (obj instanceof List<?>) {
			newList.addAll((List)obj);
			Object selection = sel.getFirstElement();
			int index = newList.indexOf(selection);
			index = index - 1;
			if (index >= 0 && index < newList.size()) {
				if (newList.remove(selection))
					newList.add(index, selection);
			}
		}
		notifyPropertyFeatureChange(newList);
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void handleMoveDown() {
		IStructuredSelection sel = getTableSelection();
		if (sel.size()> 1) {
			return;
		}
		Object obj = getElement().eGet(getReference());
		List newList = new ArrayList();
		if (obj instanceof List<?>) {
			newList.addAll((List)obj);
			Object selection = sel.getFirstElement();
			int index = newList.indexOf(selection);
			index = index + 1;
			if (index >= 0 && index < newList.size()) {
				if (newList.remove(selection))
					newList.add(index, selection);
			}
		}
		notifyPropertyFeatureChange(newList);		
	}
	
	/**
	 * @return
	 */
	protected IStructuredSelection getTableSelection() {
		IStructuredSelection sel = (IStructuredSelection) propertyTable.getTableViewer().getSelection();
		return sel;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == propertyTable.getAddButton()){
			handleCreate();
		} else if (e.widget == propertyTable.getModifyButton()) {
			handleUpdate();
		} else if (e.widget == propertyTable.getDeleteButton()) {
			handleDelete();
		} else if (isSortable() && e.widget == propertyTable.getMoveUpButton()) {
			handleMoveUp();
		} else if (isSortable() && e.widget == propertyTable.getMoveDownButton()) {
			handleMoveDown();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent event) {
		Point p = new Point(event.x, event.y);
		TableItem item = getTableControl().getItem(p);
		if (item == null) {
			handleCreate();
		} else {
			handleUpdate();
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent e) {
		IStructuredSelection sel = getTableSelection();
		if (sel == null || sel.isEmpty()) {
			propertyTable.getModifyButton().setEnabled(false);
			propertyTable.getDeleteButton().setEnabled(false);
		}
		if (sel != null && sel.size() == 1) {
			propertyTable.getModifyButton().setEnabled(true);
			propertyTable.getDeleteButton().setEnabled(true);
		}
		if (sel != null && sel.size() > 1) {
			propertyTable.getModifyButton().setEnabled(false);
			propertyTable.getDeleteButton().setEnabled(true);	
			if (isSortable()) {
				propertyTable.getMoveUpButton().setEnabled(false);
				propertyTable.getMoveDownButton().setEnabled(false);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (e.character == SWT.DEL) { 
			handleDelete();
		}else if (e.keyCode == SWT.F2) { 
			handleUpdate();
		} else if (e.keyCode == SWT.INSERT) { 
			handleCreate();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt.events.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlMoved(ControlEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlResized(ControlEvent e) {
	}

	/**
	 * @return the sortable
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * @param sortable the sortable to set
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.widgets.model.IPropertyTableWidget#addMenuAction(com.odcgroup.workbench.editors.widgets.model.IPropertyWidgetAction)
	 */
	public void addMenuAction(IPropertyWidgetAction action) {
		menuActions.add(action);		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.widgets.model.IPropertyTableWidget#getMenuActions()
	 */
	public List<IPropertyWidgetAction> getMenuActions() {
		return menuActions;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.item.AbstractPropertyWidget#notifyPropertyFeatureChange(java.lang.Object)
	 */
	@Override
	public void notifyPropertyFeatureChange(Object value) {
		if (value == null) {
			value = Collections.emptyList();
		}
		super.notifyPropertyFeatureChange(value);
	}


	/**
	 * @return
	 */
	private Table getTableControl() {
		return (Table) getWidgetControl();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.AbstractPropertyWidget#getWidgetControl()
	 */
	public Control getWidgetControl() {
		return propertyTable.getTableViewer().getTable();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#addPropertyFeatureChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.addPropertyFeatureChangeListener(propertyChangeListener);
		for (IPropertyWidgetAction action : menuActions) {
			action.addPropertyChangeListener(propertyChangeListener);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.internal.AbstractPropertyWidget#removePropertyFeatureChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	@Override
	public void removePropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		super.removePropertyFeatureChangeListener(propertyChangeListener);
		for (IPropertyWidgetAction action : menuActions) {
			action.removePropertyChangeListener(propertyChangeListener);
		}
	}	
	
	/**
	 *
	 */
	class TableLabelProvider implements ITableLabelProvider {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			return getPropertyTableColumnText(element, columnIndex);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 */
		public void addListener(ILabelProviderListener arg0) {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
		 */
		public void dispose() {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
		 */
		public boolean isLabelProperty(Object arg0, String arg1) {
			return true;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
		 */
		public void removeListener(ILabelProviderListener arg0) {
		}

	}
	
	/**
	 *
	 */
	class TableContentProvider implements IStructuredContentProvider {
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List<?>) {
				return ((List<?>)inputElement).toArray();
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged
		 * (org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object obj, Object obj1) {
		}

	}
	
	/**
	 *
	 */
	class TableSelectionChangedListener implements ISelectionChangedListener {
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged
		 * (org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			// enable the delete button
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection != null && !selection.isEmpty()) {
				propertyTable.getDeleteButton().setEnabled(true);
				propertyTable.getModifyButton().setEnabled(true);
				if (selection.size() > 1)
					propertyTable.getModifyButton().setEnabled(false);
			}
			if (selection != null && selection.toList().size() == 1 
					&& getTableInput() != null
					&& isSortable()) {
				propertyTable.getMoveUpButton().setEnabled(true);
				propertyTable.getMoveDownButton().setEnabled(true);
			}
			for (IPropertyWidgetAction action : menuActions) {
				action.selectionChanged(event);
			}
		}

	}

}
