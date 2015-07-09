package com.temenos.t24.tools.eclipse.basic.wizards.t24unit;

import java.util.Map;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;



/** This class is responsible for code creation for TUnit Test */
public class T24UnitCodeCreator {

    private static T24UnitCodeCreator codeCreator = null;
    private static Map<String, String> templateProperties = null;
  
    
    
    private T24UnitCodeCreator() {
        loadTemplateProperties();
    }
    
    public static T24UnitCodeCreator getInstance() {
        if (codeCreator == null) {
            codeCreator = new T24UnitCodeCreator();
        }
        return codeCreator;
    }
    
    /**
     * Method called by the IWizard to generate code. This method generates code
     * for TUnitTest.
     * 
     */
     
    public void generateCode(String projectFullPath, String testFileName) {
        String t24UnitTestFileContent = templateProperties.get(PluginConstants.T24_UNITTEST_TEMPLATE);
        t24UnitTestFileContent = substituteData(t24UnitTestFileContent, "TESTFILENAME",testFileName);
        createFile(projectFullPath,testFileName,t24UnitTestFileContent);
    }
    
    
    
    /**
     * Substitutes the passed parameter values to the template
     * 
     * @param fileContent File content
     * @param type type of the value to be substituted
     * @param value value from parameter to substitute
     */
    private static String substituteData(String fileContent, String type, String value) {
        String result = "";
        type = "<<" + type + ">>";
        try {
            result = fileContent.replaceAll(type, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Loads the properties file which contains the template code for TUnit Test
     */
    private static void loadTemplateProperties() {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        templateProperties = mu.loadProperties();
    }
    
    
    /**
     * Creates file for the file name and content provided in the current
     * project
     * 
     * @param fileName Name of the file
     * @param fileContent Content of the file
     * 
     */
    private void createFile(String projectFullPath,String fileName, String fileContent) {
        String filePath = projectFullPath + "\\" + fileName + ".b";
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.existFile(filePath)) {
            String errMessage = "File " + fileName + " already exists. Are you sure you want to overwrite?";
            T24MessageDialog dialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "File Exists", errMessage, MessageDialog.WARNING);
            if (dialog.open() != InputDialog.OK) {
                return ;
            }
        }
        int status = fileUtil.saveToFile(fileContent, filePath, true);
        if (status < 0) {
            T24MessageDialog errorDialog = new T24MessageDialog(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error Dialog",
                    "Failed to Create the File "+fileName, MessageDialog.ERROR);
            errorDialog.open();
            return;
        }
       EditorDocumentUtil.getIFile(filePath);
      
    }
}