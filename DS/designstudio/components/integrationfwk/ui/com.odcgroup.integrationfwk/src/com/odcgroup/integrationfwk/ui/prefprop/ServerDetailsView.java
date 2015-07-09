package com.odcgroup.integrationfwk.ui.prefprop;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ServerDetailsView {
	private final Group serverDetails;
	private final Label hostTitle;
	private final Text hostText;
	private final Label ofsSourceTitle;
	private final Text ofsSourceText;
	private final Text portText;
	private final Label portTitle;
	private final Label lblUserName;
	private final Text txtUserName;
	private final Label lblPassword;
	private final Text txtPassword;
	private final Group userCredentials;
	/** connection choice group */
	private final Group connectionChoiceDetails;
	/** agent connection choice button */
	private final Button buttonAgent;
	/** web connection choice button */
	private final Button buttonWeb;
	/** web connection group */
	private final Group webConnection;
	/** landscape url text box */
	private final Text textIFServiceURL;
	/** landscape url label */
	private final Label labelIFServiceURL;

	public ServerDetailsView(Composite parent) {
		// connection choice group creation
		connectionChoiceDetails = new Group(parent, SWT.SHADOW_IN);
		connectionChoiceDetails.setText("Connection Method");
		connectionChoiceDetails.setLayout(new RowLayout(SWT.VERTICAL));

		buttonAgent = new Button(connectionChoiceDetails, SWT.RADIO);
		buttonAgent.setText("Agent");

		buttonWeb = new Button(connectionChoiceDetails, SWT.RADIO);
		buttonWeb.setText("Web Service");

		serverDetails = new Group(parent, SWT.SHADOW_IN);
		serverDetails.setText("Connection Parameters");
		serverDetails.setLayout(new GridLayout(2, false));

		hostTitle = new Label(serverDetails, SWT.LEFT);
		hostTitle.setText("Host:");
		hostText = new Text(serverDetails, SWT.SINGLE | SWT.BORDER);
		hostText.setLayoutData(gridData());

		ofsSourceTitle = new Label(serverDetails, SWT.LEFT);
		ofsSourceTitle.setText("OfsID:");
		ofsSourceText = new Text(serverDetails, SWT.SINGLE | SWT.BORDER);
		ofsSourceText.setLayoutData(gridData());

		portTitle = new Label(serverDetails, SWT.LEFT);
		portTitle.setText("Port:");
		portText = new Text(serverDetails, SWT.SINGLE | SWT.BORDER);
		portText.setLayoutData(gridData());
		portText.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent event) {
				filterPortKeys(event);
			}

			public void keyReleased(KeyEvent event) {
			}
		});
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		// web connection group creation
		webConnection = new Group(parent, SWT.SHADOW_IN);
		webConnection.setText("Web Connection Details");
		webConnection.setLayout(new GridLayout(2, false));
		webConnection.setLayoutData(gridData);

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		labelIFServiceURL = new Label(webConnection, SWT.RIGHT);
		labelIFServiceURL.setText("IF Services URL : ");

		textIFServiceURL = new Text(webConnection, SWT.SINGLE | SWT.BORDER);
		textIFServiceURL.setLayoutData(gridData);

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		userCredentials = new Group(parent, SWT.SHADOW_IN);
		userCredentials.setText("User Credentials");
		userCredentials.setLayout(new GridLayout(2, false));
		userCredentials.setLayoutData(gridData);

		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		lblUserName = new Label(userCredentials, SWT.LEFT);
		lblUserName.setText("User Name:");

		txtUserName = new Text(userCredentials, SWT.SINGLE | SWT.BORDER);
		txtUserName.setLayoutData(gridData);

		lblPassword = new Label(userCredentials, SWT.LEFT);
		lblPassword.setText("Password:");

		txtPassword = new Text(userCredentials, SWT.SINGLE | SWT.BORDER);
		txtPassword.setEchoChar('*');
		txtPassword.setLayoutData(gridData);
		setAgentComponentsAccessible(false);
		setWebComponentsAccessible(false);
		listenConnectionChoice();
	}

	private void filterPortKeys(KeyEvent event) {
		if (event.keyCode == SWT.ARROW_LEFT || event.keyCode == SWT.ARROW_RIGHT) {
			return;
		} else if (event.keyCode == SWT.BS || event.keyCode == SWT.DEL) {
			return;
		} else if (Character.isDigit(event.character)) {
			return;
		} else {
			event.doit = false;
		}
	}

	/**
	 * get the entered value from {@link #textIFServiceURL}
	 * 
	 * @return
	 */
	public String getComponentServiceURL() {
		return textIFServiceURL.getText();
	}

	public Composite getComposite() {
		return serverDetails;
	}

	public String getHost() {
		return hostText.getText();
	}

	public String getOfsSource() {
		return ofsSourceText.getText();
	}

	public String getPassword() {
		return txtPassword.getText();
	}

	public String getPort() {
		return portText.getText();
	}

	public String getUserName() {
		return txtUserName.getText();
	}

	private GridData gridData() {
		GridData textGridData = new GridData();
		textGridData.horizontalAlignment = GridData.FILL;
		textGridData.grabExcessHorizontalSpace = true;
		return textGridData;
	}

	/**
	 * checking whether the connection type is agent or not.
	 * 
	 * @return true if agent connection, false otherwise.
	 */
	public Boolean isAgentConnection() {
		return buttonAgent.getSelection();
	}

	/**
	 * checking whether the connection type is web or not.
	 * 
	 * @return true if web connection, false otherwise.
	 */
	public boolean isWebConnection() {
		return buttonWeb.getSelection();
	}

	/**
	 * method which helps to listen the button's in connection choice group.
	 */
	private void listenConnectionChoice() {
		buttonAgent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				setAgentComponentsAccessible(true);
				setWebComponentsAccessible(false);
			}
		});

		buttonWeb.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				setWebComponentsAccessible(true);
				setAgentComponentsAccessible(false);
			}
		});
	}

	/**
	 * method which decides whether the agent connection components need to be
	 * accessible or not.
	 * 
	 * @param enabled
	 *            - true if need to be accessible, false otherwise.
	 */
	public void setAgentComponentsAccessible(boolean enabled) {
		hostTitle.setEnabled(enabled);
		hostText.setEnabled(enabled);
		portTitle.setEnabled(enabled);
		portText.setEnabled(enabled);
		ofsSourceTitle.setEnabled(enabled);
		ofsSourceText.setEnabled(enabled);
	}

	/**
	 * method which set the connection type as agent.
	 * 
	 * @param enabled
	 */
	public void setAgentConnection(boolean enabled) {
		buttonAgent.setEnabled(enabled);
		buttonAgent.setSelection(enabled);
		setAgentComponentsAccessible(enabled);
		buttonWeb.setEnabled(!enabled);
		buttonWeb.setSelection(!enabled);
		setWebComponentsAccessible(!enabled);
	}

	/**
	 * set the all ui components as disabled one except connection choice.
	 */
	public void setAllComponentsDisabled() {
		setAgentComponentsAccessible(false);
		setEnabled(false);
		setWebComponentsAccessible(false);
	}

	/**
	 * set the given value to the text box {@link #textIFServiceURL}
	 * 
	 * @param componentServiceURL
	 */
	public void setComponentServiceURL(String componentServiceURL) {
		textIFServiceURL.setText(componentServiceURL);
	}

	public void setEnabled(boolean enabled) {
		buttonAgent.setEnabled(enabled);
		buttonWeb.setEnabled(enabled);
	}

	public void setHost(String host) {
		hostText.setText(host);
	}

	public void setOfsSource(String ofsSource) {
		ofsSourceText.setText(ofsSource);
	}

	public void setPassword(String password) {
		txtPassword.setText(password);
	}

	public void setPort(String port) {
		portText.setText(port);
	}

	public void setUserName(String userName) {
		txtUserName.setText(userName);
	}

	/**
	 * method which decides whether the web connection components need to be
	 * accessible or not.
	 * 
	 * @param enabled
	 *            - true if need to be accessible, false otherwise.
	 */
	public void setWebComponentsAccessible(boolean enabled) {
		labelIFServiceURL.setEnabled(enabled);
		textIFServiceURL.setEnabled(enabled);
	}
}
