package com.odcgroup.page.ui.properties;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author phanikumark
 *
 */
public class EditableDatasetPropertySourceAdapter extends
		AbstractPropertySourceAdapter {

	public EditableDatasetPropertySourceAdapter(Property property,
			CommandStack commandStack) {
		super(property, commandStack);
	}

	/**
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#getPropertyValue()
	 */
	public Object getPropertyValue() {
    	Property prop = getProperty();
    	String value = prop.getValue();
//        if (StringUtils.isEmpty(value)) {
//    		Widget rw = prop.getWidget().getRootWidget();
//        	Property entity = rw.findProperty(PropertyTypeConstants.DOMAIN_ENTITY);
//        	if(StringUtils.isNotEmpty(entity.getValue()) 
//        			&& !EditableDatasetUtil.entityHasSQLNameAnnotation(prop, entity.getValue())) {
//        		value = entity.getValue();
//        	}
//       		setPropertyValue(value);
//    	}
		return getProperty().getValue();			
	}

	/**
	 * @see com.odcgroup.page.ui.properties.PropertySourceAdapter#setPropertyValue(java.lang.Object)
	 */
	public void setPropertyValue(Object newValue) {		
		String newVal = "";
        if (newValue != null) {
        	newVal = (String)newValue;
        }
		
    	String oldValue = getProperty().getValue();
    	if (oldValue == null) {
    		oldValue = "";
    	}
    	
    	if (! oldValue.equals(newVal)) {
    		UpdatePropertyCommand upc = new UpdatePropertyCommand(getProperty(), (String)newValue);
        	getCommandStack().execute(upc);    
        	
        	Property iden = getProperty().getWidget().findProperty(PropertyTypeConstants.TABLE_EDITABLE_ROW_IDENTIFIER);
        	if (iden != null) {
        		IOfsProject ofsProject = OfsResourceHelper.getOfsProject(getProperty().eResource());
        		DomainRepository repository = DomainRepository.getInstance(ofsProject);
        		MdfDataset ds = repository.getDataset(MdfNameFactory.createMdfName(newVal));
        		if (ds != null) {
        			UpdatePropertyCommand upcm = new UpdatePropertyCommand(iden, getPrimaryKey(ds));
        			getCommandStack().execute(upcm);
        		}
        	}
    	}
	}
	
	@SuppressWarnings("unchecked")
	private String getPrimaryKey(MdfDataset ds) {
		String key = "";
		List<MdfDatasetProperty> props = ds.getProperties();
		for (MdfDatasetProperty dp : props) {
			if (dp instanceof MdfDatasetMappedProperty) {
				MdfDatasetMappedProperty mp = (MdfDatasetMappedProperty) dp;
				String path = mp.getPath();
				MdfProperty prop = ds.getBaseClass().getProperty(path);
				if (prop != null && prop.isPrimaryKey()) {
					return prop.getName();
				}
			}
		}
		return key;
	}

}
