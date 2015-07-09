package com.temenos.t24.tools.eclipse.basic.views.tasks;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * A LabelProvider is a class invoked by JFace Viewers, e.g. TableViewer. It is used 
 * to retrieve textual labels (that can be displayed) and Images of an element passed 
 * to it.
 */
public class TaskLabelsProvider extends LabelProvider implements ITableLabelProvider {
    
    public TaskLabelsProvider(){
    }

    /**
     * Returns a text representing the element Object passed to it. This piece
     * of text will be the one displayed on the View table. 
     * @param element - typically it'll be an instance implementing IT24ViewItem
     * @param index - column index
     * @return String - label representing the object.
     */
    public String getColumnText(Object element, int index){
        TaskItem task = (TaskItem)element;
        switch(index){
            case 0:
                return " "+task.getDescription();
            case 1:
                return task.getMessage();
            case 2:
                if(-1 == task.getLine())
                    return "No line available";
                else 
                    return Integer.toString(task.getLine());
            case 3:
                return task.getFilename();
            default:
                return "unknown column "+index;
        }
        
    }

    /**
     * Returns an Image associated to the element Object passed to it. The Image 
     * will be displayed on the View table. 
     * @param element - typically it'll be an instance implementing IT24ViewItem
     * @param index - not used
     * @return Image - Image associated to the object.
     */
    public Image getColumnImage(Object element, int index){
        TaskItem task = (TaskItem)element;
        if(index == 0){
            if(T24_VIEW_ITEM_CATEGORY.COMPILE_ERROR_ITEM.equals(task.getCategory())){
                return (EclipseUtil.getImage(ViewConstants.COMPILE_ERROR_IMAGE_FILE));
                
            } else if(T24_VIEW_ITEM_CATEGORY.COMPILE_WARNING_ITEM.equals(task.getCategory())){
                return (EclipseUtil.getImage(ViewConstants.COMPILE_WARNING_IMAGE_FILE));
                
            } else if(T24_VIEW_ITEM_CATEGORY.COMPILE_STANDARD_ITEM.equals(task.getCategory())){
                return (EclipseUtil.getImage(ViewConstants.COMPILE_STANDARD_IMAGE_FILE));                
                
            } else if(T24_VIEW_ITEM_CATEGORY.COMPILE_CODEREVIEW_ITEM.equals(task.getCategory())){
                return (EclipseUtil.getImage(ViewConstants.COMPILE_CODEREVIEW_IMAGE_FILE));                
                
            } else {
                return (EclipseUtil.getImage(ViewConstants.DEFAULT_IMAGE_FILE));
            }
        } else {
            return null;
        }
        
    }    
   
}
