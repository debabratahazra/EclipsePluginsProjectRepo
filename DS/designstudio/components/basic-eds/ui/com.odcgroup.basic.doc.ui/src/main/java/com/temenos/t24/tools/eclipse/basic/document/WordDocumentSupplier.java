package com.temenos.t24.tools.eclipse.basic.document;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.actions.ActionsUtils;
import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;
import com.temenos.t24.tools.eclipse.basic.utils.T24DocViewerUtil;

/**
 * supply the word document when the action happened, available in T24DocView.
 * 
 * @author sbharathraja
 * 
 */
public class WordDocumentSupplier {

    /**
     * if the word document is in user's T24CompDoc location,open the document
     */
    public void doOpenWordFile() {
        IFile iFile = null;
        boolean isFileThere = false;
        String product = "", component = "";
        SubroutineInfo subRoutineInfo = T24DocViewerUtil.subRoutineInfo;
        if (subRoutineInfo != null) {
            product = subRoutineInfo.getProduct();
            component = subRoutineInfo.getComponent();
        }
        IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        String componentPath = DocumentFileUtil.getRoot() + DocumentFileUtil.SEP + product + DocumentFileUtil.SEP + component
                + DocumentFileUtil.SEP;
        String validFileName = component + DocumentCostants.WORD_03_EXTENSION;
        String validAlterFileName = component + DocumentCostants.WORD_07_EXTENSION;
        File word_03_File = new File(componentPath + validFileName);
        File word_07_File = new File(componentPath + validAlterFileName);
        if (word_03_File.exists()) {
            iFile = T24DocViewerUtil.makeFileToIFile(word_03_File);
            isFileThere = true;
        } else if (word_07_File.exists()) {
            iFile = T24DocViewerUtil.makeFileToIFile(word_07_File);
            isFileThere = true;
        }
        if (isFileThere)
            doOpen(iFile, activePage);
        else {
            String header = "Open Document";
            ActionsUtils.launchMessageDialogPopup(header, PluginConstants.MESSAGE_NO_WORD_DOCUMENT_FOUND, MessageDialog.WARNING);
        }
    }

    /**
     * method which open the word document within the eclipse editor
     * 
     * @param iFile - file to be opened
     * @param activePage - current active workbench page
     */
    private void doOpen(IFile iFile, IWorkbenchPage activePage) {
        IEditorInput editorInput = new FileEditorInput(iFile);
        try {
            activePage.openEditor(editorInput, IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
        } catch (PartInitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
