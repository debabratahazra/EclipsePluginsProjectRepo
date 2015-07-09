package com.temenos.ds.t24.data.rps.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.rps.RemoteServiceException;
import com.temenos.ds.t24.data.rps.ResourceProviderService;
import com.temenos.services.resourceprovider.ResourceProviderServiceProviderAPI;
import com.temenos.services.resourceprovider.data.Field;
import com.temenos.services.resourceprovider.data.FieldDefinitions;
import com.temenos.services.resourceprovider.data.Identifier;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.T24UserContextCallBackImpl;
import com.temenos.soa.services.data.Response;
import com.temenos.soa.services.data.ResponseDetails;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * DS ResourceProviderService API impl based on CF ResourceProviderServiceProviderAPI.
 * Incl. nicer types (e.g. hides Identifier, return type instead outParams, and properly throws Exception).
 *
 * @author Michael Vorburger
 */
public class ResourceProviderServiceWrapper implements ResourceProviderService {
	// NOTE: If you make changes to this class, consider if other ResourceProviderService implementations need likewise adaption
	
	private static Logger LOGGER = LoggerFactory.getLogger(ResourceProviderServiceWrapper.class);

	private static final int MAX_OVERRIDE_RETRIES = 3;
	
	private final ResourceProviderServiceProviderAPI service;

	public ResourceProviderServiceWrapper(ResourceProviderServiceProviderAPI delegate) {
		super();
		this.service = delegate;
	}

