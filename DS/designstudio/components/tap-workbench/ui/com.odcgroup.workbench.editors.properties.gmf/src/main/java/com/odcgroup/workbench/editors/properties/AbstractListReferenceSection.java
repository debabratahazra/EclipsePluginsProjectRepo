package com.odcgroup.workbench.editors.properties;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import com.odcgroup.workbench.editors.properties.controls.ListReferencePropertyControl;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

/**
 * @author pkk
 * 
 */
public abstract class AbstractListReferenceSection extends AbstractOFSPropertySection {

	protected SectionListReferencePropertyHelper referenceUtil = null;
	protected AbstractTitleAreaDialog dialog = null;	
	protected ListReferencePropertyControl listReferenceControl;
	
	/**
	 * abstract method, should be implemented by concrete classes
	 * which should return a specific popup dialog
	 * 
	 * @return AbstractTitleAreaDialog
	 */
	public abstract AbstractTitleAreaDialog getPopuopDialog(Shell shell);
	
	/**
	 * @return
	 */
	protected abstract SectionListReferencePropertyHelper getReferenceUtil();

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractOFSPropertySection#createPropertyControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createPropertyControls(Composite parent) {
		this.referenceUtil = getReferenceUtil();
		listReferenceControl = new ListReferencePropertyControl(parent, getEObject(),getPopuopDialog(getShell()), referenceUtil, getEditingDomain());
		listReferenceControl.createListReferencePropertyControls(true);
		listReferenceControl.setPropertySheetPage(propertySheetPage);
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.OFSBasePropertySection#getCommandToExecute(org.eclipse.swt.widgets.Widget)
	 */
	protected ICommand getCommandToExecute(Widget control) {
		// no implementation required
		return null;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.OFSBasePropertySection#refreshControls()
	 */
	protected void refreshControls() {
		listReferenceControl.setParentObject(getEObject());
		listReferenceControl.refreshControls();
	}	

	/**
	 * 
	 */
	protected void refreshPage() {
		if (propertySheetPage.getCurrentTab() != null){
			propertySheetPage.refresh();
		}
	}	

}