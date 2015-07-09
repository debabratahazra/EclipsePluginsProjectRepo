package com.odcgroup.workbench.editors.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

public abstract class OFSBasePropertySection extends AbstractModelerPropertySection   {
	
	protected TabbedPropertySheetPage propertySheetPage;
	protected TabbedPropertySheetWidgetFactory factory;
	private boolean dirty = false;	
	protected Composite parent = null;

	public static final int CANCELLED = 0x06;
	
	private StringBuffer errorMsg;
	private StringBuffer warnMsg;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.parent = parent;
		this.propertySheetPage = (TabbedPropertySheetPage)aTabbedPropertySheetPage;
		factory = propertySheetPage.getWidgetFactory();
		createInputControls(parent, aTabbedPropertySheetPage);
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection#getEditingDomain()
	 */
	public TransactionalEditingDomain getEditingDomain() {
        if(propertySheetPage instanceof IEditingDomainProvider){
        	return (TransactionalEditingDomain)((IEditingDomainProvider)propertySheetPage).getEditingDomain();
        }  else {
        	return super.getEditingDomain();
        }
	}
	
	/**
	 * @param control
	 */
	protected void  executeChanges(Widget control){
		if(isDirty()) {
			executeChanges(getCommandToExecute(control));
			setDirty(false);
		}
	}
	
	/**
	 * GMF specific commands
	 * 
	 * @param command
	 */
	protected void executeChanges(ICommand command){
		if (command == null)
			return;
        IOperationHistory history = OperationHistoryFactory.getOperationHistory();
        try {
            IStatus status = history.execute(command, new NullProgressMonitor(), null);
            validateModel();
			if (status.getCode() == CANCELLED
					|| status.getCode() == IStatus.CANCEL
					|| status.getCode() == IStatus.ERROR) {
				refresh();
			}
        } catch (ExecutionException e) {
        	OfsCore.getDefault().logError(e.getLocalizedMessage(), e);
        }
		if (propertySheetPage.getCurrentTab() != null){
			propertySheetPage.refresh();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	public final void refresh() {
		try {			
			refreshControls();			
		} catch(Exception e){
			OFSPropertyPlugIn.getDefault().logError("Error refreshing section", e);
		}
	}
	
	/**
	 * @return
	 */
	public boolean isReadOnlyModel() {
		IFile file = WorkspaceSynchronizer.getFile(getEObject().eResource());
		if (file == null) {
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		validateModel();
	}


	/**
	 * emf constraints disabled as emf-like constraints were put in place
	 * as per OCS-23097
	 */
	protected void validateModel() {
		
		errorMsg = new StringBuffer("| ");
		warnMsg = new StringBuffer("| ");

		IBatchValidator validator = ValidationUtil.getInstance().getBatchValidator();
				
		IStatus status = validator.validate(eObject);
		
		if(!status.isOK()) {
			if(!status.isMultiStatus()) {
				if (status.getSeverity() == IStatus.ERROR){
					errorMsg = new StringBuffer(status.getMessage());
				} else if (status.getSeverity() == IStatus.WARNING){
					warnMsg = new StringBuffer(status.getMessage());
				}
			} else {
				for(IStatus childStatus : status.getChildren()) {
					if (childStatus.getSeverity() == IStatus.ERROR){
						errorMsg.append(childStatus.getMessage()+" | ");
					} else if (childStatus.getSeverity() == IStatus.WARNING){
						warnMsg.append(childStatus.getMessage()+" | ");
					}
				}
			}
		}			
	}
				
	
	/**
	 * @return
	 */
	protected Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
    
    /**
     * @return
     */
    public boolean isDirty() {
		return dirty;
	}

	/**
	 * @param dirty
	 */
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	
	/**
	 * abstract method to be implemented by child sections 
	 */
	protected abstract void refreshControls();
	
	/**
	 * @param control
	 * @return
	 */
	protected abstract ICommand getCommandToExecute(Widget control);
	
	/**
	 * @param parent
	 * @param propertySheetPage
	 */
	protected abstract void createInputControls(Composite parent, TabbedPropertySheetPage propertySheetPage);



	public StringBuffer getErrorMsg() {
		return errorMsg;
	}



	public StringBuffer getWarnMsg() {
		return warnMsg;
	}

	

}
