package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.zealcore.se.core.AbstractExtensionVisitor;
import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.dl.TypeRegistry;
import com.zealcore.se.core.model.IType;

/**
 * The IFWFacade provides a unified API to the components of the importer
 * framework. It help provide a simplified view and contributes convenience
 * services for UI providers.
 */
@SuppressWarnings("unchecked")
public final class IFWFacade {

    private static final String IMPORTERS_EXTENSION = "importers";

    private static final String EXTENDED_IMPORTERS_EXTENSION = "extendedimporters";

    private static Set<IChangeListener> changeListeners = new HashSet<IChangeListener>();

    private static final String NEWLINE = System.getProperty("line.separator");

    public static final int IIMPORTER_VERSION = 1;

    public static final int IEXTENDEDIMPORTER_VERSION = 2;

    private IFWFacade() {

    }

    public static boolean isImported(final IFile f) {
        return null != getLogset(f);
    }

    private static Logset getLogset(final IFile f) {
        for (final Logset logset : Logset.getLogsets()) {
            if (logset.contains(f)) {
                return logset;
            }
        }
        return null;
    }

    /**
     * Returns the registered LogAdapters which are able to read the supplied
     * file (returns canRead(file) == true)
     * 
     * @param file
     *            the file to test
     * @return a collections of LogAdapters which are able to read the file
     */
    public static Collection<ILogAdapter> findLogAdapter(final IFile file,
            final int importerVersionType) {
        final Collection<ILogAdapter> adapters = new ArrayList<ILogAdapter>();

        for (final ILogAdapter adapter : getLogAdapters(importerVersionType)) {
            try {
                if (adapter.canRead(file)) {
                    adapters.add(adapter);
                }
            } catch (final RuntimeException e) {
                SeCorePlugin.logError("Plug-in Name: " + adapter + NEWLINE
                        + "Failed to import: " + file.getName(), e);
            }
        }

        return adapters;
    }

    /**
     * Adds the listener to the collection of listeners who will be notified
     * when files are added or removed, by sending it one of the messages
     * defined in the <code>IChangeListener</code> interface.
     * 
     * @param listener
     *            the listener which should be notified
     */
    public static void addChangeListener(final IChangeListener listener) {
        IFWFacade.changeListeners.add(listener);
    }

    /**
     * Removes the listener from the collection of listeners who will be
     * notified when files are added or removed,.
     * 
     * @param listener
     *            the listener which should no longer be notified
     */
    public static void removeChangeListener(final IChangeListener listener) {
        IFWFacade.changeListeners.remove(listener);
    }

    private static boolean doNotRecurse;

    /**
     * Notifies all change listener. Currently tries to avoid recursion
     * 
     * @param changed
     *            the changed
     */
    static void notifyChangeListener(final boolean changed) {
        if (IFWFacade.doNotRecurse) {
            return;
        }
        IFWFacade.doNotRecurse = true;
        // The defensive copy helps prevent concurrent modifications
        for (final IChangeListener listener : new ArrayList<IChangeListener>(
                IFWFacade.changeListeners)) {
            listener.update(changed);
        }
        IFWFacade.doNotRecurse = false;
    }

    /**
     * Creates a new {@link ILogAdapter} instance from a the provided id. The id
     * should be equals to the id specified in the importers extension point
     * 
     * @param adapterId
     * @return -
     */
    public static ILogAdapter getLogAdapter(final String adapterId, final int importerVersionType) {
        final Collection<ILogAdapter> adapters = getLogAdapters(importerVersionType);

        for (final ILogAdapter adapter : adapters) {
            if (adapter.getId().equalsIgnoreCase(adapterId)) {
                return adapter;
            }
        }

        return null;
    }

    public static Collection<ILogAdapter> getLogAdapters(final int importerVersionType) {
        // FIXME If only a few casts CoreException, others should be returned

        // TODO Decide on which importer (IImporter or IExtendedImporter)
        // to be used for a given logset
        try {
            final List<ILogAdapter> adapters = new ArrayList<ILogAdapter>();

            switch (importerVersionType) {
            case IIMPORTER_VERSION:
                new AbstractExtensionVisitor(IMPORTERS_EXTENSION) {
                    @Override
                    protected void visit(final IConfigurationElement element) {
                        adapters.add(new LogAdapter(new ImporterDescriptor(
                                element)));
                    }
                }.run();
                break;
            case IEXTENDEDIMPORTER_VERSION:
                // Fall through
            default:
                new AbstractExtensionVisitor(EXTENDED_IMPORTERS_EXTENSION) {
                    @Override
                    protected void visit(final IConfigurationElement element) {
                        adapters.add(new ExtendedLogAdapter(
                                new ImporterDescriptor(element)));
                    }
                }.run();
            }

            return adapters;
        } catch (final CoreException e) {
            SeCorePlugin.logError(e);
            return Collections.EMPTY_LIST;
        }
    }

    public static Set<IType> getSearchableTypes() {
        final ITypeRegistry typeRegistry = TypeRegistry.getInstance();
        final Set<IType> searchableTypes = new HashSet<IType>();
        for (final ITypePackage typePackage : typeRegistry.getTypePackages()) {
            for (final IType iType : typePackage.getTypes()) {
                if (iType.isSearchable()) {
                    searchableTypes.add(iType);
                }
            }
        }
        return searchableTypes;
    }
}
