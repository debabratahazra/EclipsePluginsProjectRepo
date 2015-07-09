package com.temenos.t24.tools.eclipse.basic.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.jface.text.Position;

import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * This class models a group of view items (IT24ViewItem)with the same label 
 * (and category), but with different positions within a document.
 * E.g. if a document has the following contents: 
 * CALL TEST1.MODULE
 * CALL TEST2.MODULE
 * CALL TEST2.MODULE
 * CALL TEST2.MODULE
 * CALL TEST1.MODULE
 * For the category (CALLS), there are two groups, one with the label "TEST1.MODULE"
 * and another with the label "TEST2.MODULE". The first group has two positions (i.e.
 * there are two elements) whereas the second has three.
 */
public class T24ViewItemGroup{
    private String label;
    private T24_VIEW_ITEM_CATEGORY category;
    private ArrayList<Position> positionList;    
    private int noItems  = 0;
    private int getIndex = -1;
    
    /**
     * @param label    - item "readable" name as it will appear on a View
     */    
    public T24ViewItemGroup(String label){
        this(label, T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY);
    }
    
    /**
     * @param label    - item "readable" name as it will appear on a View
     * @param category - as defined in ViewConstants, e.g. BASIC_CALL_ITEM
     */          
    public T24ViewItemGroup(String label, T24_VIEW_ITEM_CATEGORY category){
        positionList = new ArrayList<Position>();
        this.label = label;
        this.category = category;        
    }
    
    /**
     * @param item - T24ViewItem with which to start the group
     */          
    public T24ViewItemGroup(IT24ViewItem item){
        positionList = new ArrayList<Position>();
        this.addPosition(item.getPosition());
        this.label = item.getLabel();
        this.category = item.getCategory();        
    }    
    
    public String getLabel(){
        return this.label;
    }
    public int getNoItems(){
        return this.noItems;
    }
    public T24_VIEW_ITEM_CATEGORY getCategory() {
        return this.category;
    }
    
    public void addPosition(Position pos){
        positionList.add(noItems, pos);
        noItems++;
    }    
    
    /*
     * Increments the position index and use it to return 
     * the next item. If the end is reached, it'll start again
     * back at the beginning (it rotates).
     */
    public Position getNextPosition(){
        int listSize = positionList.size();
        if(noItems>0){
            // There are some items in the list
            getIndex++;
            if(getIndex<listSize){
                return positionList.get(getIndex);
            } else {
                getIndex = 0; // start at the begining
                return positionList.get(getIndex);
            }
        } else {
            // No items in list yet
            return null;
        }
    }
    
    public void sortList(){
        Collections.sort(positionList, new PositionComparator());
    }
    
    /**
     * Comparator class
     */
    class PositionComparator implements Comparator<Position> {
        public int compare(Position firstItem, Position secondItem){
            return (firstItem.getOffset()- secondItem.getOffset());
        }
    }
    
    public String toString(){
        return label;
    }    
}
