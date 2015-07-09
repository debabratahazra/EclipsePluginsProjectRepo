package com.odcgroup.page.transformmodel.ui.builder;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.AGGREGATION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.BEAN_PROPERTY;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.BEAN_PROPERTY_PREFIX;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.CHARS;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.COLUMN_NAME;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOCUMENTATION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ASSOCIATION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ATTRIBUTE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FIELD_TYPE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FOR;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FORMAT;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.HORIZONTAL_ALIGNMENT;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.INCLUDE_SOURCE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.INCLUDE_TYPE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.REQUIRED;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.TYPE;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.ATTRIBUTE;
import static com.odcgroup.page.metamodel.WidgetTypeConstants.INCLUDE;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ext.gui.GUIAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.model.util.ModelAnnotationHelper;
import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.mdf.DomainConstants;
import com.odcgroup.page.metamodel.InclusionType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibrary;
import com.odcgroup.page.metamodel.WidgetTemplate;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.CdmUtils;
import com.odcgroup.page.model.util.WidgetFactory;
import com.odcgroup.page.model.util.WidgetUtils;

/**
 * Builds the Widgets for an MdfDatasetMappedProperty.
 * 
 * @author atr
 */
public class MdfDatasetMappedPropertyWidgetBuilder extends AbstractWidgetBuilder {

    /**
     * Creates a new MdfDatasetPropertyWidgetBuilder.
     * 
     * @param modelElement The MdfModelElement to create
     * @param parentWidget The Widget to which child Widgets will be added
     * @param builderContext The context
     */
    public MdfDatasetMappedPropertyWidgetBuilder(MdfDatasetMappedProperty modelElement, 
            Widget parentWidget, WidgetBuilderContext builderContext) {
        super(modelElement, parentWidget, builderContext);
    }

    /**
     * Creates the Widgets. These Widgets may contain child Widgets.
     * 
     * @return List The newly created Widgets. These Widgets may contain child Widgets
     */
    public List<Widget> buildWidgets() {
    	
    	List<Widget> result = null;
    	
        MdfDatasetMappedProperty dsp = getMdfDatasetMappedProperty();

        if (dsp.isTypeDataset()) {
        	result = buildLinkedDatasetWidgets();
        } else {

	        MdfProperty dsProperty = findMdfProperty(dsp);
	        if (dsProperty instanceof MdfAttribute) {
	        	result = buildAttributeWidgets((MdfAttribute) dsProperty);
	        } else {
	            // This is an association. In this case use the association builders
	            MdfAssociation association = (MdfAssociation) dsProperty;
	            WidgetBuilder wb = null;
	            if (dsp.isSingleValued()) {
		            Multiplicity multiplicity = new Multiplicity(association);
	                if (multiplicity.isByReference()) {
	                    // [0..1] by reference
	                    wb =  new MdfAssociationOneByRefWidgetBuilder(association, getParentWidget(), getBuilderContext());
	                } else {
	                    // [0..1] by value
	                    wb =  new MdfAssociationOneByValWidgetBuilder(association, getParentWidget(), getBuilderContext());
	                }
	            } else {
	                wb = getBuilderFactory().createBuilder(dsProperty, getParentWidget(), getBuilderContext());
	            }
	            result = wb.buildWidgets();
	        }
        }
        return result;
    }

	private MdfProperty findMdfProperty(MdfDatasetMappedProperty dsp) {
        MdfProperty property = resolvePath(dsp);
        if ( property == null ) {
        	String message = "The dataset [{0}] has an invalid path [{1}] for property [{2}]"; //$NON-NLS-1$
        	throw new PageException(MessageFormat.format(message, 
        			new Object[]{dsp.getParentDataset(), dsp.getPath(), dsp.getName()}));
        }
		return property;
	} 
    
    /**
     * Creates the Properties. These will be used to update an existing Widget.
     * 
     * @return List An empty List
     */
    public List<Property> buildProperties() {
    	
        List<Property> result = new ArrayList<Property>();

        MdfDatasetMappedProperty dsp = getMdfDatasetMappedProperty();
        if ( ! dsp.isTypeDataset()) {
	        // At the moment this is only used for MatrixGroups
	        Property p = createProperty(COLUMN_NAME);
	        p.setValue(dsp.getName());
	        result.add(p);
        }

        return result;
    }    

