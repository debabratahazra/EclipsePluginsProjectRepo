package com.zealcore.se.core.ifw;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.ifw.assertions.IAssertionSet;

public final class ImporterExporter {

    static final String IMPL_NODE = "impl";

    static final String ASSERTION_SET_NODE = "assertionSet";

    static final String ADVANCED_SEARCH_SET_NODE = "advancedSearchSet";

    static final String ASSERTION_REGISTRY_NODE = "AssertionRegistry";

    static final String ADVANCED_SEARCH_REGISTRY_NODE = "AdvancedSearchRegistry";

    private ImporterExporter() {}

    public static void exportAssertions(final IMemento owner,
            final IAssertionRegistry registry) {
        for (final IAssertionSet set : registry.getAssertionSets()) {
            final IMemento setMemento = owner
                    .createChild(ImporterExporter.ASSERTION_SET_NODE);
            set.saveSate(setMemento);
            setMemento.putString(ImporterExporter.IMPL_NODE, set.getClass()
                    .getName());
        }
    }

    /**
     * Imports assertions from the Memento to the Assertions registry. It is
     * designed to use with Settings import - where the assertions is one part
     * of the settings.
     * 
     * @param owner
     * @param registry
     * @param typeRegistry
     * @return -
     */
    public static int importAssertions(final IMemento owner,
            final IAssertionRegistry registry, final ITypeRegistry typeRegistry) {

        final IMemento assertionRegistry = owner
                .getChild(ImporterExporter.ASSERTION_REGISTRY_NODE);

        // This if prevents null pointer exception in for-loop
        if (assertionRegistry == null
                || assertionRegistry
                        .getChildren(ImporterExporter.ASSERTION_SET_NODE).length == 0) {
            return 0;
        }

        int noOfImportedItems = 0;
        for (final IMemento iMemento : assertionRegistry
                .getChildren(ImporterExporter.ASSERTION_SET_NODE)) {
            final String className = iMemento
                    .getString(ImporterExporter.IMPL_NODE);
            try {
                final Class<?> impl = Class.forName(className);
                final IAssertionSet set = (IAssertionSet) impl.newInstance();
                set.init(iMemento, typeRegistry);
                registry.addAssertionSet(set);
                noOfImportedItems++;
            } catch (final ClassNotFoundException e) {
                // FIXME LogError and try continue importing instead
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                // FIXME LogError and try continue importing instead
                throw new RuntimeException(e);
            } catch (final IllegalAccessException e) {
                // FIXME LogError and try continue importing instead
                throw new RuntimeException(e);
            }
        }
        return noOfImportedItems;
    }
}
