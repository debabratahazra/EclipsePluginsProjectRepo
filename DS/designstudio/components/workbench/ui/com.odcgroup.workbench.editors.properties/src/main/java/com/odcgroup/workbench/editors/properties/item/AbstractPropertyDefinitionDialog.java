package com.odcgroup.workbench.editors.properties.item;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;

/**
 *
 * @author pkk
 *
 */
public abstract class AbstractPropertyDefinitionDialog extends TitleAreaDialog 
	implements IPropertyDefinitionDialog {
	

	private List<IPropertyFeature> properties = new ArrayList<IPropertyFeature>();	
	private EObject element;
	private EObject parent;
	private boolean update = false;
	
	/**
	 * @param parentShell
	 */
	public AbstractPropertyDefinitionDialog(Shell parentShell, EObject element, EObject parent, boolean update) {
		super(parentShell);
		this.element = element;
		this.parent = parent;
		this.update = update;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#create()
	 */
	public void create() {
		super.create();
		this.setTitle(element.eClass().getName());
		if (update){
			setMessage("Update "+element.eClass().getName());
		} else {
			setMessage("Create new "+element.eClass().getName());
		}
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);		
		shell.setText(element.eClass().getName()); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyDefinitionDialog#getDefinedProperty()
	 */
	public EObject getDefinedProperty() {
		return element;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected final Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		initiateControls();
		return control;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected final Control createDialogArea(Composite parent) {
		configureProperties();
		Composite body = (Composite) super.createDialogArea(parent);
		body.setLayout(new GridLayout(1, false));
		GridData gData = new GridData(GridData.FILL_BOTH);
		body.setLayoutData(gData);		
		if (!properties.isEmpty()) {
			createPropertyControls(body);
		}
		return body;
	}	
	
	
	/**
	 * @param body
	 */
	protected void createPropertyControls(Composite body) {
		for (IPropertyFeature property : properties) {
			((AbstractPropertyWidget) property).createPropertyControl(body);
			property.addPropertyFeatureChangeListener(this);
		}
	}
	
	/**
	 * @param IPropertyFeature
	 */
	public void addPropertyFeature(IPropertyFeature propertyItem) {
		if (propertyItem != null) {
			properties.add(propertyItem);
		}
	}
	
	/**
	 * 
	 */
	protected void initiateControls() {
		if (element != null) {
			for(IPropertyFeature widget : properties) {
				widget.initiateWidget(element, parent);
			}
		}
		Button okBtn = getButton(TitleAreaDialog.OK);
		if (okBtn != null) {
			if (validate(element) ) {
				okBtn.setEnabled(true);
			} else {
				okBtn.setEnabled(false);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeListener#propertyChanged(com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeEvent)
	 */
	public void propertyChanged(IPropertyFeatureChangeEvent event) {
		IPropertyFeature propertyItem = event.getPropertyFeature();
		Object val = event.getValue();
		element.eSet(propertyItem.getStructuralFeature(), val);
		initiateControls();	
	}

	/**
	 * @return the update
	 */
	public boolean isUpdate() {
		return update;
	}
	
	/**
	 * @return the property
	 */
	public EObject getProperty() {
		return element;
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyContainer#getPropertyFeatures()
	 */
	public List<IPropertyFeature> getPropertyFeatures() {
		return properties;
	}

	/**
	 * 
	 */
	protected abstract void configureProperties();

	

}
