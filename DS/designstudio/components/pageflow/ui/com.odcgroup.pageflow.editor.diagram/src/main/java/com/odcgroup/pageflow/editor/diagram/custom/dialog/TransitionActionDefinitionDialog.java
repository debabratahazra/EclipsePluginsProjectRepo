package com.odcgroup.pageflow.editor.diagram.custom.dialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.model.PageflowFactory;
import com.odcgroup.pageflow.model.PageflowPackage;
import com.odcgroup.pageflow.model.ProblemManagement;
import com.odcgroup.pageflow.model.Property;
import com.odcgroup.pageflow.model.TransitionAction;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

public class TransitionActionDefinitionDialog extends AbstractTitleAreaDialog {
	
	
	private TransitionAction transitionAction;
	
	private Text actionName;
	private Text desc;
	private Text uri;
	private Composite radio;
	protected Button deleteButton;
	protected Button addButton;	
	protected Button editButton;
	private Button validBtn;
	protected String[] columnLabels = new String[] {"Name","Value"};
	protected Table table;
	private Font tableFont;
	protected TableViewer tableViewer;
	private boolean validationProcess = false;
	
	private List properties = null;
	
	private static final String PREV_STATE = "Go back to previous state";
	private static final String CONTINUE = "Continue";
	private static final String VALIDATION = "Implements a Validation process Class ";

	/**
	 * @param parentShell
	 * @param addCommand
	 */
	public TransitionActionDefinitionDialog(Shell parentShell, AddCommand addCommand) {
		super(parentShell);
		Collection collection = addCommand.getCollection();
		this.transitionAction = (TransitionAction) collection.iterator().next();
		this.properties = populateProperties(transitionAction.getProperty());
		this.domain = addCommand.getDomain();
	}
	
	private List populateProperties(EList props){
		List properties = new ArrayList();
		for(int i=0;i < props.size();i++){
			Property property = (Property)props.get(i);
			ActionProperty ap = new ActionProperty();
			ap.setName(property.getName());
			ap.setValue(property.getValue());
			properties.add(ap);
		}
		return properties;
	}
	
