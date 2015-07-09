package com.temenos.t24.tools.eclipse.basic.utils;

import java.io.InputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;

public class EclipseUtil {

    /**
     * @return PlatformUI.getWorkbench().getActiveWorkbenchWindow()
     */
    public static IWorkbenchWindow getActiveWindow() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }

    /**
     * Returns the IPath pointing to where the plugin stores its state.
     * 
     * @return
     */
    public static IPath getPluginStateLocation() {
        T24BasicPlugin plugin = T24BasicPlugin.getDefault();
        IPath stateLocation = plugin.getStateLocation();
        return stateLocation;
    }

    /**
     * @return true => both current dir and default project are not null or
     *         empty. false => at least one of them is either null or empty ("")
     */
    public static boolean isLocalDirAndProjectSet() {
        boolean setOk = false;
        String localCurrentDir = EclipseUtil.getLocalCurDirectory(EclipseUtil.isSaveLocallyOn());
        String localDefaultProject = EclipseUtil.getLocalDefaultProject();
        if ((!"".equals(localCurrentDir) && (localCurrentDir != null))
                && (!"".equals(localDefaultProject) && (localDefaultProject != null))) {
            setOk = true;
        }
        return setOk;
    }

    public static IPath getTemporaryProject() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject proj = root.getProject(PluginConstants.T24_TEMPORARY_IDE_PROJECT);
        if (!proj.exists()) {
            createTemporaryProject();
        }
        if (!proj.isOpen()) {
            try {
                proj.open(null);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        IPath projPath = proj.getLocation();
        return projPath;
    }

    /**
     * Creates a temporary project under current workspace.
     */
    public static void createTemporaryProject() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject proj = root.getProject(PluginConstants.T24_TEMPORARY_IDE_PROJECT);
        try {
            if (!proj.exists()) {
                proj.create(null);
            }
            proj.open(null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the temporary project under current workspace.
     */
    public static void removeTemporaryProject() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        IProject p = root.getProject(PluginConstants.T24_TEMPORARY_IDE_PROJECT);
        try {
            p.close(null);
            p.delete(true, true, null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the local current directory path set up by the user
     * 
     * This is where local files are stored; e.g. when retrieving a file from
     * the remote server, it'll be stored there.
     * 
     * @param isSaveLocallyOn: true => files retrieved from server are saved
     *            locally in a user designated directory, this is the one
     *            returned. false => a hardcoded directory, for temporary
     *            operations, is returned.
     * 
     * @return String - local workspace directory. e.g.
     *         "C\:eclipse\workspace\Basic_project\subdirectory" If no directory
     *         has yet been specified it'll return "".
     */
    public static String getLocalCurDirectory(boolean isSaveLocallyOn) {
        if (isSaveLocallyOn) {
            IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
            String localDir = store.getString(PluginConstants.T24_LOCAL_DIRECTORY);
            return localDir;
        } else {
            /**
             * basicFiles are not saved locally when retrieved from server, so
             * they are temporarily placed in a pre-defined temporary project.
             * They'll be deleted from there at some point (e.g. when their
             * editor closes) .
             */
            return getTemporaryProject().toOSString();
        }
    }

    /**
     * @return local default project (as set in preferences) (e.g.
     *         C\:eclipse\workspace\Basic_project)
     */
    public static String getLocalDefaultProject() {
        IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
        String localDefaultProject = store.getString(PluginConstants.T24_LOCAL_DEFAULT_PROJECT_FULLPATH);
        return localDefaultProject;
    }

    public static boolean isSaveLocallyOn() {
        IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
        boolean saveLocallyOn = store.getBoolean(PluginConstants.T24_SAVE_LOCALLY_BY_DEFAULT_ON);
        return saveLocallyOn;
    }

    /**
     * Updates the StatusLine (the line right at the bottom with an image and a
     * text string.
     * 
     * @param imageFile - e.g. "/icons/signon.gif"
     * @param text - String to display next to the image
     */
    public static void updateStatusLine(String imageFile, String text) {
        IStatusLineManager slm = getStatusLine();
        Image signOnImage = new Image(null, getInputStream(imageFile));
        slm.setMessage(signOnImage, text);
        slm.update(true);
    }

    /**
     * Utility method that returns IStatusLineManager, useful for handling the
     * status line.
     * 
     * @return
     */
    private static IStatusLineManager getStatusLine() {
        IStatusLineManager statusLineManager = null;
        IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        if (activePage != null) {
            IWorkbenchPart activePart = activePage.getActivePart();
            if (activePart instanceof IViewPart) {
                statusLineManager = ((IViewPart) activePart).getViewSite().getActionBars().getStatusLineManager();
            } else if (activePart instanceof IEditorPart) {
                statusLineManager = ((IEditorPart) activePart).getEditorSite().getActionBars().getStatusLineManager();
            }
        }
        return statusLineManager;
    }

    /**
     * Returns an Image object built from the imageFile passed
     * 
     * @param imageFile - (e.g.) "/icons/t24RegionImage.gif"
     * @return Image
     */
    public static Image getImage(String imageFile) {
        Image image = new Image(null, getInputStream(imageFile));
        return image;
    }

    /**
     * Utility method.
     * 
     * @param imageFile - filename of image (e.g.)"/icons/t24RegionImage.gif"
     * @return InputStream of file
     */
    private static InputStream getInputStream(String imageFile) {
        return EclipseUtil.class.getResourceAsStream(imageFile);
    }

    /**
     * Returns the current preference store
     * 
     * @return IPreferenceStore current preference store
     */
    public static IPreferenceStore getPreferenceStore() {
        return (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
    }
}
