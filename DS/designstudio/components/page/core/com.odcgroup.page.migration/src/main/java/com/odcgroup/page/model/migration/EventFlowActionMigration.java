package com.odcgroup.page.model.migration;

import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.metamodel.FunctionTypeConstants;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

import static com.odcgroup.page.metamodel.ParameterTypeConstants.FLOW_ACTION_PARAMETER;

/**
 * Simplified Event for OnClick/Submit 
 *
 */
public class EventFlowActionMigration extends AbstractContentMigration {
	
	private boolean sameValue(String flowActionValue, String paramValue) {
		if (StringUtils.isBlank(paramValue) && StringUtils.isBlank(flowActionValue)) return true;
		paramValue = paramValue.trim();
		if (!paramValue.startsWith(FLOW_ACTION_PARAMETER)) return false;
		String[] parts = paramValue.split("=");
		if (parts.length > 1) {
			String value = parts[1].trim();
			return value.equals(flowActionValue);
		}
		return false;
	}
	
	private void migrate(Event event) {
		if(FunctionTypeConstants.SUBMIT_FUNCTION.equals(event.getFunctionName())) {
			Parameter flowAction = event.findParameter(FLOW_ACTION_PARAMETER);
			if (flowAction != null) {
				Parameter param = event.findParameter("param");
				if (param != null) {
					if (sameValue(flowAction.getValue(), param.getValue())) {
						event.eResource();
						if (event.isSimplifiedEvent()) {
							event.getParameters().remove(param);
						} else {
							event.getParameters().remove(flowAction);
						}
					}
				}
			}
		}
	}
	
	private void migrate(List<Event> events) {
		for (Event event : events) {
			migrate(event);
		}
	}
	
	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Stack<Widget> stack = new Stack<Widget>();
			Model model = (Model) contents.get(0);
			stack.push(model.getWidget());
			while (! stack.isEmpty()) {
				Widget widget = stack.pop();
				migrate(widget.getEvents());
				for (Widget containedWidget : widget.getContents()) {
					stack.push(containedWidget);
				}
			}
		}
	}

}
