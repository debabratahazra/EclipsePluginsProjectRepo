package com.odcgroup.domain.edmx.ui.wizard;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.domain.edmx.ui.EDMXUICore;
import com.odcgroup.domain.edmx.ui.util.EDMXConstants;

/**
 * @author Ramya Veigas
 * @version 1.0
 */
public class EDMXPage extends WizardPage {

	private Text url;
	private Text username;
	private Text password;
	
	protected EDMXPage() {
		super("");
		setTitle(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_PAGE_TITLE));
		setDescription(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_PAGE_DESC));
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		final GridData fieldGridData = new GridData(gridDataStyle);
		final GridData lblGridData = new GridData();
		lblGridData.widthHint = 120;
		
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		
		/** 
		 * ###########################################
		 * Login group
		 * ###########################################
		 */
		
        Group loginGroup = new Group(topLevel, SWT.NULL);
		loginGroup.setLayout(new GridLayout(2, false));
		loginGroup.setLayoutData(new GridData(gridDataStyle));
		loginGroup.setText(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_LOGIN_GROUP_LABEL));
		
		// login group - url
		Label label = new Label(loginGroup, SWT.NULL);
		label.setText(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_URL_LABEL));
		label.setLayoutData(lblGridData);
		url = new Text(loginGroup, SWT.BORDER | SWT.SINGLE);
		url.setLayoutData(fieldGridData);
		url.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
		// login group - username
		label = new Label(loginGroup, SWT.NULL);
		label.setText(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_USERNAME_LABEL));
		label.setLayoutData(lblGridData);
		username = new Text(loginGroup, SWT.BORDER | SWT.SINGLE);
		username.setLayoutData(fieldGridData);
        
		// login group - password
		label = new Label(loginGroup, SWT.NULL);
		label.setText(EDMXUICore.getDefault().getString(EDMXConstants.EDMX_WIZARD_PASSWORD_LABEL));
		label.setLayoutData(lblGridData);
		password = new Text(loginGroup, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);
		password.setLayoutData(fieldGridData);
		
        initialize();
        setControl(topLevel);
	}
	
	private void initialize() {
		url.setText("");
		username.setText("");
		password.setText("");
	}
	
	/**
	 * Ensure required informations in here.
	 */
	protected void dialogChanged() {
		if (url.getText().length() == 0) {
			updateStatus(EDMXUICore.getDefault()
					.getString(EDMXConstants.EDMX_WIZARD_URL_ERROR));
			return;
		}
		
		if (url.getText().length() > 0) {
			try {
				new URL(url.getText());
			} catch (MalformedURLException e) {
				updateStatus(EDMXUICore.getDefault()
						.getString(EDMXConstants.EDMX_WIZARD_MALFORMEDURL_ERROR));
				return;
			}
		}

		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}
	
	public Text getUrl() {
		return url;
	}
	
	public URL getSvcUrl() {
		try {
			return new URL(getUrl().getText());
		} catch (MalformedURLException e) {
			return null;
		}
	}


	public Text getUsername() {
		return username;
	}

	public Text getPassword() {
		return password;
	}
}
