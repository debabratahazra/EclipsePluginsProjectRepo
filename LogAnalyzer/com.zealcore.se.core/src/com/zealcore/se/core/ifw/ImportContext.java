package com.zealcore.se.core.ifw;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IArtifactID;
import com.zealcore.se.core.model.LogFile;

public final class ImportContext {

    private File file;

    private LogFile log;

    private Logset logset;

    private ImportContext(final Logset log, final File file) {
        logset = log;
        this.file = file;
    }

    public File getFile() {

        return file;
    }

    public static ImportContext valueOf(final Logset log, final IFile file) {
        return valueOf(log, file.getRawLocation().toFile());
    }

    public static ImportContext valueOf(final Logset log, final File file) {
        return new ImportContext(log, file);
    }

    public IFile getResource() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        String fileAbs = getFile().getAbsolutePath();
        IPath location = Path.fromOSString(fileAbs);
        return root.getFileForLocation(location);
    }

    public LogFile getLog() {
        if (log == null) {
            log = LogFile.valueOf(getFile());
        }
        return log;
    }

    public Logset getLogset() {
        return logset;
    }

    @SuppressWarnings("unchecked")
    public <T extends IArtifact> T resolveArtifact(final T artifact) {
        return (T) logset.resolveArtifact(artifact);
    }

    public IArtifact getArtifact(final IArtifactID id) {
        return logset.getArtifact(id);
    }
}
