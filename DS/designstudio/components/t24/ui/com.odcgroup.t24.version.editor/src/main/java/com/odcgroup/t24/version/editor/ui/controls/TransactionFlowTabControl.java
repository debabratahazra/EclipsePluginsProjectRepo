package com.odcgroup.t24.version.editor.ui.controls;

import java.util.ArrayList;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.CellEditorProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.swt.util.SWTResourceManager;
import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.AddItemsToListDialog;
import com.odcgroup.t24.version.versionDSL.DealSlip;
import com.odcgroup.t24.version.versionDSL.DealSlipFormatFunction;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.YesNo;
import com.odcgroup.t24.version.versionDSL.impl.VersionDSLFactoryImpl;

public class TransactionFlowTabControl extends AbstractVersionTabControl {

	private Combo exceptionCombo;

	private Combo interfaceCombo;
	private Control styleTxt;
	private TableViewer tableViewer;
	private Table dsTable;
	private ComboViewer bdcCombo;
	private ComboViewer triggerCombo;

	private Button chgBtn;

	private Button caBtn;

	private Button apprBtn;

	private TableViewerColumn tableViewerColumn;
	private TableViewerColumn tableViewerColumn_1;

	private List list;
	private ISWTObservableList seqWidget;
	private IObservableList versionDealSlipFormatsObserveList;
	private Button remBtn;
	

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TransactionFlowTabControl(Composite parent,
			VersionDesignerEditor editor, DataBindingContext context) {
    	super(parent, editor, context);
	}

