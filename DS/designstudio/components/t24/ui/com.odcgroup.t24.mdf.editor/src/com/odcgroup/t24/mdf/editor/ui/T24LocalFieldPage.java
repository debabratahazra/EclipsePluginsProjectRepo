package com.odcgroup.t24.mdf.editor.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.domain.repository.DomainRepositoryUtil;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.editor.ui.WidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.FieldSelectionDialog;
import com.odcgroup.mdf.editor.ui.dialogs.ModelElementTreeSelectionUI;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.MdfModelPage;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;

public class T24LocalFieldPage extends MdfModelPage {
   
	private static final String INPUTCHANGECOMBO_VALUE_NONE = "NONE";
	private static final String INPUTCHANGECOMBO_VALUE_NOINPUT = "NOINPUT";
	private static final String INPUTCHANGECOMBO_VALUE_NOCHANGE = "NOCHANGE";
	
	private MdfProperty initialMdfProperty = null;
	private Text maximumCharacterText;
	private Text minimumCharacterText;
	private Combo inputChangeCombo;
	private Button allowOverrideCheckBox;
	private Button defaultValueCheckBox;
	private Text enrichmentText;
	private Button browseButton;
	
	public T24LocalFieldPage(MdfProperty mdfProperty) {
		super(mdfProperty);
		setTitle("T24 LocalField");
        setDescription("Infromation specific to T24 Local Field.");
        this.initialMdfProperty = mdfProperty;
        setWorkingCopy(initialMdfProperty);
	}

	@Override
	public void createControl(Composite parent) {
		WidgetFactory factory = getWidgetFactory();
        Composite container = factory.createComposite(parent);
        container.setLayout(new GridLayout(2, false));
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
       //Maximum Character controls
        factory.createLabel(container, "Maximum Characters:");
        maximumCharacterText = factory.createText(container, null);
        maximumCharacterText.setEnabled(getEditMode() != MODE_EDIT);
        GridData maximumCHarTextGridData = new GridData();
        maximumCHarTextGridData.widthHint=170;
        maximumCharacterText.setLayoutData(maximumCHarTextGridData);
        //Minimum Characters controls.
        factory.createLabel(container, "Minimum Characters:");
        minimumCharacterText = factory.createText(container, null);
        minimumCharacterText.setEnabled(getEditMode() != MODE_EDIT);
        GridData minimumCharTextGridData = new GridData();
        minimumCharTextGridData.widthHint=170;
        minimumCharacterText.setLayoutData(minimumCharTextGridData);
        //No Input/change Control
        factory.createLabel(container, "No Input/Change:");
        inputChangeCombo = factory.createCombo(container);
        inputChangeCombo.setEnabled(true);
        GridData comboGridData = new GridData();
        comboGridData.widthHint =150;
        inputChangeCombo.setLayoutData(comboGridData);
        inputChangeCombo.setItems(new String[]{INPUTCHANGECOMBO_VALUE_NONE, INPUTCHANGECOMBO_VALUE_NOINPUT, INPUTCHANGECOMBO_VALUE_NOCHANGE});
        inputChangeCombo.select(0);// select default: NONE
        factory.createLabel(container, "Enrichment:");
        Composite enrichmentContainer = factory.createComposite(container);
        enrichmentContainer.setLayout(new GridLayout(2, true));
        GridData enrichmentContainerGridData = new GridData(GridData.FILL_HORIZONTAL);
        enrichmentContainerGridData.verticalIndent =-5;
        enrichmentContainer.setLayoutData(enrichmentContainerGridData);
        enrichmentText = factory.createText(enrichmentContainer, null);
        GridData enrichmentTextGridData =new GridData();
        enrichmentTextGridData.widthHint =250;
        enrichmentText.setLayoutData(enrichmentTextGridData);
        browseButton = factory.createButton(enrichmentContainer, "Browse...", SWT.NULL);
        //Override and DefaultValue check box.
        Composite checkBoxContainer = factory.createComposite(container);
        checkBoxContainer.setLayout(new GridLayout(1, false));
        GridData containerGridData = new GridData();
        containerGridData.widthHint=300;
        allowOverrideCheckBox=factory.createButton(checkBoxContainer, "Allow Override", SWT.CHECK);
        allowOverrideCheckBox.setEnabled(true);
        defaultValueCheckBox=factory.createButton(checkBoxContainer, "DefaultValue", SWT.CHECK);
        defaultValueCheckBox.setEnabled(true);
        initialize();
        //add the listners to the controls.
        addListeners();
        
	}

