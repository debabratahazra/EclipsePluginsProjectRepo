package com.odcgroup.t24.enquiry.importer.ui.wizard;

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

import com.odcgroup.t24.enquiry.importer.IEnquirySelector;
import com.odcgroup.t24.enquiry.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class EnquirySelectionPage extends WizardPage implements Listener {
	private static final Logger logger = LoggerFactory.getLogger(EnquirySelectionPage.class);

	private static String ALL_CHOICES = Messages.EnquirySelectionPage_choiceAll;

	private IEnquirySelector selector;

	private Text enquiry;

	private Combo component;
	private Combo module;

	private EnquirySelectionGroup vsg;

	private void initialize() {
		enquiry.setText(selector.getFilter().getName());

		setComboContent(module, selector.getModules());
		module.select(0);

		setComboContent(component, selector.getComponents());
		component.select(0);

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
	private void updateEnquirySelectionGroup() {
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
		if (enquiry.getText().length() == 0) {
			setErrorMessage(Messages.EnquirySelectionPage_errorEnquiryName);
			return;
		}
	}

	protected void handleComponentChange(ModifyEvent e) {
		String choice = component.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setComponent(choice);
		updateEnquirySelectionGroup();
	}

	protected void handleModuleChange(ModifyEvent e) {
		String choice = module.getText();
		if (ALL_CHOICES.equals(choice)) {
			choice = null;
		}
		selector.getFilter().setModule(choice);
		updateEnquirySelectionGroup();
	}


	protected void handleEnquiryChange(ModifyEvent e) {
		selector.getFilter().setName(enquiry.getText());
		updateEnquirySelectionGroup();
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
		selectionGroup.setText(Messages.EnquirySelectionPage_selectionGroupLabel);

		Label label = null;

		// enquiry name (pattern)
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.EnquirySelectionPage_enquiryLabel);
		enquiry = new Text(selectionGroup, SWT.BORDER | SWT.SINGLE);
		enquiry.setLayoutData(fieldGridData);
		enquiry.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleEnquiryChange(e);
			}
		});

		// module name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.EnquirySelectionPage_moduleLabel);
		module = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		module.setLayoutData(fieldGridData);
		module.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleModuleChange(e);
			}
		});

		// component name
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.EnquirySelectionPage_componentLabel);
		component = new Combo(selectionGroup, SWT.BORDER | SWT.SINGLE);
		component.setLayoutData(fieldGridData);
		component.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				handleComponentChange(e);
			}
		});

		vsg = new EnquirySelectionGroup(topLevel, this, selector, ""); //$NON-NLS-1$

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
		if (b) {
			initialize();
		}
	}

	/**
	 * @param importer
	 */
	public EnquirySelectionPage(IEnquirySelector selector) {
		super(""); //$NON-NLS-1$
		setTitle(Messages.EnquirySelectionPage_title);
		setDescription(Messages.EnquirySelectionPage_description);
		this.selector = selector;
		setPageComplete(false);
	}
}