package com.temenos.ds.t24.data.eson;

import javax.jms.IllegalStateException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.eson.ESONDataModel.Attribute;
import com.temenos.ds.t24.data.eson.ESONDataModel.Feature;
import com.temenos.ds.t24.data.eson.ESONDataModel.MultiValue;
import com.temenos.ds.t24.data.eson.ESONDataModel.NewObject;
import com.temenos.ds.t24.data.eson.ESONDataModel.Value;

/**
 * Parser of ESON grammar
 * @author yandenmatten
 */
public class ESONParser {

	private static Logger LOGGER = LoggerFactory.getLogger(ESONResourceProviderMapper.class);

	private final char[] ESON_TOKEN = "{}[]:".toCharArray();
	private ESONTokenizer esonTokenizer;
	private ESONDataModelFactory factory;
	
	public ESONParser(String input) {
		esonTokenizer = new ESONTokenizer(input);
		factory = new ESONDataModelFactory();
	}
	
	/**
	 * Parsing of 
	 * Factory:
	 * 	(imports += NamespaceImport)*
	 * 	(epackages+=PackageImport)*   // Not supported
	 * 	(annotations+=Annotation)*    // Not supported
	 * 	root=NewObject;
	 */
	public ESONDataModel parse() throws IllegalStateException {
		parseNamespaceImport();
		
		// TODO: replace by parseNewObject ?
		factory.setRootEClass(expectId("Class name"));
		expectToken("{");
		while (!nextTokenIs("}")) {
			Feature feature = parseFeature();
			factory.addRootFeature(feature);
		}
		expectToken("}");
		expectEndOfData();

		return factory.getEsonDataModel();
	}

	// Higher level grammar
	
	/**
	 * Not implemented parsing rule
	 * PackageImport:
	 * 'use' ePackage = [ecore::EPackage|QualifiedName] '.*';
	 */
	protected void parsePackageImport() {
		throw new RuntimeException("Not implemented");
	}
	
	/**
	 * Parsing of 
	 * NamespaceImport:
	 * 	'import' importedNamespace = QualifiedNameWithWildcard;
	 */
	protected void parseNamespaceImport() throws IllegalStateException {
		while (nextTokenIs("import")) {
			expectKeyword("import");
			String importedNamespace = expectId("Import namespace");
			factory.addImportedNamespace(importedNamespace);
		}
	}
	
	/** 
	 * Not implemented parsing rules
	 * Annotation:
	 * 	CustomNameMapping;
	 * CustomNameMapping:
	 * 	"@Name" "{" eClass=[ecore::EClass|QualifiedName] "=" nameFeature=[ecore::EAttribute] "}" ;
	 */
	protected void parseAnnotation() {
		throw new RuntimeException("Not implemented");
	}
	
	/** 
	 * Parsing of
	 * Feature:
	 * 	eFeature=[ecore::EStructuralFeature] ":" (value=Value)? ;
	 *  // value isn't really optional semantically of course
	 * 	// but because it may be missing while typing, this works out much better in practice like this
	 */
	private Feature parseFeature() throws IllegalStateException {
		Feature feature = factory.createFeature(expectId("Field name"));
		expectToken(":");
		feature.value = parseValue();
		return feature;
	}
	
	/**
	 * Parsing of
	 * NewObject:
	 * 	eClass=[ecore::EClass|QualifiedName] (name=ValidID)? "{" // optional name not supported
	 * 		(features += Feature)*
	 * 	"}";
	 */
	private NewObject parseNewObject() throws IllegalStateException {
		String objectType = expectId("eClass");
		NewObject newObject = factory.createNewObject(objectType);
		// optional name not supported
		expectToken("{");
		
		while (!nextTokenIs("}")) {
			Feature feature = parseFeature();
			newObject.features.add(feature);
		}
		expectToken("}");
		return newObject;
	}
	
	/**
	 * Parsing of
	 * Value:
	 * 	MultiValue
	 * 	| Attribute 
	 *  | => NewObject  // Note the original grammar used a inlined NewObject rule
	 * 	| => Reference; // Reference not supported
	 */
	private Value parseValue() throws IllegalStateException {
		if (nextTokenIs("[")) {
			return parseMultiValue();
		} else if (nextIsNewObject()) {
			return parseNewObject();
		} else {
			return parseAttribute();
		}
	}
	
