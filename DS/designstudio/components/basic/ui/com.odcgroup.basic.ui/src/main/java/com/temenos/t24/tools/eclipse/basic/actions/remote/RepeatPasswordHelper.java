package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.jface.dialogs.InputDialog;

import com.temenos.t24.tools.eclipse.basic.dialogs.RepeatPasswordDialog;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredRepeatPassword;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * This is a helper class.
 * Class to guide the repeat password process.
 * Passwords may need to be repeated in a number of cases,
 * e.g. new user or password expire.
 * 
 */
public class RepeatPasswordHelper {
    private LogConsole log = LogConsole.getT24BasicConsole();    
    
    /**
     * Main processing method.
     * @param processType - e.g. ProtocolConstants.RESPONSE_TYPE_REPEAT_PASSWORD
     * @param username
     * @param password: this is the original password entered by the user when
     * he/she tried to log in for first time.          
     */
    public Response runRepeatPasswordProcess(String processType, String username, String password) {
        Response response = new Response();
        RepeatPasswordDialog dialog = new RepeatPasswordDialog(EclipseUtil.getActiveWindow().getShell(), password);

        String validationMessage = "";
        do {
            if (dialog.open() != InputDialog.OK){
                // The user clicked something else other than OK. The execution is finished.
                response.setPassed(false);
                response.setRespMessage("User cancelled operation");
                return response;

            } else {
                // The user clicked OK. Now validate inputs.
                validationMessage = validateInputs(dialog);
                if(!"".equals(validationMessage)) {
                    log.logMessage(validationMessage);
                    dialog.setMessage(validationMessage);
                    /** Set passwords to their original values */
                    dialog.resetPasswords();
                    
                } 
            }
        } while(!StringUtil.isEmpty(validationMessage));

        // This line is reached if user clicked OK and input validation passed.
        return sendRepeatPasswordToServer(processType, username, dialog.getFirstPassword(), dialog.getSecondPassword());

    }
    
    
    /**
     * Validate repeat password dialog inputs.
     * @param dialog
     * @return error message String. If no error (i.e. all validations passed) it'll return "" 
     */
    private String validateInputs(RepeatPasswordDialog dialog) {
        StringUtil su = new StringUtil();
        if (su.atLeastOneEmpty(new String[]{dialog.getFirstPassword(), dialog.getSecondPassword()})) {
            return "Error. At least one input parameter is empty.";
            
        } else if (!dialog.getFirstPassword().equals(dialog.getSecondPassword())) {
            return "Error. Passwords are not identical.";

        }
        
        /** validation passed */
        return "";
            
    }
    
    private Response sendRepeatPasswordToServer(final String processType, final String username, final String firstPswd, final String secondPswd) {
        /** 
         * This is a repeat password process, it is assumed that the channel the user is trying 
         * to connect has already been set previously.
         */
        ProtocolUtil pu = new ProtocolUtil();
        MonitoredRepeatPassword monitoredRepeatPswd = new MonitoredRepeatPassword();
        return monitoredRepeatPswd.execute(processType, username, firstPswd, secondPswd, pu.getCurrentChannel());
        
    }
}
