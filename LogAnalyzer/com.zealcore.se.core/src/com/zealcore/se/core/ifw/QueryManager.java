package com.zealcore.se.core.ifw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.zealcore.se.core.SeCorePlugin;
import com.zealcore.se.core.dl.ILogSessionItemListener;
import com.zealcore.se.core.dl.ITypePackage;
import com.zealcore.se.core.dl.ITypeRegistry;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.generic.IGenericLogItem;

class QueryManager implements ILogSessionItemListener {

    private final List<IQuery> queries = new ArrayList<IQuery>();

    private IProgressMonitor mon;

    private List<IQuery> activeQueries;

    private ITypeRegistry registry;

    private Logset logset;
    
    private ITypePackage[] packages;

    public void add(final IQuery query) {
        if (!queries.contains(query)) {
            query.setLogset(logset);
            queries.add(query);
        } else {
            throw new IllegalStateException(
                    "Trying to add an already existing query to logset.");
        }
    }

    public void remove(final IQuery query) {
        queries.remove(query);

    }

    public boolean visit(final IObject item) {
        if (activeQueries == null) {
            throw new IllegalStateException(
                    "Internal error, visit method is called before visitBegin. Corrupt metadata?");
        }
        if (activeQueries == null || activeQueries.isEmpty()) {
            return false;
        }
        mon.worked(1);

        if (item instanceof ILogEvent) {
            final ILogEvent event = (ILogEvent) item;
            ITypePackage pkg = registry.getTypePackage(item.getType());
            for (final ITypePackage temp : packages) {
                if (temp.getClass().getName().equals(pkg.getClass().getName())) {
                    temp.processLogEvent(event);
                    break;
                }
            }
        } else if (item instanceof IGenericLogItem) {
            /*
             * This call adds the given type to the generic type package
             */
            item.getType();
        }
        if (activeQueries == null) {
            return false;
        }
        for (final Iterator<IQuery> iter = activeQueries.iterator(); iter
                .hasNext();) {
            final IQuery q = iter.next();
            if (!q.visit(item)) {
                iter.remove();
            }
        }
        
        return true;
    }

    public void visitBegin(final Reason reason) {
        activeQueries = new ArrayList<IQuery>(queries);
        for (final Iterator<IQuery> iter = activeQueries.iterator(); iter
                .hasNext();) {
            final IQuery q = iter.next();
            if (!q.visitBegin(reason)) {
                iter.remove();
            }
        }
        packages = getTypeRegistry().getTypePackages();
        for (final ITypePackage pkg : packages) {
            pkg.begin();
            pkg.addLogsessionItemListener(this);
        }
    }

    private ITypeRegistry getTypeRegistry() {
        registry = SeCorePlugin.getDefault().getService(ITypeRegistry.class);
        return registry;
    }

    public void visitEnd(final boolean atEnd) {
        for (final IQuery q : activeQueries) {
            q.visitEnd(atEnd);
        }

        for (final IQuery q : queries) {
            if (!activeQueries.contains(q)) {
                q.visitEnd(false);
            }
        }

        activeQueries.clear();
        activeQueries = null;

        for (final ITypePackage pkg : packages) {
            pkg.removeLogsessionItemListener(this);
            pkg.end();
        }
        packages = null;

    }

    void setProgressMonitor(final IProgressMonitor mon) {
        this.mon = mon;
    }

    public void logFileItemCreated(final IObject abstractLogFileItem) {
        visit(abstractLogFileItem);
    }

    public void setLogset(final Logset logset) {
        this.logset = logset;
    }

    public void setStart(final boolean start) {
        if (activeQueries != null) {
            for (final IQuery q : activeQueries) {
                q.setStart(start);
            }
        }
    }

    public void setEnd(final boolean end) {
        if (activeQueries != null) {
            for (final IQuery q : activeQueries) {
                q.setEnd(end);
            }
        }
    }

    public void inValidate() {
        this.visitEnd(true);
        queries.clear();
    }
    
    public boolean isQuerySetEmpty() {
        if(activeQueries == null) return true;
        return activeQueries.size() == 0;
    }

}
