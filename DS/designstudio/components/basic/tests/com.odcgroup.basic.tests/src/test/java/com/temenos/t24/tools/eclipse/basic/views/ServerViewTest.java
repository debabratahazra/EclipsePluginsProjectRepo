package com.temenos.t24.tools.eclipse.basic.views;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.text.Position;
import org.junit.Test;

import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;


public class ServerViewTest {
    
    @Test
	public void testT24ViewItemCompare() {
        IT24ViewItem item1 = new T24ViewItem("GOSUB_label", T24_VIEW_ITEM_CATEGORY.GOSUB_ITEM);
        IT24ViewItem item2 = new T24ViewItem("LABEL_label", T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertTrue(item1.compareTo(item2)>0);
        
        item1 = new T24ViewItem("LABEL_label", T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertTrue(item1.compareTo(item2)==0);
        
        item1 = new T24ViewItem("CALL_label", T24_VIEW_ITEM_CATEGORY.CALL_ITEM);
        assertTrue(item1.compareTo(item2)<0);
        
        item1 = new T24ViewItem("T24REGION_label", T24_VIEW_ITEM_CATEGORY.T24REGION_ITEM);
        assertTrue(item1.compareTo(item2)>0);

    }    
    
    @Test
	public void testT24ViewItemEquals() {
        IT24ViewItem item1 = new T24ViewItem("GOSUB_label", T24_VIEW_ITEM_CATEGORY.GOSUB_ITEM);
        IT24ViewItem item2 = new T24ViewItem("LABEL_label", T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertFalse(item1.equals(item2));
        
        item1 = new T24ViewItem("LABEL_label", T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertTrue(item1.equals(item2));

        Position pos1 = new Position(100);
        item1 = new T24ViewItem("LABEL_label", pos1, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        Position pos2 = new Position(100);
        item2 = new T24ViewItem("LABEL_label", pos2, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertTrue(item1.equals(item2));
        
        pos2 = new Position(101);
        item2 = new T24ViewItem("LABEL_label", pos2, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
        assertFalse(item1.equals(item2));        
        
    }    
    
}
