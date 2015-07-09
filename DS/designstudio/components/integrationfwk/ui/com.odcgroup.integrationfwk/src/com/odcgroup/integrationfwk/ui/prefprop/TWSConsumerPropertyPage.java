package com.odcgroup.integrationfwk.ui.prefprop;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;

import com.odcgroup.integrationfwk.ui.common.MasterDataBuilder;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.projects.TWSProjectPreferences;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

//This need to be moved over..

public class TWSConsumerPropertyPage extends PropertyPage {

	// notice same for all plugins
	private TWSPropertiesView twsPropertiesView;
	/**
	 * flag to tell whether the master data builder has started their job
	 * already or not
	 */
	private boolean isMasterDataBuildingStartedAlready;

	public TWSConsumerPropertyPage() {
		super();
	}

	private void addTwsPropertiesView(Composite parent) {

		twsPropertiesView = new TWSPropertiesView(parent);
		twsPropertiesView.setUseProjectSpecificSettings(twsConsumerProject()
				.getUseProjectSpecificSettings());
		twsPropertiesView.setHost(twsConsumerProject().getHost());
		twsPropertiesView.setOfsSource(twsConsumerProject().getOfsSource());
		twsPropertiesView.setPort(Integer.toString(twsConsumerProject()
				.getPort()));
		twsPropertiesView.setConnectionType(twsConsumerProject()
				.isAgentConnection());
		// ensure that accessibility of components
		twsPropertiesView.setUseProjectSpecificSettings(true);
		twsPropertiesView.setComponentServiceURL(twsConsumerProject()
				.getComponentServiceURL());
		twsPropertiesView.setUserName(twsConsumerProject().getUserName());
		twsPropertiesView.setPassword(twsConsumerProject().getPassword());

	}

	@Override
	protected Control createContents(Composite parent) {
		addTwsPropertiesView(parent);
		return parent;
	}

	/**
	 * 
	 * @return port details from properties view if valid one, default
	 *         preference otherwise
	 */
	private int getPort() {
		try {
			return Integer.parseInt(twsPropertiesView.getPort());
		} catch (NumberFormatException ex) {
			return twsProjectPreferences().getPortPreference();
		}
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
		if (!twsPropertiesView.getAgentConnection()) {
			return true;
		}
		return TWSConsumerProjectUtil.isValidAgentDetails(
				twsPropertiesView.getHost(), twsPropertiesView.getPort(),
				twsPropertiesView.getOfsSource());
	}

	@Override
	protected void performApply() {
		super.performApply();
	}

	@Override
	protected void performDefaults() {
		twsPropertiesView.setUseProjectSpecificSettings(false);
		twsPropertiesView.setHost(twsProjectPreferences().getHostPreference());
		twsPropertiesView.setOfsSource(twsProjectPreferences()
				.getOfsSourcePreference());
		twsPropertiesView.setPort(Integer.toString(twsProjectPreferences()
				.getPortPreference()));
		twsPropertiesView.setConnectionType(new Boolean(twsProjectPreferences()
				.getAgentConnectionPreferenceDefault()));
		twsPropertiesView.setComponentServiceURL(twsProjectPreferences()
				.getComponentServiceURLPreference());
		twsPropertiesView.setUserName(twsProjectPreferences()
				.getUserNamePreference());
		twsPropertiesView.setPassword(twsProjectPreferences()
				.getPasswordPreference());
		twsPropertiesView.setAllUIComponentsDisabled();
	}

	@Override
	public boolean performOk() {
		if (!isValidToProceed()) {
			return false;
		}
		updateAgentDetails();
		twsConsumerProject().setUseProjectSpecificSettings(
				twsPropertiesView.getUseProjectSpecificSettings());
		twsConsumerProject().setHost(twsPropertiesView.getHost());
		twsConsumerProject().setOfsSource(twsPropertiesView.getOfsSource());
		twsConsumerProject().setPort(getPort());
		twsConsumerProject().setUserName(twsPropertiesView.getUserName());
		twsConsumerProject().setPassword(twsPropertiesView.getPassword());
		twsConsumerProject().setAgentConnection(
				twsPropertiesView.getAgentConnection());
		twsConsumerProject().setComponentServiceURL(
				twsPropertiesView.getComponentServiceURL());
		// connection validation has been done and the master data fetching job
		// initiated already. This is happen when the user is trying press the
		// Ok button after pressed the Apply button.
		if (isMasterDataBuildingStartedAlready) {
			return true;
		}
		if (TWSConsumerProjectUtil.isValidConnection(twsConsumerProject())) {
			MasterDataBuilder.startBuilding(twsConsumerProject());
			isMasterDataBuildingStartedAlready = true;
			return true;
		} else {
			return false;
		}
	}

	private TWSConsumerProject twsConsumerProject() {
		IResource resource = (IResource) getElement().getAdapter(
				IResource.class);
		IProject project = (IProject) resource;
		TWSConsumerProject twsProject = new TWSConsumerProject(project);
		return twsProject;
	}

	private TWSProjectPreferences twsProjectPreferences() {
		return new TWSProjectPreferences();
	}

	/**
	 * Method which updates the agent details such as host, port, ofs with
	 * default preference if any one of those has left blank.
	 */
	private void updateAgentDetails() {
		if (twsPropertiesView.getHost().length() == 0) {
			twsPropertiesView.setHost(twsProjectPreferences()
					.getHostPreference());
		}
		if (twsPropertiesView.getPort().length() == 0) {
			twsPropertiesView.setPort(String.valueOf(twsProjectPreferences()
					.getPortPreference()));
		}
		if (twsPropertiesView.getOfsSource().length() == 0) {
			twsPropertiesView.setOfsSource(twsProjectPreferences()
					.getOfsSourcePreference());
		}
	}
}