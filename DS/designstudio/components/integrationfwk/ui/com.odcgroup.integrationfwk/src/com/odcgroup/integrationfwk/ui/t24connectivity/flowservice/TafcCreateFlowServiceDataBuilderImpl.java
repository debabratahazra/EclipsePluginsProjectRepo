package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import java.util.Iterator;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.common.CreateFlowServiceDataBuilder;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.SourceType;

/**
 * Implementation of {@link CreateFlowServiceDataBuilder} regards to TAFC
 * platform.
 * 
 * @author sbharathraja
 * 
 */
public class TafcCreateFlowServiceDataBuilderImpl extends
		CreateFlowServiceDataBuilder implements
		TafcCreateFlowServiceDataBuilder {

	/**
	 * Constructs the TafcCreateFlowServiceDataBuilder with given details.
	 * 
	 * @param eventFlow
	 * @throws TWSConsumerPluginException
	 */
	public TafcCreateFlowServiceDataBuilderImpl(EventFlow eventFlow)
			throws TWSConsumerPluginException {
		super(eventFlow);
	}

	public JDynArray createComponetServiceData()
			throws TWSConsumerPluginException {
		// IF Create Flow is not requires this data for now. Modify this if
		// needs to build some data in future.
		return new JDynArray("");
	}

	public JDynArray createContractData() throws TWSConsumerPluginException {
		Flow flow = eventFlowContainer.getFlow();
		JDynArray contractData = new JDynArray("");
		populateContractData(contractData, flow.getFields().getInputFields(), 1);
		return contractData;
	}

	public JDynArray createExitPointData() throws TWSConsumerPluginException {
		Event event = eventFlowContainer.getEvent();
		if (event == null) {
			throw new TWSConsumerPluginException("Event instance is null.");
		}
		JDynArray exitPointData = new JDynArray("");
		// inserting exit point in 1st position.
		exitPointData.insert(getExitPoint(), 1);
		// iterate over all overrides to insert the overrides into dyn array.
		Iterator<String> itr = event.getOverrides().getOverrides().iterator();
		int count = 1;
		while (itr.hasNext()) {
			// inserting overrides in 2nd position.
			exitPointData.insert(itr.next(), 2, count);
			count++;
		}
		// insert the contract name in 3rd position (if applicable)
		exitPointData = setContractName(event.getExitPointType(), exitPointData);
		return exitPointData;
	}

	public JDynArray createIntegrationFlowBaseData()
			throws TWSConsumerPluginException {
		Event event = eventFlowContainer.getEvent();
		if (event == null) {
			throw new TWSConsumerPluginException("Event instance is null.");
		}
		JDynArray integrationFlowBaseData = new JDynArray("");
		// insert the flow name in 1st position
		// The flow name comes here actually has the project name at the
		// beginning as like IFToolingTest-FTFlow.This project name has been
		// appended at the time of initiating the publish action itself.
		integrationFlowBaseData.insert(event.getFlowName(), 1);
		// insert the exit point type in 2nd position
		integrationFlowBaseData.insert(getExitPointType(), 2);
		// insert the contract name in 3rd position
		integrationFlowBaseData.insert(getContractName(), 3);
		// insert the flow attributes in 4th position as MV
		List<String> attributes = getFlowAttributes();
		if (attributes.size() > 0) {
			for (int i = 0; i < attributes.size(); i++) {
				integrationFlowBaseData.insert(attributes.get(i), 4, i);
			}
		} else {
			integrationFlowBaseData.insert("", 4);
		}
		return integrationFlowBaseData;
	}

	/**
	 * Helps to populate the contract data into given jdyn array.
	 * 
	 * @param jdynArray
	 *            - instance needs to be filled with contract data.
	 * @param contractData
	 *            - list which contains all contract data
	 * @param mValue
	 *            - jdyn array position
	 */
	private void populateContractData(JDynArray jdynArray,
			List<Field> contractData, int mValue) {

		Iterator<Field> itr = contractData.iterator();
		int count = mValue;
		while (itr.hasNext()) {
			Field temp = itr.next();
			// insert the field display name in 1st position
			jdynArray.insert(temp.getDisplayName(), count, 1);
			// insert the field type in 2nd position
			jdynArray.insert(temp.getFieldType(), count, 2);
			// insert the field definition in 3rd position.
			jdynArray.insert(temp.getFieldName(), count, 3);
			count++;
		}
	}

	/**
	 * Helps to set the contract name on the given constructed exit point.
	 * <p>
	 * This contract name is set only for TSA.SERVICE type of events.
	 * 
	 * @param exitPointType
	 *            - instance of {@link ExitPointType}
	 * @param constructedExitPoint
	 *            - jdynarray which has already contains the exit point
	 * @return constructed jdynarray with contract name if applicable.
	 */
	private JDynArray setContractName(ExitPointType exitPointType,
			JDynArray constructedExitPoint) {
		// return the incoming constructed exit point as it is for exceptional
		// scenario
		if (exitPointType == null) {
			return constructedExitPoint;
		}
		String sourceType = exitPointType.getSourceType();

		if (sourceType == null || sourceType.length() == 0) {
			return constructedExitPoint;
		}

		switch (SourceType.valueOf(sourceType)) {
		// iterate over other cases in case of the other type of event
		// needs contract name in future.
		case TSA_SERVICE:
			constructedExitPoint.insert(exitPointType.getContractName(), 3);
			return constructedExitPoint;
		default:
			return constructedExitPoint;
		}

	}

}
