package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.Widget;

public class MorphToCalendarCommand extends AbstractMorphToCommand {

	public MorphToCalendarCommand(Widget selectedWidget, String templateType) {
		super(selectedWidget, templateType);
	}

	protected String[] getProperties() {
		return new String[] {"item-column", "item-css", "format", "editable", "enabled", "enabledIsBasedOn", "enabledIsBasedOn-simplified",
				"enabledIsBasedOn-advanced", "item-halign", "newLine", "previewValue", "item-width",
				"domainAttribute", "required", "tabIndex", "type"};
	}

}
