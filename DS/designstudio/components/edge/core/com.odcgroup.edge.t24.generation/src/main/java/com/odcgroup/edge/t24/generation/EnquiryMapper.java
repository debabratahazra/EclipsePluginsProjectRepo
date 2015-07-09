package com.odcgroup.edge.t24.generation;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;

import com.acquire.intelligentforms.entities.Project;
import com.acquire.util.StringUtils;
import com.odcgroup.edge.t24.generation.enquiry.BasicEnquiryProject;
import com.odcgroup.edge.t24.generation.util.ComponentMap;
import com.odcgroup.edge.t24.generation.util.ComponentMap.ComponentType;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.edge.t24.generation.util.MapperUtility;
import com.odcgroup.edge.t24.generation.util.TextTranslations;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.enquiry.ApplicationType;
import com.odcgroup.t24.enquiry.enquiry.DrillDown;
import com.odcgroup.t24.enquiry.enquiry.DrillDownType;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.enquiry.FromFieldType;
import com.odcgroup.t24.enquiry.enquiry.ScreenType;
import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.enquiry.impl.SelectionImpl;
import com.odcgroup.t24.scoping.EnquiryCrossReferenceResolver;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.temenos.connect.utils.SlangManager;

/**
 * TODO: Document me!
 *
 * @author saleem.akbar
 *
 */
public class EnquiryMapper extends EdgeMapper<Enquiry>
{
    private static final Logger LOGGER = GenLogger.getLogger(EnquiryMapper.class);
    
    EnquiryMapper(IProject p_eclipseProject, MainOutputLocation p_mainOutputLocation, SlangManager p_slangManager)
    {
        super( p_eclipseProject, p_mainOutputLocation, p_slangManager );
    }

    @Override
    public Location getLocation(Enquiry p_enquiry)
    {
        final String t24EnquiryName = p_enquiry.getName();
        
        /*
         * 13/08/2014 Simon Hayes
         * 
         * Mods to deal with the possibility that T24 enquiry name is something like: %25CUSTOMER.
         * 
         * Unless we do something about it, generation will fail for such enquiries because:
         * - MapperUtility.processT24NameToIRISName() will reduce such names to something like: "25customer"
         * 
         * - when the in-memory edgeConnect project is serialized out to XML, the value of ifpFriendlyEnquiryName effectively becomes the "base name" for
         *   various attributes of the <Product> element
         *   
         * - but XML attribute names beginning with digits are illegal
         * 
         * Crunching out problematic parts of the name would lead to the possibility of name collisions (many-to-one mapping of T24 enquiry names to edge IFP
         * names).
         * 
         * We therefore replace "%" with "PCT." in the T24 name before passing it to MapperUtility.processT24NameToIRISName(), which gives us a "safe"
         * camel-cased name such as Pct25Customer.
         */
        final String ifpFriendlyEnquiryName = MapperUtility.processT24NameToIRISName(StringUtils.replaceAll(t24EnquiryName, "%", "PCT."));

        /*
         * (cont'd)
         * 
         * In order to generate the right data store structure for multi-valued groups, however, BasicEnquiryProject needs to know (or be able to deduce)
         * the IRIS name of the resource that will represent a single result entry.
         * 
         * For an enquiry with T24 name: %25CUSTOMER, this would be: "25customer" (i.e. different from ifpFriendlyEnquiryName, which - given the name-mangling
         * above would come out as: "Pct25Customer").
         * 
         * So, given that we also need this string ourselves (as the basis for the IRIS relation key to be added to HrefComponentMap.properties), we build it
         * here & forward on to BasicEnquiryProject generator below.
         */
        final String irisEnquiryResultEntryResourceName = "enq" + MapperUtility.processT24NameToIRISName(t24EnquiryName);
        
        return new Location( irisEnquiryResultEntryResourceName, ifpFriendlyEnquiryName, ifpFriendlyEnquiryName + Project.PROJECT_FILE_EXTENSION, ifpFriendlyEnquiryName );
    }
    
    @Override
    public void map(Enquiry p_enquiry, ComponentMap p_componentMap, ModelLoader p_modelLoader, Location p_location, String p_modelName)  throws Exception
    {
        final String irisEnquiryResultEntryResourceName = p_location.getResourceName();
        final String ifpFriendlyEnquiryName = p_location.getComponentName();
        
    	final File enquiryComponentDir = new File(p_location.getFullProjectDir());
    	final File enquiryTemplatesDir = new File(enquiryComponentDir, "templates");
    	
    	LOGGER.info("Enquiry: {}", p_enquiry.getName());
    	
    	// explore(p_enquiry);
    	
    	final BasicEnquiryProject generator = BasicEnquiryProject.create(this, ifpFriendlyEnquiryName, irisEnquiryResultEntryResourceName, p_enquiry, p_modelLoader);
    	setConnectProject(new ConnectProject(generator.getFormContext()), p_modelName);    	
    	generator.go(enquiryTemplatesDir);
    	
    	// add entries for this enquiry to our HrefComponentMap.properties
    	
    	storeMapperDetails( p_componentMap, p_location,  ComponentType.ENQUIRY, getMapperData() );
    }
    
