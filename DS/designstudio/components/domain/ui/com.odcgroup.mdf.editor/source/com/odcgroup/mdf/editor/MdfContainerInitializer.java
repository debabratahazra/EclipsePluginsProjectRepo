package com.odcgroup.mdf.editor;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.Bundle;


public class MdfContainerInitializer extends ClasspathContainerInitializer {
    public static final String LIB_CONTAINER = "com.odcgroup.mdf.editor.LIB_CONTAINER";

    public void initialize(final IPath path, IJavaProject project)
            throws CoreException {
        if (isMdfLibraryContainer(path)) {

            // Create a library
            final IClasspathEntry[] libs = new IClasspathEntry[3];
            Bundle bundle = Platform.getBundle("com.odcgroup.mdf");

            libs[0] = newLibrairieEntry(bundle, "lib/otf-core.jar");
            libs[1] = newLibrairieEntry(bundle, "lib/mdf.jar");
            libs[2] = newLibrairieEntry(bundle, "lib/log4j.jar");

            IClasspathContainer container = new ClassPathContainer(libs);

            JavaCore.setClasspathContainer(path,
                    new IJavaProject[] { project },
                    new IClasspathContainer[] { container }, null);
        }
    }

    private IClasspathEntry newLibrairieEntry(Bundle bundle, String path) throws CoreException {
        try {
            URL entry = bundle.getEntry(path);
            URL resolved = FileLocator.resolve(entry);
            path = resolved.getPath();
            return JavaCore.newLibraryEntry(new Path(path), null, null, true);
        } catch (IOException e) {
            MdfCore.throwCoreException("Could find librairy " + path, e);
            return null;
        }
    }

    public static final void updateClasspath(IProject project,
            IProgressMonitor monitor) throws CoreException {
        IJavaProject jproject = JavaCore.create(project);

        IClasspathEntry[] oldClassPath = jproject.getRawClasspath();
        IPath mdfContainerPath = new Path(LIB_CONTAINER);

        for (int i = 0; i < oldClassPath.length; i++) {
            if (oldClassPath[i].getPath().equals(mdfContainerPath)) {
                return;
            }
        }

        IClasspathEntry[] newClassPath = new IClasspathEntry[oldClassPath.length + 1];
        System.arraycopy(oldClassPath, 0, newClassPath, 0, oldClassPath.length);

        // Add LIB_CONTAINER
        newClassPath[oldClassPath.length] = JavaCore.newContainerEntry(
                mdfContainerPath, true);

        jproject.setRawClasspath(newClassPath, null);
    }

    private boolean isMdfLibraryContainer(IPath path) {
        return (path != null) && LIB_CONTAINER.equals(path.segment(0));
    }

    private final class ClassPathContainer implements IClasspathContainer {
        private final IPath path;
        private final IClasspathEntry[] libs;

        private ClassPathContainer(IClasspathEntry[] libs) {
            this.path = new Path(MdfContainerInitializer.LIB_CONTAINER);
            this.libs = libs;
        }

        public IClasspathEntry[] getClasspathEntries() {
            return libs;
        }

        public String getDescription() {
            return "MDF Core Runtime Libraries";
        }

        public int getKind() {
            return K_DEFAULT_SYSTEM;
        }

        public IPath getPath() {
            return path;
        }
    }
}
