package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.ui.action.edit.CopyOfsElementsAction;
/**
 * Copy action for the Domain Model .only Domain model will copy.
 * @author snn
 *
 */
public class CopyDomainModelAction extends CopyOfsElementsAction {
  /**
   * CopyDomainModelAction constructor.
   * @param site
   * @param clipboard
   */
   public CopyDomainModelAction(ICommonViewerSite site, Clipboard clipboard) {
		super(site, clipboard);
		
	}


	/**
	 * @see com.odcgroup.mdf.editor.ui.actions.CopyDomainModelAction.acceptOfsElement
	 */
	protected boolean acceptOfsElement(IOfsElement element) {
		if(element instanceof IOfsModelResource){
			return true;
		}
		return false;
	}

	
	protected boolean updateSelection(IStructuredSelection selection) {
		boolean status= super.updateSelection(selection);
		if(selection.getFirstElement() instanceof IOfsModelPackage){
			status=false;
		}
		if(selection.size()>1){
			status=false;
		}
		return status;
	}
}
