/*
 * 
 */
package com.zealcore.se.ui.internal.assertions;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.swt.widgets.Composite;

import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.assertions.Assertion;
import com.zealcore.se.core.ifw.assertions.IAssertionRegistry;
import com.zealcore.se.core.model.IType;
import com.zealcore.se.ui.search.SearchFilterInput;

public final class EditableAssertionAdapter implements IAdapterFactory {

    private static final Class<?>[] SUPPORTED_TYPES = { IEditor.class };

    private final ITypeRegistry registry;

    private final IAssertionRegistry assertionRegistry;

    public EditableAssertionAdapter(final ITypeRegistry registry,
            final IAssertionRegistry assertionRegistry) {
        this.registry = registry;
        this.assertionRegistry = assertionRegistry;

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(final Object adaptableObject,
            final Class targetType) {

        if (!targetType.equals(IEditor.class)) {
            return null;
        }

        if (!(adaptableObject instanceof Assertion)) {
            return null;
        }

        final Assertion concrete = (Assertion) adaptableObject;

        final IEditor editor = new AssertionEditor(registry, assertionRegistry,
                concrete);

        return editor;
    }

    /**
     * {@inheritDoc}
     */
    public Class<?>[] getAdapterList() {
        return EditableAssertionAdapter.SUPPORTED_TYPES.clone();
    }

    private static class AssertionEditor implements IEditor {

        private final Assertion assertion;

        private final Set<IType> types;

        private final IAssertionRegistry assertionRegistry;

        private SearchFilterInput inputEdit;

        public AssertionEditor(final ITypeRegistry registry,
                final IAssertionRegistry assertionRegistry,
                final Assertion assertion) {
            this.assertionRegistry = assertionRegistry;
            this.assertion = assertion;

            types = new LinkedHashSet<IType>();
            for (final ITypePackage pkg : registry.getTypePackages()) {
                types.addAll(pkg.getTypes());
            }

        }

        /**
         * {@inheritDoc}
         */
        public void addModifyListener(final IModifyListener listener) {
            inputEdit.addModifyListener(listener);
        }

        /**
         * {@inheritDoc}
         */
        public void removeModifyListener(final IModifyListener listener) {
            inputEdit.removeModifyListener(listener);
        }

        /**
         * {@inheritDoc}
         */
        public void saveChanges() {

            assertion.setSearchAdapter((SearchAdapter) inputEdit
                    .createAdapter());
            assertionRegistry.save();
        }

        /**
         * {@inheritDoc}
         */
        public Composite createContents(final Composite parent) {
            final SearchAdapter adapter = assertion.getSearchAdapter();
            inputEdit = new SearchFilterInput(
                    SearchFilterInput.SEARCHABLE_TYPES);

            return inputEdit.createContent(parent, adapter);
        }
    }
}
