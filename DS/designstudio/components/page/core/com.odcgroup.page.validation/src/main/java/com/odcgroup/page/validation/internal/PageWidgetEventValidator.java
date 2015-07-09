package com.odcgroup.page.validation.internal;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.metamodel.ParameterTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.validation.Activator;
import com.odcgroup.page.validation.internal.constraint.PageValidationListener;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This class defines specific constraints for events
 * 
 * <p>
 * This first implementation just check a subset of the properties of each
 * event, in particular all properties that might contain either a reference to
 * a model (module, pageflow) or to a domain element.
 * 
 * @author atr
 */
public class PageWidgetEventValidator {

	private static final String FLOW_ACTION_PATTERN = "flow-action=\\w[a-zA-Z0-9]*";
	private static final String FLOW_CHANGE_PATTERN = "flow-change=[a-zA-Z0-9]*";
	
//	private static final String SNIPPET_FILTER = "Filter"; 									//$NON-NLS-1$
	private static final String SNIPPET_FILTERSET = "FilterSet";							//$NON-NLS-1$
	private static final String SNIPPET_QUERY = "Query";									//$NON-NLS-1$
//	private static final String SNIPPET_QUERYTABDISPLAY = "QueryTabDisplay";				//$NON-NLS-1$

	private static final String SNIPPET_PROPERTY_QUERY_OUTPUT_MODULE = "queryOutputModule";	//$NON-NLS-1$
	private static final String SNIPPET_PROPERTY_TARGET_DATASET = "targetDataset";			//$NON-NLS-1$
//	private static final String SNIPPET_PROPERTY_QUERY_ATTRIBUTES = "queryAttributes";		//$NON-NLS-1$
//	private static final String SNIPPET_PROPERTY_SELECTED_TAB = "selectedTab";				//$NON-NLS-1$

	/** This listener is notify whenever an error is found */
	private PageValidationListener listener = null;

	/** The widget to be validated */
	private Widget widget = null;

	/** The repository that contains all domain items for a given OFS project */
	private DomainRepository repository = null;

	/** The OFS project */
	private IOfsProject ofsProject = null;
	
	/** used to avoid to notify twice the same error message */
	private Set<String> statusSent = new HashSet<String>();

	/**
	 * Notifies the Listener of a new error message
	 * 
	 * @param event
	 *            the invalid event
	 * @param message
	 *            the error message
	 */
	private void notifyError(Event event, String message) {
		if (!statusSent.contains(message)) {
			IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, -1, message, null);
			listener.onValidation(status, event);
			statusSent.add(message);
		}
	}
	
	/**
	 * Checks the reference to a pageflow is valid
	 * @param event the event to be verified
	 */
	private void checkPageflowReference(Event event) {
		if (ofsProject == null) return;
		Parameter param = event.findParameter(ParameterTypeConstants.CALL_URI);
		if (param != null) { 
			String strURI = param.getValue();
			if (StringUtils.isNotEmpty(strURI) && strURI.startsWith(ModelURIConverter.SCHEME)) {
				URI uri = URI.createURI(strURI);
				if (!ofsProject.ofsModelResourceExists(uri)) {
					String msg = "The {0} event [{1}] has a reference to an unknown pageflow [{2}]"; //$NON-NLS-1$
					notifyError(event, MessageFormat.format(msg, 
							new Object[] { event.getNature().getLiteral().toLowerCase(), event.getEventName(), strURI }));
				}
			}
		}
	}
	
	/**
	 * Checks the reference to a seearch module is valid
	 * @param event the event to be verified
	 */
	private void checkSearchModuleReference(Event event) {
		if (ofsProject == null) return;
		for (Snippet snippet : event.getSnippets()) {
			if (snippet.getTypeName().equals(SNIPPET_QUERY)) {
				Property prop = snippet.findProperty(SNIPPET_PROPERTY_QUERY_OUTPUT_MODULE);
				if (prop != null) {
					String strURI = prop.getValue();
					if (StringUtils.isNotEmpty(strURI) && strURI.startsWith(ModelURIConverter.SCHEME)) {
						URI uri = ModelURIConverter.createModelURI(strURI);
						if ( ! ofsProject.ofsModelResourceExists(uri)) {
							String msg = "The {0} event [{1}] has a reference to an unknown search module [{2}]"; //$NON-NLS-1$
							notifyError(event, MessageFormat.format(msg, 
									new Object[] { event.getNature().getLiteral().toLowerCase(), event.getEventName(), strURI }));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Checks the reference to a dataset is valid
	 * @param event the event to be verified
	 */
	private void checkTargetDatasetReference(Event event) {
		if (repository == null) return;
		for (Snippet snippet : event.getSnippets()) {
			if (snippet.getTypeName().equals(SNIPPET_FILTERSET)) {
				Property prop = snippet.findProperty(SNIPPET_PROPERTY_TARGET_DATASET);
				if (prop != null && !StringUtils.isEmpty(prop.getValue())) {
					MdfName qname = MdfNameFactory.createMdfName(prop.getValue());
					if (repository.getDataset(qname) == null) {
						String msg = "The {0} event [{1}] has a reference to an unknown dataset [{2}]";
						notifyError(event, MessageFormat.format(msg, 
								new Object[] { event.getNature().getLiteral().toLowerCase(), event.getEventName(), qname }));
					}
				}
			}
		}
	}	

	private void checkSearchFieldSearchSubmitFunctionFlowAction(Event event) {
		
		if (event.getEventName().equalsIgnoreCase("Search")) {
			if(FunctionTypeConstants.SUBMIT_FUNCTION.equals(event.getFunctionName())) {
				Parameter parameter = event.findParameter("param");
				if (parameter != null) {
					String value = parameter.getValue();
					if (StringUtils.isNotBlank(value)) {
						if (!paramHasValidValue(value)) {
							String message = "Param value is not valid, must match format flow-action=xxx(;flow-change=xxx)";
							notifyError(event, MessageFormat.format(message, 
							new Object[] { event.getNature().getLiteral().toLowerCase(), event.getEventName(), value }));
						}
					}
				}
			}
		}
	}
	
	protected static boolean paramHasValidValue(String value) {
		
		if (StringUtils.isNotBlank(value)) {
			String[] params = value.split(";");

			if (params.length == 1) {
				return params[0].matches(FLOW_ACTION_PATTERN);
			} else if (params.length == 2) {
				boolean flowActionParamValid = params[0].matches(FLOW_ACTION_PATTERN);
				boolean flowChangeParamValid = params[1].matches(FLOW_CHANGE_PATTERN);
				return flowActionParamValid & flowChangeParamValid;
			} else {
				// should only contain 0,1 or 2 params
				return false;
			}
		}
		else {
			//Do not need to validate empty flow value
			return true;
		}
	}
	
	/**
	 * Checks all the event defined for the given widget
	 */
	public void checkConstraints() {
		for (Event event : widget.getEvents()) {
			checkPageflowReference(event);
			checkSearchModuleReference(event);
			checkSearchFieldSearchSubmitFunctionFlowAction(event);
			checkTargetDatasetReference(event);		
		}
	}

	/**
	 * Constructs a new event validator
	 * 
	 * @param listener
	 *            will be notified whenever an error is found
	 * @param widget
	 *            the widget to be verified
	 */
	public PageWidgetEventValidator(PageValidationListener listener, Widget widget) {
		this.listener = listener;
		this.widget = widget;
		Resource res = widget.eResource();
		if (res != null) {
			ofsProject = OfsResourceHelper.getOfsProject(res);
		}
		if (ofsProject != null) {
			repository = DomainRepository.getInstance(ofsProject);
		}
	}

}
