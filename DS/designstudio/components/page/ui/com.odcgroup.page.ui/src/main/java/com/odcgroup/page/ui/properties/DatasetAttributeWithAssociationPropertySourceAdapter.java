package com.odcgroup.page.ui.properties;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.CHARS;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FORMAT;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.TYPE;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.util.EclipseUtils;

/**
 * Specialized PropertySourceAdapter for Dataset Attributes for editable widget within a Table/Tree
 */
public class DatasetAttributeWithAssociationPropertySourceAdapter extends AbstractPropertySourceAdapter  {
	
	
	private class PathWalker extends PathVisitor {
		private final MdfDataset root;
		private MdfEntity type;
		private MdfProperty prop = null;

		private PathWalker(MdfDataset root) {
			this.root = root;
			this.type = root.getBaseClass();
		}

		public boolean visit(String name) {
			if (type instanceof MdfClass) {
				prop = ((MdfClass) type).getProperty(name);

				if (prop == null) {
					prop = ModelHelper.getReverseAssociation(root.getParentDomain(), (MdfClass) type, name);
				}

				if (prop == null) {
					return false;
				} else {
					type = prop.getType();
					return true;
				}
			} else {
				return false;
			}
		}

		public MdfProperty getProperty() {
			return prop;
		}
	}

    /**
     * Gets the value of the property.
     * 
     * @return Object The value of the property
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public Object getPropertyValue() {
        String s = getProperty().getValue();
        return s == null ? "" : s;
    }
    
	private String getDisplayFormat(MdfEntity entity) {
		String format = "";
		if (entity instanceof MdfBusinessType) {
			MdfBusinessType bizType = (MdfBusinessType)entity;
			format = GUIAspect.getDisplayFormatValue(bizType);
			if (format == null) {
				format = "";
			}
		}
		return format;
	}
	
    private MdfProperty getProperty(String path) {
    	MdfProperty dsp = null;
    	Widget wTable = getProperty().getWidget().findAncestor(WidgetTypeConstants.TABLE_TREE);
    	if (wTable != null) {
			Property prop = wTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
			if (StringUtils.isNotEmpty(datasetName)) {
				DomainRepository repository = DomainRepository.getInstance(EclipseUtils.findCurrentProject());
				MdfDataset dataset = repository.getDataset(MdfNameFactory.createMdfName(datasetName));
				if (dataset != null) {
					PathWalker walker = new PathWalker(dataset);
					PathVisitor.visitPath(path, walker);
					dsp = walker.getProperty();
				}
			}
		}
		return dsp;
    }
    
    /**
     * Sets the value of the property.
     * 
     * @param newValue
     *            The value of the property
     *            
     * @see IPropertySource#setPropertyValue(Object, Object)
     */
    public void setPropertyValue(Object newValue) {
    	
        String domainAttributeName = "";
        if (newValue != null) {
        	domainAttributeName = String.valueOf(newValue);
        	domainAttributeName = domainAttributeName.trim();
        }
		
        CompoundCommand compoundCommand = new CompoundCommand("Change Dataset Attribute");
        
        MdfProperty dsp = getProperty(domainAttributeName);
        Widget widget = getWidget();
        widget.setPropertyValue(TYPE, "");
        if (dsp != null) {
			if (dsp.getType() instanceof MdfBusinessType) {
				MdfBusinessType bizType = (MdfBusinessType)dsp.getType();
				List<MdfAnnotationProperty> properties = GUIAspect.getWidgetProperties(bizType);
				for (MdfAnnotationProperty ap : properties) {
					widget.setPropertyValue(ap.getName(), ap.getValue());
				}
			}
			widget.setPropertyValue(FORMAT, getDisplayFormat(dsp.getType()));
        }

        
        // update property with the new name
        compoundCommand.add(new UpdatePropertyCommand(getProperty(), domainAttributeName));

        // update dependent property

    	// update all properties
    	getCommandStack().execute(compoundCommand);
    }

	@Override
    public void resetPropertyValue() {
    	Property attribute = getProperty();
    	if (!attribute.isReadonly()) {

			CompoundCommand cc = new CompoundCommand("Reset Dataset Attribute");
			
	    	// reset the attribute
	    	String oldValue = attribute.getValue();
	   		String newValue = attribute.getType().getDefaultValue();
	    	if (! oldValue.equals(newValue)) {
	    		cc.add(new UpdatePropertyCommand(attribute, newValue));
	    	}

	    	// reset dependent properties
	    	Property maxChars = getWidget().findProperty(PropertyTypeConstants.CHARS);
	    	if (maxChars != null) {
		    	oldValue = maxChars.getValue();
		   		newValue = maxChars.getType().getDefaultValue();
		    	if (! oldValue.equals(newValue)) {
		    		cc.add(new UpdatePropertyCommand(maxChars, newValue));
		    	}
	    	}
	    	
	        getCommandStack().execute(cc);

    	}
    }

    /**
     * Creates a new DomainAttributePropertySourceAdapter.
     * 
     * @param property The Property
     * @param commandStack The command stack to execute the command
     */
    public DatasetAttributeWithAssociationPropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
}