	/**
	 * Basically this method is the implementation of => of the Value rule
	 */
	private boolean nextIsNewObject() {
		if (!esonTokenizer.hasNext()) {
			return false;
		}
		String firstNextToken = esonTokenizer.peekNextToken();
		String secondNextToken = esonTokenizer.peekSecondNextToken();
		
		if (firstNextToken == null || secondNextToken == null) {
			return false;
		}
		
		return !isESONToken(firstNextToken) && secondNextToken.equals("{");
	}

	/**
	 * Parsing of
	 * MultiValue:
	 *  "[" (values += Value)* "]" ;
	 */
	private MultiValue parseMultiValue() throws IllegalStateException {
		expectToken("[");
		MultiValue multiValue = factory.createMultiValue();
		while (!nextTokenIs("]")) {
			multiValue.values.add(parseValue());
		}
		expectToken("]");
		return multiValue;
	}
	
	/**
	 * Not implemented parsing rule
	 * Reference:
	 * 	value = [ecore::EObject|QualifiedName];
	 */
	protected void parseReference() {
		throw new RuntimeException("Not implmented");
	}
	
	
	/**
	 * Partial parsing of
	 * Attribute: 
	 *   (EnumAttribute |    // Not supported
	 *    StringAttribute | 
	 *    IntegerAttribute | // Not supported
	 *    BooleanAttribute | // Not supported
	 *    DoubleAttribute |  // Not supported
	 *    DateAttribute |    // Not supported
	 *    NullAttribute);    // Not supported
	 */
	private Attribute parseAttribute() throws IllegalStateException {
		return factory.createStringAttribute(expectStringValue("Field Single Value"));
	}

	
	// Low level grammar

	private boolean nextTokenIs(String expectedNextToken) {
		if (!esonTokenizer.hasNext()) {
			return false;
		}
		String nextToken = esonTokenizer.peekNextToken();
		return StringUtils.equals(nextToken, expectedNextToken);
	}

	private void expectKeyword(String keyword) throws IllegalStateException {
		String nextToken = esonTokenizer.nextToken();
		if (!StringUtils.equals(nextToken, keyword)) {
			String errorMessage = "The keyword \"" + keyword + "\" was expected, but the next token was \"" + nextToken + "\"";
			LOGGER.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
	}
	
	private void expectToken(String token) throws IllegalStateException {
		String nextToken = esonTokenizer.nextToken();
		if (!StringUtils.equals(nextToken, token)) {
			String errorMessage = "The token \"" + token + "\" was expected, but the next token was \"" + nextToken + "\"";
			LOGGER.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
	}
	
	private String expectOneOfToken(String... tokens) throws IllegalStateException {
		String nextToken = esonTokenizer.nextToken();
		for (String token: tokens) {
			if (StringUtils.equals(nextToken, token)) {
				return token;
			}
		}
		String errorMessage = "None of these token were found: " + StringUtils.join(tokens, ", ") + ". The next token is \"" + nextToken + "\"";
		LOGGER.error(errorMessage);
		throw new IllegalStateException(errorMessage);
	}
	
	private String expectId(String idName) throws IllegalStateException {
		if (!esonTokenizer.hasNext()) {
			String errorMessage = (idName!=null?idName:"Id") + " expected, but end of ESON reached";
			LOGGER.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
		return esonTokenizer.nextToken();
	}

	private String expectStringValue(String expectedStringName) throws IllegalStateException {
		if (!esonTokenizer.hasNext()) {
			String errorMessage = (expectedStringName!=null?expectedStringName:"Id") + " (string) expected, but end of ESON reached";
			LOGGER.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
		// ESON grammar says a string is either a wrapped between single quote or double quote
		String quote = expectOneOfToken("\"", "'");
		String stringValue = esonTokenizer.nextToken();
		expectToken(quote);
		return stringValue;
	}

	private void expectEndOfData() throws IllegalStateException {
		if (esonTokenizer.hasNext()) {
			String errorMessage = "End of ESON expected, but not reached. Next token is \"" + esonTokenizer.nextToken() + "\"";
			LOGGER.error(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
	}
	
	private boolean isESONToken(String token) {
		if (token.length() != 1) {
			return false; // all eson token are 1 char size
		}
		return ArrayUtils.contains(ESON_TOKEN, token.charAt(0));
	}
	
}
