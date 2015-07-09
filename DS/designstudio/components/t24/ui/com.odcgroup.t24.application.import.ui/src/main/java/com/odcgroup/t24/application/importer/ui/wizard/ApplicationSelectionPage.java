package com.odcgroup.t24.application.importer.ui.wizard;

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

import com.odcgroup.t24.application.importer.IApplicationSelector;
import com.odcgroup.t24.application.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class ApplicationSelectionPage extends WizardPage implements Listener {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationSelectionPage.class);

	private static String ALL_CHOICES = Messages.ApplicationSelectionPage_choiceAll;

	private IApplicationSelector selector;

	private Text application;

	private Combo component;
	private Combo product;
	private Combo description;

	private ApplicationSelectionGroup vsg;

	private void initialize() {
		application.setText(selector.getFilter().getName());

		setComboContent(product, selector.getProducts());
		product.select(0);

		setComboContent(component, selector.getComponents());
		component.select(0);

		setComboContent(description, selector.getAppDescriptions());
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
	private void updateApplicationSelectionGroup() {
		try {
			vsg.setInput(selector.getFilteredModels());
		} catch (T24ServerException ex) {
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
		if (application.getText().length() == 0) {
			setErrorMessage(Messages.ApplicationSelectionPage_errorApplicationName);
			return;
		}
	}

	protected void handleComponentChange(ModifyEvent e) {
		String choice = component.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setComponent(choice);
		updateApplicationSelectionGroup();
	}

	protected void handleModuleChange(ModifyEvent e) {
		String choice = product.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setProduct(choice);
		updateApplicationSelectionGroup();
	}

	protected void handleApplicationChange(ModifyEvent e) {
		selector.getFilter().setName(application.getText());
		updateApplicationSelectionGroup();
	}

	private void handleDescriptionChange(ModifyEvent e) {
		String choice = description.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setDescription(choice);
		updateApplicationSelectionGroup();

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
		selectionGroup.setText(Messages.ApplicationSelectionPage_selectionGroupLabel);

		Label label = null;

		// application name (pattern)
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.ApplicationSelectionPage_applicationLabel);
		application = new Text(selectionGroup, SWT.BORDER | SWT.SINGLE);
		application.setLayoutData(fieldGridData);
		application.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleApplicationChange(e);
			}
		});

		// product name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.ApplicationSelectionPage_productLabel);
		product = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		product.setLayoutData(fieldGridData);
		product.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleModuleChange(e);
			}
		});

		// component name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.ApplicationSelectionPage_componentLabel);
		component = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		component.setLayoutData(fieldGridData);
		component.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleComponentChange(e);
			}
		});

		// description name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.ApplicationSelectionPage_descriptionLabel);
		description = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		description.setLayoutData(fieldGridData);
		description.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleDescriptionChange(e);
			}
		});
		vsg = new ApplicationSelectionGroup(topLevel, this, selector, ""); //$NON-NLS-1$

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
	public ApplicationSelectionPage(IApplicationSelector selector) {
		super(""); //$NON-NLS-1$
		setTitle(Messages.ApplicationSelectionPage_title);
		setDescription(Messages.ApplicationSelectionPage_description);
		this.selector = selector;
		setPageComplete(false);
	}

}