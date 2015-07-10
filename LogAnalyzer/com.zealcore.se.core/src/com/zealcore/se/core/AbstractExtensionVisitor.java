package com.zealcore.se.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public abstract class AbstractExtensionVisitor {

    private final String extensionId;

    private String pluginId = SeCorePlugin.PLUGIN_ID;

    public AbstractExtensionVisitor(final String extensionId) {
        this.extensionId = extensionId;

    }

    public AbstractExtensionVisitor(final String pluginId,
            final String extensionId) {
        this(extensionId);
        this.pluginId = pluginId;
    }

    public void run() throws CoreException {
        final IExtensionRegistry registry = Platform.getExtensionRegistry();
        if (registry == null) {
            return;
        }
        final IExtensionPoint extensionPoint = registry.getExtensionPoint(
                this.pluginId, this.extensionId);
        final IExtension[] extensions = extensionPoint.getExtensions();

        for (final IExtension extension : extensions) {
            for (final IConfigurationElement element : extension
                    .getConfigurationElements()) {

                visit(element);

            }
        }
    }

    protected abstract void visit(IConfigurationElement element)
            throws CoreException;

}
