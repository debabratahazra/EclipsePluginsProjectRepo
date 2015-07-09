package com.odcgroup.page.transformmodel;

import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_DEFINE_ELEMENT;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_KEY_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_NAME_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_PREFIX_KEYWORD_ATTRIBUTE;
import static com.odcgroup.page.transformmodel.BeanConstants.BEAN_URI;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;


/**
 * The transformer for the Bean-Define property.
 * 
 * @author Gary Hayes
 */
public class BeanDefinePropertyTransformer extends BasePropertyTransformer {

	/**
     * Constructs a new BeanDefinePropertyTransformer.
     * 
     * @param type
     *            The PropertyType
     */
    public BeanDefinePropertyTransformer(PropertyType type) {
        super(type);
    }
    
    /**
     * Transforms the Property.
     * 
     * @param context
     *            The WidgetTransformerContext
     * @param property
     *            The Property to transform
     */
    public void transform(WidgetTransformerContext context, Property property) {
        Widget widget = property.getWidget();

        Property pName = widget.findProperty(PropertyTypeConstants.BEAN_NAME);
        Property pDefine = widget.findProperty(PropertyTypeConstants.BEAN_DEFINE);
        
        if (pName == null || pDefine == null) {
            return;
        } 
        
        if (StringUtils.isEmpty(pName.getValue()) || StringUtils.isEmpty(pDefine.getValue())) {
            return;
        }
        
        Element e = appendElement(context, BEAN_URI, BEAN_DEFINE_ELEMENT);

        addAttribute(context, e, BEAN_NAME_ATTRIBUTE, pName.getValue());
        addAttribute(context, e, BEAN_KEY_ATTRIBUTE, pDefine.getValue());
        // @see http://rd.oams.com/browse/OCS-42357 & http://rd.oams.com/browse/DS-5547
        addAttribute(context, e, BEAN_PREFIX_KEYWORD_ATTRIBUTE, "final");
    }
}
