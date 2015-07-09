package com.temenos.t24.tools.eclipse.basic.protocols;

public class Command {
    private String body;
    private String commandName;
    
    public Command(String command){
        this.commandName = command;
    }
    
    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return this.body;
    }
    public String getCommandName(){
        return this.commandName;
    }
}