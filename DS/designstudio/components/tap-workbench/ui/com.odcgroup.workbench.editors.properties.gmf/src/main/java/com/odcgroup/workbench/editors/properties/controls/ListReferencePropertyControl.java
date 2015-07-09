package com.odcgroup.workbench.editors.properties.controls;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EEnumImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardContentsHelper;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.ClipboardManager;
import org.eclipse.gmf.runtime.common.ui.util.CustomData;
import org.eclipse.gmf.runtime.common.ui.util.CustomDataTransfer;
import org.eclipse.gmf.runtime.common.ui.util.ICustomData;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.ListMenuAction;
import com.odcgroup.workbench.editors.properties.util.ListReferenceColumn;
import com.odcgroup.workbench.editors.properties.util.PropertyPopupDialog;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;
import com.odcgroup.workbench.editors.ui.widgets.ReferenceUtil;


/**
 * @author pkk
 *
 */
public class ListReferencePropertyControl implements SelectionListener, MouseListener, ICellModifier, KeyListener, ControlListener {
	
	protected SectionListReferencePropertyHelper referenceUtil = null;
	protected List<ListReferenceColumn> columns;	
	protected AbstractTitleAreaDialog dialog = null;	
	protected PropertyTableWidget propertyTableWidget = null;
	protected Composite body;
	protected EObject parent;
	protected TransactionalEditingDomain editingDomain;
	protected TabbedPropertySheetPage propertySheetPage;
	
	private IAction copyAction;
	private IAction pasteAction;
	private int selectionIndex;
	
	private static final String CUSTOM_DATA_FORMAT_PREFIX = "eReferenceList";
	
	
	/**
	 * @param body
	 * @param dialog
	 * @param referenceUtil
	 */
	public ListReferencePropertyControl(Composite comp, EObject parent, AbstractTitleAreaDialog dialog, SectionListReferencePropertyHelper referenceUtil, TransactionalEditingDomain editingDomain){
		this.body =  OFSUIFactory.INSTANCE.createComposite(comp);
		body.setLayout(new GridLayout(1, false));
		this.parent = parent;
		this.dialog = dialog;
		this.referenceUtil = referenceUtil;		
		this.editingDomain = editingDomain;
		this.columns = getTableColumns(referenceUtil);
	}
	
	/**
	 * 
	 */
	public void dispose() {
		
	}
	