    @SuppressWarnings("unused")
	private void explore(Enquiry p_enquiry) throws Exception {
    	LOGGER.debug("EnquiryMapper.explore() {");
    	
    	LOGGER.debug("p_enquiry.getName() -> " + p_enquiry.getName());
    	LOGGER.debug("p_enquiry.getDescription() -> " + TextTranslations.getLocalTranslations( this, p_enquiry.getDescription(), null ));
    	LOGGER.debug("p_enquiry.getFileName() -> " + p_enquiry.getFileName());
    	LOGGER.debug("p_enquiry.getHeader() -> " + p_enquiry.getHeader());
    	LOGGER.debug("p_enquiry.getTarget() -> " + p_enquiry.getTarget());
    	LOGGER.debug("p_enquiry.getStartLine() -> " + p_enquiry.getStartLine());
    	LOGGER.debug("p_enquiry.getEndLine() -> " + p_enquiry.getEndLine());
    	
    	final MdfClass applicationMdfClass = EnquiryCrossReferenceResolver.getBaseClassFor(p_enquiry);
    	LOGGER.debug("applicationMdfClass=" + applicationMdfClass);
    	
    	// Attributes
    	
    	final EList<String> attrs = p_enquiry.getAttributes();
    	final int numAttrs = (attrs == null) ? 0 : attrs.size();
    	LOGGER.debug("Attribute count: " + numAttrs);
    	
    	// Search parameters
    	
    	final SelectionExpression selectionExpn = p_enquiry.getCustomSelection();
		final EList<EObject> searchParamDefs = (selectionExpn == null) ? null : selectionExpn.eContents();
		final int numSearchParamDefs = (searchParamDefs == null) ? 0 : searchParamDefs.size();
		
		LOGGER.debug("Search param count: " + numSearchParamDefs);

		for (int i = 0; i < numSearchParamDefs; ++i) {
			final EObject searchParamDef = searchParamDefs.get(i);
			
			if (! (searchParamDef instanceof SelectionImpl)) {
				LOGGER.debug("- searchParamDefs[" + i + "]: " + searchParamDef + " (ignoring)");
				continue;
			}
			
			final SelectionImpl selectionImpl = (SelectionImpl) searchParamDef;
			
			LOGGER.debug(
				"- searchParamDefs[" + i + "]: " +
				"fieldName=" + selectionImpl.getField() +
				", ecDataItemName=" + MapperUtility.processT24NameToIRISName(selectionImpl.getField()) +
				", mandatory=" + selectionImpl.getMandatory() +
				", operands=" + selectionImpl.getOperands()
			);
		}		
    	
    	// Drilldowns
    	
    	final EList<DrillDown> drillDowns = p_enquiry.getDrillDowns();
    	final int numDrillDowns = (drillDowns == null) ? 0 : drillDowns.size();
    	
    	LOGGER.debug("Drilldown count: " + numDrillDowns);
    	
    	for (int i = 0; i < numDrillDowns; ++i) {
    		final DrillDown drillDown = drillDowns.get(i);
    		DrillDownType type = drillDown.getType();
    		LOGGER.debug(
    			"- drillDowns[" + i +
    			"]: application=" + getApplication(type) +
    			", fromField=" + getFromField(type) +
    			", image=" + drillDown.getImage() +
    			/*", info=" + drillDown.getInfo() +*/
    			", labelField=" + drillDown.getLabelField() +
    			", screen=" + getScreen(type) +
    			", criteria=" + drillDown.getCriteria() +
    			", description=" + TextTranslations.getLocalTranslations( this, drillDown.getDescription(), null ) 
    			
    		);
    	}
    	
    	// Fields (i.e. enquiry result table columns)
    	
    	final EList<Field> enqFields = p_enquiry.getFields();
    	final int numFields = (enqFields == null) ? 0 : enqFields.size();
    	LOGGER.debug("Enquiry result field count: " + numFields);
    	
    	for (int i = 0; i < numFields; ++i) {
    		final Field field = enqFields.get(i);
    		LOGGER.debug(
    			"- enqFields[" + i +
    			"]: name=" + field.getName() +
    			", ecDataItemName: " + MapperUtility.processT24NameToIRISName(field.getName()) +
    			", label=" + TextTranslations.getLocalTranslations( this, field.getLabel(), null ) +
    			", alignment=" + field.getAlignment() +
    			", attributes=" + field.getAttributes() +
    			", commaSeparator=" + field.getCommaSeparator() +
    			", conversion=" + field.getConversion() +
    			", displaySection=" + field.getDisplaySection() +
    			", displayType=" + field.getDisplayType() +
    			", columnWidth=" + field.getColumnWidth() +
    			", length=" + field.getLength() +
    			", hidden=" + field.getHidden() +
    			", noColumnLabel=" + field.getNoColumnLabel() +
    			", numberOfDecimals=" + field.getNumberOfDecimals() +
    			", operation=" + field.getOperation() +
    			", position=" + field.getPosition() +
    			", singleMulti=" + field.getSingleMulti()
    		);
    	}
    	
    	LOGGER.debug("} EnquiryMapper.explore()");
    }

	/**
	 * @param type
	 * @return
	 */
	private String getScreen(DrillDownType type) {
		if(type instanceof ScreenType){
			return ((ScreenType)type).getValue();
		}
		return null;
	}

	/**
	 * @param type
	 * @return
	 */
	private String getFromField(DrillDownType type) {
		if(type instanceof FromFieldType){
			return ((FromFieldType)type).getValue();
		}
		return null;
	}

	/**
	 * @return
	 */
	private String getApplication(DrillDownType type) {
		if(type instanceof ApplicationType){
			return ((ApplicationType)type).getValue();
		}
		return null;
	}
}
