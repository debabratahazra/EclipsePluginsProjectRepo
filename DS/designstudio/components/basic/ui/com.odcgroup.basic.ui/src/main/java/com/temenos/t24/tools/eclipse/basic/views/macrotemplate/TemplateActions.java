package com.temenos.t24.tools.eclipse.basic.views.macrotemplate;

import java.util.regex.Matcher;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.templates.TemplateCreateDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.templates.TemplateOpenDialog;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtil;
import com.temenos.t24.tools.eclipse.basic.utils.MementoUtilFactory;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

public class TemplateActions {

    private LogConsole log = LogConsole.getT24BasicConsole();
    private IWorkbenchWindow window;

    public TemplateActions() {
        this.window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }

    /***************************************************************************
     * EDIT TEMPLATE Opens an editor with the details of the template selected,
     * so they can be updated.
     */
    public void editTemplate(IT24ViewItem templateSelected, String projectFullPath) {
        if (templateSelected != null) {
            // OPEN THE SELECTED TEMPLATE
            // This will allow the user to change it.
            // Get the relevant template information.
            Template temp = (Template) templateSelected.getData();
            String templateName = temp.getName();
            String templateBody = temp.getBody();
            // SAVE TEMPLATE TO LOCAL WORKSPACE
            saveToLocalWorkspace(templateName, templateBody,projectFullPath);
            // OPEN TEMPLATE IN EDITOR
            String serverDir = null;
            EditorDocumentUtil.openFileWithEditor(projectFullPath, templateName, serverDir, true);
            // DISPLAY EDITTING TEMPLATE MESSAGE
            String message = "Editing a Template.\n" + "Once the changes have been made, please click on the \"Create\" button.\n"
                    + "If the same name is given then the Template will be overwritten, otherwise\n"
                    + "a new Template will be created.";
            String dialogTitle = "  " + templateSelected.getLabel();
            T24MessageDialog dialog = new T24MessageDialog(window.getShell(), dialogTitle, message, MessageDialog.INFORMATION);
            if (dialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK. The
                // execution is finished.
                return;
            }
        }
    }

    /***************************************************************************
     * OPEN TEMPLATE
     * 
     * @param selection - template selected will be opened with an editor.
     */
    public void openTemplate(IT24ViewItem templateSelected, String projectFullPath ) {
        if (templateSelected != null) {
            // Get the relevant template information.
            Template temp = (Template) templateSelected.getData();
            String templateName = temp.getName();
            String templateBody = temp.getBody();
            TemplateOpenDialog dialog = new TemplateOpenDialog(window.getShell());
            if (dialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK. The
                // execution is finished.
                return;
            }
            String basicProgramName = dialog.getModulename();
            String prefix = dialog.getPrefix();
            // SUBSTITUTE TEMPLATE PARAMETERS (NAME, PREFIX)
            templateBody = substituteParameters(templateBody, basicProgramName, prefix);
            // SAVE TEMPLATE TO LOCAL WORKSPACE
            String fileName = getFileName(templateName, basicProgramName, prefix);
            saveToLocalWorkspace(fileName, templateBody,projectFullPath);
            // OPEN TEMPLATE IN EDITOR
            EditorDocumentUtil.openFileWithEditor(projectFullPath, fileName, null, true);
        }
    }

    /**
     * Gets the file name from the module name entered and prefix. Ex., if the
     * template chosen is TEMPLATE.FIELDS, <<prefix>>.<<moduleName>>.FILEDS
     * will be the file name
     * 
     * @param templateName name of the template Eg., TEMPLATE.FIELDS
     * @param moduleName name of the module Eg., TELLER
     * @param prefix prefix ex., TT
     * @return file name eg., TT.TELLER.FIELDS
     */
    private String getFileName(String templateName, String moduleName, String prefix) {
        String fileName = moduleName;
        if (prefix != null && prefix.length() <= 0) {
            prefix = "XX";
        }
        if (templateName.startsWith("TEMPLATE")) {
            fileName = prefix + "." + templateName.replaceFirst("TEMPLATE", moduleName);
        }else if (templateName.startsWith("XX") && !prefix.equals("XX")) {
            fileName = templateName.replaceFirst("XX", prefix);
        }else if(templateName.startsWith("Table")){
        	fileName = prefix + "." + templateName.replaceFirst("Table", moduleName);
        }else{
        	fileName = prefix + "." + moduleName;
        }
        return fileName;
    }

    /**
     * Substitutes the passed parameters (name and prefix) in the template body.
     * 
     * @param templateBody - full text of the template
     * @param templateName - original template name, e.g. TEMPLATE.W
     * @param name - name that the user wants to apply to the template, e.g
     *            ACCOUNT.BALANCE
     * @param prefix - used to substitute '<<PREFIX>>' sequences within the
     *            body
     * @return the template with the parameters substituted.
     */
    private String substituteParameters(String templateBody, String basicModuleName, String prefix) {
        if (!"".equals(basicModuleName)) {
            templateBody = templateBody.replaceAll("<<TEMPLATE_NAME>>", basicModuleName);
        }
        if (!"".equals(prefix)) {
            // quoteReplacement, adds a slash (\) in front of dollars ($),
            // this is needed, otherwise replaceAll fails with $.
            prefix = Matcher.quoteReplacement(prefix);
            templateBody = templateBody.replaceAll("<<PREFIX>>", prefix);
        }
        return templateBody;
    }

