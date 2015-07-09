package com.odcgroup.menu.editor.wizard;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.workbench.core.helper.StringHelper;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationPage;

public class MenuWizardPage extends AbstractNewModelResourceCreationPage {

	protected MenuWizardPage(String pageName, IWorkbench workbench,
			IPath containerFullPath) {
		super(pageName, workbench, containerFullPath, "menu");
	}

	@Override
	public void createControl(Composite parent) {

		Composite topLevel = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 12;
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		topLevel.setFont(parent.getFont());

		Label label = new Label(topLevel, SWT.WRAP);
		label.setText(StringHelper.toFirstUpper(model) + " Name :");
		label.setFont(this.getFont());

		fileNameField = new Text(topLevel, SWT.SINGLE | SWT.BORDER);
		fileNameField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileNameField.addModifyListener(validator);
		fileNameField.setFont(this.getFont());
		fileNameField.setText("MyMenu");

		setControl(parent);
	}
	
	/**
	 * @return
	 */
	public String getNameFieldValue() {
		return fileNameField.getText();
	}

	@Override
	public boolean doFinish(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return false;
	}
}
