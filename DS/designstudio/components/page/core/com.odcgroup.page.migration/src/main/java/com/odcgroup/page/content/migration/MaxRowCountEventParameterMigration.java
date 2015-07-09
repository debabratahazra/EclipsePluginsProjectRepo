package com.odcgroup.page.content.migration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Parameter;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.IQuery;
import com.odcgroup.page.model.snippets.SnippetUtil;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.migration.AbstractContentMigration;
import com.odcgroup.workbench.migration.MigrationException;

/**
 * migrates the additional parameter "maxRowCount" on the event
 * to the query snippet of the event
 * @author pkk
 *
 */
public class MaxRowCountEventParameterMigration extends AbstractContentMigration {
	
	/**
	 * 
	 */
	private static final String ROW_COUNT_PARAM = "Query.maxRowCount";

	@Override
	protected void doMigrate(IOfsProject ofsProject, Resource resource,
			IProgressMonitor monitor) throws MigrationException {
		EList<EObject> contents = resource.getContents();
		if (contents.size() > 0) {
			Model model = (Model) contents.get(0);
			Widget rootWidget = model.getWidget();
			doPerformMigration(rootWidget.getContents());
		}		
	}
	
	/**
	 * @param widget
	 */
	private void doPerformMigration(List<Widget> widgets) {
		for (Widget widget : widgets) {
			List<Event> events = widget.getEvents();
			for (Event event : events) {
				List<Parameter> params = new ArrayList<Parameter>();
				params.addAll(event.getParameters());
				for (Parameter parameter : params) {
					if (parameter.getName().equals(ROW_COUNT_PARAM)) {
						IQuery query  = SnippetUtil.getQuery(event);
						if (query != null) {
							try {
								if (parameter.getValue() != null) {
									int ii = new Integer(parameter.getValue()).intValue();
									query.setMaxRowCount(ii);
									event.getParameters().remove(parameter);
								}
							} catch (NumberFormatException e) {
								// ignore incase of exception
							}
						}
					}
				}
			}
			doPerformMigration(widget.getContents());
		}
	}
	


}
