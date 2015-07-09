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
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.util.EclipseUtils;

/**
 * To be used in editable table/tree. allows the user to select the attribute in the editable dataset.
 */
public class EditableDatasetAttributeCellEditor extends DialogCellEditor {
	
	/** The Property*/
	private Property property;
	
	private boolean withAssociation = false;
	
	/**
	 * Constructor
	 * 
	 * @param parent 
	 * 			The parent component
	 * @param property
	 * 			The selected property
	 */
    public EditableDatasetAttributeCellEditor(Composite parent, Property property, boolean withAssociation) {
        super(parent);
        this.property = property;
        this.withAssociation = withAssociation;
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
    	Widget widget = property.getWidget();
    	boolean permission = false;
    	if (PropertyTypeConstants.TABLE_FORMAT_ROW_PERMISSION.equals(property.getTypeName())) {
    		permission = true;
    	}
    	Widget table = widget.findAncestor(WidgetTypeConstants.TABLE_TREE);
    	if (table != null) {
    	
	    	Property datasetProperty = table.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
	    	
	    	if (datasetProperty != null && StringUtils.isNotEmpty(datasetProperty.getValue())) {
		    		
	    		// Dialog Title & Message
	    		PropertyType pt = property.getType();
				String title = pt.getDisplayName();
				String message = pt.getDescription();
	    		
	    		// Domain Repository
	    		DomainRepository reposity = 
	    			DomainRepository.getInstance(EclipseUtils.findCurrentProject());
	    		
	    		// Domain Filter
	    		DomainFilter filter = null;
	    		if (withAssociation) {
	    			String type = widget.getTypeName();
	    			if ("TableColumnSearch".equals(type) || "TableColumnCombobox".equals(type)) {
			    		filter = DomainFilterHelper.createAcceptFilter(
			    				DomainFilterEnum.DATASET_PROPERTY_ATTRIBUTE,
			         			DomainFilterEnum.DATASET_PROPERTY_LINKED_DATASET
			    				);
	    			} else {
			    		filter = DomainFilterHelper.createAcceptFilter(
			    				DomainFilterEnum.DATASET_PROPERTY_ATTRIBUTE
			    				);	    				
	    			}
	    		} else {
	    			if (permission) {
			    		filter = DomainFilterHelper.createAcceptFilter(
			    				DomainFilterEnum.DATASET_PROPERTY_ATTRIBUTE,
			         			DomainFilterEnum.DATASET_DERIVED_PROPERTY_ATTRIBUTE,
			         			DomainFilterEnum.BOOLEAN_TYPE
			    				);
	    				
	    			} else {
			    		filter = DomainFilterHelper.createAcceptFilter(
			    				DomainFilterEnum.DATASET_PROPERTY_ATTRIBUTE,
			         			DomainFilterEnum.DATASET_DERIVED_PROPERTY_ATTRIBUTE
			    				);
	    				
	    			}
	    		}
	    		
	            // Domain Entity Name & Association Name  
	    		MdfName entityName = MdfNameFactory.createMdfName(datasetProperty.getValue());  
	    		String fqn = datasetProperty.getValue() + DomainConstants.ENTITY_SEPARATOR + property.getValue();
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
	    		if (withAssociation) {
	    			dialog.setTreeCollapsed(true);
	    			dialog.setAcceptChildrenOfDatasetMappedProperty(true);
	    		}
	
	    		if (Dialog.OK == dialog.open()) {
	        		result = dialog.getSelectedPath();
	        	}
	    	} else {
	
	    		// We cannot select an attribute if we cannot find the editable dataset
	    		MessageDialog.openError(
	    				cellEditorWindow.getShell(), 
	    				"Error", "No Editable Dataset defined on this Table/Tree");
	    		
	    	}
    	}
    	
	    return result;
    	
    }	
}
