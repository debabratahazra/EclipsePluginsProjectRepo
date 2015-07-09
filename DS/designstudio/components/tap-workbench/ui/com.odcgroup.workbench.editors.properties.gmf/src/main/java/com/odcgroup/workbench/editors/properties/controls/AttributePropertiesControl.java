package com.odcgroup.workbench.editors.properties.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.SectionAttributePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionBooleanPropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionBrowsePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.properties.util.SectionMultiplePropertyRowHelper;
import com.odcgroup.workbench.editors.properties.util.SectionPropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;
import com.odcgroup.workbench.editors.ui.ResourceUtil;
import com.odcgroup.workbench.generation.GenerationCore;

/**
 * @author pkk
 *
 */
public abstract class AttributePropertiesControl implements IPropertyControl {
	/**
	 * list of attributes to view in the properties control
	 */
	private List<SectionPropertyHelper> features = new LinkedList<SectionPropertyHelper>();
	private Composite attributeControlComposite = null;
	protected TransactionalEditingDomain editingDomain;
	protected TabbedPropertySheetPage propertySheetPage;
	protected EObject parentObject;
	protected EFactory factory;
	protected Map<String, List<SectionPropertyHelper>> rowControls = new HashMap<String, List<SectionPropertyHelper>>();
	protected static int MESSAGE_WARNING = SWT.OK|SWT.ICON_WARNING;
	protected static int MESSAGE_ERROR = SWT.OK|SWT.ICON_ERROR;
	
	/**
	 * @param parent
	 * @param attributes
	 * @param editingDomain
	 * @param propertySheetPage
	 * @param factory
	 */
	public AttributePropertiesControl(Composite parent, List<SectionPropertyHelper> features, TransactionalEditingDomain editingDomain, TabbedPropertySheetPage propertySheetPage, EFactory factory, EObject parentObject){
		this.attributeControlComposite = OFSUIFactory.INSTANCE.createComposite(parent);
		attributeControlComposite.setLayout(new GridLayout(1, false));
		OFSUIFactory.INSTANCE.createLine(900, attributeControlComposite);
		this.features = features;
		this.editingDomain = editingDomain;
		this.propertySheetPage = propertySheetPage;
		this.factory = factory;
		this.parentObject = parentObject;
	}
	
	/**
	 * 
	 */
	public void dispose() {
		if (attributeControlComposite != null){
			attributeControlComposite.dispose();
		}
	}
	
	/**
	 * 
	 */
	public void createAttributePropertyControls(){
		List<SectionAttributePropertyHelper> booleanAttributes = new ArrayList<SectionAttributePropertyHelper>();
		List<SectionAttributePropertyHelper> otherAttributes = new ArrayList<SectionAttributePropertyHelper>();
		for (SectionPropertyHelper feature : features) {
			if (feature instanceof SectionAttributePropertyHelper){
				SectionAttributePropertyHelper property = (SectionAttributePropertyHelper)feature;
				Class<?> attributeType = property.getFeature().getEType().getInstanceClass();
				if (attributeType.equals(boolean.class)){
					booleanAttributes.add(property);
				} else {
					otherAttributes.add(property);
				}				
			}
		}
		if (booleanAttributes.size() > 0){
			renderBooleanControls(booleanAttributes);
		}
		boolean boolAtt = false;
		if (booleanAttributes.size()>0){
			boolAtt = true;
		}
		renderTextControls(otherAttributes, boolAtt);
	}
	
