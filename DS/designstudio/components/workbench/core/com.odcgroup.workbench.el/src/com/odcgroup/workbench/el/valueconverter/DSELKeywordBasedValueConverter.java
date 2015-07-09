package com.odcgroup.workbench.el.valueconverter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.KeywordBasedValueConverter;
import org.eclipse.xtext.nodemodel.INode;

public class DSELKeywordBasedValueConverter extends KeywordBasedValueConverter {

	private static final Map<String, String> opMappings = new HashMap<String, String>();
	
	static {
		opMappings.put("=", "==");
		opMappings.put("<>", "!=");
		opMappings.put("!<", ">=");
		opMappings.put("!>", "<=");
		opMappings.put("NOT", "!");
		opMappings.put("AND", "&&");
		opMappings.put("OR", "||");
	}
	
	@Override
	public String toValue(String string, INode node)
			throws ValueConverterException {
		// replace the optional compare notations with the default ones
		if(opMappings.containsKey(string)) {
			string = opMappings.get(string);
		}
		return super.toValue(string, node);
	}
	
	@Override
	public String toString(String value) throws ValueConverterException {
		// replace the optional compare notations with the default ones
		if(opMappings.containsKey(value)) {
			value = opMappings.get(value);
		}
		return super.toString(value);
	}
}
