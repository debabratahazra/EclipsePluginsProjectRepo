package com.temenos.t24.tools.eclipse.basic.views.inlineDocs;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

import org.dom4j.Document;
import org.dom4j.Node;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.temenos.t24.tools.eclipse.basic.document.xml.XmlUtil;
import com.temenos.t24.tools.eclipse.basic.help.AbstractT24FileHelper;
import com.temenos.t24.tools.eclipse.basic.help.T24BasicFileExplorer;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewManager;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.listener.T24CommentsFileExplorer;
import com.temenos.t24.tools.eclipse.basic.wizards.docgeneration.file.GenerateDocConstants;

/**
 * class which helps to providing child node of tree viewer
 * 
 * @author sbharathraja
 * 
 */
public class T24LabelCollector {

    /**
     * helper to get the basic file
     */
    private AbstractT24FileHelper fileHelper;
    /** array of labels */
    private String[] labelsArr;

    /**
     * get the labels presented in given subroutine
     * 
     * @param routineName - name of the subroutine
     * @return array of labels
     */
    public String[] getLabels(String routineName) {
        fileHelper = new T24BasicFileExplorer();
        File basicFile = fileHelper.getBasicFile(routineName);
        if (basicFile == null)
            return new String[0];
        IFile iSub = EditorDocumentUtil.getIFile(basicFile.getPath());
        IDocumentProvider documentProvider = new TextFileDocumentProvider();
        try {
            documentProvider.connect(iSub);
            IDocument basicDocument = documentProvider.getDocument(iSub);
            ViewManager viewMGR = new ViewManager();
            IT24ViewItem[] labelsCollection = viewMGR.getViewItems(basicDocument, T24_VIEW_ITEM_CATEGORY.LABEL_ITEM);
            labelsArr = new String[labelsCollection.length];
            int i = 0;
            for (IT24ViewItem label : labelsCollection) {
                labelsArr[i] = label.getLabel();
                i++;
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return labelsArr;
    }

    /**
     * get the labels presented in given subroutine which is actually taken from
     * stripped out comments xml file stored in T24 Doc Location
     * 
     * @param subRoutineName
     * @return
     */
    public String[] getLabelsFromExtractedFile(String subRoutineName) {
        String[] labels = null;
        fileHelper = new T24CommentsFileExplorer();
        String xmlFilePath = fileHelper.getCommentsXMLPath(subRoutineName);
        Document xmlDocument = XmlUtil.loadDocument(xmlFilePath);
        if (xmlDocument == null)
            return new String[0];
        List<Node> goSubNodes = XmlUtil.getNodes(xmlDocument, "//SubRoutine/*");
        labels = new String[goSubNodes.size()];
        ListIterator<Node> iter = goSubNodes.listIterator();
        int i = 0;
        while (iter.hasNext()) {
            Node childNode = iter.next();
            labels[i] = XmlUtil.getAttributeValue(childNode, GenerateDocConstants.IDENTIFIER_OF__ELEMENT);
            i++;
        }
        return labels;
    }
    // private void test() {
    // if (basicDocument != null) {
    // for (String label : labelsArr) {
    // int labelLineNo = DocumentViewerUtil.getLableLine(basicDocument, label);
    // try {
    // IRegion region = basicDocument.getLineInformation(labelLineNo);
    // String line = basicDocument.get(region.getOffset(), region.getLength());
    // System.out.println(line);
    // } catch (BadLocationException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // }
}
