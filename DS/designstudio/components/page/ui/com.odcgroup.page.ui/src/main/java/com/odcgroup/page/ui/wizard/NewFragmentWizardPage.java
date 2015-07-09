package com.odcgroup.page.ui.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.mdf.editor.ui.dialogs.FormWidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * Wizard Page for creating a new Fragment.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewFragmentWizardPage extends AbstractPageDesignerWizardPage {

	/** Domain object selector */
	private EntitySelector typeSelector = null;
	
	/** */
	private Button xtooltipTypeBtn = null;
	
	/** */
	private Button regularTypeBtn = null;

	
	

   /**
    * set the dialog changes. 
    */
	private void dialogChanged() {
		
		MdfName mdfName = typeSelector.getSelection();
		getSpecification().setDomainName(mdfName);
		
		boolean isDomainDefined = mdfName != null;
		if(regularTypeBtn.getSelection()){
		    if(isDomainDefined){
			 getSpecification().setDefaultFragment(false);
			}else{
			 getSpecification().setDefaultFragment(true);
			}
		}
	
		
	}	
	
	/** 
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizardPage#createExtendedControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createExtendedControls(Composite parent) {
		Label label = new Label(parent, SWT.WRAP);
		GridData labelData = new GridData();
		labelData.widthHint = 80;
		label.setLayoutData(labelData);
		label.setText("Dataset:");
	    typeSelector = 
	    	new EntitySelector(
	    			parent, 
	    			new FormWidgetFactory(), 
	    			getSpecification().getDomainObjectProvider(), 
	    			"");
	    typeSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    typeSelector.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
	    });
	    
	    Label fragmentTypelbl = new Label(parent, SWT.WRAP);
		GridData typelblData = new GridData();
		labelData.widthHint = 80;
		typelblData.verticalIndent=15;
		fragmentTypelbl.setLayoutData(typelblData);
		fragmentTypelbl.setText("Fragment Type:");
		
		
	    Group typeGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
        typeGroup.setLayout(new GridLayout(2, true));
        GridData typeGroupData = new GridData(GridData.FILL_HORIZONTAL);
        typeGroup.setLayoutData(typeGroupData);
        
        
        GridData btnData = null;
        regularTypeBtn = new Button(typeGroup, SWT.RADIO);
        btnData = new GridData();
       
        regularTypeBtn.setLayoutData(btnData);
        regularTypeBtn.setText("Regular");
        regularTypeBtn.setSelection(true);
        regularTypeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				getSpecification().setFragmentType(FragmentModelType.REGULAR);
			}
		});

        xtooltipTypeBtn = new Button(typeGroup, SWT.RADIO);
        btnData = new GridData();
        btnData.horizontalIndent = 20;
       
        xtooltipTypeBtn.setLayoutData(btnData);
        xtooltipTypeBtn.setText("Extended tooltip");
        xtooltipTypeBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				getSpecification().setFragmentType(FragmentModelType.XTOOLTIP);
			}
		});
	   //DS-
       // initializes();
        
		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.NEW_FRAGMENT);

	}
	
	/**
	 * Initializes the Wizard.
	 * @param specification
	 */
	public NewFragmentWizardPage(ModelSpecification specification) {
		super("New Fragment", specification);
	}

}