	/**
	 * @param booleanAttributes
	 */
	private void renderBooleanControls(List<SectionAttributePropertyHelper> booleanAttributes) {
		Composite body = OFSUIFactory.INSTANCE.createComposite(attributeControlComposite);
        body.setLayout(new GridLayout(1, false));
        for (SectionPropertyHelper feature : features) {
			if (feature instanceof SectionAttributePropertyHelper){
				SectionAttributePropertyHelper property = (SectionAttributePropertyHelper)feature;
	        	if (booleanAttributes.contains(property)){
	        		Button checkbox = OFSUIFactory.INSTANCE.createCheckBox(body, property.getLabel());
	        		checkbox.addSelectionListener(new SelectionAdapter() {
	        			
						public void widgetSelected(SelectionEvent e) {
							updateChanges(e.widget);
						}
	        			
	        		});
	        		property.setControl(checkbox);
	        	}
			}
        } // if boolean attributes are available, then create a delimiter
        if (booleanAttributes.size()>0){
            OFSUIFactory.INSTANCE.createLine(attributeControlComposite, 1);   
        }		
	}
	
	
	/**
	 * @param otherAttributes
	 * @param boolAtt
	 */
	private void renderTextControls(List<SectionAttributePropertyHelper> otherAttributes, boolean boolAtt){
		Composite body = OFSUIFactory.INSTANCE.createComposite(attributeControlComposite);
		int gridCol = 2;	
        body.setLayout(new GridLayout(gridCol, false));
		int textWidth = 300;
		int textAreaWidth = 500;	
		for (SectionPropertyHelper feature : features) {
			if (feature instanceof SectionAttributePropertyHelper){
				SectionAttributePropertyHelper property = (SectionAttributePropertyHelper)feature;
				boolean isRequired = false;
				if (property.getFeature().isRequired() 
						|| (property.getReference() != null && property.getReference().isRequired())){
					isRequired = true;
				}
	        	if (otherAttributes.contains(property)){
	        		createAttributeControls(property, body, isRequired, textWidth, textAreaWidth, false);
	        	}
			} else if (feature instanceof SectionListReferencePropertyHelper) {
				SectionListReferencePropertyHelper refProperty = (SectionListReferencePropertyHelper)feature; 
	        	if (refProperty.isDisplayLabel()) {
	        		if (refProperty.getLabel() != null && !refProperty.getLabel().trim().equals("")) {
	        			OFSUIFactory.INSTANCE.createLabel(body, refProperty.getLabel(), refProperty.getTooltip(), false);
	        		}
	        	}
	        	ListReferencePropertyControl listReferenceControl = new ListReferencePropertyControl(body, getReferenceObject(refProperty), refProperty.getPopuopDialog(), refProperty, editingDomain);        	
	        	listReferenceControl.createListReferencePropertyControls(true); 
	        	listReferenceControl.setPropertySheetPage(propertySheetPage);
	    		refProperty.setControl(listReferenceControl);			
			} else if (feature instanceof SectionMultiplePropertyRowHelper) {
				SectionMultiplePropertyRowHelper multiProperty = (SectionMultiplePropertyRowHelper) feature;
				Map<String, SectionPropertyHelper> mappedFeatures = multiProperty.getMappedFeatures();
				Composite rowbody = OFSUIFactory.INSTANCE.createComposite(attributeControlComposite);
				rowbody.setLayout(new GridLayout(mappedFeatures.size(), false));
				createMultiFeatureRowControl(rowbody, mappedFeatures);
			} else if (feature instanceof SectionBooleanPropertyHelper){
				SectionBooleanPropertyHelper property = (SectionBooleanPropertyHelper)feature;
				createChoiceControl(property, attributeControlComposite);
			} else if (feature instanceof SectionBrowsePropertyHelper){
				SectionBrowsePropertyHelper property = (SectionBrowsePropertyHelper)feature;
				BrowsePropertyControl control = new BrowsePropertyControl(body, getParentObject(), property, editingDomain, factory);
				control.createBrowsePropertyControl();
				property.setControl(control);
			}
        }
	
	}	
	