	/**
	 * @param parentShell
	 */
	public TransitionActionDefinitionDialog(Shell parentShell){
		super(parentShell);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog#setCommand(org.eclipse.emf.edit.command.AddCommand)
	 */
	public void setCommand(AddCommand command) {
		Collection collection = command.getCollection();
		this.transitionAction = (TransitionAction) collection.iterator().next();
		this.properties = populateProperties(transitionAction.getProperty());
		this.domain = command.getDomain();
	}
	
	/**
	 * @param parentShell
	 * @param addCommand
	 * @param update
	 */
	public TransitionActionDefinitionDialog(Shell parentShell, AddCommand addCommand, boolean update){
		this(parentShell, addCommand);
		this.update = update;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(Messages.TransitionActionDefDialogTitle);
		this.setMessage(Messages.TransitionActionDefDialogMsg);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.TransitionActionDefDialogTxt);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		super.setMessage(Messages.TransitionActionDefDialogMsg);
		Composite composite = (Composite) super.createDialogArea(parent);
		 // create the composite to hold the widgets
		GridData gridData;
		
		Composite body = new Composite(composite, SWT.NULL);
		GridLayout layout = new GridLayout(1, false);
		body.setLayout(layout);
		
		//added for issue 11185
		Group group = new Group(body, SWT.SHADOW_ETCHED_IN);
		group.setText(" "+Messages.TransitionActionValidationGrp+" ");
		GridLayout gridLayout1 = new GridLayout(2, false);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 4;
		group.setLayout(gridLayout1);
		group.setLayoutData(gridData);	
		
		new Label (group, SWT.NONE).setText(VALIDATION);						
		validBtn = new Button(group, SWT.CHECK);
	    	   
		Group def = new Group(body, SWT.SHADOW_ETCHED_IN);
		def.setText(" "+Messages.TransitionActionDefGroup+" ");
		GridLayout gridLayout = new GridLayout();
		int ncol = 4;
		gridLayout.numColumns = ncol;
		gridLayout.marginHeight = 10;
		gridLayout.marginWidth = 15;
		def.setLayout(gridLayout);
		
		
		new Label (def, SWT.NONE).setText(Messages.TransitionActionNameLabel);						
		actionName = new Text(def, SWT.BORDER);
		if (transitionAction.getName() != null)
			actionName.setText(transitionAction.getName());
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol - 2;
		actionName.setLayoutData(gridData);
		
		Button selectButton = new Button(def, 0x800000);
		selectButton.setToolTipText(Messages.SubPageflowGeneralPropertySectionBrowseTooltip);
		GridData gridData_1 = new GridData();
		gridData_1.heightHint = 20;
		selectButton.setLayoutData(gridData_1);
		selectButton.setText(Messages.SubPageflowGeneralPropertySectionBrowseBtnText);
		
		new Label (def, SWT.NONE).setText(Messages.TransitionActionURILabel);						
		uri = new Text(def, SWT.BORDER);
		if (transitionAction.getUri()!= null)
			uri.setText(transitionAction.getUri());		
	
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol - 1;
		uri.setLayoutData(gridData); 
		setErrorMessage(null);
		setMessage(Messages.TransitionActionDefDialogMsg);
		selectButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {	
				boolean filterByValidation = false;
				if (validBtn.getSelection()){
					filterByValidation = true;
				}
				final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				ProcessSelectionDialog dialog = ProcessSelectionDialog.createDialog(shell, filterByValidation);
				dialog.setTitle(Messages.TransitionActionBrowseDialogTitle); 
				dialog.setMessage(Messages.TransitionActionBrowseDialogMessage); 
				final int resultCode = dialog.open();
				if (resultCode == IDialogConstants.OK_ID) {
					IType result = (IType)dialog.getFirstResult();
					String className = "class:"+result.getFullyQualifiedName();
					if (uri.getText() != null){
						String erlVal = uri.getText();
						if (!erlVal.equals(className)){
							domain.getCommandStack().execute(RemoveCommand.create(domain, transitionAction.getProperty()));
							tableViewer.refresh();
						}
					}
					actionName.setText(result.getElementName());
					uri.setText("class:"+result.getFullyQualifiedName());
					if(validate())
						setErrorMessage(null);				
				}				
			}
		});
		//added for issue OCS-11185
		actionName.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if(validate())
					setErrorMessage(null);
			}

			public void keyReleased(KeyEvent e) {
				if(validate())
					setErrorMessage(null);
			}
		});
		uri.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if(validate())
					setErrorMessage(null);
			}

			public void keyReleased(KeyEvent e) {
				if(validate())
					setErrorMessage(null);
			}
		});
		Label descl = new Label (def, SWT.NONE);		
		descl.setText(Messages.TransitionActionDescLabel);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		descl.setLayoutData(gridData);
		
		desc = new Text(def,  SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		if (transitionAction.getDesc()!= null)
			desc.setText(transitionAction.getDesc());
		gridData = new GridData();
		gridData.widthHint = 300;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		gridData.heightHint = 60;
		gridData.horizontalSpan = ncol - 1;
		desc.setLayoutData(gridData);
		
		
		createPropertiesTable(body, ncol);
		
		
		group = new Group(body, SWT.SHADOW_ETCHED_IN);
		group.setText(" "+Messages.TransitionActionErrorGrp+" ");
		gridLayout = new GridLayout(1, false);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);	
		
		
		radio = new Composite(group, SWT.NULL);
		radio.setLayout(new RowLayout());
		
	    final Button failBtn = new Button(radio, SWT.RADIO);
	    failBtn.setText(PREV_STATE);		
	    if (transitionAction.getProblemManagement().equals(ProblemManagement.BACK_LITERAL))
	    	failBtn.setSelection(true);
	    
	    final Button noneBtn = new Button(radio, SWT.RADIO);
	    noneBtn.setText(CONTINUE);
	    if (transitionAction.getProblemManagement().equals(ProblemManagement.CONTINUE_LITERAL)) {
	    	noneBtn.setSelection(true);
	    } else {
	    	failBtn.setSelection(true);	    	
	    }
	    
