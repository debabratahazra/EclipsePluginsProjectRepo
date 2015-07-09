package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withMnemonic;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.widgetIsEnabled;
import static org.hamcrest.Matchers.allOf;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.ContextMenuFinder;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;

public class MyAbstractSWTBot<T extends Widget> extends AbstractSWTBot<T> {

	public MyAbstractSWTBot(T w) throws WidgetNotFoundException {
		super(w);
	}

	public MyAbstractSWTBot(T w, SelfDescribing description) {
		super(w, description);
	}

	public SWTBotMenu contextMenu(String text, int index) throws WidgetNotFoundException {
		if (widget instanceof Control) {
			return contextMenu((Control) widget, text, index);
		}
		throw new WidgetNotFoundException("Could not find menu: " + text); //$NON-NLS-1$
	}
	
	@SuppressWarnings("unchecked")
	protected SWTBotMenu contextMenu(final Control control, final String text, final int index) {
		Matcher<MenuItem> withMnemonic = withMnemonic(text);
		final Matcher<MenuItem> matcher = allOf(widgetOfType(MenuItem.class), withMnemonic);
		final ContextMenuFinder menuFinder = new ContextMenuFinder(control);

		new SWTBot().waitUntil(new DefaultCondition() {
			public String getFailureMessage() {
				return "Could not find context menu with text: " + text; //$NON-NLS-1$
			}

			public boolean test() throws Exception {
				return !menuFinder.findMenus(matcher).isEmpty();
			}
		});
		return new SWTBotMenu(menuFinder.findMenus(matcher).get(index), matcher);
	}
	
	/**
	 * Wait until the widget is enabled.
	 * 
	 * @since 2.0
	 */
	protected void waitForEnabled() {
		new SWTBot().waitUntil(widgetIsEnabled(this));
	}

	/**
	 * Check if the widget is enabled, throws if the widget is disabled.
	 * 
	 * @since 1.3
	 */
	protected void assertEnabled() {
		Assert.isTrue(isEnabled(), MessageFormat.format("Widget {0} is not enabled.", this)); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
}
