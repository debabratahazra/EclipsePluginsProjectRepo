package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.T24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

/**
 * Misc of utilities for the templates.
 */
public class TemplateUtil {
    
    /**
     * @return IT24ViewItem[] - returns a T24ViewItem[] array with all the 
     * templates already stored.
     */
    public IT24ViewItem[] getTemplatesStored(){
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        // Retrieve the templates stored in the plug-in. Build an array of
        // T24ViewItem with them; with it we'll be able to show them on 
        // a view.
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        Map<String, String> props = mu.loadProperties();
        
        Set<String> keys = props.keySet();
        Iterator<String> templateKeyIter = keys.iterator();
        while(templateKeyIter.hasNext()){
            // Templates can be of two types (or categories): 
            //1. system (pre-defined and cannot be deleted)
            //2. user (created by the user, can be deleted)
            T24_VIEW_ITEM_CATEGORY templateCategory = T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY;
            String key = (String)templateKeyIter.next();
            if(key.indexOf("t24.template.system")!=-1){
                // IT IS A PRE-DEFINED SYSTEM 
                templateCategory = T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM;
               
            } else if(key.indexOf("t24.template.user")!=-1){
                // IT IS A USER 
                templateCategory = T24_VIEW_ITEM_CATEGORY.TEMPLATE_USER_ITEM;
            } 

            // Create a new T24ViewItem if needed and add it to the array list
            if(!T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY.equals(templateCategory)){
                // Split the value into its elements. The value has the following structure:
                // "template.item.key<<NR>>TEMP_NAME<<NR>>TEMP_BODY
                //e.g. t24.template.system.TEMPLATE<<NR>>TEMPLATE<<NR>>CALL CACHE.READ('F.<<1>>',<<1>>.ID,R.<<1>>,YERR)
                String tempData = (String)props.get(key);
                String[] tempElements = tempData.split(TemplateConstants.TEMPLATE_NEW_RECORD_SEPARATOR);
                
                Template temp = new Template();
                temp.setKey(tempElements[0]);
                temp.setName(tempElements[1]);
                temp.setBody(tempElements[2]);

                // Create an item and add it to the array List
                IT24ViewItem item = new T24ViewItem(temp.getName(), null, templateCategory, (Object)temp);
                itemsAList.add(item);         
            }
        }
        
        // Sort the array list with the templates; this will group the templates
        // by category: either User or System templates.
        Collections.sort(itemsAList, new TemplateComparator());
        
        // Transform ArrayList => to IT24ViewItem[]
        int arraySize = itemsAList.size();
        IT24ViewItem[] items = new IT24ViewItem[arraySize];
        return ((IT24ViewItem[]) itemsAList.toArray(items));
    }
    
    /**
     * Internal class used to sort the list of templates.
     */
     private class TemplateComparator implements Comparator<IT24ViewItem>{
        public int compare(IT24ViewItem first, IT24ViewItem second){
            
            if(T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM.equals(first.getCategory()) &&  
               T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM.equals(second.getCategory())){
                return first.getLabel().compareToIgnoreCase(second.getLabel());
                
            } else if(T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM.equals(first.getCategory()) &&  
                      T24_VIEW_ITEM_CATEGORY.TEMPLATE_USER_ITEM.equals(second.getCategory())){
                return -1;    
                
            } else if(T24_VIEW_ITEM_CATEGORY.TEMPLATE_USER_ITEM.equals(first.getCategory()) &&  
                    T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM.equals(second.getCategory())){                
                return 1;
                
            } else {
                // They are both TEMPLATE_USER_ITEM
                return first.getLabel().compareToIgnoreCase(second.getLabel()); 
            } 
        }
    }    
}
