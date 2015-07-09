package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ext.constraints.ConstraintsAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.TextfieldPropertyFilter;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * PrecisionScalePropertySourceAdapter get the precision and scale values.
 * @author snn
 *
 */
public class PrecisionScalePropertySourceAdapter extends
		AbstractPropertySourceAdapter {
    /**
     * PrecisionScalePropertySourceAdapter constructor.
     * @param property
     * @param commandStack
     */
	public PrecisionScalePropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
		
	}
	
	
	/**
	 * get the property value.
	 */
	public Object getPropertyValue() {
		
		Property prop = getProperty();
	    String value = prop.getValue();

	    if (value.isEmpty()) {
	    	// derive the precision/scale from the the domain
			
			// Retrieves names for dataset & dataset attribute
			String datasetName = "";
			String datasetAttributeName = "";
	    	Widget wTable = getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
	    	if (wTable != null) {
	    		// the widget are in an editable table/tree
	    		Property attrProp = getWidget().findProperty(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
	    		datasetAttributeName = attrProp != null ? attrProp.getValue() : "";
				Property propDaset = wTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
				datasetName = propDaset != null ? propDaset.getValue() : "";
	    	} else {
	    		Widget root = getWidget().getRootWidget();
	    		datasetName = root.getPropertyValue(PropertyTypeConstants.DOMAIN_ENTITY);
	    		datasetAttributeName = getWidget().getPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE);
	    	}
				
	    	// Load the dataset property
	    	MdfDatasetProperty dsp = null;
	    	if (StringUtils.isNotEmpty(datasetName) && StringUtils.isNotEmpty(datasetAttributeName)) {
				IOfsProject ofsProject = OfsResourceHelper.getOfsProject(getWidget().eResource());
				DomainRepository repository = DomainRepository.getInstance(ofsProject);
				MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
				if (dataset != null) {
					dsp = dataset.getProperty(datasetAttributeName);
				}
	    	}
	    	
	    	if (dsp != null) {
				MdfAnnotationProperty property = null;
				if (dsp != null) {
					MdfEntity entity = dsp.getType();
					if (entity instanceof MdfBusinessType) {
						MdfAnnotation constraints=ConstraintsAspect.getConstraints((MdfBusinessType)entity);
						if (constraints != null){
							//get the precision /scale property .
							property = constraints.getProperty(getProperty().getTypeName());
						}
						 
					}
				}
				if (property != null) {
					//get the precision/scale value.
					value=property.getValue() + " " + TextfieldPropertyFilter.BUSINESS_TYPE;
				}
			}
			
		}
	    
		return value;
	}

   /**
    * set the property value.
    */
	public void setPropertyValue(Object newValue) {
		String da = "";
		if (newValue != null) {
			da = String.valueOf(newValue);
		}

		String oldValue = getProperty().getValue();
		if (oldValue == null) {
			oldValue = "";
		}
        if (!oldValue.equals(da)) {
			UpdatePropertyCommand upc = new UpdatePropertyCommand(getProperty(), da);
			getCommandStack().execute(upc);
		}

	}

}
