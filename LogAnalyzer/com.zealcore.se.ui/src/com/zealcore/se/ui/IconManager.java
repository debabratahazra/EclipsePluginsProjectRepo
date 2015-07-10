package com.zealcore.se.ui;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;

/**
 * Utility class for getting Zealcore related icons.
 * 
 * @author stch
 */
public final class IconManager {

    private static final String PNG_SUFFIX = ".png";

    private static final String GIF_SUFFIX = ".gif";

    private static IconManager instance = new IconManager();

    public static final String ZEALCORE_LOGGO_SMALL_IMG_ID = "logo/zealcore_logo_16";

    public static final String TIMECLUSTER_SMALL_IMG_ID = "timeclustersmall";

    public static final String CASEFILE_SMALL_IMG_ID = "casefilesmall";

    public static final String LOGFILE_SMALL_IMAGE_ID = "logfilesmall";

    public static final String ZEALCORE_LOGGO_MEDIUM_IMG_ID = "logo/zealcore_logo_32";

    public static final String ZEALCORE_WIZARD_ICON = "zealcore_wizard2";

    public static final String TIMECLUSTER_MEDIUM_IMG_ID = "timeclustermedium";

    public static final String CASEFILE_MEDIUM_IMG_ID = "casefilemedium";

    public static final String LOGFILE_MEDIUM_IMAGE_ID = "logfilemedium";

    public static final String BLOB_IMAGE = "blue_blob";

    public static final String LOGMARK_SMALL_IMAGE = "logmark";

    public static final String STEP_FORWARD_IMG = "e_forward";

    public static final String STEP_BACK_IMG = "e_back";

    public static final String EVENT_SMALL_IMG = "eventsmall";

    public static final String GANTT_SMALL = "gant_small";

    public static final String CHAIN_OVERLAY_ICON = "cluster_chain";
    
    public static final String CHAIN_OVERLAY_ICON_MEDIUM = "cluster_chain_medium";

    public static final String ZOOM_IN_IMG = "plus";

    public static final String ZOOM_OUT_IMG = "minus";

    public static final String ERROR_OVERLAY = "error_ovr";

    public static final String FILTER_IMAGE = "cfilter";

    public static final String TASK_SMALL_IMG = "model/task";

    public static final String UPRIGHT_ARROW = "model/uprigh_arrow";

    public static final String IMPORTED_FILE = "importedfile";

    public static final String COLLAPSE_ALL = "collapseall";

    public static final String LOCK_SMALL = "small_lock";

    public static final String FIND_BUGS = "assertion-wizard";

    public static final String SUCCESS_OVERLAY = "success_ovr";

    public static final String ARRAY_PARTITION = "arraypartition_obj";

    public static final String OVR_IMPORTED = "ovr_imported";

    public static final String SD_LAYOUT_HIERARCHICAL = "statediagram-layoutstyle-hierarchical";

    public static final String SD_LAYOUT_ORGANIC = "statediagram-layoutstyle-organic";

    public static final String SD_LAYOUT_ORTHOGONAL = "statediagram-layoutstyle-orthogonal";

    public static final String SD_LAYOUT_DIRECTED_ORTHOGONAL = "statediagram-layoutstyle-directed-orthogonal";

    public static final String SEQUENCE_SMALL = "sequencesmall";

    public static final String TEXT_BROWSER = "abcsmall";

    public static final String STATE_SMALL = "statesmall";

    public static final String REPORT_SMALL = "report/report-16";

    public static final String REPORT_MEDIUM = "report/report-64";
    
    public static final String CURREENT_SELECTION_ICON = "current_selection";
    
    public static final String ZOOM_IN = "zoom_in";

    public static final String ZOOM_OUT = "zoom_out";

    private IconManager() {}

    private void putIcon(final ImageRegistry reg, final String iconName,
            final String ext) {
        final Bundle bundle = Platform.getBundle(SeUiPlugin.PLUGIN_ID);
        final IPath path = new Path("icons/" + iconName + ext);
        final URL url = FileLocator.find(bundle, path, null);
        final ImageDescriptor desc = ImageDescriptor.createFromURL(url);
        reg.put(iconName, desc);
    }

    void initializeImageRegistry(final ImageRegistry reg) {

        putIcon(reg, IconManager.ZEALCORE_LOGGO_SMALL_IMG_ID,
                IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.ZEALCORE_LOGGO_MEDIUM_IMG_ID,
                IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.ZEALCORE_WIZARD_ICON, IconManager.PNG_SUFFIX);

        putIcon(reg, IconManager.LOGFILE_MEDIUM_IMAGE_ID,
                IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.LOGFILE_SMALL_IMAGE_ID, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.CASEFILE_MEDIUM_IMG_ID, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.CASEFILE_SMALL_IMG_ID, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.TIMECLUSTER_MEDIUM_IMG_ID,
                IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.TIMECLUSTER_SMALL_IMG_ID,
                IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.STEP_FORWARD_IMG, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.STEP_BACK_IMG, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.BLOB_IMAGE, IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.LOGMARK_SMALL_IMAGE, IconManager.PNG_SUFFIX);

        putIcon(reg, IconManager.CHAIN_OVERLAY_ICON, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.CHAIN_OVERLAY_ICON_MEDIUM, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.IMPORTED_FILE, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.EVENT_SMALL_IMG, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.TASK_SMALL_IMG, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.UPRIGHT_ARROW, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.ZOOM_IN_IMG, IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.ZOOM_OUT_IMG, IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.FIND_BUGS, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.ERROR_OVERLAY, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.SUCCESS_OVERLAY, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.FILTER_IMAGE, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.COLLAPSE_ALL, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.LOCK_SMALL, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.ARRAY_PARTITION, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.OVR_IMPORTED, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.SD_LAYOUT_DIRECTED_ORTHOGONAL,
                IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.SD_LAYOUT_ORTHOGONAL, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.SD_LAYOUT_ORGANIC, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.SD_LAYOUT_HIERARCHICAL, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.GANTT_SMALL, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.SEQUENCE_SMALL, IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.TEXT_BROWSER, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.STATE_SMALL, IconManager.GIF_SUFFIX);

        putIcon(reg, IconManager.REPORT_SMALL, IconManager.PNG_SUFFIX);
        putIcon(reg, IconManager.REPORT_MEDIUM, IconManager.PNG_SUFFIX);
        
        putIcon(reg, IconManager.CURREENT_SELECTION_ICON, IconManager.GIF_SUFFIX);
        
        putIcon(reg, IconManager.ZOOM_IN, IconManager.GIF_SUFFIX);
        putIcon(reg, IconManager.ZOOM_OUT, IconManager.GIF_SUFFIX);
    }

    /**
     * Singleton patter, gets the single instance
     * 
     * @return the single instance
     */
    public static IconManager getInstance() {
        return IconManager.instance;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *                the path, should be any of the constants defined in
     *                IconManager.
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(final String path) {
        final SeUiPlugin plugin = SeUiPlugin.getDefault();
        if (plugin == null) {
            return null;
        }
        final ImageDescriptor descriptor = plugin.getImageRegistry()
                .getDescriptor(path);
        return descriptor;
    }
}
