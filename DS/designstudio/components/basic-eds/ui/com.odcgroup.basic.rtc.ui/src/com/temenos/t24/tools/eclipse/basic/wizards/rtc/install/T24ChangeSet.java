package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.util.ArrayList;
import java.util.List;

import com.temenos.t24.tools.eclipse.basic.rtc.T24SourceItem;

public class T24ChangeSet {

    private int workItemRef;
    private String changeSetReference;
    private List<T24SourceItem> sourceItems;

    public T24ChangeSet(String reference, int workItemRef) {
        changeSetReference = reference;
        this.workItemRef = workItemRef;
        sourceItems = new ArrayList<T24SourceItem>();
    }

    public String getReference() {
        return changeSetReference;
    }

    public int getWorkItemReference() {
        return workItemRef;
    }

    public List<T24SourceItem> getSourceItems() {
        return sourceItems;
    }

    public void addSourceItem(T24SourceItem item) {
        sourceItems.add(item);
    }

    public void removeSourceItem(T24SourceItem item) {
        sourceItems.remove(item);
    }
}
