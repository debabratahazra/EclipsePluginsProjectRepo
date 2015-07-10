package com.zealcore.se.ui.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BitCheckTest {

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int FOUR = 4;

    private static final int EIGHT = 8;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testAnyOf() {

        Assert.assertTrue(BitCheck.anyOf(BitCheckTest.ONE | BitCheckTest.TWO
                | BitCheckTest.FOUR, BitCheckTest.ONE, BitCheckTest.TWO));
        Assert.assertFalse(BitCheck.anyOf(BitCheckTest.FOUR
                | BitCheckTest.EIGHT, BitCheckTest.ONE, BitCheckTest.TWO));

    }

}
