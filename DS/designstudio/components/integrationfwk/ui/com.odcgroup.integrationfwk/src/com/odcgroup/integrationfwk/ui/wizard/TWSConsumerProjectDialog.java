package com.odcgroup.integrationfwk.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.odcgroup.integrationfwk.ui.prefprop.TWSPropertiesGroup;
import com.odcgroup.integrationfwk.ui.prefprop.TWSPropertiesView;
import com.odcgroup.integrationfwk.ui.projects.TWSProjectPreferences;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.Utils;

public class TWSConsumerProjectDialog extends WizardPage {

	private Label lbl, lbl1;
	private Button locationOption;
	private Text projectName;
	private Text location;
	private Button browse;
	private TWSPropertiesView twsPropertiesView;

	protected TWSConsumerProjectDialog(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 5;
		layout.verticalSpacing = 15;
		composite.setLayout(layout);
		setControl(composite);

		lbl = new Label(composite, SWT.NULL);
		lbl.setText("Project Name:");

		projectName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		GridData gridDataProjectName = new GridData(GridData.FILL_HORIZONTAL);
		gridDataProjectName.horizontalSpan = 2;
		projectName.setLayoutData(gridDataProjectName);
		projectName.setFocus();
		locationOption = new Button(composite, SWT.CHECK);
		locationOption.setText("Use Default Location");
		locationOption.setSelection(true);
		GridData gridDataLocationOpt = new GridData(GridData.FILL_HORIZONTAL);
		gridDataLocationOpt.horizontalSpan = 3;
		locationOption.setLayoutData(gridDataLocationOpt);

		projectName.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
				projectNameChanged();
			}

		});

		lbl1 = new Label(composite, SWT.NULL);
		lbl1.setText("Choose Location:");
		lbl1.setEnabled(false);

		location = new Text(composite, SWT.SINGLE | SWT.BORDER);
		final String loc = ResourcesPlugin.getWorkspace().getRoot()
				.getLocationURI().toString().replace('/', '\\');
		location.setText(loc.substring(loc.indexOf('\\') + 1)); // file:/D:/runtime-EclipseApplication
		location.setEnabled(false);
		GridData gridDataLocation = new GridData(GridData.FILL_HORIZONTAL);
		location.setLayoutData(gridDataLocation);

		browse = new Button(composite, SWT.PUSH);
		browse.setText("Browse...");
		browse.setEnabled(false);

		locationOption.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				if (!locationOption.getSelection()) {
					if (!locationOption.getGrayed()) {
						locationOption.setGrayed(true);
						location.setText("");
						location.setEnabled(true);
						lbl1.setEnabled(true);
						browse.setEnabled(true);
					}
				} else {
					if (locationOption.getGrayed()) {
						locationOption.setGrayed(false);
						// locationOption.setSelection(true);
						location.setText(loc.substring(loc.indexOf('\\') + 1));
						location.setEnabled(false);
						browse.setEnabled(false);
						lbl1.setEnabled(false);
					}
				}

			}
		});

		browse.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				DirectoryDialog dlg = new DirectoryDialog(getShell());
				dlg.setText("Browse For Folder");
				dlg.setMessage("Select the location directory");
				String dir = dlg.open();
				if (dir != null) {
					location.setText(dir);
				}
			}
		});

		Group twsPropertiesGroup = TWSPropertiesGroup
				.createTwsPropertiesGroup(composite);

		setPropertiesView(twsPropertiesGroup);
		projectNameChanged();
	}

	public boolean getLocationOption() {
		return locationOption.getSelection();
	}

	public String getProjectLocation() {
		return location.getText();
	}

	public String getProjectName() {
		return projectName.getText();
	}

	public TWSPropertiesView getTWSCredentials() {
		return twsPropertiesView;
	}

	private boolean projectNameAlreadyExists() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName.getText());
		return project.exists();
	}

	private boolean projectNameBlank() {
		return projectName.getText().equals("");
	}

	private void projectNameChanged() {
		if (projectNameBlank()) {
			setDescription("Enter a project name.");
			setPageComplete(false);
		} else if (projectNameAlreadyExists()) {
			setDescription("Project already exists!");
			setPageComplete(false);
		} else if (Character.isDigit(getProjectName().charAt(0))) {
			setErrorMessage("Project name cannot start with number!");
			setPageComplete(false);
		} else if (!Utils.containsValidChar(getProjectName())) {
			setErrorMessage("Project name contains invalid character!");
			setPageComplete(false);
		} else {
			// clear error message
			setErrorMessage(null);
			setDescription(StringConstants._UI_WIZARD_CREATE_INTEGRATION_PROJET_IN_LOCATION);
			setPageComplete(true);
		}
	}

	private void setPropertiesView(Group twsPropertiesGroup) {
		twsPropertiesView = new TWSPropertiesView(twsPropertiesGroup);
		twsPropertiesView.setUseProjectSpecificSettings(false);
		twsPropertiesView.setHost(twsProjectPreferences().getHostPreference());
		twsPropertiesView.setOfsSource(twsProjectPreferences()
				.getOfsSourcePreference());
		twsPropertiesView.setPort(Integer.toString(twsProjectPreferences()
				.getPortPreference()));
		twsPropertiesView.setComponentServiceURL(twsProjectPreferences()
				.getComponentServiceURLPreference());
		twsPropertiesView.setUserName(twsProjectPreferences()
				.getUserNamePreference());
		twsPropertiesView.setPassword(twsProjectPreferences()
				.getPasswordPreference());
	}

	private TWSProjectPreferences twsProjectPreferences() {
		return new TWSProjectPreferences();
	}
}