package com.odcgroup.t24.server.properties.wizards;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * ServerProperties wizard page
 */

public class ServerPropertiesConnectionTypePage extends WizardPage {

	private final static String AGENT_CONNECTION = "Agent_Connection";
	public static String currentSelected = null;
	public static final String WEBSERVICES = "WebServices";
	private Button btnAgentConnection;
	private Button btnWebServicestafj;

	public ServerPropertiesConnectionTypePage(ISelection selection) {
		super("wizardPage");
		setTitle("Choose the server connection type");
		setDescription("This wizard creates a new server properties file with the connection type selected, that can be opened by a multi-page editor.");
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.verticalSpacing = 9;
		setControl(container);
		
		Label lblChooseTheConnection = new Label(container, SWT.NONE);
		GridData gd_lblChooseTheConnection = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblChooseTheConnection.widthHint = 213;
		lblChooseTheConnection.setLayoutData(gd_lblChooseTheConnection);
		lblChooseTheConnection.setText("Choose the connection type:");
		
		btnAgentConnection = new Button(container, SWT.RADIO);
		btnAgentConnection.setText("T24 Server-Agent");
		btnAgentConnection.setSelection(true);
		currentSelected = AGENT_CONNECTION;
		btnAgentConnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnAgentConnection.getSelection())
					currentSelected = AGENT_CONNECTION;
			}
		});
		
		btnWebServicestafj = new Button(container, SWT.RADIO);
		btnWebServicestafj.setText("T24 Server-Web Service");
		btnWebServicestafj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnWebServicestafj.getSelection())
					currentSelected = WEBSERVICES;
			}
		});
	}
}