package com.odcgroup.t24.menu.editor.properties;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.odcgroup.t24.menu.menu.Enabled;
import com.odcgroup.t24.menu.menu.MenuItem;
import com.odcgroup.t24.menu.menu.MenuPackage;
import com.odcgroup.t24.menu.menu.MenuRoot;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.section.AbstractModelPropertySection;
import com.odcgroup.workbench.editors.properties.widgets.CheckboxPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.ComboPropertyWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleGroupWidget;
import com.odcgroup.workbench.editors.properties.widgets.SimpleTextWidget;

/**
 * @author pkk
 */
public class MenuGeneralPropertySection extends AbstractModelPropertySection {

	@Override
	protected void configureProperties() {
		
		SimpleGroupWidget group = new SimpleGroupWidget(null);
		// name attribute
		SimpleTextWidget menuName = new SimpleTextWidget(
				MenuPackage.eINSTANCE.getMenuItem_Name(), "Name", false);
		menuName.setFillHorizontal(true);
		group.setFillBoth(false);
		group.addPropertyFeature(menuName);
		
		// security role
		SimpleTextWidget role = new SimpleTextWidget(
				MenuPackage.eINSTANCE.getMenuItem_SecurityRole(), "Security Role");
		role.setFillHorizontal(true);
		group.addPropertyFeature(role);		
		
		// enabled
		ComboPropertyWidget enabled = new ComboPropertyWidget(MenuPackage.eINSTANCE.getMenuItem_Enabled(), "Enabled") {
			
			@Override
			public String getItemDisplayText(Object element) {				
				return ((Enabled) element).getName();
			}
			
			@Override
			public Object[] getComboItems(Object element) {
				return Enabled.VALUES.toArray();
			}
		};
		group.addPropertyFeature(enabled);
		
		// displaytabs
		CheckboxPropertyWidget tabs = new CheckboxPropertyWidget(MenuPackage.eINSTANCE.getMenuItem_DisplayTabs(), "Display Children as Tabs", Boolean.TRUE);
		group.addPropertyFeature(tabs);
		
		// shortcut
		SimpleTextWidget shortcut = new SimpleTextWidget(
				MenuPackage.eINSTANCE.getMenuItem_Shortcut(), "Keyboard Shortcut");
		shortcut.setFillHorizontal(true);
		group.addPropertyFeature(shortcut);
		
		this.addPropertyFeature(group);
		
		
	

	}

	
	protected void initiateControls() {
	    super.initiateControls();
		int level=0;
		//get the level of the menu.if level =3 then enable the dispaly childern as tab in property view..
		if (input != null) {
			if(input instanceof MenuItem){
			 	level=getMenuLevel((MenuItem)input, level);
			}
		}
		//enable the dispaly childern as tab checkbox.
		enableDisplayChildernTab(level);
	}	
	/**
	 * enbale the dispaly childern as tab checkbox if level=3
	 * @param level
	 */
	private void enableDisplayChildernTab(int level){
		for (IPropertyFeature widget:getPropertyFeatures()){
			   if(widget instanceof SimpleGroupWidget){
				   for(IPropertyFeature feature:((SimpleGroupWidget)widget).getPropertyFeatures()){
				      if(feature instanceof CheckboxPropertyWidget ){
				    	 if(level==3){
					   ((CheckboxPropertyWidget)feature).setEnable(true);
					   
				    	 }else{
				    		 ((CheckboxPropertyWidget)feature).setEnable(false);
				    	 }
				      }
				   }
			  }
		  }
		
	}
 /**
  * get the level of the menu in the tree.	
  * @param item
  * @param level
  * @return
  */
  private int getMenuLevel(MenuItem item, int level){
	 EObject parent= item.eContainer();
	 if (parent == null) {
		return level;
	 }
	 level++;
	 if (parent instanceof MenuRoot) {
		return level;
	 }
	 level = getMenuLevel((MenuItem)parent, level);
	 return level; 
  }

  @Override
  public void propertyChanged(IPropertyFeatureChangeEvent event) {
      Object val = event.getValue();
      EStructuralFeature feature = event.getPropertyFeature().getStructuralFeature();
      if (feature instanceof EReference) {
    	  if (val != null && val instanceof IEObjectDescription) {
    		  val = ((IEObjectDescription)val).getEObjectOrProxy();
    	  }
      }
      IEditingDomainProvider provider = (IEditingDomainProvider) getPart().getAdapter(IEditingDomainProvider.class);
      if (provider != null) {
		  EditingDomain editingDomain = provider.getEditingDomain();	
		  Command cmd = SetCommand.create(editingDomain, getInput(), feature, val);
		  if (cmd != null) {
		      editingDomain.getCommandStack().execute(cmd);
		  }
      }
  }
}
