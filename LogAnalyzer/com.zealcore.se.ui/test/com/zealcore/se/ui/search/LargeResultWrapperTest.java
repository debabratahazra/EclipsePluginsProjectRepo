package com.zealcore.se.ui.search;

import org.junit.Assert;
import org.junit.Test;

public class LargeResultWrapperTest {

    private int depth;

    @Test
    public void testWrap() {

        int size = 1000000;
        Integer[] data = new Integer[size];

        for (int i = 0; i < size; i++) {
            data[i] = i;
        }
        checkRecursive(new LargeResultWrapper(data).wrap(false));
    }

    public void checkRecursive(final Object[] wrap) {
        // System.out.println("Depth: " + depth++);
        Assert.assertTrue("Size must be less than MAX but was " + wrap.length,
                wrap.length <= LargeResultWrapper.MAX);
        for (Object object : wrap) {
            if (object instanceof LargeResultWrapper) {
                LargeResultWrapper wrapper = (LargeResultWrapper) object;
                // System.out.println(wrapper.getLabel(wrapper));
                checkRecursive(wrapper.getChildren(wrapper));
            }
        }
        depth--;
    }
}
