package com.odcgroup.page.ui.properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.LabelProvider;

import com.odcgroup.page.metamodel.DataValue;


/**
 * The label Provider for data values.
 * 
 * @author Gary Hayrs
 */
public class DataValueLabelProvider extends LabelProvider {

    /**
     * Returns the text for the label of the given element.
     * 
     * @param element the element for which to provide the label text
     * @return the text string used to label the element, or <code>null</code> if there is no text label for the given
     *         object
     */
    public String getText(Object element) {
        DataValue dv = (DataValue) element;
        if (!StringUtils.isEmpty(dv.getDisplayName())) {
            return dv.getDisplayName();
        }
        return dv.getValue();
    }
}
