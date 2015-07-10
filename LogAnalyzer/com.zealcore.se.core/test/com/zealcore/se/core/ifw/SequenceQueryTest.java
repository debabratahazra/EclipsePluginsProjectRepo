package com.zealcore.se.core.ifw;

import static com.zealcore.se.core.ifw.ModelFactory.newActivity;
import static com.zealcore.se.core.ifw.ModelFactory.newMember;
import static com.zealcore.se.core.ifw.ModelFactory.newMessage;
import static com.zealcore.se.core.ifw.ModelFactory.newSquence;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.zealcore.se.core.ifw.SequenceQuery.Data;
import com.zealcore.se.core.model.IActivity;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;
import com.zealcore.se.core.model.ITimed;

public class SequenceQueryTest extends AbstractQueryTest {

    private SequenceQuery subject;

    private ISequence sequence;

    private int time = 200;

    private boolean isRefreshed;

    @Before
    public void setUp() {
        sequence = newSquence("TestSequence");
        subject = new SequenceQuery(sequence, this);
        subject.initialize(this);
        subject.setCacheSize(20);
        populate(Reason.FILE_ADDED);
        isRefreshed = false;
    }

    @Override
    public void populate(final Reason reason) {
        final ISequenceMember m = newMember(sequence, "Foo");
        addArtifact(m);
        boolean atEnd = true;
        subject.visitBegin(reason);
        for (int i = 0; i < 400; i += 10) {

            if (!subject.visit(newActivity(m, i, i + 10))) {
                atEnd = false;
                break;
            }

            subject.visit(newMessage(i + 2, m, m));
            subject.visit(newMessage(i + 4, m, m));
            subject.visit(newMessage(i + 6, m, m));
            subject.visit(newMessage(i + 8, m, m));
        }
        subject.visitEnd(atEnd);

        isRefreshed = true;

    }

    @Test
    public void testNormal() {
        final Data data = subject.getData(10, 10);

        assertEquals(200, time);

        final List<ITimed> elements = data.getElements();
        final List<ISequenceMessage> messages = data.getMessages();
        assertEquals(20, elements.size());
        assertEquals(16, messages.size());
        ITimed element = elements.get(0);
        if (element instanceof IActivity) {
            IActivity activity = (IActivity) element;
            assertEquals(180L, activity.getStartTime());
        }
        element = elements.get(19);
        if (element instanceof IActivity) {
            IActivity activity = (IActivity) element;
            assertEquals(218L, activity.getStartTime());
        }
        assertEquals(182L, messages.get(0).getTs());
    }

    @Test
    public void testAtStart() {
        time = 0;
        Data data = subject.getData(10, 10);
        final List<ITimed> elements = data.getElements();
        long ts = 0;
        for (final ITimed a : elements) {
            assertEquals(ts, a.getTimeReference().longValue());
            ts += 2;
        }
        assertEquals(10, elements.size());
        assertTrue(isRefreshed);

        List<ISequenceMessage> msgResults = subject.getData(10, 10).getMessages();
        assertEquals(8, msgResults.size());
    }

    @Test
    public void testAtEnd() {
        time = 400;
        final List<ITimed> elements = subject.getData(10, 10).getElements();
        final List<ISequenceMessage> msg = subject.getData(10, 10).getMessages();
        assertEquals(10, elements.size());
        assertEquals(8, msg.size());
        long ts = 380;
        for(ITimed a : elements) {
            assertEquals(ts, a.getTimeReference().longValue());
            ts += 2;
        }
        assertTrue(isRefreshed);
    }


    @Test
    public void testGetMembers() {

        String another = "Another";
        addArtifact(newMember(newSquence("NotSame"), another));
        addArtifact(newMember(sequence, another));
        Collection<ISequenceMember> members = subject.getMembers();
        assertEquals(2, members.size());
    }

    public long getCurrentTime() {
        return time;
    }

    @Override
    IQuery getQuery() {
        return subject;
    }
}
