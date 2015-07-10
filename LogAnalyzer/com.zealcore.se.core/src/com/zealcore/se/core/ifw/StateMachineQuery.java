package com.zealcore.se.core.ifw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zealcore.se.core.dl.StateMachineGraph;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IStateTransition;
import com.zealcore.se.core.model.ITimedTransition;

public final class StateMachineQuery extends AbstractQuery {

    private final IArtifact stateMachine;

    private IDataSource dataSource;

    private final StateMachineGraph statMachineGraph;

    private final TimeCache<ITimedTransition> cache;

    private Map<IObject, TimeCache<ITimedTransition>> nextPrev = new HashMap<IObject, TimeCache<ITimedTransition>>();

    public StateMachineQuery(final IArtifact stateMachine,
            final ITimeProvider time) {
        this.stateMachine = stateMachine;
        statMachineGraph = new StateMachineGraph(this.stateMachine);
        cache = new TimeCache<ITimedTransition>(time);
    }

    public void initialize(final IDataSource data) {
        dataSource = data;
    }

    public boolean visit(final IObject item) {
        if (isFull()) {
            return false;
        }
        if (item instanceof ITimedTransition) {
            final ITimedTransition tt = (ITimedTransition) item;
            if (matches(tt)) {
                add(tt);
            }
        }
        return true;
    }

    private boolean isFull() {
        boolean isFull = cache.isFull();
        for (TimeCache<ITimedTransition> timeCache : nextPrev.values()) {
            if (!timeCache.isFull()) {
                return false;
            }
        }
        return isFull;
    }

    private void add(final ITimedTransition tt) {
        cache.putInCache(tt);
        IStateTransition t = tt.getTransition();
        TimeCache<ITimedTransition> tCache = nextPrev.get(t);
        if (tCache != null && !tCache.isFull()) {
            tCache.put(tt);
        }
        TimeCache<ITimedTransition> sCache = nextPrev.get(t.getTo());
        if (sCache != null && !sCache.isFull()) {
            sCache.put(tt);
        }
    }

    private boolean matches(final ITimedTransition tt) {
        IStateTransition transition = tt.getTransition();
        if (transition == null) {
            return false;
        }
        return transition.getTo().getParent().equals(stateMachine);
    }

    public StateMachineGraph getGraph() {
        return statMachineGraph;
    }

    private void process(final IState state) {
        if (state.getParent().equals(stateMachine)) {
            statMachineGraph.addState(state);
            if (!nextPrev.containsKey(state)) {
                nextPrev.put(state, new TimeCache<ITimedTransition>(cache
                        .getTime(), 5));
            }
        }
    }

    private void process(final IStateTransition stateTransition) {
        if (stateTransition.getFrom().getParent().equals(stateMachine)) {
            statMachineGraph.addTransition(stateTransition);
            if (!nextPrev.containsKey(stateTransition)) {
                nextPrev.put(stateTransition, new TimeCache<ITimedTransition>(
                        cache.getTime(), 5));
            }
        }
    }

    public boolean visitBegin(final Reason reason) {
        cache.clear();
        for (TimeCache<ITimedTransition> timeCache : nextPrev.values()) {
            timeCache.clear();
        }
        return true;
    }

    public void visitEnd(final boolean atEnd) {
        cache.setAtEnd(atEnd);

        for (TimeCache<ITimedTransition> timeCache : nextPrev.values()) {
            timeCache.setAtEnd(atEnd);
        }
        statMachineGraph.clear();
        for (IArtifact item : dataSource.getArtifacts()) {
            if (item instanceof IStateTransition) {
                process((IStateTransition) item);
            }
            if (item instanceof IState) {
                process((IState) item);
            }
        }
    }

    public List<ITimedTransition> getTimedTransitions(final int backward,
            final int forward) {
        if (dataSource == null) {
            throw new IllegalStateException("call initialize first");
        }
        List<ITimedTransition> list = cache.get(backward, forward);
        if (list == null) {
            dataSource.refresh();
            list = getTimedTransitions(backward, forward);
        }
        return list;
    }

