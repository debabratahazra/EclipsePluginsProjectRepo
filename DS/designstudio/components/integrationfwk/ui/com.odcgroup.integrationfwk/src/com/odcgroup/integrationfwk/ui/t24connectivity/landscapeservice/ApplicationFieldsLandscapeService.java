package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Fields;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationLandscapeService;

/**
 * gets all the fields of application from T24.
 * 
 * @author eswaranmuthu
 * 
 */
public class ApplicationFieldsLandscapeService extends
		IntegrationLandscapeService {

	public ApplicationFieldsLandscapeService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	protected ApplicationFieldsLandscapeService(
			IntegrationConnector integrationConnector) {
		super(integrationConnector);
	}

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection. Two arguments 1) application name - IN param 2) all fields
	 * in an application - OUT param
	 * 
	 * @return
	 */
	protected JSubroutineParameters constructjSubroutineRequest(
			String applicationName) {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(applicationName));
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	/**
	 * method constructs fields of Application from JSubroutineParameters second
	 * arguments is OUT param, get the second param and iterate it to get all
	 * fields.
	 * 
	 * @param jSubroutineResponse
	 *            JSubroutineParameters type response coming from T24.
	 * @return
	 */
	public Fields getApplicationFieldsList(
			JSubroutineParameters jSubroutineResponse) {
		JDynArray jdynArray = jSubroutineResponse.get(1);
		int itemCount = jdynArray.getNumberOfAttributes();
		Fields fields = new Fields();
		List<Field> listField = new ArrayList<Field>();
		Field field;
		for (int itemNum = 0; itemNum < itemCount; itemNum++) {
			// JSubroutineParameters extends List<jDynArray>, the below line get
			// first dynArray from List, returns all subsequent dynarray.
			field = new ApplicationVersionField();
			field.setFieldName(jdynArray.get(itemNum + 1, 1));
			field.setFieldType(jdynArray.get(itemNum + 1, 2));
			listField.add(field);
		}
		fields.setInputFields(listField);
		return fields;
	}

	public Fields getFields(String applicationName, String projectName) {
		return getFields(applicationName, projectName, false);
	}

	public Fields getFields(String applicationName, String projectName,
			boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest(applicationName);
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, LANDSCAPE_APPLICATION_FIELDS, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			return getApplicationFieldsList(jSubroutineResponse);
		} else {
			return new Fields();
		}
	}

	/**
	 * returns list of fields for given application.
	 * 
	 * @return
	 */
	@Override
	public List<String> getResponse(String projectName) {
		return new ArrayList<String>();
	}
}
