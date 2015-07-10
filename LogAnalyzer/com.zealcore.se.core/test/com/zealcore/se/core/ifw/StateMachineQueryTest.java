package com.zealcore.se.core.ifw;

import static org.junit.Assert.*;
import static com.zealcore.se.core.ifw.ModelFactory.*;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.dl.StateMachineGraph;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IStateTransition;
import com.zealcore.se.core.model.ITimedTransition;

public class StateMachineQueryTest extends AbstractQueryTest {

    private StateMachineQuery subject;

    private IArtifact stateMachine;

    private long time = 100;

    private IStateTransition ping;

    private IState stateTo;

    @Before
    public void setUp() throws Exception {
        stateMachine = newStateMachine("FooStateMachine");

        subject = new StateMachineQuery(stateMachine, this);

        subject.initialize(this);
        subject.setCacheSize(50);

        populate(Reason.FILE_ADDED);

    }

    @Override
    IQuery getQuery() {
        return subject;
    }

    @Override
    protected void populate(final Reason reason) {
        subject.visitBegin(reason);

        final IState stateFrom = newState(stateMachine, "Bill");
        stateTo = newState(stateMachine, "Bob");
        ping = newStateTransition(stateFrom, stateTo);
        final IStateTransition pong = newStateTransition(stateTo, stateFrom);
        boolean atEnd = true;

        addArtifact(stateFrom);
        addArtifact(stateTo);
        addArtifact(ping);
        addArtifact(pong);
        // add artifact twice
        addArtifact(pong);

        for (int i = 0; i < 200; i += 20) {
            if (!subject.visit(newTimedTransition(ping, i, i + 5))) {
                atEnd = false;
                break;
            }
            if (!subject.visit(newTimedTransition(pong, i + 10, i + 15))) {
                atEnd = false;
                break;
            }
        }
        subject.visitEnd(atEnd);
    }

    @Test
    public void testGraph() throws Exception {
        StateMachineGraph graph = subject.getGraph();
        Collection<IState> states = graph.getStates();
        Collection<IStateTransition> trans = graph.getTransitions();

        assertEquals(2, states.size());
        assertEquals(2, trans.size());
    }

    @Test
    public void testNormal() throws Exception {
        final List<ITimedTransition> transitions = subject.getTimedTransitions(
                3, 3);
        assertEquals(6, transitions.size());

        long expectedTs = 70;
        for (final ITimedTransition timedTransition : transitions) {
            assertEquals(expectedTs, timedTransition.getStartTime());
            assertEquals(5L, timedTransition.getDurationTime());
            expectedTs += 10;
        }

    }

    @Test
    public void testAtBeginning() throws Exception {
        time = 0;

        final List<ITimedTransition> transitions = subject.getTimedTransitions(
                3, 3);
        assertEquals(3, transitions.size());

        long expectedTs = 0;
        for (final ITimedTransition timedTransition : transitions) {
            assertEquals(expectedTs, timedTransition.getStartTime());
            assertEquals(5L, timedTransition.getDurationTime());
            expectedTs += 10;
        }

    }

    @Test
    public void testNextByStateTransition() {
        setRefreshCalled(false);
        ITimedTransition next = subject.next(ping);
        assertNotNull(next);
        assertTrue(next.getTimeReference() > time);
    }

    @Test
    public void testNextByState() {
        setRefreshCalled(false);
        ITimedTransition next = subject.next(stateTo);
        assertNotNull(next);
        assertTrue(next.getTimeReference() > time);
    }

    @Test
    public void testPreviousByStateTransition() {
        setRefreshCalled(false);
        ITimedTransition next = subject.previous(ping);
        assertTrue(next.getTimeReference() < time);
    }

    @Test
    public void testPreviousByState() {
        setRefreshCalled(false);

        ITimedTransition next = subject.previous(stateTo);
        assertNotNull(next);
        assertTrue(next.getTimeReference() < time);
        while (next != null) {
            time = next.getStartTime();
            next = subject.previous(stateTo);
            if (next != null) {
                assertTrue(next.getTimeReference() < time);
            }
        }
        assertEquals(0L,time);

    }

    @Test
    public void testAtEnd() throws Exception {
        time = 200;

        final List<ITimedTransition> transitions = subject.getTimedTransitions(
                3, 3);
        assertEquals(3, transitions.size());

        long expectedTs = 170;
        for (final ITimedTransition timedTransition : transitions) {
            assertEquals(expectedTs, timedTransition.getStartTime());
            assertEquals(5L, timedTransition.getDurationTime());
            expectedTs += 10;
        }

    }

    public long getCurrentTime() {
        return time;
    }
}
