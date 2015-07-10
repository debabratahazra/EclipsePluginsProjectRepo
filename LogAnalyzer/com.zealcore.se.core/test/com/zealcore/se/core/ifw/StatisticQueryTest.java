package com.zealcore.se.core.ifw;

import static org.junit.Assert.*;
import static com.zealcore.se.core.ifw.ModelFactory.*;

import java.util.UUID;

import org.junit.Test;

import com.zealcore.se.core.model.ITask;

public class StatisticQueryTest {

    @Test
    public void testCalcUtil() {
        ITask bill = ModelFactory.newTask("Bill");
        ITask bull = ModelFactory.newTask("Bull");
        StatisticQuery subject = StatisticQuery.valueOf(Logset.valueOf(UUID
                .randomUUID()));
        subject.visitBegin(Reason.FILE_ADDED);
        subject.visit(ModelFactory.newTaskExec(bill, 0, 50));
        subject.visit(ModelFactory.newTaskExec(bull, 50, 100));
        subject.visit(ModelFactory.newTaskExec(bill, 100, 150));
        subject.visit(ModelFactory.newTaskExec(bull, 150, 200));
        subject.visitEnd(true);

        assertEquals(50d, bill.getUtilization(),0);
        assertEquals(50d, bull.getUtilization(),0);

    }

    @Test
    public void testCalcUtilBigNumber() {
        ITask bill = ModelFactory.newTask("Big");
        ITask bull = ModelFactory.newTask("Bugg");
        StatisticQuery subject = StatisticQuery.valueOf(Logset.valueOf(UUID
                .randomUUID()));
        subject.visitBegin(Reason.FILE_ADDED);
        subject.visit(ModelFactory.newTaskExec(bill, 0, 1000000000000L));
        subject.visit(ModelFactory.newTaskExec(bull, 1000000000000L,
                2 * 1000000000000L));
        subject.visitEnd(true);

        assertEquals(50d, bull.getUtilization(),0);
        assertEquals(50d, bill.getUtilization(),0);

    }

    @Test
    public void testPerformance() {
        StatisticQuery subject = StatisticQuery.valueOf(Logset.valueOf(UUID
                .randomUUID()));

        for (int i = 0; i < 100000; i++) {
            subject.visit(newEvent(i));
        }

    }
}
