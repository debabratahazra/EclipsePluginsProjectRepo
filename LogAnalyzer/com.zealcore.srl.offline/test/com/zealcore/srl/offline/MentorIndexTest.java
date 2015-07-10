package com.zealcore.srl.offline;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class MentorIndexTest {
    private static final String FOO1 = "FOO1";

    private static final String FOO0 = "Foo0";

    private MentorIndex mentorIndex = new MentorIndex();

    @Before
    public void setUp() throws Exception {}

    @Test
    public void testGetSignal() {
        Assert.assertEquals("signal0", mentorIndex.getSignal(0));
        mentorIndex.putSignal(0, FOO0);
        Assert.assertEquals(FOO0, mentorIndex.getSignal(0));
        Assert.assertEquals("signal1", mentorIndex.getSignal(1));
        mentorIndex.putSignal(1, FOO1);
        Assert.assertEquals(FOO1, mentorIndex.getSignal(1));
    }

    @Test
    public void testGetClazz() {
        Assert.assertEquals("class0", mentorIndex.getClazz(0));
        mentorIndex.putClazz(0, FOO0);
        Assert.assertEquals(FOO0, mentorIndex.getClazz(0));
        Assert.assertEquals("class1", mentorIndex.getClazz(1));
        mentorIndex.putClazz(1, FOO1);
        Assert.assertEquals(FOO1, mentorIndex.getClazz(1));
    }

    @Test
    public void testGetState() {
        Assert.assertEquals("state0", mentorIndex.getState(0));
        mentorIndex.putState(0, FOO0);
        Assert.assertEquals(FOO0, mentorIndex.getState(0));
        Assert.assertEquals("state1", mentorIndex.getState(1));
        mentorIndex.putState(1, FOO1);
        Assert.assertEquals(FOO1, mentorIndex.getState(1));
    }

}
