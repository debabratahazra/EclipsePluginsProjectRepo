package com.zealcore.se.core.ifw;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import com.zealcore.se.core.model.ITask;

public class TaskQueryTest {
    @Test
    public void testCalcUtil() {
        ITask bill = ModelFactory.newTask("Bill");
        ITask bull = ModelFactory.newTask("Bull");
        ITask intBull = ModelFactory.newTask("IntBull");
        TaskQuery subject = TaskQuery
                .valueOf(Logset.valueOf(UUID.randomUUID()));
        subject.visitBegin(Reason.FILE_ADDED);
        assertEquals(0, subject.getExecutedTasks().size());
        assertEquals(0, subject.getNotExecutedTasks().size());

        subject.visit(ModelFactory.newTaskExec(bill, 0, 50));
        assertEquals(1, subject.getExecutedTasks().size());
        assertEquals(0, subject.getNotExecutedTasks().size());
        subject.visit(ModelFactory.newTaskExec(bull, 50, 100));
        subject.visit(ModelFactory.newTaskExec(bill, 100, 150));
        subject.visit(ModelFactory.newTaskExec(bull, 150, 200));
        assertEquals(2, subject.getExecutedTasks().size());
        assertEquals(0, subject.getNotExecutedTasks().size());
        subject.visit(bill);
        subject.visit(bull);
        assertEquals(2, subject.getExecutedTasks().size());
        assertEquals(0, subject.getNotExecutedTasks().size());
        subject.visit(intBull);
        subject.visitEnd(true);
        assertEquals(2, subject.getExecutedTasks().size());
        assertEquals(true, subject.getExecutedTasks().contains(bill));
        assertEquals(true, subject.getExecutedTasks().contains(bull));
        assertEquals(1, subject.getNotExecutedTasks().size());
        assertEquals(true, subject.getNotExecutedTasks().contains(intBull));
    }
}
