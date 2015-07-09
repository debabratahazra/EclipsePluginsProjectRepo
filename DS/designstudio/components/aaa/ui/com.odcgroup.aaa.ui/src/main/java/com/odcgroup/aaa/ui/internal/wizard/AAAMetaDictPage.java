package com.odcgroup.aaa.ui.internal.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAMetaDictPage extends AbstractAAAConnectionPage {

	public AAAMetaDictPage(IOfsProject ofsProject, AAAImportFacade importFacade) {
		super("", ofsProject, importFacade);
		setTitle(Messages.getString("aaa.wizard.metadict.page.title"));
		setDescription(Messages.getString("aaa.wizard.metadict.page.description"));
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		
		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.IMPORT_META_DICTIONNARY);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.wizard.AbstractAAAConnectionPage#doProcessWithValidConnection()
	 */
	@Override
	protected void doProcessWithValidConnection() {
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.wizard.AbstractAAAConnectionPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		return null; // No 2nd page
	}

	public boolean canFlipToNextPage() {
		return false;
	}

	
}
