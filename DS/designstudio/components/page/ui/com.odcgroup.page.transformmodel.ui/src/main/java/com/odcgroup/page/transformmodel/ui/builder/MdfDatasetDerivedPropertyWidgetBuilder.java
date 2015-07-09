package com.odcgroup.page.transformmodel.ui.builder;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.AGGREGATION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.BEAN_PROPERTY;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.COLUMN_NAME;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOCUMENTATION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ATTRIBUTE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FIELD_TYPE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FOR;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FORMAT;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.HORIZONTAL_ALIGNMENT;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.TYPE;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.ATTRIBUTE;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.CdmUtils;
import com.odcgroup.page.model.util.WidgetFactory;


/**
 * Builds the Widgets for an MdfDatasetDerivedProperty.
 * 
 * @author atr
 */
public class MdfDatasetDerivedPropertyWidgetBuilder extends AbstractWidgetBuilder {

    /**
     * Creates a new MdfDatasetPropertyWidgetBuilder.
     * 
     * @param modelElement The MdfModelElement to create
     * @param parentWidget The Widget to which child Widgets will be added
     * @param builderContext The context
     */
    public MdfDatasetDerivedPropertyWidgetBuilder(MdfDatasetDerivedProperty modelElement, 
            Widget parentWidget, WidgetBuilderContext builderContext) {
        super(modelElement, parentWidget, builderContext);
    }

    /**
     * Creates the Widgets. These Widgets may contain child Widgets.
     * 
     * @return List The newly created Widgets. These Widgets may contain child Widgets
     */
    @SuppressWarnings("unchecked")
	public List<Widget> buildWidgets() {
        if (shouldCreateColumn(getParentWidget())) {
            return createColumn(getModelElement(), getParentWidget());
        }

        List<Widget> widgets = new ArrayList<Widget>();

        MdfDatasetDerivedProperty dsp = getMdfDatasetDerivedProperty();

		if (!CdmUtils.isValidCdmDomain(dsp.getQualifiedName())) {

			// create label for domain
			Widget label = createWidget("LabelDomain");
			label.setID(dsp.getName());
			label.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName(), true);
			label.setPropertyValue(DOCUMENTATION, dsp.getDocumentation());
			label.setPropertyValue(FOR, dsp.getName());
			label.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}");

			// create field for domain
			String fieldType = getCorporateDesign().getFieldType(dsp.getType());
			MetaModel metamodel = MetaModelRegistry.getMetaModel();
			WidgetLibrary library = metamodel.findWidgetLibrary("xgui");
			WidgetTemplate template = library.findWidgetTemplate(fieldType);
			WidgetFactory factory = new WidgetFactory();
			Widget field = factory.create(template);	
			field.setID(dsp.getName());
			field.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName(), true);
			field.setPropertyValue(DOCUMENTATION, dsp.getDocumentation());
			field.setPropertyValue(TYPE, getPrimitiveType(dsp.getType()));
			field.setPropertyValue(BEAN_PROPERTY, dsp.getName());		
			field.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}");
			
			// update widget properties from business type
			if (dsp.getType() instanceof MdfBusinessType) {
				MdfBusinessType bizType = (MdfBusinessType)dsp.getType();
				List<MdfAnnotationProperty> properties = GUIAspect.getWidgetProperties(bizType);
				for (MdfAnnotationProperty ap : properties) {
					Property wp = field.findProperty(ap.getName());
					if (wp != null) {
						wp.setValue(ap.getValue());
					}
				}
			}

			field.setPropertyValue(FORMAT, getDisplayFormat(dsp.getType()));
			
			widgets.add(label);
			widgets.add(field);
			
		} else {
			
	        Widget aw = createWidget(ATTRIBUTE);
	        aw.setID(dsp.getName());
	        aw.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName());
	        aw.setPropertyValue(DOCUMENTATION, dsp.getDocumentation());
	        aw.setPropertyValue(TYPE, getPrimitiveType(dsp.getType()));
	
	        String fieldType = getCorporateDesign().getFieldType(dsp.getType());
	        aw.setPropertyValue(FIELD_TYPE, fieldType);
	
	        aw.setPropertyValue(BEAN_PROPERTY, dsp.getName());
	        aw.setPropertyValue(FOR, dsp.getName());
	
	        aw.setPropertyValue(AGGREGATION, "true");
	        widgets.add(aw);
		}

        return widgets;
    }

    /**
     * Creates the Properties. These will be used to update an existing Widget.
     * 
     * @return List An empty List
     */
    public List<Property> buildProperties() {
        MdfDatasetProperty dsp = getMdfDatasetDerivedProperty();
        List<Property> result = new ArrayList<Property>();

        // At the moment this is only used for MatrixGroups
        Property p = createProperty(COLUMN_NAME);
        p.setValue(dsp.getName());
        result.add(p);

        return result;
    }

    /**
     * Gets the MdfModelElement as an MdfDatasetDerivedProperty.
     * 
     * @return MdfDatasetDerivedProperty
     */
    protected MdfDatasetDerivedProperty getMdfDatasetDerivedProperty() {
        return (MdfDatasetDerivedProperty) getModelElement();
    }
}
