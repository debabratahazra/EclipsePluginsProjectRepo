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

import com.odcgroup.mdf.editor.ui.dialogs.FormWidgetFactory;
import com.odcgroup.mdf.editor.ui.dialogs.mdf.assist.EntitySelector;
import com.odcgroup.mdf.metamodel.MdfName;

/**
 * Wizard Page for creating a new Layer.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewLayerWizardPage extends AbstractPageDesignerWizardPage {

	/** Domain object selector */
	private EntitySelector typeSelector = null;
	
	/** */
	private Button horizontalBoxBtn = null;
	
	/** */
	private Button verticalBoxBtn = null;

	/** */
	private void initializes() 
	{
        verticalBoxBtn.setSelection(true);	
	}

	/**
	 * 
	 */
	private void dialogChanged() {
		
		MdfName mdfName = typeSelector.getSelection();
		getSpecification().setDomainName(mdfName);
	}	
	
	/**
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizardPage#createExtendedControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createExtendedControls(Composite parent) {
		
        Group typeGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
        typeGroup.setLayout(new GridLayout(1, true));
        GridData typeGroupData = new GridData(GridData.FILL_HORIZONTAL);
        typeGroupData.horizontalSpan = 2;
        typeGroup.setLayoutData(typeGroupData);
        typeGroup.setText("Layer Type");
        
        GridData btnData = null;
        verticalBoxBtn = new Button(typeGroup, SWT.RADIO);
        btnData = new GridData();
        btnData.horizontalIndent = 90;
        verticalBoxBtn.setLayoutData(btnData);
        verticalBoxBtn.setText("Vertical Box");
        verticalBoxBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				getSpecification().setFragmentType(FragmentModelType.VERTICAL);
			}
		});

        horizontalBoxBtn = new Button(typeGroup, SWT.RADIO);
        btnData = new GridData();
        btnData.horizontalIndent = 90;
        horizontalBoxBtn.setLayoutData(btnData);
        horizontalBoxBtn.setText("Horizontal Box");
        horizontalBoxBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				getSpecification().setFragmentType(FragmentModelType.HORIZONTAL);
			}
		});

		
        Group domainGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
        domainGroup.setLayout(new GridLayout(2, false));        
        GridData domainData = new GridData(GridData.FILL_HORIZONTAL);
        domainData.horizontalSpan = 2;
        domainGroup.setLayoutData(domainData);
        domainGroup.setText("Domain Object");
		
		Label label = new Label(domainGroup, SWT.WRAP);
		GridData labelData = new GridData();
		labelData.widthHint = 80;
		label.setLayoutData(labelData);
		label.setText("Dataset:");
	    typeSelector = 
	    	new EntitySelector(
	    			domainGroup, 
	    			new FormWidgetFactory(), 
	    			getSpecification().getDomainObjectProvider(), 
	    			"");
	    typeSelector.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    typeSelector.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
	    });
	    
        initializes();
	}
	
	/**
	 * Initializes the Wizard.
	 * @param specification
	 */
	public NewLayerWizardPage(ModelSpecification specification) {
		super("New Layer", specification);
	}

}
