package com.odcgroup.page.ui.properties;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.odcgroup.page.metamodel.AccountabilityRule;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.dialog.PageModelSelectionDialog;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.helper.OfsEditorUtil;

/**
 * Cell editor for editing the property IncludeSrc.
 * 
 * @author Gary Hayes
 */

public class IncludeSrcCellEditor extends DialogCellEditor {
    
    /** The Property being edited. */
    private Property property;
	
	/**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property The property being edited
	 */
    public IncludeSrcCellEditor(Composite parent, Property property) {
        super(parent);
        this.property = property;
    }
    
    protected Control createControl(Composite parent) {
	final Control control=super.createControl(parent);
	Control[] childern=((Composite)control).getChildren();
	if(childern.length>1&&!childern[0].isDisposed()){
	    childern[0].addListener(SWT.MouseDoubleClick, new Listener() {
		public void handleEvent(Event event) {
		    openModelInEditor();
		}
	    });
	}   
	return control;
    }

    /**
     * Open dialog window
     * 
     * @param cellEditorWindow 
     * 			The cell editor window
     * @return Object The path
     */
    protected Object openDialogBox(Control cellEditorWindow) { 
        Set<String> fe = findFileExtensions();
        PageModelSelectionDialog dialog=null;
        if(property.getTypeName().equals("includeXtooltipFragment")){
        	dialog=PageModelSelectionDialog.createDialog(fe,PageModelSelectionDialog.XTOOLTIP_FRAGMENT_FILTER);
        }else{
        	dialog=PageModelSelectionDialog.createDialog(fe);
          }
    	dialog.open();
    	Object[] result = dialog.getResult();
    	
    	if (result == null || result.length == 0) {
    		return getValue();
    	}
    	
    	return result[0];
    }
    
    /**
     * Finds the allowed file extensions. This is determined by the IncludeabilityRules.
     * 
     * @return Set of String's
     */
    private Set<String> findFileExtensions() {
    	 Set<String> result = new HashSet<String>();
    	//if property is included Xtooltip fragment filter the Xtooltip type fragments.
    	if(property.getTypeName().equals("includeXtooltipFragment")){
    		 result.add("fragment");
    		 return result;
    	}
        // Find all the child WidgetType's which are allowed in the parent type
       
        Widget rw = property.getWidget().getRootWidget();
        MetaModel metamodel = MetaModelRegistry.getMetaModel(); 
        for (AccountabilityRule ar : metamodel.getIncludeability().getRules()) {
            if (ar.getParent() == rw.getType()) {
                result.add(ar.getChild().getName().toLowerCase());
            }
        }
        
        return result;
    }
    /**
     * open the model element in the Editor.
     */
    private void openModelInEditor(){
	String resourceFile=(String)getValue();
	if(resourceFile!=null && StringUtils.isNotEmpty(resourceFile)){
	    Resource resource=property.getWidget().eResource();
	    IOfsProject project=OfsResourceHelper.getOfsProject(resource);
	    URI uri=URI.createURI(resourceFile);
	    IOfsModelResource ofsResource = project.getModelResourceSet().getOfsModelResource(uri);
	    if(ofsResource!=null){
		OfsEditorUtil.openEditor(ofsResource);
	    }
	}
    }
}
