package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.views.tree.ICheckBoxTreeNode;

public class ChangesetNode implements ICheckBoxTreeNode {

    private String changeSetRef;
    private List<ChangeNode> changes;
    private boolean checked = true;

    public ChangesetNode(String changeSetRef) {
        this.changeSetRef = changeSetRef;
        changes = new ArrayList<ChangeNode>();
    }

    public ICheckBoxTreeNode[] getChildren() {
        int size = changes.size();
        ICheckBoxTreeNode[] changesArr = new ICheckBoxTreeNode[size];
        changes.toArray(changesArr);
        return changesArr;
    }

    public Image getImage() {
        return FTPClientImplConstants.REMOTE_FILE_IMAGE;
    }

    public String getLabel() {
        return changeSetRef;
    }

    public ICheckBoxTreeNode getParent() {
        return null;
    }

    public boolean hasChildren() {
        return changes.size() > 0 ? true : false;
    }

    public void addChanges(ChangeNode change) {
        changes.add(change);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean state) {
        checked = state;
    }
}
