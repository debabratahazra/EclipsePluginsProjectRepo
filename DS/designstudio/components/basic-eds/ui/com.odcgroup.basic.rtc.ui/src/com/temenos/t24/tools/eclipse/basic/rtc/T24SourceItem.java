package com.temenos.t24.tools.eclipse.basic.rtc;

import com.ibm.team.filesystem.common.IFileContent;
import com.ibm.team.filesystem.common.IFileItemHandle;

public class T24SourceItem {

    private String name;
    private IFileItemHandle fileItem;
    private IFileContent fileContent;

    public T24SourceItem(String name, IFileItemHandle fileItem, IFileContent fileContent) {
        this.name = name;
        this.fileItem = fileItem;
        this.fileContent = fileContent;
    }

    public String getName() {
        return name;
    }

    public IFileItemHandle getFileItem() {
        return fileItem;
    }

    public IFileContent getFileContent() {
        return fileContent;
    }

    public boolean equals(Object obj) {
        if (obj instanceof T24SourceItem) {
            return ((T24SourceItem) obj).getName().equals(name);
        }
        return false;
    }
}
