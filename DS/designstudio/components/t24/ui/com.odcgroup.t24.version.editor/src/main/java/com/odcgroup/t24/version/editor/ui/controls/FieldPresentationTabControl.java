package com.odcgroup.t24.version.editor.ui.controls;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.builder.builderState.impl.EObjectDescriptionImpl;
import org.eclipse.xtext.ui.label.GlobalDescriptionLabelProvider;
import org.eclipse.xtext.ui.search.IXtextEObjectSearch;

import com.odcgroup.t24.version.databinding.SpinnerUpdateValueStrategy;
import com.odcgroup.t24.version.editor.VersionEditorActivator;
import com.odcgroup.t24.version.editor.databinding.converters.BooleanToYesNoTypeConverter;
import com.odcgroup.t24.version.editor.databinding.converters.YesNoToBooleanConverter;
import com.odcgroup.t24.version.editor.ui.VersionDesignerEditor;
import com.odcgroup.t24.version.editor.ui.VersionMultiPageEditor;
import com.odcgroup.t24.version.editor.ui.dialogs.EditHyperlinkDialog;
import com.odcgroup.t24.version.editor.ui.dialogs.XtextResourceSelectionDialog;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.FieldsLayoutPattern;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage.Literals;
import com.odcgroup.workbench.editors.properties.widgets.BrowsePropertyWidget;

public class FieldPresentationTabControl extends AbstractFieldTabControl {
	
	private Group grpHeader;
	private Spinner rowPositionSpinner;
	private Spinner columnPositionSpinner;
	private Spinner maxLengthSpinner;
	private Spinner enrichmentLengthSpinner;
	//private Text enquiryText;
	private Text enquiryCriteriaText;
	private ComboViewer inputBehavourComboViewer;
	private ComboViewer popUpBehaviourComboViewer;
	private ComboViewer caseConventionComboViewer;
	private ComboViewer fieldDisplayCOmboViewer;
	private Button mandatoryCheckBox;
	private ComboViewer expansionComboViewer;
	private Button rigthAdjustCheckBox;
	private Button reKeyCheckBox;
	private Button hotValidateCheckBox;
	private Button hotFieldCheckBox;
	private Button webValidateCheckBox;
	private Button enrichmentCheckBox;
	private Link hyperlink;
	private ISWTObservableValue observeText;
	
	private List<Binding> bindings;
	
	private Group grpPosition;
	private Color enabledBGColor;
	private Color disabledBGColor = new Color(PlatformUI.getWorkbench().getDisplay(), 205,201,201);
	
	BrowsePropertyWidget enquiryBrowse;
	
	private Text enquiryText;
	
	private Button btnClear;
	
	// @Inject
	private IXtextEObjectSearch eObjectSearch;

	// @Inject
	private GlobalDescriptionLabelProvider globalDescriptionLabelProvider;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FieldPresentationTabControl(Composite parent, VersionDesignerEditor editor, DataBindingContext context,TreeViewer viewer) {
		super(parent, editor, context, viewer);
	    bindings = new ArrayList<Binding>();
	}
	
	/**
	 * @return
	 */
	public Group getHeaderGroup() {
	    return grpHeader;
	}

	@Override
	protected void createTabControls(Composite body) {	
		
		Composite parent = new Composite(body, SWT.NONE);
		GridData parentGD = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		parent.setLayoutData(parentGD);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = layout.marginHeight = 0;
		parent.setLayout(layout);
	    toolkit.adapt(parent);
	    toolkit.paintBordersFor(parent);

	    // widget properties group
	    Group grpWidget = new Group(parent, SWT.NONE);
	    grpWidget.setLayout(new GridLayout(2, false));
	    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3);
	    grpWidget.setLayoutData(gd);
	    grpWidget.setText("Widget Properties");
	    toolkit.adapt(grpWidget);
	    toolkit.paintBordersFor(grpWidget);
	    
	    Label lblDisplayType = new Label(grpWidget, SWT.NONE);
	    lblDisplayType.setAlignment(SWT.CENTER);
	    toolkit.adapt(lblDisplayType, true, true);
	    lblDisplayType.setText("Display Type:");
	    lblDisplayType.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_DisplayType"));