    void setCacheSize(final int size) {
        cache.setSize(size);
    }

    /**
     * NOTE/Experiemental: This method may likely return null, since it will
     * only look in it's current cache
     * 
     * Returns the next (as in the first having start time > now)
     * {@link ITimedTransition} that were taken on the supplied transition
     * 
     * @param trans
     * @return -
     */
    public ITimedTransition next(final IStateTransition trans) {
        long time = cache.getCurrentTime();
        /*
         * Look in the big cache first - this is an optimization as it is more
         * likely it will be here
         */
        for (ITimedTransition transition : cache) {
            if (transition.getStartTime() > time
                    && transition.getTransition().equals(trans)) {
                return transition;
            }
        }
        return nextViaSmall(trans);
    }

    private ITimedTransition nextViaSmall(final IObject trans) {
        long time = cache.getCurrentTime();
        /* If not found in the big cache, look in the small cache */
        TimeCache<ITimedTransition> tCache = nextPrev.get(trans);
        List<ITimedTransition> data = tCache.get(0, 2);
        /* No such element found */
        if (data == null) {
            dataSource.refresh();
        }
        data = tCache.get(0, 2);
        for (ITimedTransition transition : data) {
            if (transition.getTimeReference() > time) {
                return transition;
            }
        }
        return null;
    }

    private ITimedTransition prevViaSmall(final IObject trans) {
        ITimedTransition prev = null;
        long time = cache.getCurrentTime();
        TimeCache<ITimedTransition> tCache = nextPrev.get(trans);
        List<ITimedTransition> data = tCache.get(2, 0);
        /* No such element found */
        if (data == null) {
            dataSource.refresh();
        }
        data = tCache.get(2, 0);
        for (ITimedTransition transition : data) {
            if (transition.getTimeReference() < time) {
                prev = transition;
            }
        }
        return prev;
    }

    /**
     * NOTE/Experiemental: This method may likely return null, since it will
     * only look in it's current cache
     * 
     * Returns the next (as in the first having start time > now)
     * {@link ITimedTransition} that would to the graph being in the state
     * 
     * @param to
     * @return -
     */
    public ITimedTransition next(final IState to) {
        long time = cache.getCurrentTime();
        for (ITimedTransition transition : cache) {
            if (transition.getStartTime() > time
                    && transition.getTransition().getTo().equals(to)) {
                return transition;
            }
        }
        return nextViaSmall(to);
    }

    /**
     * NOTE/Experiemental: This method may likely return null, since it will
     * only look in it's current cache
     * 
     * Returns the previous (as in having start time < now)
     * {@link ITimedTransition} that followed the supplied transition
     * 
     * @param trans
     * @return -
     */
    public ITimedTransition previous(final IStateTransition trans) {
        ITimedTransition previous = null;
        long time = cache.getCurrentTime();
        for (ITimedTransition transition : cache) {
            if (transition.getStartTime() < time
                    && transition.getTransition().equals(trans)) {
                previous = transition;
            }
        }
        if (previous != null) {
            return previous;
        }

        /* If not found in the big cache, look in the small cache */
        return prevViaSmall(trans);

    }

    /**
     * NOTE/Experiemental: This method may likely return null, since it will
     * only look in it's current cache
     * 
     * 
     * Returns the previous (as in having start time < now)
     * {@link ITimedTransition} that if taken would to lead to the graph being
     * the {@link IState} to.
     * 
     * @param to
     * @return -
     */
    public ITimedTransition previous(final IState to) {
        ITimedTransition previous = null;
        for (ITimedTransition transition : cache) {
            if (transition.getStartTime() < cache.getCurrentTime()
                    && transition.getTransition().getTo().equals(to)) {
                previous = transition;
            }
        }
        if (previous != null) {
            return previous;
        }
        return prevViaSmall(to);
    }

    @Override
    public void setStart(final boolean start) {
        cache.setAtStart(start);
    }

    @Override
    public void setEnd(final boolean end) {
        cache.setAtEnd(end);
    }
}
