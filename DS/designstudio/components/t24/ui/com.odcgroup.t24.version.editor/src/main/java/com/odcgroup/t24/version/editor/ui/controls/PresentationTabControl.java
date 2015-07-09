package com.odcgroup.t24.version.editor.ui.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.databinding.property.value.IValueProperty;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.swt.util.SWTResourceManager;
import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.providers.LanguageViewerLableProvider;
import com.odcgroup.t24.version.model.translation.VersionTranslationHandler;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.TranslationUICore;
import com.odcgroup.translation.ui.views.ITranslationModel;
import com.odcgroup.translation.ui.views.ITranslationViewer;
import com.odcgroup.translation.ui.views.model.TranslationModel;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;



public class PresentationTabControl extends AbstractVersionTabControl {

	/**
	 * Create the composite.
	 * 
	 * @param editor
	 * @param editedVersion
	 */
	public PresentationTabControl(Composite parent,
			VersionDesignerEditor editor, DataBindingContext context) {
    	super(parent, editor, context);
	}

	private Group grpHeader;
	private Combo recordCombo;
	private Combo fldCombo;
	private Text curPosTxt;
	private Text toolbarTxt;
	private IObservableList languageLocaleList;
	private Table table;
	private ITranslationViewer headerViewer;
	
	private static final String DEFAULT_LANGUAGE = "en";

	/**
	 * @return
	 */
	public Group getHeaderGroup() {
		return grpHeader;
	}

