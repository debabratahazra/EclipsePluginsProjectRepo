package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.mdf.editor.ui.dialogs.NewDomainCreationWizard;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.resources.OfsModelResource;
import com.odcgroup.workbench.ui.action.edit.IOfsModelResourcePaster;
import com.odcgroup.workbench.ui.action.edit.PasteOfsElementsAction;
import com.odcgroup.workbench.ui.dnd.OfsElementTransfer;
/**
 * Paste Action for the Domain model copy&paste .
 * @author snn
 *
 */
public class PasteDomainModelAction extends PasteOfsElementsAction {

	private Clipboard clipboard=null;
	/**
	 * PasteDomainModelAction constructor.
	 * @param site
	 * @param clipboard
	 */
	public PasteDomainModelAction(ICommonViewerSite site, Clipboard clipboard) {
		super(site,clipboard);
		this.clipboard=clipboard;
	}
   /**
    * run method for paste action.open the wizard creation/paste wizard.
    */
	public void run(IStructuredSelection selection) {
	
		// get the clipboard content
		final IOfsElement[][] clipboardData = new IOfsElement[1][];
		getSite().getShell().getDisplay().syncExec(new Runnable() {
		public void run() {
			   OfsElementTransfer ofsTransfer = OfsElementTransfer.getInstance();
				clipboardData[0] = (IOfsElement[]) clipboard.getContents(ofsTransfer);
					}
				});
				IOfsElement[] ofsElements = clipboardData[0];
				if (ofsElements == null) {
					return;
				}
				
		if(ofsElements.length!=0 && ofsElements[0] instanceof OfsModelResource){
			//true if it is a copy /paste action.open copy wizard.
		NewDomainCreationWizard wizard=new  NewDomainCreationWizard((OfsModelResource)ofsElements[0],true);
		wizard.init(PlatformUI.getWorkbench(),selection);
        WizardDialog dialog= new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
        PixelConverter converter= new PixelConverter(JFaceResources.getDialogFont());
		dialog.setMinimumPageSize(converter.convertWidthInCharsToPixels(70), converter.convertHeightInCharsToPixels(20));
		dialog.create();
		int res= dialog.open();
		notifyResult(res == Window.OK);
		 }
		
		
		
	 }

	protected IOfsModelResourcePaster getOfsModelResourcePaster(Shell shell,
		IOfsProject srcOfsProject, IOfsProject dstOfsProject,
		IOfsModelResource ofsModelResource) {
	
	 return null;
}



   

}
