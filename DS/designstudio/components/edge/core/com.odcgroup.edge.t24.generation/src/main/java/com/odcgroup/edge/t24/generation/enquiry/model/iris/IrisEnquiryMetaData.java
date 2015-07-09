package com.odcgroup.edge.t24.generation.enquiry.model.iris;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;

import com.acquire.util.AssertionUtils;
import com.odcgroup.edge.t24.generation.util.GenLogger;
import com.odcgroup.iris.generator.FieldTypeChecker;
import com.odcgroup.iris.generator.IRISEnquiryMapper;
import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;

/**
 * TODO: Document me!
 *
 * @author shayes
 *
 */
public class IrisEnquiryMetaData
{
    private static final Logger LOGGER = GenLogger.getLogger(IrisEnquiryMetaData.class);

    // Names of IRIS odata response <entry> sub-groups for multi-valued fields by (unqualified) IRIS field-name (loaded by loadIrisMetaData() from IRIS metadata - i.e. T24-enq*.properties - on construction)
    private final HashMap<String,String> m_irisMultiValueGroupNameByIrisEnqFieldName = new HashMap<String,String>();
    
    // Names of IRIS odata response <entry> sub-groups for sub-value fields by (unqualified) IRIS field-name (loaded by loadIrisMetaData() from IRIS metadata - i.e. T24-enq*.properties - on construction).
    private final HashMap<String,String> m_irisSubValueGroupNameByIrisEnqFieldName = new HashMap<String,String>();

    public IrisEnquiryMetaData(Enquiry p_enquiry, MdfClass p_enquiryApplicationMdfClass)
    {
    	AssertionUtils.requireNonNull(p_enquiry, "p_enquiry");

    	if (p_enquiryApplicationMdfClass == null)
    	{
       		LOGGER.error("Unable to load IRIS meta-data for enquiry: " + p_enquiry.getName() + " (no MdfClass available for underlying application: " + p_enquiry.getFileName() + ")");
       		return;
    	}
    	
        final com.odcgroup.iris.generator.Application app = new com.odcgroup.iris.generator.Application(p_enquiryApplicationMdfClass);

        if (LOGGER.isInfoEnabled())
        	LOGGER.info("Loading IRIS field meta-data for: " + p_enquiry.getName() + "...");
        
        final EMEntity emEntity = (new IRISEnquiryMapper()).getEntity(p_enquiry, app, new FieldTypeChecker(p_enquiry), RESOURCE_TYPE.enquiry);
        
        if (emEntity == null)
        {
        	LOGGER.error("Unable to load IRIS meta-data for enquiry: " + p_enquiry.getName() + " (" + IRISEnquiryMapper.class.getSimpleName() + ".getEntity() call returned null)");
        	return;
        }

        final List<EMProperty> topLevelFieldDefs = emEntity.getProperties();
        final int numTopLevelFieldDefs = (topLevelFieldDefs == null) ? 0 : topLevelFieldDefs.size();
        
        if (LOGGER.isInfoEnabled())
        	LOGGER.info("Processing " + numTopLevelFieldDefs + " top-level field def(s):");
        
        final String irisEnquiryResultEntityName = emEntity.getName();

        for (int i = 0; i < numTopLevelFieldDefs; ++i)
        {
        	final EMProperty topLevelFieldDef = topLevelFieldDefs.get(i);
        	final String topLevelFieldName = topLevelFieldDef.getName();
        	
        	final List<EMProperty> childMultiValueFieldDefs = topLevelFieldDef.getSubProperties();
        	final int numChildMultiValueFieldDefs = (childMultiValueFieldDefs == null) ? 0 : childMultiValueFieldDefs.size();
        	
        	if (numChildMultiValueFieldDefs == 0)
        	{
        		if (LOGGER.isInfoEnabled())
        			LOGGER.info("- single-value field: \"" + topLevelFieldName + '"');
        		
        		continue;
        	}
        	
        	// topLevelFieldDef is for a multi-valued field
        	
    		final String multiValueResponseGroupName = irisEnquiryResultEntityName + '_' + topLevelFieldName;

    		if (LOGGER.isInfoEnabled())
    			LOGGER.info("- multi-valued entity: \"" + topLevelFieldName + "\" - response group name=\"" + multiValueResponseGroupName + '"');

    		for (int j = 0; j < numChildMultiValueFieldDefs; ++j)
        	{
        		final EMProperty multiValueFieldDef = childMultiValueFieldDefs.get(j);
        		final String multiValueFieldName = multiValueFieldDef.getName();
        		
        		final List<EMProperty> subValueFieldProperties = multiValueFieldDef.getSubProperties();
        		final int numSubValueProperties = (subValueFieldProperties == null) ? 0 : subValueFieldProperties.size();

        		if (numSubValueProperties == 0)
        		{
        			if (LOGGER.isInfoEnabled())
        				LOGGER.info("--> multi-valued entity field: " + multiValueFieldName + " (no sub-values)");
        			
            		m_irisMultiValueGroupNameByIrisEnqFieldName.put(multiValueFieldName, multiValueResponseGroupName);
            		continue;
        		}
        		
        		// multiValueFieldDef represents a field within the multi-valued entity represented by topLevelFieldDef that has sub-value fields
        		
        		if (LOGGER.isInfoEnabled())
        			LOGGER.info("--> multi-valued entity field: " + multiValueFieldName + " with sub-values:");
    			
    			final String subValueReponseGroupName = irisEnquiryResultEntityName + '_' + multiValueFieldName;
    					            		
        		for (int k = 0; k < numSubValueProperties; ++k)
        		{
        			final EMProperty subValueProperty = subValueFieldProperties.get(k);
        			final String subValueFieldName = subValueProperty.getName();
        			
        			if (LOGGER.isInfoEnabled())
	        			LOGGER.info("----> sub-value field: " + subValueFieldName + ", response sub-group name=\"" + subValueReponseGroupName + '"');
	        		
	        		m_irisSubValueGroupNameByIrisEnqFieldName.put(subValueFieldName, subValueReponseGroupName);
            		m_irisMultiValueGroupNameByIrisEnqFieldName.put(subValueFieldName, multiValueResponseGroupName);
        		}
        	}
        } // for each EMProperty in emEntity
        

        if (LOGGER.isInfoEnabled())
        {
        	LOGGER.info("Loading of IRIS meta data complete");
        }
    }
    
    public String getIrisMultiValueGroupName(String p_irisEnqFieldName)
    {
    	return m_irisMultiValueGroupNameByIrisEnqFieldName.get(p_irisEnqFieldName);
    }
    
    public String getIrisSubValueGroupName(String p_irisEnqFieldName)
    {
    	return m_irisSubValueGroupNameByIrisEnqFieldName.get(p_irisEnqFieldName);
    }
}
