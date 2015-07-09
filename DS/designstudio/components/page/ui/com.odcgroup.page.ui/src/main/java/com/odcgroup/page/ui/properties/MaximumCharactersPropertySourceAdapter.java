package com.odcgroup.page.ui.properties;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.util.EclipseUtils;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Specialized PropertySourceAdapter for Domain Attributes and Associations.
 * 
 * @author atr
 */
public class MaximumCharactersPropertySourceAdapter extends DefaultPropertySourceAdapter  {
	
    @Override
    public void resetPropertyValue() {

    	// default value
   		String newValue = getProperty().getType().getDefaultValue();

   		Widget widget = getWidget();
		if (widget.isDomainWidget()) {
			// attempt to retrieve the default value from the domain
			IOfsProject ofsProject = EclipseUtils.findCurrentProject();
			DomainRepository repository = DomainRepository.getInstance(ofsProject);
			if (repository != null) {
				MdfDatasetProperty attribute = (MdfDatasetProperty) widget.findDomainObject(repository);
				if (attribute != null) {
					String value = ModelAnnotationHelper.getDisplayLength(attribute.getParentDataset(), attribute);
					if (value != null) {
						newValue = value;
					}
				}
			}
    	}

    	// reset the value
		Command cmd = new UpdatePropertyCommand(getProperty(), newValue);
        getCommandStack().execute(cmd);

    }

    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public MaximumCharactersPropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
}