    /**
     * Saves the contents passed locally with the filename given + .b ending.
     * 
     * @param filename
     * @param contents
     */
    private void saveToLocalWorkspace(String filename, String contents, String projectFullPath) {
        // Note: files sent to or coming from the remote server do not have the
        // .b extension.
        // However when handled by eclipse's editor locally they need to have a
        // local extension,
        // which in our case is ".b"
        FileUtil fu = new FileUtil();
        String fullFilename = projectFullPath + "\\" + filename + ".b";
        fu.saveToFile(contents, fullFilename, true);
    }

    /***************************************************************************
     * DELETE TEMPLATE Deletes a template previously selected. Only user created
     * templates can be deleted. Pre-defined system templates cannot be deleted.
     */
    public void deleteTemplate(IT24ViewItem templateSelected) {
        if (templateSelected != null) {
            String message = "";
            if (T24_VIEW_ITEM_CATEGORY.TEMPLATE_SYSTEM_ITEM.equals(templateSelected.getCategory())) {
                // SYSTEM TEMPLATE CAN NOT BE DELETED
                message = "Failed. " + templateSelected.getLabel() + " is a system template and cannot be deleted.";
                String dialogTitle = "  " + templateSelected.getLabel();
                T24MessageDialog dialog = new T24MessageDialog(window.getShell(), dialogTitle, message, MessageDialog.ERROR);
                if (dialog.open() != InputDialog.OK) {
                    // The user clicked something else other than OK. The
                    // execution is finished.
                    return;
                }
            } else {
                MementoUtil mu = MementoUtilFactory.getMementoUtil();
                Template temp = (Template) templateSelected.getData();
                mu.deleteProperty(temp.getKey());
                message = "Template " + templateSelected.getLabel() + " has been successfully deleted.";
            }
            // Display message on console.
            log.logMessage(message);
        }
    }

    /***************************************************************************
     * CREATE TEMPLATE The current active editor will be stored as a template.
     */
    public void createTemplate() {
        StringUtil su = new StringUtil();
        // Check that there is an active editor.
        TextEditor editor = EditorDocumentUtil.getActiveEditor();
        if (editor == null) {
            // No active editor was found. Display error message.
            String message = "Failed. No active editor was found.\n"
                    + "A Template can only be created if an active editor is opened.";
            String dialogTitle = "  ";
            T24MessageDialog dialog = new T24MessageDialog(window.getShell(), dialogTitle, message, MessageDialog.ERROR);
            dialog.open();
            return;
        }
        TemplateCreateDialog dialog = new TemplateCreateDialog(window.getShell());
        boolean atLeastOneInputEmpty = false;
        do {
            if (dialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK. The execution
                // is finished.
                return;
            } else {
                // The user clicked OK. Now validate if inputs are empty. If at
                // least one is empty
                // open the dialog again.
                if (su.atLeastOneEmpty(new String[] { dialog.getTemplateName() })) {
                    // At least one input is empty
                    atLeastOneInputEmpty = true;
                    log.logMessage("Error: empty input. Please ensure that the Name is not empty.");
                } else {
                    // The user clicked OK, and the inputs are ok.
                    // Reset flag
                    atLeastOneInputEmpty = false;
                }
            }
        } while (atLeastOneInputEmpty);
        // Build the Template store string. It has the following structure:
        // template.key=template.key<<NR>>template_name<<NR>>template_body
        // e.g.:
        // t24.template.system.TEMPLATE=t24.template.system.TEMPLATE<<NR>>TEMPLATE<<NR>>*
        // Template body ...
        String templateKey = "t24.template.user." + dialog.getTemplateName();
        StringBuffer sb = new StringBuffer();
        sb.append(templateKey + TemplateConstants.TEMPLATE_NEW_RECORD_SEPARATOR);
        sb.append(dialog.getTemplateName() + TemplateConstants.TEMPLATE_NEW_RECORD_SEPARATOR);
        sb.append(this.getTemplateBody());
        String templateValue = sb.toString();
        MementoUtil mu = MementoUtilFactory.getMementoUtil();
        mu.createProperty(templateKey, templateValue);
        String fullPathLocation = EditorDocumentUtil.getActiveEditorLocation(window);
        String message = "Template " + dialog.getTemplateName() + " has been successfully created in " + fullPathLocation;
        // Display message on console.
        log.logMessage(message);
    }

    /**
     * @return the active document contents, which will be used as the new
     *         Template's body.
     */
    private String getTemplateBody() {
        String body = EditorDocumentUtil.getActiveEditorContents();
        return body;
    }
}