	/**
	 * @param body
	 */
	private void createDealSlipGroup(Composite body) {
		
		Group grpDealSlip = new Group(body, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_BOTH);
		grpDealSlip.setLayoutData(gd);
		grpDealSlip.setText("Deal Slip");
		grpDealSlip.setLayout(new GridLayout(1, false));
		toolkit.adapt(grpDealSlip);
		toolkit.paintBordersFor(grpDealSlip);

		Composite comp = new Composite(grpDealSlip, SWT.NONE);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(comp);
		toolkit.paintBordersFor(comp);
		comp.setLayout(new GridLayout(3, false));
		
		Label label = new Label(comp, SWT.NONE);
		label.setText("Formats:");
		label.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		toolkit.adapt(label, true, true);

		Composite tcomp = new Composite(comp, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true,	true, 1, 1);
		tcomp.setLayoutData(gd);
		tableViewer = new TableViewer(tcomp, SWT.BORDER | SWT.FULL_SELECTION);
		dsTable = tableViewer.getTable();
		dsTable.setLinesVisible(true);
		dsTable.setHeaderVisible(true);
		toolkit.paintBordersFor(dsTable);

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFormat = tableViewerColumn.getColumn();
		tblclmnFormat.setText("Format");
		tableViewerColumn.getColumn().setToolTipText(VersionEditorActivator.getDefault().getString("_HekpText_DealSlipFormat"));

		tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnFunction = tableViewerColumn_1.getColumn();
		tblclmnFunction.setText("Function");
		tableViewerColumn_1.getColumn().setToolTipText(VersionEditorActivator.getDefault().getString("_HekpText_DealSlipFunction"));
		
		TableColumnLayout layout = new TableColumnLayout();
		tcomp.setLayout(layout);
		layout.setColumnData( tableViewerColumn.getColumn(), new ColumnWeightData( 30 ) );
		layout.setColumnData( tableViewerColumn_1.getColumn(), new ColumnWeightData( 60 ) );
		
		Composite composite = new Composite(comp, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setLayout(new GridLayout(1, false));		
		
		Button addBtn = new Button(composite, SWT.NONE);
		addBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		final Button remBtn = new Button(composite, SWT.NONE);
		remBtn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		remBtn.setEnabled(false);
		
		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(dsTable.getItems().length == 0){
					remBtn.setEnabled(false);
				}else{
					remBtn.setEnabled(true);
				}
				VersionDSLFactoryImpl versionFactoryImpl = new VersionDSLFactoryImpl();
				DealSlip newDealSlip = versionFactoryImpl.createDealSlip();
				newDealSlip.setFunction(DealSlipFormatFunction.I);
				newDealSlip.setFormat("Add New Format");
				versionDealSlipFormatsObserveList.add(newDealSlip);
				tableViewer.add(newDealSlip);
				tableViewer.refresh();
			}
		});
		addBtn.setText("Add");
		
		
		remBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndices = dsTable.getSelectionIndex();
				if(selectionIndices >= 0 && dsTable.getItems().length != 0){
					dsTable.remove(selectionIndices);
					versionDealSlipFormatsObserveList.remove(selectionIndices);
				}else{
					remBtn.setEnabled(false);
				}
			}
		});
		remBtn.setText("Remove");
		
		dsTable.addSelectionListener(new SelectionListener() {			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(dsTable.getItems().length != 0){
					remBtn.setEnabled(true);
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		Label label_1 = new Label(comp, SWT.NONE);
		label_1.setText("Trigger:");
		toolkit.adapt(label_1, true, true);
		label_1.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Trigger"));

		triggerCombo = new ComboViewer(comp, SWT.READ_ONLY);
		Combo tgcom = triggerCombo.getCombo();
		GridData gdTgr = new GridData(SWT.LEFT, SWT.CENTER, true, false,	1, 1);
		tgcom.setLayoutData(gdTgr);
		new Label(comp, SWT.NONE);
		tgcom.setItems(new String[] { "None", "OL", "RQ" });
		toolkit.adapt(tgcom);
		toolkit.paintBordersFor(tgcom);

		Label styleLbl = new Label(comp, SWT.NONE);
		styleLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_StyleSheet"));
		styleLbl.setText("Style Sheet:");
		toolkit.adapt(styleLbl, true, true);

		styleTxt = new Text(comp, SWT.BORDER);
		gd = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		styleTxt.setLayoutData(gd);
		toolkit.adapt(styleTxt, true, true);
		new Label(comp, SWT.NONE);

	}

	@Override
	protected void createTabControls(Composite body) {

		Composite comp = new Composite(body, SWT.NONE);
		comp.setLayout(new GridLayout(2, true));
		toolkit.adapt(comp);
		toolkit.paintBordersFor(comp);		
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite parent = new Composite(comp, SWT.NONE);
		parent.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		parent.setLayoutData(gd);
		toolkit.adapt(parent);
		toolkit.paintBordersFor(parent);
		
		// combo comp
		Composite comComp = new Composite(parent, SWT.NONE);
		comComp.setLayout(new GridLayout(2, false));
		//comComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,	1, 1));
		toolkit.adapt(comComp);
		toolkit.paintBordersFor(comComp);

		Label excepLbl = new Label(comComp, SWT.NONE);
		toolkit.adapt(excepLbl, true, true);
		excepLbl.setText("Exception Processing:");
		excepLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_ExceptionProcessing"));

		exceptionCombo = new Combo(comComp, SWT.BORDER | SWT.READ_ONLY);
		GridData exceptionGridData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		exceptionGridData.widthHint = 21;
		exceptionCombo.setLayoutData(exceptionGridData);
		exceptionCombo.setItems(new String[] { "None","0. (Reject messages where an NAU record exist)", "1. (Overwrite NAU record with OFS data)", "2. (Accept Reversal as Deletion)", "3. (Apply both option 1 and option 2)" });
		toolkit.adapt(exceptionCombo);
		
		Label intLbl = new Label(comComp, SWT.NONE);
		intLbl.setText("Interface Exception:");
		toolkit.adapt(intLbl, true, true);
		intLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_InterfaceException"));

		interfaceCombo = new Combo(comComp, SWT.READ_ONLY);
		interfaceCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		interfaceCombo.setItems(new String[] { "Null (Reject errors / Approve overrides)",
				"1 (Errors on HLD / Approve Overrides)",
				"2 (Errors Rejected / Overrides in HLD)", "3 (Errors in HLD / Overrides HLD)", "4 (Hold Only)" });
		toolkit.adapt(interfaceCombo);
		toolkit.paintBordersFor(interfaceCombo);

		Label ctrlLbl = new Label(comComp, SWT.READ_ONLY);
		ctrlLbl.setText("Business Day Control:");
		toolkit.adapt(ctrlLbl, true, true);
		ctrlLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_BusinessDay"));

		bdcCombo = new ComboViewer(comComp, SWT.READ_ONLY);
		Combo combo_5 = bdcCombo.getCombo();
		combo_5.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		combo_5.setItems(new String[] { "None", "Normal", "Restricted",	"Closed" });
		toolkit.adapt(combo_5);
		toolkit.paintBordersFor(combo_5);

		// id sequence comp
		Composite isSeqComposite = new Composite(parent, SWT.NONE);
		isSeqComposite.setLayout(new GridLayout(1, false));
		isSeqComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label idLbl = new Label(isSeqComposite, SWT.NONE);
		idLbl.setText("ID Sequence:");
		toolkit.adapt(idLbl, true, true);
		idLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_IDSeq"));

		Composite isSeqListComposite = new Composite(isSeqComposite, SWT.NONE);
		GridData gdIdSeq = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		isSeqListComposite.setLayoutData(gdIdSeq);
		isSeqListComposite.setLayout(new GridLayout(2, false));
		toolkit.adapt(isSeqListComposite);

		list = new List(isSeqListComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite btnGroup = new Composite(isSeqListComposite, SWT.NONE);
		GridData btnGroupData = new GridData(GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING);
		btnGroup.setLayoutData(btnGroupData);
		btnGroup.setLayout(new GridLayout(1, true));
		Button addBtn = new Button(btnGroup, SWT.NONE);
		addBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		remBtn = new Button(btnGroup, SWT.NONE);
		remBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		enableRemoveBtn();

		addBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddItemsToListDialog idsDialog = new AddItemsToListDialog(
						getShell(), seqWidget);
				idsDialog.open();
				if(list.getItems().length != 0){
					remBtn.setEnabled(true);
				}
			}
		});
		toolkit.adapt(addBtn, true, true);
		addBtn.setText("Add");

		remBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionIndex() != -1) {
					seqWidget.remove(list.getSelectionIndex());					
				}
				enableRemoveBtn();
			}
		});
		toolkit.adapt(remBtn, true, true);
		remBtn.setText("Remove");

		// checkbox comp
		Composite chkGroup = new Composite(parent, SWT.NONE);
		chkGroup.setLayout(new GridLayout(2, false));
