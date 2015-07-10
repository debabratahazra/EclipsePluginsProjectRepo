package com.zealcore.se.core.ifw;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.IFilter;
import com.zealcore.se.core.ImportOperationCancelledException;
import com.zealcore.se.core.model.ILogEvent;
import com.zealcore.se.core.model.IObject;
import com.zealcore.se.core.model.ITask;
import com.zealcore.se.core.model.ITaskDuration;

public class GanttQueryTest extends AbstractQueryTest {

    private static final String TEST_TASK_1 = "TestTask 1";

    private static final String TEST_TASK_2 = "TestTask 2";

    private GanttQuery subject;

    private long time = 200;

    @Override
    IQuery getQuery() {
        return subject;
    }

    @Before
    public void setUp() {
    	Logset  logset = Logset.valueOf(UUID.randomUUID());
    	try {
			logset.addLog(new TestAdapter((ILogEvent)null));
		} catch (ImportOperationCancelledException e) {
			e.printStackTrace();
		}	
        subject = new GanttQuery(this);
        subject.setLogset(logset);
        subject.initialize(subject.getLogset());

    }

    @Test
    public void testNormal() {
        populate(Reason.FILE_ADDED);
        List<ITaskDuration> data = subject.getExecutions(10, 10);
        assertEquals(20, data.size());
        long ts = 100;
        for (final ITaskDuration duration : data) {
            assertEquals(ts, duration.getStartTime());
            ts += 10;
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFilter() {
        System.out.println("start!");
        populate(Reason.FILE_ADDED);

        final List<IFilter<IObject>> filter = new ArrayList<IFilter<IObject>>();

        // Filter out every second event.
        for (final ITaskDuration iTask : subject.getExecutions(10, 10)) {
            filter.add(new TaskFilter(iTask));
            break;
        }
        // subject.setFilter(filter);

        System.out.println(filter.toString());

        final List<ITaskDuration> data = subject.getExecutions(10, 10);
        for (final ITaskDuration i : data) {
            System.out.println(i);
        }
        // for (final ITask iTask : subject.getTasks()) {
        // System.out.println(iTask.getName());
        // }
        //
        // assertEquals(20, data.size());
        // final long ts = 0;
        // for (final ITaskDuration duration : data) {
        // assertEquals(ts, duration.getStartTime());
        // System.out.println(duration.getStartTime());
        // ts += 20;
        // }
        // System.out.println("done!");
    }

    public static class TaskFilter implements IFilter {

        private final Object out;

        public TaskFilter(final Object a) {
            super();
            out = a;
        }

        public boolean filter(final Object x) {
            if (x instanceof ITaskDuration) {
                final ITaskDuration d = (ITaskDuration) x;
                final ITask other = d.getOwner();
                boolean isEq = out.equals(other);
                return !isEq;
            }
            return true;
        }

        @Override
        public String toString() {
            return out.toString();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (out == null ? 0 : out.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TaskFilter other = (TaskFilter) obj;
            if (out == null) {
                if (other.out != null) {
                    return false;
                }
            } else if (!out.equals(other.out)) {
                return false;
            }
            return true;
        }
    }

    @Test
    public void testAtStart() {
        populate(Reason.FILE_ADDED);
        time = 0;
        List<ITaskDuration> data = subject.getExecutions(10, 10);
        assertEquals(10, data.size());
        long ts = 0;
        for (final ITaskDuration duration : data) {
            assertEquals("Unexpected timestamps", ts, duration.getStartTime());
            ts += 10;
        }
    }

    @Test
    public void testAtEnd() {
        populate(Reason.FILE_ADDED);
        time = 400;
        List<ITaskDuration> data = subject.getExecutions(10, 10);
        assertEquals(10, data.size());

        long ts = 300;
        for (final ITaskDuration duration : data) {
            assertEquals(ts, duration.getStartTime());
            ts += 10;
        }
    }

    public long getCurrentTime() {
        return time;
    }

    @Override
    protected void populate(final Reason reason) {
        subject.visitBegin(reason);
        boolean atEnd = true;
        final ITask task1 = ModelFactory.newTask(TEST_TASK_1);
        final ITask task2 = ModelFactory.newTask(TEST_TASK_2);
        for (int i = 0; i < 400; i += 10) {
            if (i % 20 == 0) {
                if (!subject.visit(ModelFactory.newTaskExec(task1, i, i + 10))) {
                    atEnd = false;
                    break;
                }
            } else {
                if (!subject.visit(ModelFactory.newTaskExec(task2, i, i + 10))) {
                    atEnd = false;
                    break;
                }
            }
        }
        subject.visitEnd(atEnd);
    }

}
