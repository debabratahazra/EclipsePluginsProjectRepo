package com.odcgroup.t24.common.importer.ui.wizard;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.t24.common.importer.IContainerSelector;

public class ContainerExSelectionPage extends ContainerSelectionPage {

	private String message;
	
	private Text name;

	protected boolean doIsPageComplete() {
		return super.doIsPageComplete()
			&& StringUtils.isNotBlank(name.getText());
	}
	
	protected void nameChanged() {
		getFolderSelector().setModelName(name.getText());
	}
	
	protected void createAdditionalGroup(Composite parent) {

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(gd);

		Label label = new Label(composite, SWT.WRAP);
		label.setText(this.message);
		label.setFont(this.getFont());

		name = new Text(composite, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(gd);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				nameChanged();
			}				
		});
		name.addListener(SWT.Modify, this);
		name.setFont(this.getFont());
	}
	

	public ContainerExSelectionPage(IContainerSelector folderSelector, String message) {
		super(folderSelector);
		this.message = message;
	}

}
