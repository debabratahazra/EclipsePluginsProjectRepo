package com.odcgroup.integrationfwk.ui.prefprop;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class TWSPropertiesView {

	private Link configureWorkspaceSettingsLink;
	private Button projectSpecificSettingsButton;
	private ServerDetailsView serverDetailsView;

	public TWSPropertiesView(Composite parent) {

		parent.setLayout(new GridLayout(2, false));
		addProjectSpecificSettingsButton(parent);
		addConfigureWorkspaceSettingsLink(parent);
		addSeparator(parent);
		addServerDetailsView(parent);

	}

	private void addConfigureWorkspaceSettingsLink(Composite parent) {
		configureWorkspaceSettingsLink = new Link(parent, SWT.NONE);
		configureWorkspaceSettingsLink
				.setText("<A>Configure Workspace Settings...</A>");
		configureWorkspaceSettingsLink.addListener(SWT.Selection,
				new Listener() {

					public void handleEvent(Event arg0) {
						launchTWSPreferencePage();
					}

				});
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.HORIZONTAL_ALIGN_END;
		configureWorkspaceSettingsLink.setLayoutData(gridData);
	}

	private void addProjectSpecificSettingsButton(Composite parent) {
		projectSpecificSettingsButton = new Button(parent, SWT.CHECK);
		projectSpecificSettingsButton
				.setText("Enable project specific settings");
		projectSpecificSettingsButton
				.addSelectionListener(new SelectionListener() {

					public void widgetDefaultSelected(SelectionEvent arg0) {
					}

					public void widgetSelected(SelectionEvent arg0) {
						configureWorkspaceSettingsLink
								.setEnabled(!projectSpecificSettingsButton
										.getSelection());
						serverDetailsView
								.setEnabled(projectSpecificSettingsButton
										.getSelection());
						serverDetailsView.setAgentComponentsAccessible(false);
						serverDetailsView.setWebComponentsAccessible(false);
					}
				});
	}

	private void addSeparator(Composite parent) {
		Label separator = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		separator.setLayoutData(gridData);
	}

	private void addServerDetailsView(Composite parent) {
		serverDetailsView = new ServerDetailsView(parent);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		serverDetailsView.getComposite().setLayoutData(gridData);
	}

	/**
	 * get the type of connection.
	 * 
	 * @return true if connection type is agent,false otherwise.
	 */
	public boolean getAgentConnection() {
		return serverDetailsView.isAgentConnection();
	}

	/**
	 * get the landscape url.
	 * 
	 * @return
	 */
	public String getComponentServiceURL() {
		return serverDetailsView.getComponentServiceURL();
	}

	public String getHost() {
		return serverDetailsView.getHost();
	}

	public String getOfsSource() {
		return serverDetailsView.getOfsSource();
	}

	public String getPassword() {
		return serverDetailsView.getPassword();
	}

	public String getPort() {
		return serverDetailsView.getPort();
	}

	public boolean getUseProjectSpecificSettings() {
		return projectSpecificSettingsButton.getSelection();
	}

	public String getUserName() {
		return serverDetailsView.getUserName();
	}

	private void launchTWSPreferencePage() {
		// this is for a pop up preference page
		IPreferencePage page = new TWSPreferencePage();
		page.setTitle("Integration Studio");
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode("1", page);
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(new Shell(), mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();
	}

	/**
	 * set all ui components as disabled one.
	 */
	public void setAllUIComponentsDisabled() {
		serverDetailsView.setAllComponentsDisabled();
	}

	/**
	 * set the given landscape url to {@link ServerDetailsView}
	 * 
	 * @param componentServiceURL
	 */
	public void setComponentServiceURL(String componentServiceURL) {
		serverDetailsView.setComponentServiceURL(componentServiceURL);
	}

	/**
	 * set the type of connection.
	 * 
	 * @param isAgent
	 *            - true if the connection type is agent, false if web
	 *            connection.
	 */
	public void setConnectionType(boolean isAgent) {
		serverDetailsView.setAgentConnection(isAgent);
	}

	public void setHost(String host) {
		serverDetailsView.setHost(host);
	}

	public void setOfsSource(String ofsSource) {
		serverDetailsView.setOfsSource(ofsSource);
	}

	public void setPassword(String password) {
		serverDetailsView.setPassword(password);
	}

	public void setPort(String port) {
		serverDetailsView.setPort(port);
	}

	public void setUseProjectSpecificSettings(boolean selected) {
		projectSpecificSettingsButton.setSelection(selected);
		serverDetailsView.setEnabled(selected);
	}

	public void setUserName(String userName) {
		serverDetailsView.setUserName(userName);
	}
}
