package com.temenos.t24.tools.eclipse.basic.actions.remote;

/**
 * Simple transfer object.
 * Based on the <a href="http://java.sun.com/blueprints/corej2eepatterns/Patterns/TransferObject.html">Transfer Object</a>
 * Used internally to facilitate holding and transferring signing on data among classes.  
 *  
 */
public class SignOnData {
    
    public static String username;
    public static String firstPswd;
    public static String secondPswd;
    public static String channel;
    public static boolean rememberLogin;
}
