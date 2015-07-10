package com.zealcore.se.core.ifw;

import com.zealcore.se.core.model.AbstractActivity;
import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.AbstractDuration;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.AbstractProcessSwitchEvent;
import com.zealcore.se.core.model.AbstractSequence;
import com.zealcore.se.core.model.AbstractState;
import com.zealcore.se.core.model.AbstractStateTransition;
import com.zealcore.se.core.model.AbstractTimedTransition;
import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.IArtifact;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IProcessSwitch;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.IState;
import com.zealcore.se.core.model.IStateTransition;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;
import com.zealcore.se.core.model.ITimedTransition;
import com.zealcore.se.core.model.LogFile;

final class ModelFactory {

    private static LogFile logFile = new LogFile();

    private ModelFactory() {

    }

    static IActivity newActivity(final ISequenceMember m, final long ts0,
            final long ts1) {
        final AbstractActivity act = new AbstractActivity() {};
        act.setStartEvent(ModelFactory.newEvent(ts0));
        act.setStopEvent(ModelFactory.newEvent(ts1));
        act.setOwner(m);
        return act;

    }

    static ILogEvent newEvent(final long ts) {
        final AbstractLogEvent e = new AbstractLogEvent() {};
        e.setTs(ts);
        return e;

    }

    static ISequenceMessage newMessage(final long ts,
            final ISequenceMember sender, final ISequenceMember recipent) {
        final Message m = new Message();
        m.setTs(ts);
        m.setSender(sender);
        m.setRecipent(recipent);
        return m;

    }

    static ISequenceMember newMember(final ISequence parent, final String name) {
        final SeqMember m = new SeqMember();
        m.setName(name);
        m.setParent(parent);
        return m;
    }

    private static class SeqMember extends AbstractArtifact implements
            ISequenceMember {

        private ISequence parent;

        public ISequence getParent() {
            return parent;
        }

        public void setParent(final ISequence parent) {
            this.parent = parent;

        }

    }

    private static class Message extends AbstractLogEvent implements
            ISequenceMessage {

        private ISequenceMember recipent;

        private ISequenceMember sender;

        private long drawingTs;

        public long getDeliveryTime() {
            return 0;
        }

        public String getMessage() {
            return "NoMessage";
        }

        public ISequenceMember getRecipent() {
            return recipent;
        }

        public ISequenceMember getSender() {
            return sender;
        }

        public void setRecipent(final ISequenceMember recipent) {
            this.recipent = recipent;
        }

        public void setSender(final ISequenceMember sender) {
            this.sender = sender;
        }

        public long getDrawingTs() {
            return drawingTs;
        }

        public void setDrawingTs(long ts) {
            this.drawingTs = ts;
        }
    }

    public static ISequence newSquence(final String name) {
        final AbstractSequence seq = new AbstractSequence() {};
        seq.setName(name);
        return seq;
    }

    public static IArtifact newStateMachine(final String name) {
        final IArtifact stateMachine = new AbstractArtifact() {};
        stateMachine.setName(name);
        return stateMachine;
    }

    public static IState newState(final IArtifact stateMachine,
            final String name) {
        final AbstractState state = new AbstractState() {};
        state.setName(name);
        state.setParent(stateMachine);
        return state;
    }

    public static IStateTransition newStateTransition(final IState stateFrom,
            final IState stateTo) {
        final AbstractStateTransition stateTransition = new AbstractStateTransition() {};
        initializeArtifact(stateTransition, stateFrom.getName() + "-"
                + stateTo.getName());
        stateTransition.setFrom(stateFrom);
        stateTransition.setTo(stateTo);

        return stateTransition;
    }

    private static void initializeArtifact(final IArtifact artifact,
            final String name) {
        artifact.setLogFile(logFile);
        artifact.setName(name);
    }

    public static ITimedTransition newTimedTransition(
            final IStateTransition stateTransition, final int t0, final int t1) {
        final AbstractTimedTransition tt = new AbstractTimedTransition() {};
        tt.setStartEvent(newEvent(t0));
        tt.setStopEvent(newEvent(t1));
        tt.setTransition(stateTransition);
        return tt;
    }

    public static ITask newTask(final String name) {
        final ITask task = new Task();
        task.setName(name);
        return task;
    }

    public static ITaskDuration newTaskExec(final ITask task, final long t0,
            final long t1) {

        final TaskDuration exe = new TaskDuration();
        exe.setOwner(task);
        exe.setStartEvent(new TaskSwitch(t0));
        exe.setStopEvent(newEvent(t1));
        return exe;
    }

    private static class Task extends AbstractArtifact implements ITask {

        private double util;

        public double getUtilization() {
            return util;
        }

        public void setUtilization(final double util) {
            this.util = util;
        }

        // not visible for the user
        public int getPriority() {
            return 0;
        }

    }

    private static class TaskSwitch extends AbstractProcessSwitchEvent {
        TaskSwitch(final long ts) {
            setTs(ts);
        }

        public int getPriority() {
            return 0;
        }
    }

    private static class TaskDuration extends AbstractDuration implements
            ITaskDuration {

        @Override
        public ITask getOwner() {
            return (ITask) super.getOwner();
        }

        @Override
        public IProcessSwitch getStartEvent() {
            return (IProcessSwitch) super.getStartEvent();
        }
    }
}
