package com.odcgroup.page.ui.wizard;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * This is the base class for all {@link WizardPage} in the Page Designer.<p>
 * 
 * Concrete Wizard Page should override the method 
 * {@code AbstractPageDesignerWizardPage#createExtendedControls(Composite)}.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public abstract class AbstractPageDesignerWizardPage extends WizardPage {
	
	/** The description */
	private Text description;
	
	/** The item name field */
	private Text name;

	/** The model specification */
	private ModelSpecification specification;
	
	/**
	 * Ensure required informations in here.
	 */
	private void dialogChanged() {
		
		String modelName = name.getText();

		// the model name is mandatory
		if (StringUtils.isEmpty(modelName)) {
			updateStatus("You must enter a " + getSpecification().getModelTypeName() + " name");
			return;
		}

		if(!Character.isUpperCase(modelName.charAt(0))) {
			setErrorMessage(getSpecification().getModelTypeName()+" name must start with an uppercase letter");
			return;
		}
		
		// DS-2069 the presence of special character in the name of the model
		if (!getSpecification().isValidModelName(modelName)) {
			updateStatus("This " + getSpecification().getModelTypeName() + " name contains invalid characters.");
			return;
		}

		// check the name of the model does not already exists
		if (getSpecification().modelExists(modelName)) {
			updateStatus("This " + getSpecification().getModelTypeName() + " already exist");
			return;
		}
		
		specification.setModelName(modelName);
		specification.setDescription(description.getText());

		// no error
		updateStatus(null);
	}

	/**
	 * Initializes the fields
	 */
	private void initialize() {
		ModelSpecification specification = getSpecification();
		name.setText(specification.getModelName());
		description.setText(specification.getDescription());
	}
	
	/**
	 * @param message
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	/**
	 * Concrete wizard page can override this method to create additional controls
	 * @param parent the parent composite
	 */
	protected void createExtendedControls(Composite parent) {
		// do nothing by default
	}

	/**
	 * @return the container path
	 */
	protected final IPath getContainerPath() {
		return this.specification.getContainerPath();
	}

	/**
	 * Create the necessary widget for the dialog window.
	 * 
	 * @param parent
	 *            The parent window
	 */
	public void createControl(Composite parent) {

		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;

		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(2, false));

		Label label = null;
		GridData labelData = null;
		GridData fieldData = null;

		// label : model-name
		label = new Label(topLevel, SWT.NULL);
		label.setText(StringHelper.toFirstUpper(getSpecification().getModelTypeName()) + " Name:");
		labelData = new GridData();
		labelData.widthHint = 110;
		label.setLayoutData(labelData);

		// field : item-name
		name = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		fieldData = new GridData(gridDataStyle);
		name.setLayoutData(fieldData);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		// label : item description
		label = new Label(topLevel, SWT.NULL);
		labelData = new GridData();
		labelData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
		labelData.widthHint = 90;
		label.setLayoutData(labelData);
		label.setText("Description:");

		// field : item description
		description = new Text(topLevel, SWT.BORDER | SWT.SINGLE);
		fieldData = new GridData(gridDataStyle);
		fieldData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
		fieldData.heightHint = 100;
		description.setLayoutData(fieldData);
		description.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		
		createExtendedControls(topLevel);

		initialize();
		dialogChanged();
		setControl(topLevel);
	}
	
	/**
	 * @return the specification of the model
	 */
	public final ModelSpecification getSpecification() {
		return this.specification;
	}

	/**
	 * Initializes the Wizard.
	 * @param pageName the name of the wizard
	 * @param specification the specification of the model
	 */
	public AbstractPageDesignerWizardPage(String pageName, ModelSpecification specification) {
		super(pageName);
		this.specification = specification;
		String modelTypeName = specification.getModelTypeName();
		setTitle("Create a new " + StringHelper.toFirstUpper(modelTypeName));
		setDescription("Click Finish to create a new " + modelTypeName);
		setPageComplete(false);
	}

}
