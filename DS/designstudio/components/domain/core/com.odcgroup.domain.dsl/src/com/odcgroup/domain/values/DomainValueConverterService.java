package com.odcgroup.domain.values;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import com.odcgroup.workbench.dsl.values.DSLValueConverterService;

public class DomainValueConverterService extends DSLValueConverterService {

	/* we define a list of known namespaces, which are replaced by a short name to make
	 * the serialization more compact and nicer looking.
	 */
	private static Map<String, String> knownNamespaces = new HashMap<String, String>();
	
	static {
		knownNamespaces.put("mdf", "http://www.odcgroup.com/mdf");
		knownNamespaces.put("aaa", "http://www.odcgroup.com/mdf/aaa");
		knownNamespaces.put("sql", "http://www.odcgroup.com/mdf/sql");
		knownNamespaces.put("gui", "http://www.odcgroup.com/mdf/ext/gui");
		knownNamespaces.put("con", "http://www.odcgroup.com/mdf/ext/constraints");
		knownNamespaces.put("java", "http://www.odcgroup.com/mdf/java");
		knownNamespaces.put("svc", "http://www.odcgroup.com/mdf/services");
		knownNamespaces.put("qb", "http://www.odcgroup.com/querybuilder");
		knownNamespaces.put("odcql", "http://www.odcgroup.com/heart/qe/odcql");
		knownNamespaces.put("back", "http://www.odcgroup.com/mdf/back");
		knownNamespaces.put("gcl", "http://www.odcgroup.com/mdf/gcl");
		knownNamespaces.put("castor", "http://www.odcgroup.com/mdf/castor");		
		knownNamespaces.put("i18n", "http://www.odcgroup.com/mdf/translation");		
		knownNamespaces.put("cdm", "http://www.odcgroup.com/mdf/cdm");		
		knownNamespaces.put("custo", "http://www.odcgroup.com/mdf/customization");		
		knownNamespaces.put("annotations", "http://www.odcgroup.com/mdf/annotations");
		// NOTE The following NS String is also in the T24Aspect and must be kept in sync
		knownNamespaces.put("t24", "http://www.temenos.com/t24");		
	}
	
	@ValueConverter(rule = "Namespace")
	public IValueConverter<String> Namespace() {
		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node) throws ValueConverterException {
				if (string == null) return null;
				String namespace = knownNamespaces.get(string);
				if(namespace!=null) return namespace;
				if(string.startsWith("\"") && string.endsWith("\"")) return string.substring(1, string.length()-1);
				return string;
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				if(knownNamespaces.containsValue(value)) {
					for(Entry<String, String> entry : knownNamespaces.entrySet()) {
						if(entry.getValue().equals(value)) return entry.getKey();
					}
				}
				return "\"" + value + "\"";
			}
			
		};
	}

	@ValueConverter(rule = "UniqueBoolean")
	public IValueConverter<Boolean> UniqueBoolean() {
		return new IValueConverter<Boolean>() {

			@Override
			public Boolean toValue(String string, INode node) throws ValueConverterException {
				return "notUnique".equals(string) ? false : true;
			}

			@Override
			public String toString(Boolean value) throws ValueConverterException {
				return value ? "unique" : "notUnique";
			}
		};
	}
	
	@ValueConverter(rule = "Documentation")
	public IValueConverter<String> Documentation() {
		return new IValueConverter<String>() {

			@Override
			public String toValue(String string, INode node) throws ValueConverterException {
				if(string!=null) {
					if(string.length()>4) {
						return string.substring(2, string.length()-2).trim();
					} else {
						return string;
					}
				} else {
					return null;
				}
			}

			@Override
			public String toString(String value) throws ValueConverterException {
				if(value==null) return null;
				return "/* " + value.trim() + " */";
			}
			
		};
	}
	
	@Override
	protected boolean isKeyword(String value) {
		String[] keywords = new String[] { "PK", "BK", "required", "acceptNullValue", "deprecationInfo",
				"Domain", "ONE", "cDATA", "synchronized", "default", "abstract", "secured", "extends", 
				"reverse", "singleValued", "byReference", "byValue", "notUnique", "Enumerations", "BusinessTypes", "Classes", "Datasets" };
		return ArrayUtils.contains(keywords, value);
	}
}
