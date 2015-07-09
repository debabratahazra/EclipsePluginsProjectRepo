package com.odcgroup.integrationfwk.ui.prefprop;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.odcgroup.integrationfwk.ui.projects.TWSProjectPreferences;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

public class TWSPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	ServerDetailsView serverDetailsView;
	TWSProjectPreferences twsProjectPreferences;

	@Override
	protected Control createContents(Composite parent) {
		serverDetailsView = new ServerDetailsView(parent);
		serverDetailsView.setHost(twsProjectPreferences().getHostPreference());
		serverDetailsView.setOfsSource(twsProjectPreferences()
				.getOfsSourcePreference());
		serverDetailsView.setPort(Integer.toString(twsProjectPreferences()
				.getPortPreference()));
		serverDetailsView.setAgentConnection(new Boolean(
				twsProjectPreferences().getAgentConnectionPreference()));
		// ensure both of the connection choice button is enabled
		serverDetailsView.setEnabled(true);
		serverDetailsView.setComponentServiceURL(twsProjectPreferences()
				.getComponentServiceURLPreference());
		serverDetailsView.setUserName(twsProjectPreferences()
				.getUserNamePreference());
		serverDetailsView.setPassword(twsProjectPreferences()
				.getPasswordPreference());
		// component accessibility
		if (new Boolean(twsProjectPreferences().getAgentConnectionPreference())) {
			serverDetailsView.setAgentComponentsAccessible(true);
			serverDetailsView.setWebComponentsAccessible(false);
		} else {
			serverDetailsView.setAgentComponentsAccessible(false);
			serverDetailsView.setWebComponentsAccessible(true);
		}
		return parent;
	}

	/**
	 * 
	 * @return port details from properties view if valid one, default
	 *         preference otherwise
	 */
	private int getPort() {
		try {
			return Integer.parseInt(serverDetailsView.getPort());
		} catch (NumberFormatException ex) {
			return twsProjectPreferences().getPortPreferenceDefault();
		}
	}

	public void init(IWorkbench arg0) {
	}

	/**
	 * Method which helps to validate the agent details. If any empty values
	 * found, a dialog will be shown to the user as whether they want to use the
	 * default preference value, and then proceed based on the user's selection.
	 * 
	 * @return true if valid to proceed/user wants to correct the details, false
	 *         otherwise
	 */
	private boolean isValidToProceed() {
		if (!serverDetailsView.isAgentConnection()) {
			return true;
		}
		return TWSConsumerProjectUtil.isValidAgentDetails(
				serverDetailsView.getHost(), serverDetailsView.getPort(),
				serverDetailsView.getOfsSource());
	}

	@Override
	protected void performDefaults() {
		serverDetailsView.setHost(twsProjectPreferences()
				.getHostPreferenceDefault());
		serverDetailsView.setOfsSource(twsProjectPreferences()
				.getOfsSourcePreferenceDefault());
		serverDetailsView.setPort(Integer.toString(twsProjectPreferences()
				.getPortPreferenceDefault()));
		serverDetailsView.setAgentConnection(new Boolean(
				twsProjectPreferences().getAgentConnectionPreferenceDefault()));
		// components accessibility
		if (new Boolean(twsProjectPreferences()
				.getAgentConnectionPreferenceDefault())) {
			serverDetailsView.setAgentComponentsAccessible(true);
			serverDetailsView.setWebComponentsAccessible(false);
		} else {
			serverDetailsView.setAgentComponentsAccessible(false);
			serverDetailsView.setWebComponentsAccessible(true);
		}
		// ensure both of the connection choice button is enabled
		serverDetailsView.setEnabled(true);

	}

	@Override
	public boolean performOk() {
		if (!isValidToProceed()) {
			return false;
		}
		updateAgentDetails();
		twsProjectPreferences().setHostPreference(serverDetailsView.getHost());
		twsProjectPreferences().setOfsSourcePreference(
				serverDetailsView.getOfsSource());
		twsProjectPreferences().setPortPreference(getPort());
		twsProjectPreferences().setUserNamePreference(
				serverDetailsView.getUserName());
		twsProjectPreferences().setPasswordPreference(
				serverDetailsView.getPassword());
		twsProjectPreferences().setAgentConnectionPreference(
				serverDetailsView.isAgentConnection().toString());
		twsProjectPreferences().setComponentServiceURLPreference(
				serverDetailsView.getComponentServiceURL());
		return true;
	}

	private TWSProjectPreferences twsProjectPreferences() {
		return new TWSProjectPreferences();
	}

	/**
	 * Method which updates the agent details such as host, port, ofs with
	 * default preference if any one of those has left blank.
	 */
	private void updateAgentDetails() {
		if (serverDetailsView.getHost().length() == 0) {
			serverDetailsView.setHost(twsProjectPreferences()
					.getHostPreferenceDefault());
		}
		if (serverDetailsView.getPort().length() == 0) {
			serverDetailsView.setPort(String.valueOf(twsProjectPreferences()
					.getPortPreferenceDefault()));
		}
		if (serverDetailsView.getOfsSource().length() == 0) {
			serverDetailsView.setOfsSource(twsProjectPreferences()
					.getOfsSourcePreferenceDefault());
		}
	}
}
