package com.odcgroup.page.transformmodel.util;

/**
 * @author amc
 */
public class AttributeRenderer {

	private final StringBuilder script;

	private AttributeRenderer(StringBuilder script) {
		this.script = script;
	}
	
	public void render(String name, String value) {
		script.append(" ");
		script.append(name);
		script.append("=\"");
		script.append(value);
		script.append("\"");
	}
	
	public static AttributeRenderer getInstance(StringBuilder script) {
		return new AttributeRenderer(script);
	}
}