	@Override
	public void doSave(MdfModelElement element) {
		try {
				if (!maximumCharacterText.getText().trim().isEmpty()) {
					int maxChar = Integer.parseInt(maximumCharacterText.getText()
							.trim());
					T24Aspect.setMaxChars((MdfProperty) workingCopy, maxChar);
				}
				if (!minimumCharacterText.getText().trim().isEmpty()) {
					int minChar = Integer.parseInt(minimumCharacterText.getText()
							.trim());
					T24Aspect.setMinChars((MdfProperty) workingCopy, minChar);
				}
		} catch (NumberFormatException numberFomat) {
		}
		
		if (!inputChangeCombo.getText().isEmpty()) {
			T24Aspect.setNoInputChange((MdfProperty) workingCopy,
					inputChangeCombo.getText());
		}
		if (!enrichmentText.getText().trim().isEmpty()) {
			T24Aspect.setApplicationEnrich((MdfProperty) workingCopy,
					enrichmentText.getText());
		}
		T24Aspect.setOverridePossible((MdfProperty) workingCopy,
				allowOverrideCheckBox.getSelection());
		T24Aspect.setDefaultPossible((MdfProperty) workingCopy,
				defaultValueCheckBox.getSelection());

	}
	/**
	 * add listeners to the controls.
	 */
	private void addListeners(){
		maximumCharacterText.addModifyListener(this);
		minimumCharacterText.addModifyListener(this);
		inputChangeCombo.addSelectionListener(this);
		allowOverrideCheckBox.addSelectionListener(this);
		defaultValueCheckBox.addSelectionListener(this);
		enrichmentText.addModifyListener(this);
		browseButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
               openEnrichmentType();
            }
        });
	}
	
	private void initialize(){
		maximumCharacterText.setText(Integer.toString(T24Aspect.getMaxChars(initialMdfProperty)));
		minimumCharacterText.setText(Integer.toString(T24Aspect.getMinChars(initialMdfProperty)));
		if(T24Aspect.getNoInputChange(initialMdfProperty)!=null){
			inputChangeCombo.setText(T24Aspect.getNoInputChange(initialMdfProperty));
		}else{
			inputChangeCombo.setText(INPUTCHANGECOMBO_VALUE_NONE);
		}
		if(initialMdfProperty instanceof MdfAssociation){
			enrichmentText.setEnabled(true);
			browseButton.setEnabled(true);
			if(T24Aspect.getApplicationEnrich(initialMdfProperty) !=null)
			enrichmentText.setText(T24Aspect.getApplicationEnrich(initialMdfProperty));
		}else {
			enrichmentText.setEnabled(false);
			browseButton.setEnabled(false);
		}
		allowOverrideCheckBox.setSelection(T24Aspect.getOverridePossible(initialMdfProperty));
		defaultValueCheckBox.setSelection(T24Aspect.getDefaultPossible(initialMdfProperty));
		
	}
	/**
	 * open the enrichment dialog to select the enrichmentvalue.
	 */
	private void openEnrichmentType(){
		  if (initialMdfProperty instanceof MdfAssociation){
			MdfEntity entity = DomainRepositoryUtil.getMdfEntity(((MdfAssociationImpl) initialMdfProperty).getType()
					.getQualifiedName(), ((MdfAssociationImpl) initialMdfProperty).eResource());
			  ModelElementTreeSelectionUI modelElementUI = new ModelElementTreeSelectionUI();
			  FieldSelectionDialog dialog = new FieldSelectionDialog(getShell(),modelElementUI);
			  modelElementUI.setFieldSelectionDialog(dialog);
			  modelElementUI.setInput(((MdfClass)entity));
			  if(dialog.open() ==Dialog.OK){
				 Object[] result= dialog.getResult();
				 if(result.length !=0){
				     String name =((MdfProperty)result[0]).getName();  
					 enrichmentText.setText(name);
				 }
			  }
			  
		  }
	}
}
