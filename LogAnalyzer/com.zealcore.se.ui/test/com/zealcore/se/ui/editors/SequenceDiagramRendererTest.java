/*
 * 
 */
package com.zealcore.se.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import junit.framework.Assert;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

import com.zealcore.se.core.model.AbstractArtifact;
import com.zealcore.se.core.model.AbstractLogEvent;
import com.zealcore.se.core.model.ISequence;
import com.zealcore.se.core.model.ISequenceMember;
import com.zealcore.se.core.model.ISequenceMessage;

public final class SequenceDiagramRendererTest {

    public SequenceDiagramRendererTest() {
    // 
    }

    private static class LogEvent extends AbstractLogEvent {
        LogEvent(final long ts) {
            this.setTs(ts);
        }

    }

    private static class Actor extends AbstractArtifact implements
            ISequenceMember {

        private ISequence parent;

        public Actor(final String label) {
            this.setName(label);

        }

        public ISequence getParent() {
            return this.parent;
        }

        public void setParent(final ISequence parent) {
            this.parent = parent;

        }

    }

    private static class Message extends LogEvent implements ISequenceMessage {

        private ISequenceMember sender;

        private ISequenceMember recipent;

        private String message = "ping()";

        private long drawingTs;

        public Message(final Actor sender, final Actor recipent, final int ts) {
            super(ts);
            this.setSender(sender);
            this.setRecipent(recipent);

        }

        public void setMessage(final String message) {
            this.message = message;

        }

        public ISequenceMember getRecipent() {
            return this.recipent;
        }

        public ISequenceMember getSender() {
            return this.sender;
        }

        public void setRecipent(final ISequenceMember recipent) {
            this.recipent = recipent;

        }

        public void setSender(final ISequenceMember sender) {
            this.sender = sender;
        }

        public String getMessage() {
            return this.message;
        }

        public long getDeliveryTime() {
            return 0;
        }

        public long getDrawingTs() {
            return drawingTs;
        }

        public void setDrawingTs(long ts) {
            this.drawingTs = ts;
        }

    }

    @Test
    public void testMocks() {
        final String name = "Kaz";
        final Actor actor = new Actor(name);
        Assert.assertEquals(actor, actor);
        Assert.assertEquals(actor.hashCode(), actor.hashCode());

        final Actor actor2 = new Actor(name);
        Assert.assertEquals(actor, actor2);
        Assert.assertEquals(actor.hashCode(), actor2.hashCode());
    }

    @Test
    public void testIsVisible() {
        final SequenceDiagramRenderer subject = new SequenceDiagramRenderer();

        final Rectangle clip = new Rectangle(0, 0, 10, 10);

        Assert.assertTrue(subject.isVisible(clip, new Rectangle(0, 0, 1, 1)));
        Assert.assertTrue(subject.isVisible(clip, new Rectangle(1, 1, 1, 1)));
        Assert.assertTrue(subject.isVisible(clip, new Rectangle(9, 9, 1, 1)));
        Assert.assertTrue(subject.isVisible(clip, new Rectangle(9, 9, 10, 10)));
        Assert.assertTrue(subject
                .isVisible(clip, new Rectangle(-1, -1, 10, 10)));

        Assert.assertFalse(subject.isVisible(clip,
                new Rectangle(11, 11, 10, 10)));

    }

    @Test
    public void testExtractActors() {
        final SequenceDiagramRenderer subject = new SequenceDiagramRenderer();

        final LinkedHashSet<ISequenceMember> empty = new LinkedHashSet<ISequenceMember>();

        final Actor actor = new Actor("foo");
        final Message message = new Message(actor, actor, 0);
        final ArrayList<ISequenceMessage> msgs = new ArrayList<ISequenceMessage>();
        msgs.add(message);
        subject.getData().setMessages(msgs);

        final Collection<? extends ISequenceMember> extracted = subject
                .extractActors(subject.getData(), empty);
        Assert.assertEquals(0, empty.size());

        Assert.assertEquals(1, extracted.size());

    }

    // /*
    // * Test that getFormattedString(...) returns strings that are line broken
    // * after each 10 characters
    // */
    // @Test
    // public void testgetFormattedString() {
    // final SequenceDiagramRenderer subject = new SequenceDiagramRenderer();
    //
    // ArrayList<String> testStrings = new ArrayList<String>();
    // ArrayList<String> resultStrings = new ArrayList<String>();
    // final int noOfChars = 80;
    // for (int i = 0; i < noOfChars; i++) {
    // String str = "";
    // for (char j = 65; j < i + 65; j++) {
    // str += Character.toString(j);
    // }
    // testStrings.add(str);
    // }
    //
    // for (int i = 0; i < noOfChars; i++) {
    // String str = "";
    // for (char j = 65; j < i + 65; j++) {
    // str += Character.toString(j);
    // if (i >= 9 && (j - 64) % 10 == 0 && (j - 64 > 9) && j < i + 64) {
    // str += NEWLINE;
    // }
    // }
    // resultStrings.add(str);
    // }
    //
    // for (int i = 0; i < testStrings.size(); i++) {
    // String testString = testStrings.get(i);
    // String retVal = subject.getFormattedString(testString, true);
    // String string = resultStrings.get(i);
    // Assert.assertEquals(retVal, string);
    // }
    //
    // }

    @Test
    public void testDraw() {}
}
