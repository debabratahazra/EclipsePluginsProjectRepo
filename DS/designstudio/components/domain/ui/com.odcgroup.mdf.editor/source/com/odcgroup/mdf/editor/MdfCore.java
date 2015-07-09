package com.odcgroup.mdf.editor;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;

import com.odcgroup.mdf.editor.builders.MdfDomainValidator;
import com.odcgroup.mdf.editor.model.MdfMarkerUtils;
import com.odcgroup.mdf.editor.ui.MdfPerspective;
import com.odcgroup.mdf.editor.ui.editors.MdfModelEditor;
import com.odcgroup.mdf.editor.ui.views.MdfProjectsView;


/**
 * @version 1.0
 * @author <a href="cvedovini@odyssey-group.com">Claude Vedovini </a>
 */
public final class MdfCore {

    public static final String PLUGIN_ID = "com.odcgroup.mdf.editor";
    public static final String NATURE_ID = MdfNature.class.getName();
    public static final String MODEL_EDITOR_ID = MdfModelEditor.class.getName();
    public static final String DOMAIN_MODEL_EDITOR_ID = "com.odcgroup.domain.Domain";
    public static final String PROJECTS_VIEW_ID = MdfProjectsView.class.getName();
    public static final String PERSPECTIVE_ID = MdfPerspective.class.getName();
    public static final String VALIDATION_BUILDER_ID = MdfDomainValidator.class.getName();
    public static final String ICON_DOMAIN_FILE = "mml";
    public static final String ICON_DOMAIN = "domain";
    public static final String ICON_ENUM = "enum";
    public static final String ICON_CLASS = "class";
    public static final String ICON_MAIN_CLASS = "mainclass";
    public static final String ICON_DATASET = "dataset";
    public static final String ICON_ATTR = "attr";
    public static final String ICON_ASSOC = "assoc";
    public static final String ICON_RASSOC = "rassoc";
    public static final String ICON_NVPAIR = "nvpair";
    public static final String ICON_ERROR = "error";
    public static final String ICON_WARNING = "warning";
    public static final String ICON_DATASETPROPERTY = "dsprop";
    public static final String ICON_KEY = "icon_key";
    public static final String FORM_BANNER = "form_banner";
    public static final String ACTION_HIDE_CLASSES = "com.odcgroup.mdf.editor.actions.filterClasses";
    public static final String ACTION_HIDE_MAIN_CLASSES = "com.odcgroup.mdf.editor.actions.filterMainClasses";
    public static final String ACTION_HIDE_ENUMS = "com.odcgroup.mdf.editor.actions.filterEnums";
    public static final String ACTION_HIDE_DATASETS = "com.odcgroup.mdf.editor.actions.filterDatasets";
    public static final String MENU_MDF = "com.odcgroup.mdf.editor.menu";
    public static final String DOMAIN_FILE_EXT = "domain";
    public static final String ICON_BKEY = "bkey";
    public static final String ICON_VALUE = "value";
    public static final String ICON_SUM = "sum";
    public static final String NEW_ATTR = "newattr";
    
    /**
     * @deprecated Use MdfMarkerUtils.MARKER_ID instead
     */
    @Deprecated
    public static final String MDF_MARKER = MdfMarkerUtils.MARKER_ID;
    public static final IStatus STATUS_OK = Status.OK_STATUS;
    public static final String MML_EXTENSION_POINT = "com.odcgroup.mdf.editor.mmlextensions";
    public static final String ICON_ANNOTATION = "annotation";
    public static final String ICON_ANNOTATIONPROPERTY = "annotprop";
    public static final String ICON_BUSINESSTYPE = "btype";

    /**
     * Constructor for MdfCore
     */
    private MdfCore() {
        super();
    }

    public static IStatus newStatus(String message, int severity) {
        return new Status(severity, MdfCore.PLUGIN_ID, -1, message, null);
    }

    public static IStatus newStatus(String message, Throwable t) {
        return new Status(IStatus.ERROR, MdfCore.PLUGIN_ID, -1, message, t);
    }

    public static IStatus newStatus(Throwable t) {
        return newStatus(t.toString(), t);
    }

    public static void openError(Shell shell, Throwable t) {
        if (t instanceof InvocationTargetException) {
            t = ((InvocationTargetException) t).getTargetException();
        }

        ErrorDialog.openError(shell, "Error", t.getLocalizedMessage(),
                newStatus(t));
    }

    public static void throwCoreException(Throwable t) throws CoreException {
        if (t == null) {
            throwCoreException(null, null);
        } else {
            throwCoreException(t.toString(), t);
        }
    }

    public static void throwCoreException(String message) throws CoreException {
        throwCoreException(message, null);
    }

    public static void throwCoreException(String message, Throwable t)
            throws CoreException {
        if (message == null) {
            message = (t == null) ? "Unspecified error occured!" : t.toString();
        }

        throw new CoreException(newStatus(message, t));
    }

}
