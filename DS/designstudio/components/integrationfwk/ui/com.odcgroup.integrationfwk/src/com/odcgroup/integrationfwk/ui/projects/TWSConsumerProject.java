package com.odcgroup.integrationfwk.ui.projects;

import java.io.File;
import java.io.Serializable;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.prefprop.TWSPreferenceConstants;
import com.odcgroup.integrationfwk.ui.views.TWSConsumerProjectProperties;

public class TWSConsumerProject implements Serializable, IWorkbenchAdapter,
		IAdaptable {

	private IProject project;
	// public String getCustomName() {
	// return customName;
	// }

	private TWSProjectPreferences twsProjectPreferences;

	/*
	 * This constructor is invoked when the new project is created when the
	 * workbench is loaded
	 */
	public TWSConsumerProject(IProject project) {

		super();
		this.project = project;
		// decide what type of project it is
		twsProjectPreferences = new TWSProjectPreferences();
		// BackgroundThread bt = new BackgroundThread();
		// new Thread(bt).start();
	}

	public TWSConsumerProject(IProject project, String dummy) {

		super();
		this.project = project;
		// decide what type of project it is
		twsProjectPreferences = new TWSProjectPreferences();

	}

	public TWSConsumerProject(IProject project,
			TWSProjectPreferences twsProjectPreferences) {

		super();
		this.project = project;
		this.twsProjectPreferences = twsProjectPreferences;
		System.out.println("finsihed TWSConsumerProject");
	}

	public TWSConsumerProject(String name) {
		super();
		// customName = name;
		twsProjectPreferences = new TWSProjectPreferences();
		project = null;
		System.out.println("finsihed TWSConsumerProject with the param name");
	}

	public Object getAdapter(Class adapter) {
		if (adapter == IWorkbenchAdapter.class) {
			return this;
		}
		if (adapter == IPropertySource.class) {
			return new TWSConsumerProjectProperties(this);
		}
		return null;
	}

	// added because I implemented the IworkbenchPart, IAdaptable
	// see how i can later remove this
	public Object[] getChildren(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Helps to get the component service url from project properties.
	 * 
	 * @return respective property if available, saved global preference
	 *         otherwise.
	 */
	public String getComponentServiceURL() {
		if (getUseProjectSpecificSettings()) {
			String savedProperty = getProperty(TWSPreferenceConstants.COMPONENTSERVICE_URL);
			if (savedProperty == null || savedProperty.equalsIgnoreCase("")) {
				return twsProjectPreferences.getComponentServiceURLPreference();
			} else {
				return savedProperty;
			}
		}
		return twsProjectPreferences.getComponentServiceURLPreference();
	}

	public String getHost() {
		if (getUseProjectSpecificSettings()) {
			String prop = getProperty(TWSPreferenceConstants.HOST);
			if (prop.equals("")) {
				return twsProjectPreferences.getHostPreference();
			} else {
				return prop;
			}
		} else {
			return twsProjectPreferences.getHostPreference();
		}
	}

	public ImageDescriptor getImageDescriptor(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLabel(Object arg0) {
		// TODO Auto-generated method stub
		return "dummy";
		// return customName;
	}

	private String getLibPathString() {
		return getPathString() + File.separatorChar + "lib";

	}

	@Deprecated
	public String getMetaInfPathString() {
		return getPathString() + File.separator + "ejbModule" + File.separator
				+ "META-INF";
	}

	public String getOfsSource() {
		if (getUseProjectSpecificSettings()) {
			String prop = getProperty(TWSPreferenceConstants.OFS_SOURCE);
			if (prop.equals("")) {
				return twsProjectPreferences.getOfsSourcePreference();
			} else {
				return prop;
			}
		} else {
			return twsProjectPreferences.getOfsSourcePreference();
		}
	}

	public Object getParent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		if (getUseProjectSpecificSettings()) {
			String prop = getProperty(TWSPreferenceConstants.PASSWORD);
			if (prop == null) {
				return "";
			}
			if (prop.equals("")) {
				return twsProjectPreferences.getPasswordPreference();
			} else {
				return prop;
			}
		} else {
			return twsProjectPreferences.getPasswordPreference();
		}
	}

	public String getPathString() {
		return project.getLocation().toOSString();
	}

	public int getPort() {
		if (getUseProjectSpecificSettings()) {
			String prop = getProperty(TWSPreferenceConstants.PORT);
			if (prop.equals("")) {
				return twsProjectPreferences.getPortPreference();
			} else {
				return Integer.parseInt(prop);
			}
		} else {
			return twsProjectPreferences.getPortPreference();
		}
	}

	public IProject getProject() {
		return project;
	}

	public String getProperty(String propertyName) {
		String propertyValue = "";
		try {
			propertyValue = project.getPersistentProperty(new QualifiedName(
					IntegrationToolingActivator.PLUGIN_ID, propertyName));
		} catch (CoreException e) {

			TWSConsumerLog.logError(e);
		}
		return propertyValue;
	}

	/**
	 * Helps to get the preference 'always save resource before publishing it'
	 * 
	 * @return true if resource is always to be saved before publishing, false
	 *         otherwise.
	 */
	public boolean getResourcesToBeAlwaysSaveBeforePublishing() {
		String savedProperty = getProperty(TWSPreferenceConstants.SAVE_RESOURCE_BEFORE_PUBLISH);
		if (savedProperty == null || savedProperty.length() == 0) {
			return false;
		} else {
			return Boolean.parseBoolean(savedProperty);
		}
	}

	public String getT24CredentialsFilePath() {
		return getPathString() + File.separatorChar
				+ "t24Credentials.properties";
	}

	// candidate for moving to util directory
	public String getT24ServicesFilePath() {
		return getPathString() + File.separatorChar + "t24Services.xml";
	}

	public String getTWSProjectName() {
		return project.getName();
	}

	public TWSProjectPreferences getTwsProjectPreferences() {
		return twsProjectPreferences;
	}

	public boolean getUseProjectSpecificSettings() {
		return Boolean
				.parseBoolean(getProperty(TWSPreferenceConstants.USE_PROJECT_SPECIFIC_SETTINGS));
	}

	public String getUserName() {
		if (getUseProjectSpecificSettings()) {
			String prop = getProperty(TWSPreferenceConstants.USER_NAME);
			if (prop == null) {
				return "";
			}
			if (prop.equals("")) {
				return twsProjectPreferences.getUserNamePreference();
			} else {
				return prop;
			}
		} else {
			return twsProjectPreferences.getUserNamePreference();
		}
	}

	public String getVersionFilePath() {
		return getPathString() + File.separatorChar + "version.properties";
	}

	/**
	 * return true if the type of connection is agent, false otherwise.
	 * 
	 * @return
	 */
	public Boolean isAgentConnection() {

		if (getUseProjectSpecificSettings()) {
			String savedProperty = getProperty(TWSPreferenceConstants.AGENT_CONNECTION_TYPE);
			if (savedProperty == null) {
				return false;
			}
			if (savedProperty.equals("")) {
				return new Boolean(
						twsProjectPreferences.getAgentConnectionPreference());
			} else {
				return new Boolean(savedProperty);
			}
		}
		return new Boolean(twsProjectPreferences.getAgentConnectionPreference());

	}

	/**
	 * set the given boolean value into agent connection preference.
	 * 
	 * @param isAgentConnection
	 *            - true if agent connection, false otherwise.
	 */
	public void setAgentConnection(boolean isAgentConnection) {
		setProperty(TWSPreferenceConstants.AGENT_CONNECTION_TYPE,
				Boolean.toString(isAgentConnection));
	}

	/**
	 * Helps to set the given value into preference 'always save resource before
	 * publishing'.
	 * 
	 * @param resourceToBeAlwaysSaveBeforePublishing
	 */
	public void setAlwaySaveResourceBeforePublishing(
			boolean resourceToBeAlwaysSaveBeforePublishing) {
		setProperty(TWSPreferenceConstants.SAVE_RESOURCE_BEFORE_PUBLISH,
				Boolean.toString(resourceToBeAlwaysSaveBeforePublishing));
	}

	/**
	 * Helps to set the given component service url into project properties.
	 * 
	 * @param componentServiceURL
	 */
	public void setComponentServiceURL(String componentServiceURL) {
		setProperty(TWSPreferenceConstants.COMPONENTSERVICE_URL,
				componentServiceURL);
	}

	public void setHost(String host) {
		setProperty(TWSPreferenceConstants.HOST, host);
	}

	public void setOfsSource(String ofsSource) {
		setProperty(TWSPreferenceConstants.OFS_SOURCE, ofsSource);
	}

	public void setPassword(String password) {
		setProperty(TWSPreferenceConstants.PASSWORD, password);
	}

	public void setPort(int port) {
		setProperty(TWSPreferenceConstants.PORT, Integer.toString(port));
	}

	public void setProject(IProject project) {
		this.project = project;
		twsProjectPreferences = new TWSProjectPreferences();
	}

	public void setProperty(String propertyName, String propertyValue) {
		QualifiedName qualifiedName = new QualifiedName(IntegrationToolingActivator.PLUGIN_ID,
				propertyName);
		try {
			project.setPersistentProperty(qualifiedName, propertyValue);
		} catch (CoreException e) {
			TWSConsumerLog.logError(e);
		}
	}

	public void setTwsProjectPreferences(
			TWSProjectPreferences twsProjectPreferences) {
		this.twsProjectPreferences = twsProjectPreferences;
	}

	public void setUseProjectSpecificSettings(boolean useProjectSpecificSettings) {
		setProperty(TWSPreferenceConstants.USE_PROJECT_SPECIFIC_SETTINGS,
				Boolean.toString(useProjectSpecificSettings));
	}

	public void setUserName(String userName) {
		setProperty(TWSPreferenceConstants.USER_NAME, userName);
	}

}
