package com.odcgroup.workbench.integration.tests.util.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.results.VoidResult;
import org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList.ListElement;
import org.hamcrest.Matcher;

@SuppressWarnings("restriction")
public class SWTBotListElement extends AbstractSWTBot<ListElement> {

	public SWTBotListElement(ListElement w) throws WidgetNotFoundException {
		super(w);
	}
	
	@SuppressWarnings("unchecked")
	static public SWTBotListElement get(SWTBot bot, int index) {
		Matcher<ListElement> matcher = allOf(widgetOfType(ListElement.class));
		return new SWTBotListElement(bot.widget(matcher, index));
	}
	
	public String getText() {
		return widget.toString();
	}

	public void select() {
		asyncExec(new VoidResult() {
			public void run() {
				widget.setEnabled(true);
			}
		});
		notifyListeners();
	}

	protected void notifyListeners() {
		notify(SWT.MouseDown);
		notify(SWT.MouseUp);
		notify(SWT.Selection);
	}
}
