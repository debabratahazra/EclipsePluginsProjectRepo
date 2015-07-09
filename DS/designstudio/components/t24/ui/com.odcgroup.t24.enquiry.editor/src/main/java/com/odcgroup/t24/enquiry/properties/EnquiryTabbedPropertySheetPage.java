package com.odcgroup.t24.enquiry.properties;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 *
 * @author phanikumark
 *
 */
public class EnquiryTabbedPropertySheetPage extends TabbedPropertySheetPage {
	
	private CommandStack commandStack;

	/**
	 * @param tabbedPropertySheetPageContributor
	 */
	public EnquiryTabbedPropertySheetPage(ITabbedPropertySheetPageContributor tabbedPropertySheetPageContributor, CommandStack commandStack) {
		super(tabbedPropertySheetPageContributor);
		this.commandStack = commandStack;
	}

	/**
	 * @return the commandStack
	 */
	public CommandStack getCommandStack() {
		return commandStack;
	}

}
