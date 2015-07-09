package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.DEFAULT_SORT_DIRECTION;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.DOMAIN_ATTRIBUTE;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.FILTER;
import static com.odcgroup.page.metamodel.PropertyTypeConstants.SORT_ASCENDING;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_COMPUTATION;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_COMPUTE_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_DUMP_MODEL;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_FILTER;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_FORMAT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_FOR_EACH_ITEM_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_FOR_EACH_ROW_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_FROM_INDEX;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_GROUP_COLUMN_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_GROUP_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_HANDLE;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_KEEP_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_KEEP_FILTER;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_LIST_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_MODEL_REF;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAME;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_PAGE;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_RECURSE_DATA_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_RENDER_LIST_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_REVERSE;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ROOT_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_SIZE;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_SORT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_SORT_COLUMN;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_STICKY;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_VIEW_REF;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_CELL_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_COLUMNS_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_COLUMN_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_LABEL_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_ROW_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_LOGIC;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetLibraryConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
/**
 * Abstract base class for all concrete WidgetTransformer.
 * 
 * @author Gary Hayes
 * @author Alexandre Jaquet
 */
public abstract class BaseWidgetTransformer implements WidgetTransformer {
	
	/** The EventTransformer. */
	private ElementEventTransformer eventTransformer;	

	/** The Widget Type. */
	private WidgetType type;

	/** The halign attribute. */
	private static final String HALIGN = "halign";
	
	/** The valign attribute. */
	private static final String VALIGN = "valign";
	
	/** The css class attribute. */
	private static final String CSS_CLASS = "class";
	
	/** The xwidth attribute. */
	private static final String X_WIDTH = "xwidth";
	/** */
	/**
	 * Gets the widget type.
	 * 
	 * @return WidgetType The widget type
	 */
	protected WidgetType getWidgetType() {
		return type;
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            The widget type
	 */
	public BaseWidgetTransformer(WidgetType type) {
		this.type = type;
	}
	
	/**
	 * Gets the EventTransformer.
	 * 
	 * @return EventTransformer
	 */
	protected ElementEventTransformer getEventTransformer() {
		if (eventTransformer == null) {
			eventTransformer = new ElementEventTransformer();
		}
		return eventTransformer;
	}	

	/**
	 * Returns true if the WidgetTransformer is designed to transform the specified WidgetType. This is handled by the
	 * GenericWidgetTransformer.
	 * 
	 * @param widget
	 *            The Widget to test
	 * @return boolean True if the WidgetTransformer is designed to transform the specified WidgetType
	 */
	public boolean isTransformer(Widget widget) {
		return getWidgetType().equals(widget.getType());
	}
	
	/**
	 * Transforms the events.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Element whose children are to be transformed
	 * @exception CoreException
	 */
	protected void transformEvents(WidgetTransformerContext context, Widget widget) throws CoreException {
		getEventTransformer().transform(context, widget);
	}	

	/**
	 * Transforms the child Widgets.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The Element whose children are to be transformed
	 * @exception CoreException
	 */
	protected void transformChildren(WidgetTransformerContext context, Widget widget) throws CoreException {
		for (Iterator it = widget.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(child);
			wt.transform(context, child);
		}
	}

	/**
	 * Creates an Element of the specified namespace and name.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param namespace
	 *            The namespace of the Element to create
	 * @param elementName
	 *            The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element createElement(WidgetTransformerContext context, String namespace, String elementName) {
		Document d = context.getDocument();
		Namespace ns = context.getTransformModel().findNamespace(namespace);
		Element e = d.createElementNS(ns.getUri(), elementName);
		e.setPrefix(ns.getPrefix());
		return e;
	}

	/**
	 * Creates an Element of the specified namespace and appends it to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param namespace
	 *            The namespace of the Element to create
	 * @param elementName
	 *            The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element appendElement(WidgetTransformerContext context, String namespace, String elementName) {
		Element e = createElement(context, namespace, elementName);
		context.getParentElement().appendChild(e);
		return e;
	}

	/**
	 * Creates an Element of the specified namespace and appends it to the specified parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param namespace
	 *            The namespace of the Element to create
	 * @param elementName
	 *            The name of the Element
	 * @return Element The newly-created Element
	 */
	protected Element appendElement(WidgetTransformerContext context, Element parent, String namespace,
			String elementName) {
		Element element = createElement(context, namespace, elementName);
		TransformUtils.appendChild(parent, element);
		return element;
	}

	/**
	 * Builds the &lt;udp:udp&gt; element along with its associated attributes: model-ref, sticky and view-ref. It
	 * appends this element to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return Element the udp element
	 */
	protected Element buildUdp(WidgetTransformerContext context, Widget widget) {
		
		Element retElem = null;
		
		String modelRefValue = widget.getPropertyValue(UDP_MODEL_REF);
		String stickyFlagValue = widget.getPropertyValue(UDP_STICKY);
		String viewReferenceValue = widget.getPropertyValue(UDP_VIEW_REF);

		if (context.isRootUDPOpen()) {
			
			retElem = appendElement(context, UDP_NAMESPACE_URI, UDP_MODEL_REF);
			Attr sticky = context.getDocument().createAttribute(UDP_STICKY);
			Attr viewReference = context.getDocument().createAttribute(UDP_VIEW_REF);
			retElem.setAttributeNode(sticky);
			retElem.setAttributeNode(viewReference);
			sticky.setValue(stickyFlagValue);
			viewReference.setValue(viewReferenceValue);
			retElem.setNodeValue(modelRefValue);
			
		} else {
		
			retElem = appendElement(context, UDP_NAMESPACE_URI, UDP_ROOT_ELEMENT);
	
			Attr modelRef = context.getDocument().createAttribute(UDP_MODEL_REF);
			Attr sticky = context.getDocument().createAttribute(UDP_STICKY);
			Attr viewReference = context.getDocument().createAttribute(UDP_VIEW_REF);
	
			modelRef.setValue(modelRefValue);
			sticky.setValue(stickyFlagValue);
			viewReference.setValue(viewReferenceValue);
	
			retElem.setAttributeNode(modelRef);
			retElem.setAttributeNode(sticky);
			retElem.setAttributeNode(viewReference);
			
		}
	
		return retElem;
	}

	/**
	 * Builds the &lt;udp:page&gt; element and appends it to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent element
	 * @param widget
	 *            The widget
	 */
	protected void buildUdpPage(WidgetTransformerContext context, Element parent, Widget widget) {
		String size = widget.getPropertyValue(PropertyTypeConstants.PAGE_SIZE);
		Element page = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_PAGE);
		Element sizeElt = appendElement(context, page, UDP_NAMESPACE_URI, UDP_SIZE);
		sizeElt.setTextContent(size);
		Element handle = appendElement(context, page, UDP_NAMESPACE_URI, UDP_HANDLE);
		handle.setTextContent("<scope:get-module-rank/>p");
	}

