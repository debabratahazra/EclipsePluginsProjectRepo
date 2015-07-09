package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.util.ArrayList;
import java.util.List;

public class InstallableItemCollection {

    private List<InstallableItem> sourceItems;
    private List<InstallableItem> dataItems;
    private List<InstallableItem> undefinedItems;
    private List<InstallableItem> xmlItems;

    public InstallableItemCollection() {
        sourceItems = new ArrayList<InstallableItem>();
        dataItems = new ArrayList<InstallableItem>();
        undefinedItems = new ArrayList<InstallableItem>();
        xmlItems = new ArrayList<InstallableItem>();
    }

    public void addSourceItem(InstallableItem item) {
        sourceItems.add(item);
    }

    public void addDataItem(InstallableItem item) {
        dataItems.add(item);
    }

    public void addUndefinedItem(InstallableItem item) {
        undefinedItems.add(item);
    }
    
    public void addXmlItem(InstallableItem item) {
        xmlItems.add(item);
    }

    public List<InstallableItem> getSourceItems() {
        return sourceItems;
    }

    public List<InstallableItem> getDataItems() {
        return dataItems;
    }

    public List<InstallableItem> getUndefinedItems() {
        return undefinedItems;
    }
    
    public List<InstallableItem> getXmlItems() {
        return xmlItems;
    }
}