	/**
	 * @param property
	 * @param body
	 */
	private void createChoiceControl(SectionBooleanPropertyHelper property, Composite body){
		Composite choiceComp = OFSUIFactory.INSTANCE.createRadioComposite(body);
		choiceComp.setBackground(body.getBackground());
		Button trueBtn = new Button(choiceComp, SWT.RADIO);
		trueBtn.setBackground(choiceComp.getBackground());
		trueBtn.setText(property.getTrueLabel());
		trueBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateChanges(e.widget);
			}
		});
		property.setTrueControl(trueBtn);
		Button falseBtn = new Button(choiceComp, SWT.RADIO);
		falseBtn.setBackground(choiceComp.getBackground());
		falseBtn.setText(property.getFalseLabel());
		falseBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateChanges(e.widget);
			}
		});
		property.setFalseControl(falseBtn);
	}
	
	/**
	 * @param rowBody
	 * @param mappedFeatures
	 */
	private void createMultiFeatureRowControl(Composite rowBody, Map<String, SectionPropertyHelper> mappedFeatures){		
		Set<String> keys = mappedFeatures.keySet();	
		Group group = null;
		for (String key : keys) {
			SectionPropertyHelper feature = mappedFeatures.get(key);
			group = OFSUIFactory.INSTANCE.createGroup(rowBody, key);
			if (feature instanceof SectionAttributePropertyHelper){
				SectionAttributePropertyHelper property = (SectionAttributePropertyHelper)feature;
				boolean isRequired = false;
				if (property.getFeature().isRequired() 
						|| (property.getReference() != null && property.getReference().isRequired())){
					isRequired = true;
				}
        		createAttributeControls(property, group, isRequired, 300, 350, true);
			} else if (feature instanceof SectionListReferencePropertyHelper) {
				SectionListReferencePropertyHelper refProperty = (SectionListReferencePropertyHelper)feature; 
	        	ListReferencePropertyControl listReferenceControl = new ListReferencePropertyControl(group, getReferenceObject(refProperty), refProperty.getPopuopDialog(), refProperty, editingDomain);        	
	        	listReferenceControl.createListReferencePropertyControls(true); 
	        	listReferenceControl.setPropertySheetPage(propertySheetPage);
	    		refProperty.setControl(listReferenceControl);			
			}
		}
		
	}
	
	
	/**
	 * @param property
	 * @param body
	 * @param isRequired
	 * @param textWidth
	 * @param textAreaWidth
	 */
	private void createAttributeControls(SectionAttributePropertyHelper property, Composite body, boolean isRequired, int textWidth, int textAreaWidth, boolean noLabels){
		if (property.isEnumeration()) {
			OFSUIFactory.INSTANCE.createLabel(body, property.getLabel(), property.getTooltip(), isRequired);
			Combo combo = OFSUIFactory.INSTANCE.createCombo(body);
			combo.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent event) {
            		updateChanges(event.widget);
				}
			});
			property.setControl(combo);
		
		} else {
    		final Text textWidget;
    		
    		if (property.getFeature().isUnsettable()){
    			if (!noLabels){
    				OFSUIFactory.INSTANCE.createLabel(body, property.getLabel(), property.getTooltip(), isRequired);
    			}
    			textWidget = OFSUIFactory.INSTANCE.createTextField(body);
    			textWidget.setText(property.getFeature().getDefaultValue().toString());
    			textWidget.setEditable(false);
    		} else {
        		if(property.isTextarea()){
        			int verIndent = 5;
        			if (!noLabels){
        				OFSUIFactory.INSTANCE.createDescriptionLabel(body, property.getLabel());
        				verIndent = 0;
        			}
        			textWidget = OFSUIFactory.INSTANCE.createTextArea(body, textAreaWidth, verIndent);
        		} else {
        			if (!noLabels){
        				OFSUIFactory.INSTANCE.createLabel(body, property.getLabel(), property.getTooltip(), isRequired);
        			}
        			textWidget = OFSUIFactory.INSTANCE.createTextField(body, property.isWidthAsTextArea() ? textAreaWidth+23 : textWidth);
        		}
        		textWidget.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent e) {
                    	if (validateInput(e.widget)) {
                    		updateChanges(e.widget);
                    	}
                    }
                });
        		
        		property.setControl(textWidget);        		
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
			for (SectionPropertyHelper feature : features) {
				if (feature instanceof SectionAttributePropertyHelper){
					SectionAttributePropertyHelper property = (SectionAttributePropertyHelper)feature;
					if (property.getControl() != null && control.equals(property.getControl())){
						// check if the attribute is ID & check for uniqueness
						String originalVal = null;
						Object objVal = getPropertyValue(property);
						if (objVal != null) {
							originalVal = objVal.toString();
						} else {
							originalVal = "";
						}
						if (property.getFeature().isRequired() 
								|| (property.getReference() != null && property.getReference().isRequired())){
							if (str != null && str.trim().length() > 0 && !str.equals(originalVal)){				
								return true;
							}
							return false;
						} else {
							/*
							str = str.trim();
							if (str.length()>=0){
								return true;
							}*/
							return true;
						}					 
					}
				} else {
					return true;
				}
			}
					
		}		
		return false;	
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
			if (data == null){
				return "";
			}
		} else if (control instanceof Combo){
			int index =  ((Combo)control).getSelectionIndex();
			data = getEnumerationFeatureValue(control, index);
		}
		return data;
	}
	
	/**
	 * @param control
	 * @param index
	 * @return
	 */
	protected Object getEnumerationFeatureValue(Widget control, int index) {
		for (SectionPropertyHelper feature : features) {
			if (feature instanceof SectionAttributePropertyHelper){
				SectionAttributePropertyHelper property = (SectionAttributePropertyHelper)feature;
				if (property.getControl() != null && control.equals(property.getControl())){
					return property.getEnumeratedValues().get(index);
				}
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#updateChanges(java.lang.Object)
	 */
	public  abstract void updateChanges(Object control);

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#getUpdateCommand(java.lang.Object)
	 */
	public ICommand getUpdateCommand(Object obj) {
		Widget control = (Widget) obj;
		Object data = getDataFromWidget(control);
		if (!(control instanceof Button) && data == null){
			return null;
		}
		for (SectionPropertyHelper feature : features) {
			if (feature instanceof SectionAttributePropertyHelper){
				SectionAttributePropertyHelper aProperty = (SectionAttributePropertyHelper)feature;
				if (aProperty.getControl() != null && control.equals(aProperty.getControl())){
					return getCommandForAttributeControl(control, aProperty, data);
				}
			} else if (feature instanceof SectionMultiplePropertyRowHelper){
				SectionMultiplePropertyRowHelper multiProperty = (SectionMultiplePropertyRowHelper)feature;
				Map<String, SectionPropertyHelper> mappedFeatures = multiProperty.getMappedFeatures();
				Set<String> keys = mappedFeatures.keySet();
				for (String key : keys) {
					SectionPropertyHelper property = mappedFeatures.get(key);
					if (property instanceof SectionAttributePropertyHelper){
						SectionAttributePropertyHelper aProperty = (SectionAttributePropertyHelper)property;
						if (aProperty.getControl() != null && control.equals(aProperty.getControl())){
							return getCommandForAttributeControl(control, aProperty, data);
						}
					} 
				}
			} else if (feature instanceof SectionBooleanPropertyHelper){
				SectionBooleanPropertyHelper bProperty = (SectionBooleanPropertyHelper)feature;
				if(((Button)control).getSelection()){
					return getCommandForBooleanControl(control, bProperty, data);
				}
			}
		}
		return null;
	}		
	
	/**
	 * @param property
	 * @param data
	 * @return
	 */
	private MessageBox getMessageBox(SectionAttributePropertyHelper property, Object data){
		int type = MESSAGE_WARNING;
		if(property.isID())
			type = MESSAGE_ERROR;
		
		MessageBox messageBox =  new MessageBox(Display.getCurrent().getActiveShell(), type);
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
		messageBox.setMessage("The feature '"
				+ property.getLabel()+"' of <"
				+ getParentObject().eClass().getName()+"> "
				+ msg+". \nThe value specified ["+data+"] is already taken");
		return messageBox;
	}
	
	/**
	 * @param control
	 * @param property
	 * @param data
	 * @return
	 */
	protected ICommand getCommandForBooleanControl(Widget control, SectionBooleanPropertyHelper property, Object data){
		if(control instanceof Button && control.equals(property.getTrueControl())){
			((Button)property.getFalseControl()).setSelection(false);
			return CommandUtil.create(editingDomain, getParentObject(), property.getFeature(), true);
		} else {
			((Button)property.getTrueControl()).setSelection(false);
			return CommandUtil.create(editingDomain, getParentObject(), property.getFeature(), false);			
		}
	}
	
	/**
	 * @param control
	 * @param property
	 * @param data
	 * @return
	 */
	protected ICommand getCommandForAttributeControl(Widget control, SectionAttributePropertyHelper property, Object data){
		// check for attributes whether they are referenced/direct
		if (property.getReference() == null){
			// check if the attribute is ID & check for uniqueness
			if (property.getFeature().isRequired() && property.isUnique()){	
				if (checkIsAttributeValueTaken(data, property.getFeature()) 
						&& getParentObject().eGet(property.getFeature()) != null 
						&& !getParentObject().eGet(property.getFeature()).equals(data)){
					((Text)property.getControl()).setText((String)data);
					IFile pageflowFile = ResourceUtil.getFile(parentObject);
					IProject project = pageflowFile.getProject();
					if(property.isID() && GenerationCore.isTechnical(project)){	
						getMessageBox(property, data).open();
						return null;
					}
				}
			}	
			// set the value for the feaure
			if (getParentObject().eGet(property.getFeature())== null || !getParentObject().eGet(property.getFeature()).equals(data)) {
				if(property.getFeature().getEType().getInstanceClass() == int.class){
					Integer intVal = 0;
					try {
						intVal = getIntegerValue(data);
					} catch (Exception e) {
						MessageBox messageBox =  new MessageBox(Display.getCurrent().getActiveShell(), SWT.OK|SWT.ICON_WARNING);
						messageBox.setMessage("Specify an int value for feature '"
								+property.getLabel()+"' of <"
								+getParentObject().eClass().getName()+"> ");
						messageBox.open();
						return null;
					}
					return CommandUtil.create(editingDomain, getParentObject(), property.getFeature(), intVal);							
				} else if(property.getFeature().getEType().getInstanceClass() == String.class){	
					String str = (String)data;
					// Localization - set the localization key
					if (property.isLocalizeable()){
						throw new UnsupportedOperationException("ATR Refactor the code agains the new translation API");
// REFACTOR-BEGIN
//						
//						Localizable localizable = (Localizable)(property.getAdapterFactory().adapt(getParentObject(), Localizable.class));
//						LocalizableElement element = localizable.getLocalizableElements().get(property.getAttributeIndex());
//						element.setMessage(element.getDefaultLocale(), str.trim());	
//						final EStructuralFeature feat = property.getFeature();
//						// make the editor dirty for the diagram label to refresh
//						List<ICommand> commands = new ArrayList<ICommand>();
//						commands.add(CommandUtil.create(editingDomain, getParentObject(), feat, ""));
//						commands.add(CommandUtil.create(editingDomain, getParentObject(), property.getFeature(), element.getKey()));
//						return new CompositeCommand(getCommandName(getParentObject(), feat), commands);
// REFACTOR-END
					} else {
						return CommandUtil.create(editingDomain, getParentObject(), property.getFeature(), str.trim());						
					}
				} else if(property.getFeature().getEType().getInstanceClass() == boolean.class){
					Boolean bool = (Boolean)data;
					return CommandUtil.create(editingDomain, getParentObject(), property.getFeature(), bool);
				} else {
					// other types are not handled
					return null;
				}
			} 
		} else {
			if (getParentObject().eGet(property.getReference())== null){
				createReferenceObject(property.getReference(), getReferenceObject(property.getReference()));
			} 
			return CommandUtil.create(editingDomain, (EObject)getParentObject().eGet(property.getReference()), property.getFeature(), data);
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
	 * 
	 */
	public void refreshPropertyMessages() {
		// let the caller implement this
	}
	
	private Object getPropertyValue(SectionAttributePropertyHelper property) {
		Object data = null;	
		if (getParentObject() != null){
			if (property.getReference() != null){
				Object obj = getParentObject().eGet(property.getReference());
				if (obj != null && obj instanceof EObject){
					data = ((EObject)obj).eGet(property.getFeature());
				}
			} else {
				data = getParentObject().eGet(property.getFeature());		
			}
		}
		return data;
	}
	
	/**
	 * @param property
	 */
	private void refreshAttributeFeature(SectionAttributePropertyHelper property){
		Object data = getPropertyValue(property);
		if (property.getControl() != null){
			if (property.getControl() instanceof Button){
				((Button)property.getControl()).setSelection((Boolean)data);
			} else if (property.getControl() instanceof Text){
				if (data == null){
					data = "";
				}
				// Localization
				if (property.isLocalizeable()) {
					throw new UnsupportedOperationException("ATR Refactor the code agains the new translation API");
// REFACTOR-BEGIN
//					Localizable localizable = (Localizable)(property.getAdapterFactory().adapt(getParentObject(), Localizable.class));
//					LocalizableElement element = localizable.getLocalizableElements().get(property.getAttributeIndex());
//					String msg = element.getMessage(element.getDefaultLocale()).getText();
//					if (msg == null || msg.length() ==0){
//						msg = data.toString();
//					}
//					((Text)property.getControl()).setText(msg);		
// REFACTOR-END
				} else {
					((Text)property.getControl()).setText(data.toString());					
				}
			} else if (property.getControl() instanceof Combo){
				Combo combo = (Combo)property.getControl();
				combo.setItems(getEnumerationFeatureValues(property.getEnumeratedValues()));
				combo.select(getEnumerationIndex(property.getEnumeratedValues(), (Enumerator)data));
			}
		}
	
	}
	
	/**
	 * @param ref
	 */
	private void refreshListReferenceFeature(SectionListReferencePropertyHelper ref){
		if (getParentObject() != null){
			if (ref.getParentRef() == null){
				ref.getControl().setParentObject(getParentObject());
			} else {
				ref.getControl().setParentObject((EObject)getParentObject().eGet(ref.getParentRef()));
			}
		}
		ref.getControl().refreshControls();
	}
	
	/**
	 * @param ref
	 */
	private void refreshBrowsePropertyControl(SectionBrowsePropertyHelper ref){
		if (getParentObject() != null){
			ref.getControl().setParentObject(getParentObject());
		}	
		ref.getControl().refreshControls();
	}
	
	/**
	 * refresh all the rendered controls
	 */
	public void refreshControls() {
		for (SectionPropertyHelper feature : features) {
			if (feature instanceof SectionAttributePropertyHelper){
				refreshAttributeFeature((SectionAttributePropertyHelper)feature);
			} else if (feature instanceof SectionBooleanPropertyHelper){
				SectionBooleanPropertyHelper bProp = (SectionBooleanPropertyHelper)feature;
				Object data = null;	
				if (getParentObject() != null){
					data = getParentObject().eGet(bProp.getFeature());
				}
				Boolean bool = (Boolean)data;
				if (bool){
					((Button)bProp.getTrueControl()).setSelection(true);
				} else {
					((Button)bProp.getFalseControl()).setSelection(true);
				}
			} else if (feature instanceof SectionBrowsePropertyHelper){
				refreshBrowsePropertyControl((SectionBrowsePropertyHelper)feature);
			} else if (feature instanceof SectionListReferencePropertyHelper){		
				SectionListReferencePropertyHelper ref = (SectionListReferencePropertyHelper) feature;
				refreshListReferenceFeature(ref);
			} else if (feature instanceof SectionMultiplePropertyRowHelper){
				SectionMultiplePropertyRowHelper multiProperty = (SectionMultiplePropertyRowHelper)feature;
				Map<String, SectionPropertyHelper> mappedFeatures = multiProperty.getMappedFeatures();
				Set<String> keys = mappedFeatures.keySet();
				for (String key : keys) {
					SectionPropertyHelper property = mappedFeatures.get(key);
					if (property instanceof SectionAttributePropertyHelper){
						refreshAttributeFeature((SectionAttributePropertyHelper)property);
					} else if (property instanceof SectionListReferencePropertyHelper){
						refreshListReferenceFeature((SectionListReferencePropertyHelper)property);
					}
				}
			} 
			
		}	
		refreshSiblingControls();
	}
	
	/**
	 * @param reference
	 * @return
	 */
	private EObject getReferenceObject(SectionListReferencePropertyHelper reference) {
		if (getParentObject() != null){
			if (reference.getParentRef() == null){
				return getParentObject();
			} else {
				return (EObject)getParentObject().eGet(reference.getParentRef());
			}
		}
		return null;
	}
	
	/**
	 * @param reference
	 * @return
	 */
	private EObject getReferenceObject(SectionBrowsePropertyHelper reference){
		if (getParentObject() != null){
			if (reference.getReference() == null){
				return getParentObject();
			} else {
				return (EObject)getParentObject().eGet(reference.getReference());
			}
		}
		return null;		
	}
	
	/**
	 * 
	 */
	public void refreshSiblingControls() {
		
	}
	
	/**
	 * @param values
	 * @param value
	 * @return
	 */
	protected int getEnumerationIndex(List<?> values, Enumerator value){
		for (int i = 0; i < values.size(); i++) {
			if(values.get(i).equals(value)){
				return ((Enumerator)values.get(i)).getValue();
			}
		}
		return 1000;		
	}
	
	/**
	 * @param values
	 * @return
	 */
	protected String[] getEnumerationFeatureValues(List<?> values) {
		String[] ret = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			ret[i] = ((Enumerator)values.get(i)).getName();
		}
		return ret;
	}
	
	/**
	 * @param key
	 * @param feature
	 * @return
	 */
	public boolean checkIsAttributeValueTaken(Object key, EStructuralFeature feature) {
		// let the calling class implement this
		return true;
	}
	
	/**
	 * @param data
	 * @return
	 */
	private Integer getIntegerValue(Object data) throws Exception {
		Integer intVal = 0;
		try {
			intVal = new Integer(data.toString());
		} catch (NumberFormatException nfe){
			throw new Exception(nfe);
		}
		return intVal;
	}
	
	/**
	 * @param reference
	 * @return
	 */
	public EObject getReferenceObject(EReference reference){
		return factory.create(reference.getEReferenceType());
	}
	
	/**
	 * helper method - creates the reference object incase the 
	 * reference object is not created with the containing object
	 */
	protected void createReferenceObject(EReference refElement, EObject refObject) {
		CommandUtil.createEReference(editingDomain, getParentObject(), refElement, refObject);
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
	
	/**
	 * @return
	 */
	public Composite getControl() {
		return attributeControlComposite;
	}

}
