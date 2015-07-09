package com.odcgroup.integrationfwk.ui.common;

import java.util.List;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.model.Event;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.model.ExitPointType;
import com.odcgroup.integrationfwk.ui.model.Flow;
import com.odcgroup.integrationfwk.ui.model.SourceType;

/**
 * Act as a abstraction layer for constructing data to create a flow in T24 via
 * Integration Framework and provides operation to help to the construct the
 * data regardless of TAF platforms.
 * 
 * @author sbharathraja
 * 
 */
public class CreateFlowServiceDataBuilder {

	/**
	 * Exit point type string for version type of exit point.
	 */
	private static final String EXIT_POINT_TYPE_VERSION = "VERSION";

	/**
	 * Exit point type string for application type of exit point.
	 */
	private static final String EXIT_POINT_TYPE_APPLICATION = "APPLICATION";

	/**
	 * Exit point type string for component service type of exit point.
	 */
	private static final String EXIT_POINT_TYPE_COMPONENT_SERVICE = "COMPONENT.SERVICE";

	/**
	 * Exit point type string for tsa service type of exit point.
	 */
	private static final String EXIT_POINT_TYPE_TSA_SERVICE = "TSA.SERVICE";

	/**
	 * Exit point for component service type of exit point.
	 * <p>
	 * Please note that this string for T24 Version/Application would be
	 * INPUT.ROUTINE/AUTH.ROUTINE.
	 */
	private static final String EXIT_POINT_FOR_COMPONENT_SERVICE = "SERVICE.OPERATION";

	/**
	 * Exit point for tsa service type of exit point.
	 * <p>
	 * Please note that this string for T24 Version/Application would be
	 * INPUT.ROUTINE/AUTH.ROUTINE.
	 */
	private static final String EXIT_POINT_FOR_TSA_SERVICE = "JOB.PROCESS";

	/**
	 * instance which holds the all designed events and flow regards to an
	 * Integration project
	 */
	protected EventFlow eventFlowContainer;

	/**
	 * Constructs the instance of {@link CreateFlowServiceDataBuilder} with
	 * given details.
	 * 
	 * @param eventFlow
	 * @throws TWSConsumerPluginException
	 */
	public CreateFlowServiceDataBuilder(EventFlow eventFlow)
			throws TWSConsumerPluginException {
		if (eventFlow == null) {
			throw new TWSConsumerPluginException(
					"EventFlow instance cannot be null.");
		}
		eventFlowContainer = eventFlow;
	}

	// TODO: keep an eye on CatalogService web service changes and remove
	// this method when the respective web-service provide us the list of
	// service names with out the word 'service'
	/**
	 * Helps to remove the last occurrence of word service from the given
	 * contract name.
	 * <p>
	 * For example: CustomerService will be return as Customer
	 * <p>
	 * <b>NOTE:</b> This is a temporary clean up process. The Service Repository
	 * web service should provide us the service name with out the service word
	 * which is what the T24 can understand.
	 * 
	 * @param contractName
	 * @return CustomerService will be return as Customer
	 */
	private String cleanContractName(String contractName) {
		final String wordToBeRemoved = "Service";
		if (contractName.endsWith(wordToBeRemoved)) {
			return contractName.substring(0,
					contractName.lastIndexOf(wordToBeRemoved));
		} else if (contractName.endsWith("SERVICE")) {
			return contractName.substring(0,
					contractName.lastIndexOf("SERVICE"));
		}
		return contractName;
	}

	/**
	 * Helps to get the contract name of a flow.
	 * 
	 * @return constructed contract name.
	 *         (FUNDS.TRANSFER/FUNDS.TRANSFER,AA/CUSTOMER
	 *         .GETSMSDETAILS/CALC.INT)
	 * @throws TWSConsumerPluginException
	 */
	protected String getContractName() throws TWSConsumerPluginException {
		Event aSingleEvent = eventFlowContainer.getEvent();

		if (aSingleEvent == null) {
			throw new TWSConsumerPluginException("event is not available.");
		}

		if (aSingleEvent.getExitPointType() == null) {
			throw new TWSConsumerPluginException(
					"exit point type is not available.");
		}
		ExitPointType exitPointType = aSingleEvent.getExitPointType();
		String sourceType = exitPointType.getSourceType();

		if (sourceType == null || sourceType.trim().length() == 0) {
			throw new TWSConsumerPluginException(
					"exit point type is not available.");
		}
		// the default contract name
		String contractName = exitPointType.getContractName();
		switch (SourceType.valueOf(sourceType)) {
		case APPLICATION:
			// return the default contract name
			return contractName;
		case VERSION:
			// return the default contract name
			return contractName;
		case COMPONENT_SERVICE:
			// get the operation name.
			// The operation name comes here is actually with project at end (as
			// like getSmsDetails-IFToolingTest). This project name has been
			// appended to this operation name at the time of initiating the
			// publish action.
			String operationName = exitPointType.getExitPoint();
			if (operationName.contains("-")) {
				// remove the project name from operation name
				operationName = operationName.substring(0,
						operationName.indexOf("-"));
			}
			// combined the service and operation
			contractName = contractName + "." + operationName;

			return contractName;
		case TSA_SERVICE:
			return eventFlowContainer.getFlow().getBaseEvent();
		}
		// shouldn't be reached.
		throw new TWSConsumerPluginException(sourceType
				+ " is unknown type of exit point.");
	}

