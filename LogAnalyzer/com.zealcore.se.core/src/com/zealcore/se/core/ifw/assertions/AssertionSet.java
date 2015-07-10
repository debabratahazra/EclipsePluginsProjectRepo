/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.IChangeListener;
import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.IObject;

public final class AssertionSet extends AbstractQuery implements IAssertionSet {

    static final String ASSERTIONS_NODE = "assertion";

    static final String IMPL_NODE = "class";

    static final String DESCRIPTION_NODE = "description";

    static final String NAME_NODE = "name";

    private final Set<IAssertion> assertions = new LinkedHashSet<IAssertion>();

    private final Set<IChangeListener> listeners = new LinkedHashSet<IChangeListener>();

    private String description;

    private String name;

    /**
     * {@inheritDoc}
     */
    public void addAssertion(final IAssertion assertion) {
        if (this.assertions.add(assertion)) {
            notifyListeners();
        }
    }

    private void notifyListeners() {
        for (final IChangeListener listener : this.listeners) {
            listener.update(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void addChangeListener(final IChangeListener listener) {
        this.listeners.add(listener);

    }

    /**
     * {@inheritDoc}
     */
    public Iterable<IAssertion> getAssertions() {
        return Collections.unmodifiableCollection(this.assertions);
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    public void init(final IMemento memento, final ITypeRegistry typeService) {

        this.name = memento.getString(AssertionSet.NAME_NODE);
        this.description = memento.getString(AssertionSet.DESCRIPTION_NODE);
        for (final IMemento assertionMemento : memento
                .getChildren(AssertionSet.ASSERTIONS_NODE)) {
            final String clazz = assertionMemento
                    .getString(AssertionSet.IMPL_NODE);
            try {
                final IAssertion ass = (IAssertion) Class.forName(clazz)
                        .newInstance();
                ass.init(assertionMemento, typeService);
                this.assertions.add(ass);
            } catch (final IllegalArgumentException e) {
                SeCorePlugin.logError(e);
            } catch (final InstantiationException e) {
                // FIXME Don't fail - Log this error and continue read the
                // assertion set
                throw new RuntimeException(e);
            } catch (final IllegalAccessException e) {
                // FIXME Don't fail - Log this error and continue read the
                // assertion set
                throw new RuntimeException(e);
            } catch (final ClassNotFoundException e) {
                // FIXME Don't fail - Log this error and continue read the
                // assertion set
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeAssertion(final IAssertion assertion) {
        if (this.assertions.remove(assertion)) {
            notifyListeners();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeChangeListener(final IChangeListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void saveSate(final IMemento memento) {

        memento.putString(AssertionSet.NAME_NODE, getName());
        memento.putString(AssertionSet.DESCRIPTION_NODE, getDescription());
        for (final IAssertion ass : this.assertions) {
            final IMemento instance = memento
                    .createChild(AssertionSet.ASSERTIONS_NODE);
            ass.saveState(instance);
            instance
                    .putString(AssertionSet.IMPL_NODE, ass.getClass().getName());
        }
    }

    public static IAssertionSet valueOf(final String name,
            final String description) {
        final AssertionSet set = new AssertionSet();
        set.name = name;
        set.description = description;
        return set;
    }

    @Override
    public String toString() {
        return getName() == null ? "N/A" : getName();
    }

    public boolean visitBegin(final Reason reason) {
        for (final IAssertion assertion : this.assertions) {
            assertion.visitBegin(reason);
        }
        return true;
    }

    public void visitEnd(final boolean end) {
        for (final IAssertion assertion : this.assertions) {
            assertion.visitEnd(end);
        }
    }

    public boolean visit(final IObject item) {
        for (final IAssertion assertion : this.assertions) {
            assertion.visit(item);
        }
        return true;
    }

    public IAssertionSetResult getResult() {
        final AssertionSetResult result = new AssertionSetResult(this);
        for (final IAssertion assertion : this.assertions) {
            result.results.add(assertion.getResult());
        }
        return result;
    }

    private static class AssertionSetResult implements IAssertionSetResult {

        private Boolean failed;

        /** The results. */
        private final List<IAssertionResult> results = new ArrayList<IAssertionResult>();

        /** The set. */
        private final IAssertionSet set;

        /**
         * The Constructor.
         * 
         * @param set
         *                the set
         */
        AssertionSetResult(final IAssertionSet set) {
            this.set = set;
        }

        /**
         * {@inheritDoc}
         */
        public IAssertionSet getAssertionSet() {
            return this.set;
        }

        public Iterator<IAssertionResult> iterator() {
            final List<IAssertionResult> copy = new ArrayList<IAssertionResult>(
                    this.results);

            Collections.sort(copy, new Comparator<IAssertionResult>() {
                public int compare(final IAssertionResult o1,
                        final IAssertionResult o2) {
                    if (o1.hasFailed() || o2.hasFailed()) {
                        if (o1.hasFailed() && o2.hasFailed()) {
                            return o1.getAssertion().getName().compareTo(
                                    o2.getAssertion().getName());
                        } else if (o1.hasFailed()) {
                            return -1;
                        } else if (o2.hasFailed()) {
                            return 1;
                        }
                    }
                    return o1.getAssertion().getName().compareTo(
                            o2.getAssertion().getName());
                }
            });

            return copy.iterator();
        }

        @Override
        public String toString() {
            return getAssertionSet().getName();
        }

        public boolean hasFailed() {
            if (this.failed != null) {
                return this.failed;
            }

            for (final IAssertionResult assertionResult : this) {
                if (assertionResult.hasFailed()) {
                    this.failed = Boolean.TRUE;
                    return true;
                }
            }
            this.failed = Boolean.FALSE;
            return false;
        }

    }

    public void initialize(final IDataSource data) { }

    public int getHitCount() {
        int hitCount = 0;
        for (final IAssertion assertion : this.assertions) {
            hitCount += assertion.getHitCount();
        }
        return hitCount;
    }

    public void resetHitCount() {
        for (final IAssertion assertion : this.assertions) {
            assertion.resetHitCount();
        }
    }

    public void setSaveResults(boolean save) {
        for (final IAssertion assertion : this.assertions) {
            assertion.setSaveResults(save);
        }
    }
}
