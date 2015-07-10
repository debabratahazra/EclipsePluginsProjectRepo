/*
 * 
 */
package com.zealcore.se.core.ifw.assertions;

import org.eclipse.ui.IMemento;

import com.zealcore.se.core.SearchAdapter;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.ifw.AbstractQuery;
import com.zealcore.se.core.ifw.IDataSource;
import com.zealcore.se.core.ifw.Reason;
import com.zealcore.se.core.model.IObject;

public final class Assertion extends AbstractQuery implements IAssertion {

    private static final String NAME_NODE = "name";

    private static final String DESCRIPTION_NODE = "description";

    private static final String SEVERITY_NODE = "severity";

    private static final String MATCHER_NODE = "checks";

    /** The description. */
    private String description;

    /** The severity. */
    private int severity;

    /** The name. */
    private String name;

    /** The adapter. */
    private SearchAdapter adapter;

    private AssertionResult assertionResult;
    
    private int hitCount;

    private boolean save;
    /**
     * Creates a new IAssertion representing the specified name, description,
     * severity and adapter values.
     * 
     * @param adapter
     *                the adapter
     * @param severity
     *                the severity
     * @param description
     *                the description
     * @param name
     *                the name
     * 
     * @return the IAssertion
     */
    public static IAssertion valueOf(final String name,
            final String description, final int severity,
            final SearchAdapter adapter) {

        final Assertion assertion = new Assertion();

        assertion.setDescription(description);
        assertion.setName(name);
        assertion.setSeverity(severity);

        assertion.adapter = adapter;

        return assertion;

    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return this.description == null ? "" : description;
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return this.name == null ? "" : name;
    }

    /**
     * {@inheritDoc}
     */
    public int getSeverity() {
        return this.severity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean visit(final IObject item) {
        if (this.assertionResult == null) {
            throw new IllegalStateException(
                    "The Assertion has not been initialized properly. Please call begin() prior to visit");
        }
        if (this.adapter.filter(item)) {
            hitCount++;
            if (save) {
                this.assertionResult.addFailed(item);
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void init(final IMemento memento, final ITypeRegistry typeService) {

        setName(memento.getString(Assertion.NAME_NODE));
        setDescription(memento.getString(Assertion.DESCRIPTION_NODE));
        setSeverity(memento.getInteger(Assertion.SEVERITY_NODE));
        final IMemento child = memento.getChild(Assertion.MATCHER_NODE);
        this.adapter = SearchAdapter.valueOf(child, typeService);
    }

    /**
     * {@inheritDoc}
     */
    public void saveState(final IMemento memento) {
        memento.putString(Assertion.NAME_NODE, getName());
        memento.putString(Assertion.DESCRIPTION_NODE, getDescription());
        memento.putInteger(Assertion.SEVERITY_NODE, getSeverity());
        final IMemento matcherMemento = memento
                .createChild(Assertion.MATCHER_NODE);
        this.adapter.saveState(matcherMemento);
    }

    public SearchAdapter getSearchAdapter() {
        return this.adapter;
    }

    @Override
    public String toString() {
        return getName() == null ? "N/A" : getName();
    }

    public void setDescription(final String description) {
        this.description = description == null ? "" : description;
    }

    public void setName(final String name) {
        this.name = name == null ? "" : name;
    }

    public void setSeverity(final int severity) {
        this.severity = severity;
    }

    public void setSearchAdapter(final SearchAdapter adapter) {
        this.adapter = adapter;
    }

    public String getCondition() {
        return getSearchAdapter().getCondition();
    }

    public boolean visitBegin(final Reason r) {
        this.assertionResult = new AssertionResult(this);
        hitCount = 0;
        save = true;
        return true;
    }

    public void visitEnd(final boolean end) {
    }

    public IAssertionResult getResult() {
        return this.assertionResult;
    }

    public void initialize(final IDataSource data) {}

    public int getHitCount() {
        return hitCount;
    }

    public void resetHitCount() {
        hitCount = 0;
    }
    
    public void setSaveResults(boolean save) {
        this.save = save;
    }
}
