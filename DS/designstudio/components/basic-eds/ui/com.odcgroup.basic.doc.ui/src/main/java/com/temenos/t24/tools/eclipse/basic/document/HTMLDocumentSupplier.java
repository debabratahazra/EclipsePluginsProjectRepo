package com.temenos.t24.tools.eclipse.basic.document;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.temenos.t24.tools.eclipse.basic.document.util.DocumentFileUtil;
import com.temenos.t24.tools.eclipse.basic.document.xml.SubroutineInfo;
import com.temenos.t24.tools.eclipse.basic.utils.T24DocViewerUtil;

/**
 * supplying the html document which presented in local disk, to active page and
 * open it in internal web browser
 * 
 * @author sbharathraja
 * 
 */
public class HTMLDocumentSupplier {

    /** name of the product */
    private String productName = "";
    /** name of the component */
    private String componentName = "";

    /**
     * get the html document from local disk and open within RTC Editor
     * 
     */
    public boolean doOpenHTMLDoc() {
        loadPriorInfo();
        if (isEligibleToLoadHTML()) {
            IFile iHTMLFile = getHTMLFile();
            if (iHTMLFile == null)
                return false;
            openInEditor(iHTMLFile);
            return true;
        }
        return false;
    }

    /**
     * loading the subroutine info to current attributes.
     */
    private void loadPriorInfo() {
        SubroutineInfo subRoutineInfo = T24DocViewerUtil.subRoutineInfo;
        if (subRoutineInfo != null) {
            productName = subRoutineInfo.getProduct();
            componentName = subRoutineInfo.getComponent();
        }
    }

    /**
     * checking whether the selected component eligible to opening the html
     * document
     * 
     * @return true - if eligible, false - if un-eligible
     */
    private boolean isEligibleToLoadHTML() {
        if (DocumentFileUtil.getRoot().equalsIgnoreCase("") || productName.equalsIgnoreCase("")
                || componentName.equalsIgnoreCase(""))
            return false;
        else
            return true;
    }

    /**
     * get the html file from local disk
     * 
     * @return - html file as IFile
     */
    private IFile getHTMLFile() {
        IFile iHTMLFile = null;
        String componentPath = DocumentFileUtil.getRoot() + DocumentFileUtil.SEP + productName + DocumentFileUtil.SEP
                + componentName + DocumentFileUtil.SEP;
        String fileName = componentName + DocumentCostants.HTML_EXTENSION;
        File validHTML = new File(componentPath + fileName);
        if (validHTML.exists()) {
            iHTMLFile = T24DocViewerUtil.makeFileToIFile(validHTML);
        }
        return iHTMLFile;
    }

    /**
     * open the html document within RTC Editor
     * 
     * @param iHTMLFile - html file to be open
     */
    private void openInEditor(IFile iHTMLFile) {
        IWorkbenchPage activePage = T24DocViewerUtil.getActivePage();
        IEditorInput editorInput = new FileEditorInput(iHTMLFile);
        try {
            activePage.openEditor(editorInput, "org.eclipse.ui.browser.editor");
        } catch (PartInitException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
