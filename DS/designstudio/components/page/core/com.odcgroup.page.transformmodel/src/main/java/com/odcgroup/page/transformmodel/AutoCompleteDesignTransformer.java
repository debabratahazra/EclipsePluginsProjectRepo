package com.odcgroup.page.transformmodel;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;
import com.odcgroup.page.model.util.WidgetHelper;
import com.odcgroup.page.model.widgets.IAutoCompleteDesign;
import com.odcgroup.page.transformmodel.util.AttributeRenderer;

/**
 * @author pkk
 *
 */
public class AutoCompleteDesignTransformer extends BaseWidgetTransformer {
	
	private IAutoCompleteDesign  autocomp;

	/**
	 * @param type
	 */
	public AutoCompleteDesignTransformer(WidgetType type) {
		super(type);
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget)
			throws CoreException {
		autocomp = WidgetHelper.getAutoCompleteDesign(widget);
		if (autocomp == null) {
			return;
		}
		StringBuilder script = new StringBuilder();
		script.append(BEGIN_PAGE_NODE);
		renderAutoCompleteDesign(context, autocomp, script);
		script.append(END_PAGE_NODE);

		NodeList nodes = xmlToDOM(script);
		if (nodes == null) {
			return;
		}
		Document document = context.getDocument();
		document.setStrictErrorChecking(false);
		Element parent = context.getParentElement();
		for (int kx = 0; kx < nodes.getLength(); kx++) {
			Node node = document.importNode(nodes.item(kx), true);
			parent.appendChild(node);
		}
	}
	
	/**
	 * @param context
	 * @param autoomp
	 * @param script
	 */
	private void renderAutoCompleteDesign(WidgetTransformerContext context, 
			IAutoCompleteDesign autocomp, StringBuilder script) {
		script.append("<xsp:init-page>");
		if (!StringUtils.isEmpty(autocomp.getTitleAttribute())) {
			script.append("<xsp-request:set-attribute");
			renderAttribute(script, "name", "title");
			script.append(">");
			script.append(autocomp.getTitleAttribute());
			script.append("</xsp-request:set-attribute>");
		}
		if (!StringUtils.isEmpty(autocomp.getSortAttribute())) {
			script.append("<xsp-request:set-attribute");
			renderAttribute(script, "name", "Query.DSOutput.orderBy.o.attribute");
			script.append(">");
			script.append(autocomp.getSortAttribute());
			script.append("</xsp-request:set-attribute>");
		}
		if (autocomp.getMaxReturnedRows() > 0) {
			script.append("<xsp-request:set-attribute");
			renderAttribute(script, "name", "Query.maxRowCount");
			script.append(">");
			script.append(autocomp.getMaxReturnedRows());
			script.append("</xsp-request:set-attribute>");
			
		}
		if(!autocomp.getLines().isEmpty()) {
			script.append("<xsp-request:set-attribute");
			renderAttribute(script, "name", "details");
			script.append(">");
			script.append("<xsp:expr>");
			script.append("new String[][][]{");
			script.append(renderLineItems(context, autocomp.getFirstLine()));
			if (autocomp.getSecondLine() != null) {
				script.append(",");
				script.append(renderLineItems(context, autocomp.getSecondLine()));
			}
			if(autocomp.getThirdLine() != null) {
				script.append(",");
				script.append(renderLineItems(context, autocomp.getThirdLine()));
			}
			script.append("}");
			script.append("</xsp:expr>");
			script.append("</xsp-request:set-attribute>");
		}
		script.append("</xsp:init-page>");
		script.append(AJAX_INCLUDE_NODE);
	}
	
	/**
	 * @param line
	 * @return
	 */
	private String renderLineItems(WidgetTransformerContext context, ILineSnippet line) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (line.getFirstLineItem() != null) {
			renderLineItem(context, sb, line.getFirstLineItem());			
		}
		if (line.getSecondLineItem() != null) {
			sb.append(",");
			renderLineItem(context, sb, line.getSecondLineItem());
		}
		if (line.getThirdLineItem() != null) {
			sb.append(",");
			renderLineItem(context, sb, line.getThirdLineItem());
		}
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * @param sb
	 * @param item
	 */
	private void renderLineItem(WidgetTransformerContext context, StringBuilder sb, ILineItemSnippet item) {
		sb.append("{");
		if (item.isTranslation()) {
			sb.append("\"label\",");	
		} else {
			sb.append("\"\",");
		}
		sb.append("\""+item.getAttributeName()+"\",");
		if (StringUtils.isEmpty(item.getCSSClass())) {
			sb.append("\"\"");
		} else {
			sb.append("\""+item.getCSSClass()+"\"");
		}
		sb.append("}");
	}
	
	/**
	 * @param script
	 * @param name
	 * @param value
	 */
	private final void renderAttribute(StringBuilder script, String name, String value) {
		AttributeRenderer.getInstance(script).render(name, value);
	}
	
	
	/**
	 * @param builder
	 * @return NodeList
	 */
	private NodeList xmlToDOM(StringBuilder builder) {
		return xmlToDOM(builder.toString());
	}

	/**
	 * @param xmlString
	 * @return NodeList
	 */
	private NodeList xmlToDOM(String xmlString) {
		NodeList nodes = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xmlString)));
			Node root = document.getFirstChild();
			nodes = root.getChildNodes();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}

	@Override
	public Element getParentElement(WidgetTransformerContext context,
			Widget widget) {
		return context.getParentElement();
	}
	
	private static final String BEGIN_PAGE_NODE = "<xsp:page language=\"java\" "
		 + " xmlns:xsp=\"http://apache.org/xsp\" "
		 + " xmlns:xsp-request=\"http://apache.org/xsp/request/2.0\" "
		 + " xmlns:cinclude=\"http://apache.org/cocoon/include/1.0\"> ";
	
	private static final String END_PAGE_NODE = "</xsp:page>";
	
	private static final String AJAX_INCLUDE_NODE = "<cinclude:include " +
			"src=\"cocoon:/ajax-xml/ajax/search/AjaxAutoCompleteFmk\"/>";

}
