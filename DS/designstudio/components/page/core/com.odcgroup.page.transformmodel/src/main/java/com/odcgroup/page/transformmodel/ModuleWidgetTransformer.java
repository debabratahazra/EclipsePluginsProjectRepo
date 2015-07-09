package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.metamodel.PropertyTypeConstants.MESSAGE_REPORTER_FORM_NAME;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.ReporterContants.ADD_SCOPE_REPORTER_ELEMENT;
import static com.odcgroup.page.transformmodel.ReporterContants.FORM_NAME_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.ReporterContants.MSG_RENDERING_ELEMENT;
import static com.odcgroup.page.transformmodel.ReporterContants.REPORTER_ELEMENT;
import static com.odcgroup.page.transformmodel.ReporterContants.REPORTER_PREFIX;
import static com.odcgroup.page.transformmodel.ReporterContants.REPORTER_URI;
import static com.odcgroup.page.transformmodel.ReporterContants.REPORT_KEY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.ReporterContants.REPORT_KEY_SUFFIX;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAME_ELEMENT;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_PAGE;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.MetaInfoRendererUtil;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * The class <code>ModuleWidgetTransformer</code> transforms "Module" widget
 * into XSP code.
 * 
 * @author atr
 */
public class ModuleWidgetTransformer extends BaseWidgetTransformer {

	@Override
	public Element getParentElement(WidgetTransformerContext context, Widget widget) {
		return null;
	}

	@Override
	public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
		
		if("autoComplete".equals(widget.getPropertyValue(PropertyTypeConstants.SEARCH))) {
			Element pageElem = createElement(context, XSP_NAMESPACE_URI, XSP_PAGE);
			context.setParentElement(pageElem);
			// add DS Header
			MetaInfoRendererUtil.addComment(context, widget, pageElem);
			Widget auto = getAutoCompleteDesign(widget);
			if (auto != null) {
				// no need to transform box widgets if any
				WidgetTransformer wt = context.getTransformModel().findWidgetTransformer(auto);
				wt.transform(context, auto);
			} else {
				// if auto widget is not found
				transformChildren(context, widget);
			}
		} else {
			Element pageElem = createElement(context, XSP_NAMESPACE_URI, XSP_PAGE);
			context.setParentElement(pageElem);
	
			// add DS Header
			MetaInfoRendererUtil.addComment(context, widget, pageElem);
	
			Element moduleElem = createElement(context, XGUI_NAMESPACE_URI, "module");
			pageElem.appendChild(moduleElem);
			context.setParentElement(moduleElem);
	
			// properties
			transformProperties(context, widget);
	
			// DS-2756-begin Add messaging facility on editable form (in module
			// only)
			Property pName = widget.findProperty(PropertyTypeConstants.BEAN_NAME);
			Property pDefine = widget.findProperty(PropertyTypeConstants.BEAN_DEFINE);
			if (pName != null && pDefine != null) {
				if (StringUtils.isNotEmpty(pName.getValue()) && StringUtils.isNotEmpty(pDefine.getValue())) {
					Element reporter = createElement(context, REPORTER_URI, REPORTER_ELEMENT);
					context.getParentElement().appendChild(reporter);
					reporter.setPrefix(REPORTER_PREFIX);
	
					Element addScope = createElement(context, reporter.getNamespaceURI(), ADD_SCOPE_REPORTER_ELEMENT);
					addScope.setPrefix(REPORTER_PREFIX);
					String reportKey = pDefine.getValue();
					addScope.setAttribute(REPORT_KEY_ATTRIBUTE, reportKey + REPORT_KEY_SUFFIX);
					reporter.appendChild(addScope);
	
					Element msgRendering = createElement(context, reporter.getNamespaceURI(), MSG_RENDERING_ELEMENT);
					msgRendering.setPrefix(REPORTER_PREFIX);
					Property messageReporterProperty = widget.findProperty(MESSAGE_REPORTER_FORM_NAME);
					msgRendering.setAttribute(FORM_NAME_ATTRIBUTE, messageReporterProperty.getValue());
					reporter.appendChild(msgRendering);
				}
			}
			// DS-2756-end
	
			// Module name & translation key
			Element nameElem = createElement(context, XGUI_NAMESPACE_URI, XGUI_NAME_ELEMENT);
			context.getParentElement().appendChild(nameElem);
			String key = context.getTranslationKey(widget, ITranslationKind.NAME);
			if (key != null) {
				Element i18nElem = createElement(context, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
				i18nElem.setTextContent(key);
				nameElem.appendChild(i18nElem);
			}
	
			// DS-2896-begin requires new nav:managerequest element for all the
			// modules
			Element nav = createElement(context, TransformerConstants.NAV_NAMESPACE_URI, "managerequest");
			TransformUtils.convertToAttribute(context, nav, "nav-id", widget.getID());
			TransformUtils.appendChild(context.getParentElement(), nav);
			context.setParentElement(nav);
			// DS-2896-end
	
			transformChildren(context, widget);
			context.setParentElement(pageElem);
		}
	}
	
	/**
	 * @param parent
	 * @return
	 */
	private Widget getAutoCompleteDesign(Widget parent) {
		List<Widget> widgets = parent.getContents();
		for (Widget widget : widgets) {
			if (widget.getTypeName().equals("AutoCompleteDesign")) {
				return widget;
			}
			return getAutoCompleteDesign(widget); 
		}
		return null;
	}

	/**
	 * Creates a new TableWidgetTransformer.
	 * 
	 * @param type
	 *            the widget type
	 */
	public ModuleWidgetTransformer(WidgetType type) {
		super(type);
	}

}