    /**
     * Creates the Widgets in the case when the MdfDatasetProperty corresponds to an MdfAttribute.
     * 
     * @param mdfAttribute The MdfAttribute
     * @return List The newly created Widgets. These Widgets may contain child Widgets
     */
    @SuppressWarnings("unchecked")
	private List<Widget> buildAttributeWidgets(MdfAttribute mdfAttribute) {
        MdfDatasetMappedProperty dsp = getMdfDatasetMappedProperty();
        
        if (shouldCreateColumn(getParentWidget())) {
            return createColumn(getModelElement(), getParentWidget());
        }
        
        if (shouldCreateItem(getParentWidget())) {
        	return createItem(getModelElement(), getParentWidget());
        }

        List<Widget> widgets = new ArrayList<Widget>();
        
		if (!CdmUtils.isValidCdmDomain(dsp.getQualifiedName())) {

			// create label for domain
			Widget label = createWidget("LabelDomain");
			label.setID(dsp.getName());
			label.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName(), true);
			label.setPropertyValue(DOCUMENTATION, dsp.getDocumentation());
			label.setPropertyValue(FOR, dsp.getName());
			label.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}"); //$NON-NLS-1$
			//set the lable required to false if the label parent is xtootlip
			if(WidgetUtils.isWidgetXtooltipType(getParentWidget().getRootWidget())){
			 label.setPropertyValue(REQUIRED, String.valueOf(false));
			}else{
			  label.setPropertyValue(REQUIRED, String.valueOf(mdfAttribute.isRequired()));
			}
			// create field for domain
			
			// EXTRAIRE CE QUIT SUIT, CONSTRUIRE UN TABLE [Property-name, value]
			// DE SORTE QUE CECI SOIT REUTILISABLE SUR LA PROPERTY DOMAIN-ATTRIBUTE-PROPERTY-SOURCE.
			
