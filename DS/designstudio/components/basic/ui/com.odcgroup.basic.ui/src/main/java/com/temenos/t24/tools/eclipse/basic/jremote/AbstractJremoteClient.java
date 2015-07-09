package com.temenos.t24.tools.eclipse.basic.jremote;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.jbase.jremote.DefaultJConnectionFactory;
import com.jbase.jremote.JConnection;
import com.jbase.jremote.JRemoteException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;

/**
 * Implements the connectivity related operations from the
 * {@link IJremoteClient}.
 * 
 * @author ssethupathi
 * 
 */
public abstract class AbstractJremoteClient implements IJremoteClient {

    protected JConnection connection;
    private boolean isConnected = false;

    /**
     * Returns the {@link RemoteSite} corresponding to this remote client.
     * 
     * @return
     */
    protected abstract RemoteSite getRemoteSite();

    /**
     * {@inheritDoc}
     */
    public boolean connect() throws NumberFormatException {
        isConnected = false;
        RemoteSite remoteSite = getRemoteSite();
        int jagentPort = -1;
        try {
            jagentPort = Integer.parseInt(remoteSite.getPortNumber());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid Port number : " + e.getMessage());
        }
        if (remoteSite.getConnectionType().equals(RemoteConnectionType.JCA) && jagentPort > 0) {
            DefaultJConnectionFactory connectionFactory = new DefaultJConnectionFactory();
            connectionFactory.setHost(remoteSite.getHostIP());
            connectionFactory.setPort(jagentPort);
            try {
                connection = connectionFactory.getConnection(remoteSite.getUserName(), remoteSite.getPassword());
                isConnected = true;
                configureTerminal();
                return isConnected;
            } catch (JRemoteException e) {
                RemoteOperationsLog.error("Unable to connect to jagent in host " + remoteSite.getHostIP() + " port " + jagentPort);
                return false;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * {@inheritDoc}
     */
    public void disconnect() {
        try {
            connection.close();
        } catch (JRemoteException e) {
            // Do nothing
        }
    }

    /**
     * Sets the LogConsole for the JConnection's terminal output.
     */
    // TODO - to be changed to use a dedicated debug console. Exists until such
    // time.
    private void configureTerminal() {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(LogConsole.getT24BasicConsole().getOutputStream(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        connection.setTerminalOutputWriter(writer);
    }
}
