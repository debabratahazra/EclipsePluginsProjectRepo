package com.odcgroup.workbench.editors.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.odcgroup.workbench.editors.properties.controls.ListReferencePropertyControl;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.dialog.AbstractTitleAreaDialog;

/**
 * @author pkk
 * Properties Section to display the basic properties of the selection
 * (basic properties represent only the attributes of the selection)
 */
public abstract class AbstractTabbedPropertiesSection extends AbstractOFSTabbedPropertySection {
	
	
	/**
	 * list of attributes to view in the properties section
	 */
	private List<SectionAttributePropertyHelper> attributes = new LinkedList<SectionAttributePropertyHelper>();
	private List<SectionListReferencePropertyHelper> listReferences = new LinkedList<SectionListReferencePropertyHelper>();
	
	
	/**
	 * @param reference
	 * @return
	 */
	public abstract EObject getReferenceObject(EReference reference);
	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.AbstractOFSPropertySection#createPropertyControls(org.eclipse.swt.widgets.Composite)
	 */
	protected void createPropertyControls(Composite parent, String tabName) {
		Composite body = factory.createFlatFormComposite(parent);
		body.setLayout(new GridLayout(1, false));
		createAttributeControls(body, attributes, tabName);
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
	 * @see com.odcgroup.workbench.editors.properties.OFSBasePropertySection#getCommandToExecute(org.eclipse.swt.widgets.Widget)
	 */
	protected ICommand getCommandToExecute(Widget control) {
		Object data = getDataFromWidget(control);
		for(Iterator<SectionAttributePropertyHelper> ii = attributes.iterator();ii.hasNext();){
			SectionAttributePropertyHelper property = ii.next();
			if (property.getControl() != null && control.equals(property.getControl())){
				// check for attributes whether they are referenced/direct
				if (property.getReference() == null){
					// check if the attribute is ID & check for uniqueness
					if (property.getFeature().isRequired() || property.isUnique()){	
						if (checkIsTaken(data, property.getFeature()) && !getEObject().eGet(property.getFeature()).equals(data)){
							((Text)property.getControl()).setText((String)data);
							MessageBox messageBox;
							if(property.isID())
								messageBox =  new MessageBox(Display.getCurrent().getActiveShell(), SWT.OK|SWT.ICON_ERROR);
							else
								messageBox =  new MessageBox(Display.getCurrent().getActiveShell(), SWT.OK|SWT.ICON_WARNING);
							String msg = "";
							if (property.getFeature().isRequired()){
								msg = "is required";
							}  
							if (property.isUnique() && property.getFeature().isRequired()){
								msg = "is required & has to be unique";
							} 
							if (property.isUnique()){
								msg = "has to be unique";
							}
							messageBox.setMessage("The feature '"+property.getLabel()+"' of <"+getEObject().eClass().getName()+"> "+msg+". \nThe value specified ["+data+"] is already taken");
							messageBox.open();
							return null;
						}
					}	
					// set the value for the feaure
					if (getEObject().eGet(property.getFeature())== null || !getEObject().eGet(property.getFeature()).equals(data)) {
						if(property.getFeature().getEType().getInstanceClass() == int.class){
							Integer intVal = getIntegerValue(data);
							if (intVal > 0)
								return CommandUtil.create(getEditingDomain(), getEObject(), property.getFeature(), intVal);							
						} else {							
							return CommandUtil.create(getEditingDomain(), getEObject(), property.getFeature(), data);
						}
					} 
				} else {
					if (getEObject().eGet(property.getReference())== null){
						CommandUtil.createEReference(getEditingDomain(), getEObject(), property.getReference(), getReferenceObject(property.getReference()));
					} 
					return CommandUtil.create(getEditingDomain(), (EObject)getEObject().eGet(property.getReference()), property.getFeature(), data);
				}
			}
		}
		return null;
	}	
	
	/**
	 * @param owner
	 * @param feature
	 * @return
	 */
	private static String getCommandName(EObject owner, EStructuralFeature feature) {
		return owner.eClass().getName()+"_"+feature.getName()+"_Change";
	}
	
	/**
	 * @param data
	 * @return
	 */
	private Integer getIntegerValue(Object data){
		Integer intVal = 0;
		try {
			intVal = new Integer(data.toString());
		} catch(Exception e){
			// do nothing
		}
		return intVal;
	}
	
	/**
	 * helper method - creates the reference object incase the 
	 * reference object is not created with the containing object
	 */
	protected ICommand createReferenceObjectCommand(EReference refElement, EObject refObject) {
		return CommandUtil.create(getEditingDomain(), getEObject(), refElement, refObject);
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.OFSBasePropertySection#refreshControls()
	 */
	protected void refreshControls() {
		for(Iterator<SectionAttributePropertyHelper> ii = attributes.iterator();ii.hasNext();){
			SectionAttributePropertyHelper property = ii.next();
			Object data = null;	
			if (getEObject() != null){
				if (property.getReference() != null){
					Object obj = getEObject().eGet(property.getReference());
					if (obj != null && obj instanceof EObject){
						data = ((EObject)obj).eGet(property.getFeature());
					}
				} else {
					data = getEObject().eGet(property.getFeature());		
				}
			}
			if (property.getControl() != null){
				if (property.getControl() instanceof Button){
					((Button)property.getControl()).setSelection((Boolean)data);
				} else if (property.getControl() instanceof Text){
					if (data == null){
						data = "";
					}
					((Text)property.getControl()).setText(data.toString());
				}
			}
		}	
		
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
	 * @param body
	 * @param properties
	 */
	private void createAttributeControls(Composite body, List<SectionAttributePropertyHelper> properties, String tabName){
		List<SectionAttributePropertyHelper> booleanAttributes = new ArrayList<SectionAttributePropertyHelper>();
		List<SectionAttributePropertyHelper> otherAttributes = new ArrayList<SectionAttributePropertyHelper>();
		for(Iterator<SectionAttributePropertyHelper> i = properties.iterator();i.hasNext();){
			SectionAttributePropertyHelper property = i.next();
			Class attributeType = property.getFeature().getEType().getInstanceClass();
			if (attributeType.equals(boolean.class) && tabName.equals(property.getTab())){
				booleanAttributes.add(property);
			} else if (tabName.equals(property.getTab())){
				otherAttributes.add(property);
			}
		}
		if (booleanAttributes.size() > 0){
			renderBooleanControls(body, booleanAttributes, tabName);
		}
		boolean boolAtt = false;
		if (booleanAttributes.size()>0){
			boolAtt = true;
		}
		renderOtherControls(body, otherAttributes, boolAtt, tabName);
	}
	
	/**
	 * @param parent
	 * @param booleanAttributes
	 */
	private void renderBooleanControls(Composite parent, List<SectionAttributePropertyHelper> booleanAttributes, String tabName){
		Composite body = OFSUIFactory.INSTANCE.createComposite(parent);
        body.setLayout(new GridLayout(1, false));
        for(Iterator<SectionAttributePropertyHelper> ii = attributes.iterator();ii.hasNext();){
        	SectionAttributePropertyHelper property = ii.next();
        	if (booleanAttributes.contains(property) && tabName.equals(property.getTab())){
        		Button checkbox = OFSUIFactory.INSTANCE.createCheckBox(body, property.getLabel());
        		checkbox.addSelectionListener(new SelectionListener() {
        			
					public void widgetDefaultSelected(SelectionEvent e) {
					}

					public void widgetSelected(SelectionEvent e) {
						setDirty(true);
						executeChanges(e.widget);
					}
        			
        		});
        		property.setControl(checkbox);
        	}
        } 
	}
	
	/**
	 * @param parent
	 * @param otherAttributes
	 */
	private void renderOtherControls(Composite parent, List<SectionAttributePropertyHelper> otherAttributes, boolean boolAtt, String tabName){
		Composite body = OFSUIFactory.INSTANCE.createComposite(parent);
		int gridCol = 2;
		int textWidth = 300;
		int textAreaWidth = 500;		
        body.setLayout(new GridLayout(gridCol, false));
       
        /*space holder for special controls*/
        createEnumerationSpecialControls(body, tabName);
        
        for(Iterator<SectionAttributePropertyHelper> ii = attributes.iterator();ii.hasNext();){
        	SectionAttributePropertyHelper property = ii.next();
        	if (otherAttributes.contains(property) && tabName.equals(property.getTab())){
        		final Text textWidget;
        		if (property.getFeature().isUnsettable()){
        			OFSUIFactory.INSTANCE.createLabel(body, property.getLabel(), property.getTooltip(), property.getFeature().isRequired());
        			textWidget = OFSUIFactory.INSTANCE.createTextField(body);
        			textWidget.setText(property.getFeature().getDefaultValue().toString());
        			textWidget.setEditable(false);
        		} else {
	        		
	        		if(property.isTextarea()){
	            		OFSUIFactory.INSTANCE.createDescriptionLabel(body, property.getLabel());
	        			textWidget = OFSUIFactory.INSTANCE.createTextArea(body, textAreaWidth);
	        		} else {
	            		OFSUIFactory.INSTANCE.createLabel(body, property.getLabel(), property.getTooltip(), property.getFeature().isRequired());
	        			textWidget = OFSUIFactory.INSTANCE.createTextField(body, textWidth);
	        		}
	        		textWidget.addFocusListener(new FocusAdapter() {
	                    public void focusLost(FocusEvent e) {
	                    	if (validateInput(e.widget)) {
	                    		setDirty(true);
	                    		executeChanges(e.widget);
	                    	}
	                    }
	                });
	        		
	        		textWidget.addModifyListener(new ModifyListener() {
	                    public void modifyText(ModifyEvent event) {
	                    }
	                });
	        		property.setControl(textWidget);
        		}
        	}
        }
        
        //fillers
        if (otherAttributes.size() == 1 && listReferences.size()>1) {
        	OFSUIFactory.INSTANCE.createFiller(body, 2);
        }
        
        /*space holder for special controls*/
        createSpecialControls(body, tabName);
        
        
        /* other references */       
        for(Iterator<SectionListReferencePropertyHelper> it = listReferences.iterator();it.hasNext();){
        	SectionListReferencePropertyHelper refProperty = it.next();  
        	if (tabName.equals(refProperty.getTab())){
	        	if (!(refProperty.getLabel() == null))
	        		OFSUIFactory.INSTANCE.createLabel(body, refProperty.getLabel(), refProperty.getTooltip(), false);
	        	ListReferencePropertyControl listReferenceControl = new ListReferencePropertyControl(body, getEObject(),getPopuopDialog(getShell()), refProperty, getEditingDomain());        	
	        	listReferenceControl.createListReferencePropertyControls(true); 
	        	listReferenceControl.setPropertySheetPage(propertySheetPage);
	    		refProperty.setControl(listReferenceControl);
        	}
        }
		
	}	
	
	/**
	 * @param control
	 * @return
	 */
	private boolean validateInput(Widget control){
		if (control instanceof Button){
			return true;
		} else if (control instanceof Text){
			String str = ((Text)control).getText();
			if (str.length()>0 && !str.trim().equals("")){				
				return true;
			}			
		}
		
		return false;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public abstract boolean checkIsTaken(Object key, EStructuralFeature feature);
	
	
	/**
	 * @param body
	 */
	public void createSpecialControls(Composite body, String tabName){
		// let the sectionimpl override which needs to render special controls
	}
	
	/**
	 * @param body
	 * @param tabName
	 */
	public void createEnumerationSpecialControls(Composite body, String tabName) {
		// let the sectionimpl override which needs to render special controls
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
	public void addAttribute(SectionAttributePropertyHelper attributeProperty){
		attributes.add(attributeProperty);
	}
	
	/**
	 * @param listReferenceProperty
	 */
	public void addListReference(SectionListReferencePropertyHelper listReferenceProperty){
		listReferences.add(listReferenceProperty);
	}
	

}
