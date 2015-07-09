package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.I18nConstants.I18N_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.I18nConstants.I18N_TEXT_ELEMENT_NAME;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_ACTIVITY_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_ID;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_MODEL_ITEM;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_MODEL_REF;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_NAME_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_TEXT_ELEMENT;
import static com.odcgroup.page.transformmodel.XGuiConstants.XGUI_VALUE;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_NAMESPACE_URI;
import static com.odcgroup.page.transformmodel.XSPConstants.XSP_PAGE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.WidgetType;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.transformmodel.util.MetaInfoRendererUtil;
import com.odcgroup.page.transformmodel.util.TransformUtils;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * The transformer for the Page Widget. This adds a layer contains meta-information
 * about the page being generated.
 * 
 * @author Gary Hayes
 * @author Alexandre Jaquet
 */
public class PageWidgetTransformer extends BaseWidgetTransformer {
	
	/** The Date Format. */
	private DateFormat dateFormat;

    /**
     * Creates a new PageWidgetTransformer.
     * 
     * @param type
     */
    public PageWidgetTransformer(WidgetType type) {
        super(type);
        
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }
    
    /**
     * @see com.odcgroup.page.transformmodel.WidgetTransformer#transform(com.odcgroup.page.transformmodel.WidgetTransformerContext, com.odcgroup.page.model.Widget)
     */
    public void transform(WidgetTransformerContext context, Widget widget) throws CoreException {
        Element page = createElement(context, XSP_NAMESPACE_URI, XSP_PAGE);
        context.setParentElement(page);
        // add DS Header
        MetaInfoRendererUtil.addComment(context, widget, page);
        
        Element activity = appendElement(context, XGUI_NAMESPACE_URI, XGUI_ACTIVITY_ELEMENT);
        context.setParentElement(activity);
        
        transformProperties(context, widget);
        transformChildren(context, widget);
        
        // At the metadata to the END of the page so that when the developer uses the Xsp Editor view
        // it won't disturb him
        transformMetaDataLayer(context, widget);
        
        context.setParentElement(page);
    }
    
    /**
     * Gets the Xml element which represents the parent Element to which children will be attached.
     * Note that this does not return all the XML that this transformer will generate. It is essentially
     * used to help in the content-assist and auto-completion facilities.
     *  
     * @param context The WidgetTransformerContext
     * @param widget The Widget to get the Element for
     * @return Element The Element
     */
    public Element getParentElement(WidgetTransformerContext context, Widget widget) {
        return createElement(context, XSP_NAMESPACE_URI, XSP_PAGE);
    } 
    
    /**
     * Creates a layer containing metadata related to the page.
     * 
     * @param context The WidgetTransformerContext
     * @param widget The Widget to transform
     */
    private void transformMetaDataLayer(WidgetTransformerContext context, Widget widget) {
        Element modelRef = appendElement(context, XGUI_NAMESPACE_URI, XGUI_MODEL_REF);

        URI uri = widget.eResource().getURI();
        String modelName = uri.lastSegment();
        
        // trim the modelname segment
        uri = uri.trimSegments(1);
        String path = OfsResourceHelper.getPathFromPlatformURI(uri);
        String modelPkg = TransformUtils.getModelPackage(path);
        // get the packages 
        int index = modelPkg.indexOf("/");
        String pkg = modelPkg.substring(index+1);
        pkg = pkg.replace('/', '.');
    
        IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(context.getProject());
        String ofsProjectName = ofsProject.getName();
        
        addAttribute(context, modelRef, XGUI_ID, ofsProjectName + "." + pkg + "." + modelName + "_modelref");
        
        // The general project name
        Element name = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_NAME_ELEMENT);
        Element title = appendElement(context, name, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
        title.setTextContent("general.model.reference");
        
        Element modelItem = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_MODEL_ITEM);
        addAttribute(context, modelItem, XGUI_VALUE, ofsProjectName);
        appendMessageToModelItem(context, modelItem, "general.project.name");
        
        // The general model name element
        modelItem = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_MODEL_ITEM);
        addAttribute(context, modelItem, XGUI_VALUE, modelName);
        appendMessageToModelItem(context, modelItem, "general.model.name");
       
        // The general package element
        modelItem = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_MODEL_ITEM);
        addAttribute(context, modelItem, XGUI_VALUE, pkg);
        appendMessageToModelItem(context, modelItem, "general.package");

        // The general gendatetime element
        modelItem = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_MODEL_ITEM);
        addAttribute(context, modelItem, XGUI_VALUE, generateDateTime());
        appendMessageToModelItem(context, modelItem, "general.gendatetime");

        // The general genbyuser element
        modelItem = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_MODEL_ITEM);
        addAttribute(context, modelItem, XGUI_VALUE, System.getProperty("user.name"));
        appendMessageToModelItem(context, modelItem, "general.genbyuser");

        // The general dsversion element
        modelItem = appendElement(context, modelRef, XGUI_NAMESPACE_URI, XGUI_MODEL_ITEM);
        addAttribute(context, modelItem, XGUI_VALUE, OfsCore.getVersionNumber());
        appendMessageToModelItem(context, modelItem, "general.dsversiontext");

    }
    

    /**
     * Appends a message to the specified model item
     * 
     * @param context
     * 			The widget transformer context
     * @param parent
     * 			The parent element
     * @param value
     * 			The value
     */
    private void appendMessageToModelItem(WidgetTransformerContext context, Element parent,String value) {
        Element xguiText = appendElement(context, parent, XGUI_NAMESPACE_URI, XGUI_TEXT_ELEMENT);
        Element i18nText = appendElement(context, xguiText, I18N_NAMESPACE_URI, I18N_TEXT_ELEMENT_NAME);
        i18nText.setTextContent(value);    
    }
    
    /**
     * Generate the current time
     * 
     * @return String the current time
     */
    private String generateDateTime() {
    	return dateFormat.format(Calendar.getInstance().getTime());
    }
}
