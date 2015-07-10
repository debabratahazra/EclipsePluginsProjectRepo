package com.zealcore.se.ui.editors;

import junit.framework.Assert;

import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

import com.zealcore.se.core.util.Span;

public class LinearScaleSelectorTest {

    @Test
    public void testLinearScalertoScreen() {
        long firstDuration = 1234;
        long lastDuration = 5345;
        long midPoint = (firstDuration + lastDuration) / 2;

        long i = firstDuration;
        double ratio = 0.0;
        int xcord = 0;

        final Rectangle dataScreenArea = new Rectangle(0, 0, 400, 300);

        SimpleLinearScaleRuler ruler = new SimpleLinearScaleRuler(midPoint,
                Span.valueOf(firstDuration, lastDuration), Span.valueOf(
                        dataScreenArea.x, dataScreenArea.width));

        while (i < lastDuration) {

            System.out.println(i + " " + ruler.toScreen(i));
            ratio = (double) (i - firstDuration)
                    / (lastDuration - firstDuration);
            xcord = (int) (0.5 + (ratio
                    * (dataScreenArea.width - dataScreenArea.x) + dataScreenArea.x));

            Assert.assertEquals("1:1 transform", xcord, ruler.toScreen(i));

            ++i;

        }
    }

    @Test
    public void testLinearScaletoTimestamp() {
        long firstDuration = 1234;
        long lastDuration = 5345;
        long midPoint = (firstDuration + lastDuration) / 2;
        final Rectangle dataScreenArea = new Rectangle(0, 0, 400, 300);

        int i = dataScreenArea.x;
        double ratio = 0.0;
        long timestamp = 0;

        SimpleLinearScaleRuler ruler = new SimpleLinearScaleRuler(midPoint,
                Span.valueOf(firstDuration, lastDuration), Span.valueOf(
                        dataScreenArea.x, dataScreenArea.width));

        while (i < dataScreenArea.width) {

            System.out.println(i + " " + ruler.toTimestamp(i));
            ratio = (double) (i - dataScreenArea.x)
                    / (dataScreenArea.width - dataScreenArea.x);
            timestamp = (long) (0.5 + (ratio * (lastDuration - firstDuration) + firstDuration));

            Assert.assertEquals("1:1 transform", timestamp,
                    ruler.toTimestamp(i));

            ++i;

        }
    }

}
