package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.Widget;

public class MorphToValueCommand extends AbstractMorphToCommand {

	public MorphToValueCommand(Widget selectedWidget, String templateType) {
		super(selectedWidget, templateType);
	}

	protected String[] getProperties() {
		return new String[] {"item-column", "item-css", "format", "enabled", "enabledIsBasedOn", "enabledIsBasedOn-simplified",
				"enabledIsBasedOn-advanced", "editable", "item-halign", "newLine", "previewValue", "item-width"};
	}

}