	/**
	 * Helps to get the exit point for a designed flow.
	 * 
	 * @return 
	 *         INPUT.ROUTINE/AUTH.ROUTINE/SERVICE.OPERATION-&lt;projectName&gt;/JOB
	 *         .PROCESS-&lt;projectName&gt;
	 * @throws TWSConsumerPluginException
	 */
	protected String getExitPoint() throws TWSConsumerPluginException {
		Event aSingleEvent = eventFlowContainer.getEvent();

		if (aSingleEvent == null) {
			throw new TWSConsumerPluginException("event is not available.");
		}

		if (aSingleEvent.getExitPointType() == null) {
			throw new TWSConsumerPluginException(
					"exit point type is not available.");
		}
		// construct project name from exit point.
		String exitPoint = aSingleEvent.getExitPointType().getExitPoint();
		// exit point came here is with project name at end (like as
		// INPUT.ROUTINE-IFTest). This project name construction been happened
		// while initiating the Publish Action.
		String projectName = exitPoint;
		if (exitPoint != null && exitPoint.trim().length() > 0
				&& exitPoint.contains("-")) {
			projectName = projectName.substring(projectName.indexOf("-"));
		}

		String sourceType = aSingleEvent.getExitPointType().getSourceType();

		if (sourceType == null || sourceType.trim().length() == 0) {
			throw new TWSConsumerPluginException(
					"exit point type is not available.");
		}
		switch (SourceType.valueOf(sourceType)) {
		case VERSION:
			return exitPoint;
		case APPLICATION:
			return exitPoint;
		case COMPONENT_SERVICE:
			return EXIT_POINT_FOR_COMPONENT_SERVICE + projectName;
		case TSA_SERVICE:
			return EXIT_POINT_FOR_TSA_SERVICE + projectName;
		}
		// shouldn't happen
		throw new TWSConsumerPluginException(sourceType
				+ " is unknown type of exit point.");
	}

	/**
	 * Helps to get the string which has to be passed into T24 to found out the
	 * type of the exit point.
	 * 
	 * @return exit point type.
	 * @throws TWSConsumerPluginException
	 */
	protected String getExitPointType() throws TWSConsumerPluginException {
		Event aSingleEvent = eventFlowContainer.getEvent();

		if (aSingleEvent == null) {
			throw new TWSConsumerPluginException("event is not available.");
		}

		if (aSingleEvent.getExitPointType() == null) {
			throw new TWSConsumerPluginException(
					"exit point type is not available.");
		}
		String sourceType = aSingleEvent.getExitPointType().getSourceType();

		if (sourceType == null || sourceType.trim().length() == 0) {
			throw new TWSConsumerPluginException(
					"exit point type is not available.");
		}

		switch (SourceType.valueOf(sourceType)) {
		case VERSION:
			return EXIT_POINT_TYPE_VERSION;
		case APPLICATION:
			return EXIT_POINT_TYPE_APPLICATION;
		case COMPONENT_SERVICE:
			return EXIT_POINT_TYPE_COMPONENT_SERVICE;
		case TSA_SERVICE:
			return EXIT_POINT_TYPE_TSA_SERVICE;
		}
		// shouldn't happen
		throw new TWSConsumerPluginException(sourceType
				+ " is unknown type of exit point.");
	}

	/**
	 * Returns the flow attributes
	 * 
	 * @return
	 */
	protected List<String> getFlowAttributes() {
		Flow flow = eventFlowContainer.getFlow();
		return flow.getAttributesAsListOfString();
	}

}
