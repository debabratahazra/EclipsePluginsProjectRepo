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
 * Misc of utilities for the macros.
 */
public class MacroUtil {
    
    /**
     * @return IT24ViewItem[] - returns a T24ViewItem[] array with all the 
     * macros already stored.
     */
    public IT24ViewItem[] getMacrosStored(){
        ArrayList<IT24ViewItem> itemsAList = new ArrayList<IT24ViewItem>();
        // Retrieve the macros stored in the plug-in. Build an array of
        // T24ViewItem with them; with it we'll be able to show them on 
        // a view.
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        Map<String, String> props = mu.loadProperties();
        
        Set<String> keys = props.keySet();
        Iterator<String> macroKeyIter = keys.iterator();
        while(macroKeyIter.hasNext()){
            // Macros can be of two types (or categories): 
            //1. system macros (pre-defined and cannot be deleted)
            //2. user macros (created by the user, can be deleted)
            T24_VIEW_ITEM_CATEGORY macroCategory = T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY;
            String key = (String)macroKeyIter.next();
            if(key.indexOf("t24.macro.system")!=-1){
                // IT IS A PRE-DEFINED SYSTEM MACRO
                macroCategory = T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM;
               
            } else if(key.indexOf("t24.macro.user")!=-1){
                // IT IS A USER MACRO
                macroCategory = T24_VIEW_ITEM_CATEGORY.MACRO_USER_ITEM;
            } 

            // Create a new T24ViewItem if needed and add it to the array list
            if(!T24_VIEW_ITEM_CATEGORY.NULL_CATEGORY.equals(macroCategory)){
                // Split the value into its elements. The value has the following structure:
                // "macro.item.key<<NR>>MACRO_NAME<<NR>>PROMPT_TEXT[;PROMPT_TEXT]<<NR>>MACRO_BODY
                //e.g. t24.macro.system.1<<NR>>CACHE.READ<<NR>>Filename<<NR>>CALL CACHE.READ('F.<<1>>',<<1>>.ID,R.<<1>>,YERR)
                String macroData = (String)props.get(key);
                String[] macroElements = macroData.split(mu.getProperty("t24.key.record.separator"));
                
                Macro macro = new Macro();
                macro.setKey(macroElements[0]);
                macro.setName(macroElements[1]);
                macro.setBody(macroElements[3]);
                
                String[] questions;
                if("".equals(macroElements[2])){
                    questions = new String[0];
                } else {
                    questions = macroElements[2].split(";");
                }
                macro.setQuestions(questions);              

                // Create an item and add it to the array List
                IT24ViewItem item = new T24ViewItem(macro.getName(), null, macroCategory, (Object)macro);
                itemsAList.add(item);         
            }
        }
        // Sort the array list with the macros; this will group the macros
        // by category: either User or System macros.
        Collections.sort(itemsAList, new MacroComparator());
        
        // Transform ArrayList => to IT24ViewItem[]
        int arraySize = itemsAList.size();
        IT24ViewItem[] items = new IT24ViewItem[arraySize];
        return ((IT24ViewItem[]) itemsAList.toArray(items));
    }
    
    /**
     * Internal class used to sort the list of macros.
     */
     private class MacroComparator implements Comparator<IT24ViewItem>{
        public int compare(IT24ViewItem first, IT24ViewItem second){
            
            if(T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM.equals(first.getCategory()) &&  
               T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM.equals(second.getCategory())){
                return first.getLabel().compareToIgnoreCase(second.getLabel());
                
            } else if(T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM.equals(first.getCategory()) &&  
                      T24_VIEW_ITEM_CATEGORY.MACRO_USER_ITEM.equals(second.getCategory())){
                return -1;    
                
            } else if(T24_VIEW_ITEM_CATEGORY.MACRO_USER_ITEM.equals(first.getCategory()) &&  
                    T24_VIEW_ITEM_CATEGORY.MACRO_SYSTEM_ITEM.equals(second.getCategory())){                
                return 1;
                
            } else {
                // They are both MACRO_USER_ITEM
                return first.getLabel().compareToIgnoreCase(second.getLabel()); 
            } 
        }
    }        
}
