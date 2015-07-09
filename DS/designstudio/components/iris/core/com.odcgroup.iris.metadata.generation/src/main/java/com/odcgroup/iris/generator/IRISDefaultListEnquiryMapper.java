package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generator.Resource.RESOURCE_TYPE;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.t24.enquiry.enquiry.EnquiryPackage;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;
import com.odcgroup.t24.enquiry.util.EMUtils;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;

/**
 * 
 * This class builds an entity from an mdf class (i.e., for T24 application) to
 * generate the metadata and T24 properties for default list enquiry.
 * 
 * @author ssethupathi
 * 
 */
public class IRISDefaultListEnquiryMapper {

	private static Logger LOGGER = LoggerFactory.getLogger(IRISDefaultListEnquiryMapper.class);

	// All fo this logic comes from here http://10.44.5.14:9090/source/xref/T24/EB/Source/Private/EB_Reports/ENQUIRY.DEFAULT.b
	// DO NOT BLAME US!
	private static final int MAX_DEFAULT_ENQUIRY_COLOUMN_DATA_LENGTH = 75;
	private static final int MAX_DEFAULT_ENQUIRY_COLOUMNS = 5;
	private static final int COL_SPACER = 2;
	
	/**
 	 * Builds and returns an EMEntity instance for the MdfClass. If the default
 	 * @param fieldTypeChecker
 	 * @return entity
 	 */
	public List<EMEntity> getEntities(MdfClass mdfClass, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {
		List<EMEntity> entities = new ArrayList<EMEntity>();
		List<EMProperty> defaultDomainProperties = getDefaultEnquiryProperties(mdfClass, fieldTypeChecker);
		
		// Live
		EMEntity liveEntity = getLiveEntity(mdfClass, defaultDomainProperties, modelLoader, fieldTypeChecker);
		if (!liveEntity.getProperties().isEmpty()) {
			entities.add(liveEntity);
			LOGGER.debug("Generated default list metadata for resource: ["+mdfClass.getName()+"]...");
		} else {
			// it's possible that an application might not have a primary key property
			LOGGER.debug("Skipping default list metadata generaton due to no property to add for resource: ["+mdfClass.getName()+"]...");
			return entities; // Just return because there is no point proceeding further
		}
		
		// Resolve application type
		String applicationType = getApplicationType(mdfClass);
		
		// Unauth
		if (isUnauthFile(applicationType)) {
			EMEntity unAuthEntity = getUnauthEntity(mdfClass, defaultDomainProperties, modelLoader, fieldTypeChecker);
			entities.add(unAuthEntity);
			LOGGER.debug("Generated default unauth list metadata for resource: ["+mdfClass.getName()+"]...");
		} else {
			LOGGER.debug("Skipping default unauth list metadata generaton for resource: ["+mdfClass.getName()+"] - N/A...");
		}
		
		// Hist
		if (isHistFile(applicationType)) {
			EMEntity histEntity = getHistEntity(mdfClass, defaultDomainProperties, modelLoader, fieldTypeChecker);
			entities.add(histEntity);
			LOGGER.debug("Generated default history list metadata for resource: ["+mdfClass.getName()+"]...");
		} else {
			LOGGER.debug("Skipping default history list metadata generaton for resource: ["+mdfClass.getName()+"] - N/A...");
		}
		
		// Return all Entities
		return entities;
	}
	
	private EMEntity getEntityFromExistingEnq(MdfClass mdfClass, String enqPostFix, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {	
			Enquiry enquiry = getExistingEnquiry(mdfClass, modelLoader, enqPostFix);
		 		if (enquiry != null) {
		 			Application application = new Application(mdfClass);
		 			IRISEnquiryMapper enquiryMapper = new IRISEnquiryMapper();
		 			return enquiryMapper.getEntity(enquiry, application, fieldTypeChecker, RESOURCE_TYPE.enqlist);
		 		}
		return null;
	}
	
	private EMEntity constructDefaultEntity(MdfClass mdfClass, List<EMProperty> defaultProperties, 
		String irisResourcePostFix, String t24ResourcePostFix) {
		EMEntity entity = new EMEntity(Resource.RESOURCE_TYPE.enqlist + EMUtils.camelCaseName(mdfClass.getName()) + irisResourcePostFix);
 		String t24Name = IRISDomainMapper.getT24Name(mdfClass);	
		String t24ResourceName = (t24ResourcePostFix != null && !t24ResourcePostFix.isEmpty()) ? 
				"%" + t24Name + "$" + t24ResourcePostFix 
				: "%" + t24Name;
		entity.setT24Name(t24ResourceName);
		entity.addProperties(defaultProperties);
		return entity;
	}
	
	// For Live
	private EMEntity getLiveEntity(MdfClass mdfClass, List<EMProperty> defaultProperties, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {
		EMEntity entity = getEntityFromExistingEnq(mdfClass, "", modelLoader, fieldTypeChecker);
		if(entity != null) {
			return entity;
		} else {
			return constructDefaultEntity(mdfClass, defaultProperties, "", ""); 
		}
	}
	
	// For Unauth
	private EMEntity getUnauthEntity(MdfClass mdfClass, List<EMProperty> defaultProperties, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {
		String t24EnquiryPostFix = "$NAU";
		EMEntity entity = getEntityFromExistingEnq(mdfClass, t24EnquiryPostFix, modelLoader, fieldTypeChecker);
		if(entity != null) {
			return entity;
		} else {
			return constructDefaultEntity(mdfClass, defaultProperties, "Unauth", t24EnquiryPostFix); 
		}
	}
	
	// For Unauth
	private EMEntity getHistEntity(MdfClass mdfClass, List<EMProperty> defaultProperties, ModelLoader modelLoader, FieldTypeChecker fieldTypeChecker) {
		String t24EnquiryPostFix = "$HIS";
		EMEntity entity = getEntityFromExistingEnq(mdfClass, t24EnquiryPostFix, modelLoader, fieldTypeChecker);
		if(entity != null) {
			return entity;
		} else {
			return constructDefaultEntity(mdfClass, defaultProperties, "Hist", t24EnquiryPostFix); 
		}
	}	
	
	// returns the appropriate enquiry if exists or null if not found
	public Enquiry getExistingEnquiry(MdfClass mdfClass, ModelLoader modelLoader, String postFix) {
		/*
		 * Since we want to check 2 names, just bring the full table and do the
		 * check here.
		 */
		String t24Name = IRISDomainMapper.getT24Name(mdfClass);
		String enqName1 = t24Name + "-LIST" + postFix;	// See if we have Customized -LIST Enquiry
		String enqName2 = "%" + t24Name + postFix;		// See if we have Customized %ENQUIRY
		Enquiry ret =  modelLoader.getEObjectByFullName((EObject)mdfClass, new String[]{enqName1, enqName2}, 
				EnquiryPackage.Literals.ENQUIRY, true);
		
		return ret;

	}

	// returns a select list of properties of the application represented by mdf
	// class to build the default enquiry entity.
	// TODO due to the inconsistencies in default fields for % enquiry returned
	// from T24,
	// we can only support 'Id' property for the moment
	@SuppressWarnings("unchecked")
	private List<EMProperty> getDefaultEnquiryProperties(MdfClass mdfClass, FieldTypeChecker fieldTypeChecker) {
		List<EMProperty> emProperties = new ArrayList<EMProperty>();

		if (mdfClass.hasPrimaryKey()) {
			// Construct an ID
			EMProperty idProp = new EMProperty("Id");
			idProp.setT24Name("@ID");				
			idProp.addVocabularyTerm(new EMTerm(EMTermType.ID_TERM, "true"));
			emProperties.add(idProp);
			int coloumnUsed = getPropertySize((MdfProperty)mdfClass.getPrimaryKeys().get(0));
			
			if (coloumnUsed <= MAX_DEFAULT_ENQUIRY_COLOUMN_DATA_LENGTH) {
				// Now add properties until we reach max coloum width or max no of cols allowed
				int noOfCols = 1;
				for (MdfProperty mdfProperty : (List<MdfProperty>)mdfClass.getProperties()) {
					if (noOfCols > 5 || coloumnUsed > MAX_DEFAULT_ENQUIRY_COLOUMNS)
						break;
					if (!mdfProperty.isPrimaryKey() && isDisplayable(mdfProperty)) {
						EMProperty property = new EMProperty(EMUtils.camelCaseName(mdfProperty.getName()));
						property.setT24Name(IRISDomainMapper.getT24Name(mdfProperty));				
						emProperties.add(property);
						coloumnUsed += getPropertySize(mdfProperty);
						noOfCols++;
					}
				}
			 }
			
		}
		
		return emProperties;
	}
	
	// Hist file if the application type is 'H'	
	private boolean isHistFile(String applicationType) {
		if ("H".equals(applicationType) || "D".equals(applicationType) ) {
			return true;
		}
	return false;
	}
	
	// Unauth file if the application type is either 'U' or 'H'
	private boolean isUnauthFile(String applicationType) {
		if ("U".equals(applicationType) || "H".equals(applicationType) || "D".equals(applicationType) ) {
			return true;
		}
		return false;
	}
	
	// Below we have to sort out if Property is of Type 'D' i.e. Displayable
	private boolean isDisplayable(MdfProperty propertyType) { 
		if ("D".equals(T24Aspect.getTypeModifiers(propertyType))) {
			return true;
		}
		return false;
	}
	
	private String getApplicationType(MdfClass mdfClass) {
		try {
			return T24Aspect.getType(mdfClass).name();
		} catch (Exception e) {
			/*
			 * Will have a npe if LocalReferences or Groups ...
			 * applicationType can stay empty or (later) have
			 * a specific kind of 'LREF' type ...
			 */
			LOGGER.debug("Failed to resolve application type for resource ["+mdfClass.getName()+"]...");
		}
		return "";
	}
	
	private int getPropertySize(MdfProperty property) {
		return (T24Aspect.getMaxLength(property) == null?
					10 : T24Aspect.getMaxLength(property)) + COL_SPACER;
	}
}
