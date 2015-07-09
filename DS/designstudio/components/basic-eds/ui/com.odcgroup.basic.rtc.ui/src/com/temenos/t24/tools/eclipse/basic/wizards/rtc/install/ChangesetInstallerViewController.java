/**
 * 
 */
package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.rtc.T24SourceItem;
import com.temenos.t24.tools.eclipse.basic.views.tree.ICheckBoxTreeNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewRoot;

/**
 * @author ssethupathi
 * 
 */
public class ChangesetInstallerViewController {

    private List<T24ChangeSet> changeSets;

    public ChangesetInstallerViewController(List<T24ChangeSet> changeSets) {
        this.changeSets = changeSets;
    }

    public IT24TreeViewRoot getInput() {
        IT24TreeViewRoot root = new WorkitemTree();
        for (T24ChangeSet changeSet : changeSets) {
            ChangesetNode csn1 = new ChangesetNode("WI-" + changeSet.getWorkItemReference() + "-" + changeSet.getReference());
            List<T24SourceItem> t24Sourceitems = changeSet.getSourceItems();
            for (T24SourceItem item : t24Sourceitems) {
                ChangeNode cn1 = new ChangeNode(item.getName(), csn1);
                csn1.addChanges(cn1);
            }
            root.addParentNode(csn1);
        }
        return root;
    }

    // TODO : A better, efficient way of doing the mapping of selected nodes to
    // the list of change sets
    public List<T24ChangeSet> getChangeSets(IT24TreeViewRoot selected) {
        List<T24ChangeSet> selectedItems = new ArrayList<T24ChangeSet>();
        List<IT24TreeViewNode> changeSetNodes = Arrays.asList(selected.getParentNodes());
        int changeSetCount = changeSets.size();
        for (int changeSetIdx = 0; changeSetIdx < changeSetCount; changeSetIdx++) {
            IT24TreeViewNode node = changeSetNodes.get(changeSetIdx);
            if (node instanceof ICheckBoxTreeNode) {
                if (!((ICheckBoxTreeNode) node).isChecked()) {
                    continue;
                }
            }
            T24ChangeSet oldChangeSet = changeSets.get(changeSetIdx);
            T24ChangeSet newChangeSet = new T24ChangeSet(oldChangeSet.getReference(), oldChangeSet.getWorkItemReference());
            List<T24SourceItem> items = oldChangeSet.getSourceItems();
            int changesCount = items.size();
            IT24TreeViewNode[] nodes = node.getChildren();
            for (int changeIdx = 0; changeIdx < changesCount; changeIdx++) {
                if (nodes[changeIdx] instanceof ICheckBoxTreeNode) {
                    ICheckBoxTreeNode node1 = ((ICheckBoxTreeNode) nodes[changeIdx]);
                    if (node1.isChecked()) {
                        newChangeSet.addSourceItem(items.get(changeIdx));
                    }
                }
            }
            selectedItems.add(newChangeSet);
        }
        return selectedItems;
    }
}
