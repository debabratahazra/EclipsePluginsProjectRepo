package com.temenos.t24.tools.eclipse.basic.remote.data;

public class InstallableItem {

    private String name;
    private String localPath;
    private ItemType type = ItemType.UNDEFINED;

    public InstallableItem(String name, String localPath) {
        this.name = name;
        this.localPath = localPath;
        setType(name);
    }

    public String getName() {
        return name;
    }

    public String getLocalPath() {
        return localPath;
    }

    public ItemType getType() {
        return type;
    }

    private void setType(String name) {
        if(name != null && (name.endsWith(".version") || name.endsWith(".enquiry") || name.endsWith(".xml"))){
            type = ItemType.XML;
        }
        if (name != null && name.endsWith(".d")) {
            type = ItemType.DATA;
        }
        if (name != null && name.endsWith(".b")) {
            type = ItemType.SOURCE;
        }
    }
}
