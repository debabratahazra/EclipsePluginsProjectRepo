package com.odcgroup.workbench.editors.properties.section;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.odcgroup.workbench.editors.properties.model.IPropertyContainer;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.widgets.ISWTWidget;

/**
 * @author pkk
 *
 */
public abstract class AbstractModelPropertySection extends AbstractPropertySection implements IPropertyContainer {
	
	protected EObject input;
    private EditingDomain editingDomain = null;
	private List<IPropertyFeature> properties = new ArrayList<IPropertyFeature>();
	protected TabbedPropertySheetWidgetFactory widgetFactory;
	protected TabbedPropertySheetPage propertySheetPage;
	protected Composite body = null;
	
	

	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.propertySheetPage = (TabbedPropertySheetPage)aTabbedPropertySheetPage;
		widgetFactory = propertySheetPage.getWidgetFactory();
		
		Composite pageComposite = widgetFactory.createComposite(parent);
        GridLayout layout = new GridLayout(1, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        pageComposite.setLayout(layout);    
        
        configureProperties();
        
		body = getWidgetFactory().createComposite(pageComposite);
		GridData data = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(data);
		
		layout = new GridLayout(1, false);
		body.setLayout(layout);
		
		if (!properties.isEmpty()) {
			createPropertyControls(body);
		}		
		
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);		
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		
		 /*
         * Update editing domain
         */
		IEditingDomainProvider provider = (IEditingDomainProvider) part
				.getAdapter(IEditingDomainProvider.class);
		if (provider != null) {
			EditingDomain theEditingDomain = provider.getEditingDomain();
			setEditingDomain(theEditingDomain);
		}

		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj instanceof EObject) {
			input = (EObject) obj;
		}
	}
	
	/**
     * Sets the editingDomain.
     * @param editingDomain The editingDomain to set.
     */
    protected void setEditingDomain(EditingDomain editingDomain) {
        this.editingDomain = editingDomain;
    }
	
	/**
	 * @return
	 */
	public EObject getInput() {
		return input;
	}

	@Override
	public void propertyChanged(IPropertyFeatureChangeEvent event) {
		Object val = event.getValue();
		EStructuralFeature feature = event.getPropertyFeature().getStructuralFeature();
		if(val!=null && ((String)val).equals("")){
			val = null;
		}
		Command cmd = SetCommand.create(editingDomain, input, feature, val);
		if (cmd != null) {
			editingDomain.getCommandStack().execute(cmd);
		}
	}

	@Override
	public List<IPropertyFeature> getPropertyFeatures() {
		return properties;
	}

	@Override
	public void addPropertyFeature(IPropertyFeature propertyFeature) {
		if (propertyFeature != null) {
			properties.add(propertyFeature);
		}		
	}
	
	
	/**
	 * 
	 */
	protected void initiateControls() {
		if (input != null) {
			for (IPropertyFeature widget : properties) {
				widget.initiateWidget(input, input);
			}
			if (getPart() instanceof IEditorPart) {
				IEditorInput editInput = ((IEditorPart)getPart()).getEditorInput();
				if (editInput instanceof FileEditorInput) {
					body.setEnabled(true);
				} else {
					body.setEnabled(false);
				}
			}
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	public final void refresh() {
		initiateControls();	
	}
	
	/**
	 * @return
	 */
	public boolean isReadOnlyModel() {
		IFile file = WorkspaceSynchronizer.getFile(input.eResource());
		if (file == null) {
			return true;
		}
		return false;
	}
	

	/**
	 * @param parent
	 */
	protected void createPropertyControls(Composite parent) {
		for (IPropertyFeature property : properties) {
			((ISWTWidget) property).createPropertyControl(body);
			property.addPropertyFeatureChangeListener(this);
		}
	}
	
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

	/**
	 * 
	 */
	protected abstract void configureProperties();

}
