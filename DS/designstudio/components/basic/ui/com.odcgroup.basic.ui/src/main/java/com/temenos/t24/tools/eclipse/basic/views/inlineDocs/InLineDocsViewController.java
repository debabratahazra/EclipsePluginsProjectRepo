package com.temenos.t24.tools.eclipse.basic.views.inlineDocs;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model.InLineLabelsNode;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model.InLineRootNode;
import com.temenos.t24.tools.eclipse.basic.views.inlineDocs.model.T24InLineDocsTree;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * controller class which controls the in-line document view
 * 
 * @author sbharathraja
 * 
 */
public class InLineDocsViewController {

    /** instance of class {@link InLineDocsViewController} */
    private static InLineDocsViewController controller = new InLineDocsViewController();

    /**
     * get the instance of class {@link InLineDocsViewController} singleton
     * pattern
     * 
     * @return instance of class {@link InLineDocsViewController}
     */
    public static InLineDocsViewController getInstance() {
        return controller;
    }

    private InLineDocsViewController() {
        // block the instance creation
    }

    /**
     * build the in-line document tree with given subroutine name
     * 
     * @param subRoutineName - name of the subroutine
     * @return tree's root node
     */
    public IT24TreeViewRoot getInLineDocsTree(String subRoutineName) {
        IT24TreeViewRoot treeRoot = new T24InLineDocsTree();
        treeRoot.addParentNode(new InLineRootNode(subRoutineName));
        return treeRoot;
    }

    /**
     * build the children's for in-line document tree for given parent node
     * 
     * @param parentNode - node which contains the subroutine name
     * @return array of child nodes
     */
    public IT24TreeViewNode[] getLabelsNode(InLineRootNode parentNode) {
        IT24TreeViewNode labelNode = null;
        T24LabelCollector collector = new T24LabelCollector();
        List<IT24TreeViewNode> labelsList = new ArrayList<IT24TreeViewNode>();
        for (String label : collector.getLabelsFromExtractedFile(parentNode.getLabel())) {
            labelNode = new InLineLabelsNode(parentNode, label);
            labelsList.add(labelNode);
        }
        IT24TreeViewNode[] labelNodesArr = new IT24TreeViewNode[labelsList.size()];
        labelsList.toArray(labelNodesArr);
        return labelNodesArr;
    }
}
