package com.odcgroup.integrationfwk.ui.services.connectivity;

import java.util.List;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.temenos.services.integrationflow.data.xsd.ComponentServiceData;
import com.temenos.services.integrationflow.data.xsd.ContractData;
import com.temenos.services.integrationflow.data.xsd.ExitPoint;
import com.temenos.services.integrationflow.data.xsd.IntegrationFlowBase;


/**
 * Responsible for providing methods to build the data requires for creating a
 * flow in T24 via Integration Framework in TAFJ Platform.
 * 
 * @author sbharathraja
 * 
 */
public interface TafjCreateFlowServiceDataBuilder {
	/**
	 * Helps to create the component service data from a single event & flow
	 * (attached to that event) designed in an Integration Project.
	 * 
	 * @return <code>List<{@link ComponentServiceData}></code>
	 * 
	 * @throws TWSConsumerPluginException
	 *             - in case of any issues while constructing the data.
	 */
	public List<ComponentServiceData> createComponetServiceData()
			throws TWSConsumerPluginException;

	/**
	 * Helps to create the contract data from a single event & flow (attached to
	 * that event) designed in an Integration Project.
	 * <p>
	 * The instance returning from here will actually holds the T24
	 * Application/Version/Component Service field(s) which are all have been
	 * added into the flow enrichment as part of the designed flow.
	 * <p>
	 * A single field on this contract data will have a field definition(as same
	 * as the field represents in T24), field type (data type of the field) and
	 * the user defined display name of the field.
	 * 
	 * @return <code>List<{@link ContractData}></code>
	 * 
	 * @throws TWSConsumerPluginException
	 *             - in case of any issues while constructing the data.
	 */
	public List<ContractData> createContractData()
			throws TWSConsumerPluginException;

	/**
	 * Helps to create the exit point data from a single event & flow (attached
	 * to that event) designed in an Integration project.
	 * <p>
	 * The exit point data will holds the following data in respective position.
	 * <p>
	 * &nbsp; &nbsp;-1st position : exit point
	 * (INPUT.ROUTINE-&lt;ProjectName&gt/AUTH.ROUTINE-&lt;ProjectName&gt for
	 * Application/Version type of exit point,
	 * SERVICE.OPERATION-&lt;ProjectName&gt; for component service type of exit
	 * point, JOB.PROCESS-&lt;ProjectName&gt; for TSA Service type of exit
	 * point.)
	 * <p>
	 * &nbsp; &nbsp;-2nd position : override(s) from T24 (more than one can be
	 * presented here)
	 * <p>
	 * &nbsp; &nbsp;-3rd position : contract name (This data will be set only
	 * for TSA Service type of exit point; other type of exit point will have
	 * this data as empty)
	 * 
	 * @return instance of {@link ExitPoint}
	 * 
	 * @throws TWSConsumerPluginException
	 *             - in case of any issues while constructing the data.
	 */
	public ExitPoint createExitPointData() throws TWSConsumerPluginException;

	/**
	 * Helps to create the integration flow base data from a single event & flow
	 * (attached to that event) designed in an Integration project.
	 * <p>
	 * The integration flow base data will holds the following data in
	 * respective position.
	 * <p>
	 * &nbsp; &nbsp;- 1st position : flow name (given by user while creating a
	 * flow)
	 * <p>
	 * &nbsp; &nbsp;- 2nd position : exit point type (VERSION for version type
	 * of exit point, APPLICATION for application type of exit point,
	 * COMPONENT.SERVICE for component service type of exit point, TSA.SERVICE
	 * for tsa service type of exit point)
	 * <p>
	 * &nbsp; &nbsp;-3rd position : contract name;
	 * <p>
	 * &nbsp; &nbsp;&nbsp; &nbsp;T24 Application/Version name for
	 * Application/Version type of exit point. (e.g.:
	 * FUNDS.TRANSFER/FUNDS.TRANSFER,ACC);
	 * <p>
	 * &nbsp; &nbsp;&nbsp;
	 * &nbsp;&lt;ComponentServiceName&gt;.&lt;ServiceOperation&gt; for component
	 * service type of exit point. (e.g.: Customer.getSmsDetails);
	 * <p>
	 * &nbsp; &nbsp;&nbsp; &nbsp; T24 COB Job name for TSA service type of exit
	 * point. (e.g.: CALC.INT)
	 * 
	 * @return instance of {@link IntegrationFlowBase}
	 * @throws TWSConsumerPluginException
	 *             - in case of any issues while constructing the data.
	 */
	public IntegrationFlowBase createIntegrationFlowBaseData()
			throws TWSConsumerPluginException;
}
