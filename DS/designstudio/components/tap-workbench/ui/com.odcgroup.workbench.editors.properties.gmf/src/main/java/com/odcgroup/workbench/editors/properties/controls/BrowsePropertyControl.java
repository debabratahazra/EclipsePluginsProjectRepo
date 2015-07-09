package com.odcgroup.workbench.editors.properties.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.SectionBrowsePropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.ResourceUtil;
import com.odcgroup.workbench.ui.repository.ModelSelectionDialog;

public class BrowsePropertyControl implements SelectionListener, IPropertyControl {
	
	private SectionBrowsePropertyHelper feature = null;
	private Composite body = null;
	private EObject parentObject = null;
	private TransactionalEditingDomain editingDomain = null;
	private Text textField;
	private Button selectButton;
	private EFactory factory;
	
	/**
	 * @param composite
	 * @param parentObject
	 * @param feature
	 * @param editingDomain
	 */
	public BrowsePropertyControl(Composite composite, EObject parentObject, 
			SectionBrowsePropertyHelper feature, 
			TransactionalEditingDomain editingDomain, EFactory factory) {
		this.body = OFSUIFactory.INSTANCE.createComposite(composite);
		this.parentObject = parentObject;
		this.feature = feature;
		this.editingDomain = editingDomain;
		this.factory = factory;
	}
	
	/**
	 * 
	 */
	public void createBrowsePropertyControl() {
		Composite comp = OFSUIFactory.INSTANCE.createComposite(body);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 1;
		comp.setLayout(gridLayout);
		OFSUIFactory.INSTANCE.createLabel(comp, feature.getLabel(), feature.getLabel(),true);
		textField = OFSUIFactory.INSTANCE.createTextField(comp);
		textField.setEditable(false);
		selectButton = new Button(comp, 0x800000);
		selectButton.addSelectionListener(this);
		GridData gridData_1 = new GridData();
		gridData_1.heightHint = 20;
		selectButton.setLayoutData(gridData_1);
		selectButton.setText(feature.getBrowseLabel());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		// incase of readonly file
		if (!CommandUtil.makeReadOnlyWriteable(parentObject, editingDomain)) {
			return;
		}
		final ModelSelectionDialog dialog = getModelSelectionDialog();
		if (dialog.open() == 0) {
			final EObject obj = (EObject)dialog.getFirstResult();						
			CommandUtil.executeCommand(getUpdateCommand(obj.eResource().getURI().toString()));
			refreshControls();
			textField.setText(getValueToSet().toString());
		}		
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#updateChanges(java.lang.Object)
	 */
	public void updateChanges(Object command) {
		// empty
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#getUpdateCommand(java.lang.Object)
	 */
	public ICommand getUpdateCommand(Object data) {
		if (getParentObject().eGet(feature.getReference())== null){
			CommandUtil.createEReference(editingDomain,  getParentObject(), feature.getReference(), 
					getReferenceObject(feature.getReference()));
		} 
		return CommandUtil.create(editingDomain, (EObject)getParentObject().eGet(feature.getReference()), feature.getFeature(), data);
	}
	
	/**
	 * helper method - creates the reference object incase the 
	 * reference object is not created with the containing object
	 */
	protected void createReferenceObject(EReference refElement, EObject refObject) {
	}	
	
	/**
	 * @param reference
	 * @return
	 */
	public EObject getReferenceObject(EReference reference){
		return factory.create(reference.getEReferenceType());
	}
	
	/**
	 * @return
	 */
	protected ModelSelectionDialog getModelSelectionDialog() {
		IFile pageflowFile = ResourceUtil.getFile(getParentObject());
		IProject project = pageflowFile.getProject();

		IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, feature.getExtensions());
		ModelSelectionDialog dialog = ModelSelectionDialog.createDialog(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                getRelatedResources(lookup, feature.getModelClass()), feature.getAdapterFactory());

        dialog.setTitle("Select "+feature.getReference().getName());
		dialog.setMultipleSelection(false);
		return dialog;
	}
	
	/**
	 * @param lookup
	 * @param eClass
	 * @return
	 */
	private Object[] getRelatedResources(ModelResourceLookup lookup, EClass eClass) {		
		Set<IOfsModelResource> mResources = lookup.getAllOfsModelResources();
		List<EObject> list = new ArrayList<EObject>();
		try {
			for (IOfsModelResource mResource : mResources) {
				List<EObject> content = mResource.getEMFModel();
				if (!content.isEmpty() && eClass.isInstance(content.get(0))) {
					list.add(content.get(0));
				}
			}
		} catch (Exception e) {
		}
		return list.toArray();
	}
	
	/**
	 * 
	 */
	public void refreshControls() {
		textField.setText(getValueToSet().toString());
	}
	
	/**
	 * @return
	 */
	public Object getValueToSet() {		
		Object obj = parentObject.eGet(feature.getReference());
		if (obj != null){
			Object object = ((EObject)obj).eGet(feature.getFeature());
			if (object != null){
				return object;
			}
		}
		return "";
	}

	/**
	 * @return
	 */
	public EObject getParentObject() {
		return parentObject;
	}

	/**
	 * @param parentObject
	 */
	public void setParentObject(EObject parentObject) {
		this.parentObject = parentObject;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#getControl()
	 */
	public Composite getControl() {
		return body;
	}
	
	

}