	    fieldDisplayCOmboViewer = new ComboViewer(grpWidget, SWT.READ_ONLY);
	    Combo combo = fieldDisplayCOmboViewer.getCombo();
	    combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
	    combo.setItems(new String[] { "None", "NoDisplay", "Combobox",
		    "Vertical.Options", "Toggle", "DropDown.NoInput" });
	    toolkit.adapt(combo);
	    toolkit.paintBordersFor(combo);
	    
	    Label lblInputBehavior = new Label(grpWidget, SWT.NONE);
	    lblInputBehavior.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,1, 1));
	    lblInputBehavior.setText("Input Behavior:");
	    toolkit.adapt(lblInputBehavior, true, true);
	    lblInputBehavior.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_InputBehaviour"));

	    inputBehavourComboViewer = new ComboViewer(grpWidget, SWT.READ_ONLY);
	    Combo comboInputBehaviour = inputBehavourComboViewer.getCombo();
	    comboInputBehaviour.setItems(new String[] {"None", "NoInput", "NoChange"});
	    GridData layoutData = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		comboInputBehaviour.setLayoutData(layoutData);
	    toolkit.adapt(comboInputBehaviour);
	    toolkit.paintBordersFor(comboInputBehaviour);
	    
	    Label lblCaseConvention = new Label(grpWidget, SWT.NONE);
	    toolkit.adapt(lblCaseConvention, true, true);
	    lblCaseConvention.setText("Case Convention:");
	    lblCaseConvention.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_CaseConv"));

	    caseConventionComboViewer = new ComboViewer(grpWidget, SWT.READ_ONLY);
	    Combo combo_2 = caseConventionComboViewer.getCombo();
	    GridData gd_combo_2 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	    combo_2.setLayoutData(gd_combo_2);
	    combo_2.setItems(new String[] {"None" ,"Lowercase", "Proper_Case", "Uppercase"});
	    toolkit.adapt(combo_2);
	    toolkit.paintBordersFor(combo_2);
	    
	    Label lblexpansionCombo = new Label(grpWidget, SWT.NONE);
	    toolkit.adapt(lblexpansionCombo, true, true);
	    lblexpansionCombo.setText("Expansion:");
	    lblexpansionCombo.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Expansion"));
	    
	    expansionComboViewer = new ComboViewer(grpWidget, SWT.READ_ONLY);
	    Combo expansionCombo = expansionComboViewer.getCombo();
	    GridData gd_ExpCombo = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
	    expansionCombo.setLayoutData(gd_ExpCombo);
	    expansionCombo.setItems(new String[] {"None" ,"No"});
	    toolkit.adapt(expansionCombo);
	    toolkit.paintBordersFor(expansionCombo);
	    
	    Label lblMaxLength = new Label(grpWidget, SWT.NONE);
	    lblMaxLength.setText("Max Length:");
	    toolkit.adapt(lblMaxLength, true, true);
	    lblMaxLength.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_MaxChar"));

	    maxLengthSpinner = new Spinner(grpWidget, SWT.BORDER);
	    maxLengthSpinner.setMaximum(1000);
	    maxLengthSpinner.setSelection(35);
	    toolkit.adapt(maxLengthSpinner);
	    toolkit.paintBordersFor(maxLengthSpinner);
	    
	    Label lblEnrichmentLength = new Label(grpWidget, SWT.NONE);
	    lblEnrichmentLength.setText("Enrichment Length:");
	    toolkit.adapt(lblEnrichmentLength, true, true);
	    lblEnrichmentLength.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_EnrichmentLength"));
	    
	    enrichmentLengthSpinner = new Spinner(grpWidget, SWT.BORDER);
	    enrichmentLengthSpinner.setMaximum(1000);
	    toolkit.adapt(enrichmentLengthSpinner);
	    toolkit.paintBordersFor(enrichmentLengthSpinner);
	    
	    Composite compHyperLink = new Composite(grpWidget, SWT.NONE);
	    compHyperLink.setLayout(new GridLayout(3, false));
	    GridData gdHypLink = new GridData(SWT.FILL, SWT.LEFT, true, false, 2, 1);
	    compHyperLink.setLayoutData(gdHypLink);
	    
	    Label lblHyperlink = new Label(compHyperLink, SWT.NONE);
	    lblHyperlink.setText("Hyperlink:");
	    lblHyperlink.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
	    lblHyperlink.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_HyperLink"));
	    
	    hyperlink = new Link(compHyperLink, SWT.NONE);
	    hyperlink.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false, 1, 1));
	    
	    Button btnEdit = new Button(compHyperLink, SWT.NONE);
	    btnEdit.setText("Edit");
	    btnEdit.setLayoutData(new GridData(SWT.CENTER, SWT.LEFT, false, false, 1, 1));
	    
	    btnEdit.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		EditHyperlinkDialog dialog = new EditHyperlinkDialog(getShell(), observeText);
	    		dialog.open();
	    	}
		});
	    
	    hyperlink.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		try {
					PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(e.text));
				} catch (PartInitException e1) {
				} catch (MalformedURLException e1) {
					MessageDialog mDialog = new MessageDialog(
							null,
							"Invalid link",
							null,
							"Cannot open the hyperlink. Please check if this is a valid link.",
							MessageDialog.ERROR,
							new String[] { "OK" }, 0);
					mDialog.open();
				}
	    	}
		});
	    
	    new Label(grpWidget, SWT.NONE);
	    
	    rigthAdjustCheckBox = new Button(grpWidget, SWT.CHECK);
	    rigthAdjustCheckBox.setText("Right Adjust");
	    toolkit.adapt(rigthAdjustCheckBox, true, true);
	    rigthAdjustCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    rigthAdjustCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_RightAdjust"));
	    
	    enrichmentCheckBox = new Button(grpWidget, SWT.CHECK);
	    enrichmentCheckBox.setText("Enrichment");
	    toolkit.adapt(enrichmentCheckBox, true, true);
	    enrichmentCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
	    enrichmentCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Enrichment"));
	    
	    // positioning group
	    grpPosition = new Group(parent, SWT.NONE);
	    gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1);
	    grpPosition.setLayoutData(gd);
	    grpPosition.setText("Positionning");
	    toolkit.adapt(grpPosition);
	    toolkit.paintBordersFor(grpPosition);
	    grpPosition.setLayout(new GridLayout(2, false));
	    
	    Label label = new Label(grpPosition, SWT.NONE);
	    label.setText("Row Position:");
	    toolkit.adapt(label, true, true);
	    label.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Row"));
	    
	    rowPositionSpinner = new Spinner(grpPosition, SWT.BORDER);
	    rowPositionSpinner.setMaximum(1000);
	    toolkit.adapt(rowPositionSpinner);
	    toolkit.paintBordersFor(rowPositionSpinner);
	    
	    Label label_1 = new Label(grpPosition, SWT.NONE);
	    label_1.setText("Column Position:");
	    toolkit.adapt(label_1, true, true);
	    label_1.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Column"));
	    
	    columnPositionSpinner = new Spinner(grpPosition, SWT.BORDER);
	    columnPositionSpinner.setMaximum(1000);
	    toolkit.adapt(columnPositionSpinner);
	    toolkit.paintBordersFor(columnPositionSpinner);
	    
	    // constraints group
	    Group grpConstraints = new Group(parent, SWT.NONE);
	    gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1);
	    grpConstraints.setLayoutData(gd);
	    grpConstraints.setLayout(new GridLayout(1, false));
	    grpConstraints.setText("Constraints");
	    toolkit.adapt(grpConstraints);
	    toolkit.paintBordersFor(grpConstraints);
	    
	    mandatoryCheckBox = new Button(grpConstraints, SWT.CHECK);
	    mandatoryCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    mandatoryCheckBox.setText("Mandatory");
	    toolkit.adapt(mandatoryCheckBox, true, true);
	    mandatoryCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Mandatory"));
	    
	    reKeyCheckBox = new Button(grpConstraints, SWT.CHECK);
	    reKeyCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    reKeyCheckBox.setText("Rekey Required");
	    toolkit.adapt(reKeyCheckBox, true, true);
	    reKeyCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Rekey"));
	    
	    // validation group
	    Group grpValidation = new Group(parent, SWT.NONE);
	    grpValidation.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
	    grpValidation.setLayout(new GridLayout(1, false));
	    grpValidation.setText("Validation");
	    toolkit.adapt(grpValidation);
	    toolkit.paintBordersFor(grpValidation);
	    
	    hotValidateCheckBox = new Button(grpValidation, SWT.CHECK);
	    hotValidateCheckBox.setText("Hot Validate");
	    hotValidateCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    toolkit.adapt(hotValidateCheckBox, true, true);
	    hotValidateCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_HotValidate"));
	    
	    hotFieldCheckBox = new Button(grpValidation, SWT.CHECK);
	    hotFieldCheckBox.setText("Hot Field");
	    hotFieldCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    toolkit.adapt(hotFieldCheckBox, true, true);
	    hotFieldCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_HotField"));
	    
	    webValidateCheckBox = new Button(grpValidation, SWT.CHECK);
	    webValidateCheckBox.setText("Web Validate");
	    webValidateCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
	    toolkit.adapt(webValidateCheckBox, true, true);	  
	    webValidateCheckBox.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_WebValidate"));
	    
	    // selection enquiry group
	    Group grpSelect = new Group(parent, SWT.NONE);
	    grpSelect.setLayout(new GridLayout(4, false));
	    gd = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
	    grpSelect.setLayoutData(gd);
	    grpSelect.setText("Selection Enquiry");
	    toolkit.adapt(grpSelect);
	    toolkit.paintBordersFor(grpSelect);

	    Label lblEnquiry = new Label(grpSelect, SWT.NONE);
	    lblEnquiry.setText("Enquiry:");
	    lblEnquiry.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_DropDown"));
	    toolkit.adapt(lblEnquiry, true, true);
	    
	    enquiryText = new Text(grpSelect, SWT.BORDER);
		enquiryText.setEditable(false);
		enquiryText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		enquiryText.setToolTipText("Text is read-only");		
		toolkit.adapt(enquiryText, true, true);
		
		Button enquirySelectionBtn = new Button(grpSelect, SWT.NONE);
		enquirySelectionBtn.setText("   ...   ");
		toolkit.adapt(enquirySelectionBtn, true, true);
		
		eObjectSearch = VersionMultiPageEditor.injector.getInstance(IXtextEObjectSearch.class);
		globalDescriptionLabelProvider = VersionMultiPageEditor.injector.getInstance(GlobalDescriptionLabelProvider.class);
	    
		addListenerToEnquirySelectionBtn(enquirySelectionBtn);
		
		btnClear = new Button(grpSelect, SWT.NONE);
		btnClear.setText("Clear");
		addListenerToClearBtn();

	    Label lblSelectionCriteria = new Label(grpSelect, SWT.NONE);
	    lblSelectionCriteria.setText("Selection Criteria:");
	    toolkit.adapt(lblSelectionCriteria, true, true);

	    enquiryCriteriaText = new Text(grpSelect, SWT.BORDER);
	    enquiryCriteriaText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3,   1));
	    toolkit.adapt(enquiryCriteriaText, true, true);
	    enquiryCriteriaText.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_SelectionEnquiry"));

	    Label label_9 = new Label(grpSelect, SWT.NONE);
	    label_9.setText("Widget:");
	    toolkit.adapt(label_9, true, true);
	    label_9.setToolTipText(VersionEditorActivator.getDefault().getString("_HelpText_Widget"));

	    popUpBehaviourComboViewer = new ComboViewer(grpSelect, SWT.READ_ONLY);
	    Combo combo_3 = popUpBehaviourComboViewer.getCombo();
	    GridData gdPop = new GridData(SWT.LEFT, SWT.FILL, true, false, 1, 1);
	    //gdPop.widthHint = 100;
	    combo_3.setLayoutData(gdPop);
	    combo_3.setItems(new String[] {"None" , "Calendar", "Calculator", "RATE CONTROL", "Recurrence"});
	    toolkit.adapt(combo_3);
	    toolkit.paintBordersFor(combo_3);
	    new Label(grpSelect, SWT.NONE);
	    
	    enabledBGColor = grpPosition.getBackground();
	}

	/**
	 * 
	 */
	private void addListenerToClearBtn() {
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enquiryText.setText("");
				enableClearBtn();
			}
		});
	}

	@Override
	protected void bindData() {
		Field field = getTabInput();
		if (field != null) {
			if (!bindings.isEmpty()) {
				for(Binding binding : bindings) {
					binding.dispose();
				}
				bindings.clear();
			}
			bindSpinners();
			bindTextWidgets();
			bindComboviewers();
			bindCheckboxs();
			bindHyperlink();
		}
		enableClearBtn();
	}

	private void bindHyperlink() {
		UpdateValueStrategy targetToModel = new UpdateValueStrategy(){
			
			@Override
			public Object convert(Object value) {
				if(value.toString().isEmpty()){
					return null;
				}
				String val = value.toString().replace("<a>", "");
				val = val.replace("</a>", "");
				return (Object)val;
			}
		};
		
		UpdateValueStrategy modelToTarget = new UpdateValueStrategy(){
			
			@Override
			public Object convert(Object value) {
				if(value.toString().isEmpty()){
					return null;
				}
				return "<a>"+ value +"</a>";
			}
		};
		
		observeText = SWTObservables.observeText(hyperlink);
		IObservableValue hyperlnkVal = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__HYPERLINK); 
		bindings.add(getBindingContext().bindValue(observeText, hyperlnkVal, targetToModel, modelToTarget));
	}

	/**bind the checkbox widgets.
	 * @param selection
	 * @param editingDomain
	 */
	private void bindCheckboxs() {
	    //target to model and model to taregt update Strategy.
	    UpdateValueStrategy targetToModelUpdatsStrategy = new UpdateValueStrategy();
	    targetToModelUpdatsStrategy.setConverter(new BooleanToYesNoTypeConverter());
	    UpdateValueStrategy modelToTragetUpdateStrategy = new UpdateValueStrategy();
	    modelToTragetUpdateStrategy.setConverter(new YesNoToBooleanConverter());
	    //bind the Mandatory chekc box.
	    IObservableValue mandatoryCheckBoxObservable = SWTObservables.observeSelection(mandatoryCheckBox);
	    IObservableValue selectedFieldMandatoryObserveValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__MANDATORY);
	    bindings.add(getBindingContext().bindValue(mandatoryCheckBoxObservable, selectedFieldMandatoryObserveValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	    //bind right Adjust checkbox.
	    IObservableValue rigthAdjustChecckBoxObservable = SWTObservables.observeSelection(rigthAdjustCheckBox);
	    IObservableValue selectedFieldrightAdjustObserveValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(),Literals.FIELD__RIGHT_ADJUST); 
	    bindings.add(getBindingContext().bindValue(rigthAdjustChecckBoxObservable, selectedFieldrightAdjustObserveValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	    //bind Rekey Required checkbox.
	    IObservableValue reKeyRequiredCheckBoxObservable= SWTObservables.observeSelection(reKeyCheckBox);
	    IObservableValue selectedFieldRekeyRequiredObserveValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(),Literals.FIELD__REKEY_REQUIRED); 
	    bindings.add(getBindingContext().bindValue(reKeyRequiredCheckBoxObservable, selectedFieldRekeyRequiredObserveValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	    //bind hot validate checkbox.
	    IObservableValue hotValidateCheckBoxObservable = SWTObservables.observeSelection(hotValidateCheckBox);
	    IObservableValue selectedFieldHotValidateObserveValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(),Literals.FIELD__HOT_VALIDATE); 
	    bindings.add(getBindingContext().bindValue(hotValidateCheckBoxObservable, selectedFieldHotValidateObserveValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	    //bind hot field checkbox.
	    IObservableValue hotFieldCheckBoxObservable = SWTObservables.observeSelection(hotFieldCheckBox);
	    IObservableValue selectedFieldHotFieldObserveValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(),Literals.FIELD__HOT_FIELD); 
	    bindings.add(getBindingContext().bindValue(hotFieldCheckBoxObservable, selectedFieldHotFieldObserveValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	    //bind web validte checkbox.
	    IObservableValue webValidateCheckBoxObservable = SWTObservables.observeSelection(webValidateCheckBox);
	    IObservableValue selectedFieldWebValidateObservableValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(),Literals.FIELD__WEB_VALIDATE); 
	    bindings.add(getBindingContext().bindValue(webValidateCheckBoxObservable, selectedFieldWebValidateObservableValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	    //bind enrichment checkbox.
	    IObservableValue enrichmentCheckBoxObservable = SWTObservables.observeSelection(enrichmentCheckBox);
	    IObservableValue selectedFieldEnrichmentObservableValue = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(),Literals.FIELD__ENRICHMENT); 
	    bindings.add(getBindingContext().bindValue(enrichmentCheckBoxObservable, selectedFieldEnrichmentObservableValue, targetToModelUpdatsStrategy, modelToTragetUpdateStrategy));

	}

	
	/**bind the Text Widget to the fields table viewer.
	 * @param selection
	 */
	private void bindTextWidgets() {
	    UpdateValueStrategy enquireyValueStrategy = stringValueUpdateStrategy();
	    //data binding for the enquiry text.
	    ISWTObservableValue enquieryTextObservable = SWTObservables.observeText(enquiryText, SWT.Modify);
	    IEMFValueProperty  pop1 = EMFEditProperties.value(getEditingDomain(), Literals.FIELD__SELECTION_ENQUIRY);
	    bindings.add(getBindingContext().bindValue(enquieryTextObservable, pop1.observe(getTabInput()),enquireyValueStrategy,null));
	    //data binding for the enquirey criteria.
	    ISWTObservableValue citeriaObservable =SWTObservables.observeText(enquiryCriteriaText, SWT.FocusOut);
	    IEMFValueProperty  pop2 = EMFEditProperties.value(getEditingDomain(), Literals.FIELD__ENQUIRY_PARAMETER);;
	    bindings.add(getBindingContext().bindValue(citeriaObservable, pop2.observe(getTabInput()),enquireyValueStrategy,null));
	}

	/** UpdateValueStrategy for the TextFields to not to set the empty string.
	 * @return updateStrategy 
	 */
	protected UpdateValueStrategy stringValueUpdateStrategy() {
	    UpdateValueStrategy enquireyValueStrategy = new UpdateValueStrategy();
	    enquireyValueStrategy.setConverter(new Converter(String.class ,String.class) {
		@Override
		public Object convert(Object fromObject) {
		    if(((String)fromObject).trim().isEmpty()){
			return null;
		    }
		    return ((String)fromObject).trim();
		}
	    });
	    return enquireyValueStrategy;
	}

	
	/**bind the spinner to the fields table viewer.
	 * @param selection
	 */
	private void bindSpinners() {
	    //row position data binding. 
	    ISWTObservableValue  rowPositionWidgetObservable =  SWTObservables.observeSelection(rowPositionSpinner);
	    IObservableValue  seltedFieldRowObservable =EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__ROW);
	    bindings.add(getBindingContext().bindValue(rowPositionWidgetObservable,seltedFieldRowObservable, null, new SpinnerUpdateValueStrategy()));
	    //column position data binding
	    ISWTObservableValue columnPositionWidgetObservable = SWTObservables.observeSelection(columnPositionSpinner);
	    IObservableValue selctedFieldColumnPostionObservable = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__COLUMN);
	    bindings.add(getBindingContext().bindValue(columnPositionWidgetObservable, selctedFieldColumnPostionObservable, null, new SpinnerUpdateValueStrategy()));
	    //data binding for the max length.
	    ISWTObservableValue maxlengthWidgetObservable = SWTObservables.observeSelection(maxLengthSpinner);
	    IObservableValue selctedFieldMaxlengthObservable = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__MAX_LENGTH);
	    bindings.add(getBindingContext().bindValue(maxlengthWidgetObservable,selctedFieldMaxlengthObservable));
	    //data binding for the enrichment length.
	    ISWTObservableValue enrichMentLengthWidgetObservable = SWTObservables.observeSelection(enrichmentLengthSpinner);
	    IObservableValue enrichMentLengthObservable = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__ENRICHMENT_LENGTH);
	    bindings.add(getBindingContext().bindValue(enrichMentLengthWidgetObservable,enrichMentLengthObservable));
	}


	/**bind the comboviewers.
	 * @param selection
	 * @param editingDomain
	 */
	private void bindComboviewers() {
	    //input behaviour combo databinding
	    ISWTObservableValue inputBehaviourComboObservable =SWTObservables.observeSelection(inputBehavourComboViewer.getCombo());
	    IObservableValue   selectedFieldInputBehaviourObservable =  EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__INPUT_BEHAVIOUR);
	    bindings.add(getBindingContext().bindValue(inputBehaviourComboObservable,selectedFieldInputBehaviourObservable));
	    //popup behaviour combo data binding.
	    ISWTObservableValue popUpBehaviourComboObservable =SWTObservables.observeSelection(popUpBehaviourComboViewer.getCombo());
	    IObservableValue    selectedFieldPopUpBehaviourObservable = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__POPUP_BEHAVIOUR);
	    bindings.add(getBindingContext().bindValue(popUpBehaviourComboObservable,selectedFieldPopUpBehaviourObservable));
	    //Case convention combo viewer data binding.
	    ISWTObservableValue caseConventionComboObservable =SWTObservables.observeSelection(caseConventionComboViewer.getCombo());
	    IObservableValue    selectedFieldCaseConventionObservable =EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__CASE_CONVENTION);
	    bindings.add(getBindingContext().bindValue(caseConventionComboObservable,selectedFieldCaseConventionObservable));
	    //Expansion
	    ISWTObservableValue expansionComboObservable = SWTObservables.observeSelection(expansionComboViewer.getCombo());
	    IObservableValue    expansionObservable =EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__EXPANSION);
	    bindings.add(getBindingContext().bindValue(expansionComboObservable,expansionObservable));
	    //Field Diaplay type comboviewer data binding.
	    ISWTObservableValue fieldDisplayComboObservable = SWTObservables.observeSelection(fieldDisplayCOmboViewer.getCombo());
	    IObservableValue    selectedFieldfieldDisplayObservable = EMFEditObservables.observeValue(getEditingDomain(), getTabInput(), Literals.FIELD__DISPLAY_TYPE);
	    bindings.add(getBindingContext().bindValue(fieldDisplayComboObservable,selectedFieldfieldDisplayObservable));

	}

	@Override
	public void setTabInput(Field input) {
		super.setTabInput(input);
		if (!getContent().isDisposed()) {
			bindData();
			setEnablePositioning(input);
		}
	}
	

	private void setEnablePositioning(Field field) {
		if(field != null){
			EObject eContainer = field.eContainer();
			Version  version = (Version) eContainer;
			if(!version.getFieldsLayoutPattern().equals(FieldsLayoutPattern.NONE)){
				grpPosition.setEnabled(false);
				rowPositionSpinner.setBackground(disabledBGColor);
				columnPositionSpinner.setBackground(disabledBGColor);
			}
			else {
				grpPosition.setEnabled(true);
				rowPositionSpinner.setBackground(enabledBGColor);
				columnPositionSpinner.setBackground(enabledBGColor);
			}
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		disabledBGColor.dispose();
	}
	
	/**
	 * enables screen function combo and clear button
	 */
	public void enableClearBtn() {
		
		if(enquiryText.getText().isEmpty()){
			btnClear.setEnabled(false);
		}else{
			btnClear.setEnabled(true);
		}
	}
	
	/**
	 * Add Selection listener to enquirySelectionBtn, to perform indexing search.
	 * @param enquirySelectionBtn
	 */
	private void addListenerToEnquirySelectionBtn(Button enquirySelectionBtn) {
		enquirySelectionBtn.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("restriction")
			@Override
			public void widgetSelected(SelectionEvent e) {
				XtextResourceSelectionDialog dialog = new XtextResourceSelectionDialog(getShell(), eObjectSearch,
						globalDescriptionLabelProvider);
				dialog.setInitialTypePattern("Enquiry", false);
				final int resultCode = dialog.open();
				if (resultCode == IDialogConstants.OK_ID) {
					Object obj = dialog.getSelection();
					if (obj != null)
						enquiryText.setText(((EObjectDescriptionImpl) obj).getQualifiedName().toString());
				}
				enableClearBtn();
			}
		});
	}	
}