	@Override
	protected void createTabControls(Composite body) {

		Composite subcomp = new Composite(body, SWT.NONE);
		subcomp.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		subcomp.setLayoutData(gd);
		toolkit.adapt(subcomp);
		toolkit.paintBordersFor(subcomp);

		Composite presfcomp = new Composite(subcomp, SWT.NONE);
		presfcomp.setLayout(new GridLayout(3, false));
		gd = new GridData(GridData.FILL_VERTICAL);
		presfcomp.setLayoutData(gd);
		toolkit.adapt(presfcomp);
		toolkit.paintBordersFor(presfcomp);

		grpHeader = new Group(subcomp, SWT.NONE);
		// allow only GroupHeader to fill horizontally as well
		gd = new GridData(GridData.FILL_BOTH);
		grpHeader.setLayoutData(gd);
		grpHeader.setText("Header");
		toolkit.adapt(grpHeader);
		toolkit.paintBordersFor(grpHeader);
		grpHeader.setLayout(new GridLayout(1, false));
		grpHeader.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Header"));
		createHeaderControl(grpHeader);

		Composite composite = new Composite(presfcomp, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		gd = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		composite.setLayoutData(gd);
		composite.setLayout(new GridLayout(2, false));

		Label lblRecordsPerPage = new Label(composite, SWT.NONE);
		lblRecordsPerPage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblRecordsPerPage.setText("Records per page:");
		toolkit.adapt(lblRecordsPerPage, true, true);
		lblRecordsPerPage.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_RecordPerPage"));

		recordCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		recordCombo.setLayoutData(gd);
		recordCombo.setListVisible(true);
		recordCombo.setText("1");
		recordCombo.setItems(new String[] { "1", "MULTI" });
		toolkit.adapt(recordCombo);
		toolkit.paintBordersFor(recordCombo);

		Label lblFieldsPerLine = new Label(composite, SWT.NONE);
		lblFieldsPerLine.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFieldsPerLine.setText("Fields per line:");
		toolkit.adapt(lblFieldsPerLine, true, true);
		lblFieldsPerLine.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_FieldsPerLine"));

		fldCombo = new Combo(composite, SWT.BORDER | SWT.READ_ONLY);
		fldCombo.setLayoutData(gd);
		fldCombo.setText("1");
		fldCombo.setItems(new String[] { "1", "MULTI" });
		toolkit.adapt(fldCombo);
		toolkit.paintBordersFor(fldCombo);

		Label lblInitialCursorPosition = new Label(composite, SWT.NONE);
		lblInitialCursorPosition.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblInitialCursorPosition.setText("Initial Cursor Position:");
		toolkit.adapt(lblInitialCursorPosition, true, true);
		lblInitialCursorPosition.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_InitialCursorPosition"));

		curPosTxt = new Text(composite, SWT.BORDER);
		curPosTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(curPosTxt, true, true);

		Label lblToolbar = new Label(composite, SWT.NONE);
		lblToolbar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblToolbar.setText("Browser Toolbar:");
		lblToolbar.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_BrowserToolbar"));

		toolbarTxt = new Text(composite, SWT.BORDER);
		toolbarTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblLang = new Label(composite, SWT.NONE);
		lblLang.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		lblLang.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblLang.setText("Languages:");
		lblLang.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_LanguageCode"));
		//Language Locale TableViewer
		int style = SWT.CHECK|SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.LINE_SOLID;
		TableViewer tableViewer = new TableViewer(composite, style);
		table = tableViewer.getTable();
		GridData gridData = new GridData(GridData.FILL_BOTH );
		gd.verticalIndent = 0;
		gd.horizontalIndent =0;
		table.setLayoutData(gridData);
		table.setLinesVisible(true);
		CheckboxCellEditor checkBoxCellEditor = new CheckboxCellEditor((Composite)tableViewer.getControl(),SWT.NONE);
		ArrayList<IEMFValueProperty> defaultProperties = new ArrayList<IEMFValueProperty>();
		tableViewer.setCellEditors(new CellEditor[] {checkBoxCellEditor});
		
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		tableViewer.setContentProvider(contentProvider);
		IObservableMap[] map=Properties.observeEach(contentProvider.getKnownElements(),defaultProperties.toArray(new IValueProperty[0]));
		tableViewer.setLabelProvider(new LanguageViewerLableProvider(map));
		//get the Locales.
		VersionTranslationHandler versionTransaltion = new VersionTranslationHandler(getEditor());
		ArrayList<Locale> locales = versionTransaltion.getLocales();
	    WritableList input = new WritableList(locales, Locale.class);
		tableViewer.setInput(input);
		
		table.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.item instanceof TableItem){
					updateLanguageLocale((TableItem)e.item);
				}
			}
		});
		
		
	}
   /**
    * update the version locales based on the checkbox selection
    * @param item
    */
	private void updateLanguageLocale(TableItem item){
		
		Locale locale = (Locale)item.getData();
		String language = locale.getLanguage();
		if(item.getChecked()){
			if(!languageLocaleList.contains(language))
			   languageLocaleList.add(locale.getLanguage());
		}else {
			if(languageLocaleList.contains(language))
			   languageLocaleList.remove(locale.getLanguage());
		}
	}
	/**
	 * set the checked state of the check box 
	 */
	private void setCheckBoxCheckedStatus(){
		languageLocaleList = EMFEditObservables.observeList(getEditingDomain(), getEditedVersion(), Literals.VERSION__LANGUAGE_LOCALE);
		List<TableItem> items = Arrays.asList(table.getItems());
		for(TableItem item: items){
			String language = null;
			if(item.getData() instanceof Locale){
				Locale languageLocale = (Locale)item.getData();
				language = languageLocale.getLanguage();
			} 
			if(languageLocaleList.contains(language)){
				item.setChecked(true);
			}else if(languageLocaleList.isEmpty() && language.equals(DEFAULT_LANGUAGE)){
				languageLocaleList.add(language);
				item.setChecked(true);
			} else {
				item.setChecked(false);
			}
		}
		
	}
	@Override
	protected void bindData() {
		Version version = getEditedVersion();
		EditingDomain edomain = getEditor().getEditingDomain();		
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

		// records per page		
		IObservableValue recWidget = WidgetProperties.selection().observe(recordCombo);
		IObservableValue recValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__RECORDS_PER_PAGE);
		getBindingContext().bindValue(recWidget, recValue);
		
		// fields per line
		IObservableValue fldlineWidget = WidgetProperties.selection().observe(fldCombo);
		IObservableValue fldlineValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__FIELDS_PER_LINE);
		getBindingContext().bindValue(fldlineWidget, fldlineValue);

		// cursor position
		IObservableValue curWidget = WidgetProperties.text(SWT.Modify).observe(curPosTxt);
		IObservableValue curValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__INITIAL_CURSOR_POSITION);
		getBindingContext().bindValue(curWidget, curValue, strat, strat);
		
		// toolbar
		IObservableValue toolWidget = WidgetProperties.text(SWT.Modify).observe(toolbarTxt);
		IObservableValue toolValue = EMFEditObservables.observeValue(edomain, version, Literals.VERSION__BROWSER_TOOLBAR);
		getBindingContext().bindValue(toolWidget, toolValue, strat, strat);
		//set the checked status of the locale checkboxes.
		setCheckBoxCheckedStatus();
		
		// update translations
		if(version != null){
			IProject project = OfsResourceHelper.getProject(version.eResource());
			ITranslationManager manager = TranslationCore.getTranslationManager(project);
			ITranslation translation = manager.getTranslation(version);
			if (translation != null) {
				ITranslationPreferences prefs = manager.getPreferences();
				ITranslationModel model = new TranslationModel(prefs, translation);
				headerViewer.hideTranslationKind(ITranslationKind.NAME);
				model.selectKind(ITranslationKind.HEADER1);
				headerViewer.hideOrigin();
				headerViewer.setTranslationModel(model);
			} 
			
		}

	}
	
	private void createHeaderControl(Composite parent) {
		Version editedVersion = getEditedVersion();
		if(editedVersion != null){
		IProject project = OfsResourceHelper.getProject(editedVersion.eResource());
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		ITranslation translation = manager.getTranslation(editedVersion);
		if (translation != null) {
			ITranslationPreferences prefs = manager.getPreferences();
			ITranslationModel model = new TranslationModel(prefs, translation);
			headerViewer = TranslationUICore.getTranslationViewer(project,	parent);
			headerViewer.hideTranslationKind(ITranslationKind.NAME);
			model.selectKind(ITranslationKind.HEADER1);
			headerViewer.hideOrigin();
			headerViewer.setTranslationModel(model);
		} else {
			// display something in the UI that indicates the translation is not
			// available
			}
		}
	}
}