			String fieldType = getCorporateDesign().getFieldType(dsp.getType());
			MetaModel metamodel = MetaModelRegistry.getMetaModel();
			WidgetLibrary library = metamodel.findWidgetLibrary("xgui");
			WidgetTemplate template = library.findWidgetTemplate(fieldType);
			WidgetFactory factory = new WidgetFactory();
			Widget field = factory.create(template);
			field.setID(dsp.getName());
			field.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName(), true);
			field.setPropertyValue(DOCUMENTATION, dsp.getDocumentation());
	        field.setPropertyValue(REQUIRED, String.valueOf(mdfAttribute.isRequired()));
			field.setPropertyValue(TYPE, getPrimitiveType(dsp.getType()));
			field.setPropertyValue(BEAN_PROPERTY, dsp.getName());		
			field.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}"); //$NON-NLS-1$
			
			// update widget properties from business type
			if (dsp.getType() instanceof MdfBusinessType) {
				MdfBusinessType bizType = (MdfBusinessType)dsp.getType();
				List<MdfAnnotationProperty> properties = GUIAspect.getWidgetProperties(bizType);
				for (MdfAnnotationProperty ap : properties) {
					field.setPropertyValue(ap.getName(), ap.getValue());
				}
				field.setPropertyValue(CHARS, ModelAnnotationHelper.getDisplayLength(dsp.getParentDataset(),dsp));
			}
			
			field.setPropertyValue(FORMAT, getDisplayFormat(dsp.getType()));

			widgets.add(label);
			widgets.add(field);
			
		} else {
        
	        Widget aw = createWidget(ATTRIBUTE);
	        aw.setID(dsp.getName());
	        aw.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName());
	        aw.setPropertyValue(DOCUMENTATION, mdfAttribute.getDocumentation());
	        aw.setPropertyValue(REQUIRED, String.valueOf(mdfAttribute.isRequired()));
	        aw.setPropertyValue(TYPE, getPrimitiveType(mdfAttribute.getType()));
	
	        String fieldType = getCorporateDesign().getFieldType(dsp.getType());
	        aw.setPropertyValue(FIELD_TYPE, fieldType);
	
	        aw.setPropertyValue(BEAN_PROPERTY, dsp.getName());
	        aw.setPropertyValue(FOR, dsp.getName());
	
	        aw.setPropertyValue(AGGREGATION, "true"); //$NON-NLS-1$
	        
	        widgets.add(aw);
		}
        return widgets;
    }

	/**
     * Creates the Widgets when the DatasetProperty is a LinkedDataset.
     * 
     * @return List The newly created Widgets. These Widgets may contain child Widgets
     */
    private List<Widget> buildLinkedDatasetWidgets() {
    	
        List<Widget> widgets = new ArrayList<Widget>();
    	
        // Don't allow Associations to be added to Tables
        if (! shouldCreateColumn(getParentWidget())) {
        
	        MdfDatasetMappedProperty dsp = getMdfDatasetMappedProperty();
	
	        String cardinality = PropertyTypeConstants.CARDINALITY_ONE;
	        if (dsp.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
	            cardinality = PropertyTypeConstants.CARDINALITY_MANY;
	        }

	        // retrieve the qualified name of the linked dataset
	        MdfDatasetMappedPropertyImpl impl = (MdfDatasetMappedPropertyImpl) dsp;
	        MdfName qName = impl.getLinkDataset().getQualifiedName();
	
	        Model model = new FragmentFinder(getOfsProject()).findFragment(qName, cardinality);
	
	        if (model != null) {
	        	
	            // create label for mapped property (DS-1767)
	    		Widget label = createWidget("LabelDomain");
	    		label.setPropertyValue(DOMAIN_ATTRIBUTE, dsp.getName(), true);
	    		label.setPropertyValue(DOCUMENTATION, dsp.getDocumentation());
	    		label.setPropertyValue(HORIZONTAL_ALIGNMENT, "${corporatehalign}"); //$NON-NLS-1$
	            MdfProperty property = findMdfProperty(dsp);
	            if (property instanceof MdfAssociation) {
	            	MdfAssociation association = (MdfAssociation)property;
	        		label.setPropertyValue(REQUIRED, String.valueOf(association.isRequired()));
	            }
	    		
	    		// create the widget for mapped property
	            Widget include = createWidget(INCLUDE);
	            include.findProperty(INCLUDE_SOURCE).setModel(model);
	            include.setPropertyValue(INCLUDE_TYPE, InclusionType.SOURCE_INCLUDE_LITERAL.toString());
	            include.setPropertyValue(DOMAIN_ASSOCIATION, dsp.getName());
	            include.setPropertyValue(BEAN_PROPERTY_PREFIX, dsp.getName() + "."); //$NON-NLS-1$
	            widgets.add(label);
	            widgets.add(include);
	            
	        } else {
	        	
	            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	            String msg = "No default fragment has been defined for this Domain Entity '{0}' with cardinality '{1}'"; //$NON-NLS-1$
	            Dialog dialog = new MessageDialog(shell, "Error", null, //$NON-NLS-1$
	                    MessageFormat.format(msg, 
	                    		new Object[]{qName.getQualifiedName(), cardinality}), 
	                    0, new String[] { "OK" }, 0); //$NON-NLS-1$
	            dialog.open();
	        }
        }
        return widgets;
    }

    /**
     * Gets the MdfModelElement as an MdfDatasetMappedProperty.
     * 
     * @return MdfDatasetMappedProperty
     */
    private final MdfDatasetMappedProperty getMdfDatasetMappedProperty() {
        return (MdfDatasetMappedProperty) getModelElement();
    }

    /**
     * Finds the corresponding MdfAttribute given the dataset path. The dataset path starts from the main Class and uses
     * bean-utils like notation. ie. clientRelation.name. This corresponds to an association to the main entity called
     * clientRelation whose MdfClass has a property called name.
     * 
     * @param mainClass The main MdfClass
     * @param datasetPath The path to the attribute
     * @return MdfProperty The MdfProperty
     */
    private MdfProperty resolvePath(MdfDatasetMappedProperty dsp) {
    	
    	MdfProperty property = null;
    	
        MdfClass mainClass = dsp.getParentDataset().getBaseClass();
        String datasetPath = dsp.getPath();
    	
        if (!datasetPath.contains(DomainConstants.DATASET_PROPERTY_PATH_SEPARATOR)) {
            // This is an attribute of the main Entity
        	property = mainClass.getProperty(datasetPath);

        } else {
            String[] ps = datasetPath.split("\\" + DomainConstants.DATASET_PROPERTY_PATH_SEPARATOR);

            MdfProperty p = null;
            for (int i = 0; i < ps.length; ++i) {
                p = mainClass.getProperty(ps[i]);
                if (p == null) {
                	// invalid path, or mml has changed.
                	break;
                }
                if (p instanceof MdfAssociation) {
                    MdfAssociation mdfa = (MdfAssociation) p;
                    mainClass = (MdfClass) mdfa.getType();
                } else if (p instanceof MdfReverseAssociation) { // DS-1637
                	MdfReverseAssociation mdfra = (MdfReverseAssociation) p;
                	mainClass =  (MdfClass)mdfra.getType();
                }
            }

            property = p;
        }
        
        return property;
    }

   
   
}
