package com.odcgroup.workbench.editors.properties.controls;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.odcgroup.workbench.editors.properties.choice.ChoiceGroup;
import com.odcgroup.workbench.editors.properties.choice.ChoiceItem;
import com.odcgroup.workbench.editors.properties.choice.ReferenceAttributesItem;
import com.odcgroup.workbench.editors.properties.choice.ReferenceListItem;
import com.odcgroup.workbench.editors.properties.util.CommandUtil;
import com.odcgroup.workbench.editors.properties.util.SectionListReferencePropertyHelper;
import com.odcgroup.workbench.editors.ui.OFSUIFactory;

/**
 * @author pkk
 *
 */
public abstract class ChoiceReferencePropertyControl implements IPropertyControl {
	
	private ChoiceGroup choiceGroup = null;
	private Composite parentComposite = null;
	private Map<ChoiceItem, Button> buttonMap = new LinkedHashMap<ChoiceItem, Button>();
	private TabbedPropertySheetPage propertySheetPage = null;
	private EObject parentObject = null;
	protected TransactionalEditingDomain editingDomain;
	private ListReferencePropertyControl listReferenceControl = null;
	private AttributePropertiesControl attrControl = null;
	private EFactory factory = null;
	private Composite body = null;
	private ChoiceItem selection = null;
	
	
	/**
	 * @param title
	 * @param body
	 * @param choiceReferences
	 */
	public ChoiceReferencePropertyControl(ChoiceGroup choiceGroup, Composite parentComp, TabbedPropertySheetPage propertySheetPage, 
			TransactionalEditingDomain editingDomain, EFactory factory, EObject parentObject){
		this.choiceGroup = choiceGroup;
		this.parentComposite = OFSUIFactory.INSTANCE.createComposite(parentComp);
		this.parentComposite.setLayout(new ColumnLayout());
		this.propertySheetPage = propertySheetPage;
		this.editingDomain = editingDomain;
		this.factory = factory;
		this.parentObject = parentObject;
	}	
	
