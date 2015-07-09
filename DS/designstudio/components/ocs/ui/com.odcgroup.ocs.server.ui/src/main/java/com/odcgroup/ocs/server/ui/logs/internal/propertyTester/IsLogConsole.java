package com.odcgroup.ocs.server.ui.logs.internal.propertyTester;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.ui.console.MessageConsole;

import com.odcgroup.ocs.server.ui.logs.LogConsole;

/**
 * Property tester which determines if it is a Log Console
 * @author yan
 * @since 1.40
 */
public class IsLogConsole extends PropertyTester {

	/* (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (!(receiver instanceof MessageConsole)) {
			return false;
		}
		MessageConsole messageConsole = (MessageConsole)receiver;
		return messageConsole.getAttribute(LogConsole.LOG_CONSOLE_ATTRIB_KEY) != null;
	}

}
