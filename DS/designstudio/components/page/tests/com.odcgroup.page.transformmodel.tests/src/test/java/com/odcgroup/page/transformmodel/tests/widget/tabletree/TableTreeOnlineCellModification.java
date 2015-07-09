package com.odcgroup.page.transformmodel.tests.widget.tabletree;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.eclipse.emf.common.util.URI;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.odcgroup.page.transformmodel.tests.AbstractDSPageTransformationUnitTest;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;

public class TableTreeOnlineCellModification extends AbstractDSPageTransformationUnitTest {

	private static final String STANDARD_NAMESPACES_DECLARATION = 
			" xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" " +
			"xmlns:i18n=\"http://apache.org/cocoon/i18n/2.1\" " +
			"xmlns:ic=\"http://www.odcgroup.com/uif/inputcontrol/0.1\" " +
			"xmlns:nav=\"http://www.odcgroup.com/uif/nav/0.1\" " +
			"xmlns:scope=\"http://www.odcgroup.com/uif/scope/0.1\" " +
			"xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" " +
			"xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" " +
			"xmlns:xsp=\"http://apache.org/xsp\" ";
	
	@Before
	public void setUp() {
		createModelsProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	/**
	 * This test validate that a correct bean definition (as specified in the FDS 7.1) works
	 */
	@Test
	public void testFDS7_1TableTreeAssert() throws SAXException, IOException {
		assertFDS7_1TableTreeSubtree("<bean:define xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" key=\"DSTableModel.Ds4884.DS4884_DSOrderList.entities\" name=\"DS4884_DSExtOperation\" prefix-keyword=\"final\">\n" +
				"	<bean:param xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" name=\"property\">(<udp:item xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" column=\"id\"/>)</bean:param>\n" +
				"</bean:define>");
	}
	
	/**
	 * Test if the subtree passed in parameter (the relevant xml part) match the FDS 7.1 specification about bean definition
	 */
	private void assertFDS7_1TableTreeSubtree(String subtree) throws SAXException, IOException {
		assertXml("The paragraph 7.1 of the Online cell editing FDS is not properly implemented", 
				"<bean:define " + STANDARD_NAMESPACES_DECLARATION + " key=\"DSTableModel.Ds4884.DS4884_DSOrderList.entities\" name=\"DS4884_DSExtOperation\" prefix-keyword=\"final\">\n" +
				"<bean:param name=\"property\">(<udp:item column=\"id\"/>)</bean:param>\n" +
				"</bean:define>", subtree);
	}

	/**
	 * Extract the relevant subtree from the generated xml and run an Assert.assert against it
	 */
	private void assertFDS7_1TableTree(String generatedXml) throws UnsupportedEncodingException, XpathException, SAXException, IOException {
		String subtree = extractFirstSubtree(
				"/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/udp:udp[1]/udp:render-list[1]/xgui:table[1]/udp:for-each-row[1]/xgui:row[1]/bean:define[1]",
				generatedXml);
		Assert.assertFalse("Subtree not found", subtree.isEmpty());
		assertFDS7_1TableTreeSubtree(subtree);
	}

	/**
	 * This test validate that a correct column definition (as specified in the FDS 7.3) works
	 */
	@Test
	public void testFDS7_3ColumnHeaderSimplifiedAssert() throws SAXException, IOException, JDOMException {
		assertFDS7_3ColumnHeaderSimplifiedSubtree(
				"<xgui:column xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" enabled=\"true\">\n" +
                "	<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"id\">P8W2_.code</xsp:attribute>\n" +
                "	<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"name\"><udp:column-name xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" /></xsp:attribute>\n" +
                "	<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"type\"><udp:column-type xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" column=\"code\"/></xsp:attribute>\n" +
                "	<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"sorting\"><xsp:expr xmlns:xsp=\"http://apache.org/xsp\">sorting</xsp:expr></xsp:attribute>\n" +
                "</xgui:column>");
	}

	/**
	 * Test if the subtree passed in parameter (the relevant xml part) match the FDS 7.3 specification about column definition
	 */
	private void assertFDS7_3ColumnHeaderSimplifiedSubtree(String subtree) throws SAXException, IOException, JDOMException {
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build(new StringReader(subtree));
		
		// Check if xsp:attribute name="name" exists
		@SuppressWarnings("rawtypes") List attributes = document.getRootElement().getChildren("attribute", document.getRootElement().getNamespace("xsp"));
		Assert.assertTrue("Should at least have 2 attribute", attributes.size() >= 2);
		Element element = (Element)attributes.get(1);
		Assert.assertEquals("Should have a name attribute with a name value", "name", element.getAttributeValue("name"));
		Assert.assertEquals("Should a one and only one child", 1, element.getChildren().size());
		Element columnName = element.getChild("column-name", document.getRootElement().getNamespace("udp"));
		Assert.assertNotNull("Should define a column name", columnName);
		
		// Check if xgui:onevent is not under xgui:column
		Assert.assertEquals("shouldn't have any event defined", 0, document.getRootElement().getChildren("onevent", document.getRootElement().getNamespace("xgui")).size());
	}
	
	/**
	 * Extract the relevant subtree from the generated xml and run an Assert.assert against it
	 */
	private void assertFDS7_3ColumnHeaderSimplified(String generatedXml) throws UnsupportedEncodingException, XpathException, SAXException, IOException, JDOMException {
		String subtree = extractFirstSubtree(
				"/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/udp:udp[1]/udp:render-list[1]/xgui:table[1]/xgui:columns[1]/xgui:column[1]",
				generatedXml);
		Assert.assertFalse("Subtree not found", subtree.isEmpty());
		assertFDS7_3ColumnHeaderSimplifiedSubtree(subtree);
	}

	/**
	 * This test validate that a correct cell definition (as specified in the FDS 7.4) works
	 */
	@Test
	public void testFDS7_4CellContentGenerationAssert() throws SAXException, IOException, JDOMException {
		Map<String,String> context = new HashMap<String, String>();
		context.put("widget-name", "textfield");
		context.put("submit-attribute", "instr.code");
		assertFDS7_4CellContentGenerationSubtree(
				"<xgui:cell xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" >\n" +
				"	<xsp:logic xmlns:xsp=\"http://apache.org/xsp\" >if (<bean:is-true xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" key=\"DSBean.Ds4884.DS4884_DSOrderList.updateMode\"/>) {</xsp:logic>\n" +
				"	<xgui:textfield xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" widget-group=\"form\" type=\"text\">\n" +
				"		<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"id\"><bean:get-property xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" bean=\"DS4884_DSExtOperation\" property=\"id\"/>.instr.code</xsp:attribute>\n" + 
				"		<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"editable\"><udp:item xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" column=\"permission\"/></xsp:attribute>\n" + 
				"		<xgui:value xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" >\n" +
				"			<bean:get-property xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" bean=\"DS4884_DSExtOperation\" property=\"instr.code\"/>\n" +
				"		</xgui:value>\n" +
				"	</xgui:textfield>\n" +
				"	<xsp:logic xmlns:xsp=\"http://apache.org/xsp\" >} else {</xsp:logic>\n" +
				"		<xgui:label xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" >\n" +
				"			<xgui:text xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" ><udp:item xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" column=\"code\"/></xgui:text>\n" +
				"		</xgui:label>\n" +
				"	<xsp:logic xmlns:xsp=\"http://apache.org/xsp\">}</xsp:logic>\n" +
				"</xgui:cell>",
				context);
	}

	/**
	 * This test validate that a correct cell definition (as specified in the FDS 7.4) works
	 */
	@Test
	public void testFDS7_4CellContentGenerationAssert_withSumTreeCol() throws SAXException, IOException, JDOMException {
		Map<String,String> context = new HashMap<String, String>();
		context.put("widget-name", "textfield");
		context.put("submit-attribute", "instr.code");
		context.put("sumTreeCol", "true");
		assertFDS7_4CellContentGenerationSubtree(
				"<xgui:cell xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" >\n" +
				"	<xsp:logic xmlns:xsp=\"http://apache.org/xsp\" >if (<bean:is-true xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" key=\"DSBean.Ds4884.DS4884_DSOrderList.updateMode\"/>) {</xsp:logic>\n" +
				"	<xgui:textfield xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" widget-group=\"form\" type=\"text\">\n" +
				"		<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"id\"><bean:get-property xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" bean=\"DS4884_DSExtOperation\" property=\"id\"/>.instr.code</xsp:attribute>\n" + 
				"		<xsp:attribute xmlns:xsp=\"http://apache.org/xsp\" name=\"editable\"><udp:item xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" column=\"permission\"/></xsp:attribute>\n" + 
				"		<xgui:value xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" >\n" +
				"			<bean:get-property xmlns:bean=\"http://www.odcgroup.com/uif/bean/0.1\" bean=\"DS4884_DSExtOperation\" property=\"instr.code\"/>\n" +
				"		</xgui:value>\n" +
				"       <xgui:onevent type=\"change\">\n" +
				"         <xgui:sumTreeCol/>" +
				"       </xgui:onevent>" +
				"	</xgui:textfield>\n" +
				"	<xsp:logic xmlns:xsp=\"http://apache.org/xsp\" >} else {</xsp:logic>\n" +
				"		<xgui:label xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" >\n" +
				"			<xgui:text xmlns:xgui=\"http://www.odcgroup.com/uif/xgui/0.1\" ><udp:item xmlns:udp=\"http://www.odcgroup.com/uif/udp/0.1\" column=\"code\"/></xgui:text>\n" +
				"		</xgui:label>\n" +
				"	<xsp:logic xmlns:xsp=\"http://apache.org/xsp\">}</xsp:logic>\n" +
				"</xgui:cell>",
				context);
	}

	/**
	 * Test if the subtree passed in parameter (the relevant xml part) match the FDS 7.4 specification about the cell contents
	 */
	@SuppressWarnings("rawtypes")
	private void assertFDS7_4CellContentGenerationSubtree(String subtree, Map<String, String> context) throws SAXException, IOException, JDOMException {
		SAXBuilder parser = new SAXBuilder();
		Document document = parser.build(new StringReader(subtree));

		String widgetName = context.get("widget-name"); 
		
		// Check if xsp:attribute name="name" exists
		{
			List logic = document.getRootElement().getChildren("logic", document.getRootElement().getNamespace("xsp"));
			if (! widgetName.equals("combobox")) {
				Assert.assertEquals("Should have 3 logic tags which handle the editing mode according to FDS 7.4", 3, logic.size());
			} else {
				// DS-5347 display translation for a value bound to an enumeration, brings 3 additional logic elements
				Assert.assertEquals("Should have 6 logic tags which handle the editing mode according to FDS 7.4", 6, logic.size());
			}
			Element logic1 = (Element)logic.get(0);
			Assert.assertEquals(3, logic1.getContent().size());
			Assert.assertEquals("if (", ((Text)logic1.getContent().get(0)).getText());
			Element isTrue = (Element)logic1.getContent().get(1);
			Assert.assertEquals("is-true", isTrue.getName());
			String dataset = "Ds4884.DS4884_DSOrderList";
			Assert.assertEquals("DSBean." + dataset + ".updateMode", isTrue.getAttributeValue("key"));
			Text logicText = (Text)logic1.getContent().get(2);
			Assert.assertEquals(") {", logicText.getText());
			
			Element logic2 = (Element)logic.get(1);
			Assert.assertEquals("} else {", logic2.getText());
			Element logic3 = (Element)logic.get(2);
			if (! widgetName.equals("combobox")) {
				Assert.assertEquals("}", logic3.getText());
			} else {
				// in the Assert.assert below the udp elements has been ignored.
				Assert.assertEquals("if(null ==  || .equals(\"\")) {", logic3.getText());
				Element logic4 = (Element)logic.get(3);
				Assert.assertEquals("} else {", logic4.getText());
				Element logic5 = (Element)logic.get(4);
				Assert.assertEquals("}", logic5.getText());
				Element logic6 = (Element)logic.get(5);
				Assert.assertEquals("}", logic6.getText());
			}
		}
		
		// Check editable generation
		{
			Element editableWidget = (Element)document.getRootElement().getChildren().get(1);
			boolean hasSumTreeCol = 
					context.containsKey("sumTreeCol") && 
					context.get("sumTreeCol").equals("true");
			Assert.assertEquals("Wrong widget for editable widget according to FDS 7.4.1", widgetName, editableWidget.getName());
			if (widgetName.equals("autocomplete")) {
				Assert.assertEquals("formSearch", editableWidget.getAttributeValue("widget-group"));
			} else {
				Assert.assertEquals("form", editableWidget.getAttributeValue("widget-group"));
			}
			
			int editableWidgetNbExpectedChildren = 3;
			if (widgetName.equals("caldatefield")) {
				editableWidgetNbExpectedChildren = 4;
			} else if (widgetName.equals("autocomplete")) { 
				editableWidgetNbExpectedChildren = 8;				
			} else if (widgetName.equals("combobox")) { 
				editableWidgetNbExpectedChildren = 13;
			} else if (widgetName.equals("autocomplete")) {
				editableWidgetNbExpectedChildren = 13;
			} else if (hasSumTreeCol) {
				editableWidgetNbExpectedChildren = 4;
			}
			Assert.assertEquals(editableWidgetNbExpectedChildren, editableWidget.getChildren().size());
			
			if (!widgetName.equals("autocomplete")) {
				Element attribute1 = (Element)editableWidget.getChildren().get(0);
				Assert.assertEquals("attribute", attribute1.getName());
				Assert.assertEquals("id", attribute1.getAttributeValue("name"));
				// <xsp:expr><udp:item column="id"/></xsp:expr>.instr.code
				{
					Assert.assertEquals(1, attribute1.getChildren().size());
					Element expr = (Element)attribute1.getChildren().get(0);
					Assert.assertEquals("get-property", expr.getName());
					Assert.assertEquals(2, expr.getAttributes().size());
					
					Assert.assertEquals(0, expr.getChildren().size());
					Assert.assertEquals("id", expr.getAttributeValue("property"));
					
					Text text = (Text)attribute1.getContent().get(1);
					Assert.assertEquals("." + context.get("submit-attribute"), text.getText());
					
				}
				Element attribute2 = (Element)editableWidget.getChildren().get(1);
				Assert.assertEquals("attribute", attribute2.getName());
				Assert.assertEquals("editable", attribute2.getAttributeValue("name"));
				// <udp:item column property="permission"/>
				{
					Assert.assertEquals(1, attribute2.getChildren().size());
					Element getProperty = (Element)attribute2.getChildren().get(0);
					Assert.assertEquals(getProperty.getName(), "item");
					String permission = "permission";
					Assert.assertEquals(permission, getProperty.getAttributeValue("column"));
				}
			}
			
			int valueIndex = 2;
			if (widgetName.equals("caldatefield")) {
				// Additionally expect this for calendar: 
				// <xsp:attribute name="date-format"><udp:datetime-pattern option="date-only"/></xsp:attribute>
				Element attribute3 = (Element)editableWidget.getChildren().get(2);
				Assert.assertEquals("attribute", attribute3.getName());
				Assert.assertEquals("date-format", attribute3.getAttributeValue("name"));
				Assert.assertEquals(1, attribute3.getChildren().size());
				Assert.assertEquals("datetime-pattern", ((Element)attribute3.getChildren().get(0)).getName());
				
				valueIndex = 3;
			} 
			
			if (!widgetName.equals("checkbox")) {
				Element value = (Element)editableWidget.getChildren().get(valueIndex);
				if (widgetName.equals("combobox")) { 
					Assert.assertEquals("selected-value", value.getName());					
				} else {
					Assert.assertEquals("value", value.getName());
				}
				{
					Assert.assertEquals(1, value.getChildren().size());
					Element getProperty = (Element)value.getChildren().get(0);
					if (widgetName.equals("caldatefield")) {
						// calendar have a format datetime tag before the get-property
						Element formatDatetime = getProperty;
						getProperty = (Element)formatDatetime.getChildren().get(0);
					}
					Assert.assertEquals("get-property", getProperty.getName());
					String bean = "DS4884_DSExtOperation";
					Assert.assertEquals(bean, getProperty.getAttributeValue("bean"));
					Assert.assertEquals(context.get("submit-attribute"), getProperty.getAttributeValue("property"));
				}
			}
			if (hasSumTreeCol) {
				Element onEvent = (Element)editableWidget.getChildren().get(valueIndex+1);
				Assert.assertEquals("onevent", onEvent.getName());
				Assert.assertEquals("change", onEvent.getAttributeValue("type"));
				Assert.assertEquals(1, onEvent.getChildren().size());
				Element sumTreeCol = (Element)onEvent.getChildren().get(0);
				Assert.assertEquals("sumTreeCol", sumTreeCol.getName());
			}
		}
		
		// Check non editable generation
		if (!widgetName.equals("combobox") && !widgetName.equals("caldatefield")) {
			// regarding combo the this kind of test has already been done with the xml comparison
			Element nonEditableWidget = (Element)document.getRootElement().getChildren().get(3);
			Assert.assertEquals("label", nonEditableWidget.getName());
			List labelChildren = nonEditableWidget.getChildren();
			Assert.assertEquals(1, labelChildren.size());
			Element text = (Element)labelChildren.get(0);
			Assert.assertEquals("text", text.getName());
			List textChildren = text.getChildren();
			Assert.assertEquals(1, textChildren.size());
			Element item = (Element)textChildren.get(0);
			Assert.assertEquals("item", item.getName());
			List itemAttributes = item.getAttributes();
			Assert.assertEquals(1, itemAttributes.size());
			Attribute itemAttribute = (Attribute)itemAttributes.get(0);
			Assert.assertEquals("column", itemAttribute.getName());
			Assert.assertEquals("code", itemAttribute.getValue());
		}
	}
	
	private void assertFDS7_4CellContentGeneration(String generatedXml, Map<String,String> context) throws UnsupportedEncodingException, XpathException, SAXException, IOException, JDOMException {
		String subtree = extractFirstSubtree(
				"/xsp:page[1]/xgui:module[1]/nav:managerequest[1]/xgui:vbox[1]/udp:udp[1]/udp:render-list[1]/xgui:table[1]/udp:for-each-row[1]/xgui:row[1]/xgui:cell[1]",
				generatedXml);
		Assert.assertFalse("Subtree not found", subtree.isEmpty());
		assertFDS7_4CellContentGenerationSubtree(subtree, context);
	}


	/**
	 * Test expected results file for text field
	 */
	@Test
	public void testExpectedResultsForTextField() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, XpathException, SAXException, JDOMException {
			String expectedXml = readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnTextField.xml");
			assertFDS7_1TableTree(expectedXml);
			assertFDS7_3ColumnHeaderSimplified(expectedXml);
			Map<String, String> context = new HashMap<String, String>();
			context.put("widget-name", "textfield");
			context.put("submit-attribute", "quantity");
			assertFDS7_4CellContentGeneration(expectedXml, context);
	}

	/**
	 * Test expected results file for text area
	 */
	@Test
	public void testExpectedResultsForTextArea() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, XpathException, SAXException, JDOMException {
			String expectedXml = readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnTextArea.xml");
			assertFDS7_1TableTree(expectedXml);
			assertFDS7_3ColumnHeaderSimplified(expectedXml);
			Map<String, String> context = new HashMap<String, String>();
			context.put("widget-name", "textarea");
			context.put("submit-attribute", "quantity");
			assertFDS7_4CellContentGeneration(expectedXml, context);
	}

	/**
	 * Test expected results file for text area
	 */
	@Test
	public void testExpectedResultsForCalendar() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, XpathException, SAXException, JDOMException {
			String expectedXml = readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnCalendar.xml");
			assertFDS7_1TableTree(expectedXml);
			assertFDS7_3ColumnHeaderSimplified(expectedXml);
			Map<String, String> context = new HashMap<String, String>();
			context.put("widget-name", "caldatefield");
			context.put("submit-attribute", "date2");
			assertFDS7_4CellContentGeneration(expectedXml, context);
	}

	/**
	 * Test expected results file for text area
	 */
	@Test
	public void testExpectedResultsForCheckbox() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, XpathException, SAXException, JDOMException {
			String expectedXml = readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnCheckbox.xml");
			assertFDS7_1TableTree(expectedXml);
			assertFDS7_3ColumnHeaderSimplified(expectedXml);
			Map<String, String> context = new HashMap<String, String>();
			context.put("widget-name", "checkbox");
			context.put("submit-attribute", "flag2");
			assertFDS7_4CellContentGeneration(expectedXml, context);
	}

	/**
	 * Test expected results file for text area
	 */
	@Test
	public void testExpectedResultsForCombobox() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, XpathException, SAXException, JDOMException {
			String expectedXml = readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnCombobox.xml");
			assertFDS7_1TableTree(expectedXml);
			assertFDS7_3ColumnHeaderSimplified(expectedXml);
			Map<String, String> context = new HashMap<String, String>();
			context.put("submit-attribute", "statut2");
			context.put("widget-name", "combobox");
			assertFDS7_4CellContentGeneration(expectedXml, context);
	}

	/**
	 * Test expected results file for text area
	 */
	@Test
	public void testExpectedResultsForSearchField() throws ModelNotFoundException, IOException, InvalidMetamodelVersionException, XpathException, SAXException, JDOMException {
			String expectedXml = readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnSearchField.xml");
			assertFDS7_1TableTree(expectedXml);
			assertFDS7_3ColumnHeaderSimplified(expectedXml);
			Map<String, String> context = new HashMap<String, String>();
			context.put("widget-name", "autocomplete");
			context.put("submit-attribute", "instr.code");
			assertFDS7_4CellContentGeneration(expectedXml, context);
	}

	/**
	 * Test the generation of a table with only one column that is editable as a text field
	 * 
	 * Expected changes
	 * 7.1 Table/tree
	 * should contains a <bean:define>. Example:
	 * <bean:define key="DSBean.Ds4884.DS4884_DSExtOperation.entities" name="DS4884_DSExtOperation">
	 *   <bean:param name="property">(<udp:item column="id"/>)</bean:param>
	 * </bean:define>
	 * 
	 * 7.2 Column Sorter Declaration
	 * should add a condition around sort-column(s). Example:
	 * <udp:sort>
	 *   <udp:handle><scope:get-module-rank/>s</udp:handle>
	 *   <xsp:logic>if ( ! <bean:is-true key="DSBean.Ds4884.DS4884_DSExtOperation.updateMode"/>) {</xsp:logic>
	 *     <udp:sort-column>code</udp:sort-column>
	 *   <xsp:logic>}</xsp:logic>
	 * </udp:sort>
	 * 
	 * 7.3 Column header simplified generation
	 * should add an attribute name set to <upd:column-name/>
	 * <xgui:column enabled="true">
	 *   <xsp:attribute name="id">P6Z5_.code</xsp:attribute>
	 *   <xsp:attribute name="name"><udp:column-name/></xsp:attribute>
	 *   <xsp:attribute name="type"><udp:column-type column="code"/></xsp:attribute>
	 *   <xsp:attribute name="sorting"><xsp:expr>sorting</xsp:expr></xsp:attribute>
	 *  </xgui:column>
	 *  
	 *  7.4 Cell Content Generation & 7.4.1 Generation of the Editable Widget in a cell
	 *  should include an update mode test and textfield widget with correct attributes.
	 *  Example:
	 *  <xgui:cell>
	 *    <xsp:logic>if (<bean:is-true key="DSBean.Ds4884.DS4884_DSExtOperation.updateMode"/>) {</xsp:logic>
	 *      <xgui:textfield widget-group="form" type="text">
	 *        <xsp:attribute name=�id�><expr><udp:item column="id"/></expr>.instr.code</xsp:attribute>
	 *        <xsp:attribute name="editable"><bean:get-property bean="DS4884_DSOrderList" property="permission"/></xsp:attribute>
	 *        <xgui:value>
	 *          <bean:get-property bean="DS4884_DSExtOperation" property="instr.code"/>
	 *        </xgui:value>
	 *      </xgui:textfield>
	 *    <xsp:logic>} else {</xsp:logic>
	 *      <xgui:label>
	 *        <xgui:text><udp:item column="code"/></xgui:text>
	 *      </xgui:label>
	 *    <xsp:logic>}</xsp:logic>
	 *  </xgui:cell>
	 */
	@Test
	public void testDS4884TableColumnTextField() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnTextField.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		URI uri = getResourceURI("/Default/module/DS4884_TableColumnTextField.module");
		String generatedXml = createXmlFromUri(uri);
		
		assertFDS7_1TableTree(generatedXml);
		assertFDS7_3ColumnHeaderSimplified(generatedXml);
		Map<String, String> context = new HashMap<String, String>();
		context.put("widget-name", "textfield");		
		assertTransformation(
				uri, 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnTextField.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[3]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[4]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[5]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[6]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[3]");
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a text field
	 * This test include a sumTreeCol function on a OnChange event as specified in the FDS 6.4.3
	 */
	@Test
	public void testDS4884TableColumnTextField_withSumTreeColFunction() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnTextField_withSumTreeColFunction.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		assertTransformation(
				getResourceURI("/Default/module/DS4884_TableColumnTextField_withSumTreeColFunction.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_withSumTreeCol.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a text area
	 */
	@Test
	public void testDS4884TableColumnTextArea() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnTextArea.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		assertTransformation(
				getResourceURI("/Default/module/DS4884_TableColumnTextArea.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnTextArea.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[3]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[4]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[5]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[6]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[3]");
	}
	

	/**
	 * Test the generation of a table with only one column that is editable as a calendar
	 */
	@Test
	public void testDS4884TableColumnCalendar() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnCalendar.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		assertTransformation(
				getResourceURI("/Default/module/DS4884_TableColumnCalendar.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnCalendar.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[3]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[4]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[5]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[6]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[3]");
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a checkbox
	 */
	@Test
	public void testDS4884TableColumnCheckbox() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnCheckbox.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		assertTransformation(
				getResourceURI("/Default/module/DS4884_TableColumnCheckbox.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnCheckbox.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[3]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[4]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[5]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[6]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[3]");
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a combobox
	 */
	@Test
	public void testDS4884TableColumnCombobox() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnCombobox.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		assertTransformation(
				getResourceURI("/Default/module/DS4884_TableColumnCombobox.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnCombobox.xml"),
				"/page[1]/comment()[1]");
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a search field
	 */
	@Test
	public void testDS4884TableColumnSearchField() throws Exception {
		copyResourceInModelsProject("module/Default/module/DS4884_TableColumnSearchField.module");
		copyResourceInModelsProject("domain/ds4884/DS4884.domain");
		
		assertTransformation(
				getResourceURI("/Default/module/DS4884_TableColumnSearchField.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS4884_TableColumnSearchField.xml"),
				"/page[1]/comment()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[3]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[4]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[5]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/label[1]/text[1]/text()[6]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[1]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[1]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[2]", 
				"/page[1]/module[1]/managerequest[1]/vbox[1]/udp[1]/render-list[1]/hbox[1]/logic[1]/content[1]/label[3]/text[1]/text()[3]");
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a search field
	 */
	@Test
	public void testDS5312TableColumnComboboxWithFK() throws Exception {
		copyResourceInModelsProject("domain/ds5312/DS5312Domain.domain");
		copyResourceInModelsProject("module/Default/module/DS5312_TableColumnComboboxWithFK.module");
		
		assertTransformation(
				getResourceURI("/Default/module/DS5312_TableColumnComboboxWithFK.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS5312_TableColumnComboboxWithFK.xml"),
				"/page[1]/comment()[1]" 
				);
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a search field
	 */
	@Test
	public void testDS5315TableColumnEditableWidgetInParentHierarchy() throws Exception {
		copyResourceInModelsProject("domain/ds5315/DS5315Domain.domain");
		copyResourceInModelsProject("module/Default/module/DS5315_TableColumnEditableWidgetInParentHierarchy.module");
		
		assertTransformation(
				getResourceURI("/Default/module/DS5315_TableColumnEditableWidgetInParentHierarchy.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS5315_TableColumnHierarchy.xml"),
				"/page[1]/comment()[1]" 
				);
	}
	
	/**
	 * Test the generation of a table with only one column that is editable as a search field
	 */
	@Test
	public void testDS5349TableColumnTextWidgetInConditional() throws Exception {
		copyResourceInModelsProject("domain/ds5315/DS5315Domain.domain");
		copyResourceInModelsProject("module/Default/module/DS5349_TableColumnTextFieldInConditional.module");
		
		assertTransformation(
				getResourceURI("/Default/module/DS5349_TableColumnTextFieldInConditional.module"), 
				readFileFromClasspath("/com/odcgroup/page/transformmodel/tests/widget/tabletree/ExpectedDS5349_TableColumnTextField.xml"),
				"/page[1]/comment()[1]" 
				);
	}
}
