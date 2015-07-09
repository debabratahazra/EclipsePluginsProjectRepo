package com.temenos.t24.tools.eclipse.basic.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * A LabelProvider is a class invoked by JFace Viewers, e.g. TableViewer. It is used 
 * to retrieve textual labels (that can be displayed) and Images of an element passed 
 * to it.
 */
public class T24LabelsProvider extends LabelProvider implements ITableLabelProvider {

    public T24LabelsProvider() {
    }

    /**
     * Returns a text representing the element Object passed to it. This piece
     * of text will be the one displayed on the View table. 
     * @param element - typically it'll be an instance implementing IT24ViewItem
     * @param index - not used
     * @return String - label representing the object.
     */
    public String getColumnText(Object element, int index) {
        if (element instanceof T24ViewItemGroup) {
            T24ViewItemGroup group = (T24ViewItemGroup) element;
            if (ViewConstants.T24_VIEW_ITEM_CATEGORY.CALL_ITEM.equals(group.getCategory())) {
                // For CALLs add the numbef of matches
                return "  " + group.getLabel() + " (" + group.getNoItems() + ")";
            } else if (ViewConstants.T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM.equals(group.getCategory())) {
                // For Variables add the numbef of matches
                return "  " + group.getLabel() + " (" + group.getNoItems() + ")";
            } else {
                return "  " + group.getLabel();
            }
        } else if (element instanceof T24ViewItem) {
            IT24ViewItem item = (IT24ViewItem) element;
            return "  " + item.getLabel();
        } else {
            return "";
        }
    }

    /**
     * Returns an Image associated to the element Object passed to it. The Image 
     * will be displayed on the View table. 
     * @param element - typically it'll be an instance implementing IT24ViewItem
     * @param index - not used
     * @return Image - Image associated to the object.
     */
    public Image getColumnImage(Object element, int index) {
        T24_VIEW_ITEM_CATEGORY itemCategory;
        if (element instanceof T24ViewItemGroup) {
            T24ViewItemGroup group = (T24ViewItemGroup) element;
            itemCategory = group.getCategory();
        } else if (element instanceof T24ViewItem) {
            IT24ViewItem item = (IT24ViewItem) element;
            itemCategory = item.getCategory();
        } else {
            itemCategory = T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY;
        }
        if (T24_VIEW_ITEM_CATEGORY.LABEL_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.LABEL_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.T24REGION_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.REGION_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.CALL_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.CALL_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.INSERT_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.INSERT_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.VARIABLE_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.VARIABLE_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.TODO_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.TODO_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.MACRO_SYSTEM_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.MACRO_USER_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.MACRO_USER_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.TEMPLATE_SYSTEM_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.TEMPLATE_USER_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.TEMPLATE_USER_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.SERVER_DIRECTORY_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.DIRECTORY_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.SERVER_FILE_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.FILE_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.ERROR_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.ERROR_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.WARNING_ITEM.equals(itemCategory)) {
            return (EclipseUtil.getImage(ViewConstants.WARNING_IMAGE_FILE));
        } else if (T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY.equals(itemCategory)) {
            return (null);
        } else {
            return (EclipseUtil.getImage(ViewConstants.DEFAULT_IMAGE_FILE));
        }
    }
}
