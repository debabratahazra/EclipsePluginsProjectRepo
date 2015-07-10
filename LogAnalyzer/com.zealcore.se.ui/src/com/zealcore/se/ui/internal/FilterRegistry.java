/*
 * 
 */
package com.zealcore.se.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.zealcore.se.core.AbstractExtensionVisitor;
import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.ui.SeUiPlugin;

public final class FilterRegistry {

    private static final String CLASS_ELEMENT = "class";

    private final Collection<IUiFilter> liveFilters = new ArrayList<IUiFilter>();

    private static FilterRegistry instance = new FilterRegistry();

    private FilterRegistry() {

        try {
            new AbstractExtensionVisitor(SeUiPlugin.PLUGIN_ID, "simpleFilters") {

                @Override
                protected void visit(final IConfigurationElement element)
                        throws CoreException {
                    liveFilters.add(new FilterProxy(element));

                }

            }.run();

        } catch (final CoreException e) {
            SeUiPlugin.logError("Unable to read filters", e);
        }

    }

    /**
     * Gets the live filters.
     * 
     * @return the live filters
     */
    public Collection<IUiFilter> getLiveFilters() {
        return Collections.unmodifiableCollection(liveFilters);
    }

    /**
     * Gets the singleton instance.
     * 
     * @return the instance
     */
    public static FilterRegistry getInstance() {
        return FilterRegistry.instance;
    }

    private static class FilterProxy implements IUiFilter {
        private final IFilter<IObject> delegate;

        private String name = "";

        private final String id;

        private boolean experienceFailure;

        public boolean filter(final IObject x) {
            if (experienceFailure) {
                return true;
            }
            try {
                return delegate.filter(x);

            } catch (final RuntimeException ex) {
                experienceFailure = true;
                throw ex;
            }
        }

        @SuppressWarnings("unchecked")
        public FilterProxy(final IConfigurationElement element)
                throws CoreException {

            delegate = (IFilter<IObject>) element
                    .createExecutableExtension(FilterRegistry.CLASS_ELEMENT);
            name = element.getAttribute("name");
            id = element.getAttribute("id");
        }

        @Override
        public String toString() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    public interface IUiFilter extends IFilter<IObject> {
        String getId();
    }
}
