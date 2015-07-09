package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

/**
 * Class that models a macro entity. It's just a data holder. 
 */
public class Macro {
    private String key;
    private String[] questions;
    private String name;
    private String body;
    
    public void setKey(String key){
        this.key = key;        
    }
    public void setQuestions(String[] questions){
        this.questions = questions;        
    }
    public void setName(String name){
        this.name = name;        
    }
    public void setBody(String body){
        this.body = body;        
    }
    public String getKey(){
        return this.key;
    }
    public String[] getQuestions(){
        return this.questions;
    }
    public String getName(){
        return this.name;
    }
    public String getBody(){
        return this.body;
    }
}
