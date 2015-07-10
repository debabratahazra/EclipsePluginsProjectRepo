/*
 * 
 */
package com.zealcore.se.ui.editors;

import java.util.UUID;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.model.IWorkbenchAdapter;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ITimeCluster;
import com.zealcore.se.ui.util.ArtifactColorMap;

public interface ILogSessionWrapper extends IWorkbenchAdapter, IAdaptable {

    /**
     * Gets the case file associated with this wrapper.
     * 
     * @return the case file
     */
    ICaseFile getCaseFile();

    /**
     * Gets the color map associated with this wrapper.
     * 
     * @return the color map
     */
    ArtifactColorMap getColorMap();

    ITimeCluster getDefaultViewSet();

    void addChangeListener(IChangeListener changeListener);

    void removeChangeListener(IChangeListener changeListener);

    UUID getId();

    IFolder getFolder();
}
