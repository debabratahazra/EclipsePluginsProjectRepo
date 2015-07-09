package com.odcgroup.t24.localref.application.importer.ui.wizard;

import java.util.Collections;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.application.localrefapplication.ILocalRefApplicationSelector;
import com.odcgroup.t24.localref.application.importer.ui.Messages;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

public class LocalRefApplicationSelectionPage extends WizardPage implements
		Listener {
	private static final Logger logger = LoggerFactory.getLogger(LocalRefApplicationSelectionPage.class);

	private ILocalRefApplicationSelector selector;

	private Text localRefApp;
	
	private LocalRefApplicationSelectionGroup vsg;

	private void initialize() {
		localRefApp.setText(selector.getFilter().getName());
		vsg.setAllChecked(true);
	}
	
	@SuppressWarnings("unchecked")
	private void updateLocalRefSelectionGroup() {
		try {
			vsg.setInput(selector.getFilteredModels());
			vsg.updateCountLabel();
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
		if (localRefApp.getText().length() == 0) {
			setErrorMessage(Messages.LocalRefApplicationSelectionPage_errorLocalRefName);
			return;
		}		
	}
	
	protected void handleLocalRefChange(ModifyEvent e) {
		selector.getFilter().setName(localRefApp.getText());
        updateLocalRefSelectionGroup();
        canFlipToNextPage();
	}

	@Override
	public boolean canFlipToNextPage() {
		if(selector.getSelectedModels().isEmpty()) {
			return false;
		}
		return true;
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
		selectionGroup.setLayout(new GridLayout(2,false));
		selectionGroup.setLayoutData(new GridData(gridDataStyle));
		selectionGroup.setText(Messages.LocalRefApplicationSelectionPage_selectionGroupLabel);
		
		Label label = null;
		
		//  localref name (pattern)
		label = new Label(selectionGroup, SWT.NULL);
		label.setText(Messages.LocalRefApplicationSelectionPage_localRefLabel);
		localRefApp = new Text(selectionGroup, SWT.BORDER | SWT.SINGLE);
		localRefApp.setLayoutData(fieldGridData);
		localRefApp.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
            	handleLocalRefChange(e);
            }
        });
		
		vsg = new LocalRefApplicationSelectionGroup(topLevel, this, selector, ""); //$NON-NLS-1$

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
	public LocalRefApplicationSelectionPage(ILocalRefApplicationSelector selector) {
		super(""); //$NON-NLS-1$
		setTitle(Messages.LocalRefApplicationSelectionPage_title);
		setDescription(Messages.LocalRefApplicationSelectionPage_description);
		this.selector = selector;
		setPageComplete(false);
	}
}
