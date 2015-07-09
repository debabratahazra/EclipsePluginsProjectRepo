package com.odcgroup.page.ui.properties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.mdf.ecore.MdfEnumValueImpl;
import com.odcgroup.mdf.ecore.MdfEnumerationImpl;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.UniqueIdGenerator;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.ui.command.CreateWidgetCommand;
import com.odcgroup.page.ui.command.DeleteWidgetCommand;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;
import com.odcgroup.page.ui.util.DomainObjectUtils;

/**
 * Specialized PropertySourceAdapter for Domain Attributes and Associations.
 * 
 * @author atr
 */
public class DomainAttributePropertySourceAdapter extends AbstractPropertySourceAdapter  {
	
	private boolean READ_ONLY = true;

	protected String getDisplayLength(String attributeName) {
		String displayLength = null;
    	MdfDataset dataset = DomainObjectUtils.getDataset(getWidget());
    	if (dataset != null) {
    		displayLength = ModelAnnotationHelper.getDisplayLength(dataset, attributeName);
    	}
    	return displayLength;
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

        String widgetTypeNAme = getWidget().getType().getName(); 
        if(widgetTypeNAme.equals(WidgetTypeConstants.RADIO_BUTTON)) {
        	createRadioButtons(domainAttributeName, compoundCommand);
        } else if (widgetTypeNAme.equals(WidgetTypeConstants.CHECKBOX)) {
            // DS-4763
        	createCheckBoxes(domainAttributeName, compoundCommand);
        }
        
        // update property with the new name
        compoundCommand.add(new UpdatePropertyCommand(getProperty(), domainAttributeName));

        // update dependent property
    	Property maxChars = getWidget().findProperty(PropertyTypeConstants.CHARS);
    	if (maxChars != null) {
    		String displayLen = maxChars.getType().getDefaultValue();
    		if (!domainAttributeName.isEmpty()) {
    			String len = getDisplayLength(domainAttributeName);
    			if (len != null) {
    				displayLen = len;
    			}
    		}
    		compoundCommand.add(new UpdatePropertyCommand(maxChars, displayLen));
    	}

    	// update all properties
    	getCommandStack().execute(compoundCommand);
    }


    // DS-4763
    private void createCheckBoxes(String domainAttributeName, CompoundCommand compoundCommand) {
		MdfEntity type = getPropertyType(domainAttributeName);
		if(type instanceof MdfEnumerationImpl) {
			MdfPrimitive primType = ((MdfEnumerationImpl) type).getType(); 
			if (primType.getName().equals("EnumMask")) {
				String enumPropertyValue = getWidget().getPropertyValue(PropertyTypeConstants.ENUM_VALUE);
				if(StringUtils.isBlank(enumPropertyValue)) {
					//Remove the existing checkbox as it is easier than updating 
					compoundCommand.add(new DeleteWidgetCommand(getWidget().getParent(), getWidget()));
					
					List<MdfEnumValueImpl> enumValues = retrieveEnumValues(domainAttributeName);
					int lastEnumValuePosition = enumValues.size() - 1;
					
					//Loop over backwards so that radio buttons appear in enumerated order in page
					for (int enumerationNo = lastEnumValuePosition; enumerationNo >= 0 ; enumerationNo--) {
						CreateWidgetCommand createWidgetCommmand = createCheckBoxWidget(domainAttributeName, enumValues, enumerationNo);
						compoundCommand.add(createWidgetCommmand);
					}
				}
			}
		}
    }

    // DS-4763
	private CreateWidgetCommand createCheckBoxWidget(String domainAttributeName, List<MdfEnumValueImpl> enumValues, int enumerationNo) {
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetLibrary xguiLibrary = metaModel.findWidgetLibrary("xgui");
		WidgetTemplate checkboxTemplate = xguiLibrary.findWidgetTemplate(WidgetTypeConstants.CHECKBOX);
		WidgetFactory widgetFactory = new WidgetFactory();
		Widget checkboxWidget = widgetFactory.create(checkboxTemplate);
		
		populateWidgetWithStandardProperties(checkboxWidget);
		populateCheckBoxProperties(domainAttributeName, enumValues, checkboxWidget, enumerationNo);

		int nextSelectedChild = getWidget().getIndexOfSelectedChild() + 1;
		CreateWidgetCommand createWidgetCommmand = new CreateWidgetCommand(getWidget().getParent(), checkboxWidget, nextSelectedChild, new Point());
		return createWidgetCommmand;
	}
	
