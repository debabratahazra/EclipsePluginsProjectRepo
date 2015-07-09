package com.odcgroup.t24.version.editor.ui.controls;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.odcgroup.t24.swt.util.SWTResourceManager;
import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.RoutineDialogCellEditor;
import com.odcgroup.t24.version.editor.ui.providers.DefaultTabDialogCellEditingSupport;
import com.odcgroup.t24.version.editor.ui.providers.DefaultTabTextCellEditingSupport;
import com.odcgroup.t24.version.editor.ui.providers.DefaultTableViewerLabelProvider;
import com.odcgroup.t24.version.versionDSL.Default;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.ValueOrAtRoutine;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

public class DefaultingTabControl extends AbstractFieldTabControl {
   
    private Group grpHeader;
    private TableViewer tableViewer;
    private TableViewerColumn existingValueTableViewerColumn;
    private TableViewerColumn newValueTableViewerColumn;
    private TableViewerColumn routinesTableViewerColumn;
    private TableViewerColumn svTabelViewerColumn;
    private TableViewerColumn mvTableViewerColumn;
    private IObservableList selectedFieldObservableList;
    private ArrayList<IEMFValueProperty> defaultProperties;
    private Button btnRemove;
	private Button btnAdd;
	   

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public DefaultingTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context ,TreeViewer viewer) {
    	super(parent, editor, context, viewer); 
    }

    /**
     * @return
     */
    public Group getHeaderGroup() {
    	return grpHeader;
    }

    @Override
    protected void createTabControls(Composite body) {
    	
		Composite mainComposite = new Composite(body, SWT.NONE);
		GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, true,1, 1);
		//gdata.widthHint = 800;
		mainComposite.setLayoutData(gdata);
		toolkit.adapt(mainComposite);
		toolkit.paintBordersFor(mainComposite);
		mainComposite.setLayout(new GridLayout(2, false));	
		
		Composite tabComp = new Composite(mainComposite, SWT.NONE);
		tabComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1, 1));
		toolkit.adapt(tabComp);
		toolkit.paintBordersFor(tabComp);
		tabComp.setLayout(new GridLayout(1, true));
			
		//field defaults table viewer
		int style = SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.LINE_SOLID;
		tableViewer = new TableViewer(tabComp, style);
		final Table table = tableViewer.getTable();
		GridData gd = new GridData(GridData.FILL_BOTH /*| GridData.VERTICAL_ALIGN_BEGINNING*/);
		gd.verticalIndent = 0;
		gd.horizontalIndent =0;
		table.setLayoutData(gd);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		//text cell editor for the mv value
		TextCellEditor mvtextCellEditor = new TextCellEditor((Composite)tableViewer.getControl(),SWT.NONE);
		DefaultTabTextCellEditingSupport mvtexteditingSupport = new DefaultTabTextCellEditingSupport(tableViewer, getBindingContext(), mvtextCellEditor,getEditor());		
		//mv column viewer
		mvTableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnMv = mvTableViewerColumn.getColumn();
		tblclmnMv.setText("MV");
		tblclmnMv.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_MV"));
		mvTableViewerColumn.setEditingSupport(mvtexteditingSupport);
		
		//sv value text cell editor
		TextCellEditor svtextCellEditor = new TextCellEditor((Composite)tableViewer.getControl(),SWT.NONE);
		DefaultTabTextCellEditingSupport svtexteditingSupport = new DefaultTabTextCellEditingSupport(tableViewer, getBindingContext(), svtextCellEditor,getEditor());
		//sv column viewer
		svTabelViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnSv = svTabelViewerColumn.getColumn();
		tblclmnSv.setText("SV");
		tblclmnSv.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_SV"));
		svTabelViewerColumn.setEditingSupport(svtexteditingSupport);
		
	    //cell editor for the existing value.
		TextCellEditor existValuetextCellEditor = new TextCellEditor((Composite)tableViewer.getControl(),SWT.NONE);
		DefaultTabTextCellEditingSupport existValuetexteditingSupport = new DefaultTabTextCellEditingSupport(tableViewer, getBindingContext(), existValuetextCellEditor,getEditor());
		//column viewer for the existing value
		existingValueTableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnExistingValue = existingValueTableViewerColumn.getColumn();
		tblclmnExistingValue.setText("Existing Value");
		tblclmnExistingValue.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_AutoOldContent"));
		existingValueTableViewerColumn.setEditingSupport(existValuetexteditingSupport);
		
	    //new value cell editor.
		TextCellEditor newValuetextCellEditor = new TextCellEditor((Composite)tableViewer.getControl(),SWT.NONE);
		DefaultTabTextCellEditingSupport newValuetexteditingSupport = new DefaultTabTextCellEditingSupport(tableViewer, getBindingContext(), newValuetextCellEditor,getEditor());
		//new vallue column viewer.
		newValueTableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewValue = newValueTableViewerColumn.getColumn();
		tblclmnNewValue.setText("New Value");
		tblclmnNewValue.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_AutoNewContent"));
		newValueTableViewerColumn.setEditingSupport(newValuetexteditingSupport);
		
	    //routines column viewer. 
		routinesTableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnRoutines = routinesTableViewerColumn.getColumn();
		tblclmnRoutines.setText("Routines");
		tblclmnRoutines.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_AutoRoutines"));
		
		//routine Dialog cell editor for the Routines.
		RoutineDialogCellEditor routineDialogCellEditor = new RoutineDialogCellEditor((Composite)tableViewer.getControl());
		DefaultTabDialogCellEditingSupport comboCellEditing =new DefaultTabDialogCellEditingSupport(tableViewer,getBindingContext(),routineDialogCellEditor,getEditor());
		routinesTableViewerColumn.setEditingSupport(comboCellEditing);
		
		TableColumnLayout layout = new TableColumnLayout();
		tabComp.setLayout(layout);
		layout.setColumnData( mvTableViewerColumn.getColumn(), new ColumnWeightData( 10 ) );
		layout.setColumnData( svTabelViewerColumn.getColumn(), new ColumnWeightData( 10 ) );
		layout.setColumnData( existingValueTableViewerColumn.getColumn(), new ColumnWeightData( 20 ) );
		layout.setColumnData( newValueTableViewerColumn.getColumn(), new ColumnWeightData( 20 ) );
		layout.setColumnData( routinesTableViewerColumn.getColumn(), new ColumnWeightData( 20 ) );
	    
		//EMf value property for the Default properties.  
		defaultProperties= new ArrayList<IEMFValueProperty>();
		EAttribute[] defaultAttributes = {Literals.DEFAULT__MV ,Literals.DEFAULT__SV ,Literals.DEFAULT__DEFAULT_IF_OLD_VALUE_EQUALS };
		for(EAttribute attribute :Arrays.asList(defaultAttributes)){
		    IEMFValueProperty property = EMFProperties.value(attribute);
		    if(property !=null){
			defaultProperties.add(property);
		    }
		}
		IEMFValueProperty newvalueRoutineproperty = EMFProperties.value(Literals.VALUE_OR_AT_ROUTINE__VALUE);
		IEMFValueProperty outineNameproperty = EMFProperties.value(Literals.ROUTINE__NAME);
		if(newvalueRoutineproperty !=null){
		    defaultProperties.add(newvalueRoutineproperty);
		    defaultProperties.add(outineNameproperty);
		}
	        //content provider and label provider for the table.
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableViewer.setContentProvider(contentProvider);
		IObservableMap[] map=Properties.observeEach(contentProvider.getKnownElements(),defaultProperties.toArray(new IValueProperty[0]));
		tableViewer.setLabelProvider(new DefaultTableViewerLabelProvider(map));
		
		Composite composite = new Composite(mainComposite, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		composite.setLayout(new GridLayout(1, false));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		//add default button
		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnAdd.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		    	addNewDefault();
		    }
		});
		btnAdd.setText("Add");
		
		//removedefault button
		btnRemove = new Button(composite, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemove.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
			removeDefault();
		    }
		});
		btnRemove.setText("Remove");		
		
    }
   
    @Override
    protected void bindData() {
    	Field field = getTabInput();
		if (field != null) {
			bindDefaultViewer();	
		}
    }
   
    /**
     * bind the Default Table viewer with the Field Table Viewer.
     */
    private void bindDefaultViewer() {
		if (getTabInput() != null) {
		    selectedFieldObservableList = EMFEditObservables.observeList(getEditor().getEditingDomain(), getTabInput(), Literals.FIELD__DEFAULTS);
		    tableViewer.setInput(selectedFieldObservableList);
		    // Listener for the remove button enable state.
		    IObservableValue tabelDefaultObserver = ViewersObservables.observeSinglePostSelection(tableViewer);
		    if (tableViewer.getTable().getItemCount() == 0) {
		    	setRemoveButtonState(false);
		    }
		    // change listener for the default selection
		    tabelDefaultObserver.addChangeListener(new IChangeListener() {
				@Override
				public void handleChange(ChangeEvent event) {
				    updateRemoveButton(getTabInput());
				    updateAddButton(getTabInput());
				}
		    });
	
		}
    }

    /**set the add button enable state.
     * @param enable
     */
    private void setAddButtonState(boolean enable) {
    	btnAdd.setEnabled(enable);	
    }
    
    /**set the Remove button enable state.
     * @param enable
     */
    private void setRemoveButtonState(boolean enable) {
       btnRemove.setEnabled(enable);	
    }
    
    /**update the remove button enable state based on the selection.
     * @param selection
     */
    private void updateRemoveButton(Field field) {
		if (field != null) {
			if (!field.getDefaults().isEmpty()) {
				setRemoveButtonState(true);
			} else {
				setRemoveButtonState(false);
			}
		}
    }
    
    /**update the add button enable state based on the selection.
     * @param selection
     */
    private void updateAddButton(Field field) {
		if (field != null) {
			if(field.getMV() == null & field.getSV() ==null && !field.getDefaults().isEmpty()){
				setAddButtonState(false);
			}
			else {
				setAddButtonState(true);
			}
		  
		}
    }
    
    /**
     * add a new Default to the Selected Field.
     */
    protected void addNewDefault() {
    	Field firstElement = getTabInput();
    	VersionDSLFactoryImpl versionFactoryImpl = new VersionDSLFactoryImpl();
		Default newDefault = versionFactoryImpl.createDefault();
		Integer mv =  firstElement.getMV();
		Integer sv = firstElement.getSV();
		if (mv == null & sv == null) {
			newDefault.setMv(Integer.valueOf(0));
			newDefault.setSv(Integer.valueOf(0));
			setAddButtonState(false);
		} else if (mv !=null && mv.intValue()==1 && sv == null) {
			newDefault.setMv(Integer.valueOf(firstElement.getDefaults().size() + 1));
			newDefault.setSv(Integer.valueOf(0));
		} else if (mv!=null && mv.intValue() == 1 &&  sv.intValue() == 1) {
			newDefault.setMv(mv);
			newDefault.setSv(sv);
		}
		
		
		newDefault.setDefaultIfOldValueEquals("");
		ValueOrAtRoutine valueOrAtRoutine = versionFactoryImpl.createValueOrAtRoutine();
		valueOrAtRoutine.setValue("");
		newDefault.setDefaultNewValueOrAtRoutine(valueOrAtRoutine);
		if (getViewer() != null && getViewer().getTree().getItemCount() > 0
				&& selectedFieldObservableList != null) {
			selectedFieldObservableList.add(newDefault);
			tableViewer.setSelection(new StructuredSelection(newDefault));
		}
    }
    
    @Override
    public void setTabInput(Field input) {
    	super.setTabInput(input);
		if (!getContent().isDisposed()) {
			bindData();
		}
    }
    
    /**
     * remove the selected default.
     */
    private void removeDefault() {
		if (tableViewer != null
				&& tableViewer.getSelection() instanceof IStructuredSelection) {
			Object selectedElement = ((IStructuredSelection) tableViewer
					.getSelection()).getFirstElement();
			if (selectedElement != null) {
				int index = tableViewer.getTable().getSelectionIndex();
				tableViewer.getTable().remove(index);
				selectedFieldObservableList.remove(selectedElement);
				tableViewer.refresh(selectedElement);
				if (tableViewer.getTable().getItemCount() == 0) {
					setRemoveButtonState(false);
				}
			}
		}
		if (tableViewer.getTable().getItemCount() == 0) {
			setAddButtonState(true);
		}
    }
}