	/**
	 * 
	 */
	public void ceateChoiceReferencePropertyControl() {
		if (choiceGroup != null){
			List<ChoiceItem> items = choiceGroup.getChoiceItems();
			Composite choiceComp = OFSUIFactory.INSTANCE.createCompositeWithRowLayout(parentComposite);
			choiceComp.setBackground(parentComposite.getBackground());
    		RowData data = null;
			Label emptyLabel = new Label(choiceComp, SWT.NONE);
			emptyLabel.setBackground(choiceComp.getBackground());
			data = new RowData();
			data.width = 10;
			emptyLabel.setText("");
			emptyLabel.setLayoutData(data);
    		Label label = new Label(choiceComp, SWT.NONE);
    		label.setBackground(choiceComp.getBackground());
			data = new RowData();
    		data.width = 150;
    		label.setLayoutData(data);
    		label.setText(choiceGroup.getLabel()+" : ");
    		ChoiceItem item = null;
	    	Button choiceBtn = null;          
    		for(int i=0;i<items.size();i++){
    			item = items.get(i);
    			choiceBtn = new Button(choiceComp, SWT.RADIO);
    			choiceBtn.setBackground(choiceComp.getBackground());
				choiceBtn.setText(item.getChoiceLabel());
				choiceBtn.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						handleButtonSelection(e.widget);
					}
				});
				buttonMap.put(item, choiceBtn);		
				if (! (i== items.size())){
					OFSUIFactory.INSTANCE.createLineWithRowData(choiceComp, 50);
				}
    		} 
    		createControlBodyComposite();
		}		
	}
	
	/**
	 * @param listItem
	 */
	private void createReferenceListControl(Composite body, ReferenceListItem listItem) {
		SectionListReferencePropertyHelper refProperty = new SectionListReferencePropertyHelper(listItem.getChoiceLabel(), listItem.getChoiceReference(), null, listItem.getFactory(), listItem.getReferenceClass());
		listReferenceControl = new ListReferencePropertyControl(body, getParentObject(), null, refProperty, editingDomain);        	
    	listReferenceControl.createListReferencePropertyControls(true); 
    	listReferenceControl.setPropertySheetPage(propertySheetPage);	
    	listReferenceControl.refreshControls();
	}
	
	
	/**
	 * @param attItem
	 */
	private void createReferenceAttributesControl(Composite body, ReferenceAttributesItem attItem){
		attrControl = new AttributePropertiesControl(body, attItem.getStructuralfeatures(), editingDomain, propertySheetPage, getFactory(), getParentObject()){
			public boolean checkIsAttributeTaken(Object key, EStructuralFeature feature) {
				return checkAttributeTaken(key, feature);
			}

			@Override
			public void updateChanges(Object control) {
				updateAttributeControlChanges(control);				
			}
		};
		attrControl.createAttributePropertyControls();
		attrControl.setParentObject(getParentObject());
		attrControl.refreshControls();
	}
	
	/**
	 * @param control
	 */
	private void updateAttributeControlChanges(Object control) {
		updateChanges(control);
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#updateChanges(java.lang.Object)
	 */
	public abstract void updateChanges(Object control);

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.controls.IPropertyControl#getUpdateCommand(java.lang.Object)
	 */
	public ICommand getUpdateCommand(Object control) {		
		return attrControl.getUpdateCommand(control);
	}

	/**
	 * 
	 */
	public void refreshControls() {
		if (choiceGroup != null){	
    		if (listReferenceControl != null) {
    			listReferenceControl.setParentObject(parentObject);	
    		}
    		if (attrControl != null){
    			attrControl.setParentObject(parentObject);
    		}
			List<ChoiceItem> items = choiceGroup.getChoiceItems();
			ChoiceItem item = null;
			if (selection != null) {
				handleChoiceSelection(selection);
			} else {
				boolean assigned = false;
				for(int i=0;i<items.size();i++){
					item = items.get(i);
					Object obj = getParentObject().eGet(item.getChoiceReference());
					if (obj != null){
						if (!(obj instanceof List) || (obj instanceof List && !((List)obj).isEmpty())) {
							assigned = true;
							buttonMap.get(item).setSelection(true);
							deSelectOtherReferences(item);
							refreshChoiceSelection(item);
							break;
						} 
					}
				}
				if (!assigned){
					for(int i=0;i<items.size();i++){
						item = items.get(i);
						if (choiceGroup.isDefaultItem(item)){
							buttonMap.get(item).setSelection(true);
							deSelectOtherReferences(item);
							handleChoiceSelection(item);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param defaultItem
	 */
	public void deSelectOtherReferences(ChoiceItem defaultItem) {

		List<ChoiceItem> items = choiceGroup.getChoiceItems();
		ChoiceItem item = null;
		for(int i=0;i<items.size();i++){
			item = items.get(i);
			if (!defaultItem.equals(item)){
				buttonMap.get(item).setSelection(false);
			}
		}
	}
	
	/**
	 * @param key
	 * @param feature
	 * @return
	 */
	public boolean checkAttributeTaken(Object key, EStructuralFeature feature){
		return true;
	}


	/**
	 * @param control
	 */
	public void handleButtonSelection(Widget control) {
		if (control instanceof Button){
			Button btn = (Button)control;
			if (btn.getSelection()){				
				ChoiceItem item = getChoiceItem(btn);
				selection = item;
				List<ChoiceItem> items = choiceGroup.getChoiceItems();
				ChoiceItem existingItem = null;
				for(int i=0;i<items.size();i++){
					ChoiceItem chItem = items.get(i);
					Object obj = getParentObject().eGet(chItem.getChoiceReference());
					if (obj != null){
						if (!(obj instanceof List) || (obj instanceof List && !((List)obj).isEmpty())) {
							existingItem = chItem;
							break;
						} 
					}
				}
				if (existingItem != null && !existingItem.equals(item)){
					boolean okpressed = MessageDialog.openConfirm(getShell(), 
							"Confirm", 
							"Are you sure you want to delete reference <<"
							+existingItem.getChoiceLabel()+">>?");
					if (okpressed){
						TransactionalEditingDomain domain = (TransactionalEditingDomain) getEditingDomain();
						final ChoiceItem itm = existingItem;
						IStatus status = CommandUtil.execute(domain, "UpdateRef", getParentObject(), new Runnable() {
								public void run() {
									Object obj = getParentObject().eGet(itm.getChoiceReference());
									if (obj instanceof List){
										((List)obj).clear();
									} else {
										getParentObject().eSet(itm.getChoiceReference(), null);
									}								
								}							
							});		
						if (status.getCode()==1){
							selection = null;
							refreshControls();
							return;
						}
						handleChoiceSelection(item);
						refreshSectionMessage();
					} else {
						btn.setSelection(false);
						buttonMap.get(existingItem).setSelection(true);
						return;
					}
				} else {
					handleChoiceSelection(item);
				}
				
			}
			
		}		
	}	
	

	public void refreshSectionMessage() {
		// no implementation
	}
	
	/**
	 * @param item
	 */
	private void handleChoiceSelection(ChoiceItem item) {
		if (item != null){
			dispose();
			if (item instanceof ReferenceListItem){
	    		ReferenceListItem listItem = (ReferenceListItem)item;
	        	createReferenceListControl(body, listItem);
        		listReferenceControl.refreshControls();
	        } else if (item instanceof ReferenceAttributesItem){	    		
	        	ReferenceAttributesItem attItem = (ReferenceAttributesItem)item;
	        	createReferenceAttributesControl(body, attItem);
				attrControl.refreshControls();
	        }	
        	parentComposite.layout();
		}		
	}
	
	
	public void dispose(){
		listReferenceControl = null;
		attrControl = null;
		body.dispose();
		body = null;
		createControlBodyComposite();
	}
	
	private void createControlBodyComposite() {
		body = OFSUIFactory.INSTANCE.createComposite(parentComposite);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.verticalAlignment = SWT.BEGINNING;
		data.horizontalAlignment = SWT.LEFT;
		data.widthHint = 900;
		data.heightHint = 250;
		body.setLayoutData(data);
	}
	
	/**
	 * @param item
	 */
	private void refreshChoiceSelection(ChoiceItem item){
		if (item != null){
			dispose();
			if (item instanceof ReferenceListItem){
				if (listReferenceControl != null) {
	        		listReferenceControl.refreshControls();
	        	} else {
		    		ReferenceListItem listItem = (ReferenceListItem)item;
		        	createReferenceListControl(body, listItem);
		        	parentComposite.layout();	        		
	        	}
	        } else if (item instanceof ReferenceAttributesItem){
	        	if (attrControl != null){
					attrControl.refreshControls();
				} else {
		        	ReferenceAttributesItem attItem = (ReferenceAttributesItem)item;
		        	createReferenceAttributesControl(body, attItem);
		        	parentComposite.layout();
				}
	        }	
		}				
	}
	
	/**
	 * @param btn
	 * @return
	 */
	private ChoiceItem getChoiceItem(Widget btn) {
		if (choiceGroup != null){
			List<ChoiceItem> items = choiceGroup.getChoiceItems();
    		ChoiceItem item = null;
    		for(int i=0;i<items.size();i++){
    			item = items.get(i);
    			if (buttonMap.get(item).equals(btn)){
    				return item;
    			}
    		}
		}
		return null;
	}	
	
	/**
	 * @return
	 */
	private Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	public EObject getParentObject() {
		return parentObject;
	}

	public void setParentObject(EObject parentObject) {
		this.parentObject = parentObject;
	}

	public TransactionalEditingDomain getEditingDomain() {
		return editingDomain;
	}

	public void setEditingDomain(TransactionalEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	public EFactory getFactory() {
		return factory;
	}
	
	public Composite getControl() {
		return parentComposite;
	}

}