	/**
	 * 
	 */
	public void createListReferencePropertyControls(boolean basicProperty){
		// no custom dialog available, use the default one
		if (dialog == null){
			dialog = new PropertyPopupDialog(getShell());
		}
		ReferenceUtil util = null;
		if (referenceUtil.getParentRef() == null){
			util = new ReferenceUtil(referenceUtil.getLabel(), referenceUtil.getReference(), referenceUtil.getRefObject(), referenceUtil.isSorter(), referenceUtil.isEditable());
		} else {
			util = new ReferenceUtil(referenceUtil.getLabel(), referenceUtil.getReference(), parent, referenceUtil.isSorter(), referenceUtil.isEditable()); 
		}
		propertyTableWidget = new PropertyTableWidget(body, GridData.FILL_BOTH, util, columns, basicProperty, referenceUtil.isMultiSelect());
		// add selection listeners to all button controls of the table widget
		propertyTableWidget.getAddButton().addSelectionListener(this);
		propertyTableWidget.getDeleteButton().addSelectionListener(this);
		
		if (referenceUtil.isEditable())
			propertyTableWidget.getModifyButton().addSelectionListener(this);
		
		if (referenceUtil.isSorter()){
			propertyTableWidget.getMoveUpButton().addSelectionListener(this);
			propertyTableWidget.getMoveDownButton().addSelectionListener(this);
		}	
		
		//contextMenu in case of multiselect
		hookMenus();
		
		// add control listener to the table
		propertyTableWidget.getTable().addControlListener(this);

		propertyTableWidget.getTable().addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				int clientWidth = propertyTableWidget.getTable().getClientArea().width;
				event.height = (new Double(event.gc.getFontMetrics()
						.getHeight() * 1.4)).intValue();
				event.width = clientWidth * 2;
			}
		});

		propertyTableWidget.getTable().addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				if ((event.detail & SWT.SELECTED) == 0)
					return; /* item not selected */
				int clientWidth = propertyTableWidget.getTable().getClientArea().width;
				GC gc = event.gc;
				Color oldForeground = gc.getForeground();
				Color oldBackground = gc.getBackground();
				gc.setForeground(ColorConstants.lightBlue);
				gc.setBackground(ColorConstants.blue);
				gc.fillGradientRectangle(0, event.y, clientWidth, event.height,
						false);
				gc.setForeground(oldForeground);
				gc.setBackground(oldBackground);
				event.detail &= ~SWT.SELECTED;
			}
		});

		// add key listener for specific key pressed events
		propertyTableWidget.getTable().addKeyListener(this);		
		// add mouse listener for doubleclick events
		propertyTableWidget.getTable().addMouseListener(this);
		// cellmodifier		
		//propertyTableWidget.getTableViewer().setCellModifier(this);	
		
		// set the input for the table
		propertyTableWidget.getTableViewer().setInput(getTableInput());		
		
	}

	/**
	 * 
	 */
	private void hookMenus() {
		MenuManager popupMenu = new MenuManager();
		if (referenceUtil.hookCopyAction()){
			hookCopyAction(popupMenu);
		}
		if (referenceUtil.isMultiSelect() && (referenceUtil.getContextMenuActions().size() > 0))
			hookContextMenuActions(popupMenu);
		Menu menu = popupMenu.createContextMenu(propertyTableWidget.getTable());
		propertyTableWidget.getTable().setMenu(menu);
	}
	
	/**
	 * @return
	 */
	private String getCustomDataFormat() {
		return CUSTOM_DATA_FORMAT_PREFIX+referenceUtil.getReference().getName();
	}
	
	/**
	 * @param popupMenu
	 */
	private void hookCopyAction(MenuManager popupMenu){	
		//copy action
		copyAction = new Action() {	
			public void run() {
				TableItem[] items = propertyTableWidget.getTable().getSelection();
				List<EObject> selection = new ArrayList<EObject>();
				for (TableItem tableItem : items) {
					selection.add((EObject)tableItem.getData());
				}
				String clipString = ClipboardUtil.copyElementsToString(selection, null, new NullProgressMonitor());
				CustomData data =  new CustomData(getCustomDataFormat(), clipString.getBytes());
				 /* Add the data to the clipboard manager */
		        if (data != null) {
		            ClipboardManager.getInstance().addToCache(new ICustomData[] { data }, CustomDataTransfer.getInstance());
		            ClipboardManager.getInstance().flushCacheToClipboard();
		        }
			}			
		};
		copyAction.setText("Copy");
		
		//paste action
		pasteAction = new Action() {			
			public void run() {
				final EObject target = parent;		
				ICustomData[] data = ClipboardManager.getInstance().getClipboardData(getCustomDataFormat(), ClipboardContentsHelper.getInstance());
				if (data != null && data.length > 0) {
			    	for (int j = 0; j < data.length; j++) {
			            /* Get the string from the clipboard data */
			            final String xml = new String(data[j].getData());
						executeChanges(new RecordingCommand(getEditingDomain()) {
							protected void doExecute() {
								Collection pastedObjects = ClipboardUtil.pasteElementsFromString(xml, target, null, new NullProgressMonitor());
								if (selectionIndex!=-1){
									EList refList = (EList)parent.eGet(referenceUtil.getReference());
									moveLastElement(pastedObjects, refList);
								}
							}
						});
			        }
			    }

			}			
		};
		pasteAction.setText("Paste");
		
		popupMenu.add(copyAction);
		popupMenu.add(pasteAction);
	}
	
	/**
	 * @param pastedObjects
	 * @param refList
	 */
	private void moveLastElement(Collection pastedObjects, EList refList){
		boolean found = false;
		for (Object object : pastedObjects) {
			if (isLastElement(pastedObjects, refList, object)){
				found = true;
				refList.move(selectionIndex, refList.size()-1);				
			}
		}
		if (found){
			moveLastElement(pastedObjects, refList);
		}
	}
	
	/**
	 * @param pastedObjects
	 * @param referenceList
	 * @param obj
	 * @return
	 */
	private boolean isLastElement(Collection pastedObjects, EList referenceList, Object obj){
		int i = referenceList.indexOf(obj);
		if (i==referenceList.size()-1){
			return true;
		}
		return false;
	}
	
	/**
	 * @param popupMenu
	 */
	private void hookContextMenuActions(MenuManager popupMenu) {
		List<ListMenuAction> actions = referenceUtil.getContextMenuActions();
		for (ListMenuAction action : actions) {
			action.setTable(propertyTableWidget.getTable());
			popupMenu.add(action);
		}
	}
	
	/**
	 * 
	 */
	public void refreshControls() {
		if (isReadOnlyModel()) {
			if (copyAction != null)copyAction.setEnabled(false);
			if (pasteAction != null)pasteAction.setEnabled(false);
			if (!propertyTableWidget.getTable().isDisposed()) {
				propertyTableWidget.getAddButton().setEnabled(false);
				propertyTableWidget.getModifyButton().setText("View");
			}
		}
		if (!propertyTableWidget.getTable().isDisposed()) {
			propertyTableWidget.getTable().removeAll();
			propertyTableWidget.getDeleteButton().setEnabled(false);
			if (referenceUtil.isEditable())
				propertyTableWidget.getModifyButton().setEnabled(false);
			if (referenceUtil.isSorter()){
				propertyTableWidget.getMoveUpButton().setEnabled(false);
				propertyTableWidget.getMoveDownButton().setEnabled(false);
			}
			propertyTableWidget.getTableViewer().setInput(getTableInput());
			propertyTableWidget.setReadOnly(isReadOnlyModel());
		}
		
	}
	
	/**
	 * @return
	 */
	protected AddCommand getAddCommand() {
		AddCommand command = null;
		try {
			command = (AddCommand) AddCommand.create(getEditingDomain(), parent ,referenceUtil.getReference(), referenceUtil.getRefObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return command;
	}
	
	/**
	 * @param listReference
	 * @return
	 */
	private List<ListReferenceColumn> getTableColumns(SectionListReferencePropertyHelper referenceUtil) {
		Method[] methods = referenceUtil.getRefObject().getClass().getMethods();
		if (referenceUtil.getColumns() == null){
			List<ListReferenceColumn> columns = new ArrayList<ListReferenceColumn>();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("set")) {//$NON-NLS-1$
					Class setType = methods[i].getParameterTypes()[0];
					if (!setType.equals(EList.class)) {
						ListReferenceColumn column = new ListReferenceColumn(methods[i].getName().substring(3), methods[i].getName().substring(3));
						if (setType.equals(boolean.class)){
							column.setBooleanType(true);
						} 
						columns.add(column);					
					}
				}
			}
			return columns;
		} else {
			List<ListReferenceColumn> columns = referenceUtil.getColumns();
			for (int i = 0; i < methods.length; i++) {
				for(int j =0;j<columns.size();j++) {
					ListReferenceColumn column = columns.get(j);
					if (methods[i].getName().startsWith("set")) {//$NON-NLS-1$
						if (column.getName().equals(methods[i].getName().substring(3))){
							Class setType = methods[i].getParameterTypes()[0];
							if (!setType.equals(EList.class)) {								
								if (setType.equals(boolean.class)){
									column.setBooleanType(true);
								} 				
							}
						}
					}
				}
			}
			return columns;
		}
	}
	
	/**
	 * @return
	 */
	protected Object getTableInput() {
		if (parent != null){
			return parent.eGet(referenceUtil.getReference());
		}
		return null;
	}
	
	/**
	 * @param command
	 */
	protected void executeChanges(Command command){
		if (command != null && command.canExecute()){
			getEditingDomain().getCommandStack().execute(command);
		} 
		if (propertySheetPage != null && propertySheetPage.getCurrentTab() != null){
			propertySheetPage.refresh();
		}
		this.refreshControls();
	}
	
	/**
	 * @return
	 */
	private Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	/**
	 * @return
	 */
	public SectionListReferencePropertyHelper getReferenceUtil() {
		return referenceUtil;
	}

	/**
	 * @return
	 */
	public TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}
	
	/**
	 * @param eObject
	 */
	public void setParentObject(EObject eObject){
		this.parent = eObject;
	}
	
	/**
	 * @return
	 */
	private boolean isReadOnlyModel() {
		if (parent == null) {
			return false;
		}
		IFile file = OfsResourceHelper.getFile(parent.eResource());
		if (file == null) {
			return true;
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		if (!CommandUtil.makeReadOnlyWriteable(parent, getEditingDomain())) {
			if (e.widget == propertyTableWidget.getModifyButton()) {
				IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
				AddCommand command = (AddCommand)AddCommand.create(getEditingDomain(), parent,referenceUtil.getReference(), sel.toList());
				dialog.setCommand(command);
				dialog.setUpdate(true);
				dialog.setReadOnly(true);
				dialog.open();		
				if (dialog.getReturnCode() == Window.CANCEL || dialog.getReturnCode() == Window.OK) {
					return;
				}				
			}
			return;
		}
		if (e.widget == propertyTableWidget.getAddButton()){ // ADD BUTTON
			if (parent != null){
				//	insert new column
				AddCommand command = getAddCommand();
				dialog.setCommand(command);
				dialog.setUpdate(false);
				dialog.open();
				referenceUtil = getReferenceUtil();
				if (dialog.getReturnCode() == Window.CANCEL) {
					return;
				}
				executeChanges(command);
			}
		} else if (e.widget == propertyTableWidget.getDeleteButton()){ // DELETE BUTTON			
			boolean okpressed = MessageDialog.openConfirm(getShell(), "Confirm", "Are you sure you want to delete the selected "
					+this.referenceUtil.getReference().getEType().getName()+"?");
			if (okpressed){
				IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
				executeChanges(RemoveCommand.create(getEditingDomain(), parent, referenceUtil.getReference(), sel.toList()));
				propertyTableWidget.getTableViewer().refresh();
			}
		} else if (referenceUtil.isEditable() && e.widget == propertyTableWidget.getModifyButton()){ //  MODIFY BUTTON
			IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
			AddCommand command = (AddCommand)AddCommand.create(getEditingDomain(), parent,referenceUtil.getReference(), sel.toList());
			dialog.setCommand(command);
			dialog.setUpdate(true);				
			dialog.open();
			referenceUtil = getReferenceUtil();				
			if (dialog.getReturnCode() == Window.CANCEL) {
				return;
			}				
			executeChanges(command);				
			propertyTableWidget.getTableViewer().refresh();					
		} else if (referenceUtil.isSorter() && e.widget == propertyTableWidget.getMoveUpButton()){ //  UP BUTTON
			IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
			EObjectContainmentEList objList = (EObjectContainmentEList) getTableInput();
			int currIndex = objList.indexOf(sel.toList().get(0));
			if (currIndex !=0){		
				Command command = MoveCommand.create(getEditingDomain(), parent, referenceUtil.getReference(), sel.toList().get(0), currIndex-1);
				executeChanges(command);			
				propertyTableWidget.getTableViewer().setSelection(sel);			
				propertyTableWidget.getTableViewer().refresh();
			}
				
		} else if (referenceUtil.isSorter() && e.widget == propertyTableWidget.getMoveDownButton()){ //  DOWNN BUTTON
			IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
			EObjectContainmentEList objList = (EObjectContainmentEList) getTableInput();
			int currIndex = objList.indexOf(sel.toList().get(0));
			if (currIndex != objList.size()-1){		
				Command command = MoveCommand.create(getEditingDomain(), parent, referenceUtil.getReference(), sel.toList().get(0), currIndex+1);
				executeChanges(command);	
				propertyTableWidget.getTableViewer().setSelection(sel);
				propertyTableWidget.getTableViewer().refresh();
			}
				
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// do nothing		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent event) {
		if (!CommandUtil.makeReadOnlyWriteable(parent, getEditingDomain())) {
			return;
		}
		if (propertyTableWidget.getTable().getItem(new Point(event.x, event.y)) == null) {
			if (parent != null){
				AddCommand command = getAddCommand();
				dialog.setCommand(command);
				dialog.setUpdate(false);				
				dialog.open();
				referenceUtil = getReferenceUtil();
				if (dialog.getReturnCode() == Window.CANCEL) {
					return;
				}
				executeChanges(command);
			}
		} else {
			IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
			AddCommand command = (AddCommand)AddCommand.create(getEditingDomain(), parent,referenceUtil.getReference(), sel.toList());
			dialog.setCommand(command);
			dialog.setUpdate(true);				
			dialog.open();
			referenceUtil = getReferenceUtil();				
			if (dialog.getReturnCode() == Window.CANCEL) {
				return;
			}				
			executeChanges(command);				
			propertyTableWidget.getTableViewer().refresh();
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent event) {
		if (!CommandUtil.makeReadOnlyWriteable(parent, getEditingDomain())) {
			return;
		}
		// get the list selection
		IStructuredSelection sel = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
		if (sel.isEmpty()){
			selectionIndex = -1;
		} else {
			EObjectContainmentEList objList = (EObjectContainmentEList) getTableInput();
			selectionIndex = objList.indexOf(sel.toList().get(0));			
		}
		
		if (sel.size()>1){
			propertyTableWidget.getAddButton().setEnabled(false);
			propertyTableWidget.getModifyButton().setEnabled(false);
			if (referenceUtil.isSorter()){
				propertyTableWidget.getMoveUpButton().setEnabled(false);
				propertyTableWidget.getMoveDownButton().setEnabled(false);
			}
		} else {
			propertyTableWidget.getAddButton().setEnabled(true);
			if (referenceUtil.isSorter()){
				propertyTableWidget.getMoveUpButton().setEnabled(true);
				propertyTableWidget.getMoveDownButton().setEnabled(true);	
			}
		}
		// get the clipboard contents for custom data format
		ICustomData[] data = ClipboardManager.getInstance().getClipboardData(getCustomDataFormat(),
				ClipboardContentsHelper.getInstance());
		
		if (propertyTableWidget.getTable().getItem(new Point(event.x, event.y)) == null 
				&& referenceUtil.hookCopyAction()) {
			copyAction.setEnabled(false);
			pasteAction.setEnabled(false);
			if (data != null){
				pasteAction.setEnabled(true);
			}
		} else {
			
			if (sel != null && referenceUtil.hookCopyAction()){
				copyAction.setEnabled(true);
				if (data != null){
					pasteAction.setEnabled(true);
				} else {
					pasteAction.setEnabled(false);
				}
			}
		}
	}

	public void mouseUp(MouseEvent e) {	
		//donothing
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		EObject obj = (EObject) element;
		List list = obj.eClass().getEAllAttributes();
		for (Iterator i = list.iterator(); i.hasNext();) {
			EAttribute attribute = (EAttribute) i.next();		
			if (attribute.getName().toLowerCase().equals(property.toLowerCase()) && attribute.getEType().getClass() != EEnumImpl.class) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {
		EObject obj = (EObject) element;
		List list = obj.eClass().getEAllAttributes();
		for (Iterator i = list.iterator(); i.hasNext();) {
			EAttribute attribute = (EAttribute) i.next();
			if (attribute.getName().toLowerCase().equals(property.toLowerCase())) {
				return obj.eGet(attribute);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {
		EObject entry = (EObject) ((TableItem) element).getData();
		List list = entry.eClass().getEAllAttributes();
		for (Iterator i = list.iterator(); i.hasNext();) {
			EAttribute attribute = (EAttribute) i.next();
			if (attribute.getName().toLowerCase().equals(property.toLowerCase())) {
				if (!entry.eGet(attribute).equals(value)) {
					Command command = SetCommand.create(getEditingDomain(), entry, attribute, value);
					executeChanges(command);
					propertyTableWidget.getTableViewer().refresh();
					refreshControls();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyListener#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
		if (!CommandUtil.makeReadOnlyWriteable(parent, getEditingDomain())) {
			return;
		}
		if (e.character == SWT.DEL) { // '\177' Del Key
			boolean okpressed = MessageDialog.openConfirm(getShell(), "Confirm", "Are you sure you want to delete the selected "
					+this.referenceUtil.getReference().getEType().getName()+"?");
			if (okpressed){
				IStructuredSelection selection = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
				if (!selection.isEmpty()) {
					executeChanges(RemoveCommand.create(getEditingDomain(),	selection.toList()));
				}
			}
		} else if (e.keyCode == 0x100000b) {
			IStructuredSelection selection = (IStructuredSelection) propertyTableWidget.getTableViewer().getSelection();
			if (!selection.isEmpty())
				propertyTableWidget.getTableViewer().editElement(selection.getFirstElement(), 0);
		} else if (e.keyCode == 0x1000009) { // insert key
			// insert new column
			if (parent != null){
				AddCommand command = getAddCommand();
				dialog.setCommand(command);
				dialog.setUpdate(false);
				dialog.open();
				referenceUtil = getReferenceUtil();
				if (dialog.getReturnCode() == Window.CANCEL) {
					return;
				}
				executeChanges(command);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// donothing		
	}

	public void controlMoved(ControlEvent e) {
		//donothing		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlResized(ControlEvent e) {
		final Table table = propertyTableWidget.getTable();
		int width = table.getClientArea().width
				- table.getBorderWidth() * 2
				- table.getVerticalBar().getSize().x;
		for (int ii = 0; ii < columns.size(); ii++) {
			table.getColumn(ii).setWidth(width / columns.size());
		}
	}

	public TabbedPropertySheetPage getPropertySheetPage() {
		return propertySheetPage;
	}

	public void setPropertySheetPage(
			TabbedPropertySheetPage propertySheetPage) {
		this.propertySheetPage = propertySheetPage;
	}
}
