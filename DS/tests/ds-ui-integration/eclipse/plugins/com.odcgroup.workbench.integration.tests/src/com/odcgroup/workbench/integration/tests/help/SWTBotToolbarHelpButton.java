package com.odcgroup.workbench.integration.tests.help;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.swt.finder.SWTBotWidget;
import org.eclipse.swtbot.swt.finder.Style;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.utils.MessageFormat;
import org.eclipse.swtbot.swt.finder.utils.SWTUtils;
import org.eclipse.swtbot.swt.finder.utils.internal.Assert;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.hamcrest.SelfDescribing;

@SWTBotWidget(clasz = ToolItem.class, style = @Style(name = "SWT.NONE", value = SWT.NONE), preferredName = "toolbarButton", referenceBy = {
		org.eclipse.swtbot.swt.finder.ReferenceBy.MNEMONIC,
		org.eclipse.swtbot.swt.finder.ReferenceBy.TOOLTIP }, returnType = SWTBotToolbarButton.class)
public class SWTBotToolbarHelpButton extends SWTBotToolbarButton {
	public SWTBotToolbarHelpButton(ToolItem w) throws WidgetNotFoundException {
		this(w, null);
	}

	public SWTBotToolbarHelpButton(ToolItem w, SelfDescribing description)
			throws WidgetNotFoundException {
		super(w, description);
		Assert.isTrue(SWTUtils.hasStyle(w, 0), "Expecting a SWT.NONE button.");
	}

	public SWTBotToolbarHelpButton click() {
		this.log.debug(MessageFormat.format("Clicking on {0}",
				new Object[] { this }));
		waitForEnabled();
		sendNotifications();
		this.log.debug(MessageFormat.format("Clicked on {0}",
				new Object[] { this }));
		return this;
	}

}
