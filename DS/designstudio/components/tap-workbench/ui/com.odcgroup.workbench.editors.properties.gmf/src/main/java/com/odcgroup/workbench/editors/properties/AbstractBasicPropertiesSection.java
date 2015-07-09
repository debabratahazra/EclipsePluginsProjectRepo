package com.odcgroup.workbench.editors.properties;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.odcgroup.workbench.editors.properties.choice.ChoiceGroup;
import com.odcgroup.workbench.editors.properties.controls.AttributePropertiesControl;
import com.odcgroup.workbench.editors.properties.controls.ChoiceReferencePropertyControl;
import com.odcgroup.workbench.editors.properties.controls.ListReferencePropertyControl;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionPropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

/**
 * @author pkk
 * Properties Section to display the basic properties of the selection
 * (basic properties represent only the attributes of the selection)
 */
public abstract class AbstractBasicPropertiesSection extends AbstractOFSPropertySection {
	
	
	/**
	 * list of attributes to view in the properties section
	 */
	private List<SectionPropertyHelper> features = new LinkedList<SectionPropertyHelper>();
	private List<SectionListReferencePropertyHelper> listReferences = new LinkedList<SectionListReferencePropertyHelper>();
	private ChoiceGroup choiceGroup = null;
	private ChoiceReferencePropertyControl choiceControl = null;
	private AttributePropertiesControl attrControl = null;
	private Composite specialControl = null;
	
	/**
	 * abstract method to be implemented by child classes
	 */
	protected abstract void configureProperties();
	
	/**
	 * @param reference
	 * @return
	 */
	public abstract EFactory getEFactory();
	
