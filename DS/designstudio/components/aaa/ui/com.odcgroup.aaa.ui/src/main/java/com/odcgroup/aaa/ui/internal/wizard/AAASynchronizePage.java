package com.odcgroup.aaa.ui.internal.wizard;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAASynchronizePage extends AbstractAAAConnectionPage {

//	private Text resource;
	
	private IOfsModelResource ofsResource;
	
	protected String getOfsResourceURI() {
		return ofsResource.getURI().path();
	}
	
//	public void createControl(Composite parent) {
//		
//		super.createControl(parent);
//		
//		GridData lblGridData = new GridData();
//		lblGridData.widthHint = 70;
//		Label label = null;		
//		
//		Composite topLevel = (Composite)getControl();
//
//		Group formatGroup = new Group(topLevel, SWT.NULL);
//		formatGroup.setLayout(new GridLayout(3, false));
//		formatGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
//		formatGroup.setText(Messages.getString("aaa.wizard.synchronize.group"));
//
//		label = new Label(formatGroup, SWT.NULL);
//		label.setText(Messages.getString("aaa.wizard.synchronize.label"));
//		label.setLayoutData(lblGridData);
//		resource = new Text(formatGroup, SWT.BORDER | SWT.SINGLE);
//		resource.setEditable(false);
//		resource.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
//		
//		initialize();
//		dialogChanged();
//		setControl(topLevel);
//
//	}
	
	

	/**
	 * @param pageName
	 * @param importFacade
	 */
	public AAASynchronizePage(IOfsModelResource ofsResource, AAAImportFacade importFacade) {
		super("", ofsResource.getOfsProject(), importFacade);
		setTitle(Messages.getString("aaa.wizard.synchronize.page.title"));
		setDescription(Messages.getString("aaa.wizard.synchronize.page.description"));
		this.ofsResource = ofsResource;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.wizard.AbstractAAAConnectionPage#doProcessWithValidConnection()
	 */
	@Override
	protected void doProcessWithValidConnection() {
	}

}
