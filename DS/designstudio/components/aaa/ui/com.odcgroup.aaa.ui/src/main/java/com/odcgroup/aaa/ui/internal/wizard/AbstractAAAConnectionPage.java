package com.odcgroup.aaa.ui.internal.wizard;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.aaa.connector.internal.InvalidMetaDictException;
import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.aaa.ui.internal.model.AAAPreferences;
import com.odcgroup.aaa.ui.internal.model.ConnectionInfo;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsProject;

public abstract class AbstractAAAConnectionPage extends AbstractAAAPage {
	
	private static String CHARSET_ISO_1 = "iso_1"; 
	private static String CHARSET_UTF8 = "utf8";
	private static String CHARSET_DEFAULT = CHARSET_ISO_1;

	private Text server;
	private Text database;
	private Text port;
	private Text username;
	private Text password;
	private CCombo charsetCombo;

	public AbstractAAAConnectionPage(String pageName, IOfsProject ofsProject, AAAImportFacade importFacade) {
		super(pageName, ofsProject, importFacade);
	}

	public AbstractAAAConnectionPage(String pageName, IOfsModelPackage ofsPackage, AAAImportFacade importFacade) {
		super(pageName, ofsPackage, importFacade);
	}

	public void createControl(Composite parent) {
		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		final GridData fieldGridData = new GridData(gridDataStyle);
		final GridData lblGridData = new GridData();
		lblGridData.widthHint = 120;
		
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));
		
		/** 
		 * ###########################################
		 * 1. database group
		 * ###########################################
		 */

		Group dbGroup = new Group(topLevel, SWT.NULL);
		dbGroup.setLayout(new GridLayout(2,false));
		dbGroup.setLayoutData(new GridData(gridDataStyle));
		dbGroup.setText(Messages.getString("aaa.wizard.database.group"));
		
		Label label = null;
		
		// database group - server
		label = new Label(dbGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.server.label"));
		server = new Text(dbGroup, SWT.BORDER | SWT.SINGLE);
        server.setLayoutData(fieldGridData);
        server.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
		// database group - database
		label = new Label(dbGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.database.label"));
		database = new Text(dbGroup, SWT.BORDER | SWT.SINGLE);
		database.setLayoutData(fieldGridData);
		database.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
		
		// database group - port number
		label = new Label(dbGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.port.label"));
		label.setLayoutData(lblGridData);
		port = new Text(dbGroup, SWT.BORDER | SWT.SINGLE);
        port.setLayoutData(fieldGridData);
        port.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
		
		// database group - charset
		label = new Label(dbGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.charset.label"));
		label.setLayoutData(lblGridData);
		charsetCombo = new CCombo(dbGroup, SWT.DROP_DOWN | SWT.BORDER);
		GridData charsetComboGridData = new GridData();
		charsetComboGridData.widthHint = 100;
		charsetCombo.setLayoutData(charsetComboGridData);
		charsetCombo.setEditable(true);
		charsetCombo.add(CHARSET_ISO_1);
		charsetCombo.add(CHARSET_UTF8);
		charsetCombo.setText(CHARSET_DEFAULT); // default selection

        /** 
		 * ###########################################
		 * 2. login group
		 * ###########################################
		 */
		
        Group loginGroup = new Group(topLevel, SWT.NULL);
		loginGroup.setLayout(new GridLayout(2,false));
		loginGroup.setLayoutData(new GridData(gridDataStyle));
		loginGroup.setText(Messages.getString("aaa.wizard.login.group"));
        
		// login group - username
		label = new Label(loginGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.username.label"));
		label.setLayoutData(lblGridData);
		username = new Text(loginGroup, SWT.BORDER | SWT.SINGLE);
		username.setLayoutData(fieldGridData);
		username.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
        
		// login group - password
		label = new Label(loginGroup, SWT.NULL);
		label.setText(Messages.getString("aaa.wizard.password.label"));
		label.setLayoutData(lblGridData);
		password = new Text(loginGroup, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);
		password.setLayoutData(fieldGridData);
		password.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                dialogChanged();
            }
        });
		
        initialize();
        dialogChanged();
        setControl(topLevel);

	}
	
	private void initialize() {
		AAAPreferences preferences = getImportFacade().getPreferences();
		server.setText(preferences.getSQLServer());
		database.setText(preferences.getDatabase());
		port.setText(preferences.getPortNumber());
		username.setText(preferences.getUsername());
		password.setText(preferences.getPassword());
		String charset = preferences.getCharset();
		if (StringUtils.isBlank(charset)) {
			charset = CHARSET_DEFAULT;
		}
		charsetCombo.setText(charset);
	}
	
	public ConnectionInfo getConnectionInfo() {
		String charset = charsetCombo.getText();
		if (StringUtils.isBlank(charset)) {
			charset = CHARSET_ISO_1;
		}
		return new ConnectionInfo(
				server.getText(),
				database.getText(),
				port.getText(),
				charset,
				username.getText(),
				password.getText()
				);
	}
	
	public boolean canFlipToNextPage() {
		if (getErrorMessage() != null) {
			return false;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		if (!validateConnection()) {
			return this;
		}
		
		// Go to the next page
		updateStatus(null);
		return super.getNextPage();
	}

	/**
	 * @return true if the connection is valid. Otherwise return false and pops up an
	 * error dialog
	 */
	protected boolean validateConnection() {
		try {
			// Test the connection
			final ConnectionInfo connectionInfo = getConnectionInfo();
			IRunnableWithProgress op = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.getString("aaa.wizard.test.connection"), 2);
					monitor.worked(1);
					getImportFacade().validateConnection(connectionInfo);
					monitor.worked(1);
					if(monitor.isCanceled()) throw new InterruptedException();
				}
			};
			getContainer().run(true, true, op);

			doProcessWithValidConnection();
		} catch (InvocationTargetException e) {
			checkForInvalidMetaDictException(e);
			setErrorMessage(e.getTargetException().getMessage());
			return false;
		} catch (InterruptedException e) {
			updateStatus(null);
			return false;
		}
		return true;
	}

	/**
	 * @param e
	 */
	private void checkForInvalidMetaDictException(InvocationTargetException e) {
		if (e.getCause() instanceof InvalidMetaDictException) {
			MultiStatus info = chunkMessageForMultiStatusDisplay(e);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(), null,
					null, info);
		}
	}

	/**
	 * In order to display a large(ish) error message we are using MultiStatus. 
	 * The problem is that the string needs to be broken down into lines and chars removed. 
	 * 
	 * @param e
	 * @return
	 */
	private MultiStatus chunkMessageForMultiStatusDisplay(
			InvocationTargetException e) {
		String PID = "com.odcgroup.aaa.ui";
		String detailedMessage = ((InvalidMetaDictException) e.getCause())
				.getDetailedMessage();
		MultiStatus info = new MultiStatus(PID, 1, e.getCause()
				.getMessage(), null);
		String[] chunks = detailedMessage.split("\n");
		for (int i = 0; i < chunks.length; i++) {
			info.add(new Status(IStatus.INFO, PID, 1, chunks[i].replace(
					"\t", "  "), null));
		}
		return info;
	}

	/**
	 * Processing done once we know the connection is valid 
	 */
	protected abstract void doProcessWithValidConnection() throws InvocationTargetException, InterruptedException;
	
	/**
	 * Ensure required informations in here.
	 */
	protected void dialogChanged() {
		
		if (server.getText().length() == 0) {
			updateStatus(Messages.getString("aaa.wizard.server.error"));
			return;
		}
		
		if (database.getText().length() == 0) {
			updateStatus(Messages.getString("aaa.wizard.database.error"));
			return;
		}

		if (port.getText().length() == 0) {
			updateStatus(Messages.getString("aaa.wizard.port.error"));
			return;
		}

		if (username.getText().length() == 0) {
			updateStatus(Messages.getString("aaa.wizard.username.error"));
			return;
		}
		
		if (password.getText().length() == 0) {
			updateStatus(Messages.getString("aaa.wizard.password.error"));
			return;
		}

		updateStatus(null);
		
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
		// TODO revalidate if still correct to remove it
		//getWizard().getContainer().updateButtons();
	}	
	

}
