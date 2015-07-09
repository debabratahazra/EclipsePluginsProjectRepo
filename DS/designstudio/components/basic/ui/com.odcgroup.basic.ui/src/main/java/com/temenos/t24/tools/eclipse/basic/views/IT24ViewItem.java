package com.temenos.t24.tools.eclipse.basic.views;

import org.eclipse.jface.text.Position;

import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;



/**
 * Generic interface meant to be implemented by all the items that appear on T24 Views. 
 * Examples of these items are: calls, labels, inserts, macros, regions, etc  
 *
 */
public interface IT24ViewItem extends Comparable<IT24ViewItem> {
    public T24_VIEW_ITEM_CATEGORY getCategory();
    public int getOffset();
    public int getLength();
    public String getLabel();
    public Position getPosition();
    public Object getData();
}