	private void populateCheckBoxProperties(String domainAttributeName, List<MdfEnumValueImpl> enumValues, Widget widget, int enumerationValue) {
		MdfEnumValueImpl mdfEnumValueImpl = enumValues.get(enumerationValue);
		String enumValue = mdfEnumValueImpl.getName();
		widget.setPropertyValue("group", domainAttributeName);
		widget.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, domainAttributeName, READ_ONLY);
		widget.setPropertyValue(PropertyTypeConstants.BEAN_PROPERTY, domainAttributeName);
		widget.setPropertyValue(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION, "trail");		
		widget.setPropertyValue(PropertyTypeConstants.ENUM_VALUE, enumValue, READ_ONLY);
		widget.setPropertyValue(PropertyTypeConstants.ID, UniqueIdGenerator.generateId(widget));
	}
    
	/**
	 * @param domainAttributeName
	 * @param compoundCommand
	 */
	private void createRadioButtons(String domainAttributeName, CompoundCommand compoundCommand) {
		
		MdfEntity type = getPropertyType(domainAttributeName);
		
		if(type instanceof MdfEnumerationImpl) {
			String enumPropertyValue = getWidget().getPropertyValue(PropertyTypeConstants.ENUM_VALUE);
			if(StringUtils.isBlank(enumPropertyValue)) {
				//Remove the existing radio button as is easier than updating 
				compoundCommand.add(new DeleteWidgetCommand(getWidget().getParent(), getWidget()));
				
				List<MdfEnumValueImpl> enumValues = retrieveEnumValues(domainAttributeName);
				int lastEnumValuePosition = enumValues.size() - 1;
				
				//Loop over backwards so that radio buttons appear in enumerated order in page
				for (int enumerationNo = lastEnumValuePosition; enumerationNo >= 0 ; enumerationNo--) {
					CreateWidgetCommand createWidgetCommmand = createRadioButtonWidget(domainAttributeName, enumValues,	enumerationNo);
					compoundCommand.add(createWidgetCommmand);
				}
			}
		}
	}


	/**
	 * @param domainAttributeName
	 * @return
	 */
	private MdfEntity getPropertyType(String domainAttributeName) {
		MdfDataset dataset = DomainObjectUtils.getDataset(getWidget());
		MdfDatasetProperty domainAttributeProperty = dataset.getProperty(domainAttributeName);
		MdfEntity type = domainAttributeProperty.getType();
		return type;
	}


	/**
	 * @param domainAttributeName
	 * @param enumValues
	 * @param enumerationNo
	 * @return
	 */
	private CreateWidgetCommand createRadioButtonWidget(String domainAttributeName, List<MdfEnumValueImpl> enumValues, int enumerationNo) {
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetLibrary xguiLibrary = metaModel.findWidgetLibrary("xgui");
		WidgetTemplate radioButtonTemplate = xguiLibrary.findWidgetTemplate(WidgetTypeConstants.RADIO_BUTTON);
		WidgetFactory widgetFactory = new WidgetFactory();
		Widget radioButtonWidget = widgetFactory.create(radioButtonTemplate);
		
		populateWidgetWithStandardProperties(radioButtonWidget);
		populateRadioButtonProperties(domainAttributeName, enumValues, radioButtonWidget, enumerationNo);

		int nextSelectedChild = getWidget().getIndexOfSelectedChild() + 1;
		CreateWidgetCommand createWidgetCommmand = new CreateWidgetCommand(getWidget().getParent(), radioButtonWidget, nextSelectedChild, new Point());
		return createWidgetCommmand;
	}


	/**
	 * @param domainAttribute
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MdfEnumValueImpl> retrieveEnumValues(String domainAttribute) {
		MdfDataset dataset = DomainObjectUtils.getDataset(getWidget());
		MdfDatasetProperty domainAttributeProperty = dataset.getProperty(domainAttribute);
		MdfEnumerationImpl mdfEnumType = (MdfEnumerationImpl) domainAttributeProperty.getType();
		List<MdfEnumValueImpl> enumValues = mdfEnumType.getValues();
		return enumValues;
	}


	/**
	 * @param attributeName
	 * @param enumValues
	 * @param widget
	 * @param enumerationValue
	 */
	private void populateRadioButtonProperties(String domainAttributeName, List<MdfEnumValueImpl> enumValues, Widget widget, int enumerationValue) {
		
		MdfEnumValueImpl mdfEnumValueImpl = enumValues.get(enumerationValue);
		String enumValue = mdfEnumValueImpl.getName();
		widget.setPropertyValue("group", domainAttributeName);
		widget.setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, domainAttributeName, READ_ONLY);
		widget.setPropertyValue(PropertyTypeConstants.BEAN_PROPERTY, domainAttributeName);
		widget.setPropertyValue(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION, "trail");
		
		widget.setPropertyValue(PropertyTypeConstants.ENUM_VALUE, enumValue, READ_ONLY);
		widget.setPropertyValue(PropertyTypeConstants.ID, UniqueIdGenerator.generateId(widget));
	}

	/**
	 * @param widget
	 */
	private void populateWidgetWithStandardProperties(Widget widget) {
		String libraryName = widget.getLibraryName();
		String widgetTypeName = widget.getTypeName();
		MetaModel metaModel = MetaModelRegistry.getMetaModel();
		WidgetType widgetType = metaModel.findWidgetType(libraryName, widgetTypeName);
		Set<PropertyType> properties = new HashSet<PropertyType>();
		properties.addAll(widgetType.getAllPropertyTypes().values());
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
    public DomainAttributePropertySourceAdapter(Property property, CommandStack commandStack) {
        super(property, commandStack);
    }
    
}