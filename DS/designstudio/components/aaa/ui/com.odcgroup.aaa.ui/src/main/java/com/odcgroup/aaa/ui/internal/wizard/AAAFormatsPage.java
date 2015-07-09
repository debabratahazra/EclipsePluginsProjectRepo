package com.odcgroup.aaa.ui.internal.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.aaa.ui.Messages;
import com.odcgroup.aaa.ui.internal.model.AAAFormatCode;
import com.odcgroup.aaa.ui.internal.model.AAAFormatType;
import com.odcgroup.aaa.ui.internal.model.AAAFunction;
import com.odcgroup.aaa.ui.internal.model.AAAImportFacade;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * @author atr
 * @since DS 1.40.0
 */
public class AAAFormatsPage extends AbstractAAAConnectionPage {

	/**
	 * @param pageName
	 * @param importFacade
	 */
	public AAAFormatsPage(IOfsModelPackage ofsPackage, AAAImportFacade importFacade) {
		super("", ofsPackage, importFacade);
		setTitle(Messages.getString("aaa.wizard.formats.page.title"));
		setDescription(Messages.getString("aaa.wizard.formats.page.description"));
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		
		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent.getShell(), IOfsHelpContextIds.IMPORT_TA_FORMAT);
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.mdf.aaa.integration.ui.wizard.AbstractAAAConnectionPage#doProcessWithValidConnection()
	 */
	@Override
	protected void doProcessWithValidConnection() throws InvocationTargetException, InterruptedException {
		final List<AAAFormatType> formatTypes = new ArrayList<AAAFormatType>();
		final List<AAAFormatCode> formatCodes = new ArrayList<AAAFormatCode>();
		final List<AAAFunction> functions = new ArrayList<AAAFunction>();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask(Messages.getString("aaa.wizard.formats.page.loading"), 3);

				// Initialize the Format Type list for the format selection page
				monitor.setTaskName(Messages.getString("aaa.wizard.formats.page.loadingFormatTypes"));
				formatTypes.addAll(getImportFacade().getFormatTypes());
				monitor.worked(1);
				if(monitor.isCanceled()) throw new InterruptedException();
				
				// Get all types
				monitor.setTaskName(Messages.getString("aaa.wizard.formats.page.loadingFormatCodes"));
				formatCodes.addAll(getImportFacade().getFormatCodes());						
				monitor.worked(1);
				if(monitor.isCanceled()) throw new InterruptedException();
				
				// Get all types
				monitor.setTaskName(Messages.getString("aaa.wizard.formats.page.loadingFunctions"));
				functions.addAll(getImportFacade().getFunctions());
				monitor.worked(1);
				if(monitor.isCanceled()) throw new InterruptedException();
			}
		};
		getContainer().run(true, true, op);

		// Initialize the Format Type list for the format selection page
		((AAAImportFormatsWizard)getWizard()).getFormatsSelectionPage().initializeFormatTypes(formatTypes);
		
		// Fill page with types
		((AAAImportFormatsWizard)getWizard()).getFormatsSelectionPage().initializeFormatCodes(formatCodes);

		// Fill combo with the available functions
		((AAAImportFormatsWizard)getWizard()).getFormatsSelectionPage().initializeFunctions(functions);
		
	}

	@Override
	protected void dialogChanged() {
		super.dialogChanged();
		getImportFacade().resetFormatCodePattern();
	}
}
