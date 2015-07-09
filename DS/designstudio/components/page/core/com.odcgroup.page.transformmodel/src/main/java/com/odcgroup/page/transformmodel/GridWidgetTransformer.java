package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_BEAN_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_GET_PROPERTY_ELEMENT;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_PROPERTY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.Grid;
import com.odcgroup.page.model.widgets.impl.GridAdapter;
import com.odcgroup.page.transformmodel.util.TransformUtils;

/**
 * This transformer transforms a grid widget into xgui
 * @author atr
 */
public class GridWidgetTransformer extends BaseWidgetTransformer {

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#getParentElement(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public Element getParentElement(WidgetTransformerContext context,Widget widget) {
		return null;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
	 */
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		Widget parent=widget.getParent();
	    String type = parent.getTypeName();
		Element oldParent=null;
		Grid grid = new GridAdapter(widget);
		int nbRows = grid.getRowCount();
		int nbColumns = grid.getColumnCount();
		//check if the grid is in the xtooltip widget.dont generate columns if parent  is xtooltipwidget.
		if(!type.equals("Xtooltip")){
			Element tableElement = createElement(context, XGUI_NAMESPACE_URI, "table");
			TransformUtils.appendChild(context, tableElement);	
			// Set the parent so that the Attributes are set on the correct element
			 oldParent= context.setParentElement(tableElement);
			transformProperties(context, widget);
		   
			if (nbColumns > 0) {
			// generate columns definition
			int width = 100 / nbColumns;
			Element columnsElement = createElement(context, XGUI_NAMESPACE_URI, "columns");
			TransformUtils.appendChild(context, columnsElement);	
			for (int cx=0; cx < nbColumns; cx++) {
				Element columnElement = createElement(context, XGUI_NAMESPACE_URI, "column");
				columnsElement.appendChild(columnElement);

	        	// column type
				TransformUtils.convertToAttribute(context, columnElement, "type", "text");
	        	
	        	// column width
	        	width = grid.getColumnWidth(cx);
	        	TransformUtils.convertToAttribute(context, columnElement, "width", width+"%");
	        	
	        	// column css class
	        	String cssClass = grid.getColumnCssClass(cx);
	        	if (StringUtils.isNotEmpty(cssClass)) {
	        		TransformUtils.convertToAttribute(context, columnElement, "cssClass", cssClass);
	        	}
	        	
			}
		}
		}
		for (int rx=0; rx < nbRows; rx++) {
			Element rowElement = createElement(context, XGUI_NAMESPACE_URI, "row");
			TransformUtils.appendChild(context, rowElement);	
			Element old = context.setParentElement(rowElement);
			for (int cx=0; cx < nbColumns; cx++) {
				Element cellElement = createElement(context, XGUI_NAMESPACE_URI, "cell");
				TransformUtils.appendChild(context, cellElement);	
				Element old2 = context.setParentElement(cellElement);
				Widget cell = grid.getCellWidgetAt(rx, cx);
				//chekc if grid parent is  xtooltipwidget and cell contain more than 1 widget. 
				if(type.equals("Xtooltip")){
					/*Element labelElement = createElement(context, XGUI_NAMESPACE_URI, "label");
					TransformUtils.appendChild(context, labelElement);
					 context.setParentElement(labelElement);*/
					if(cell.getContents().size()>1){
						 transformCell(context, cell);
					 }else{
						 transformChildren(context, cell);
					}
				 }else{
					 transformChildren(context, cell);
				}
				
				transformProperties(context, cell);
				context.setParentElement(old2);
			}
			context.setParentElement(old);
		}
		if(oldParent!=null){
		context.setParentElement(oldParent);
		}
		
	}

	/**
	 * @param type
	 */
	public GridWidgetTransformer(WidgetType type) {
		super(type);
	}

	/**
	 * trnaform the cell if a cell contain more than 1 widget .concat the widgets. 
	 * @param context
	 * @param cell
	 */
	private void transformCell(WidgetTransformerContext context, Widget cell){
		String namePropertyValue="";
		String beanPropertyValue="";
		if(cell.getContents().size()>0){
			Element lableElement = createElement(context, XGUI_NAMESPACE_URI, "label");	
			TransformUtils.appendChild(context, lableElement);	
			 context.setParentElement(lableElement);
		}
		//create xgui:text for each of the widgets in the cell.
		for (Iterator it = cell.getContents().iterator(); it.hasNext();) {
			Widget child = (Widget) it.next();
			Element textElement = createElement(context, XGUI_NAMESPACE_URI, "text");	
			TransformUtils.appendChild(context, textElement);
			Element oldParent = context.setParentElement(textElement);
			//create the bean proeprty elemnt and set the attributes.
			Property nameProperty = BeanPropertyUtils.findBeanNameProperty(context, child);
			Property beanProperty = child.findProperty(PropertyTypeConstants.BEAN_PROPERTY);
			if (nameProperty != null && beanProperty != null) {
			 namePropertyValue = nameProperty.getValue();
			 beanPropertyValue = beanProperty.getValue();
			}
			Namespace beanNamespace = context.getTransformModel().findNamespace(BEAN_URI);
			Element beanGet = context.getDocument().createElementNS(beanNamespace.getUri(), BEAN_GET_PROPERTY_ELEMENT);
			beanGet.setPrefix(BeanConstants.BEAN_PREFIX);
			
			addAttribute(context, beanGet, BEAN_BEAN_ATTRIBUTE, namePropertyValue);
			
			String bpp = BeanPropertyUtils.findBeanPropertyPrefix(context, child);
			String bpi = BeanPropertyUtils.findBeanPropertyPostfix(context, child);
			addAttribute(context, beanGet, BEAN_PROPERTY_ATTRIBUTE, bpp + beanPropertyValue + bpi);
			
			TransformUtils.appendChild(context,beanGet);
			context.setParentElement(oldParent);
			if(it.hasNext()){
				Element spaceText = createElement(context, XGUI_NAMESPACE_URI, "text");	
				TransformUtils.appendChild(context, spaceText);
				spaceText.setTextContent(" ");
				oldParent=context.setParentElement(textElement);
			}
			context.setParentElement(oldParent);
		  }
	}
}
