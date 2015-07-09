package com.temenos.t24.tools.eclipse.basic.protocols;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.eclipse.jface.preference.IPreferenceStore;

import com.google.common.io.Closeables;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

public class T24HttpProtocolManagerThread implements Runnable{
    private URL url                   = null;
    private HttpURLConnection urlConn = null;
    private OutputStreamWriter out    = null;
    private InputStream is            = null;
    
    private Command  cmd              = null;
    private Response res              = null; // Response for this object client.
    private T24HttpProtocolManager httpMgr = null;
   
    public T24HttpProtocolManagerThread(T24HttpProtocolManager httpMgr, Command cmd){
        this.cmd     = cmd;
        this.httpMgr = httpMgr;
    }
    
    /**
     * Sends an XML command over HTTP protocol using POST, to Browser application. The message 
     * embedded in POST is retrieved from the Command passed. After the message has been sent, 
     * a response is read through the InputStream.
     * A URLConnection object is used to send/received over HTTP. 
     */
    public void run(){
        IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
        String millisString  = store.getString(PluginConstants.T24_REMOTE_TIMEOUT_MILLIS);
        int millis = ProtocolConstants.HTTP_DEFAULT_TIMEOUT_MILLIS; 
        if(millisString != null){
            // The timeout was set in the properties file, so use that value instead of the default one.
            millis = Integer.parseInt(millisString);
        }
        String urlString = ProtocolUtil.buildUrlString();
        res = new Response(); // Response object
        try {
            url = new URL(urlString);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setUseCaches(false);
            urlConn.setReadTimeout(millis);
            urlConn.setDoOutput(true); // Use POST instead of GET
            urlConn.setUseCaches(false);            
            out = new OutputStreamWriter(urlConn.getOutputStream());
            out.write(cmd.getBody());
            out.flush();
            is = urlConn.getInputStream();
            String response = readResponse(is);
            res.setObject((String)response);
            res.setPassed(true);
        } catch (ConnectException e){
            res.setPassed(false);
            res.setRespMessage(ProtocolConstants.MESSAGE_FAIL_HTTP_CONNECTION+
                               "\nURL = "+urlString);
            res.setObject((String)e.toString());
        } catch (SocketTimeoutException e){
            res.setPassed(false);
            res.setRespMessage(ProtocolConstants.MESSAGE_READ_TIMEOUT_HTTP_CONNECTION+
                               "\nURL = "+urlString);
            res.setObject((String)e.toString());
        } catch (IOException e) {
            res.setPassed(false);
            res.setRespMessage(ProtocolConstants.MESSAGE_FAIL_HTTP_CONNECTION+
                                "\nURL = "+urlString);
            res.setObject((String)e.toString());
        } finally {
            closeResources();
            httpMgr.httpFinished(res);
        }
    }

    
    /**
     * @param is - InputStream provided by a URLConnection. It is through this InputStream
     * that data is read from the HTTP server
     * @return String returned from the HTTP server
     */
    public String readResponse(InputStream is) {
        StringBuffer sb = new StringBuffer();
        InputStream buffer = null;
        try {
            buffer = new BufferedInputStream(is);
            Reader r = new InputStreamReader(buffer, "ASCII");
            int c;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Closeables.closeQuietly(buffer);
        }
        return sb.toString();
    }
    
    /**
     * Closes explicitly the resources used.
     */
    public void closeResources(){
        try {
            if (out != null) {out.flush(); out.close();};
            if (is != null) is.close();
            if(urlConn != null) urlConn.disconnect();
            url = null;
            urlConn = null;            
        } catch (IOException e) {}
    }
    
    /*
     * Displays on the console the time differences between now and start
     * plus a message. I.e. (now -start) + msg
     */
    private void printTime(long start, String msg){
        long end   = System.currentTimeMillis();
        System.out.println(end - start+" "+msg);
    }    
    
}
