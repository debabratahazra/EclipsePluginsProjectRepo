package com.odcgroup.integrationfwk.ui.services.connectivity;

import java.util.ArrayList;
import java.util.List;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.common.CreateFlowServiceDataBuilder;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.SourceType;
import com.temenos.services.integrationflow.data.xsd.ComponentServiceData;
import com.temenos.services.integrationflow.data.xsd.ContractData;
import com.temenos.services.integrationflow.data.xsd.ExitPoint;
import com.temenos.services.integrationflow.data.xsd.IntegrationFlowBase;
import com.temenos.services.integrationflow.data.xsd.ObjectFactory;

/**
 * Implementation of {@link CreateFlowServiceDataBuilder} regards to TAFJ
 * platform.
 * 
 * @author sbharathraja
 * 
 */
public class TafjCreateFlowServiceDataBuilderImpl extends
	CreateFlowServiceDataBuilder implements
	TafjCreateFlowServiceDataBuilder {

    /**
     * object factory instance which helps to create the object from integration
     * flow client jar for TAFJ.
     */
    private final com.temenos.services.integrationflow.data.xsd.ObjectFactory factory;

    /**
     * Constructs the TafjCreateFlowServiceDataBuilder with given details.
     *
     * @param eventFlow
     * @throws TWSConsumerPluginException
     */
    public TafjCreateFlowServiceDataBuilderImpl(EventFlow eventFlow)
	    throws TWSConsumerPluginException {
	super(eventFlow);
	factory = new ObjectFactory();
    }

    public List<ComponentServiceData> createComponetServiceData()
	    throws TWSConsumerPluginException {
	List<ComponentServiceData> componentServiceDatas = new ArrayList<ComponentServiceData>();
	// TODO: for TAFC platform this argument is not populated
	// so leaving this argument empty for now.
	return componentServiceDatas;
    }

    public List<ContractData> createContractData()
	    throws TWSConsumerPluginException {
	Flow flow = eventFlowContainer.getFlow();
	if (flow == null) {
	    throw new TWSConsumerPluginException(
		    "Flow instance cannot be null.");
	}
	List<ContractData> contractDatas = new ArrayList<ContractData>();
	for (Field field : flow.getFields().getInputFields()) {
	    ContractData contractData = factory.createContractData();
	    contractData.setFieldDefinition(factory
		    .createContractDataFieldDefinition(field.getFieldName()));
	    contractData.setFieldName(factory.createContractDataFieldName(field
		    .getDisplayName()));
	    contractData.setFieldType(factory.createContractDataFieldType(field
		    .getFieldType()));
	    contractDatas.add(contractData);
	}
	return contractDatas;
    }

    public ExitPoint createExitPointData() throws TWSConsumerPluginException {
	ExitPoint exitPoint = new ExitPoint();
	// exit point name for Version/Application type of ExitPoint could be
	// either INPUT.ROUTINE-<ProjectName> or AUTH.ROUTINE-<ProjectName>.
	// This has been already stored in event object. But for the component
	// service type of ExitPoint the exit point name should be
	// SERVICE.OPERATION-<ProjectName> and for TSA service type of exit
	// point the exit point name should be JOBS.PROCESS-<ProjectName> and
	// this has not been stored in event object yet. Hence the below
	// conditional statement will decide what should be set as exit point
	// name based on the ExitPoint type.
	exitPoint.setName(factory.createExitPointName(getExitPoint()));
	exitPoint.setSourceName(factory
		.createExitPointSourceName(getSourceName()));

	// add the overrides
	exitPoint.getOverrideCodes().addAll(
		eventFlowContainer.getEvent().getOverrides().getOverrides());

	return exitPoint;
    }

    public IntegrationFlowBase createIntegrationFlowBaseData()
	    throws TWSConsumerPluginException {
	Event event = eventFlowContainer.getEvent();
	IntegrationFlowBase integrationFlowBase = factory
		.createIntegrationFlowBase();
	// The flow name comes here actually has the project name at the
	// beginning as like IFToolingTest-FTFlow. This project name has been
	// appended at the time of initiating the publish action itself.
	integrationFlowBase.setFlowName(factory
		.createIntegrationFlowBaseFlowName(event.getFlowName()));
	String sourceName = getContractName();
	String sourceType = getExitPointType();
	List<String> attributes = getFlowAttributes();

	integrationFlowBase.setSourceName(factory
		.createIntegrationFlowBaseSourceName(sourceName));
	integrationFlowBase.setSourceType(factory
		.createIntegrationFlowBaseSourceType(sourceType));
	integrationFlowBase.getAttributes().addAll(attributes);

	return integrationFlowBase;
    }

    /**
     * Helps to get the exit point source name for a flow.
     * <p>
     * As of now, this contract name is required only for TSA Service type of
     * exit point.
     *
     * @return exit point source name if applicable, empty string otherwise.
     */
    private String getSourceName() {
	Event event = eventFlowContainer.getEvent();
	if (event == null) {
	    // for exceptional scenario
	    return "";
	}
	ExitPointType exitPointType = event.getExitPointType();
	if (exitPointType == null) {
	    return "";
	}
	String sourceType = exitPointType.getSourceType();
	switch (SourceType.valueOf(sourceType)) {
	// iterate over other cases in case of the other type of event
	// needs contract name in future.
	case TSA_SERVICE:
	    return exitPointType.getContractName();
	default:
	    return "";
	}
    }

}
