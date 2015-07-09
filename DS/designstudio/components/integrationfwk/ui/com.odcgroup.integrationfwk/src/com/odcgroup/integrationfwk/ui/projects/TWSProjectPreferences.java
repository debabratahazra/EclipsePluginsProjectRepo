package com.odcgroup.integrationfwk.ui.projects;

import java.io.Serializable;

import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.prefprop.TWSPreferenceConstants;

public class TWSProjectPreferences implements Serializable {

	private static final int DEFAULT_PORT = 20002;
	private static final String DEFAULT_HOST = "localhost";
	private static final String DEFAULT_OFS_SOURCE = "GCS";
	/** default T24 component framework url preference */
	private final String DEFAULT_COMPONENT_FRAMEWORK_SERVICE_URL = "http://localhost:8080/axis2/";
	/** default T24 sign-on user name preference */
	private final String DEFAULT_USER_NAME = "SUSER1";
	/** default T24 sign-on password preference */
	private final String DEFAULT_PASSWORD = "123456";

	transient private final IPreferenceStore preferenceStore;

	public TWSProjectPreferences() {

		preferenceStore = IntegrationToolingActivator.getDefault().getPreferenceStore();
	}

	public TWSProjectPreferences(IPreferenceStore preferenceStore) {
		this.preferenceStore = preferenceStore;
	}

	/**
	 * return the agent connection preference
	 * 
	 * @return
	 */
	public String getAgentConnectionPreference() {
		String preference = getPreference(TWSPreferenceConstants.AGENT_CONNECTION_TYPE);
		if (preference != null && preference != "") {
			return preference;
		} else {
			return getAgentConnectionPreferenceDefault();
		}
	}

	/**
	 * return the default agent connection preference.
	 * 
	 * @return
	 */
	public String getAgentConnectionPreferenceDefault() {
		return Boolean.toString(Boolean.TRUE);
	}

	/**
	 * Helps to get the saved value for component service url preference.
	 * 
	 * @return
	 */
	public String getComponentServiceURLPreference() {
		String preference = getPreference(TWSPreferenceConstants.COMPONENTSERVICE_URL);
		if (preference != null && preference != "") {
			return preference;
		} else {
			return DEFAULT_COMPONENT_FRAMEWORK_SERVICE_URL;
		}
	}

	public String getHostPreference() {
		String pref = getPreference(TWSPreferenceConstants.HOST);
		if (pref != null && pref != "") {
			return pref;
		} else {
			return getHostPreferenceDefault();
		}
	}

	public String getHostPreferenceDefault() {
		return DEFAULT_HOST;
	}

	public String getOfsSourcePreference() {

		String pref = getPreference(TWSPreferenceConstants.OFS_SOURCE);
		if (pref != null && pref != "") {
			return pref;
		} else {
			return getOfsSourcePreferenceDefault();
		}
	}

	public String getOfsSourcePreferenceDefault() {
		return DEFAULT_OFS_SOURCE;
	}

	public String getPasswordPreference() {
		String pref = getPreference(TWSPreferenceConstants.PASSWORD);
		if (pref != null && pref.length() != 0) {
			return pref;
		}
		return DEFAULT_PASSWORD;
	}

	public int getPortPreference() {
		String pref = getPreference(TWSPreferenceConstants.PORT);
		if (pref != null && pref != "") {
			return Integer.parseInt(getPreference(TWSPreferenceConstants.PORT));
		} else {
			return getPortPreferenceDefault();
		}
	}

	public int getPortPreferenceDefault() {
		return DEFAULT_PORT;
	}

	private String getPreference(String preferenceName) {
		return preferenceStore.getString(preferenceName);
	}

	public String getUserNamePreference() {
		String pref = getPreference(TWSPreferenceConstants.USER_NAME);
		if (pref != null && pref.length() != 0) {
			return pref;
		}
		return DEFAULT_USER_NAME;
	}

	public void setAgentConnectionPreference(String agentConnection) {
		setPreference(TWSPreferenceConstants.AGENT_CONNECTION_TYPE,
				agentConnection);
	}

	/**
	 * save the given url into preference store.
	 * 
	 * @param componentServiceURL
	 */
	public void setComponentServiceURLPreference(String componentServiceURL) {
		setPreference(TWSPreferenceConstants.COMPONENTSERVICE_URL,
				componentServiceURL);
	}

	public void setHostPreference(String host) {
		setPreference(TWSPreferenceConstants.HOST, host);
	}

	public void setOfsSourcePreference(String ofsSource) {
		setPreference(TWSPreferenceConstants.OFS_SOURCE, ofsSource);
	}

	public void setPasswordPreference(String password) {
		setPreference(TWSPreferenceConstants.PASSWORD, password);
	}

	public void setPortPreference(int port) {
		setPreference(TWSPreferenceConstants.PORT, Integer.toString(port));
	}

	private void setPreference(String preferenceName, String preferenceValue) {
		preferenceStore.setValue(preferenceName, preferenceValue);
	}

	public void setUserNamePreference(String userName) {
		setPreference(TWSPreferenceConstants.USER_NAME, userName);
	}
}
