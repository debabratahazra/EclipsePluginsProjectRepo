package com.odcgroup.workbench.editors.properties.section;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.properties.internal.DiagramPropertiesStatusCodes;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.workbench.editors.properties.OFSPropertyPlugIn;

@SuppressWarnings("restriction")
public abstract class AbstractBasePropertySection extends AbstractModelerPropertySection   {
	
	protected TabbedPropertySheetPage propertySheetPage;
	protected TabbedPropertySheetWidgetFactory widgetFactory;

	private boolean dirty = false;	
	protected Composite parent = null;

	public static final int CANCELLED = DiagramPropertiesStatusCodes.CANCELLED;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public final void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.parent = parent;
		this.propertySheetPage = (TabbedPropertySheetPage)aTabbedPropertySheetPage;
		widgetFactory = propertySheetPage.getWidgetFactory();
		
		Composite pageComposite = widgetFactory.createComposite(parent);
        GridLayout layout = new GridLayout(1, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        pageComposite.setBackground(ColorConstants.lightGray);
        pageComposite.setLayout(layout);    
        
		createControls(pageComposite);		
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractModelerPropertySection#getEditingDomain()
	 */
	public final TransactionalEditingDomain getEditingDomain() {
        if(propertySheetPage instanceof IEditingDomainProvider){
        	return (TransactionalEditingDomain)((IEditingDomainProvider)propertySheetPage).getEditingDomain();
        }  else {
        	return super.getEditingDomain();
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
			if (status.getCode() == CANCELLED
					|| status.getCode() == IStatus.CANCEL
					|| status.getCode() == IStatus.ERROR) {
				refresh();
			}
        } catch (ExecutionException e) {
        	OFSPropertyPlugIn.getDefault().logError(e.getLocalizedMessage(), e);
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
			initiateControls();			
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
	 * @return the widgetFactory
	 */
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}
	
	protected abstract void initiateControls();	
	
	
	/**
	 * @param parent
	 */
	protected abstract void createControls(Composite parent);	

}
