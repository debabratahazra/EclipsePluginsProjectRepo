package com.temenos.t24.tools.eclipse.basic.views;

import org.eclipse.jface.text.Position;

import com.temenos.t24.tools.eclipse.basic.PluginException;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * This class represents an item within a View. Typically it is used
 * to include elements from a TextEditor into an Eclipse view. These 
 * elements could be; variables, GOSUB labels, CALL labels, etc
 *  
 * They will have the following parameters:
 * - label: that's the name (of the variable, of the gosub label, etc)
 * - position: a Position is an Eclipse's framework class, used to keep track of items on an Text Editor. 
 * - category: will help to classify them. Categories are typically defined in ViewConstants
 * - data; this is a generic variable included to add any additional information in the most flexible way.
 * 
 * Note: Positions are a useful feature provided by the framework. Positions can be 
 * attach to documents. They refer to offset and length of items within the document.
 * Whenever the editor is changed (by typing/deleting contents) the framework handles
 * the update of all the declared Positions for that document.
 * Example:
 * Position position = new Position(offset, length);
 * document.addPosition(POSTION_CATEGORY_XYZ, position);
 */
public class T24ViewItem implements IT24ViewItem{
    private static final int PRIME2 = 37;
    private static final int PRIME1 = 17;
    private String label = "";
    private T24_VIEW_ITEM_CATEGORY category = T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY;
    private Position position = null;
    private Object data = null;
    
    public T24ViewItem(String label){
        this(label, T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY);
    }
    
    /**
     * @param label    - item "readable" name as it will appear on a View
     * @param category - as defined in ViewConstants, e.g. CALL_ITEM
     */          
    public T24ViewItem(String label, T24_VIEW_ITEM_CATEGORY category){
        this.label = label;
        this.category = category;
    }
    
    /**
     * @param label    - item "readable" name as it will appear on a View
     * @param position - Position object linked to a document (to keep track of offset/length)
     * @param category - as defined in ViewConstants, e.g. CALL_ITEM
     */    
    public T24ViewItem(String label, Position position, T24_VIEW_ITEM_CATEGORY category){
        this.label = label;
        this.position = position;
        this.category = category;        
    }    
    
    /**
     * @param label    - item "readable" name as it will appear on a View
     * @param position - Position object linked to a document (to keep track of offset/length)
     * @param category - as defined in ViewConstants, e.g. CALL_ITEM
     * @param data     - generic variable to add additional information regarding this item
     */
    public T24ViewItem(String label, Position position, T24_VIEW_ITEM_CATEGORY category, Object data){
        this.label    = label;
        this.position = position;
        this.category = category;
        this.data     = data;
    }        
    
    public String getLabel(){
        return this.label;
    }

    /**
     * Returns the offset of the element within the text editor.
     * Note that the offset is kept by the position.
     */
    public int getOffset(){
        //return this.offset;
        return this.position.getOffset();
    }
    
    /**
     * Returns the length of the element within the text editor.
     * Note that the length is kept by the position.
     */    
    public int getLength(){
        //return this.length;
        return this.position.getLength();
    }
    
    public Position getPosition(){
        return this.position;
    }
    
    public String toString(){
        return label;
    }

    public Object getData() {
        return this.data;
    }

    public int compareTo(IT24ViewItem item) {
        int result = (10 * this.category.compareTo(item.getCategory()) + 
                           this.label.compareTo(item.getLabel()));
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean res = false;
        
        if (! (obj instanceof IT24ViewItem) ) {
            throw new PluginException("Not an instance of IT24ViewItem");
        }
        
        IT24ViewItem item = (IT24ViewItem)obj;
        
        if (label.equals(item.getLabel())) {
            res = true;
        } else {
            res = false;
        }
        
        if ( res && category.equals(item.getCategory())) {
            res = true;
        } else {
            res = false;
        }
        
        if ( res && position != null && position.equals(item.getPosition())) {
            res = true;
        } else {
            if (res && position == null && item.getPosition() == null) {
                res = true;
            } else {
                res = false;
            }
        }
        
        return res;
        
    }
    
    @Override
    public int hashCode() {
        int result = PRIME1;
        result = PRIME2 * result + label.hashCode();
        result = PRIME2 * result + category.hashCode();
        return result;
    }

    public T24_VIEW_ITEM_CATEGORY getCategory() {
        return this.category;
    }
    
    public void setData(Object data){
        this.data = data;
    }   
}
