package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.views.tree.ICheckBoxTreeNode;

public class ChangeNode implements ICheckBoxTreeNode {

    private String label;
    private ChangesetNode parent;
    private boolean checked = true;

    public ChangeNode(String label, ChangesetNode parent) {
        this.label = label;
        this.parent = parent;
    }

    public ICheckBoxTreeNode[] getChildren() {
        return null;
    }

    public Image getImage() {
        return FTPClientImplConstants.CHANGESET_FILE_IMAGE;
    }

    public String getLabel() {
        return label;
    }

    public ICheckBoxTreeNode getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return false;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean state) {
        checked = state;
    }
}
