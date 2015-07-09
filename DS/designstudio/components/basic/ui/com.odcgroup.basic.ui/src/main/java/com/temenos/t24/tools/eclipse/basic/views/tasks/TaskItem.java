package com.temenos.t24.tools.eclipse.basic.views.tasks;

import org.eclipse.jface.text.Position;

import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;


public class TaskItem implements IT24ViewItem {
    private String description;  // Task description
    private String message;      // Associated message (if any)
    private int line = -1;       // Line (if any) in associated document where something should be done.
                                 // Note: if no line is available, this value should be -1 
    private Object data;         // Additional data (if any)
    private Position position;   // Position (if any) in associated document where something should be done.
    private T24_VIEW_ITEM_CATEGORY category;     // Category (if any)
    private String label;        // Label (if any)
    
    private String fullPath; 
    private String filename;
    
    // true => item linked to a local file (in file system), 
    // false => item linked to a remote server file.
    private boolean isLocal;

    public TaskItem(String description, String message, int line){
        this.description = description;
        this.message     = message;
        this.line        = line;
    }

    public boolean isLocal() {
        return isLocal;
    }
    
    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }
    
    public void setDescription(String description){
        this.description = description;
    }    
    public void setMessage(String message){
        this.message = message;
    }        
    public void setLine(int line){
        this.line = line;
    }            
    public String getDescription(){
        return this.description;
    }    
    public String getMessage(){
        return this.message;
    }        
    public int getLine(){
        return this.line;
    }       
    
    public T24_VIEW_ITEM_CATEGORY getCategory() {
        return this.category;
    }
    public void setCategory(T24_VIEW_ITEM_CATEGORY category){
        this.category = category;
    }

    public Object getData() {
        return this.data;
    }
    public void setData(Object data){
        this.data = data;
    }

    public String getLabel() {
        return this.label;
    }

    public int getLength() {
        return this.position.getLength();
    }

    public int getOffset() {
        return this.position.getOffset();
    }

    public Position getPosition() {
        return this.position;
    }
    
    public void setFullPath(String fullPath){
        this.fullPath= fullPath; 
    }
    public String getFullPath(){
        return this.fullPath;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
    public String getFilename(){
        return this.filename;
    }
    public int compareTo(IT24ViewItem o) {
        return 0;
    }
    public String toString(){
        return this.category+" "+this.description+" "+this.message+" "+this.line;
    }
        
}
