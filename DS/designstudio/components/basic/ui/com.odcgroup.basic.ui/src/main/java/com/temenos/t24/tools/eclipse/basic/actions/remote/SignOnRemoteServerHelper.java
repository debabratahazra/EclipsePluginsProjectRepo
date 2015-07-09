package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.SignOnRemoteServerDialog;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredSignOn;

/**
 * This is a helper class.
 */
public class SignOnRemoteServerHelper {

   /**
    * Main method.
    * @return response.
    */ 
   public Response runSignOn() {
       Response response = new Response();       
       IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

       /** Open dialog to enter login details */
       SignOnRemoteServerDialog dialog = new SignOnRemoteServerDialog(window.getShell());       
       if (dialog.open() != InputDialog.OK) {
           return null;
       }
       
       /** Add all dialog info to Transfer object */
       populateTransferObject(dialog);
       
       response = signOn(dialog.getUsername(), dialog.getPassword(), dialog.getChannel(), dialog.getRememberLogin());
       
       return response;
    }   
   
   
   public void populateTransferObject(SignOnRemoteServerDialog dialog) {
       /* Store info in transfer object */
       SignOnData.username = dialog.getUsername();
       SignOnData.firstPswd = dialog.getPassword();
       SignOnData.secondPswd = "";
       SignOnData.rememberLogin = dialog.getRememberLogin();
       SignOnData.channel = dialog.getChannel();       
   }
   
   
   /**
    * Contains the core signing on business logic. Sends the sign on command down 
    * to the server and processes the response.
    * @param username 
    * @param password
    * @param channel - Browser channel, as in channels.xml config file
    * @param rememberLogin - boolean
    */
   public Response signOn(final String username, final String password, final String channel, boolean rememberLogin) {
       // Store the channel name. It'll be used in future commands sent down to Browser
       ProtocolUtil pu = new ProtocolUtil();
       pu.setCurrentChannel(channel);     
          
       MonitoredSignOn monitoredSignOn = new MonitoredSignOn();
       return monitoredSignOn.execute(username, password, channel);

    }    
}
