package com.odcgroup.t24.version.editor.ui.controls;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableList;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.AddItemsToListDialog;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.t24.version.versionDSL.YesNo;

/**
 * @author phanikumark
 *
 */
public class WebserviceTabControl extends AbstractVersionTabControl {

	private List list;
	private Text webServiceActivitytext;
	private Text serviceDestext;
	private Button btnCheckButton;
	private ComboViewer functioncomboViewer;
	private Button btnRemove;
	private ISWTObservableList listWidget;

	
	/**
	 * @param parent
	 * @param editor
	 * @param context
	 */
	public WebserviceTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context) {
		super(parent, editor, context);
	}

	@Override
	protected void createTabControls(Composite body) {
		
		Composite comp = new Composite(body, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		comp.setLayoutData(gd);
		comp.setLayout(new GridLayout(2, false));
		toolkit.adapt(comp);
		toolkit.paintBordersFor(comp);

		Group grpGeneral = new Group(comp, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		grpGeneral.setLayoutData(gridData);
		grpGeneral.setText("General");
		toolkit.adapt(grpGeneral);
		toolkit.paintBordersFor(grpGeneral);
		grpGeneral.setLayout(new GridLayout(2, false));

		btnCheckButton = new Button(grpGeneral, SWT.CHECK);
		toolkit.adapt(btnCheckButton, true, true);
		btnCheckButton.setText("Publish");
		btnCheckButton.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Publish"));
		new Label(grpGeneral, SWT.NONE);

		Label lblActivity = new Label(grpGeneral, SWT.NONE);
		lblActivity.setText("Activity:");
		toolkit.adapt(lblActivity, true, true);
		lblActivity.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Activity"));

		webServiceActivitytext = new Text(grpGeneral, SWT.BORDER);
		GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		webServiceActivitytext.setLayoutData(gdata);
		toolkit.adapt(webServiceActivitytext, true, true);

		Label lblFunction_1 = new Label(grpGeneral, SWT.NONE);
		lblFunction_1.setText("Function:");
		toolkit.adapt(lblFunction_1, true, true);
		lblFunction_1.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Function"));

		functioncomboViewer = new ComboViewer(grpGeneral,  SWT.READ_ONLY);
		Combo combo = functioncomboViewer.getCombo();
		combo.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false, 1,	1));
		combo.setText("Input\r\n");
		combo.setItems(new String[] { "None", "Input", "Authorise", "Reverse",	"See", "Copy" });
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);

		Label lblNewLabel_2 = new Label(grpGeneral, SWT.NONE);
		toolkit.adapt(lblNewLabel_2, true, true);
		lblNewLabel_2.setText("Description:");
		lblNewLabel_2.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Description"));

		serviceDestext = new Text(grpGeneral, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL
				| SWT.CANCEL | SWT.MULTI);
		serviceDestext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(serviceDestext, true, true);

		Group grpServices = new Group(comp, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		grpServices.setLayoutData(gd);
		grpServices.setText("Services");
		toolkit.adapt(grpServices);
		toolkit.paintBordersFor(grpServices);
		grpServices.setLayout(new GridLayout(2, false));
		
		list = new List(grpServices, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		list.setLayoutData(gd);
		toolkit.adapt(list, true, true);
		list.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Services"));

		Button btnAddd = new Button(grpServices, SWT.NONE);
		btnAddd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAddd.setText("Add");
		toolkit.adapt(btnAddd, true, true);
		
		btnRemove = new Button(grpServices, SWT.NONE);
		btnRemove.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemove.setText("Remove");
		toolkit.adapt(btnRemove, true, true);
		
		btnAddd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddItemsToListDialog idsDialog = new AddItemsToListDialog(
						getShell(), listWidget);
				idsDialog.open();
				if(list.getItems().length != 0){
					btnRemove.setEnabled(true);
				}
			}
		});

		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listWidget.remove(list.getSelectionIndex());	
			}
		});
		

	}

	@SuppressWarnings("static-access")
	@Override
	protected void bindData() {
		EditingDomain edomain = getEditingDomain();	
		Version version = getEditedVersion();
		
		//publish
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
		IObservableValue includeVersionContrlWidget = WidgetProperties.selection().observe(btnCheckButton); 
		IObservableValue includeVersionContrlValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__PUBLISH_WEB_SERVICE);
		getBindingContext().bindValue(includeVersionContrlWidget, includeVersionContrlValue, booleanToScript, scriptToBoolean);
		
		//activity
		IObservableValue activityWidget = WidgetProperties.text(SWT.Modify).observe(webServiceActivitytext);
		IObservableValue activityValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__WEB_SERVICE_ACTIVITY);
		getBindingContext().bindValue(activityWidget, activityValue, strat, strat);
		
		//function
		functioncomboViewer.setContentProvider(new ArrayContentProvider());
		functioncomboViewer.setLabelProvider(new LabelProvider());
		functioncomboViewer.setInput(version.getWebServiceFunction().values());
		IObservableValue cValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__WEB_SERVICE_FUNCTION);
		IObservableValue cWidget = ViewerProperties.singleSelection().observe(functioncomboViewer);
		getBindingContext().bindValue(cWidget, cValue, null, null);
		
		//description
		IObservableValue styleWidget = WidgetProperties.text(SWT.Modify).observe(serviceDestext);
		IObservableValue styleValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__WEB_SERVICE_DESCRIPTION);
		getBindingContext().bindValue(styleWidget, styleValue, strat, strat);
		
		//service list
		listWidget = WidgetProperties.items().observe(list);
		IObservableList listValue = EMFEditObservables.observeList(edomain, version, Literals.VERSION__WEB_SERVICE_NAMES);
		getBindingContext().bindList(listWidget, listValue, null, null);
		
		if(list.getItems().length != 0){
			btnRemove.setEnabled(true);
		}
	}
}
