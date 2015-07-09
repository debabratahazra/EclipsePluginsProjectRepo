package com.temenos.ds.t24.data.eson;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Holds the result of a parsing of an ESON
 * 
 * @author yandenmatten
 */
// TODO with MVO: check the list of unsupported items
public class ESONDataModel {

	public static abstract class Value {
		public abstract String toString(int level); 
	}

	/**
	 * Data model for
	 * Feature: eFeature=[ecore::EStructuralFeature] ":" (value=Value)? ;
	 */
	static public class Feature {
		public String eFeature;
		public Value value;

		public NewObject getValueAsNewObject()   { return (NewObject)value; }
		public MultiValue getValueAsMultiValue() { return (MultiValue)value; }
		public Attribute getValueAsAttribute()   { return (Attribute)value; }
		public StringAttribute getValueAsStringAttribute() { return (StringAttribute)value; }
		
		public String toString(int level) {
			StringBuilder result = new StringBuilder();
			result.append(indent(level) + eFeature + ": " + (value!=null?value.toString(level+1):null) + "\n");
			return result.toString();
		}
		
	}
	
	/**
	 * Data model for
	 * NewObject:
	 * 	eClass=[ecore::EClass|QualifiedName] (name=ValidID)? "{" // name not supported
	 * 		(features += Feature)*
	 * 	"}";
	 */
	static public class NewObject extends Value {
		public String eClass;
		// No support for optional name
		public List<Feature> features = new ArrayList<Feature>();
		
		public Feature getFeatureBy(String eFeature) {
			for (Feature feature: features) {
				if (eFeature.equals(feature.eFeature)) {
					return feature;
				}
			}
			throw new NoSuchElementException("The feature \"" + eFeature + "\" is not found.");
		}
		
		public String toString(int level) {
			StringBuilder result = new StringBuilder();
			
			result.append(indent(level) + eClass + " {\n");
			for (Feature feature: features) {
				result.append(feature.toString(level+1));
			}
			result.append(indent(level) + "}\n");
			return result.toString();
		}
	}
	
	/**
	 * DataModel for 
	 * MultiValue:
	 * 	{MultiValue} "[" (values += Value)* "]" ;
	 */
	static public class MultiValue extends Value {
		public List<Value> values = new ArrayList<Value>();
		
		public MultiValue getValueAsMultiValue(int index) { return (MultiValue)values.get(index); }
		public NewObject getValueAsNewObject(int index)   { return (NewObject)values.get(index); }
		public Attribute getValueAsAttribute(int index)   { return (Attribute)values.get(index); }
		
		public String toString(int level) {
			StringBuilder result = new StringBuilder();
			result.append("[");
			boolean onlyAttribute = onlyAttributeValues(values);
			if (!onlyAttribute) {
				result.append("\n");
			}
			for (Value value : values) {
				result.append(value.toString(level));
				if (onlyAttribute) {
					result.append(" ");
				}
			}
			if (!onlyAttribute) {
				result.append(indent(level-1));
			} else {
				result.deleteCharAt(result.length()-1); // remove the last space
			}
			result.append("]");
			return result.toString();
		}
		
		private boolean onlyAttributeValues(List<Value> values) {
			for (Value value: values) {
				if (!(value instanceof Attribute)) {
					return false;
				}
			}
			return true;
		}
	}
	
	// No support for references
	// Reference:
	//   value = [ecore::EObject|QualifiedName];
	static public class Reference { 
		public Reference() { throw new RuntimeException("Reference currently not supported"); }
	}
	
	/**
	 * Data model for
	 * Attribute: (EnumAttribute | StringAttribute | IntegerAttribute | BooleanAttribute | DoubleAttribute | DateAttribute | NullAttribute);
	 */
	static abstract public class Attribute extends Value {
	}

	/**
	 * Data model for
	 * StringAttribute:  value = STRING;
	 */
	static public class StringAttribute extends Attribute {
		public String value;
		
		public String toString(int level) {
			return "\"" + value + "\"";
		}
	}
	
	// No support for IntegerAttribute, DoubleAttribute, DateAttribute, NullAttribute and BooleanAttribute
	/**
	 * No support for IntegerAttribute 
	 */
	static public class IntegerAttribute { 
		public IntegerAttribute() { throw new RuntimeException("Integer attribute currently not supported");	}
	}
	
	/**
	 * No support for DoubleAttribute 
	 */
	static public class DoubleAttribute { 
		public DoubleAttribute() { throw new RuntimeException("Double attribute currently not supported");	}
	}
	
	/**
	 * DateAttribute
	 */
	static public class NullAttribute { 
		public NullAttribute() { throw new RuntimeException("Null attribute currently not supported");	}
	}
	
	/**
	 * BooleanAttribute
	 */
	static public class BooleanAttribute { 
		public BooleanAttribute() { throw new RuntimeException("Boolean attribute currently not supported");	}
	}
	
	/**
	 * Data model for
	 * Factory:
	 * 	(imports += NamespaceImport)*
	 * 	(epackages+=PackageImport)*   // Not supported
	 * 	(annotations+=Annotation)*    // Not supported
	 * 	root=NewObject;
	 */
	public List<String> importedNamespaces = new ArrayList<String>(); // Simplification compared to the ESON grammar
	// No support for Package imports
	// No support for Annotations
	public NewObject root = new NewObject();
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (String importedNamespace : importedNamespaces) {
			result.append("import ");
			result.append(importedNamespace);
			result.append("\n");
		}
		if (importedNamespaces.size() > 0) {
			result.append("\n");
		}
		
		result.append(root.toString(0));
		return result.toString();
	}
	
	private static String indent(int level) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i<level; i++) {
			result.append("\t");
		}
		return result.toString();
	}
}

/**
 * Data model for
 * Value:
 * 	MultiValue
 * 	| Attribute 
 * 	| ( // Following is an inlinedNewObject, if making changes, adapt above too
 * 		=>({NewObject} eClass=[ecore::EClass|QualifiedName] (name=ValidID)? "{")
 * 			(features += Feature)*
 * 		"}"
 * 		{Containment.value=current}
 * 	)
 * 	// The Syntactic Predicate => is needed here to solve "Decision can match input such as "RULE_ID '.' RULE_ID {RULE_STRING..RULE_DATE, '}', '['..'false'}" using multiple alternatives: 3, 4"
 * 	| => Reference; // Not supported
 */
/*abstract class Value {
	public abstract String toString(int level); 
}*/
