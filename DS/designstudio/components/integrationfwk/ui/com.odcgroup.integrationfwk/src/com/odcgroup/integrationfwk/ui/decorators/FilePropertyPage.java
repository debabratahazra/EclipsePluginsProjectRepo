package com.odcgroup.integrationfwk.ui.decorators;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import com.odcgroup.integrationfwk.ui.decorators.core.ResourcePropertiesManager;
import com.odcgroup.integrationfwk.ui.utils.Utils;

/**
 * 
 * 
 * Class that implements FileProperty page
 * 
 */
public class FilePropertyPage extends PropertyPage {
	private IResource resource_;
	private IProject project_;

	private Button setReadOnlyButton_;

	private Text suffixText_;

	private boolean readOnlyButtonSelected_ = false;
	private String suffixNameValue_ = "";

	/**
	 * Constructor for DemoFilePropertyPage.
	 */
	public FilePropertyPage() {
		super();
	}

	public FilePropertyPage(IProject project_) {
		super();
		this.project_ = project_;
	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(Composite)
	 *      Function to create the controls.
	 */
	@Override
	protected Control createContents(Composite arg0) {
		Composite parent = GuiFactory.getInstance().createComposite(arg0, 1);
		resource_ = (IResource) getElement();
		project_ = resource_.getProject();

		PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		StringBuffer information = new StringBuffer("The resource " + resource_.getName() + "'s property \n ");
		information.append("can be changed using this property page");
		Label informationLabel = new Label(parent, SWT.NONE);
		informationLabel.setText(information.toString());

		// Spacer
		Label spacer = new Label(parent, SWT.NONE);
		spacer.setText("");

		// Create button for setting read only attribute.
		createReadOnlyOption(parent);

		// Suffix Text
		Composite child2 = GuiFactory.getInstance().createComposite(parent, 2);
		createSuffixText(child2);

		return parent;

	}

	/**
	 * Create Read Only Button
	 */
	private void createReadOnlyOption(Composite main) {
		setReadOnlyButton_ = new Button(main, SWT.CHECK);
		setReadOnlyButton_.setText("Busy Resource ");
		boolean readOnly = resource_.isReadOnly();
		setReadOnlyButton_.setSelection(readOnly);
	}

	/**
	 * Create Suffix Name Text Box
	 */
	private void createSuffixText(Composite main) {
		Label label = new Label(main, SWT.NONE);
		label.setText("Suffix Value ");

		suffixText_ = GuiFactory.getInstance().createTextField(main);

		String suffixValue = ResourcePropertiesManager.getSuffix(resource_);
		if (suffixValue == null) {
			suffixValue = "";
		}
		suffixText_.setText(suffixValue);
	}

	private String getSuffix() {
		String suffix = "Published " + Utils.getTimeStamp(false);
		return suffix;
	}

	/**
	 * Function to handle APply button press
	 */
	@Override
	protected void performApply() {
		MessageDialog.openInformation(getShell(), "Decorations",
				"Press OK in the File Property page to decorate resources with the current Preference");
	}

	/**
	 * Function to handle default
	 */
	@Override
	protected void performDefaults() {
		// Set the System login id
		suffixText_.setText("Suffix");
	}

	/**
	 * Function to handle the ok button
	 */
	@Override
	public boolean performOk() {
		Decorator demoDecorator = Decorator.getDemoDecorator();
		// To perform decoration with Image Caching
		// DemoDecoratorWithImageCaching demoDecorator =
		// DemoDecoratorWithImageCaching.getDemoDecorator();
		if (demoDecorator == null) {
			return true;
		}
		readOnlyButtonSelected_ = setReadOnlyButton_.getSelection();
		suffixNameValue_ = suffixText_.getText();

		if (readOnlyButtonSelected_) {
			// Make the file read only
			resource_.setReadOnly(true);
		} else {
			resource_.setReadOnly(false);
		}

		if (readOnlyButtonSelected_) {
			// Update the persistent Property
			updatePersistentProperty("Busy", "true");
		} else {
			updatePersistentProperty("Busy", "false");
		}

		if (suffixNameValue_ != null && suffixNameValue_.length() != 0) {
			// Update the owner Persistent Property
			updatePersistentProperty("Suffix", suffixNameValue_);
		} else {
			updatePersistentProperty("Suffix", null);
		}

		DecoratorManager.addSuccessResources(resource_);

		// Refresh the label decorations... Change it to
		// DemoDecoratorWithImageCaching if image caching should be used
		Decorator.getDemoDecorator().refresh();
		// For Image Caching ... Change it to following
		// DemoDecoratorWithImageCaching.getDemoDecorator().refresh();
		return true;
	}

	/**
	 * Function to handle the ok button
	 */

	public boolean performPublish(boolean indicator, String eventName) {

		resource_ = project_.getFile("events" + File.separatorChar + eventName);
		Decorator demoDecorator = Decorator.getDemoDecorator();
		if (demoDecorator == null) {
			return true;
		}

		if (indicator) {
			// Update the persistent Property
			updatePersistentProperty("Busy", "true");
			updatePersistentProperty("Suffix", "Error");
		} else {
			updatePersistentProperty("Busy", "false");
			updatePersistentProperty("Suffix", getSuffix());
		}

		DecoratorManager.addSuccessResources(resource_);

		// Refresh the label decorations... Change it to
		// DemoDecoratorWithImageCaching if image caching should be used
		Decorator.getDemoDecorator().refresh();
		// For Image Caching ... Change it to following
		// DemoDecoratorWithImageCaching.getDemoDecorator().refresh();
		return true;

	}

	/**
	 * Update the Persistent property
	 */
	private void updatePersistentProperty(String qualifiedName, String qualifiedValue) {
		ResourcePropertiesManager.addPersistentProperty(resource_, qualifiedName, qualifiedValue);

	}
}
