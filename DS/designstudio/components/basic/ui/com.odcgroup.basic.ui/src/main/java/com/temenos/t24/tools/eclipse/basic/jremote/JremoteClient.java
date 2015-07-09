package com.temenos.t24.tools.eclipse.basic.jremote;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JExecuteResults;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

/**
 * Implements the {@link IRemoteAction} specific actions from the
 * {@link IJremoteClient}.
 * 
 * @author ssethupathi
 * 
 */
public class JremoteClient extends AbstractJremoteClient {

    private RemoteSite remoteSite;

    /**
     * Instantiates jremote client
     * 
     * @param remoteSite
     */
    public JremoteClient(RemoteSite remoteSite) {
        this.remoteSite = remoteSite;
    }

    /**
     * {@inheritDoc}
     */
    public String compile(String directory, String file) throws JRemoteException, NumberFormatException {
        String command = PluginConstants.T24SUBROUTINE_COMPILE_SOURCE + " " + directory + " " + file;
        String compileOption = getCompileOption();
        if (compileOption != null) {
            command = command + " " + compileOption;
        }
        try {
            connect();
            JExecuteResults results = connection.execute(command);
            JDynArray arr = results.getCapturingVar();
            String result = "";
            int linesCount = arr.getNumberOfAttributes();
            for (int line = 1; line < linesCount; line++) {
                result += arr.get(line) + "\n";
            }
            //Array size will be one when DS is not registered.
            result = (linesCount==1)? arr.toString() : result; 
            return result;
        } catch (JRemoteException e) {
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    public JSubroutineParameters run(String subroutine, JSubroutineParameters params) throws JSubroutineNotFoundException,
            JRemoteException, NumberFormatException {
        connect();
        if (connection == null) {
            return null;
        }
        try {
            JSubroutineParameters returnParams = connection.call(subroutine, params);
            return returnParams;
        } catch (JSubroutineNotFoundException e) {
            throw e;
        } catch (JRemoteException e) {
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     */
    protected RemoteSite getRemoteSite() {
        return remoteSite;
    }

    private String getCompileOption() {
        String compileOption = "-D";
        String compileWithDebugProperty = EclipseUtil.getPreferenceStore().getString(PluginConstants.T24_COMPILE_WITH_DEBUG);
        boolean compileWithDebug = Boolean.parseBoolean(compileWithDebugProperty);
        if (!compileWithDebug) {
            compileOption = null;
        }
        return compileOption;
    }
}
