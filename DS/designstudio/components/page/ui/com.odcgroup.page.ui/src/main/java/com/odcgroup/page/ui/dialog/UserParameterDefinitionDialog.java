package com.odcgroup.page.ui.dialog;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.ui.properties.table.controls.ReferenceDefinitionDialog;

/**
 *
 * @author pkk
 *
 */
public class UserParameterDefinitionDialog extends ReferenceDefinitionDialog<Parameter> {
	/** */
	private Text nameTxt;
	/** */
	private Text valueTxt;
	/** */
	private Parameter parameter = null;
	/** */
	private boolean edit;

	/**
	 * @param parentShell
	 */
	public UserParameterDefinitionDialog(Shell parentShell) {
		this(parentShell, null);
	}
	
	/**
	 * @param parentShell
	 * @param param
	 */
	public UserParameterDefinitionDialog(Shell parentShell, Parameter param) {
		super(parentShell);
		if (param != null) {
			this.edit = true;
			this.parameter = param;
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		setTitle("Parameter");
		setMessage("Event Parameter definition");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Parameter");
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginHeight = 5;
		gridLayout.marginWidth = 5;
		composite.setLayout(gridLayout);

		Composite body = new Group(composite, SWT.SHADOW_ETCHED_IN);
		gridLayout = new GridLayout(2, false);
		body.setLayout(gridLayout);
		body.setLayoutData(new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		
	    GridData gridData = new GridData();
	    gridData.widthHint = 60;

		Label label = new Label(body, SWT.NONE);
		label.setText("Name");
		label.setLayoutData(gridData);
		
		nameTxt = new Text(body, SWT.SINGLE | SWT.BORDER);
	    gridData = new GridData(GridData.FILL_HORIZONTAL);
	    nameTxt.setLayoutData(gridData);
		
		new Label(body, SWT.NONE).setText("Value");
		valueTxt = new Text(body, SWT.SINGLE | SWT.BORDER);
		valueTxt.setLayoutData(gridData);

	    if (edit) {
	    	nameTxt.setText(parameter.getName());
	    	valueTxt.setText(parameter.getValue());
	    }
		
		nameTxt.addModifyListener(this);
		valueTxt.addModifyListener(this);
		
		return composite;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ReferenceDefinitionDialog#updateDefinition()
	 */
	@Override
	protected Parameter updateDefinition() {
		if (parameter == null) {
			parameter = ModelFactory.eINSTANCE.createParameter();
			parameter.setUserDefined(true);
		}
		parameter.setName(nameTxt.getText());
		parameter.setValue(valueTxt.getText());
		return parameter;
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.controls.ReferenceDefinitionDialog#validChanges()
	 */
	@Override
	protected boolean validChanges() {
		if (StringUtils.isEmpty(nameTxt.getText()) || StringUtils.isEmpty(valueTxt.getText()))
			return false;
		return true;
	}

}