/*
	    validBtn = new Button(group, SWT.CHECK);
	    validBtn.setText(VALIDATION);*/
	    if (transitionAction.getProblemManagement().equals(ProblemManagement.VALIDATION_LITERAL)) {
	    	validBtn.setSelection(true);
			noneBtn.setSelection(false);
			failBtn.setSelection(true);
			//DS-1562
			noneBtn.setEnabled(false);
			failBtn.setEnabled(false);
			validationProcess = true;	    	
	    }
	    
	    validBtn.addSelectionListener(new SelectionAdapter() {
			// modified for issue OCS-11185
			public void widgetSelected(SelectionEvent e) {	
				// commented OCS-23013
				//actionName.setText("");
				noneBtn.setEnabled(true);
				failBtn.setEnabled(true);
				if (((Button)e.widget).getSelection()){
					failBtn.setSelection(true);
					noneBtn.setSelection(false);	
					failBtn.setEnabled(false);
					noneBtn.setEnabled(false);
					validationProcess = true;
				} else {
					failBtn.setSelection(false);
					noneBtn.setSelection(true);
					failBtn.setEnabled(true);
					noneBtn.setEnabled(true);
					validationProcess = false;
				}					
				validate();
			}

		});
		
		Label empty = new Label (body, SWT.NONE);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		empty.setLayoutData(gridData);
		if (readOnly) {
			composite.setEnabled(false);
		}
		return composite;
	}	
	/**
	 * @param composite
	 * @param ncol
	 */
	private void createPropertiesTable(Composite composite, int ncol) {
		// create property group
		Group group = new Group(composite, SWT.SHADOW_ETCHED_IN);
		group.setText(" Properties ");
		GridLayout gridLayout = new GridLayout(1, false);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = ncol;
		group.setLayout(gridLayout);
		group.setLayoutData(gridData);		

		Composite propertyComposite = OFSUIFactory.INSTANCE.createComposite(group,2,1808);
		Composite tableComposite = OFSUIFactory.INSTANCE.createComposite(propertyComposite,1,1808);
		Composite buttonComposite = OFSUIFactory.INSTANCE.createComposite(propertyComposite,1,1808);
		
		addButton = new Button(buttonComposite, 0x800000);
		addButton.setToolTipText(Messages.TransitionActionDefPropertyAddToolTip);
		addButton.setLayoutData(new GridData());
		addButton.setText("  "+Messages.TransitionActionDefPropertyAddButtonLabel+"  ");
		addButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {	
				ActionPropertyDialog dialog = new ActionPropertyDialog(getShell(), new ActionProperty());
				dialog.open();			
				if (dialog.getReturnCode() == Window.CANCEL) {
					return;
				}
				properties.add(dialog.getActionProperty());
				tableViewer.refresh();
			}

		});
		
		deleteButton = new Button(buttonComposite, 0x800000);
		deleteButton.setToolTipText(Messages.TransitionActionDefPropertyDelToolTip);
		deleteButton.setLayoutData(new GridData());
		deleteButton.setText(Messages.TransitionActionDefPropertyDelButtonLabel);
		deleteButton.setEnabled(false);
		deleteButton.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection) tableViewer.getSelection();
				properties.remove(sel.getFirstElement());
				tableViewer.refresh();
			}

		});
		
		editButton = new Button(buttonComposite, 0x800000);
		editButton.setToolTipText(Messages.TransitionActionDefPropertyEditToolTip);
		editButton.setLayoutData(new GridData());
		editButton.setText("  "+Messages.TransitionActionDefPropertyEditButtonLabel+"  ");
		editButton.setEnabled(false);
		editButton.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {		

				IStructuredSelection sel = (IStructuredSelection) tableViewer.getSelection();
				ActionPropertyDialog dialog = new ActionPropertyDialog(getShell(), (ActionProperty)sel.getFirstElement(), true);
				dialog.open();			
				if (dialog.getReturnCode() == Window.CANCEL) {
					return;
				}
				properties.set(properties.indexOf(sel.getFirstElement()), dialog.getActionProperty());
				tableViewer.refresh();
			}

		});
		
		// create table control
		table = new Table(tableComposite, 0x10b02);
		GridData gd = new GridData(1808);
		gd.heightHint = 100;
		gd.widthHint = 300;
		table.setLayoutData(gd);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		tableFont = new Font(Display.getCurrent(), "Arial", 8, SWT.NORMAL);
		table.setFont(tableFont);
		
		TableColumn column;
		for (int i = 0; i < columnLabels.length; i++) {
			column = new TableColumn(table, 16384);
			column.setText(columnLabels[i]);
			column.setResizable(true);
		}
		


		table.addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				int width = table.getClientArea().width
						- table.getBorderWidth() * 2
						- table.getVerticalBar().getSize().x;
				for (int ii = 0; ii < columnLabels.length; ii++) {
					table.getColumn(ii).setWidth(width / columnLabels.length);
				}
			}

		});

		table.addListener(SWT.MeasureItem, new Listener() {
			public void handleEvent(Event event) {
				int clientWidth = table.getClientArea().width;
				event.height = (new Double(event.gc.getFontMetrics()
						.getHeight() * 1.4)).intValue();
				event.width = clientWidth * 2;
			}
		});

		table.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				if ((event.detail & SWT.SELECTED) == 0)
					return; /* item not selected */
				int clientWidth = table.getClientArea().width;
				GC gc = event.gc;
				Color oldForeground = gc.getForeground();
				Color oldBackground = gc.getBackground();
				gc.setForeground(ColorConstants.red);
				gc.setBackground(ColorConstants.yellow);
				gc.fillGradientRectangle(0, event.y, clientWidth, event.height,
						false);
				gc.setForeground(oldForeground);
				gc.setBackground(oldBackground);
				event.detail &= ~SWT.SELECTED;
			}
		});
		// create table viewer
		createTableViewer(table);
	}

	/**
	 * @param table
	 */
	private void createTableViewer(Table table) {
		tableViewer = new TableViewer(table);
		tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				// enable the delete button
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection != null) {
					deleteButton.setEnabled(true);
					editButton.setEnabled(true);
				}
			}

		});
		
		tableViewer.setColumnProperties(columnLabels);
		
		
		
		// contentProvider
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((List) inputElement).toArray();
			}

			public void dispose() {
			}

			public void inputChanged(Viewer viewer1, Object obj, Object obj1) {
			}

		});

		// labelprovider
		tableViewer.setLabelProvider(new ITableLabelProvider() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
			 *      int)
			 */
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
			 *      int)
			 */
			public String getColumnText(Object element, int columnIndex) {
				ActionProperty obj = (ActionProperty) element;
				if (columnIndex == 0){
					return obj.getName();
				} else {
					return obj.getValue();
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
			 */
			public void addListener(
					ILabelProviderListener ilabelproviderlistener) {
				tableViewer.refresh();
				setMessage(null);
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
			 */
			public void dispose() {
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object,
			 *      java.lang.String)
			 */
			public boolean isLabelProperty(Object element, String property) {
				return true;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
			 */
			public void removeListener(
					ILabelProviderListener ilabelproviderlistener) {				
			}

		});

		tableViewer.setInput(properties);

	}

	
	
	/**
	 * @see org.eclipse.jface.dialogs.TrayDialog#close()
	 */
	@Override
	public boolean close() {
		if (tableFont != null) {
			tableFont.dispose();
		}
		return super.close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	protected void okPressed() {
		if (validate()) {		
			if(!update){
				transitionAction.setName(actionName.getText().trim());
				transitionAction.setDesc(desc.getText().trim());
				transitionAction.setUri(uri.getText().trim());
				if (validBtn.getSelection()) {
					transitionAction.setProblemManagement(ProblemManagement.VALIDATION_LITERAL);
				} else {
					Control[] child = radio.getChildren();
					for(int i=0;i < child.length;i++){
						Control control = child[i];
						if (control instanceof Button){
							Button button = (Button) control;						
				            if ((button.getStyle () & SWT.RADIO) != 0 && button.getSelection()) {
				            	if (button.getText().equals(CONTINUE)){
				            		transitionAction.setProblemManagement(ProblemManagement.CONTINUE_LITERAL);
				            	} else {
				            		transitionAction.setProblemManagement(ProblemManagement.BACK_LITERAL);
				            	}			            	
				            }									          
						}
					}					
				}
			} else {
				if (!actionName.getText().equals(transitionAction.getName())){
					Command command = SetCommand.create(domain, transitionAction, PageflowPackage.eINSTANCE.getAction_Name(), actionName.getText().trim());
					if (command != null && command.canExecute()){
						domain.getCommandStack().execute(command);
					}
				}
				if (!desc.getText().equals(transitionAction.getDesc())){
					Command command = SetCommand.create(domain, transitionAction, PageflowPackage.eINSTANCE.getAction_Desc(), desc.getText().trim());
					if (command != null && command.canExecute()){
						domain.getCommandStack().execute(command);
					}				
				}
				if (!uri.getText().equals(transitionAction.getUri())){
					Command command = SetCommand.create(domain, transitionAction, PageflowPackage.eINSTANCE.getAction_Uri(), uri.getText().trim());
					if (command != null && command.canExecute()){
						domain.getCommandStack().execute(command);
					}				
				}
				ProblemManagement mgt = null;
				if (validBtn.getSelection()) {
					mgt = ProblemManagement.VALIDATION_LITERAL;	
				} else {
					Control[] child = radio.getChildren();
					for(int i=0;i < child.length;i++){
						Control control = child[i];
						if (control instanceof Button){
							Button button = (Button) control;												
							if ((button.getStyle () & SWT.RADIO) != 0 && button.getSelection()) {
				            	if (button.getText().equals(CONTINUE)){
				            		mgt = ProblemManagement.CONTINUE_LITERAL;
				            	} else {
				            		mgt = ProblemManagement.BACK_LITERAL;
				            	}	
				            }	
						}	
					}
				}				
				Command command = SetCommand.create(domain, transitionAction, PageflowPackage.eINSTANCE.getTransitionAction_ProblemManagement(), mgt);
				if (command != null && command.canExecute()){
					domain.getCommandStack().execute(command);
				}	
				
				Command cmd = SetCommand.create(domain, transitionAction, PageflowPackage.eINSTANCE.getAction_Property(), properties);
				domain.getCommandStack().execute(cmd);
				
				
			}
			if (properties.size() > 0){
				domain.getCommandStack().execute(
    					new RecordingCommand((TransactionalEditingDomain)domain) {
    						protected void doExecute() {
    							transitionAction.getProperty().clear();
    							for(int i=0;i < properties.size();i++){
    								ActionProperty act = (ActionProperty)properties.get(i);
        							Property property = PageflowFactory.eINSTANCE.createProperty();
        							property.setName(act.getName());
        							property.setValue(act.getValue());
        							transitionAction.getProperty().add(property);
    							}
    						}
    				});	
			}
			super.okPressed();			
		}
	}
	@Override
	protected void cancelPressed() {
		super.cancelPressed();
		
	}
	
	
	/**
	 * 
	 */
	private boolean validate() {
		// modified for issue OCS-11185
		if (actionName.getText() != null) {
			actionName.getText().trim();
			if (!((actionName.getText().trim().length()) > 0)) {
				this.setErrorMessage(Messages.TransitionActionDefNameAlert);
				return false;
			}
			if (uri.getText() != null) {
				uri.getText().trim();
				if (!(uri.getText().trim().length() > 0) && !isValidationProcess()) {
					this.setErrorMessage(Messages.TransitionActionDefURIAlert);
					return false;
				} else {
					this.setErrorMessage(null);
				}
			}
		}
		return true;
	}

	public boolean isValidationProcess() {
		return validationProcess;
	}
	
	

}
