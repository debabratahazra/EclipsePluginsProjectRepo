package com.odcgroup.t24.version.importer.ui.wizard;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.version.importer.IVersionSelector;
import com.odcgroup.t24.version.importer.ui.Messages;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class VersionSelectionPage extends WizardPage implements Listener {
	private static final Logger logger = LoggerFactory.getLogger(VersionSelectionPage.class);

	private static String ALL_CHOICES = Messages.VersionSelectionPage_choiceAll;

	private IVersionSelector selector;

	private Text version;

	private Combo application;
	private Combo component;
	private Combo module;
	private Combo description;

	private VersionSelectionGroup vsg;

	private void initialize() {
		version.setText(selector.getFilter().getName());

		setComboContent(application, selector.getApplications());
		application.select(0);

		setComboContent(module, selector.getModules());
		module.select(0);

		setComboContent(component, selector.getComponents());
		component.select(0);

		setComboContent(description, selector.getDescriptions());
		description.select(0);
		if (selector.getSelectedModels().size() > 0 && vsg.getTableViewer() != null) {
			vsg.getTableViewer().setCheckedElements(selector.getSelectedModels().toArray());
		} else {
			vsg.setAllChecked(false);
		}
	}

	private void setComboContent(Combo combo, List<String> values) {
		combo.removeAll();
		combo.add(ALL_CHOICES);
		Collections.sort(values);
		for (String value : values) {
			combo.add(value);
		}
	}

	@SuppressWarnings("unchecked")
	private void updateVersionSelectionGroup() {
		try {
			vsg.setInput(selector.getFilteredModels());
		} catch (Exception ex) {
			// intentionally caught Exception and not only T24ServerException,
			// so that any unexpected RuntimeException which does happen is
			// caught by UI as well
			logger.error("getFilteredModels() failed", ex);
			vsg.setInput(Collections.EMPTY_LIST);
			setMessage(ex.getMessage(), ERROR);
		}

	}

	private void validateControls() {
		setPageComplete(!selector.getSelectedModels().isEmpty());
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}

	/**
	 * Ensure required informations in here.
	 */
	protected void dialogChanged() {
		if (version.getText().length() == 0) {
			setErrorMessage(Messages.VersionSelectionPage_errorVersionName);
			return;
		}
	}

	protected void handleApplicationChange(ModifyEvent e) {
		String choice = application.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setApplication(choice);
		updateVersionSelectionGroup();
	}

	protected void handleComponentChange(ModifyEvent e) {
		String choice = component.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setComponent(choice);
		updateVersionSelectionGroup();
	}

	protected void handleModuleChange(ModifyEvent e) {
		String choice = module.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setModule(choice);
		updateVersionSelectionGroup();
	}

	protected void handleVersionChange(ModifyEvent e) {
		selector.getFilter().setName(version.getText());
		updateVersionSelectionGroup();
	}

	private void handleDescriptionChange(ModifyEvent e) {
		String choice = description.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setDescription(choice);
		updateVersionSelectionGroup();

	}

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}

	@Override
	public void createControl(Composite parent) {

		final int gridDataStyle = GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL;
		final GridData fieldGridData = new GridData(gridDataStyle);
		final GridData lblGridData = new GridData();
		lblGridData.widthHint = 120;

		// create page content
		Composite topLevel = new Composite(parent, SWT.NULL);
		topLevel.setLayout(new GridLayout(1, true));

		Group selectionGroup = new Group(topLevel, SWT.NULL);
		selectionGroup.setLayout(new GridLayout(2, false));
		selectionGroup.setLayoutData(new GridData(gridDataStyle));
		selectionGroup.setText(Messages.VersionSelectionPage_selectionGroupLabel);

		Label label = null;

		// application name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.VersionSelectionPage_applicationLabel);
		application = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		application.setLayoutData(fieldGridData);
		application.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleApplicationChange(e);
			}
		});

		// version name (pattern)
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.VersionSelectionPage_versionLabel);
		version = new Text(selectionGroup, SWT.BORDER | SWT.SINGLE);
		version.setLayoutData(fieldGridData);
		version.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleVersionChange(e);
			}
		});

		// module name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.VersionSelectionPage_moduleLabel);
		module = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		module.setLayoutData(fieldGridData);
		module.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleModuleChange(e);
			}
		});

		// component name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.VersionSelectionPage_componentLabel);
		component = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		component.setLayoutData(fieldGridData);
		component.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleComponentChange(e);
			}
		});

		// component name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.VersionSelectionGroup_descriptionLabel);
		description = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		description.setLayoutData(fieldGridData);
		description.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleDescriptionChange(e);
			}

		});
		vsg = new VersionSelectionGroup(topLevel, this, selector, ""); //$NON-NLS-1$

		// Set the control for the receiver.
		setControl(topLevel);

		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.CONTEXT_PREFIX);
	}

	@Override
	public void handleEvent(Event event) {
		validateControls();
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		// if (importer.isServerChanged()) {
		if (b) {
			initialize();
		}
		// importer.setServerChanged(false);
		// }
	}

	/**
	 * @param importer
	 */
	public VersionSelectionPage(IVersionSelector selector) {
		super(""); //$NON-NLS-1$
		setTitle(Messages.VersionSelectionPage_title);
		setDescription(Messages.VersionSelectionPage_description);
		this.selector = selector;
		setPageComplete(false);
	}

}