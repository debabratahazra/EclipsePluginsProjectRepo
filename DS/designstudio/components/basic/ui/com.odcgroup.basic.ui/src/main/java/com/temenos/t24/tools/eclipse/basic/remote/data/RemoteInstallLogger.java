package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * This class is responsible for generating logging details for source and data items transferred from T24.<br>
 * 
 *  * @author yasar
 * 
 */
public class RemoteInstallLogger {

    private static RemoteInstallLogger INSTANCE = new RemoteInstallLogger();
    
    private RemoteInstallLogger() {
    }

    public static RemoteInstallLogger getInstance() {
        return INSTANCE;
    }
    /**
     * Generates the log file under C:\Workspace\runtime-EclipseApplication\TempT24BasicIDEProj\Logger folder.
     * The generated log file name will be the current system time. e.g;  200909131015.log  
     * Log File Details
     * ***************
     * site=10.92.5.15
	   user=username
		<source>
		AM.CRITERIA
		AM.ENTITY
		<data>
		F.FILE.CONTROL>BV.ACCT.ACTIVITY
		F.STANDARD.SELECTION>BV.ACCT.ACTIVITY
		
     * @param transferredSourceFiles
     * @param transferredDataFiles
     * @param siteName
     */
    public void generateInfoFile(List<String> transferredSourceFiles,List<String> transferredDataFiles, String siteName){
    	
    	if(transferredSourceFiles==null && transferredDataFiles==null){
    		return;
    	}
    	String localPath = getLocalPath();
        File directory = new File(localPath);
           try {
            directory.mkdirs();
        } catch (SecurityException e) {
            RemoteOperationsLog.error("Unable to create Logger directory");
            return;
        }
        String fileName =getFileName() +".log";
        String filePath = localPath + "/" + fileName;
             File file = new File(filePath);
             boolean isExists = false;
             String logContents="";
             BufferedWriter out = null;
             try {
                 isExists = file.exists();
                 if (!isExists) {
                     isExists = file.createNewFile();
                 }
                 out = new BufferedWriter(new FileWriter(file));
                 logContents=constructLogContents(transferredSourceFiles,transferredDataFiles,siteName);
                 if (out != null) {
                     out.write(logContents);
                     out.flush();
                 }
                 }catch (FileNotFoundException e) {
				RemoteOperationsLog.error("File "+fileName+ " does not exist");
				return;
			}             
             catch (IOException e) {
            	 RemoteOperationsLog.error("Unable to create file " +fileName +" under "+ file.getAbsolutePath());
            	 return;
            }	finally {
                try {
                    if (out != null)
                        out.close();
                } catch (Exception e) {
                }
            }
            // Refresh the folder
            EditorDocumentUtil.getIFile(filePath);
    }
    
    /**
     * constructs the filename using Calendar class following yyyyMMddhhmm pattern
     * @return
     */
    private String getFileName(){
    	Calendar cal = Calendar.getInstance();
    	String fileName="";
    	int year = cal.get(Calendar.YEAR);
    	int month =cal.get(Calendar.MONTH)+1;
    	int day = cal.get(Calendar.DAY_OF_MONTH);
    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	int minute = cal.get(Calendar.MINUTE);
    	int second = cal.get(Calendar.SECOND);
    	fileName = ""+year+format(month)+format(day)+format(hour)+format(minute)+format(second);
    	return fileName;
	
    }
    /**
     * formats the item. If the item is between 1-9 range it adds 0 before the item. 
     * e.g; If the item value is 7, this method will return 07.    
     * @param item
     * @return
     */
    
    private String format(int item){
    	String toFormat="";
    	toFormat= item+"";
    	if(toFormat.length()==1){
    		return "0"+toFormat;
    	}
    	return toFormat;
    }
    
    /**
     * constructs the contents for log file
     * @param transferredSourceFiles
     * @param transferredDataFiles
     * @param siteName
     * @return
     */
    
    private String constructLogContents(List<String> transferredSourceFiles,List<String> transferredDataFiles,String siteName){
    	String fileContents ="";
    	RemoteSite site = RemoteSitesManager.getInstance().getRemoteSiteFromName(siteName);
    	String hostIp = site.getHostIP();
    	String user = site.getUserName();
    	fileContents = "site="+hostIp+"\n"+"user="+user+"\n";
    	fileContents += "<source>\n";
    	if(transferredSourceFiles!=null && transferredSourceFiles.size()>0){
    		fileContents += RemoteInstallerHelper.buildString(transferredSourceFiles).replaceAll(" ", "\n");
    	}
    	fileContents += "\n<data>\n";
    	if(transferredDataFiles!=null && transferredDataFiles.size()>0){
    		fileContents += RemoteInstallerHelper.buildString(transferredDataFiles).replaceAll(" ", "\n");
    	}
    	
    	return fileContents;
    }
    
    /**
     * returns the Logger folder path .e.g; C:\Workspace\runtime-EclipseApplication\TempT24BasicIDEProj\Logger folder.
     * @return localPath
     */
    private String getLocalPath() {
        String localPath = null;
        localPath = EclipseUtil.getTemporaryProject().toOSString() + "/" + PluginConstants.LOG_FILE_DIRECTORY;
        return localPath;
    }


}