//		chkGroup.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		toolkit.adapt(chkGroup);
		toolkit.paintBordersFor(chkGroup);

		caBtn = new Button(chkGroup, SWT.CHECK);
		toolkit.adapt(caBtn, true, true);

		Label accLbl = new Label(chkGroup, SWT.NONE);
		accLbl.setText("Other Company Access");
		toolkit.adapt(accLbl, true, true);
		accLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_CompanyAccess"));

		chgBtn = new Button(chkGroup, SWT.CHECK);
		toolkit.adapt(chgBtn, true, true);

		Label chgLbl = new Label(chkGroup, SWT.NONE);
		chgLbl.setText("Auto Company Change");
		toolkit.adapt(chgLbl, true, true);
		chgLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_AutoCompChange"));

		apprBtn = new Button(chkGroup, SWT.CHECK);
		toolkit.adapt(apprBtn, true, true);

		Label appLbl = new Label(chkGroup, SWT.NONE);
		appLbl.setText("Override Approval");
		toolkit.adapt(appLbl, true, true);
		appLbl.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_OverideApproval"));

		createDealSlipGroup(comp);

	}

	/**
	 * 
	 */
	public void enableRemoveBtn() {
		if(list.getItems().length == 0){
			remBtn.setEnabled(false);
		}else{
			remBtn.setEnabled(true);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	protected void bindData() {
		EditingDomain edomain = getEditingDomain();	
		Version version = getEditedVersion();
		IObservableValue epWidget = WidgetProperties.singleSelectionIndex().observe(exceptionCombo);
		IObservableValue epValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__EXCEPTION_PROCESSING);
		UpdateValueStrategy comboValToModelStrategy = new UpdateValueStrategy();
		comboValToModelStrategy.setConverter(new Converter(new Integer(0) ,new Integer(0)) {
			public Object convert(Object fromObject) {
				int value = ((Integer) fromObject).intValue();
				if(value > 0){
				  return new Integer(value-1);
				}
				return null;
			}
		});
		UpdateValueStrategy modelToTargetStrategy = new UpdateValueStrategy();
		modelToTargetStrategy.setConverter(new Converter(new Integer(0) ,new Integer(0)) {
			public Object convert(Object fromObject) {
				if(fromObject ==null){
					return  new Integer(0);
				}
				int value = ((Integer) fromObject).intValue();
				return new Integer(value+1);
			}
		});
		getBindingContext().bindValue(epWidget,epValue,comboValToModelStrategy,modelToTargetStrategy);
		//epWidget.addChangeListener(arg0)
		// interface exception
		IObservableValue intexpWidget = WidgetProperties.singleSelectionIndex().observe(interfaceCombo);
		IObservableValue intexpValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__INTERFACE_EXCEPTION);
		getBindingContext().bindValue(intexpWidget,	intexpValue);
		
		//stylesheet
		UpdateValueStrategy strat = new UpdateValueStrategy(){
			@Override
			protected IStatus doSet(IObservableValue observableValue,
					Object value) {
				if(value != null){
				value = ((String)value).trim();
				}
				return super.doSet(observableValue, value);
			}
		};
		IObservableValue styleWidget = WidgetProperties.text(SWT.Modify).observe(styleTxt);
		IObservableValue styleValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__DEAL_SLIP_STYLE_SHEET);
		getBindingContext().bindValue(styleWidget, styleValue, strat, strat);

		bdcCombo.setContentProvider(new ArrayContentProvider());
		bdcCombo.setLabelProvider(new LabelProvider());
		bdcCombo.setInput(version.getBusinessDayControl().values());
		IObservableValue bdcValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__BUSINESS_DAY_CONTROL);
		IObservableValue bdcWidget = ViewerProperties.singleSelection().observe(bdcCombo);
		getBindingContext().bindValue(bdcWidget, bdcValue, null, null);
		
		//triggger
		triggerCombo.setContentProvider(new ArrayContentProvider());
		triggerCombo.setLabelProvider(new LabelProvider());
		triggerCombo.setInput(version.getDealSlipTrigger().values());
		IObservableValue trgValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__DEAL_SLIP_TRIGGER);
		IObservableValue trgWidget = ViewerProperties.singleSelection().observe(triggerCombo);
		getBindingContext().bindValue(trgWidget, trgValue, null, null);

		// checkbox bindings
		UpdateValueStrategy booleanToScript = new UpdateValueStrategy().setConverter(new Converter(Boolean.class,String.class) {
			@Override
			public Object convert(Object value) {
				if (Boolean.parseBoolean(value.toString())) {
					return (Object) YesNo.YES;
				}
				return (Object) YesNo.NO;
			}
		});
		
		UpdateValueStrategy scriptToBoolean = new UpdateValueStrategy().setConverter(new Converter(String.class,Boolean.class) {

			public Object convert(Object fromObject) {
				if (YesNo.YES.equals(fromObject)) {
					return Boolean.TRUE;
				}
				return Boolean.FALSE;
			}
		});
		
		//company access
		IObservableValue caWidget = WidgetProperties.selection().observe(caBtn); 
		IObservableValue caValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__OTHER_COMPANY_ACCESS);
		getBindingContext().bindValue(caWidget, caValue, booleanToScript, scriptToBoolean);
		
		// id sequence
		seqWidget = WidgetProperties.items().observe(list);
		IObservableList seqValue = EMFEditObservables.observeList(edomain, version, Literals.VERSION__KEY_SEQUENCE);
		getBindingContext().bindList(seqWidget, seqValue, null, null);
		
		if(list.getItems().length != 0){
			remBtn.setEnabled(true);
		}

		// company change
		IObservableValue changeWidget = WidgetProperties.selection().observe(chgBtn);		
		IObservableValue changeValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__AUTO_COMPANY_CHANGE);
		getBindingContext().bindValue(changeWidget, changeValue, booleanToScript, scriptToBoolean);

		// override approval
		IObservableValue overrWidget = SWTObservables.observeSelection(apprBtn);
		IObservableValue overrValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__OVERRIDE_APPROVAL);
		getBindingContext().bindValue(overrWidget, overrValue, booleanToScript, scriptToBoolean);
		
		// deal slip
		ObservableListContentProvider cp = new ObservableListContentProvider();
		EStructuralFeature[] features = new EStructuralFeature[] { Literals.DEAL_SLIP__FORMAT,	Literals.DEAL_SLIP__FUNCTION };
		IObservableMap[] observeMaps1 = EMFEditObservables.observeMaps(edomain, cp.getKnownElements(), features);
		tableViewer.setLabelProvider(new ObservableMapLabelProvider(observeMaps1));
		tableViewer.setContentProvider(cp);
		versionDealSlipFormatsObserveList = EMFEditObservables.observeList(edomain, version, Literals.VERSION__DEAL_SLIP_FORMATS);
		tableViewer.setInput(versionDealSlipFormatsObserveList);
		
		CellEditor cellEditor = new TextCellEditor(tableViewer.getTable());
		IValueProperty cep = CellEditorProperties.control().value(WidgetProperties.text(SWT.Modify));
		IEMFValueProperty dslip = EMFEditProperties.value(edomain, VersionDSLPackage.Literals.DEAL_SLIP__FORMAT);
		EditingSupport es = ObservableValueEditingSupport.create(tableViewer, getBindingContext(), cellEditor, cep, dslip);
		tableViewerColumn.setEditingSupport(es);

		CellEditor[] editors = new CellEditor[2];
		editors[1] = new ComboBoxCellEditor(tableViewer.getTable(), new String[0], SWT.READ_ONLY);
		java.util.List<DealSlipFormatFunction> list = DealSlipFormatFunction.VALUES;
		java.util.List<String> strList = new ArrayList<String>();
		for (DealSlipFormatFunction dealSlipFormatFunction : list) {
			if (DealSlipFormatFunction.NONE == dealSlipFormatFunction) {
				continue;
			}
			strList.add(dealSlipFormatFunction.getLiteral());
		}
		((ComboBoxCellEditor) editors[1]).setItems(strList.toArray(new String[0]));

		tableViewer.setColumnProperties(new String[] { "Format", "Function" });
		tableViewer.setCellModifier(new DealSlipCellModifier(tableViewer, edomain, version));
		tableViewer.setCellEditors(editors);
		
	}
}