	/**
	 * Does our Table widget have the size property set ?
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return boolean return true if the widget table have the property size set.
	 */
	protected boolean hasPageSize(WidgetTransformerContext context, Widget widget) {
		String size = widget.getPropertyValue(PropertyTypeConstants.PAGE_SIZE);
		if (!StringUtils.isEmpty(size)) {
			return true;
		}
		return false;
	}

	/**
	 * Builds the &lt;udp:list&gt; element and appends it to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param widget
	 *            The widget
	 * @return Element the list element
	 */
	protected Element buildUdpList(WidgetTransformerContext context, Element parent, Widget widget) {
		return appendElement(context, parent, UDP_NAMESPACE_URI, UDP_LIST_ELEMENT);
	}

	/**
	 * Builds the &lt;udp:group&gt; and &lt;udp:group-column&gt; elements and appends it to the parent Element. A group
	 * is created for each child Widget having the grouping property which is true. NOTE that if no groups exist then
	 * this method returns null.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param headers
	 *            The Column headers
	 */
	protected void buildUdpGroups(WidgetTransformerContext context, Element parent, List<Widget> headers) {		
		List<Widget> groups = new ArrayList<Widget>();
		for (Widget h : headers) {
			String v = h.getPropertyValue(PropertyTypeConstants.GROUP_SORT_RANK);
			if (StringUtils.isEmpty(v)) {
				continue;
			}
			int r = Integer.parseInt(v);
			if (r > 0) {
				groups.add(h);
			}
		}
		
		if (groups.size() == 0) {
			return;
		}
		
		List<Widget> sortedGroup = sortColumnsHeaderByRank(groups, PropertyTypeConstants.GROUP_SORT_RANK);
		Collections.reverse(sortedGroup);

		for (Widget w : sortedGroup) {
			Element group = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_GROUP_ELEMENT);
			String name = w.getPropertyValue(PropertyTypeConstants.COLUMN_NAME);
			Element groupColumn = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_GROUP_COLUMN_ELEMENT);
			groupColumn.setTextContent(name);
			group.appendChild(groupColumn);
			Element sortColumn = appendElement(context, group,UDP_NAMESPACE_URI,UDP_SORT_COLUMN);
			String sortColumnValue = w.getPropertyValue(PropertyTypeConstants.GROUP_SORT_COLUMN);
			if (StringUtils.isEmpty(sortColumnValue)) {
				// If the Group Sort Column is empty use the Column Name
				sortColumnValue = name;
			}
			sortColumn.setTextContent(sortColumnValue);			
		}
	}

	/**
	 * Builds the &lt;udp:render-list&gt; element and appends it to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param widget
	 *            The widget
	 * @return Element the render-list element
	 */
	protected Element buildUdpRenderList(WidgetTransformerContext context, Element parent, Widget widget) {
		Element renderList = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_RENDER_LIST_ELEMENT);
		Element logic = appendElement(context, renderList, XSP_NAMESPACE_URI, XSP_LOGIC);
		StringBuilder condition = new StringBuilder(256);
		condition.append("if(<udp:row-count/> == 0) {\n");
		condition.append("<xsp:content>\n");
		condition.append("<xgui:vbox>\n");
		condition.append("<xgui:label>\n");
		condition.append("<xgui:text><i18n:text>general.empty_result</i18n:text></xgui:text>");
		condition.append("</xgui:label>\n");
		condition.append("</xgui:vbox>\n");
		condition.append("<xgui:table id=\"");
		condition.append(widget.getID());
		condition.append("\"/>\n");
		condition.append("</xsp:content>");
		condition.append("}\n");
		logic.setTextContent(condition.toString());
		return renderList;

	
	}

	/**
	 * Builds the &lt;udp:recurse-data&gt; element and appends it to the parent Element.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param widget
	 *            The widget
	 * @return Element the recurse-data element
	 */
	protected Element buildUdpRecurseData(WidgetTransformerContext context, Element parent, Widget widget) {
		return appendElement(context, parent, UDP_NAMESPACE_URI, UDP_RECURSE_DATA_ELEMENT);
	}

	/**
	 * Builds the <udp-keep> or <udp:compute> columns section. The widget is typically a Table or a Tree. The children of this Widget
	 * (Column) are searched and a &lt;udp:keep&gt; element is generated for each ColumnHeader that is found. The
	 * caption is used for the text content of the Element
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param widget
	 *            The widget
	 * @param columns
	 *            The columns to keep
	 */
	protected void buildUdpKeepOrComputeElements(WidgetTransformerContext context, Element parent, Widget widget,
			List<Widget> columns) {
		for (Widget header : columns) {	
			String name = header.getPropertyValue(PropertyTypeConstants.COLUMN_NAME);
			//String asType = header.getPropertyValue(PropertyTypeConstants.AS_TYPE);
			String option = header.getPropertyValue(PropertyTypeConstants.OPTION);
			String format = header.getPropertyValue(PropertyTypeConstants.FORMAT);
			String compute = header.getPropertyValue(PropertyTypeConstants.COMPUTE);
			String computation = header.getPropertyValue(PropertyTypeConstants.COMPUTATION);

			if (StringUtils.isEmpty(computation)) {
				Element udpKeep = createElement(context, UDP_NAMESPACE_URI, UDP_KEEP_ELEMENT);
				udpKeep.setTextContent(name);
				//addNonEmptyAttribute(context, udpKeep, UDP_AS_TYPE, asType);
				addNonEmptyAttribute(context, udpKeep, UDP_FORMAT, format);
				TransformUtils.appendChild(parent, udpKeep);
			} else {
				Element udpCompute = createElement(context, UDP_NAMESPACE_URI, UDP_COMPUTE_ELEMENT);
				udpCompute.setTextContent(compute);
				addNonEmptyAttribute(context, udpCompute, UDP_NAME, name);
				//addNonEmptyAttribute(context, udpCompute, UDP_TYPE, asType);
				addNonEmptyAttribute(context, udpCompute, UDP_COMPUTATION, computation);
				TransformUtils.appendChild(parent, udpCompute);
			}	
		}
	}

	/**
	 * Add an attribute to the specified Element but only if it non-empty.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param element
	 *            The element to add an attribute
	 * @param attribute
	 *            The attribute to add
	 * @param value
	 *            The value to append to the attribute
	 * 
	 */
	protected void addNonEmptyAttribute(WidgetTransformerContext context, Element element, String attribute, String value) {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		addAttribute(context, element, attribute, value);
	}
	
	/**
	 * Add an attribute to the specified widget.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param element
	 *            The element to add an attribute
	 * @param attribute
	 *            The attribute to add
	 * @param value
	 *            The value to append to the attribute
	 * 
	 */
	protected void addAttribute(WidgetTransformerContext context, Element element, String attribute, String value) {
		Attr attr = context.getDocument().createAttribute(attribute);
		attr.setTextContent(value);
		element.setAttributeNode(attr);
	}

	/**
	 * Builds the &lt;udp:columns&gt; section.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param visibleColumnHeaders
	 *            The visible Column Headers
	 * @param indexOfFirstVisibleColumn
	 *            The index of the first visible Column
	 * @exception CoreException 
	 */
	protected void buildXGuiColumns(WidgetTransformerContext context, List<Widget> visibleColumnHeaders,
			int indexOfFirstVisibleColumn) throws CoreException {

		// Build the xgui:columns section
		Element columns = appendElement(context, XGUI_NAMESPACE_URI, XGUI_COLUMNS_ELEMENT);

		Element oldParent = context.setParentElement(columns);
		int index = indexOfFirstVisibleColumn;
		for (Widget w : visibleColumnHeaders) {
			buildXGuiColumn(context, w, index);
			++index;
		}
		context.setParentElement(oldParent);
	}
	
	/**
	 * Returns true if the Column Headers should be shown.
	 * 
	 * @param widget The Table (Tree...)
	 * @return boolean True if the Column Headers should be shown
	 */
	protected boolean showColumnHeaders(Widget widget) {
		String s = widget.getPropertyValue(PropertyTypeConstants.SHOW_COLUMN_HEADER);
		if ("true".equals(s)) {
			return true;
		}
		return false;
	}

	/**
	 * Builds the &lt;xgui:column&gt; section.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param header
	 *            The Column Header. This is assumed to be a visible Column
	 * @param index
	 *            The current index
	 * @exception CoreException
	 */
	private void buildXGuiColumn(WidgetTransformerContext context, Widget header, int index) throws CoreException {
		Element column = appendElement(context, XGUI_NAMESPACE_URI, XGUI_COLUMN_ELEMENT);
		Element oldParent = context.setParentElement(column);
		
		buildsXguiColumnAttributes(context, column, header);
		
		transformTranslations(context, header);
		
		buildXGuiColumnChildren(context, header);

		buildLogicSortColumn(context, header, index);

		context.setParentElement(oldParent);
	}
	
	/**
	 * Builds the Column attributes.
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param column
	 * 			The current column
	 * @param header
	 * 			The current header
	 */
	protected void buildsXguiColumnAttributes(WidgetTransformerContext context, Element column, Widget header) {
		//Extract the ColumnBody of the parent
		Widget columnBody = null;
		for(Widget w : header.getParent().getContents()) {
			if(w.getTypeName().equals(WidgetTypeConstants.COLUMN_BODY)) {
				columnBody = w;
			}
		}
		if (columnBody == null) {
			throw new PageException("ColumnBody widget not found for "
					+header.getPropertyValue(PropertyTypeConstants.COLUMN_NAME));
		}
		String type = header.getPropertyValue(PropertyTypeConstants.TYPE);		
		String halign = columnBody.getPropertyValue(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
		String valign = columnBody.getPropertyValue(PropertyTypeConstants.VERTICAL_ALIGNMENT);
		String cssClass = header.getPropertyValue(PropertyTypeConstants.CSS_CLASS);
		String action = header.getPropertyValue(PropertyTypeConstants.ACTION);
		String enabled = header.getPropertyValue(PropertyTypeConstants.ENABLED);
		String nowrap = header.getPropertyValue(PropertyTypeConstants.NO_WRAP);
		String locked = header.getPropertyValue(PropertyTypeConstants.LOCKED);
		String maxChar = header.getPropertyValue(PropertyTypeConstants.MAX_CHARACTERS);
		String icon = header.getPropertyValue(PropertyTypeConstants.ICON);
		String hTextPosition = columnBody.getPropertyValue(PropertyTypeConstants.HORIZONTAL_TEXT_POSITION);
		String vTextPosition = columnBody.getPropertyValue(PropertyTypeConstants.VERTICAL_TEXT_POSITION);
		String width = header.getPropertyValue(PropertyTypeConstants.WIDTH);
		String xWidth = header.getPropertyValue(PropertyTypeConstants.X_WIDTH);
		
		addNonEmptyAttribute(context, column, HALIGN, halign);
		addNonEmptyAttribute(context, column, PropertyTypeConstants.HORIZONTAL_TEXT_POSITION, hTextPosition);
		addNonEmptyAttribute(context, column, PropertyTypeConstants.VERTICAL_TEXT_POSITION, vTextPosition);
		addNonEmptyAttribute(context, column, PropertyTypeConstants.ICON,icon);
		if (!StringUtils.isEmpty(maxChar) && !maxChar.equals("0")){
		    addNonEmptyAttribute(context, column, XGuiConstants.XGUI_MAX_CHAR, maxChar);
		}
		addNonEmptyAttribute(context, column, PropertyTypeConstants.ENABLED, enabled);
		addNonEmptyAttribute(context, column, PropertyTypeConstants.LOCKED, locked);
		addNonEmptyAttribute(context, column, PropertyTypeConstants.ACTION, action);
		addNonEmptyAttribute(context, column, VALIGN, valign);
		addNonEmptyAttribute(context, column, "id", header.getID());
		addNonEmptyAttribute(context, column, PropertyTypeConstants.TYPE, type);
		addNonEmptyAttribute(context, column, CSS_CLASS, cssClass);
		if (!StringUtils.isEmpty(width) && !width.equals("0")){
			addNonEmptyAttribute(context, column, PropertyTypeConstants.WIDTH, width);
		}
		addNonEmptyAttribute(context, column, X_WIDTH, xWidth);
		addNonEmptyAttribute(context, column, PropertyTypeConstants.NO_WRAP, nowrap);
	}
	
	/**
	 * Builds the &lt;logic&gt; section who contain sorter.
	 * 
	 * @param context
	 *            The widget transformer context
	 * @param header
	 *            The header Widget
	 * @param index
	 *            The current index
	 */
	private void buildLogicSortColumn(WidgetTransformerContext context, Widget header, int index) {
		String s = header.getPropertyValue(PropertyTypeConstants.IS_SORTABLE);
		if (!"true".equals(s)) {
			return;
		}

		Element xspLogic = appendElement(context, XSP_NAMESPACE_URI,
				TransformerConstants.LOGIC_ELEMENT_NAME);

		// The model ref value is contained in the Table. This is the grand parent of the ColumnHeader
		String modelRefValue = header.getParent().getParent().getPropertyValue(UDP_MODEL_REF);
		StringBuilder content = new StringBuilder(1024);
		
		String columnName = header.getPropertyValue(PropertyTypeConstants.COLUMN_NAME);

		content.append("\nif (<udp:sorting-column/> == " + index + ") {\n");
		content.append("  if (<udp:is-sort-reversed/>) {\n");
		content.append("    <xsp:attribute name=\"sorting\">descending</xsp:attribute>\n");
		content
				.append("    <xsp:attribute name=\"action\">post('<pageflow:continuation/>','','','flow-action=reload,<scope:get-module-rank/>s=" + columnName + ","
						+ modelRefValue
						+ "=<bean:value-of key=\""
						+ modelRefValue
						+ "\"/>,scope-id=<scope:get-id/>')</xsp:attribute>\n");
		content.append("} else {\n");
		content.append("    <xsp:attribute name=\"sorting\">ascending</xsp:attribute>\n");
		content
				.append("    <xsp:attribute name=\"action\">post('<pageflow:continuation/>','','','flow-action=reload,<scope:get-module-rank/>s=-" + columnName + ","
						+ modelRefValue
						+ "=<bean:value-of key=\""
						+ modelRefValue
						+ "\"/>,scope-id=<scope:get-id/>')</xsp:attribute>\n");
		content.append("  }\n");
		content.append("} else {\n");
		content.append("    <xsp:attribute name=\"sorting\">none</xsp:attribute>\n");
		content
				.append("    <xsp:attribute name=\"action\">post('<pageflow:continuation/>','','','flow-action=reload,<scope:get-module-rank/>s=" + columnName + ","
						+ modelRefValue
						+ "=<bean:value-of key=\""
						+ modelRefValue
						+ "\"/>,scope-id=<scope:get-id/>')</xsp:attribute>\n");
		content.append("}");
		xspLogic.setTextContent(content.toString());
	}

	/**
	 * Builds the &lt;udp:for-each-item&gt; section.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent Element
	 * @param widget
	 *            The Widget to transform
	 * @param indexOfFirstVisibleColumn
	 *            The index of the first visible column
	 * @exception CoreException
	 */
	protected void buildUdpForEachItem(WidgetTransformerContext context, Element parent, Widget widget,
			int indexOfFirstVisibleColumn) throws CoreException {
		Element forEachItem = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_FOR_EACH_ITEM_ELEMENT);
		int index = indexOfFirstVisibleColumn;
		addAttribute(context, forEachItem, UDP_FROM_INDEX, String.valueOf(index));
		List<Widget> bodys = getVisibleColumnBodies(context, widget);
		Element cell = appendElement(context, forEachItem, XGUI_NAMESPACE_URI, XGUI_CELL_ELEMENT);
		boolean first = true;
		boolean last = false;
		for (Widget body : bodys) {
			if (bodys.indexOf(body) == bodys.size() - 1) {
				last = true;
			}
			buildXspLogicSections(context, cell, body, first, last, index);
			first = false;
			index++;
		}
	}

	/**
	 * Build the default cell section for empty ColumnBodys.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 * 
	 */
	protected void buildDefaultCell(WidgetTransformerContext context, Widget widget, Element parent) {
		Element label = appendElement(context, parent, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
		Element text = appendElement(context, label, XGUI_NAMESPACE_URI, XGUI_TEXT_ELEMENT);
		appendElement(context, text, UDP_NAMESPACE_URI, UDP_ITEM_ELEMENT);
	}

	/**
	 * Get the index of the first visible column.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param sortedColumnsHeaders 
	 * 			The list of the sorted ColumnHeader
	 * @return int The index of the first visible ColumnHeader
	 */
	protected int getIndexOfFirstVisibleColumns(WidgetTransformerContext context, List<Widget> sortedColumnsHeaders) {
		int index = 0;
		for (Widget header : sortedColumnsHeaders) {
			if (header.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("false")) {
				index++;
			}
		}
		return index;
	}

	/**
	 * Gets a list of ColumnBody where the visible property of the ColumnHeader associated within the Column is set to
	 * true.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return List<Widget> The list of the ColumnBody
	 */
	protected List<Widget> getVisibleColumnBodies(WidgetTransformerContext context, Widget widget) {
		List<Widget> result = new ArrayList<Widget>();
		List<Widget> columns = getColumns(context, widget);
		for (int i = 0; i < columns.size(); i++) {
			List<Widget> columnChildren = columns.get(i).getContents();
			for (int j = 0; j < columnChildren.size() - 1; j++) {
				Widget header = columnChildren.get(j);
				Widget body = columnChildren.get(j + 1);
				if (header.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("true")) {
					result.add(body);
				}
			}
		}
		return result;
	}

	/**
	 * Builds the &lt;xsp:logic&gt; sections inside the &lt;udp:for-each-item&gt; section..
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param parent
	 *            The parent element
	 * @param widget
	 *            The widget to transform
	 * @param first
	 *            Does the widget is first ?
	 * @param last
	 *            Does the widget is last ?
	 * @param index
	 *            The index of the ColumnBody
	 * @exception CoreException
	 */
	protected void buildXspLogicSections(WidgetTransformerContext context, Element parent, Widget widget,
			boolean first, boolean last, int index) throws CoreException {
		Element openLogicE = appendElement(context, parent, XSP_NAMESPACE_URI,
				TransformerConstants.LOGIC_ELEMENT_NAME);
		if (first) {

			openLogicE.setTextContent("if (<udp:item-index/> == " + index + ") {");
		} else {
			openLogicE.setTextContent("} else if (<udp:item-index/> == " + index + ") {");
		}
		Element oldParent = context.setParentElement(parent);
		if (widget.getContents().size() > 0) {
			transformColumnBodyChildren(context, widget);
			context.setParentElement(oldParent);
		} else {
			buildDefaultCell(context, widget, parent);
		}
		if (last) {
			Element closeLogicE = appendElement(context, parent, XSP_NAMESPACE_URI,
					TransformerConstants.LOGIC_ELEMENT_NAME);
			closeLogicE.setTextContent("}");
		}
	}

	/**
	 * Transform the ColumnBody children.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget to transform
	 * @exception CoreException
	 */
	protected void transformColumnBodyChildren(WidgetTransformerContext context, Widget widget) throws CoreException {
		for (Iterator it = widget.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(child);
			wt.transform(context, child);
		}
	}

	/**
	 * Gets the ColumnHeaders of the Widget. The Widget structure is assumed to be as follows:
	 * 
	 * MainWidget -> Column -> Column Header
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The main Widget being transformed
	 * @return List of ColumnHeader's
	 */
	protected List<Widget> getColumnHeaders(WidgetTransformerContext context, Widget widget) {
		// First find the Columns
		WidgetType cwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.COLUMN);
		List<Widget> columns = WidgetUtils.filter(widget.getContents(), cwt);
		List allChildren = WidgetUtils.getChildren(columns);

		WidgetType chwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI,
				WidgetTypeConstants.COLUMN_HEADER);
		List<Widget> headers = WidgetUtils.filter(allChildren, chwt);
		return headers;
	}

	/**
	 * Gets the Columns of the Widget.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return List<Widget> The list of the Columns
	 */
	protected List<Widget> getColumns(WidgetTransformerContext context, Widget widget) {
		WidgetType cwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.COLUMN);
		List<Widget> columns = WidgetUtils.filter(widget.getContents(), cwt);
		return columns;
	}

	/**
	 * Gets the ColumnBodys of the Widget. The Widget structure is assumed to be as follows:
	 * 
	 * MainWidget -> Column -> Column Body
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The main Widget being transformed
	 * @return List of ColumnHeader's
	 */
	protected List<Widget> getColumnBodys(WidgetTransformerContext context, Widget widget) {
		// First find the Columns
		WidgetType cwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI, WidgetTypeConstants.COLUMN);
		List<Widget> columns = WidgetUtils.filter(widget.getContents(), cwt);
		List allChildren = WidgetUtils.getChildren(columns);

		WidgetType chwt = context.getMetaModel().findWidgetType(WidgetLibraryConstants.XGUI,
				WidgetTypeConstants.COLUMN_BODY);
		List<Widget> headers = WidgetUtils.filter(allChildren, chwt);
		return headers;
	}

	/**
	 * Builds the &lt;udp:sort&gt; section.
	 * 
	 * @param context
	 *            The widget transformer context
	 * @param widget
	 *            The widget to transform
	 * @param parent
	 *            The parent element
	 * @param headers
	 * 			  The list of ColumnHeader's            
	 */
	protected void buildUdpSort(WidgetTransformerContext context, Widget widget, Element parent, List<Widget> headers) {
		// First gets the sorted ColumnHeader by rank
		List<Widget> sortedColumnsHeader = sortColumnsHeaderByRank(headers,PropertyTypeConstants.DEFAULT_SORT_RANK);
		
		List<Element> udpColumnList = new ArrayList<Element>(); 
		
		boolean sortHandleAdded = false;
		boolean atLeastOneColumnIsSortable = false;
		for (Widget header : sortedColumnsHeader) {
			if (header.getPropertyValue(PropertyTypeConstants.IS_SORTABLE).equals("false")) {
				continue;
			}
			
			atLeastOneColumnIsSortable = true;
			
			// Only sort columns which have a rank
			String s = header.getPropertyValue(PropertyTypeConstants.DEFAULT_SORT_RANK);
			if (StringUtils.isEmpty(s)) {
				continue;
			}
			
			int rank = Integer.parseInt(s);
			if (rank <= 0) {
				continue;
			}
				
			String sortColumn = header.getPropertyValue(PropertyTypeConstants.COLUMN_NAME);
			if (StringUtils.isEmpty(sortColumn)) {
				continue;
			}
		
			String reverse = header.getPropertyValue(DEFAULT_SORT_DIRECTION);
			if (reverse.equals(SORT_ASCENDING)) {
				reverse = "false";
			} else {
				reverse = "true";
			}
			udpColumnList.add(buildUdpSortColumn(context, sortColumn, reverse));
		}
		
		if (atLeastOneColumnIsSortable && ! sortHandleAdded) {
	        // Always define a sort handle otherwise none of the columns are sortable
		    Element sort = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_SORT);
		    buildUdpSortHandle(context, sort);
		    for (Element udpColumn : udpColumnList) {
				TransformUtils.appendChild(sort, udpColumn);
		    }
		}
	}
	/**
	 * Sort a collection of ColumnHeader's by rank. 
	 * 
	 * @param list
	 * 			The list of widget
	 * @param property
	 * 			The property type used to retrieve the rank value 
	 * @return List return a sorted list of passed objects
	 */
	protected List<Widget> sortColumnsHeaderByRank(List<Widget> list, String property) {
		final String propertyType = property;
		if (list.size() > 0) {
			Collections.sort(list, new Comparator<Widget>() {	        
		        public int compare(Widget w1, Widget w2) {
		        	return w1.getPropertyValue(propertyType).compareTo(w2.getPropertyValue(propertyType));
		        }
		    });
		}
		return list;
	}
	/**
	 * Builds the &lt;udp:filter&gt; section.
	 * 
	 * @param context
	 *            The widget transformer context
	 * @param widget
	 *            The widget to transform
	 * @param parent
	 *            The parent element
	 */
	protected void buildUdpFilter(WidgetTransformerContext context, Widget widget, Element parent) {
		Element filter = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_FILTER);
		String filterCode = widget.getPropertyValue(FILTER);
		filter.setTextContent(filterCode);
	}

	/**
	 * Builds the &lt;udp:keep-filter&gt; section.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 */
	protected void buildUdpKeepFilter(WidgetTransformerContext context, Widget widget, Element parent) {
		Element keepFilter = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_KEEP_FILTER);
		String filterCode = widget.getPropertyValue(PropertyTypeConstants.KEEP_FILTER);
		keepFilter.setTextContent(filterCode);
	}


	/**
	 * Does our ColumnHeaders have a filter property set.
	 * 
	 * @param context
	 *            The widget transformer context
	 * @param widget
	 *            The widget to transform
	 * @return boolean return true if a ColumnHeaders have a filter setted
	 */
	protected boolean hasFilter(WidgetTransformerContext context, Widget widget) {
		String filter = widget.getPropertyValue(FILTER);
		if (!StringUtils.isEmpty(filter)) {
			return true;
		}
		return false;
	}

	/**
	 * Builds the &lt;udp:handle&gt; section.
	 * 
	 * @param context
	 *            The widget transformer context
	 * @param parent
	 *            The parent element
	 */
	private void buildUdpSortHandle(WidgetTransformerContext context, Element parent) {
		Element handle = appendElement(context, parent, UDP_NAMESPACE_URI, UDP_HANDLE);
		handle.setTextContent("<scope:get-module-rank/>s");
	}

	/**
	 * Builds the &lt;udp:sort-column&gt; section.
	 * 
	 * @param context
	 *            The widget transformer context
	 * @param caption
	 *            The caption of the ColumnHeader
	 * @param reverse
	 *            The reverse attribute of the ColumnHeader
	 * @return Element the new UDP column
	 */
	protected Element buildUdpSortColumn(WidgetTransformerContext context, String caption, String reverse) {
		Element sortColumn = createElement(context, UDP_NAMESPACE_URI, UDP_SORT_COLUMN);
		addAttribute(context, sortColumn, UDP_REVERSE, reverse);
		sortColumn.setTextContent(caption);
		return sortColumn;
	}

	/**
	 * Builds the footer section.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 * 
	 */
	protected void buildTableFooter(WidgetTransformerContext context, Widget widget, Element parent) {
		TableFooterTransformer tableFooterTransformer = new TableFooterTransformer(widget.getType());
		tableFooterTransformer.buildXguiHbox(context, widget, parent);
	}
	
	/**
	 * Builds the &lt;udp:dump-model&gt; element.
	 * @param context
	 *            The WidgetTransformerContext
	 * @param table
	 *            The Table
	 * @param parent The parent Element
	 */
	protected void buildUdpDumpModel(WidgetTransformerContext context, Widget table, Element parent) {
		String value = table.getPropertyValue(PropertyTypeConstants.DUMP_MODEL);
		if ("true".equals(value)) {
			appendElement(context, parent, UDP_NAMESPACE_URI, UDP_DUMP_MODEL);		
		}	
	}

	/**
	 * Builds the table-model attributes.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param parent
	 *            The parent element
	 */
	protected void buildTableModelAttributes(WidgetTransformerContext context, Widget widget, Element parent) {
		String datasKey = widget.getPropertyValue(PropertyTypeConstants.DATAS_KEY);
		String datasProperty = widget.getPropertyValue(PropertyTypeConstants.DATAS_PROPERTY);
		String tableKey = widget.getPropertyValue(PropertyTypeConstants.TABLE_KEY);
		addAttribute(context, parent, PropertyTypeConstants.DATAS_KEY, datasKey);
		addAttribute(context, parent, PropertyTypeConstants.DATAS_PROPERTY, datasProperty);
		addAttribute(context, parent, PropertyTypeConstants.TABLE_KEY, tableKey);
	}

	/**
	 * Gets the list of the visible Columns.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param columns
	 *            The list of the columns
	 * @return List<Widget> The list of the visible columns
	 */
	protected List<Widget> getVisibleColumns(WidgetTransformerContext context, Widget widget, List<Widget> columns) {
		List<Widget> result = new ArrayList<Widget>();
		for (int i = 0; i < columns.size(); i++) {
			Widget column = columns.get(i);
			List<Widget> columnChildren = columns.get(i).getContents();
			for (int j = 0; j < columnChildren.size() - 1; j++) {
				Widget header = columnChildren.get(j);
				if (header.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("true")) {
					result.add(column);
				}
			}
		}
		return result;
	}

	/**
	 * This is because in the UDP code (in the for-each-column and for-each- )
	 * Gets the list of the sorted Columns. The non-visible columns are added first. This is because in the UDP code (in
	 * the for-each-column and for-each-item) we can specify a from-index and ignore the non-visible columns.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param columns
	 *            The columns to sort
	 * @return List The of sorted columns headers
	 */
	protected List<Widget> getSortedColumnHeaders(WidgetTransformerContext context, Widget widget, List<Widget> columns) {
		List<Widget> result = new ArrayList<Widget>();
		for (Widget w : columns) {
			if (w.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("false")) {
				result.add(w);
			}
		}
		for (Widget w : columns) {
			if (w.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("true")) {
				result.add(w);
			}
		}
		return result;
	}

	/**
	 * Gets the list of the visible Column Headers.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @param columns
	 *            The columns to sort
	 * @return List The of visible columns headers
	 */
	protected List<Widget> getVisibleColumnHeaders(WidgetTransformerContext context, Widget widget, List<Widget> columns) {
		List<Widget> result = new ArrayList<Widget>();
		for (Widget w : columns) {
			if (w.getPropertyValue(PropertyTypeConstants.VISIBLE).equals("true")) {
				result.add(w);
			}
		}
		return result;
	}

	/**
	 * Does our Table contain a &lt;keep-filter&gt; section ?
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param widget
	 *            The widget
	 * @return boolean return true if our Table contain a keep-filter section
	 */
	protected boolean hasUdpKeepFilter(WidgetTransformerContext context, Widget widget) {
		String filter = widget.getPropertyValue(PropertyTypeConstants.KEEP_FILTER);
		if (!StringUtils.isEmpty(filter)) {
			return true;
		}
		return false;
	}

	
	/**
	 * Builds the children of the Column Header.
	 * 
	 * @param context
	 *            The WidgetTransformerContext
	 * @param header
	 *            The ColumnHeader
	 * @exception CoreException
	 */
	private void buildXGuiColumnChildren(WidgetTransformerContext context, Widget header) throws CoreException {
		transformChildren(context, header);		
	}
		
	/**
	 * Transforms the Properties of the Widget.
	 *
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget whose properties are to be transformed
	 * @exception CoreException
	 */
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {
		for (Property p : widget.getProperties()) {
			PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(p);
			pt.transform(context, p);			
		}		
	}
	
	/**
	 * @param context
	 * @param widget
	 * @throws CoreException
	 */
	protected void transformTranslations(WidgetTransformerContext context, Widget widget) throws CoreException  {
		ITranslation translation = context.getTranslation(widget);
		if (translation != null) {
			for (ITranslationKind kind : translation.getAllKinds()) {
				TransformUtils.transformTranslation(context, translation, kind);
			}
		}
	}
	
	/**
	 * Builds the &lt;udp:for-each-row&gt; section.
	 * 
	 * @param context The WidgetTransformerContext
	 * @param widget The Widget to transform
	 * @param indexOfFirstVisibleColumn The index of the first visible column
	 * @exception CoreException
	 */
	protected void buildUdpRows(WidgetTransformerContext context, Widget widget, int indexOfFirstVisibleColumn) throws CoreException {
		// <udp:for-each-row>
		Element forEachRow = appendElement(context, UDP_NAMESPACE_URI, UDP_FOR_EACH_ROW_ELEMENT);
		Element row = appendElement(context, forEachRow, XGUI_NAMESPACE_URI, XGUI_ROW_ELEMENT);		
		
		buildUdpForEachItem(context, row, widget, indexOfFirstVisibleColumn);		
	}
	
	/**
	 * Builds the xsp logic tag for the control of not empty result.
	 * 
	 * @param context
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 */
	protected void buildLogicNotEmpty(WidgetTransformerContext context, Widget widget) {
		Element xspLogic = appendElement(context, XSP_NAMESPACE_URI, XSP_LOGIC);
		xspLogic.setTextContent("if(<udp:row-count/> &gt; 0) { ");
	}
	
	/**
	 * Close the xsp logic for the control of the not empty result.
	 * 
	 * @param context 
	 * 			The WidgetTransformerContext
	 * @param widget
	 * 			The widget
	 * @param parent
	 */
	protected void buildLogicNotEmptyClosingTag(WidgetTransformerContext context, Widget widget,Element parent) {
		Element xspLogic = appendElement(context,parent,XSP_NAMESPACE_URI, XSP_LOGIC);
		xspLogic.setTextContent("}");	
	}	
	
    /**
     * Finds the first property with the given name which is not empty. This looks for the property in the parent
     * Widgets.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to look for the property for
     * @param propertyName The property name to look for
     * @return Property The property or null if no property could be found
     */
    private Property findNonNullProperty(WidgetTransformerContext context, Widget widget, String propertyName) {
        Widget w = widget;
        List<Widget> parentWidgets = context.getParentWidgets();
        int parentIndex = parentWidgets.size() - 1;

        while (w != null) {
            Property p = w.findProperty(propertyName);
            if (p != null && StringUtils.isNotEmpty(p.getValue())) {
                return p;
            }

            w = w.getParent();
            if (w == null && parentIndex >= 0) {
                w = parentWidgets.get(parentIndex);
                parentIndex -= 1;
            }
        }

        return null;
    }	
    /**
     * Finds the first DomainEntity property which is not empty. This looks for the BeanName in the parent Widgets.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to look for the BeanName for
     * @return Property The BeanName or null if no BeanName could be found
     */
    protected Property findDomainEntity(WidgetTransformerContext context, Widget widget) {
        return findNonNullProperty(context, widget, PropertyTypeConstants.DOMAIN_ENTITY);
    }
    
    /**
     * Gets the Domain Repository.
     * 
     * @param context The WidgetTransformerContext
     * @return DomainRepository The Domain Repository
     */
    protected DomainRepository getDomainRepository(WidgetTransformerContext context) {
    	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(context.getProject());
        return DomainRepository.getInstance(ofsProject);
    }
    
    /**
     * @param w a widget
     * @return the value of the property {@code DOMAIN_ATTRIBUTE}
     */
    protected String getPropertyDomainAttributeValue(Widget w) {
    	String value = "";
    	Property p = w.findProperty(DOMAIN_ATTRIBUTE);
    	if (p != null) {
    		value = p.getValue();
    		if (value == null) {
    			value = "";
    		}
    	}
    	return value;
    }
    
    /**
     * Gets the MdfModelElement associated with the widget.
     * 
     * @param context The WidgetTransformerContext
     * @param attribute The Attribute Widget
     * @return MdfModelElement The MdfModelElement
     */
    protected MdfModelElement getMdfModelElement(WidgetTransformerContext context, Widget attribute) {     	
    
    	MdfModelElement result = null;
    	
    	MdfDataset dataset = null;
    	
    	String ectx = null;
    	String actx = null;
    	if (context.isEditableTableTree()) {
    		actx = context.getEditableDatasetAttribute();
			ectx = context.getEditableDataset();
			String assoc = context.getEditableDatasetAssociation();
    		if (StringUtils.isNotBlank(assoc)) {
    			if (StringUtils.isNotBlank(ectx)) {
    				// extract association.
    				// load the editable dataset.
    		    	DomainRepository repository = getDomainRepository(context);
    		    	MdfDataset ds = repository.getDataset(MdfNameFactory.createMdfName(ectx));
    		    	if (ds != null) {
    		    		MdfDatasetProperty dp = ds.getProperty(assoc);
    		    		if (dp instanceof MdfDatasetMappedProperty) {
    		    			MdfDatasetMappedProperty dmp = (MdfDatasetMappedProperty) dp;
    		    			if (dmp.isTypeDataset()) {
    		    				dataset = ((MdfDatasetMappedPropertyImpl) dmp).getLinkDataset();
    		    				ectx = dataset.getQualifiedName().toString();
    		    			}
    		    		}
    		    	}
    			}
    		}
    	} else {
    		actx = getPropertyDomainAttributeValue(attribute);
    		Property dep = findDomainEntity(context, attribute);
    		if (dep != null) {
    			ectx = dep.getValue();
    		}
    	}

    	
    	if (StringUtils.isBlank(actx) || StringUtils.isBlank(ectx)) { 
    		return null;
    	}

    	if (dataset == null)  {
        	DomainRepository repository = getDomainRepository(context);
    		dataset = repository.getDataset(MdfNameFactory.createMdfName(ectx));
    	}
    	if (dataset != null) {
    		result = dataset.getProperty(actx);
    	}
        	
        return result;
    }
    
    /**
     * Gets the type of the MdfModelElement. This may be an MdfAttribute or an MdfDatasetProperty.
     * 
     * @param mdfe The MdfModelElement
     * @return MdfEntity The type
     */
    protected MdfEntity getMdfType(MdfModelElement mdfe) {
        if (mdfe instanceof MdfAttribute) {
            MdfAttribute a = (MdfAttribute) mdfe;
            return a.getType();
        } else if (mdfe instanceof MdfDatasetProperty) {
            MdfDatasetProperty p = (MdfDatasetProperty) mdfe;
            return p.getType();
        }

        return null;
    }
    
    /**
	 * @param context
	 * @param translationKey
	 * @param kind
	 */
	protected void transformTranslation(WidgetTransformerContext context, ITranslation translation, String translationKey, ITranslationKind kind) {		
		if(!translation.messagesFound(kind)) {
			return;
		}
		if (!StringUtils.isEmpty(translationKey)) {
			String transElemName = null;
			if (ITranslationKind.NAME.equals(kind)) {
				transElemName = XGuiConstants.XGUI_TEXT_ELEMENT;
			} else if (ITranslationKind.TEXT.equals(kind)) {
				transElemName = XGuiConstants.XGUI_TOOLTIP_ELEMENT;
			}
			Element transElem = createElement(context, XGuiConstants.XGUI_NAMESPACE_URI, transElemName);
			context.getParentElement().appendChild(transElem);
			Element i18nElem = createElement(context, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
			transElem.appendChild(i18nElem);
			i18nElem.setTextContent(translationKey);
		}
	}    
	
	
}