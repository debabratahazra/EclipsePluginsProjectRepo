package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.domain.filter.DomainFilter;
import com.odcgroup.page.domain.filter.DomainFilterEnum;
import com.odcgroup.page.domain.filter.DomainFilterHelper;
import com.odcgroup.page.domain.ui.dialog.DomainChooserDialog;
import com.odcgroup.page.mdf.DomainConstants;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.util.EclipseUtils;

/**
 * Defines the context cell editor for the property context
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class DomainAttributeCellEditor extends DialogCellEditor {
	
	/** The Property*/
	private Property property;
	
	/**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property
	 * 			The selected property
	 */
    public DomainAttributeCellEditor(Composite parent, Property property) {
        super(parent);
        this.property = property;
    }
    
    /**
     * Open dialog window
     * 
     * @param cellEditorWindow 
     * 			The cell editor window
     * @return Object
     */
    protected Object openDialogBox(Control cellEditorWindow) {
    	
    	String result = property.getValue();
    	
    	Widget rw = property.getWidget().getRootWidget();
    	Property entity = rw.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
    	
    	if (entity != null && StringUtils.isNotEmpty(entity.getValue())) {
	    		
    		// Dialog Title & Message
    		PropertyType pt = property.getType();
			String title = pt.getDisplayName();
			String message = pt.getDescription();
    		
    		// Domain Repository
    		DomainRepository reposity = 
    			DomainRepository.getInstance(EclipseUtils.findCurrentProject());
    		
    		// Domain Filter
    		DomainFilter filter = DomainFilterHelper.createAcceptFilter(
    				DomainFilterEnum.DATASET_PROPERTY_ATTRIBUTE,
         			DomainFilterEnum.DATASET_DERIVED_PROPERTY_ATTRIBUTE   				
    				);  
    		
            // Domain Entity Name & Association Name  
    		MdfName entityName = MdfNameFactory.createMdfName(entity.getValue());  
    		String fqn = entity.getValue() + DomainConstants.ENTITY_SEPARATOR + property.getValue();
    		MdfName attributeName = MdfNameFactory.createMdfName(fqn);    		

    		// Dialog
    		DomainChooserDialog dialog = new DomainChooserDialog(
    				cellEditorWindow.getShell(), 
    				title,
    				message,
        			reposity,
        			filter, 
        			entityName,
        			attributeName);

    		if (Dialog.OK == dialog.open()) {
        		result = dialog.getSelectedPath();
        	}
    	} else {

    		// We cannot select an attribute if we cannot find the entity
    		MessageDialog.openError(
    				cellEditorWindow.getShell(), 
    				"Error", "No Dataset defined");
    		
    	}
	    
	    return result;
    	
    }	
}
