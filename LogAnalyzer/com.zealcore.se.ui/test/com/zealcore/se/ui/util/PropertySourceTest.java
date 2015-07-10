package com.zealcore.se.ui.util;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.zealcore.se.core.model.SEProperty;

public class PropertySourceTest {

    private static final String ONE = "1";

    private static final String TWO = "12";

    private static final String THREE = "123";

    @Test
    public void divideTimestampStringTest() {

        String oneBefore = ONE;
        String oneAfter = ONE;
        Assert.assertEquals(oneAfter, PropertySource
                .splitTimestampString(oneBefore));

        String twoBefore = TWO;
        String twoAfter = TWO;
        Assert.assertEquals(twoAfter, PropertySource
                .splitTimestampString(twoBefore));

        String threeBefore = THREE;
        String threeAfter = THREE;
        Assert.assertEquals(threeAfter, PropertySource
                .splitTimestampString(threeBefore));

        String fourBefore = "1234";
        String fourAfter = "1 234";
        Assert.assertEquals(fourAfter, PropertySource
                .splitTimestampString(fourBefore));

        String fiveBefore = "12345";
        String fiveAfter = "12 345";
        Assert.assertEquals(fiveAfter, PropertySource
                .splitTimestampString(fiveBefore));

        String sixBefore = "123456";
        String sixAfter = "123 456";
        Assert.assertEquals(sixAfter, PropertySource
                .splitTimestampString(sixBefore));

        String sevenBefore = "1234567";
        String sevenAfter = "1 234 567";
        Assert.assertEquals(sevenAfter, PropertySource
                .splitTimestampString(sevenBefore));

        String eightBefore = "12345678";
        String eightAfter = "12 345 678";
        Assert.assertEquals(eightAfter, PropertySource
                .splitTimestampString(eightBefore));

        String nineBefore = "123456789";
        String nineAfter = "123 456 789";
        Assert.assertEquals(nineAfter, PropertySource
                .splitTimestampString(nineBefore));

        String tenBefore = "1234567890";
        String tenAfter = "1 234 567 890";
        Assert.assertEquals(tenAfter, PropertySource
                .splitTimestampString(tenBefore));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void getPropertyValue() {
        String data = "Data";
        String structName = "myStruct";
        ArrayList<String> structData = new ArrayList<String>();
       
        String type1 = "type1";
        String data1 = "data1";
        String type2 = "type2";
        String data2 = "data2";
        String type3 = "type3";
        String data3 = "data3";
       
        structData.add(structName);
        structData.add("{" + type1 + "=");
        structData.add(data1 + ";");
        structData.add(type2);
        structData.add("  =" + data2 + "   ;");
        structData.add(type3);
        structData.add("  =   " + data3 + "   ;   " + "}");
        
        SEProperty sep = new SEProperty(data, structData);
        ArrayList<String> data4 = (ArrayList<String>)sep.getData();
        for (int i = 0; i < data4.size(); i++) {
            Assert.assertEquals(data4.get(i), structData.get(i)); 
        }
    }
}
