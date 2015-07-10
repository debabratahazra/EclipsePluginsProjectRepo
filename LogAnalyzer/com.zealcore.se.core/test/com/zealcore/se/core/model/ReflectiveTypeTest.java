package com.zealcore.se.core.model;

import junit.framework.Assert;

import org.junit.Test;

public class ReflectiveTypeTest {

    @Test
    public void testHashcodeEquals() {

        final ReflectiveType one = ReflectiveType
                .valueOf(ILogEvent.class);
        final ReflectiveType two = ReflectiveType
                .valueOf(ILogEvent.class);

        Assert.assertEquals(one.hashCode(), two.hashCode());
        Assert.assertEquals(one, two);

        Assert.assertNotNull(one.getId());
        Assert.assertNotNull(two.getId());
    }

    @Test
    public void testIsA() {
        final ReflectiveType one = ReflectiveType
                .valueOf(ILogEvent.class);
        final ReflectiveType two = ReflectiveType.valueOf(IObject.class);

        Assert.assertTrue(one.isA(two));
        Assert.assertFalse(two.isA(one));
    }
}
