/*
 * 
 */
package com.zealcore.se.ui.editors;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.zealcore.se.core.IPersistable;
import com.zealcore.se.core.ifw.ITimeProvider;
import com.zealcore.se.core.ifw.Logset;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.ui.ICaseFile;
import com.zealcore.se.ui.ITimeCluster;

/**
 * The Interface ILogViewInput would have a more proper name as IViewSetInput.
 * This is the input for views (with editor behaviour) that belong to a
 * IViewSet.
 */
public interface ILogViewInput extends IEditorInput, IPersistableElement,
        ITimeProvider {

    /**
     * Gets the case file.
     * 
     * @return the case file
     */
    ICaseFile getCaseFile();

    /**
     * Gets the time cluster.
     * 
     * @return the time cluster
     */
    ITimeCluster getTimeCluster();

    /**
     * Gets the log.
     * 
     * @return the log
     */
    ILogSessionWrapper getLog();

    Logset getLogset();

    /**
     * Data previously provided by clients.
     * 
     * @return data
     */
    IArtifactID getData();

    /**
     * Sets data on the input.
     * 
     * @param data
     *                the data
     */
    void setData(IArtifactID data);

    /**
     * Check if the input really exists.
     * 
     * @return true, if exists
     */
    boolean exists();

    /**
     * Gets the ID of the browser that this input should use as the default
     * browser
     * 
     * @return the ID of the browser that this input should use as the default
     *         browser
     */
    String getBrowserId();

    /**
     * Adds additional persistable objects. These objects will be asked to save
     * their state together with this input When a persistable is added and the
     * input contains saved state, it will also receive a call to its init
     * method.
     * 
     * @param p
     */
    void addPersitable(final IPersistable p);

    /**
     * Removes a persistable object
     * 
     * @param p
     */
    void removePersistable(final IPersistable p);
}
