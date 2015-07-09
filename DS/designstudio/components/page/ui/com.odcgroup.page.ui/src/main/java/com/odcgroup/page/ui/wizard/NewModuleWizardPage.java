package com.odcgroup.page.ui.wizard;

import java.util.List;

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
import com.odcgroup.page.metamodel.DataType;
import com.odcgroup.page.metamodel.DataValue;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;

/**
 * Wizard Page for creating a new Module.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewModuleWizardPage extends AbstractPageDesignerWizardPage {

	/** Domain object selector */
	private EntitySelector typeSelector = null;
	private static final String KEY = "SEL_VAL";
	
	/**
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizardPage#dialogChanged
	 */
	private void dialogChanged() {
		getSpecification().setDomainName(typeSelector.getSelection());
	}

	/**
	 * @see com.odcgroup.page.ui.wizard.AbstractPageDesignerWizardPage#createExtendedControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createExtendedControls(Composite parent) {
		Label label = new Label(parent, SWT.WRAP);
		GridData labelData = new GridData();
		labelData.widthHint = 90;
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
	    
	    Label type = new Label(parent, SWT.WRAP);
	    GridData moduleTypeData = new GridData();
	    moduleTypeData.widthHint = 90;
	    type.setLayoutData(moduleTypeData);
	    type.setText("Module Type:");
	    createModuleTypeGroup(parent);
	}
	
	/**
	 * @param body
	 */
	private void createModuleTypeGroup(Composite parent) {
	    Group group = new Group(parent, SWT.NONE);
	    group.setBackground(parent.getBackground());
	    GridLayout gridLayout = new GridLayout(3, false);
	    group.setLayout(gridLayout);
	    GridData data = null;
	    data = new GridData(GridData.FILL_BOTH);		
	    group.setLayoutData(data);
	    DataType dType = findDataType("SearchModule");
	    List<DataValue> dValues = dType.getValues();
	    Button radioBtn = null;
	    for (DataValue dataValue : dValues) {
		if(dataValue.getValue().equals("input")||dataValue.getValue().equals("output")){
		    Label label=new Label(group,SWT.NONE);
		}
		radioBtn = new Button(group, SWT.RADIO);
		radioBtn.setText(dataValue.getDisplayName());
		radioBtn.setData(KEY, dataValue.getValue());
		radioBtn.setBackground(group.getBackground());
		if(dataValue.getValue().equals("input")){
		    GridData radData=new GridData();
		    radData.horizontalSpan=2;
		    radioBtn.setLayoutData(radData);
		}
		if (dataValue.getValue().equals("none")) {
		    radioBtn.setSelection(true);
		}
		radioBtn.addSelectionListener(new SelectionAdapter() {
		    public void widgetSelected(SelectionEvent e) {
			getSpecification().setSearchType((String)e.widget.getData(KEY));
		    }				
		});
	    }
	}
	
	private DataType findDataType(String name) {
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		for (DataType dataType : metamodel.getDataTypes().getTypes()) {
			if (name.equals(dataType.getName())) {
				return dataType;
			}
		}
		return null;
	}

	/**
	 * Initializes the Wizard.
	 * @param specification 
	 */
	public NewModuleWizardPage(ModelSpecification specification) {
		super("New Module", specification);
	}

}
