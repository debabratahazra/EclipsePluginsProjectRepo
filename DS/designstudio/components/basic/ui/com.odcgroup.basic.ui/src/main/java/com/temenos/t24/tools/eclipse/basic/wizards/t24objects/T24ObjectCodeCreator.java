package com.temenos.t24.tools.eclipse.basic.wizards.t24objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;

/** This class is responsible for code creation for T24objects */
public class T24ObjectCodeCreator {

    private static T24ObjectCodeCreator codeCreator = null;
    private T24Object t24Object = null;
    private T24Method t24Method = null;
    private List<T24Method> t24Methods = null;
    private static Map<String, String> templateProperties = null;
    private static String t24ObjectContent = "";
    private static String t24MethodContent = "";
    private List<String> failedItems = new ArrayList<String>();
    private static String projectFullPath = null;

    private T24ObjectCodeCreator() {
        loadTemplateProperties();
    }

    public static T24ObjectCodeCreator getInstance() {
        if (codeCreator == null) {
            codeCreator = new T24ObjectCodeCreator();
        }
        return codeCreator;
    }

    /**
     * Method called by the IWizard to generate code. This method generates code
     * for T24object and methods.
     * 
     * @return message Error message if any.
     */
    public void generateCode(T24Object t24Object, String projectPath) {
        this.t24Object = t24Object;
        projectFullPath = projectPath;
        t24Methods = t24Object.getT24Methods();
        if (t24Object.isFunctionSelected()) {
            createT24Object();
        }
        createT24Methods();
        if (!failedItems.isEmpty()) {
            // There are items which are failed during creation
            String failureMessage = "The following items are not created:";
            String func = "";
            int failedItemsSize = failedItems.size();
            for (int failedItemsCount = 0; failedItemsCount < failedItemsSize; failedItemsCount++) {
                func = failedItems.get(failedItemsCount);
                failureMessage += "\r\n" + func;
            }
            T24MessageDialog dialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    " Failed to create", failureMessage, MessageDialog.INFORMATION);
            dialog.open();
        }
    }

    /**
     * Creates T24object file
     * 
     * @return true if successfully created. false otherwise.
     */
    private void createT24Object() {
        String t24ObjectFileName = "I_" + t24Object.getName();
        t24ObjectContent = templateProperties.get(PluginConstants.T24_OBJECT_TEMPLATE);
        t24ObjectContent = substituteData(t24ObjectContent, "OBJ_DESCRIPTION", formatComments(t24Object.getComments()));
        t24ObjectContent = substituteData(t24ObjectContent, "OBJ_LINK", "*@link ");
        t24ObjectContent = substituteData(t24ObjectContent, "OBJ_PACKAGE", "*@package " + t24Object.getPackageName());
        t24ObjectContent = substituteData(t24ObjectContent, "OBJ_AUTHOR", "*@author " + t24Object.getAuthorName());
        t24ObjectContent = substituteData(t24ObjectContent, "OBJ_DEFFUN", getT24MethodDefintions());
        if (!createFile(t24ObjectFileName, t24ObjectContent)) {
            failedItems.add(t24ObjectFileName);
        }
    }

    /**
     * Creates files for T24 Methods
     * 
     */
    private void createT24Methods() {
        String t24MethodTemplateContent = templateProperties.get(PluginConstants.T24_METHOD_TEMPLATE);
        int t24MethodsSize = t24Methods.size();
        for (int t24MethodCount = 0; t24MethodCount < t24MethodsSize; t24MethodCount++) {
            t24Method = t24Methods.get(t24MethodCount);
            if (!createT24Method(t24MethodTemplateContent)) {
                failedItems.add(t24Method.getT24MethodName());
            }
        }
    }

    private boolean createT24Method(String t24MethodTemplateContent) {
        t24MethodContent = t24MethodTemplateContent;
        List<String> arguments = t24Method.getArguments();
        if (t24Object.isFunctionSelected()) {
            t24MethodContent = substituteData(t24MethodContent, "MTH", "FUNCTION " + t24Object.getName() + "."
                    + t24Method.getT24MethodName() + substituteArguments());
        } else {
            t24MethodContent = substituteData(t24MethodContent, "MTH", "SUBROUTINE " + t24Object.getName() + "."
                    + t24Method.getT24MethodName() + substituteArguments());
        }
        t24MethodContent = substituteData(t24MethodContent, "MTH_DESC", formatComments(t24Method.getDescription()));
        t24MethodContent = substituteData(t24MethodContent, "MTH_PARAM", getT24MethodParameters(arguments));
        if (t24Object.isFunctionSelected()) {
            t24MethodContent = substituteData(t24MethodContent, "MTH_RETURN", "*@return " + t24Method.getReturnValue());
        } else {
            t24MethodContent = substituteData(t24MethodContent, "MTH_RETURN", "");
        }
        t24MethodContent = substituteData(t24MethodContent, "MTH_LINK", "*@link ");
        t24MethodContent = substituteData(t24MethodContent, "MTH_PACKAGE", "*@package " + t24Object.getPackageName());
        t24MethodContent = substituteData(t24MethodContent, "MTH_AUTHOR", "*@author " + t24Object.getAuthorName());
        if (t24Object.isFunctionSelected()) {
            t24MethodContent = substituteData(t24MethodContent, "MTH_INSERT", "\\$INSERT I_" + t24Object.getName());
        } else {
            t24MethodContent = substituteData(t24MethodContent, "MTH_INSERT", "");
        }
        t24MethodContent = substituteData(t24MethodContent, "MTH_FINAL_RETURN", t24Method.getReturnValue());
        return createFile(t24Object.getName() + "." + t24Method.getT24MethodName(), t24MethodContent);
    }

    /**
     * Loads the properties file which contains the template code for T24object
     * and T24 Method
     */
    private static void loadTemplateProperties() {
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        templateProperties = mu.loadProperties();
    }

    /**
     * Substitutes the values from T24objects to the template
     * 
     * @param fileContent File content
     * @param type type of the value to be substituted
     * @param value value from T24object to substitute
     */
    private static String substituteData(String fileContent, String type, String value) {
        String result = "";
        type = "<<" + type + ">>";
        try {
            result = fileContent.replaceFirst(type, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Returns the T24 Method definition part of the T24object code
     * 
     * @return T24 Method definitions
     */
    private String getT24MethodDefintions() {
        T24Method func = null;
        String t24MethodDefintions = "";
        int t24MethodSize = t24Methods.size();
        int arrayCounter = 0;
        while (arrayCounter < t24MethodSize) {
            func = t24Methods.get(arrayCounter);
            t24MethodDefintions += "\tDEFFUN " + t24Object.getName() + "." + func.getT24MethodName() + "()\r\n";
            arrayCounter++;
        }
        return t24MethodDefintions;
    }

    /**
     * Returns the arguments list to be used in T24 Method signature. Ex.,
     * (arg1,arg2,arg3)
     * 
     * @return string arguments
     */
    public String substituteArguments() {
        String arguments = "";
        String arg = null;
        List<String> argList = t24Method.getArguments();
        if (argList.get(0).length() <= 0)
            return "";
        int argSize = argList.size();
        for (int argIndex = 0; argIndex < argSize - 1; argIndex++) {
            arg = argList.get(argIndex);
            arguments += arg + ",";
        }
        return "(" + arguments + argList.get(argSize - 1) + ")";
    }

    /**
     * Gets the T24 Method parameters
     * 
     * @param List of String arguments
     * @return "@param" parameters with modifications
     */
    private static String getT24MethodParameters(List<String> parameters) {
        String t24MethodParameters = "";
        String parameter = "";
        int paramSize = parameters.size();
        for (int paramIndex = 0; paramIndex < paramSize; paramIndex++) {
            parameter = parameters.get(paramIndex);
            t24MethodParameters += "*@param " + parameter + "\r\n";
        }
        return t24MethodParameters;
    }

    /**
     * Formats the comments
     * 
     * @param comments as entered
     * @return comments formatted
     */
    private String formatComments(String comments) {
        if (comments != null && comments.length() > 0) {
            String[] commentLines = comments.split("\\r\\n");
            comments = "";
            for (String line : commentLines) {
                comments += "\r\n*" + line;
            }
            return comments + "\r\n";
        }
        return "";
    }

    /**
     * Creates file for the file name and content provided in the current
     * project
     * 
     * @param fileName Name of the file
     * @param fileContent Content of the file
     * @return true, if successfully created. false otherwise.
     */
    private boolean createFile(String fileName, String fileContent) {
        String filePath = projectFullPath + "\\" + fileName + ".b";
        FileUtil fileUtil = new FileUtil();
        if (fileUtil.existFile(filePath)) {
            String errMessage = "File " + fileName + " already exists. Are you sure you want to overwrite?";
            T24MessageDialog dialog = new T24MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                    "File Exists", errMessage, MessageDialog.ERROR);
            if (dialog.open() != IDialogConstants.OK_ID) {
                return false;
            }
        }
        int status = fileUtil.saveToFile(fileContent, filePath, true);
        if (status < 0) {
            return false;
        }
        // It refreshes the created resource so that the new file would be
        // immediately visible in the views.
        EditorDocumentUtil.getIFile(filePath);
        return true;
    }
}
