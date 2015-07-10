package com.enea.sd.example.importer;

import java.io.File;
import java.util.Collections;

import com.zealcore.se.core.IImporter;
import com.zealcore.se.core.ifw.ImportContext;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;

public class MinimalImporter implements IImporter {

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#canRead(java.io.File)
     */
    public boolean canRead(File file) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#setContext(ImportContext)
     */
    public void setContext(ImportContext context) {}

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getArtifacts()
     */
    public Iterable<IArtifact> getArtifacts() {
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#getLogEvents()
     */
    public Iterable<ILogEvent> getLogEvents() {
        return Collections.emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.zealcore.se.core.IImporter#size()
     */
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "Minimal Importer (c) Enea 2008";
    }

}
