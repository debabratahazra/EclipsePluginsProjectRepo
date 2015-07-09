package com.odcgroup.page.transformmodel.table;

import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_COLUMN;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_ITEM_ELEMENT;
import static com.odcgroup.page.transformmodel.UdpConstants.UDP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_LABEL_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.transformmodel.BaseWidgetTransformer;
import com.odcgroup.page.transformmodel.PropertyTransformer;
import com.odcgroup.page.transformmodel.UdpConstants;
import com.odcgroup.page.transformmodel.WidgetTransformerContext;
import com.odcgroup.page.transformmodel.XSPConstants;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * @author phanikumark
 *
 */
public abstract class TableColumnEditableItemWidgetTransformer extends BaseWidgetTransformer {
	
	private List<String> propertyFilter = new ArrayList<String>();
	
	private boolean isNewLine(Widget widget) {
		boolean retval = false;
		Property prop = widget.findProperty("newLine");
		if (prop != null) {
			retval = prop.getBooleanValue();
		}
		return retval;
	}
	/**
	 * @param type
	 */
	public TableColumnEditableItemWidgetTransformer(WidgetType type, String[] propToFilter) {
		super(type);
		propertyFilter.add("item-column");
		propertyFilter.add(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
		filterProperties(propToFilter);
	}
	
	/**
	 * @param properties
	 */
	protected void filterProperties(String[] properties) {
		for (String string : properties) {
			propertyFilter.add(string);
		}
	}
	
	
	/**
	 * Extract some meaningful value and add them to the transformation context.
	 * 
	 * @param context
	 * @param widget
	 */
	protected void prepareTransformationContext(WidgetTransformerContext context, Widget widget) {

		// generation within an editable table
		context.setEditableTableTree(true);
		
		// extract the name of the editable dataset
		Widget wTable = widget.findAncestor(WidgetTypeConstants.TABLE_TREE);
		if (wTable != null) {
			Property prop = wTable.findProperty(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
			String datasetName = prop != null ? prop.getValue() : ""; //$NON-NLS-1$
			context.setEditableDataset(datasetName);
		}
	
		// extract the name of the association and the code 
		String domAttr = widget.getPropertyValue(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
		int pos = domAttr.lastIndexOf(".");
		if (pos != -1) {
			String attr = domAttr.substring(pos+1);
			context.setEditableDatasetAssociation(domAttr.substring(0, pos));
			context.setEditableDatasetAttribute(attr);
		} else {
			context.setEditableDatasetAssociation("");
			context.setEditableDatasetAttribute(domAttr);
		}
	}

	/**
	 * @param context
	 * @param widget
	 * @return
	 */
	protected abstract Element performTransformation(WidgetTransformerContext context, Widget widget)
			 throws CoreException;
	
	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		try {

			prepareTransformationContext(context, widget);		

			if (context.isEditableTableTree()) {
				// check if really a non editable widget should be generated.
				boolean generateNonEditableWidget = false;
				if (!context.getEditableTableTreeLeafNode()) {
					// an intermediate node is generated : either it correspond to a grouping level or
					// to a hierarchy level.
					if (context.isHierarchyNonLeafNode()) {
						// Check is the editable widget can be generated on an intermediate node
						// that correspond to a hierarchy level. 
						generateNonEditableWidget = "false".equals(widget.getPropertyValue("editable-levels"));
					} else {
						// The Editable widget is never generated on an intermediate node 
						// that correspond to a grouping level. 
						generateNonEditableWidget = true;
					}
				}
				if (generateNonEditableWidget) {
					
					if (isNewLine(widget)) {
						appendElement(context, XGUI_NAMESPACE_URI, "br");
					}

					// retrieve the column name used to access the table model with udp tag
					String columnName = widget.getPropertyValue("item-column");
					
					// TODO REFACTOR as a function
					MdfEnumeration mdfEnumeration = null;
					Widget columnWidget = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
					MdfModelElement element = columnWidget.findDomainObject(getDomainRepository(context));
					if (element != null && element instanceof MdfDatasetProperty) {
						MdfDatasetProperty prop = (MdfDatasetProperty) element;
						if (prop.getType() instanceof MdfEnumeration) {
							mdfEnumeration = (MdfEnumeration)prop.getType();
						}
					}

					if (mdfEnumeration != null) {
						Element logic0 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
						logic0.setTextContent("if(null == <udp:item column=\""+columnName+"\"/> || <udp:item column=\""+columnName+"\"/>.equals(\"\")) {");
						Element label0 = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
						appendElement(context, label0, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
						Element logic1 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
						logic1.setTextContent("} else {");
						Element label = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
						Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
						String transPrefix = getTranslationPrefixForEnum(context, mdfEnumeration);
						text.setTextContent("<i18n:text>"+transPrefix+".<udp:item column=\""+columnName+"\"/>.text</i18n:text>");
						Element logic2 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
						logic2.setTextContent("}");
					} else {
						Element label = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
						Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
						Element udpItem = appendElement(context, text, UDP_NAMESPACE_URI, UDP_ITEM_ELEMENT);
						if (!StringUtils.isEmpty(columnName)) {
							addAttribute(context, udpItem, UDP_ITEM_COLUMN, columnName);
						}
						Element oldParent = context.setParentElement(label);
						Property cssClass = widget.findProperty("item-css");
						if (cssClass != null) {
							PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(cssClass);
							pt.transform(context, cssClass);
						}
						transformTranslations(context, widget);
						context.setParentElement(oldParent);
					}
					
					return;
				}
			}
			
			ITable table = getTable(widget);
			String seqno = table.getWidget().getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_ROW_IDENTIFIER);
			String permission = table.getWidget().getPropertyValue(PropertyTypeConstants.TABLE_FORMAT_ROW_PERMISSION);
			
			
			if (isNewLine(widget)) {
				appendElement(context, XGUI_NAMESPACE_URI, "br");
			}
			
			Element logic  = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
			logic.setTextContent("if (<bean:is-true key=\""+getModuleBeanDefine(widget)+".updateMode\"/>) {");
			
			// contains the generated widget
			Element root = performTransformation(context, widget);;

			Node firstChild = root.getFirstChild();
			
			/*
			 * <xsp:attribute name="id"><bean:get-property bean="[BEAN_NAME]" property="id"/>.[ATTRIBUTE]</xsp:attribute>
			 */
			Element idattr = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT);
			root.insertBefore(idattr, firstChild);
			addAttribute(context, idattr, "name", "id");
			String domAttr = widget.getPropertyValue(PropertyTypeConstants.TABLE_COLUMN_ITEM_DATASET_ATTRIBUTE);
			if (WidgetTypeConstants.TABLE_COLUMN_COMBOBOX_ITEM.equals(widget.getTypeName())) {
				MdfModelElement modelElement = getMdfModelElement(context, widget);
				if (modelElement instanceof MdfDatasetProperty) {
					MdfDatasetProperty datasetProperty = (MdfDatasetProperty)modelElement;
					MdfEntity type = datasetProperty.getType();
					if (!(type instanceof MdfEnumeration)) {
						if (domAttr.contains(".")) {
							domAttr = domAttr.substring(0, domAttr.lastIndexOf("."));
							domAttr += "._oid";
						}
					}
				}
			} 
			idattr.setTextContent("<bean:get-property bean=\""+getShortBeanName(widget)+"\" property=\""+seqno+"\"/>."+domAttr);
			
			
			/*
			 * <xsp:attribute name="editable"><udp:item column="[PERMISSION]"/></xsp:attribute>
			 */
			if (StringUtils.isNotBlank(permission)) {
				Node nextSibling = idattr.getNextSibling();
				Element edit = createElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_ATTRIBUTE_ELEMENT);
				if (nextSibling != null) {
					root.insertBefore(edit, nextSibling);
				} else {
					root.appendChild(edit);
				}
				addAttribute(context, edit, "name", "editable");
				Element getprop = appendElement(context, edit, UdpConstants.UDP_NAMESPACE_URI, UdpConstants.UDP_ITEM_ELEMENT);
				addAttribute(context, getprop, "column", permission);	
			}
				
			Element oldParent = context.setParentElement(root);	
	        
			context.setParentElement(oldParent); 
	
			Element logic1 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
			logic1.setTextContent("} else {");
			
			String columnName = widget.getPropertyValue("item-column");

			// TODO REFACTOR as a function
			MdfEnumeration mdfEnumeration = null;
			Widget columnWidget = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
			MdfModelElement element = columnWidget.findDomainObject(getDomainRepository(context));
			if (element != null && element instanceof MdfDatasetProperty) {
				MdfDatasetProperty prop = (MdfDatasetProperty) element;
				if (prop.getType() instanceof MdfEnumeration) {
					mdfEnumeration = (MdfEnumeration)prop.getType();
				}
			}			

			if (mdfEnumeration != null) {
				Element logic0 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
				logic0.setTextContent("if(null == <udp:item column=\""+columnName+"\"/> || <udp:item column=\""+columnName+"\"/>.equals(\"\")) {");
				Element label0 = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
				appendElement(context, label0, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
				Element logic2 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
				logic2.setTextContent("} else {");
				Element label = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
				Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
				String transPrefix = getTranslationPrefixForEnum(context, mdfEnumeration);
				text.setTextContent("<i18n:text>"+transPrefix+".<udp:item column=\""+columnName+"\"/>.text</i18n:text>");
				Element logic3 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
				logic3.setTextContent("}");
			} else {
				Element label = appendElement(context, XGUI_NAMESPACE_URI, XGUI_LABEL_ELEMENT);
				
				Property formatProperty = TransformUtils.getFormatProperty(widget);
				if (formatProperty != null && StringUtils.isNotEmpty(formatProperty.getValue())
						&& widget.getParent().getTypeName().equals(WidgetTypeConstants.CONDITIONAL_BODY)) {

					TransformUtils.insertFormatCondition(context, columnName, label, formatProperty);

				} else{
					
					Element text = appendElement(context, label, XGUI_NAMESPACE_URI,XGUI_TEXT_ELEMENT);
					Element udpItem = appendElement(context, text, UDP_NAMESPACE_URI, UDP_ITEM_ELEMENT);
					if (!StringUtils.isEmpty(columnName)) {
						addAttribute(context, udpItem, UDP_ITEM_COLUMN, columnName);
					}
				}
			}
			
			Element logic2 = appendElement(context, XSPConstants.XSP_NAMESPACE_URI, XSPConstants.XSP_LOGIC);
			logic2.setTextContent("}");
		
		} finally {
			context.setEditableTableTree(false);
			context.setEditableDataset(null);
			context.setEditableDatasetAssociation(null);
			context.setEditableDatasetAttribute(null);
		}
		
	}

	/**
	 * @param widget
	 * @return
	 */
	private ITable getTable(Widget widget) {
		Widget column = widget.findAncestor(WidgetTypeConstants.TABLE_COLUMN);
		return TableHelper.getTableColumn(column).getTable();
	}
	
	/**
	 * @param widget
	 * @return
	 */
	private String getModuleBeanDefine(Widget widget) {
		return getModulePropertyValue(widget, PropertyTypeConstants.BEAN_DEFINE);
	}
	
	/**
	 * @param widget
	 * @param propertyName
	 * @return
	 */
	private String getModulePropertyValue(Widget widget, String propertyName) {
		Widget root = widget.getRootWidget();
		String name = "";
		if (root.getTypeName().equals(WidgetTypeConstants.MODULE)) {
			name = root.getPropertyValue(propertyName);
		}
		return name;
	}
	
	/**
	 * @param table
	 * @return
	 */
	private String getBeanName(ITable table) {
		String eds = table.getWidget().getPropertyValue(PropertyTypeConstants.TABLE_EDITABLE_DATASET);
		if (StringUtils.isEmpty(eds)) {
			Widget root = table.getWidget().getRootWidget();
			eds = root.getPropertyValue(PropertyTypeConstants.BEAN_DEFINE);
		} else {
			eds = "DSBean."+eds.replace(':', '.');
		}
		return eds;
	}
	
	private String getShortBeanName(Widget widget) {
		String beanName = getBeanName(getTable(widget));
		if (StringUtils.isNotEmpty(beanName)) {
			beanName = beanName.substring(beanName.lastIndexOf('.')+1);
		}
		return beanName;
	}
	
	@Override
	protected void transformProperties(WidgetTransformerContext context, Widget widget) throws CoreException  {
		for (Property p : widget.getProperties()) {
			if (!propertyFilter.contains(p.getTypeName())) {
				PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(p);
				pt.transform(context, p);
			}
		}		
		
		Property property = ModelFactory.eINSTANCE.createProperty();
		property.setTypeName(PropertyTypeConstants.BEAN_PROPERTY);
		property.setValue(context.getEditableDatasetAttribute());
		widget.getProperties().add(property);
		
		Property bnp = ModelFactory.eINSTANCE.createProperty();
		bnp.setTypeName(PropertyTypeConstants.BEAN_NAME);
		String beanName = getBeanName(getTable(widget));
		if (StringUtils.isNotEmpty(beanName)) {
			beanName = beanName.substring(beanName.lastIndexOf('.')+1);
		}
		bnp.setValue(beanName);
		bnp.setWidget(widget);

		PropertyTransformer pt = context.getTransformModel().findPropertyTransformer(property);
		pt.transform(context, property);	
		widget.getProperties().remove(property);
		widget.getProperties().remove(bnp);
	}

	@Override
	public Element getParentElement(WidgetTransformerContext context,
			Widget widget) {
		return null;
	}

	
	
	@SuppressWarnings({"unchecked" })
	private String getTranslationPrefixForEnum(WidgetTransformerContext context, MdfEnumeration enumType) {
		String prefix = "";
		List<MdfEnumValue> values = (List<MdfEnumValue>)enumType.getValues();
		if (values.size() > 0) {
			prefix = context.getTranslationKey(values.get(0), ITranslationKind.NAME);
			prefix = prefix.substring(0, prefix.lastIndexOf('.'));
			prefix = prefix.substring(0, prefix.lastIndexOf('.'));
		}
		return prefix;
	}
	
}
