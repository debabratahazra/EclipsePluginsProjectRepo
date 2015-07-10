package com.zealcore.se.core.util;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Utility class for importing log file tasks
 * 
 * @author cafa
 * 
 */
public final class ImportUtil {

    private ImportUtil() {}

    public static void refreshFile(final File binFile) {
        IPath binFilePath = new Path(binFile.getAbsolutePath());
        IFile binFileResource = null;
        try {
            binFileResource = ResourcesPlugin.getWorkspace().getRoot()
                    .getFileForLocation(binFilePath);
        } catch (IllegalStateException e) {
            // If Workspace is closed. this occur when running JUnit tests
        }
        if (binFileResource != null) {
            try {
                binFileResource.refreshLocal(IResource.DEPTH_ZERO, null);
            } catch (CoreException e) {
                throw new IllegalStateException("Failed to refresh file !"
                        + binFile.getAbsolutePath(), e);
            }
        }
    }
}
