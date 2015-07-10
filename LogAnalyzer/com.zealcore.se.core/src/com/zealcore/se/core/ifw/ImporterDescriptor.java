package com.zealcore.se.core.ifw;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.zealcore.se.core.IExtendedImporter;
import com.zealcore.se.core.IImporter;

final class ImporterDescriptor {

    private static final String TAG_ID = "id";

    private static final String TAG_IMPLEMENTATION = "class";

    private static final String TAG_NAME = "name";

    private final IConfigurationElement ext;

    private final String id;

    private final String name;

    ImporterDescriptor(final IConfigurationElement ext) {
        this.ext = ext;
        this.id = ext.getAttribute(ImporterDescriptor.TAG_ID);
        this.name = ext.getAttribute(ImporterDescriptor.TAG_NAME);

    }

    String getName() {
        return this.name;
    }

    String getId() {
        return this.id;
    }

    IImporter createImporter() throws CoreException {
        final Object createExecutableExtension = this.ext
                .createExecutableExtension(ImporterDescriptor.TAG_IMPLEMENTATION);
        if (createExecutableExtension instanceof IImporter) {
            return (IImporter) createExecutableExtension;

        }
        // FIXME Try throw a CoreException instead
        throw new IllegalStateException("The extension : " + getId() + " in "
                + this.ext.getContributor().getName()
                + " does not implement the IImporter interface ");
    }

    IExtendedImporter createExtendedImporter() throws CoreException {
        final Object createExecutableExtension = this.ext
                .createExecutableExtension(ImporterDescriptor.TAG_IMPLEMENTATION);
        if (createExecutableExtension instanceof IExtendedImporter) {
            return (IExtendedImporter) createExecutableExtension;

        }
        // FIXME Try throw a CoreException instead
        throw new IllegalStateException("The extension: " + getId() + "  in "
                + this.ext.getContributor().getName()
                + " does not implement the IExtendedImporter interface ");
    }
}
