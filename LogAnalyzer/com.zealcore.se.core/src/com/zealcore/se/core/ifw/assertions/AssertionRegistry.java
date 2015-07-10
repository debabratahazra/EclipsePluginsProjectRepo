/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.services.IMemento2;
import com.zealcore.se.core.services.IMementoService;

public class AssertionRegistry implements IAssertionRegistry, IChangeListener {

    static final String IMPL_NODE = "impl";

    static final String ASSERTION_SET_NODE = "assertionSet";

    static final String ASSERTION_REGISTRY_NODE = "AssertionRegistry";

    private final Set<IAssertionSet> assertionSets = new HashSet<IAssertionSet>();

    private final IMementoService service;

    private final IPath registryPath;

    private final ITypeRegistry typeService;

    private boolean inititialized;

    /**
     * The Constructor of the {@link AssertionRegistry}. The
     * {@link AssertionRegistry} requires a memento service and a path to where
     * it can store it's persistent data.
     * 
     * @see IMementoService
     * 
     * @param service
     *                the service
     * @param registryPath
     *                the registry path
     */
    public AssertionRegistry(final IMementoService service,
            final IPath registryPath, final ITypeRegistry typeService) {
        this.service = service;
        this.registryPath = registryPath;
        this.typeService = typeService;
    }

    /**
     * {@inheritDoc}
     */
    public void addAssertionSet(final IAssertionSet set) {
        if (!this.inititialized) {
            readRegistry();
        }
        if (this.assertionSets.add(set)) {
            writeRegistry(this.assertionSets);
            set.addChangeListener(this);
        }
    }

    /**
     * Writes the registry.
     * 
     * @param assertionSets
     *                the assertion sets
     */
    private void writeRegistry(final Set<IAssertionSet> assertionSets) {
        if (!this.inititialized) {
            readRegistry();
        }

        final IMemento2 memento = this.service.createWriteRoot(
                AssertionRegistry.ASSERTION_REGISTRY_NODE, this.registryPath);

        for (final IAssertionSet set : assertionSets) {
            final IMemento setMemento = memento
                    .createChild(AssertionRegistry.ASSERTION_SET_NODE);
            set.saveSate(setMemento);
            setMemento.putString(AssertionRegistry.IMPL_NODE, set.getClass()
                    .getName());
        }
        try {
            memento.save();
        } catch (final IOException e) {
            // FIXME Report this exception instead of failing hard
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the registry.
     */
    private void readRegistry() {

        this.inititialized = true;
        if (this.registryPath.toFile().exists()) {
            IMemento memento = null;
            try {
                memento = this.service.createReadRoot(this.registryPath);
            } catch (final FileNotFoundException filenotfound) {
                // FIXME Report this exception instead of failing hard
                throw new RuntimeException(filenotfound);
            } catch (final WorkbenchException e) {
                // FIXME Report this exception instead of failing hard
                throw new RuntimeException(e);
            }
            if (memento != null) {
                for (final IMemento iMemento : memento
                        .getChildren(AssertionRegistry.ASSERTION_SET_NODE)) {
                    final String className = iMemento
                            .getString(AssertionRegistry.IMPL_NODE);
                    try {
                        final Class<?> impl = Class.forName(className);
                        final IAssertionSet set = (IAssertionSet) impl
                                .newInstance();
                        set.init(iMemento, this.typeService);
                        this.assertionSets.add(set);
                        set.addChangeListener(this);
                    } catch (final ClassNotFoundException e) {
                        SeCorePlugin.logError(e);
                    } catch (final InstantiationException e) {
                        SeCorePlugin.logError(e);
                    } catch (final IllegalAccessException e) {
                        SeCorePlugin.logError(e);
                    } catch (final IllegalArgumentException e) {
                        SeCorePlugin.logError(e);
                    }
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public Iterable<IAssertionSet> getAssertionSets() {
        if (!this.inititialized) {
            readRegistry();
        }
        return Collections.unmodifiableSet(this.assertionSets);
    }

    /**
     * {@inheritDoc}
     */
    public void removeAssertionSet(final IAssertionSet set) {
        if (!this.inititialized) {
            readRegistry();
        }
        if (this.assertionSets.remove(set)) {
            writeRegistry(this.assertionSets);
            set.removeChangeListener(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void update(final boolean changed) {
        writeRegistry(this.assertionSets);
    }

    public void save() {
        update(true);
    }
}