	/**
	 * @param key
	 * @return
	 */
	public abstract boolean checkIsTaken(Object key, EStructuralFeature feature);
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractOFSPropertySection#createPropertyControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createPropertyControls(Composite parent) {
		configureProperties();		
		Composite body = factory.createFlatFormComposite(parent);
		body.setLayout(new GridLayout(1, false));        
		createAttributeControls(body, features);
		ceateChoiceReferencePropertyControl(body);
        /*space holder for special controls*/
		this.specialControl = createSpecialControls(body);
		createListControls(body);
	}
	
	/**
	 * @param body
	 * @param properties
	 */
	private void createAttributeControls(Composite body, List<SectionPropertyHelper> properties){
		if (!properties.isEmpty()){
			attrControl = new AttributePropertiesControl(body, properties, getEditingDomain(), propertySheetPage, getEFactory(), getEObject()){
				public boolean checkIsAttributeValueTaken(Object key, EStructuralFeature feature) {
					return checkIsTaken(key, feature);
				}
				
				public void renderEnumerationSpecialControls(Composite body) {
					createEnumerationSpecialControls(body);	
				}
				
				public void refreshSiblingControls(){
					refreshChoiceControls();
					refreshReferenceControls();	
				}
				
				public void refreshPropertyMessages() {
					validateModel();
				}
				
				public void updateChanges(Object control) {
					setDirty(true);
					executeChanges(getUpdateCommand(control));					
				}
			};
			attrControl.createAttributePropertyControls();
		}
	}	
	
	/**
	 * @param body
	 */
	public void ceateChoiceReferencePropertyControl(Composite body) {
		if (choiceGroup != null){
			choiceControl = new ChoiceReferencePropertyControl(choiceGroup, body, propertySheetPage, getEditingDomain(), getEFactory(), getEObject()){
				public boolean checkAttributeTaken(Object key, EStructuralFeature feature){
					return checkIsTaken(key, feature);				
				}
				
				public void refreshSectionMessage() {
					validateModel();
				}
				
				public void updateChanges(Object control) {
					setDirty(true);
					executeChanges(getUpdateCommand(control));					
				}
			};
			choiceControl.ceateChoiceReferencePropertyControl();
		}
	}
	
	/**
	 * @param control
	 * @return
	 */
	protected Object getDataFromWidget(Widget control) {
		Object data = null;
		if (control instanceof Button){
			data = ((Button)control).getSelection();
		} else if (control instanceof Text){
			data = ((Text)control).getText();
		}
		return data;
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.workflow.tools.processDesigner.ui.properties.sections.AbstractWorkflowPropertySection#refreshControls()
	 */
	protected void refreshControls() {	
		refreshAttributeControls();
		refreshChoiceControls();
		checkReadOnlyMode();
		refreshReferenceControls();	
	}	
	
	private void checkReadOnlyMode() {
		if (isReadOnlyModel()) {
			if (attrControl != null)
				attrControl.getControl().setEnabled(false);
			if (choiceControl != null)
				choiceControl.getControl().setEnabled(false);
			if (specialControl != null)
				specialControl.setEnabled(false);
		}
	}
	
	/**
	 * 
	 */
	private void refreshAttributeControls() {			
		if (attrControl != null){
			attrControl.setParentObject(getEObject());
			attrControl.refreshControls();
		}	
	}
	
	/**
	 * 
	 */
	private void refreshChoiceControls() {
		if (choiceGroup != null){
			choiceControl.setParentObject(getEObject());
			choiceControl.refreshControls();
		}
	}
	
	private void refreshReferenceControls() {
		for(Iterator<SectionListReferencePropertyHelper>  ii = listReferences.iterator();ii.hasNext();){
			SectionListReferencePropertyHelper ref = ii.next();
			if (ref.getParentRef() == null){
				ref.getControl().setParentObject(getEObject());
			} else {
				ref.getControl().setParentObject((EObject)getEObject().eGet(ref.getParentRef()));
			}
			ref.getControl().refreshControls();
		}
	}
	
	/**
	 * @param parent
	 * @param otherAttributes
	 */
	private void createListControls(Composite parent){          
        Composite body = OFSUIFactory.INSTANCE.createComposite(parent);
		int gridCol = 2;	
        body.setLayout(new GridLayout(gridCol, false));
        body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        for(Iterator<SectionListReferencePropertyHelper> it = listReferences.iterator();it.hasNext();){
        	SectionListReferencePropertyHelper refProperty = it.next();  
        	if (refProperty.isDisplayLabel()) {
        		if (refProperty.getLabel() != null && !refProperty.getLabel().trim().equals("")) {
        			OFSUIFactory.INSTANCE.createLabel(body, refProperty.getLabel(), refProperty.getTooltip(), false);
        		}
        	}
        	ListReferencePropertyControl listReferenceControl = new ListReferencePropertyControl(body, getEObject(),getPopuopDialog(getShell()), refProperty, getEditingDomain());        	
        	listReferenceControl.createListReferencePropertyControls(true); 
        	listReferenceControl.setPropertySheetPage(propertySheetPage);
    		refProperty.setControl(listReferenceControl);
        }
		
	}		
	
	/**
	 * @param body
	 */
	public Composite createSpecialControls(Composite body){
		// let the sectionimpl override which needs to render special controls
		return null;
	}
	
	/**
	 * @param body
	 */
	public void createEnumerationSpecialControls(Composite body) {
		// let the subclasses override for this behaviour
	}
	
	/**
	 * @param shell
	 * @return
	 */
	public AbstractTitleAreaDialog getPopuopDialog(Shell shell) {
		return null;
	}
	
	/**
	 * @param attributeProperty
	 */
	public void addAttribute(SectionPropertyHelper attributeProperty){
		features.add(attributeProperty);
	}
	
	/**
	 * @param listReferenceProperty
	 */
	public void addListReference(SectionListReferencePropertyHelper listReferenceProperty){
		listReferences.add(listReferenceProperty);
	}

	public void setChoiceGroup(ChoiceGroup choiceGroup) {
		this.choiceGroup = choiceGroup;
	}
	
	protected ICommand getCommandToExecute(Widget control) {
		return null;
	}
	

}