	@Override
	public GetResourceResponse getResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext, String resourceName, String id, String resourceState) throws RemoteServiceException {
		T24UserContextCallBackImpl userContextCallBack = new T24UserContextCallBackImpl(userDetails);
		service.setUserContextCallBack(userContextCallBack);

		Identifier inResourceName = new Identifier(resourceName);
		Identifier inID = new Identifier(id);
		// TODO resourceState ???

		Identifier outFields = new Identifier();
		List<Field> outResourceFields = new ArrayList<Field>();
		List<FieldDefinitions> outFieldDefinitions = new ArrayList<FieldDefinitions>();
		ResponseDetails outResponseDetails = new ResponseDetails();

		try {
			service.getResource(inT24InteractionContext, inResourceName, inID, outFields, outResourceFields, outFieldDefinitions, outResponseDetails);
		} catch (Exception e) {
			throw new RemoteServiceException("Failed to invoke remote ResourceProviderService", e);
		}

		String returnCode = outResponseDetails.getReturnCode();
		if (!"SUCCESS".equals(returnCode)) {
			throw new RemoteServiceException(outResponseDetails);
		}
		
		LOGGER.debug("FieldDefinitions");
		LOGGER.debug("================");
		LOGGER.debug(StringUtils.rightPad("Name", 20) + 
				"|" +
				StringUtils.rightPad("mvGroupName", 15) +
				"|" +
				StringUtils.rightPad("svGroupName", 11));
		LOGGER.debug(StringUtils.rightPad("", 49, "-"));
		for (FieldDefinitions def : outFieldDefinitions) {
			LOGGER.debug(StringUtils.rightPad(def.getName(), 20) + 
					"|" +
					StringUtils.rightPad(def.getMvGroupName(), 15) +
					"|" +
					StringUtils.rightPad(def.getSvGroupName(), 11));
		}
		LOGGER.debug(StringUtils.rightPad("", 49, "-") + "\n");
		
		GetResourceResponse r = new ResourceProviderService.GetResourceResponse(resourceName);
		for (Field outField : outResourceFields) {
			FieldDefinitions outFieldDefinition = findOutFieldDefinitions(outFieldDefinitions, outField);
			
			GetResourceResponse.Field dsField = new GetResourceResponse.Field(
					outField.getName(), 
					outField.getValue(), 
					outField.getMv(), 
					outFieldDefinition.getMvGroupName(), 
					outField.getSv(),
					outFieldDefinition.getSvGroupName());
			
			if (!dsField.isValid()) {
				LOGGER.warn("ignoring invalid field (" + dsField + ")");
				continue;
			}
			
			ResourceProviderService.GetResourceResponse.Field existingField = findField(r.fields, dsField);
			if (existingField == null) {
				r.fields.add(dsField);
			} else {
				if (GetResourceResponse.Field.equals(existingField, dsField) ) {
					LOGGER.warn("ignoring completely equal duplicated field (" + dsField + ")");
				} else {
					throw new IllegalStateException("Duplicated field with different values (field1: " + existingField + ", field2:" + dsField + ").");
				}
			}
		}
		
		LOGGER.debug(r.toString());
		
		return r;
	}

	private GetResourceResponse.Field findField(List<GetResourceResponse.Field> fields, GetResourceResponse.Field newField) {
		for (GetResourceResponse.Field existingField : fields) {
			if (existingField.name.equals(newField.name) &&
					existingField.mv.equals(newField.mv) &&
					existingField.mvGroupName.equals(newField.mvGroupName) &&
					existingField.sv.equals(newField.sv) &&
					existingField.svGroupName.equals(newField.svGroupName)) {
				return existingField;
			}
		}
		return null;
	}

	private FieldDefinitions findOutFieldDefinitions(List<FieldDefinitions> outFieldDefinitions, Field outField) {
		FieldDefinitions fieldDefinitionFound = null;
		for (FieldDefinitions fieldDefinition : outFieldDefinitions) {
			if (fieldDefinition.getName().equals(outField.getName())) {
				if (fieldDefinitionFound != null) {
					LOGGER.warn("Multiple field definition for " + outField.getName());
					fieldDefinitionFound = chooseTheOneToKeep(fieldDefinitionFound, fieldDefinition);
				} else {
					fieldDefinitionFound = fieldDefinition;	
				}
			}
		}
		if (fieldDefinitionFound != null) {
			return fieldDefinitionFound;
		}
		throw new IllegalStateException("Field Definition missing for " + outField.getName());
	}

	/**
	 * When multiple field definition exists, this method choose which one to keep. The rule is to keep the most complete (according our need)
	 */
	private FieldDefinitions chooseTheOneToKeep(FieldDefinitions fieldDefinition1, FieldDefinitions fieldDefinition2) {
		if (fieldDefinition1 != null && fieldDefinition2 == null) {
			return fieldDefinition1;
		}
		if (fieldDefinition1 == null && fieldDefinition2 != null) {
			return fieldDefinition2;
		}
		int scoreFD1 = (StringUtils.isNotEmpty(fieldDefinition1.getMvGroupName())?2:0) +
				(StringUtils.isNotEmpty(fieldDefinition1.getSvGroupName())?1:0);
		int scoreFD2 = (StringUtils.isNotEmpty(fieldDefinition2.getMvGroupName())?2:0) +
				(StringUtils.isNotEmpty(fieldDefinition2.getSvGroupName())?1:0);
		
		if (scoreFD1 != scoreFD2) {
			LOGGER.debug(" Difference(s) of FieldDefinition of " + fieldDefinition1.getName());
			logIfDifferent(" getDataType: ", fieldDefinition1.getDataType(), fieldDefinition2.getDataType());
			logIfDifferent(" getDataType: ", fieldDefinition1.getDataType(), fieldDefinition2.getDataType());
			logIfDifferent(" getFieldType: ", fieldDefinition1.getFieldType(), fieldDefinition2.getFieldType());
			logIfDifferent(" getInputAccess: ", fieldDefinition1.getInputAccess(), fieldDefinition2.getInputAccess());
			logIfDifferent(" getMvGroupName: ", fieldDefinition1.getMvGroupName(), fieldDefinition2.getMvGroupName());
			logIfDifferent(" getName: ", fieldDefinition1.getName(), fieldDefinition2.getName());
			logIfDifferent(" getSelectionName: ", fieldDefinition1.getSelectionName(), fieldDefinition2.getSelectionName());
			logIfDifferent(" getSvGroupName: ", fieldDefinition1.getSvGroupName(), fieldDefinition2.getSvGroupName());
			logIfDifferent(" getEnumerationDescriptions: ", StringUtils.join(fieldDefinition1.getEnumerationDescriptions(), ";"), StringUtils.join(fieldDefinition2.getEnumerationDescriptions(), ";"));
			logIfDifferent(" getEnumerationValues: ", StringUtils.join(fieldDefinition1.getEnumerationValues(), ";"), StringUtils.join(fieldDefinition2.getEnumerationValues(), ";"));
			logIfDifferent(" getFieldEnrichment: ", StringUtils.join(fieldDefinition1.getFieldEnrichment(), ";"), StringUtils.join(fieldDefinition2.getFieldEnrichment(), ";"));
			logIfDifferent(" getFieldNumber: ", StringUtils.join(fieldDefinition1.getFieldNumber(), ";"), StringUtils.join(fieldDefinition2.getFieldNumber(), ";"));
			logIfDifferent(" getFieldValue: ", StringUtils.join(fieldDefinition1.getFieldValue(), ";"), StringUtils.join(fieldDefinition2.getFieldValue(), ";"));
			logIfDifferent(" getIsLanguage: ", StringUtils.join(fieldDefinition1.getIsLanguage(), ";"), StringUtils.join(fieldDefinition2.getIsLanguage(), ";"));
			logIfDifferent(" getOperands: ", StringUtils.join(fieldDefinition1.getOperands(), ";"), StringUtils.join(fieldDefinition2.getOperands(), ";"));
			logIfDifferent(" getIsMandatory: ", fieldDefinition1.getIsMandatory(), fieldDefinition2.getIsMandatory());
			logIfDifferent(" getIsMandatorySelectionField: ", fieldDefinition1.getIsMandatorySelectionField(), fieldDefinition2.getIsMandatorySelectionField());
			logIfDifferent(" getIsPrimaryKey: ", fieldDefinition1.getIsPrimaryKey(), fieldDefinition2.getIsPrimaryKey());
			LOGGER.debug(" score: " + scoreFD1 + " <-- vs --> " + scoreFD2);
		}
		
		if (scoreFD1 >= scoreFD2) {
			return fieldDefinition1;
		} else {
			return fieldDefinition2;
		}
	}

	private void logIfDifferent(String title, Object value1, Object value2) {
		if (value1 == null && value2 == null) return;
		if ((value1 == null && value2 != null) || 
			(value1 != null && value2 == null) ||
			!value1.equals(value2)) {
			LOGGER.debug(title + value1 + " <-- vs --> " + value2);
		}
	}

	@Override
	public void inputResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext,
			String resourceName, String id, String resourceState, GetResourceResponse resourceResponse, 
			boolean autoOverride)
			throws RemoteServiceException {
		
		inputResource_internal(
				userDetails, inT24InteractionContext, 
				resourceName, id, resourceState, resourceResponse, 
				autoOverride?MAX_OVERRIDE_RETRIES:0);
	}
	
	private void inputResource_internal(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext,
			String resourceName, String id, String resourceState, GetResourceResponse resourceResponse, 
			int nbOverrideRetries)
			throws RemoteServiceException {
		T24UserContextCallBackImpl userContextCallBack = new T24UserContextCallBackImpl(userDetails);
		service.setUserContextCallBack(userContextCallBack);

		Identifier inResourceName = new Identifier(resourceName);
		Identifier inID = new Identifier(id);
		// TODO resourceState ???

		Identifier outFields = new Identifier();
		List<Field> inResourceFields = new ArrayList<Field>();
		for (GetResourceResponse.Field dsField : resourceResponse.fields) {
			// Values deduced from the getResource fieldscall
			inResourceFields.add(new Field(dsField.name, dsField.mv, dsField.sv, dsField.value, "", dsField.mv, dsField.sv, ""));
		}
		ResponseDetails responseDetails = new ResponseDetails();

		try {
			service.inputResource(inT24InteractionContext, inResourceName, inID, inResourceFields, outFields, responseDetails);
		} catch (Exception e) {
			throw new RemoteServiceException("Failed to invoke remote ResourceProviderService", e);
		}

		String inputReturnCode = responseDetails.getReturnCode();
		if ("SUCCESS".equals(inputReturnCode)) {
			return;
		} else if (nbOverrideRetries > 0 && hasOverride(responseDetails.getResponses())) {
			GetResourceResponse updatedResourceResponse = autoOverrideResourceResponse(resourceResponse, responseDetails);
			inputResource_internal(
					userDetails, inT24InteractionContext, 
					resourceName, id, resourceState, updatedResourceResponse, 
					nbOverrideRetries-1);
		} else {
			throw new RemoteServiceException(responseDetails);
		}
	}
	
	private boolean hasOverride(Response[] responses) {
		for (Response response: responses) {
			if ("OVERRIDE".equals(response.getResponseType())) {
				return true;
			}
		}
		return false;
	}

	private GetResourceResponse autoOverrideResourceResponse(GetResourceResponse resourceResponse, ResponseDetails responseDetails) {
		// Remove the existing OVERRIDE
		for (Iterator<GetResourceResponse.Field> iter=resourceResponse.fields.iterator(); iter.hasNext(); ) {
			GetResourceResponse.Field field = iter.next();
			if ("OVERRIDE".equals(field.name)) {
				iter.remove();
			}
		}
		for (Response response: responseDetails.getResponses()) {
			String overrideResponse = "" +
					response.getResponseCode() + 
					"_" + 
					response.getResponseType() + 
					"_" + 
					response.getResponseText() + 
					"_" + 
					StringUtils.substringBefore(response.getResponseInfo(), "*");

			GetResourceResponse.Field newOverrideField = new GetResourceResponse.Field("OVERRIDE", overrideResponse, 1, "OVERRIDE");
			resourceResponse.fields.add(newOverrideField);
		}
		return resourceResponse;
	}

	@Override
	public void authorizeResource(T24UserDetails userDetails, T24InteractionContext inT24InteractionContext, 
			String resourceName, String id) throws RemoteServiceException {
		Identifier inResourceName = new Identifier(resourceName);
		Identifier inID = new Identifier(id);
		Identifier outFields = new Identifier();
		ResponseDetails outResponseDetails = new ResponseDetails();
		service.authoriseResource(inT24InteractionContext, inResourceName, inID, outFields, outResponseDetails);
		String inputReturnCode = outResponseDetails.getReturnCode();
		if ("SUCCESS".equals(inputReturnCode)) {
			return;
		} else {
			throw new RemoteServiceException(outResponseDetails);
		}
		
	}
}
