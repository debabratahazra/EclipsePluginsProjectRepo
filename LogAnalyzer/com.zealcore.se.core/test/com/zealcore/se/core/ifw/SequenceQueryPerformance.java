package com.zealcore.se.core.ifw;

import static com.zealcore.se.core.ifw.ModelFactory.newActivity;
import static com.zealcore.se.core.ifw.ModelFactory.newMember;
import static com.zealcore.se.core.ifw.ModelFactory.newSquence;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zealcore.se.core.ifw.SequenceQuery.Data;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;

public class SequenceQueryPerformance extends AbstractQueryTest {

    private static final int MAX = 1000000;

    private SequenceQuery subject;

    private ISequence seq;

    @Test
    public void testPerformance() {
        seq = newSquence("Seq");
        subject = new SequenceQuery(seq, this);
        subject.initialize(this);
        long start = System.currentTimeMillis();
        populate(Reason.FILE_ADDED);
        Data data = subject.getData(10, 10);
        long end = System.currentTimeMillis();
        System.out.println("Took: " + (end - start) + " to do " + MAX);
        assertEquals(11, data.getElements().size());

    }

    @Override
    IQuery getQuery() {
        return subject;
    }

    @Override
    protected void populate(final Reason r) {
        ISequenceMember m = newMember(seq, "Foo");

        boolean atEnd = true;
        for (int i = 0; i < MAX && atEnd; i++) {
            atEnd = getQuery().visit(newActivity(m, 0, 1));

        }
        if (atEnd) {
            getQuery().visit(newActivity(m, 1, 2));
        }
        getQuery().visitEnd(atEnd);
    }

    public long getCurrentTime() {
        return 1;
    }
}
