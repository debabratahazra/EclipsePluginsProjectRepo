package com.odcgroup.t24.server.external.model.internal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.util.T24WebServiceConnectionHelper;
import com.temenos.services.catalog.data.Link;
import com.temenos.services.catalog.data.ResourceField;
import com.temenos.services.catalog.data.StateTransition;
import com.temenos.services.catalog.data.response.xsd.GetResourceResponse;
import com.temenos.t24.introspector.catalog.CatalogResourceDetails;
import com.temenos.t24.introspector.exceptions.RIMGenerationException;
import com.temenos.t24.introspector.interaction.IrisModelData;
import com.temenos.t24.introspector.odata.OdataFeedData;

public class InteractionWebLoader extends AbstractCatalogServiceWebServiceLoader implements IInteractionLoader {

	private static final Logger logger = LoggerFactory.getLogger(InteractionWebLoader.class);

	private String modelName;
	private IrisModelData irisModelData;
	private OdataFeedData odataFeed;

	private final String[] toStringArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}
	
	private List<ResourceField> getFields(GetResourceResponse response) {
		List<ResourceField> fields = new ArrayList<ResourceField>();
		for (com.temenos.services.catalog.data.xsd.ResourceField rf : response.getFields()) {
			ResourceField field = new ResourceField();
			field.setDataType(rf.getDataType().getValue());
			field.setEnumerationDescriptions(toStringArray(rf.getEnumerationDescriptions()));
			field.setEnumerationValues(toStringArray(rf.getEnumerationValues()));
			field.setFieldType(rf.getFieldType().getValue());
			field.setInputAccess(rf.getInputAccess().getValue());
			field.setIsMandatory(rf.getIsMandatory().getValue());
			field.setIsMandatorySelectionField(rf.getIsMandatorySelectionField().getValue());
			field.setMvGroupName(rf.getMvGroupName().getValue());
			field.setName(rf.getName().getValue());
			field.setOperands(toStringArray(rf.getOperands()));
			field.setSelectionName(rf.getSelectionName().getValue());
			field.setSvGroupName(rf.getSvGroupName().getValue());
			field.setIsPrimaryKey(rf.getIsPrimaryKey().getValue());
			field.setFieldValue(toStringArray(rf.getFieldValue()));
			field.setFieldNumber(toStringArray(rf.getFieldNumber()));
			field.setFieldEnrichment(toStringArray(rf.getFieldEnrichment()));
			field.setIsLanguageField(rf.getIsLanguageField().getValue());
			fields.add(field);
		}
		return fields;
	}
	
	private List<Link> getLinks(GetResourceResponse response) {
		List<Link> links = new ArrayList<Link>();
		for (com.temenos.services.catalog.data.xsd.Link rf : response.getLinks()) {
			Link link = new Link();
			link.setAction(rf.getAction().getValue());
			link.setParamNames(toStringArray(rf.getParamNames()));
			link.setParamOperators(toStringArray(rf.getParamOperators()));
			link.setParamValues(toStringArray(rf.getParamValues()));
			link.setRecordId(rf.getRecordId().getValue());
			link.setResourceName(rf.getResourceName().getValue());
			link.setResourceType(rf.getResourceType().getValue());
			links.add(link);
		}
		return links;
	}
	
	private List<StateTransition> getStateTransitions(GetResourceResponse response) {
		List<StateTransition> stateTransitions = new ArrayList<StateTransition>();
		for (com.temenos.services.catalog.data.xsd.StateTransition rf : response.getStateTransitions()) {
			StateTransition stateTransition = new StateTransition();
			stateTransition.setFunction(rf.getFunction().getValue());
			stateTransition.setInitialState(rf.getInitialState().getValue());
			stateTransition.setTargetState(rf.getTargetState().getValue());
			stateTransitions.add(stateTransition);
		}
		return stateTransitions;
	}
	
	public InteractionWebLoader(Properties properties) {
		super(properties);
	}
	
	@Override
	public <T extends IExternalObject> List<T> getObjectDetails() throws T24ServerException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends IExternalObject> String getObjectAsXML(T detail) throws T24ServerException {
		checkConnection();

		try {
			String resourceName = detail.getName();
			resourceName = resourceName.replaceAll("_", ".");
			GetResourceResponse response = catalogPort.getResource(userDetails, resourceName);

			// Check Response Details First
			T24WebServiceConnectionHelper.checkResponseDetails(response.getResponseDetails().getValue());
			
			// Continue
			List<ResourceField> fields = getFields(response);
			List<Link> links = getLinks(response);
			List<StateTransition> stateTransitions = getStateTransitions(response);
			
			CatalogResourceDetails resourceDetails = new CatalogResourceDetails(resourceName, fields, stateTransitions, links);
			irisModelData.addResourceInformation(resourceName, resourceDetails);
			odataFeed.addResourceInformation(resourceName, resourceDetails);
			
		} catch (Exception ex) {
			throw new T24ServerException(ex);
		}
		
		return null;
		
	}

	@Override
	public final void setModelName(String name) {
		this.modelName = name;
		irisModelData = new IrisModelData(modelName);
		odataFeed = new OdataFeedData(modelName);
	}

	@Override
	public InputStream getRIM() throws T24ServerException {
		String value = ServerPropertiesHelper.getStrictOData(properties);
		boolean strictOdata = Boolean.valueOf(value);
		try {
			return irisModelData.getRIM(strictOdata);
		} catch (RIMGenerationException ex) {
			logger.error(ex.getMessage(), ex);
			throw new T24ServerException(ex);
		}
	}

	@Override
	public Map<String,String> getRIMsMap() throws T24ServerException {
		String value = ServerPropertiesHelper.getStrictOData(properties);
		boolean strictOdata = Boolean.valueOf(value);
		try {
				return irisModelData.getRIMsMap(strictOdata);
			} catch (RIMGenerationException ex) {
				logger.error(ex.getMessage(), ex);
				throw new T24ServerException(ex);
			}
	}

}
