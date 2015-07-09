package com.temenos.ds.t24.data.eson;

import javax.jms.IllegalStateException;

import org.junit.Test;

/**
 * @author yandenmatten
 */
public class ESONParserTest {
	
	@Test
	public void testValidImportStatements() throws IllegalStateException {
		new ESONParser("").parseNamespaceImport();
		new ESONParser("import something").parseNamespaceImport();
		new ESONParser("import something\nimport something_else").parseNamespaceImport();
		new ESONParser("import something\r\nimport something_else").parseNamespaceImport();
		new ESONParser("import something\nimport something_else\nrest of eson").parseNamespaceImport();
		new ESONParser("import something import something_else rest of eson").parseNamespaceImport();
		new ESONParser("no_import clause in this one").parseNamespaceImport();

	}

	@Test(expected=IllegalStateException.class)
	public void testInvalidImportStatement1() throws IllegalStateException {
		new ESONParser("import").parseNamespaceImport();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testInvalidImportStatement2() throws IllegalStateException {
		new ESONParser("import something import").parseNamespaceImport();
	}
	
	
	@Test
	public void testValidSimpleESON() throws IllegalStateException {
		new ESONParser("ClassName { }").parse();
		new ESONParser("ClassName { field1 : \"ABC\" }").parse();
		new ESONParser("ClassName { field1 : \"ABC\" field2: \"ABC\" field3 :\"ABC\"}").parse();
		new ESONParser("ClassName { field1:\"ABC\"\nfield2:\"ABC\"\nfield3:\"ABC\"\n}\n").parse();
	}
	
	@Test
	public void testValidMultiValuedESON() throws IllegalStateException {
		new ESONParser("ClassName { field1 : [type{key1:\"ABC\"}]}").parse();
		new ESONParser("ClassName { field1 : [type{key1:\"ABC\" key2:\"DEF\"}]}").parse();
	}
	
	@Test
	public void testValidMultiValuedAndSubValuedESON() throws IllegalStateException {
		new ESONParser("ClassName { field1 : [type{key1:[\"ABC\" \"DEF\"]}]}").parse();
		new ESONParser("ClassName { field1 : [type{key1:[\"ABC\" \"DEF\"] key2:[\"ABC\" \"DEF\"]}]}").parse();
	}
	
}
