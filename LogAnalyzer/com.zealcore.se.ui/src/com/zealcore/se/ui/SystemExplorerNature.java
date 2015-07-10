package com.zealcore.se.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * The implementation of the SystemExplorerNature.
 */
public class SystemExplorerNature implements IProjectNature {

    /** The Constant NATURE_ID. */
    public static final String NATURE_ID = SeUiPlugin.PLUGIN_ID
            + ".SystemExplorerNature";

    /** The project. */
    private IProject project;

    /**
     * {@inheritDoc}
     */
    public void configure() {}

    /**
     * {@inheritDoc}
     */
    public void deconfigure() {}

    /**
     * {@inheritDoc}
     */
    public IProject getProject() {
        return project;
    }

    /**
     * {@inheritDoc}
     */
    public void setProject(final IProject project) {
        this.project = project;

    }

    /**
     * Applies this nature to the given project
     * 
     * @param desc
     *                the project description
     * 
     * @return true, if apply to was successfull
     */
    public static boolean applyTo(final IProjectDescription desc) {
        if (desc.hasNature(SystemExplorerNature.NATURE_ID)) {

            return false;
        }

        final String[] natureIds = desc.getNatureIds();
        final String[] newIds = new String[natureIds.length + 1];
        for (int i = 0; i < natureIds.length; i++) {
            newIds[i] = natureIds[i];
        }
        newIds[newIds.length - 1] = SystemExplorerNature.NATURE_ID;
        desc.setNatureIds(newIds);

        return true;
    }

    /**
     * Applies this nature to the given project
     * 
     * @param project
     *                the project
     */
    public static void applyTo(final IProject project) {

        try {
            final IProjectDescription desc = project.getDescription();
            SystemExplorerNature.applyTo(desc);
            project.setDescription(desc, null);

        } catch (final CoreException e) {
            SeUiPlugin.logError(e);
        }
    }

}
