package com.zealcore.se.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SearchAdapterTest {

    private static final String LESS_1000 = "<1000";

    private static final String LESS_EQ_1000 = "<=1000";

    private static final String MORE_THAN_1000 = ">1000";

    private static final String MORE_OR_EQ_1000 = ">=1000";

    private static final String SHOULD_TREAT_OPTIONAL_WHITESPACE = "Should not fail on optional whitespace";

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testIntRegexp() {
        final String subject = SearchAdapter.INT_REGEXP;

        Assert.assertTrue(SearchAdapterTest.LESS_1000.matches(subject));
        Assert.assertTrue(SearchAdapterTest.LESS_EQ_1000.matches(subject));
        Assert.assertTrue(SearchAdapterTest.MORE_THAN_1000.matches(subject));
        Assert.assertTrue(SearchAdapterTest.MORE_OR_EQ_1000.matches(subject));
        Assert.assertTrue("Optional Equals", "1000".matches(subject));

        Assert.assertTrue("Not Equals test fail", "!=333".matches(subject));

    }

    @Ignore
    @Test
    public void testIntRegExpMultipleWhiteSpace() {
        final String subject = SearchAdapter.INT_REGEXP;
        Assert.assertTrue("< 1000".matches(subject));
        Assert.assertTrue("<= 1000".matches(subject));
        Assert.assertTrue("> 1000".matches(subject));
        Assert.assertTrue(">= 1000".matches(subject));

        Assert.assertTrue(SearchAdapterTest.SHOULD_TREAT_OPTIONAL_WHITESPACE,
                "<   1000".matches(subject));
        Assert.assertTrue(SearchAdapterTest.SHOULD_TREAT_OPTIONAL_WHITESPACE,
                "<=    1000".matches(subject));
        Assert.assertTrue(SearchAdapterTest.SHOULD_TREAT_OPTIONAL_WHITESPACE,
                ">             1000".matches(subject));
        Assert.assertTrue(SearchAdapterTest.SHOULD_TREAT_OPTIONAL_WHITESPACE,
                ">=        1000".matches(subject));

    }

    @Test
    public void testFloatRegExp() {
        final String subject = SearchAdapter.FLOAT_REGEXP;

        Assert.assertTrue(SearchAdapterTest.LESS_1000.matches(subject));
        Assert.assertTrue(SearchAdapterTest.LESS_EQ_1000.matches(subject));
        Assert.assertTrue(SearchAdapterTest.MORE_THAN_1000.matches(subject));
        Assert.assertTrue(SearchAdapterTest.MORE_OR_EQ_1000.matches(subject));

        Assert.assertTrue("<1.000".matches(subject));
        Assert.assertTrue("<=100.0".matches(subject));
        Assert.assertTrue(">10.00".matches(subject));
        Assert.assertTrue(">=100034".matches(subject));

        Assert.assertFalse("<.1000".matches(subject));
        Assert.assertFalse("<=10.0.0".matches(subject));
        Assert.assertFalse(">1000.".matches(subject));

    }

    @Test
    public void testStringMatch() {
        final String subject = SearchAdapter.STRING_REGEXP;

        Assert.assertTrue("56908sfd8902*43#�%/sdflkj6�%|�^".matches(subject));
        Assert.assertTrue("".matches(subject));
    }

}
